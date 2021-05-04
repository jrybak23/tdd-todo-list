package com.example.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "datetime_created")
    private LocalDateTime dateTimeCreated;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
}
