package com.ro;

import com.ro.controller.ThreadController;
import com.ro.model.Constants;
import com.ro.model.es.ESModel;
import com.ro.model.es.ESModelClient;
import com.ro.model.es.ESModelRestCalls;
import com.ro.prop.AppProp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            AppProp.loadConfig();
            new ThreadController().startThreads();
            if(AppProp.CHROME_DEBUG_ENABLED) {
                System.err.println("App Started");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
