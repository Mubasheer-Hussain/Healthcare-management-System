import MessagesView from "@/components/MessagesView";

export default function Messages() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="font-display text-2xl font-bold text-ink">Messages</h1>
        <p className="mt-1 text-sm text-ink-soft">
          Ask a question, get a follow-up, or check in with your care team.
        </p>
      </div>
      <MessagesView />
    </div>
  );
}
