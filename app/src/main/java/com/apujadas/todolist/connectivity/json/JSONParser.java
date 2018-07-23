package com.apujadas.todolist.connectivity.json;

public interface JSONParser {

    String toJson(Object object);

    <T> T fromJson(String json, Class<T> type);

}
