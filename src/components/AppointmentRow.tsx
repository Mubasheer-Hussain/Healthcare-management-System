import { Video, MapPin } from "lucide-react";
import Badge from "@/components/ui/Badge";
import type { Appointment } from "@/types";

const statusTone = {
  upcoming: "teal",
  completed: "neutral",
  cancelled: "coral",
} as const;

const statusLabel = {
  upcoming: "Upcoming",
  completed: "Completed",
  cancelled: "Cancelled",
} as const;

export default function AppointmentRow({ appointment }: { appointment: Appointment }) {
  return (
    <div className="flex flex-col gap-3 border-b border-border py-4 last:border-0 sm:flex-row sm:items-center sm:justify-between">
      <div className="flex items-center gap-3">
        <div className="grid size-11 shrink-0 place-items-center rounded-xl bg-teal-50 font-display text-xs font-bold text-teal-700">
          {appointment.date === "Today" ? "Today" : appointment.date}
        </div>
        <div>
          <p className="font-medium text-ink">{appointment.personName}</p>
          <p className="text-sm text-ink-soft">{appointment.specialtyOrReason}</p>
        </div>
      </div>

      <div className="flex items-center gap-3 pl-14 sm:pl-0">
        <div className="flex items-center gap-1.5 text-sm text-ink-soft">
          {appointment.mode === "Video call" ? (
            <Video className="size-4" />
          ) : (
            <MapPin className="size-4" />
          )}
          {appointment.time}
        </div>
        <Badge tone={statusTone[appointment.status]}>{statusLabel[appointment.status]}</Badge>
      </div>
    </div>
  );
}
