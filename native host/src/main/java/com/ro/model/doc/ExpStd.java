package com.ro.model.doc;

import org.json.JSONObject;

public class ExpStd extends Skills {
    protected String description;
    private String period;
    protected String location;

    public ExpStd(String title, String description, String period, String location) {
        super(title);
        this.description = description;
        this.period = period;
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public JSONObject getJson() {
        JSONObject obj = super.getJson();
        obj.put("description", this.description);
        obj.put("period", this.period);
        obj.put("location", this.location);

        return obj;
    }
}
