package com.apujadas.todolist.connectivity;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AWSTodoListClient implements TodoListClient {

    private static final String BASE_URL = "http://192.168.1.7:8080/";

    private OkHttpClient client = new OkHttpClient();

    @Override
    public String getAllTodos() throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment("todos")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response res = client.newCall(request).execute();
        return res.body().string();
    }

    @Override
    public void enableServer() throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment("state")
                .addPathSegment("on")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .put(null)
                .build();

        Response res = client.newCall(request).execute();
    }

    @Override
    public void disableServer() throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment("state")
                .addPathSegment("on")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .put(null)
                .build();

        Response res = client.newCall(request).execute();
    }

    @Override
    public void addTodo() {

    }

    @Override
    public void deleteTodo() {

    }
}
