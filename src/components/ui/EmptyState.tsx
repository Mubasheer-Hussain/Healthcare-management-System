import type { LucideIcon } from "lucide-react";

export default function EmptyState({
  icon: Icon,
  title,
  description,
}: {
  icon: LucideIcon;
  title: string;
  description: string;
}) {
  return (
    <div className="flex flex-col items-center justify-center rounded-2xl border border-dashed border-border bg-surface-muted/60 px-6 py-16 text-center">
      <div className="grid size-12 place-items-center rounded-full bg-surface text-teal-500 shadow-card">
        <Icon className="size-5" strokeWidth={2} />
      </div>
      <h3 className="mt-4 font-display text-base font-semibold text-ink">{title}</h3>
      <p className="mt-1.5 max-w-sm text-sm text-ink-soft">{description}</p>
    </div>
  );
}
