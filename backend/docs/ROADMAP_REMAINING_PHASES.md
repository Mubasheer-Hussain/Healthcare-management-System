# MediConnect — Remaining Phases Implementation Roadmap (Phases 2 – 6)

This document contains the complete technical blueprint, entity schemas, API contracts, and business logic for the remaining phases of the MediConnect backend.

---

## Database Architecture Overview
- **Active / Local Development**: **H2 Database (file-based)** stored at `./data/mediconnect-dev`. Zero installation required, persists across restarts, with web console at `http://localhost:8080/h2-console`.
- **Production Profile**: **PostgreSQL** (activate via `--spring.profiles.active=postgres`).
- All JPA entities are database-agnostic using standard ANSI SQL and Hibernate ORM.

---

## Phase 2: Appointments & Scheduling

### 1. Objective
Enable patients to book appointments with doctors for specific dates/times, view upcoming/past bookings, cancel bookings, and allow doctors to manage their agenda and update appointment status.

### 2. Entity Design (`com.mediconnect.backend.appointment.entity`)

#### `Appointment`
```java
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    /** e.g. "09:30", "14:00" */
    @Column(nullable = false, length = 10)
    private String timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AppointmentStatus status; // UPCOMING, COMPLETED, CANCELLED

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AppointmentMode mode;     // IN_PERSON, VIDEO_CALL

    @Column(length = 255)
    private String reason; // e.g. "Cardiology follow-up"

    @Column(columnDefinition = "TEXT")
    private String notes;
}
```

### 3. API Endpoints (`/api/v1/appointments`)
| Method | Endpoint | Access | Description |
|:---|:---|:---|:---|
| `POST` | `/api/v1/appointments` | `PATIENT` | Book a new appointment slot |
| `GET` | `/api/v1/appointments/my` | `PATIENT` | Get current patient's appointments (upcoming/past) |
| `GET` | `/api/v1/appointments/doctor/my` | `DOCTOR` | Get current doctor's schedule/agenda |
| `PATCH` | `/api/v1/appointments/{id}/status` | `PATIENT`, `DOCTOR` | Update status (`CANCELLED`, `COMPLETED`) |
| `GET` | `/api/v1/appointments/doctor/{doctorId}/available-slots` | `Public` / `PATIENT` | Query booked vs open slots for a date |

### 4. Business Logic & Validation
- **Double-booking Prevention**: A doctor cannot have two active appointments at the same date and `timeSlot`.
- **Authorization Enforcement**: Patients can only cancel their own appointments; doctors can mark appointments as completed or cancelled.

---

## Phase 3: Medical Records & File Management

### 1. Objective
Store, categorize, and serve medical documents (prescriptions, lab tests, scans) uploaded by doctors or patients, matching `RecordType = "prescription" | "lab" | "scan"` in the frontend.

### 2. Entity Design (`com.mediconnect.backend.record.entity`)

#### `MedicalRecord`
```java
@Entity
@Table(name = "medical_records")
public class MedicalRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by_id", nullable = false)
    private User uploadedBy;

    @Column(nullable = false, length = 150)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RecordType type; // PRESCRIPTION, LAB, SCAN

    @Column(nullable = false)
    private LocalDate recordDate;

    /** Path or unique object key in file storage */
    @Column(nullable = false)
    private String filePath;

    @Column(length = 100)
    private String contentType;

    private Long fileSizeBytes;
}
```

### 3. API Endpoints (`/api/v1/records`)
| Method | Endpoint | Access | Description |
|:---|:---|:---|:---|
| `GET` | `/api/v1/records/patient/me` | `PATIENT` | View own medical records |
| `GET` | `/api/v1/records/patient/{patientId}` | `DOCTOR` | Doctor views patient records |
| `POST` | `/api/v1/records/upload` | `PATIENT`, `DOCTOR` | Upload file (`MultipartFile`) + metadata |
| `GET` | `/api/v1/records/{id}/download` | `PATIENT`, `DOCTOR` | Download / stream file content |

---

## Phase 4: Conversations & Messaging

### 1. Objective
Enable direct communication between patients and doctors (as shown in the `Messages` tab in the UI), tracking conversation lists, message histories, and unread badges.

### 2. Entity Design (`com.mediconnect.backend.chat.entity`)

#### `Conversation`
- `id` (UUID)
- `participantOne` (`User`), `participantTwo` (`User`)
- `lastMessage` (String), `lastMessageTimestamp` (LocalDateTime)

#### `ChatMessage`
- `id` (UUID)
- `conversation` (`Conversation`)
- `sender` (`User`), `recipient` (`User`)
- `content` (TEXT)
- `sentAt` (LocalDateTime), `isRead` (boolean)

### 3. API Endpoints (`/api/v1/messages`)
| Method | Endpoint | Access | Description |
|:---|:---|:---|:---|
| `GET` | `/api/v1/conversations` | `Authenticated` | Get all user's active chats with unread counts |
| `GET` | `/api/v1/conversations/{conversationId}/messages` | `Authenticated` | Get message history |
| `POST` | `/api/v1/conversations/{conversationId}/messages` | `Authenticated` | Send message |
| `POST` | `/api/v1/conversations/{conversationId}/read` | `Authenticated` | Mark conversation as read |

---

## Phase 5: Notifications & Dashboard Aggregates

### 1. Objective
Provide unified stats for the dashboard headers:
- **Patient Dashboard**: Upcoming appointment count, latest prescriptions, pending messages.
- **Doctor Dashboard**: Today's patient queue count, pending consultations, recent reviews.
- **Notification alerts**: Appointment confirmations, reminder events, cancellation notices.

### 2. API Endpoints (`/api/v1/dashboard`)
| Method | Endpoint | Access | Description |
|:---|:---|:---|:---|
| `GET` | `/api/v1/dashboard/patient-summary` | `PATIENT` | Metrics & upcoming activity |
| `GET` | `/api/v1/dashboard/doctor-summary` | `DOCTOR` | Today's appointments, queue, revenue |
| `GET` | `/api/v1/notifications` | `Authenticated` | List recent notifications |

---

## Phase 6: Frontend Integration & Live Wiring

### 1. Objective
Replace mock data in Vite React frontend with real Axios API clients.
- Connect `FindDoctors.tsx` to `GET /api/v1/doctors` & `/api/v1/doctors/specialties`.
- Connect `Appointments.tsx` to `GET /api/v1/appointments/my` & `POST /api/v1/appointments`.
- Connect Auth (`LoginModal.tsx` & `RegisterModal.tsx`) to store real JWT in `localStorage` and attach Bearer token via Axios interceptors.
