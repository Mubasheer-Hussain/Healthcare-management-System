import { Link } from "react-router-dom";
import { CalendarCheck, Users, MessageCircle, ArrowRight } from "lucide-react";
import Card from "@/components/ui/Card";
import StatCard from "@/components/ui/StatCard";
import AppointmentRow from "@/components/AppointmentRow";
import { doctorAppointments } from "@/data/mockData";

export default function DoctorDashboard() {
  const today = doctorAppointments.filter((a) => a.date === "Today");

  return (
    <div className="space-y-6">
      <div>
        <h1 className="font-display text-2xl font-bold text-ink">Today's schedule</h1>
        <p className="mt-1 text-sm text-ink-soft">You have {today.length} appointments today.</p>
      </div>

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
        <StatCard label="Today's appointments" value={String(today.length)} icon={CalendarCheck} tone="teal" />
        <StatCard label="Active patients" value="128" icon={Users} tone="sky" />
        <StatCard label="Unread messages" value="4" icon={MessageCircle} tone="coral" />
      </div>

      <Card className="p-6">
        <div className="mb-1 flex items-center justify-between">
          <h2 className="font-display font-semibold text-ink">Upcoming today</h2>
          <Link to="/doctor/appointments" className="text-sm font-medium text-teal-600 hover:underline">
            View all
          </Link>
        </div>
        <div>
          {doctorAppointments
            .filter((a) => a.status === "upcoming")
            .map((a) => (
              <AppointmentRow key={a.id} appointment={a} />
            ))}
        </div>
      </Card>

      <Card className="p-6">
        <div className="mb-4 flex items-center justify-between">
          <h2 className="font-display font-semibold text-ink">Recent patient activity</h2>
          <Link to="/doctor/patients" className="text-sm font-medium text-teal-600 hover:underline">
            View patients
          </Link>
        </div>
        <div className="grid gap-3 sm:grid-cols-2">
          {["Emily Carter uploaded a lab report", "Daniel Kim sent a follow-up message"].map(
            (event) => (
              <div key={event} className="flex items-center gap-3 rounded-xl bg-bg p-3">
                <ArrowRight className="size-4 shrink-0 text-teal-500" />
                <p className="text-sm text-ink-soft">{event}</p>
              </div>
            ),
          )}
        </div>
      </Card>
    </div>
  );
}
