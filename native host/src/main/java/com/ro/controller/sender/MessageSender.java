package com.ro.controller.sender;

import com.ro.controller.reader.MessageReader;
import com.ro.utils.BytesHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MessageSender {
    private static final Logger logger = LogManager.getLogger(MessageSender.class);

    public void sendMessage(String jsonString) {
        try {
            System.out.write(BytesHelper.intToBytes(jsonString.length()));
            System.out.write(jsonString.getBytes(StandardCharsets.UTF_8));
            System.out.flush();
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        }
    }
}
