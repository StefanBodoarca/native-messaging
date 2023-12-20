package com.ro.model.doc;

import org.json.JSONObject;

public class PersonalInfo extends ExpStd {
    private String contactsNumber;

    public PersonalInfo(String contactsNumber, String title, String description, String period, String location) {
        super(title, description, period, location);
        this.contactsNumber = contactsNumber;
    }

    public String getContactsNumber() {
        return contactsNumber;
    }

    public void setContactsNumber(String contactsNumber) {
        this.contactsNumber = contactsNumber;
    }

    @Override
    public JSONObject getJson() {
        JSONObject obj = super.getJson();
        obj.put("contactsNumber", this.contactsNumber);

        return obj;
    }
}
