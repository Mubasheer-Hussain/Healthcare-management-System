import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Mail, Lock, ArrowRight } from "lucide-react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import Logo from "@/components/ui/Logo";
import type { UserRole } from "@/types";

export default function Login() {
  const navigate = useNavigate();
  const [role, setRole] = useState<UserRole>("patient");

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    // No backend wired up yet — route straight to the matching portal.
    navigate(`/${role}`);
  }

  return (
    <div className="flex min-h-[calc(100vh-4rem-14rem)] items-center justify-center px-6 py-16">
      <Card className="w-full max-w-md p-8">
        <Link to="/" className="mb-8 flex justify-center">
          <Logo />
        </Link>

        <h1 className="text-center font-display text-2xl font-bold text-ink">Welcome back</h1>
        <p className="mt-1.5 text-center text-sm text-ink-soft">
          Log in to manage your appointments and records.
        </p>

        <div className="mt-6 grid grid-cols-2 gap-2 rounded-xl bg-surface-muted p-1">
          {(["patient", "doctor"] as const).map((r) => (
            <button
              key={r}
              type="button"
              onClick={() => setRole(r)}
              className={`rounded-lg py-2 text-sm font-medium capitalize transition-colors ${
                role === r ? "bg-surface text-ink shadow-card" : "text-ink-soft"
              }`}
            >
              {r}
            </button>
          ))}
        </div>

        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
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
            Log in as {role} <ArrowRight className="size-4" />
          </Button>
        </form>

        <p className="mt-6 text-center text-sm text-ink-soft">
          New here?{" "}
          <Link to="/signup" className="font-medium text-teal-600 hover:underline">
            Create an account
          </Link>
        </p>
      </Card>
    </div>
  );
}
