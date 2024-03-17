package ch.zhaw.mathify;

import ch.zhaw.mathify.controller.Backbone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOG.info("Starting app...");

        Backbone backbone = new Backbone();
        backbone.startApplication();
    }
}
