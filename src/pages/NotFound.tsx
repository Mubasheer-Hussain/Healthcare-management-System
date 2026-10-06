import { Link } from "react-router-dom";
import { Compass } from "lucide-react";
import Button from "@/components/ui/Button";

export default function NotFound() {
  return (
    <div className="flex min-h-[calc(100vh-4rem-14rem)] flex-col items-center justify-center px-6 text-center">
      <Compass className="size-10 text-teal-500" />
      <h1 className="mt-4 font-display text-3xl font-bold text-ink">Page not found</h1>
      <p className="mt-2 max-w-sm text-ink-soft">
        The page you're looking for doesn't exist yet — or the route moved.
      </p>
      <Link to="/" className="mt-6">
        <Button>Back to home</Button>
      </Link>
    </div>
  );
}
