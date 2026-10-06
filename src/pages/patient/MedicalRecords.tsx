import { ShieldCheck, Upload } from "lucide-react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import RecordRow from "@/components/RecordRow";
import { medicalRecords } from "@/data/mockData";

export default function MedicalRecords() {
  return (
    <div className="space-y-6">
      <div className="flex flex-wrap items-center justify-between gap-4">
        <div>
          <h1 className="font-display text-2xl font-bold text-ink">Medical records</h1>
          <p className="mt-1 text-sm text-ink-soft">
            Prescriptions, lab reports, and scans in chronological order.
          </p>
        </div>
        <Button size="sm">
          <Upload className="size-4" /> Upload record
        </Button>
      </div>

      <div className="flex items-start gap-3 rounded-xl border border-teal-100 bg-teal-50 p-4">
        <ShieldCheck className="mt-0.5 size-4.5 shrink-0 text-teal-600" />
        <p className="text-sm text-teal-700">
          Every view is logged in an audit trail — you can always see who accessed a record and when.
        </p>
      </div>

      <Card className="p-6">
        {medicalRecords.map((record) => (
          <RecordRow key={record.id} record={record} />
        ))}
      </Card>
    </div>
  );
}
