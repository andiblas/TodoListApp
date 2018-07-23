package com.apujadas.todolist.bean;

import com.apujadas.todolist.domain.ToDo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HALListContainer {

    @SerializedName("todos")
    private List<ToDo> list;

    public List<ToDo> getList() {
        return list;
    }

    public void setList(List<ToDo> list) {
        this.list = list;
    }
}
