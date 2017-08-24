package api.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestEngine extends Application<AppConfig> implements Runnable{

//    public static final Logger LOGGER = LoggerFactory.getLogger(RApplication.class);
    public static void main(String[] args) throws Exception {
        new RestEngine().run("server");
    }

    @Override public void run() {
        try {
            this.run("server");
        } catch (Exception e){
           e.printStackTrace();
        }
    }
    @Override
    public void run(final AppConfig configuration, final Environment environment)
            throws Exception {
        final HeroApi res = new HeroApi();
        environment.jersey().register(res);

       // LOGGER.info("Application name: {}", configuration.getAppName());
    }
}