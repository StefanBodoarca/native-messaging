package com.ro.model.es;

public abstract class ESModel {
    protected String SERVER_URL = "";
    protected String DOMAIN = "";
    protected int PORT = -1;
    protected String PROTOCOL = "";
    protected String ES_USER;
    protected String ES_USER_PASSWORD;

    public ESModel(String server_url, String domain, int port, String protocol, String username, String password) {
        this.SERVER_URL = server_url;
        this.DOMAIN = domain;
        this.PORT = port;
        this.PROTOCOL = protocol;
        this.ES_USER = username;
        this.ES_USER_PASSWORD = password;
    }
    abstract public int putIndex(String indexName);
    abstract public int postDoc(String indexName, String jsonDoc, String docID);
    abstract public int deleteIndex(String indexName);
}
