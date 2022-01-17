package com.example.controllers;


import com.example.dto.CreateTodoItemRequest;
import com.example.dto.TodoItemResponse;
import com.example.dto.UpdateTodoItemRequest;
import com.example.services.TodoItemService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/todo-items")
public class TodoResource {
    private final TodoItemService todoItemService;

    public TodoResource(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<TodoItemResponse> getItems() {
        return todoItemService.findAll();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createItem(CreateTodoItemRequest request) {
        todoItemService.createItem(request);
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    public Response updateItem(UpdateTodoItemRequest request, @PathParam("id") UUID id) {
        todoItemService.update(request, id);
        return Response.status(OK).build();
    }
}
