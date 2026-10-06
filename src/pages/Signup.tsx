import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { User, Mail, Lock, Stethoscope, HeartPulse, ArrowRight } from "lucide-react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import Logo from "@/components/ui/Logo";
import type { UserRole } from "@/types";

export default function Signup() {
  const navigate = useNavigate();
  const [role, setRole] = useState<UserRole>("patient");

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    navigate(`/${role}`);
  }

  return (
    <div className="flex min-h-[calc(100vh-4rem-14rem)] items-center justify-center px-6 py-16">
      <Card className="w-full max-w-md p-8">
        <Link to="/" className="mb-8 flex justify-center">
          <Logo />
        </Link>

        <h1 className="text-center font-display text-2xl font-bold text-ink">Create your account</h1>
        <p className="mt-1.5 text-center text-sm text-ink-soft">
          Tell us who you are so we can set up the right portal.
        </p>

        <div className="mt-6 grid grid-cols-2 gap-3">
          <button
            type="button"
            onClick={() => setRole("patient")}
            className={`flex flex-col items-center gap-2 rounded-xl border-2 px-4 py-4 transition-colors ${
              role === "patient" ? "border-teal-500 bg-teal-50" : "border-border hover:border-teal-300"
            }`}
          >
            <HeartPulse
              className={`size-5 ${role === "patient" ? "text-teal-600" : "text-ink-soft"}`}
            />
            <span
              className={`text-sm font-medium ${role === "patient" ? "text-teal-700" : "text-ink"}`}
            >
              I'm a patient
            </span>
          </button>

          <button
            type="button"
            onClick={() => setRole("doctor")}
            className={`flex flex-col items-center gap-2 rounded-xl border-2 px-4 py-4 transition-colors ${
              role === "doctor" ? "border-teal-500 bg-teal-50" : "border-border hover:border-teal-300"
            }`}
          >
            <Stethoscope
              className={`size-5 ${role === "doctor" ? "text-teal-600" : "text-ink-soft"}`}
            />
            <span
              className={`text-sm font-medium ${role === "doctor" ? "text-teal-700" : "text-ink"}`}
            >
              I'm a doctor
            </span>
          </button>
        </div>

        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
          <label className="block">
            <span className="mb-1.5 block text-sm font-medium text-ink">Full name</span>
            <div className="flex items-center gap-2 rounded-xl border border-border bg-bg px-3.5 py-2.5 focus-within:border-teal-400">
              <User className="size-4 text-ink-soft" />
              <input
                type="text"
                required
                placeholder="Jane Doe"
                className="w-full bg-transparent text-sm text-ink placeholder:text-ink-soft focus:outline-none"
              />
            </div>
          </label>

          <label className="block">
            <span className="mb-1.5 block text-sm font-medium text-ink">Email</span>
            <div className="flex items-center gap-2 rounded-xl border border-border bg-bg px-3.5 py-2.5 focus-within:border-teal-400">
              <Mail className="size-4 text-ink-soft" />
              <input
                type="email"
                required
                placeholder="you@example.com"
                className="w-full bg-transparent text-sm text-ink placeholder:text-ink-soft focus:outline-none"
              />
            </div>
          </label>

          <label className="block">
            <span className="mb-1.5 block text-sm font-medium text-ink">Password</span>
            <div className="flex items-center gap-2 rounded-xl border border-border bg-bg px-3.5 py-2.5 focus-within:border-teal-400">
              <Lock className="size-4 text-ink-soft" />
              <input
                type="password"
                required
                placeholder="••••••••"
                className="w-full bg-transparent text-sm text-ink placeholder:text-ink-soft focus:outline-none"
              />
            </div>
          </label>

          <Button type="submit" className="w-full justify-center">
            Create account <ArrowRight className="size-4" />
          </Button>
        </form>

        <p className="mt-6 text-center text-sm text-ink-soft">
          Already have an account?{" "}
          <Link to="/login" className="font-medium text-teal-600 hover:underline">
            Log in
          </Link>
        </p>
      </Card>
    </div>
  );
}
