import type { HTMLAttributes, ReactNode } from "react";

export default function Card({
  children,
  className = "",
  ...props
}: HTMLAttributes<HTMLDivElement> & { children: ReactNode }) {
  return (
    <div
      className={`rounded-2xl border border-border bg-surface shadow-card ${className}`}
      {...props}
    >
      {children}
    </div>
  );
}
