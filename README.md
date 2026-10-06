# MediConnect — Frontend

Hospital Management System frontend. Vite + React 19 + TypeScript + Tailwind v4 + React Router.

## What's here

This is the **app shell**: routing, layout, and placeholder pages for every feature in the
brief, wired up with mock data so every screen is navigable and looks real. No backend calls
yet — that's the next step, hooking these pages up to the Spring Boot API.

### Structure

```
src/
  components/
    layout/       PublicLayout, DashboardShell, Sidebar, Topbar, Public navbar/footer
    ui/            Button, Card, Badge, StatCard, EmptyState, Logo
    DoctorCard.tsx, AppointmentRow.tsx, RecordRow.tsx, MessagesView.tsx
  pages/
    Landing.tsx, Login.tsx, Signup.tsx, NotFound.tsx
    patient/       PatientDashboard, FindDoctors, MyAppointments, MedicalRecords, Messages
    doctor/        DoctorDashboard, DoctorAppointments, DoctorPatients, DoctorMessages
  data/mockData.ts  Placeholder doctors, appointments, records, conversations
  types/index.ts    Shared TS types — mirror these when you design your Spring Boot DTOs
```

### Routes

| Path | Page |
|---|---|
| `/` | Landing |
| `/login`, `/signup` | Auth (role toggle patient/doctor, no backend wired yet) |
| `/patient`, `/patient/find-doctors`, `/patient/appointments`, `/patient/records`, `/patient/messages` | Patient portal |
| `/doctor`, `/doctor/appointments`, `/doctor/patients`, `/doctor/messages` | Doctor portal |

There's a "Preview patient/doctor view" link in the dashboard topbar so you can jump between
portals without real auth while the backend isn't connected yet.

## Run it

```bash
npm install
npm run dev
```

## Build

```bash
npm run build
```

## Next steps (suggested order)

1. Wire `/login` and `/signup` to real auth endpoints (JWT, per the tech stack) and gate the
   `/patient` and `/doctor` routes behind it instead of the current open preview links.
2. Replace `src/data/mockData.ts` reads with real API calls (e.g. React Query or plain fetch)
   for doctors, appointments, and records.
3. Build out the appointment booking flow inside `FindDoctors.tsx` — the "Book now" button is
   currently a no-op.
4. Wire file upload in `MedicalRecords.tsx` to your cloud storage endpoint.
5. Replace the in-memory chat state in `MessagesView.tsx` with real-time messaging
   (WebSocket/STOMP over Spring, or polling to start).
