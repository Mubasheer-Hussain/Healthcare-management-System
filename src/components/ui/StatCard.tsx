import type { LucideIcon } from "lucide-react";
import Card from "./Card";

export default function StatCard({
  label,
  value,
  icon: Icon,
  tone = "teal",
}: {
  label: string;
  value: string;
  icon: LucideIcon;
  tone?: "teal" | "sky" | "coral" | "amber";
}) {
  const toneClasses = {
    teal: "bg-teal-50 text-teal-600",
    sky: "bg-sky-50 text-sky-500",
    coral: "bg-coral-50 text-coral-500",
    amber: "bg-amber-400/15 text-amber-400",
  }[tone];

  return (
    <Card className="flex items-center gap-4 p-5">
      <div className={`grid size-11 shrink-0 place-items-center rounded-xl ${toneClasses}`}>
        <Icon className="size-5" strokeWidth={2} />
      </div>
      <div>
        <p className="font-display text-2xl font-bold text-ink">{value}</p>
        <p className="text-sm text-ink-soft">{label}</p>
      </div>
    </Card>
  );
}
