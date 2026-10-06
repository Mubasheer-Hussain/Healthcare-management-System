import { Link } from "react-router-dom";
import {
  FileText,
  MessageCircle,
  Search,
  ShieldCheck,
  Clock,
  ArrowRight,
} from "lucide-react";
import Button from "@/components/ui/Button";
import Badge from "@/components/ui/Badge";
import Card from "@/components/ui/Card";

const pains = [
  "Booking an appointment means phone tag and guesswork on availability",
  "Records live in folders, fax machines, and forgotten portals",
  "A quick question after a visit means waiting for the next one",
];

const features = [
  {
    icon: Search,
    tone: "teal" as const,
    title: "Appointment booking",
    description:
      "Search doctors by specialty, distance, or clinic and watch availability update live as other patients book.",
    points: ["Location-based search", "Instant slot confirmation", "Real-time calendar sync"],
  },
  {
    icon: FileText,
    tone: "sky" as const,
    title: "Medical record sharing",
    description:
      "Prescriptions, lab reports, and scans in one secure timeline — with a full audit trail of who viewed what, and when.",
    points: ["Secure upload", "Access audit trail", "Chronological history"],
  },
  {
    icon: MessageCircle,
    tone: "coral" as const,
    title: "Bidirectional communication",
    description:
      "Ask a question between visits, get a follow-up check-in, or have a prescription adjusted — without booking a new slot.",
    points: ["Direct medical inquiries", "Post-visit follow-ups", "Medication updates"],
  },
];

const steps = [
  { title: "Create your profile", description: "Sign up as a patient or doctor in under a minute." },
  { title: "Find your match", description: "Search by specialty and book the next open slot." },
  { title: "Stay connected", description: "Share records and message your care team anytime." },
];

const toneClasses = {
  teal: "bg-teal-50 text-teal-600",
  sky: "bg-sky-50 text-sky-500",
  coral: "bg-coral-50 text-coral-500",
};

