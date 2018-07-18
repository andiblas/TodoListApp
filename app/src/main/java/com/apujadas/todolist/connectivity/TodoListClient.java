package com.apujadas.todolist.connectivity;

import java.io.IOException;

public interface TodoListClient {
    void enableServer() throws IOException;
    void disableServer() throws IOException;

    void addTodo();
    void deleteTodo();

    String getAllTodos() throws IOException;
}
