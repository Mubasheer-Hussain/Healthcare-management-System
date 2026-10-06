import { FileText, FlaskConical, ScanLine, Eye } from "lucide-react";
import type { MedicalRecord } from "@/types";

const typeMeta = {
  prescription: { icon: FileText, tone: "bg-teal-50 text-teal-600", label: "Prescription" },
  lab: { icon: FlaskConical, tone: "bg-sky-50 text-sky-500", label: "Lab report" },
  scan: { icon: ScanLine, tone: "bg-coral-50 text-coral-500", label: "Scan" },
};

export default function RecordRow({ record }: { record: MedicalRecord }) {
  const meta = typeMeta[record.type];
  const Icon = meta.icon;

  return (
    <div className="flex items-center justify-between gap-3 border-b border-border py-4 last:border-0">
      <div className="flex items-center gap-3">
        <div className={`grid size-11 shrink-0 place-items-center rounded-xl ${meta.tone}`}>
          <Icon className="size-5" strokeWidth={2} />
        </div>
        <div>
          <p className="font-medium text-ink">{record.title}</p>
          <p className="text-sm text-ink-soft">
            {meta.label} · Uploaded by {record.uploadedBy}
          </p>
        </div>
      </div>

      <div className="flex items-center gap-4">
        <div className="hidden text-right sm:block">
          <p className="text-sm text-ink">{record.date}</p>
          <p className="font-mono text-xs text-ink-soft">{record.timestamp}</p>
        </div>
        <button
          className="grid size-9 place-items-center rounded-lg text-ink-soft hover:bg-surface-muted hover:text-teal-600"
          aria-label={`View ${record.title}`}
        >
          <Eye className="size-4.5" />
        </button>
      </div>
    </div>
  );
}
