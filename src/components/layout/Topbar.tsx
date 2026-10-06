import { Link } from "react-router-dom";
import { Bell, Menu } from "lucide-react";
import type { UserRole } from "@/types";

const identity: Record<UserRole, { name: string; sub: string; initials: string }> = {
  patient: { name: "Emily Carter", sub: "Patient", initials: "EC" },
  doctor: { name: "Dr. Sarah Johnson", sub: "Cardiologist", initials: "SJ" },
};

export default function Topbar({
  role,
  onMenuClick,
}: {
  role: UserRole;
  onMenuClick: () => void;
}) {
  const me = identity[role];
  const otherRole: UserRole = role === "patient" ? "doctor" : "patient";

  return (
    <header className="flex h-16 shrink-0 items-center justify-between border-b border-border bg-surface px-4 md:px-8">
      <button
        onClick={onMenuClick}
        className="grid size-9 place-items-center rounded-lg text-ink-soft hover:bg-surface-muted md:hidden"
        aria-label="Open menu"
      >
        <Menu className="size-5" />
      </button>

      <div className="hidden text-sm text-ink-soft md:block">
        Welcome back, <span className="font-medium text-ink">{me.name.split(" ")[0]}</span>
      </div>

      <div className="flex items-center gap-2 md:gap-4">
        <Link
          to={`/${otherRole}`}
          className="hidden rounded-full border border-border px-3 py-1.5 text-xs font-medium text-ink-soft hover:border-teal-400 hover:text-teal-600 sm:block"
          title="Demo aid — view the other portal (no backend auth wired up yet)"
        >
          Preview {otherRole} view
        </Link>

        <button
          className="relative grid size-9 place-items-center rounded-lg text-ink-soft hover:bg-surface-muted"
          aria-label="Notifications"
        >
          <Bell className="size-4.5" />
          <span className="absolute right-2 top-2 size-1.5 rounded-full bg-coral-500" />
        </button>

        <div className="flex items-center gap-2.5 border-l border-border pl-3">
          <div className="grid size-9 place-items-center rounded-full bg-teal-100 font-display text-xs font-bold text-teal-700">
            {me.initials}
          </div>
          <div className="hidden leading-tight sm:block">
            <p className="text-sm font-medium text-ink">{me.name}</p>
            <p className="text-xs text-ink-soft">{me.sub}</p>
          </div>
        </div>
      </div>
    </header>
  );
}
