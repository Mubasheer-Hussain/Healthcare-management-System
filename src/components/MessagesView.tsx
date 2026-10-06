import { useState } from "react";
import { Send, Stethoscope } from "lucide-react";
import Card from "@/components/ui/Card";
import { conversations, chatThread } from "@/data/mockData";
import type { ChatMessage } from "@/types";

export default function MessagesView() {
  const [activeId, setActiveId] = useState(conversations[0].id);
  const [thread, setThread] = useState<ChatMessage[]>(chatThread);
  const [draft, setDraft] = useState("");

  const active = conversations.find((c) => c.id === activeId) ?? conversations[0];

  function handleSend() {
    if (!draft.trim()) return;
    setThread((prev) => [
      ...prev,
      { id: `local-${prev.length}`, from: "me", text: draft.trim(), time: "Now" },
    ]);
    setDraft("");
  }

  return (
    <Card className="grid h-[calc(100vh-9rem)] grid-cols-1 overflow-hidden md:grid-cols-[18rem_1fr]">
      {/* Conversation list */}
      <div className="hidden flex-col border-r border-border md:flex">
        <div className="border-b border-border p-4">
          <h2 className="font-display font-semibold text-ink">Messages</h2>
        </div>
        <div className="flex-1 overflow-y-auto">
          {conversations.map((c) => (
            <button
              key={c.id}
              onClick={() => setActiveId(c.id)}
              className={`flex w-full items-start gap-3 border-b border-border px-4 py-3.5 text-left transition-colors ${
                c.id === activeId ? "bg-teal-50" : "hover:bg-surface-muted"
              }`}
            >
              <div className="grid size-10 shrink-0 place-items-center rounded-full bg-teal-100 font-display text-xs font-bold text-teal-700">
                {c.withName
                  .split(" ")
                  .map((w) => w[0])
                  .slice(-2)
                  .join("")}
              </div>
              <div className="min-w-0 flex-1">
                <div className="flex items-center justify-between gap-2">
                  <p className="truncate text-sm font-medium text-ink">{c.withName}</p>
                  <span className="shrink-0 text-xs text-ink-soft">{c.timestamp}</span>
                </div>
                <p className="truncate text-xs text-ink-soft">{c.lastMessage}</p>
              </div>
              {c.unread > 0 && (
                <span className="grid size-5 shrink-0 place-items-center rounded-full bg-coral-500 text-[10px] font-semibold text-white">
                  {c.unread}
                </span>
              )}
            </button>
          ))}
        </div>
      </div>

      {/* Active thread */}
      <div className="flex min-w-0 flex-col">
        <div className="flex items-center gap-3 border-b border-border px-5 py-4">
          <div className="grid size-9 place-items-center rounded-full bg-teal-100 text-teal-700">
            <Stethoscope className="size-4.5" />
          </div>
          <div>
            <p className="text-sm font-medium text-ink">{active.withName}</p>
            <p className="text-xs text-ink-soft">{active.withRole}</p>
          </div>
        </div>

        <div className="flex-1 space-y-3 overflow-y-auto px-5 py-5">
          {thread.map((m) => (
            <div key={m.id} className={`flex ${m.from === "me" ? "justify-end" : "justify-start"}`}>
              <div
                className={`max-w-[75%] rounded-2xl px-4 py-2.5 text-sm ${
                  m.from === "me"
                    ? "rounded-br-sm bg-teal-500 text-white"
                    : "rounded-bl-sm bg-surface-muted text-ink"
                }`}
              >
                <p>{m.text}</p>
                <p
                  className={`mt-1 text-[11px] ${m.from === "me" ? "text-teal-50/80" : "text-ink-soft"}`}
                >
                  {m.time}
                </p>
              </div>
            </div>
          ))}
        </div>

        <div className="flex items-center gap-2 border-t border-border p-4">
          <input
            value={draft}
            onChange={(e) => setDraft(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSend()}
            placeholder="Write a message…"
            className="flex-1 rounded-xl border border-border bg-bg px-4 py-2.5 text-sm text-ink placeholder:text-ink-soft focus:border-teal-400 focus:outline-none"
          />
          <button
            onClick={handleSend}
            className="grid size-10 shrink-0 place-items-center rounded-xl bg-teal-500 text-white hover:bg-teal-600"
            aria-label="Send message"
          >
            <Send className="size-4.5" />
          </button>
        </div>
      </div>
    </Card>
  );
}
