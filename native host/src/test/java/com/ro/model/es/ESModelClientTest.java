package com.ro.model.es;

import com.ro.prop.AppProp;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ESModelClientTest {

    private String indexName = "test-index";
    private String indexToDelete = "test-rest-index";
    private ESModel esModel;

    @Before
    public void setUp() throws IOException {
        AppProp.loadConfig();
        this.esModel = ESModelClient.getESModelClientInstance(AppProp.ELASTIC_SEARCH_URL, AppProp.DOMAIN, AppProp.PORT, AppProp.PROTOCOL, AppProp.ES_USER, AppProp.ES_USER_PASSWORD);
    }

    @Test
    @DisplayName("Should put a new index")
    public void createIndexRestClient() {
        int code = esModel.putIndex(this.indexName);
        System.out.println("Code: " + code);
        assertEquals(200, code);
    }

    @Test
    @DisplayName("Should put a new index")
    public void deleteIndexRestClient() {
        int code = esModel.deleteIndex(this.indexToDelete);
        System.out.println("Code: " + code);
        assertEquals(200, code);
    }
}
