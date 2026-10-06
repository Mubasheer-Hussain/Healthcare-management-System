import type { ReactNode } from "react";

type Tone = "teal" | "sky" | "coral" | "amber" | "neutral";

const toneClasses: Record<Tone, string> = {
  teal: "bg-teal-50 text-teal-700",
  sky: "bg-sky-50 text-sky-500",
  coral: "bg-coral-50 text-coral-500",
  amber: "bg-amber-400/15 text-amber-400",
  neutral: "bg-surface-muted text-ink-soft",
};

export default function Badge({
  tone = "neutral",
  children,
  className = "",
}: {
  tone?: Tone;
  children: ReactNode;
  className?: string;
}) {
  return (
    <span
      className={`inline-flex items-center gap-1 rounded-full px-2.5 py-1 text-xs font-medium ${toneClasses[tone]} ${className}`}
    >
      {children}
    </span>
  );
}
