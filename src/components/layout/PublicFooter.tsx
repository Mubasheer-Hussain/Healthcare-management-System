import Logo from "@/components/ui/Logo";

export default function PublicFooter() {
  return (
    <footer className="border-t border-border bg-surface">
      <div className="mx-auto flex max-w-6xl flex-col gap-6 px-6 py-10 sm:flex-row sm:items-center sm:justify-between">
        <Logo />
        <p className="text-sm text-ink-soft">
          Built for a Hospital Management System coursework project.
        </p>
        <div className="flex gap-3 text-xs text-ink-soft">
          <span>Mubasheer</span>
          <span aria-hidden>·</span>
          <span>Waseem</span>
          <span aria-hidden>·</span>
          <span>Affaan</span>
        </div>
      </div>
    </footer>
  );
}
