package com.ro.utils;

import org.json.JSONObject;

public final class JsonHelper {
    private JsonHelper(){}

    public static void parseStrToJson(String str) {
        JSONObject obj = new JSONObject(str);
    }
}
