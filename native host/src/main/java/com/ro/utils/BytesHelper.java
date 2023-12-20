package com.ro.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;

public final class BytesHelper {
    private static final Logger logger = LogManager.getLogger(BytesHelper.class);

    private BytesHelper(){}

    public static byte[] intToBytes(int number) {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(number);
        return b.array();
    }
}
