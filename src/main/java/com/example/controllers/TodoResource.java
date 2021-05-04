package com.example.controllers;


import com.example.dto.TodoItemRequest;
import com.example.dto.TodoItemResponse;
import com.example.services.TodoItemService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/todo-items")
public class TodoResource {
    private final TodoItemService todoItemService;

    public TodoResource(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TodoItemResponse> getItems() {
        return todoItemService.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(TodoItemRequest todoItemRequest) {
        todoItemService.createItem(todoItemRequest);
        return Response.status(CREATED).build();
    }
}
