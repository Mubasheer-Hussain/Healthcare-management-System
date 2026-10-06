import { Link } from "react-router-dom";
import { CalendarCheck, FileText, MessageCircle, Search, ArrowRight, MapPin } from "lucide-react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import StatCard from "@/components/ui/StatCard";
import AppointmentRow from "@/components/AppointmentRow";
import { patientAppointments, medicalRecords } from "@/data/mockData";

export default function PatientDashboard() {
  const upcoming = patientAppointments.filter((a) => a.status === "upcoming");
  const next = upcoming[0];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="font-display text-2xl font-bold text-ink">Your health, at a glance</h1>
        <p className="mt-1 text-sm text-ink-soft">Here's what's on deck this week.</p>
      </div>

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
        <StatCard label="Upcoming appointments" value={String(upcoming.length)} icon={CalendarCheck} tone="teal" />
        <StatCard label="Records on file" value={String(medicalRecords.length)} icon={FileText} tone="sky" />
        <StatCard label="Unread messages" value="2" icon={MessageCircle} tone="coral" />
      </div>

      <div className="grid gap-6 lg:grid-cols-3">
        <Card className="p-6 lg:col-span-2">
          <div className="mb-1 flex items-center justify-between">
            <h2 className="font-display font-semibold text-ink">Upcoming appointments</h2>
            <Link to="/patient/appointments" className="text-sm font-medium text-teal-600 hover:underline">
              View all
            </Link>
          </div>
          <div>
            {upcoming.map((a) => (
              <AppointmentRow key={a.id} appointment={a} />
            ))}
          </div>
        </Card>

        <Card className="flex flex-col gap-4 bg-teal-500 p-6 text-white">
          <p className="text-xs font-medium uppercase tracking-wide text-teal-50/80">Next up</p>
          <div>
            <p className="font-display text-lg font-semibold">{next?.personName}</p>
            <p className="text-sm text-teal-50/90">{next?.specialtyOrReason}</p>
          </div>
          <div className="flex items-center gap-2 text-sm text-teal-50/90">
            <MapPin className="size-4" />
            {next?.date}, {next?.time}
          </div>
          <Button variant="secondary" size="sm" className="mt-2 w-fit bg-white text-teal-700 hover:bg-teal-50">
            View details <ArrowRight className="size-3.5" />
          </Button>
        </Card>
      </div>

      <div className="grid gap-4 sm:grid-cols-3">
        <QuickAction to="/patient/find-doctors" icon={Search} label="Find a doctor" />
        <QuickAction to="/patient/records" icon={FileText} label="View records" />
        <QuickAction to="/patient/messages" icon={MessageCircle} label="Message your doctor" />
      </div>
    </div>
  );
}

function QuickAction({
  to,
  icon: Icon,
  label,
}: {
  to: string;
  icon: typeof Search;
  label: string;
}) {
  return (
    <Link to={to}>
      <Card className="flex items-center gap-3 p-4 transition-colors hover:border-teal-300">
        <div className="grid size-10 shrink-0 place-items-center rounded-lg bg-teal-50 text-teal-600">
          <Icon className="size-4.5" />
        </div>
        <span className="text-sm font-medium text-ink">{label}</span>
        <ArrowRight className="ml-auto size-4 text-ink-soft" />
      </Card>
    </Link>
  );
}
