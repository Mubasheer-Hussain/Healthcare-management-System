import MessagesView from "@/components/MessagesView";

export default function DoctorMessages() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="font-display text-2xl font-bold text-ink">Messages</h1>
        <p className="mt-1 text-sm text-ink-soft">
          Answer patient questions and send follow-ups between visits.
        </p>
      </div>
      <MessagesView />
    </div>
  );
}
