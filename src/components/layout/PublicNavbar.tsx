import { Link, NavLink } from "react-router-dom";
import Logo from "@/components/ui/Logo";
import Button from "@/components/ui/Button";

const links = [
  { to: "/#features", label: "Features" },
  { to: "/#how-it-works", label: "How it works" },
];

export default function PublicNavbar() {
  return (
    <header className="sticky top-0 z-40 border-b border-border bg-surface/80 backdrop-blur-md">
      <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-6">
        <Link to="/">
          <Logo />
        </Link>
        <nav className="hidden items-center gap-8 md:flex">
          {links.map((link) => (
            <a
              key={link.label}
              href={link.to}
              className="text-sm font-medium text-ink-soft transition-colors hover:text-ink"
            >
              {link.label}
            </a>
          ))}
        </nav>
        <div className="flex items-center gap-3">
          <NavLink to="/login">
            <Button variant="ghost" size="sm">
              Log in
            </Button>
          </NavLink>
          <NavLink to="/signup">
            <Button variant="primary" size="sm">
              Get started
            </Button>
          </NavLink>
        </div>
      </div>
    </header>
  );
}
