interface LogoProps {
  className?: string;
  wordmarkClassName?: string;
}

export default function Logo({ className = "", wordmarkClassName = "" }: LogoProps) {
  return (
    <div className={`flex items-center gap-2.5 ${className}`}>
      <span className="grid size-8 shrink-0 place-items-center rounded-lg bg-gradient-to-br from-teal-400 to-teal-600 shadow-pop">
        <svg viewBox="0 0 24 24" className="size-4.5 fill-white">
          <path d="M10.5 4h3v5h5v3h-5v5h-3v-5h-5V9h5z" />
        </svg>
      </span>
      <span className={`font-display text-lg font-bold tracking-tight text-ink ${wordmarkClassName}`}>
        MediConnect
      </span>
    </div>
  );
}
