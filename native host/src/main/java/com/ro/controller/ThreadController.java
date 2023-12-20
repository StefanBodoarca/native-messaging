package com.ro.controller;

import com.ro.controller.listener.ExtensionListener;
import com.ro.controller.listener.QueueReader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadController {
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private ExtensionListener extensionListener;
    private QueueReader queueReader;

    public ThreadController() {
        this.extensionListener = new ExtensionListener(this.messageQueue);
        this.queueReader = new QueueReader(this.messageQueue);
    }

    public void startThreads() {
        new Thread(this.extensionListener).start();
        new Thread(this.queueReader).start();
    }
}