export default function Landing() {
  return (
    <div>
      {/* Hero */}
      <section className="relative overflow-hidden">
        <div className="mx-auto grid max-w-6xl items-center gap-12 px-6 py-20 md:grid-cols-2 md:py-28">
          <div>
            <Badge tone="teal" className="mb-5">
              <ShieldCheck className="size-3.5" />
              Built for patients and doctors alike
            </Badge>
            <h1 className="text-balance font-display text-4xl font-bold leading-[1.08] text-ink sm:text-5xl">
              Healthcare, actually connected.
            </h1>
            <p className="mt-5 max-w-md text-balance text-lg text-ink-soft">
              MediConnect brings booking, records, and doctor-patient conversations
              into one place.
            </p>
            <div className="mt-8 flex flex-wrap items-center gap-3">
              <Link to="/signup">
                <Button size="lg">
                  Get started <ArrowRight className="size-4.5" />
                </Button>
              </Link>
              <Link to="/patient">
                <Button size="lg" variant="outline">
                  Preview patient portal
                </Button>
              </Link>
            </div>
          </div>

          <ConnectionGraphic />
        </div>
      </section>

      {/* Problem / solution */}
      <section className="border-y border-border bg-surface">
        <div className="mx-auto max-w-6xl px-6 py-16">
          <div className="grid gap-10 md:grid-cols-2">
            <div>
              <h2 className="font-display text-2xl font-bold text-ink sm:text-3xl">
                The gap between visits is where things go wrong
              </h2>
              <p className="mt-3 text-ink-soft">
                Most patient-doctor friction doesn't happen in the room — it happens
                around it.
              </p>
            </div>
            <ul className="space-y-4">
              {pains.map((pain) => (
                <li key={pain} className="flex gap-3 rounded-xl bg-bg p-4">
                  <span className="mt-0.5 grid size-6 shrink-0 place-items-center rounded-full bg-coral-50 text-coral-500">
                    <svg viewBox="0 0 24 24" className="size-3 fill-current">
                      <path d="M10.5 4h3v5h5v3h-5v5h-3v-5h-5V9h5z" />
                    </svg>
                  </span>
                  <span className="text-sm text-ink-soft">{pain}</span>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </section>

      {/* Features */}
      <section id="features" className="mx-auto max-w-6xl px-6 py-20">
        <div className="mb-12 max-w-xl">
          <h2 className="font-display text-2xl font-bold text-ink sm:text-3xl">
            Three problems, one workflow
          </h2>
          <p className="mt-3 text-ink-soft">
            Everything a patient-doctor relationship needs between appointments.
          </p>
        </div>

        <div className="grid gap-6 md:grid-cols-3">
          {features.map(({ icon: Icon, tone, title, description, points }) => (
            <Card key={title} className="flex flex-col gap-4 p-6">
              <div className={`grid size-11 place-items-center rounded-xl ${toneClasses[tone]}`}>
                <Icon className="size-5" strokeWidth={2} />
              </div>
              <h3 className="font-display text-lg font-semibold text-ink">{title}</h3>
              <p className="text-sm text-ink-soft">{description}</p>
              <ul className="mt-auto space-y-2 border-t border-border pt-4">
                {points.map((point) => (
                  <li key={point} className="flex items-center gap-2 text-sm text-ink-soft">
                    <span className="size-1 shrink-0 rounded-full bg-teal-500" />
                    {point}
                  </li>
                ))}
              </ul>
            </Card>
          ))}
        </div>
      </section>

      {/* How it works */}
      <section id="how-it-works" className="border-y border-border bg-surface">
        <div className="mx-auto max-w-6xl px-6 py-20">
          <h2 className="mb-12 font-display text-2xl font-bold text-ink sm:text-3xl">
            Up and running in three steps
          </h2>
          <div className="grid gap-8 sm:grid-cols-3">
            {steps.map((step, i) => (
              <div key={step.title} className="relative">
                <span className="font-mono text-xs font-medium text-teal-600">
                  0{i + 1}
                </span>
                <h3 className="mt-2 font-display font-semibold text-ink">{step.title}</h3>
                <p className="mt-1.5 text-sm text-ink-soft">{step.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Final CTA */}
      <section className="mx-auto max-w-6xl px-6 py-20">
        <div className="flex flex-col items-center gap-6 rounded-3xl bg-ink px-6 py-16 text-center">
          <Clock className="size-8 text-teal-400" />
          <h2 className="max-w-lg text-balance font-display text-2xl font-bold text-white sm:text-3xl">
            Your next appointment is a few clicks away
          </h2>
          <Link to="/signup">
            <Button size="lg">
              Create your account <ArrowRight className="size-4.5" />
            </Button>
          </Link>
        </div>
      </section>
    </div>
  );
}

function ConnectionGraphic() {
  return (
    <div className="relative mx-auto aspect-square w-full max-w-sm">
      <svg viewBox="0 0 320 320" className="h-full w-full">
        <path
          d="M60 90 C 140 40, 180 280, 260 230"
          fill="none"
          stroke="var(--color-border)"
          strokeWidth="2"
          strokeDasharray="6 8"
        />
        <circle cx="60" cy="90" r="4" fill="var(--color-teal-500)">
          <animateMotion
            dur="3.5s"
            repeatCount="indefinite"
            path="M0 0 C 80 -50, 120 190, 200 140"
          />
        </circle>
      </svg>

      <div className="absolute left-0 top-[18%] flex -translate-x-[16%] flex-col items-center gap-2">
        <div className="grid size-16 place-items-center rounded-2xl bg-surface shadow-pop">
          <div className="grid size-10 place-items-center rounded-full bg-teal-100 font-display text-sm font-bold text-teal-700">
            EC
          </div>
        </div>
        <span className="rounded-full bg-surface px-3 py-1 text-xs font-medium text-ink-soft shadow-card">
          Patient
        </span>
      </div>

      <div className="absolute bottom-[12%] right-0 flex translate-x-[16%] flex-col items-center gap-2">
        <div className="grid size-16 place-items-center rounded-2xl bg-surface shadow-pop">
          <div className="grid size-10 place-items-center rounded-full bg-sky-50 font-display text-sm font-bold text-sky-500">
            SJ
          </div>
        </div>
        <span className="rounded-full bg-surface px-3 py-1 text-xs font-medium text-ink-soft shadow-card">
          Doctor
        </span>
      </div>

      <div className="absolute right-4 top-4 flex items-center gap-2 rounded-2xl bg-surface px-4 py-3 shadow-pop">
        <span className="size-2 rounded-full bg-teal-500" />
        <span className="text-xs font-medium text-ink">Next: Today, 2:30 PM</span>
      </div>
    </div>
  );
}
