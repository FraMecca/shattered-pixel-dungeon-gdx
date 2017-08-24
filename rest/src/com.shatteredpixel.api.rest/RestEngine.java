package com.shatteredpixel.api.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestEngine extends Application<AppConfig> {

//    public static final Logger LOGGER = LoggerFactory.getLogger(RApplication.class);
    @Override
    public void run(final AppConfig configuration, final Environment environment)
            throws Exception {
        final Hero res = new Hero();
        environment.jersey().register(res);

       // LOGGER.info("Application name: {}", configuration.getAppName());
    }
}