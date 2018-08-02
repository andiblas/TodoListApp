package com.apujadas.todolist.connectivity.json;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class GSONJsonParser implements JSONParser {

    @Override
    public String toJson(Object object) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.toJson(object);
    }

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(json, type);
    }
}
