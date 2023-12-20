//implemented as singleton

package com.ro.model.es;
import com.ro.prop.AppProp;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ESModelRestCalls extends ESModel {
    private static final Logger logger = LogManager.getLogger(ESModelRestCalls.class);

    private static ESModelRestCalls esModelRestCallsInstance = null;

    public static ESModelRestCalls getEsModelInstance(String server_url, String domain, int port, String protocol, String username, String password) {
        if(esModelRestCallsInstance == null) {
            return new ESModelRestCalls(server_url, domain, port, protocol, username, password);
        }

        return esModelRestCallsInstance;
    }

    private ESModelRestCalls(String server_url, String domain, int port, String protocol, String username, String password){
        super(server_url, domain, port, protocol, username, password);
    }

    @Override
    public int putIndex(String indexName) {
        OkHttpClient client;
        Response response = null;
        try {
            client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(AppProp.ELASTIC_SEARCH_URL + "/" + indexName)
                    .method("PUT", RequestBody.create(null, new byte[0]))
                    .header("Authorization", Credentials.basic(this.ES_USER, this.ES_USER_PASSWORD))
                    .build();
            response = client.newCall(request).execute();

        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            response.body().close();
            logger.info(response.message() + " " + response.code());
            return response.code();
        }
    }

    @Override
    public int postDoc(String indexName, String jsonDoc, String docID) {
        return -1;
    }

    @Override
    public int deleteIndex(String indexName) {
        return -1;
    }

}
