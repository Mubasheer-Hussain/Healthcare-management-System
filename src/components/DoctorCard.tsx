import { Star, Clock } from "lucide-react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import type { Doctor } from "@/types";

const accentClasses = {
  teal: "bg-teal-100 text-teal-700",
  sky: "bg-sky-50 text-sky-500",
  coral: "bg-coral-50 text-coral-500",
  amber: "bg-amber-400/15 text-amber-400",
};

export default function DoctorCard({ doctor }: { doctor: Doctor }) {
  return (
    <Card className="flex flex-col gap-4 p-5">
      <div className="flex items-start justify-between gap-3">
        <div className="flex items-center gap-3">
          <div
            className={`grid size-12 shrink-0 place-items-center rounded-full font-display text-sm font-bold ${accentClasses[doctor.accent]}`}
          >
            {doctor.initials}
          </div>
          <div>
            <p className="font-display font-semibold text-ink">{doctor.name}</p>
            <p className="text-sm text-ink-soft">{doctor.specialty}</p>
          </div>
        </div>
        <div className="flex items-center gap-1 text-sm font-medium text-ink">
          <Star className="size-3.5 fill-amber-400 text-amber-400" />
          {doctor.rating}
          <span className="text-ink-soft">({doctor.reviewCount})</span>
        </div>
      </div>

      <p className="text-sm text-ink-soft">
        {doctor.experienceYears}+ years exp · {doctor.clinic}
      </p>

      <div className="mt-auto flex items-center justify-between border-t border-border pt-4">
        <div className="flex items-center gap-1.5 text-sm text-ink-soft">
          <Clock className="size-4" />
          Next: <span className="font-medium text-ink">{doctor.nextSlot}</span>
        </div>
        <Button size="sm">Book now</Button>
      </div>
    </Card>
  );
}
