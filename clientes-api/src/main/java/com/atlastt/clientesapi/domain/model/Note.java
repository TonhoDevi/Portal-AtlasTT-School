package com.atlastt.clientesapi.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Note {

    private final UUID id;
    private final UUID clientId;
    private String text;
    private NoteSubject subject;
    private final String author;
    private final LocalDateTime createdAt;

    private Note(UUID id, UUID clientId, String text, NoteSubject subject,
                 String author, LocalDateTime createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.text = text;
        this.subject = subject;
        this.author = author;
        this.createdAt = createdAt;
    }

    public static Note create(UUID clientId, String text, NoteSubject subject, String author) {
        Objects.requireNonNull(clientId, "clientId must not be null");
        Objects.requireNonNull(subject, "subject must not be null");
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Note text must not be blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Note author must not be blank");
        }
        return new Note(UUID.randomUUID(), clientId, text, subject, author, LocalDateTime.now());
    }

    public static Note restore(UUID id, UUID clientId, String text, NoteSubject subject,
                               String author, LocalDateTime createdAt) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(clientId, "clientId must not be null");
        Objects.requireNonNull(subject, "subject must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        return new Note(id, clientId, text, subject, author, createdAt);
    }

    public void updateText(String newText) {
        if (newText == null || newText.isBlank()) {
            throw new IllegalArgumentException("Note text must not be blank");
        }
        this.text = newText;
    }

    public void updateSubject(NoteSubject newSubject) {
        Objects.requireNonNull(newSubject, "subject must not be null");
        this.subject = newSubject;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getText() {
        return text;
    }

    public NoteSubject getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note note)) return false;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}