package com.example.services;

import com.example.dto.CreateTodoItemRequest;
import com.example.dto.TodoItemResponse;
import com.example.dto.UpdateTodoItemRequest;
import com.example.entities.TodoItem;
import com.example.repositorty.TodoItemRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final Clock clock;

    @Inject
    public TodoItemService(TodoItemRepository todoItemRepository,
                           Clock clock) {
        this.todoItemRepository = todoItemRepository;
        this.clock = clock;
    }

    public List<TodoItemResponse> findAll() {
        return todoItemRepository.findAll().stream()
                .map(TodoItemResponse::new)
                .collect(toList());
    }

    public void createItem(CreateTodoItemRequest createTodoItemRequest) {
        TodoItem todoItem = convertToEntity(createTodoItemRequest);
        todoItemRepository.save(todoItem);
    }

    private TodoItem convertToEntity(CreateTodoItemRequest createTodoItemRequest) {
        TodoItem todoItem = new TodoItem();
        todoItem.setContent(createTodoItemRequest.getContent());
        todoItem.setDateTimeCreated(LocalDateTime.now(clock));
        return todoItem;
    }

    @Transactional
    public void update(UpdateTodoItemRequest request, UUID id) {
        TodoItem item = todoItemRepository.findById(id).orElseThrow();
        item.setContent(request.getContent());
    }
}
