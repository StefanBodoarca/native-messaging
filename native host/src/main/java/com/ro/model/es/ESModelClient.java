//implemented as singleton

package com.ro.model.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

//todo change response codes from 200 in whats predefined in the client and proceed accordingly

public class ESModelClient extends ESModel {
    private static final Logger logger = LogManager.getLogger(ESModelClient.class);
    private static ESModelClient esModelClientInstance = null;
    private RestHighLevelClient restHighLevelClient;

    public static ESModelClient getESModelClientInstance(String server_url, String domain, int port, String protocol, String username, String password) {
        if(esModelClientInstance == null) {
            return new ESModelClient(server_url, domain, port, protocol, username, password);
        }

        return esModelClientInstance;
    }

    private ESModelClient(String server_url, String domain, int port, String protocol, String username, String password){
        super(server_url, domain, port, protocol, username, password);
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(this.ES_USER, this.ES_USER_PASSWORD));

        this.restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.DOMAIN, this.PORT, this.PROTOCOL)
                ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(
                            HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
        );
    }

    @Override
    public int putIndex(String indexName) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            CreateIndexResponse createIndexResponse = this.restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

            if(acknowledged) {
                return 200;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return -1;
    }

    @Override
    public int postDoc(String indexName, String jsonDoc, String docID) {
        try {
            IndexRequest request = new IndexRequest(indexName);
            request.id(docID);
            request.source(jsonDoc, XContentType.JSON);
            IndexResponse indexResponse = this.restHighLevelClient.index(request, RequestOptions.DEFAULT);

            String index = indexResponse.getIndex();
            String id = indexResponse.getId();
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                return 200;
            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                return 200;
            }
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                return -1;
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure :
                        shardInfo.getFailures()) {
                    String reason = failure.reason();
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return -1;
    }

    @Override
    public int deleteIndex(String indexName) {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            AcknowledgedResponse deleteIndexResponse = this.restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            boolean acknowledged = deleteIndexResponse.isAcknowledged();
            if(acknowledged) {
                return 200;
            }

        } catch (ElasticsearchException es_exception) {
            if(es_exception.status() == RestStatus.NOT_FOUND) {
                logger.error("index to delete NOT_FOUND");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return -1;
    }
}
