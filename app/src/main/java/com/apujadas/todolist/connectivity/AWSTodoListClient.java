package com.apujadas.todolist.connectivity;

import com.apujadas.todolist.bean.HALResponse;
import com.apujadas.todolist.connectivity.json.JSONParser;
import com.apujadas.todolist.domain.ToDo;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AWSTodoListClient implements TodoListClient {

    private static final String BASE_URL = "http://todolist-env.v2dq27mnqc.us-east-2.elasticbeanstalk.com/";

    private JSONParser jsonParser;
    private OkHttpClient client = new OkHttpClient();

    public AWSTodoListClient(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    @Override
    public List<ToDo> getAllTodos() throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment("todos")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response res = client.newCall(request).execute();
        HALResponse parsedRes = jsonParser.fromJson(res.body().string(), HALResponse.class);
        return parsedRes.getEmbedded().getList();
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
