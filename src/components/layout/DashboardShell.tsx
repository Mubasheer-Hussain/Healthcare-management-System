import { useState } from "react";
import { Outlet } from "react-router-dom";
import { X } from "lucide-react";
import Sidebar, { SidebarContent } from "./Sidebar";
import Topbar from "./Topbar";
import type { UserRole } from "@/types";

export default function DashboardShell({ role }: { role: UserRole }) {
  const [mobileOpen, setMobileOpen] = useState(false);

  return (
    <div className="flex h-screen overflow-hidden bg-bg">
      <Sidebar role={role} />

      {mobileOpen && (
        <div className="fixed inset-0 z-50 flex md:hidden">
          <div
            className="fixed inset-0 bg-ink/40"
            onClick={() => setMobileOpen(false)}
            aria-hidden
          />
          <div className="relative flex w-72 flex-col bg-surface shadow-pop">
            <button
              onClick={() => setMobileOpen(false)}
              className="absolute right-3 top-4 grid size-8 place-items-center rounded-lg text-ink-soft hover:bg-surface-muted"
              aria-label="Close menu"
            >
              <X className="size-4.5" />
            </button>
            <SidebarContent role={role} onNavigate={() => setMobileOpen(false)} />
          </div>
        </div>
      )}

      <div className="flex min-w-0 flex-1 flex-col">
        <Topbar role={role} onMenuClick={() => setMobileOpen(true)} />
        <main className="flex-1 overflow-y-auto p-4 md:p-8">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
