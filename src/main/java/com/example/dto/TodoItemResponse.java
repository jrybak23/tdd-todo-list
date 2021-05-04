package com.example.dto;

import com.example.entities.TodoItem;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TodoItemResponse {
    private final UUID id;
    private final String content;
    private final LocalDateTime dateTimeCreated;

    public TodoItemResponse(TodoItem todoItem) {
        this.id = todoItem.getId();
        this.content = todoItem.getContent();
        this.dateTimeCreated = todoItem.getDateTimeCreated();
    }
}
