package com.ro.controller.reader;

import com.ro.prop.AppProp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class MessageReader {
    private static final Logger logger = LogManager.getLogger(MessageReader.class);
    BufferedInputStream inputStream = new BufferedInputStream(System.in);
    byte[] len = new byte[4];

    public String readMessage() {
        try {

            if (inputStream.read(len) == 4) {
                int length = ByteBuffer.wrap(len).order(ByteOrder.LITTLE_ENDIAN).getInt();
                logger.info("Received length: " + length);

                if (length == 0) {
                    logger.info("Message length is 0. Systems exit");
                    System.exit(0);
                } else {
                    byte[] message = new byte[length];
                    if (inputStream.read(message) > 0) {
                        String messageOutput = new String(message, StandardCharsets.UTF_8);
                        return messageOutput;
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "";
    }
}
