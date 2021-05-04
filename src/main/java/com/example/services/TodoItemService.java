package com.example.services;

import com.example.dto.TodoItemRequest;
import com.example.dto.TodoItemResponse;
import com.example.entities.TodoItem;
import com.example.repositorty.TodoItemRepository;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;

    public TodoItemService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public List<TodoItemResponse> findAll() {
        return todoItemRepository.findAll().stream()
                .map(TodoItemResponse::new)
                .collect(toList());
    }

    public void createItem(TodoItemRequest todoItemRequest) {
        TodoItem todoItem = convertToEntity(todoItemRequest);
        todoItemRepository.save(todoItem);
    }

    private TodoItem convertToEntity(TodoItemRequest todoItemRequest) {
        TodoItem todoItem = new TodoItem();
        todoItem.setContent(todoItemRequest.getContent());
        todoItem.setDateTimeCreated(LocalDateTime.now());
        return todoItem;
    }
}
