package api.rest;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestEngine extends Application<api.rest.AppConfig> implements Runnable{

    final public HeroApi heroApi = new HeroApi();
    final public ItemApi itemApi = new ItemApi();
    // do not forget to add the to jersey().enironment

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
        environment.jersey().register(this.heroApi);
        environment.jersey().register(this.itemApi);

       // LOGGER.info("Application name: {}", configuration.getAppName());
    }
}