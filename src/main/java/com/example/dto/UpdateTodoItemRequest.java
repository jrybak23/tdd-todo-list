package com.example.dto;

import lombok.Getter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

@Getter
public class UpdateTodoItemRequest {
    private final String content;

    @JsonbCreator
    public UpdateTodoItemRequest(@JsonbProperty("content") String content) {
        this.content = content;
    }
}