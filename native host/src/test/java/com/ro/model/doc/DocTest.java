package com.ro.model.doc;

import com.ro.model.es.ESModel;
import com.ro.model.es.ESModelClient;
import com.ro.prop.AppProp;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DocTest {
    private Doc document;
    private String docName = "test-doc";
    private PersonalInfo personalInfo = new PersonalInfo("100", "software-engineer", "hard worker, clean coder", "2016-2018", "Bucharest");
    private List<Skills> skills = new ArrayList<>();
    private List<ExpStd> expStds = new ArrayList<>();
    private String indexName = "index-client";

    @Before
    public void setUp() {
        skills.add(new Skills("java"));
        skills.add(new Skills("javascript"));
        expStds.add(new ExpStd("ITA", "software-engineer", "2016-2018", "Bucharest"));
        expStds.add(new ExpStd("UPB", "computer science student", "2012-2016", "Bucharest, Romania"));
        document = new Doc(this.docName, this.personalInfo, this.skills, this.expStds);
    }

    @Test
    @DisplayName("Should display Json resulted string from document")
    public void testDocToSJsonString() {
        System.out.println(this.document.toJson());
    }

    @Test
    @DisplayName("Should insert doc to es")
    public void insertDocToEsTestThroughHighLevelClient() throws IOException {
        AppProp.loadConfig();
        ESModel model = ESModelClient.getESModelClientInstance(AppProp.ELASTIC_SEARCH_URL, AppProp.DOMAIN, AppProp.PORT, AppProp.PROTOCOL, AppProp.ES_USER, AppProp.ES_USER_PASSWORD);
        assertEquals(200, model.postDoc(this.indexName, this.document.toJson(), this.docName));
    }

}
