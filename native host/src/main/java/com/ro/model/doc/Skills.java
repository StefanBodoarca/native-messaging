package com.ro.model.doc;

import org.json.JSONObject;

import java.util.List;

public class Skills {
    protected String title;

    public Skills(){}

    public Skills(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        obj.put("title", this.title);
        return obj;
    }

    protected JSONObject listToJson(List<? extends Skills> o) {
        JSONObject obj = new JSONObject();
        int i = 0;
        for(Skills s : o) {
            obj.put(String.valueOf(i), s.getJson());
            i++;
        }

        return obj;
    }
}
