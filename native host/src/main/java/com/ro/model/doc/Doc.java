package com.ro.model.doc;

import org.json.JSONObject;

import java.util.List;

public class Doc {
    private String docName = "";
    private PersonalInfo personalInfo;
    private List<Skills> skills;
    private List<ExpStd> expStds;

    public Doc(String docName, PersonalInfo personalInfo, List skills, List expStds) {
        this.docName = docName;
        this.personalInfo = personalInfo;
        this.skills = skills;
        this.expStds = expStds;
    }

    public String toJson() {
        JSONObject obj = new JSONObject();
        obj.put("doc-name", this.docName);
        obj.put("personal-info", personalInfo.getJson());
        obj.put("skills", new Skills().listToJson(skills));
        obj.put("experience-studies", new Skills().listToJson((expStds)));
        return obj.toString();
    }
}
