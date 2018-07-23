package com.apujadas.todolist.connectivity;

import com.apujadas.todolist.domain.ToDo;

import java.io.IOException;
import java.util.List;

public interface TodoListClient {
    void enableServer() throws IOException;

    void disableServer() throws IOException;

    void addTodo();

    void deleteTodo();

    List<ToDo> getAllTodos() throws IOException;
}
