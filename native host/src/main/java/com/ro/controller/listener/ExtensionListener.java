package com.ro.controller.listener;

import com.ro.controller.reader.MessageReader;
import com.ro.prop.AppProp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class ExtensionListener implements Runnable {
    private static final Logger logger = LogManager.getLogger(ExtensionListener.class);
    private BlockingQueue messageQueue;
    private MessageReader reader;

    public ExtensionListener(BlockingQueue messageQueue) {
        this.messageQueue = messageQueue;
        reader = new MessageReader();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //read messages and send for processing
                String message = reader.readMessage();
                if (message != null && message.length() > 0) {
                    this.messageQueue.put(message);
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
