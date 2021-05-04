package com.example.repositorty;

import com.example.entities.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoItemRepository extends JpaRepository<TodoItem, UUID> {
}
