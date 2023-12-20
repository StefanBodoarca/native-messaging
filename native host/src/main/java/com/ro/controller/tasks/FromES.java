package com.ro.controller.tasks;

import com.ro.model.es.ESModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FromES extends CommonES implements Runnable {
    private static final Logger logger = LogManager.getLogger(FromES.class);
    private ESModel esModel;


    public FromES(ESModel esModel){
        this.esModel = esModel;

    }

    @Override
    public void run(){

    }
}
