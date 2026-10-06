import { useMemo, useState } from "react";
import { Search, FileText, MessageCircle } from "lucide-react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

const patients = [
  { name: "Emily Carter", age: 34, lastVisit: "Today", condition: "Cardiology follow-up", initials: "EC" },
  { name: "Daniel Kim", age: 41, lastVisit: "Today", condition: "Chest pain review", initials: "DK" },
  { name: "Grace Hoffman", age: 29, lastVisit: "Aug 12", condition: "Annual checkup", initials: "GH" },
  { name: "Marcus Bell", age: 52, lastVisit: "Aug 5", condition: "Hypertension management", initials: "MB" },
  { name: "Sofia Alvarez", age: 46, lastVisit: "Jul 30", condition: "Post-op review", initials: "SA" },
];

export default function DoctorPatients() {
  const [query, setQuery] = useState("");

  const filtered = useMemo(
    () => patients.filter((p) => p.name.toLowerCase().includes(query.toLowerCase())),
    [query],
  );

  return (
    <div className="space-y-6">
      <div>
        <h1 className="font-display text-2xl font-bold text-ink">Patients</h1>
        <p className="mt-1 text-sm text-ink-soft">Everyone currently under your care.</p>
      </div>

      <div className="flex items-center gap-2 rounded-2xl border border-border bg-surface px-4 py-3 shadow-card sm:max-w-sm">
        <Search className="size-4.5 text-ink-soft" />
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search patients"
          className="w-full bg-transparent text-sm text-ink placeholder:text-ink-soft focus:outline-none"
        />
      </div>

      <Card className="p-2 sm:p-4">
        {filtered.map((p) => (
          <div
            key={p.name}
            className="flex flex-col gap-3 border-b border-border p-3 last:border-0 sm:flex-row sm:items-center sm:justify-between"
          >
            <div className="flex items-center gap-3">
              <div className="grid size-11 shrink-0 place-items-center rounded-full bg-teal-100 font-display text-xs font-bold text-teal-700">
                {p.initials}
              </div>
              <div>
                <p className="font-medium text-ink">
                  {p.name} <span className="font-normal text-ink-soft">· {p.age}</span>
                </p>
                <p className="text-sm text-ink-soft">{p.condition}</p>
              </div>
            </div>
            <div className="flex items-center gap-2 pl-14 sm:pl-0">
              <span className="text-sm text-ink-soft">Last visit: {p.lastVisit}</span>
              <Button variant="ghost" size="sm">
                <FileText className="size-4" />
              </Button>
              <Button variant="ghost" size="sm">
                <MessageCircle className="size-4" />
              </Button>
            </div>
          </div>
        ))}
      </Card>
    </div>
  );
}
