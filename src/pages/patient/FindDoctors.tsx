import { useMemo, useState } from "react";
import { Search } from "lucide-react";
import DoctorCard from "@/components/DoctorCard";
import { doctors, specialties } from "@/data/mockData";

export default function FindDoctors() {
  const [query, setQuery] = useState("");
  const [specialty, setSpecialty] = useState("All");

  const filtered = useMemo(() => {
    return doctors.filter((d) => {
      const matchesSpecialty = specialty === "All" || d.specialty === specialty;
      const matchesQuery =
        query.trim() === "" ||
        d.name.toLowerCase().includes(query.toLowerCase()) ||
        d.specialty.toLowerCase().includes(query.toLowerCase());
      return matchesSpecialty && matchesQuery;
    });
  }, [query, specialty]);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="font-display text-2xl font-bold text-ink">Book an appointment</h1>
        <p className="mt-1 text-sm text-ink-soft">
          Search doctors by specialty, distance, or clinic.
        </p>
      </div>

      <div className="flex items-center gap-2 rounded-2xl border border-border bg-surface px-4 py-3 shadow-card">
        <Search className="size-4.5 text-ink-soft" />
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search doctors, specialties, or clinics"
          className="w-full bg-transparent text-sm text-ink placeholder:text-ink-soft focus:outline-none"
        />
      </div>

      <div className="flex flex-wrap gap-2">
        {specialties.map((s) => (
          <button
            key={s}
            onClick={() => setSpecialty(s)}
            className={`rounded-full border px-4 py-1.5 text-sm font-medium transition-colors ${
              specialty === s
                ? "border-teal-500 bg-teal-500 text-white"
                : "border-border bg-surface text-ink-soft hover:border-teal-300"
            }`}
          >
            {s}
          </button>
        ))}
      </div>

      <div className="flex items-center justify-between">
        <p className="text-sm text-ink-soft">
          {filtered.length} doctor{filtered.length !== 1 && "s"} available
        </p>
      </div>

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-3">
        {filtered.map((doctor) => (
          <DoctorCard key={doctor.id} doctor={doctor} />
        ))}
      </div>

      {filtered.length === 0 && (
        <p className="py-12 text-center text-sm text-ink-soft">
          No doctors match that search. Try a different specialty.
        </p>
      )}
    </div>
  );
}
