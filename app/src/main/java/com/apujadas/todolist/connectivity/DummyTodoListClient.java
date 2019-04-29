package com.apujadas.todolist.connectivity;

import com.apujadas.todolist.domain.ToDo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DummyTodoListClient implements TodoListClient {
    @Override
    public void enableServer() throws IOException, ServerException {
        System.out.println("Starting enableServer()...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Finished enableServer().");
    }

    @Override
    public void disableServer() throws IOException, ServerException {
        System.out.println("Starting disableServer()...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Finished disableServer().");
    }

    @Override
    public void addTodo(ToDo todo) throws IOException, ServerException {
        System.out.println("Starting addTodo()...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Finished addTodo().");
    }

    @Override
    public void deleteTodo(ToDo todo) {
        System.out.println("Starting deleteTodo()...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Finished deleteTodo().");
    }

    @Override
    public List<ToDo> getAllTodos() throws IOException, ServerException {
        System.out.println("Starting getAllTodos()...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Finished getAllTodos().");

        List<ToDo> result = new ArrayList<>();
        result.add(new ToDo());
        return result;
    }
}
