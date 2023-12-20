package com.ro.model.es;

import com.ro.prop.AppProp;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ESModelRestCallsTest {

    private String indexName = "test-rest-index";

    private String indexToDelete = "test-rest-index";
    private ESModel esModel;

    @Before
    public void setUp() throws IOException {
        AppProp.loadConfig();
        this.esModel = ESModelRestCalls.getEsModelInstance(AppProp.ELASTIC_SEARCH_URL, AppProp.DOMAIN, AppProp.PORT, AppProp.PROTOCOL, AppProp.ES_USER, AppProp.ES_USER_PASSWORD);
    }

    @Test
    @DisplayName("Should put a new index")
    public void createIndexRest() throws IOException {
        int code = esModel.putIndex(this.indexName);
        System.out.println("Code: " + code);
        assertEquals(200, code);
    }
}
