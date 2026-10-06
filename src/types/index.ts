export type UserRole = "patient" | "doctor";

export type AppointmentStatus = "upcoming" | "completed" | "cancelled";

export interface Doctor {
  id: string;
  name: string;
  specialty: string;
  clinic: string;
  experienceYears: number;
  rating: number;
  reviewCount: number;
  nextSlot: string;
  initials: string;
  accent: "teal" | "sky" | "coral" | "amber";
}

export interface Appointment {
  id: string;
  personName: string; // doctor name (patient view) or patient name (doctor view)
  specialtyOrReason: string;
  date: string;
  time: string;
  status: AppointmentStatus;
  mode: "In-person" | "Video call";
}

export type RecordType = "prescription" | "lab" | "scan";

export interface MedicalRecord {
  id: string;
  title: string;
  type: RecordType;
  uploadedBy: string;
  date: string;
  timestamp: string;
}

export interface Conversation {
  id: string;
  withName: string;
  withRole: string;
  lastMessage: string;
  timestamp: string;
  unread: number;
}

export interface ChatMessage {
  id: string;
  from: "me" | "them";
  text: string;
  time: string;
}
