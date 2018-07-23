package com.apujadas.todolist.bean;

import com.google.gson.annotations.SerializedName;

public class HALResponse {

    @SerializedName("_embedded")
    private HALListContainer embedded;
    private HALPage page;

    public HALListContainer getEmbedded() {
        return embedded;
    }

    public void setEmbedded(HALListContainer embedded) {
        this.embedded = embedded;
    }

    public HALPage getPage() {
        return page;
    }

    public void setPage(HALPage page) {
        this.page = page;
    }
}
