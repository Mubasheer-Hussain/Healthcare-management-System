import { useState } from "react";
import { CalendarX } from "lucide-react";
import Card from "@/components/ui/Card";
import Badge from "@/components/ui/Badge";
import AppointmentRow from "@/components/AppointmentRow";
import EmptyState from "@/components/ui/EmptyState";
import { doctorAppointments } from "@/data/mockData";
import type { AppointmentStatus } from "@/types";

const tabs: { key: AppointmentStatus; label: string }[] = [
  { key: "upcoming", label: "Upcoming" },
  { key: "completed", label: "Completed" },
  { key: "cancelled", label: "Cancelled" },
];

export default function DoctorAppointments() {
  const [tab, setTab] = useState<AppointmentStatus>("upcoming");
  const filtered = doctorAppointments.filter((a) => a.status === tab);

  return (
    <div className="space-y-6">
      <div className="flex flex-wrap items-center justify-between gap-4">
        <div>
          <h1 className="font-display text-2xl font-bold text-ink">Appointments</h1>
          <p className="mt-1 text-sm text-ink-soft">Manage your patient schedule.</p>
        </div>
        <Badge tone="teal">Accepting new patients</Badge>
      </div>

      <div className="flex gap-2 rounded-xl bg-surface-muted p-1 sm:w-fit">
        {tabs.map((t) => (
          <button
            key={t.key}
            onClick={() => setTab(t.key)}
            className={`flex-1 rounded-lg px-4 py-2 text-sm font-medium transition-colors sm:flex-none ${
              tab === t.key ? "bg-surface text-ink shadow-card" : "text-ink-soft"
            }`}
          >
            {t.label}
          </button>
        ))}
      </div>

      <Card className="p-6">
        {filtered.length > 0 ? (
          filtered.map((a) => <AppointmentRow key={a.id} appointment={a} />)
        ) : (
          <EmptyState
            icon={CalendarX}
            title={`No ${tab} appointments`}
            description="Your schedule for this filter is clear."
          />
        )}
      </Card>
    </div>
  );
}
