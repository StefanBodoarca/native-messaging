package com.ro.controller.tasks;

import org.json.JSONObject;

public class CommonES {
    protected JSONObject jsonDocument;

    protected CommonES(){
        this.jsonDocument = null;
    }

    public JSONObject getJsonDocument() {
        return jsonDocument;
    }

    public void setJsonDocument(JSONObject jsonDocument) {
        this.jsonDocument = jsonDocument;
    }
}
