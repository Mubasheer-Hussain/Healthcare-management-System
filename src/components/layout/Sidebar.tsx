import { Link, NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Search,
  CalendarCheck,
  FileText,
  MessageCircle,
  Users,
  LogOut,
} from "lucide-react";
import Logo from "@/components/ui/Logo";
import type { UserRole } from "@/types";

const patientLinks = [
  { to: "/patient", label: "Dashboard", icon: LayoutDashboard, end: true },
  { to: "/patient/find-doctors", label: "Find Doctors", icon: Search },
  { to: "/patient/appointments", label: "Appointments", icon: CalendarCheck },
  { to: "/patient/records", label: "Medical Records", icon: FileText },
  { to: "/patient/messages", label: "Messages", icon: MessageCircle },
];

const doctorLinks = [
  { to: "/doctor", label: "Dashboard", icon: LayoutDashboard, end: true },
  { to: "/doctor/appointments", label: "Appointments", icon: CalendarCheck },
  { to: "/doctor/patients", label: "Patients", icon: Users },
  { to: "/doctor/messages", label: "Messages", icon: MessageCircle },
];

export function SidebarContent({
  role,
  onNavigate,
}: {
  role: UserRole;
  onNavigate?: () => void;
}) {
  const links = role === "patient" ? patientLinks : doctorLinks;

  return (
    <>
      <div className="flex h-16 items-center border-b border-border px-6">
        <Link to="/">
          <Logo />
        </Link>
      </div>

      <nav className="flex-1 space-y-1 px-3 py-6">
        <p className="px-3 pb-2 text-xs font-semibold uppercase tracking-wide text-ink-soft/70">
          {role === "patient" ? "Patient portal" : "Doctor portal"}
        </p>
        {links.map(({ to, label, icon: Icon, end }) => (
          <NavLink
            key={to}
            to={to}
            end={end}
            onClick={onNavigate}
            className={({ isActive }) =>
              `flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-medium transition-colors ${
                isActive
                  ? "bg-teal-50 text-teal-700"
                  : "text-ink-soft hover:bg-surface-muted hover:text-ink"
              }`
            }
          >
            <Icon className="size-4.5" strokeWidth={2} />
            {label}
          </NavLink>
        ))}
      </nav>

      <div className="border-t border-border p-3">
        <NavLink
          to="/login"
          onClick={onNavigate}
          className="flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-medium text-ink-soft transition-colors hover:bg-surface-muted hover:text-coral-500"
        >
          <LogOut className="size-4.5" strokeWidth={2} />
          Log out
        </NavLink>
      </div>
    </>
  );
}

export default function Sidebar({ role }: { role: UserRole }) {
  return (
    <aside className="hidden w-64 shrink-0 flex-col border-r border-border bg-surface md:flex">
      <SidebarContent role={role} />
    </aside>
  );
}
