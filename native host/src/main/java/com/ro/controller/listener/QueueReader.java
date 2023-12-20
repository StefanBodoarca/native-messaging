package com.ro.controller.listener;

import com.ro.controller.tasks.FromES;
import com.ro.controller.tasks.ToES;
import com.ro.model.es.ESModel;
import com.ro.model.es.ESModelClient;
import com.ro.prop.AppProp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.concurrent.BlockingQueue;

public class QueueReader implements Runnable {
    private static final Logger logger = LogManager.getLogger(QueueReader.class);
    private BlockingQueue messageQueue;
    private ToES toESInstance;
    private FromES fromESInstance;
    private ESModel esModel;

    public QueueReader(BlockingQueue messageQueue) {
        this.messageQueue = messageQueue;
        this.esModel = ESModelClient.getESModelClientInstance(AppProp.ELASTIC_SEARCH_URL, AppProp.DOMAIN, AppProp.PORT, AppProp.PROTOCOL, AppProp.ES_USER, AppProp.ES_USER_PASSWORD);
        this.toESInstance = new ToES(this.esModel);
        this.fromESInstance = new FromES(this.esModel);
    }

    @Override
    public void run() {
        while (true) {
            //take message and process
            try {
                String jsonMessage = this.messageQueue.take().toString();
                if(AppProp.CHROME_DEBUG_ENABLED) {
                    System.err.println("Message from queue: "  + jsonMessage);
                }

                JSONObject obj = new JSONObject(jsonMessage);
                if(obj.getString("action").equals("TO_ES")) {
                    this.toESInstance.setJsonDocument(obj.getJSONObject("document"));
                    new Thread(this.toESInstance).start();
                } else if(obj.getString("action").equals("FROM_ES")) {
                    this.fromESInstance.setJsonDocument(obj.getJSONObject("query"));
                    new Thread(this.fromESInstance).start();
                }

                Thread.sleep(100);
            } catch (InterruptedException iex) {
                logger.error(iex.getMessage(), iex);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
