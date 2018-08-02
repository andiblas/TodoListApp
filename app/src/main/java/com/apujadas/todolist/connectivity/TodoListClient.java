package com.apujadas.todolist.connectivity;

import com.apujadas.todolist.domain.ToDo;

import java.io.IOException;
import java.util.List;

public interface TodoListClient {
    void enableServer() throws IOException, ServerException;

    void disableServer() throws IOException, ServerException;

    void addTodo(ToDo todo) throws IOException, ServerException;

    void deleteTodo(ToDo todo);

    List<ToDo> getAllTodos() throws IOException, ServerException;
}
