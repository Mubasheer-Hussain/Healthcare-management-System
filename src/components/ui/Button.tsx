import type { ButtonHTMLAttributes, ReactNode } from "react";

type Variant = "primary" | "secondary" | "outline" | "ghost";
type Size = "sm" | "md" | "lg";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: Variant;
  size?: Size;
  children: ReactNode;
}

const variantClasses: Record<Variant, string> = {
  primary:
    "bg-teal-500 text-white hover:bg-teal-600 shadow-pop",
  secondary:
    "bg-ink text-white hover:bg-ink/90",
  outline:
    "border border-border bg-surface text-ink hover:border-teal-400 hover:text-teal-600",
  ghost: "text-ink-soft hover:bg-surface-muted hover:text-ink",
};

const sizeClasses: Record<Size, string> = {
  sm: "px-3 py-1.5 text-sm rounded-lg gap-1.5",
  md: "px-4 py-2.5 text-sm rounded-xl gap-2",
  lg: "px-6 py-3 text-base rounded-xl gap-2",
};

export default function Button({
  variant = "primary",
  size = "md",
  className = "",
  children,
  ...props
}: ButtonProps) {
  return (
    <button
      className={`inline-flex items-center justify-center font-medium transition-colors duration-150 disabled:cursor-not-allowed disabled:opacity-50 ${variantClasses[variant]} ${sizeClasses[size]} ${className}`}
      {...props}
    >
      {children}
    </button>
  );
}
