package api.rest;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hero")
@Produces(MediaType.APPLICATION_JSON)
public class HeroApi extends APIAbstract {

    @GET
    @Path("/str")
    @Timed
    public GeneralResponse str() {
        return new GeneralResponse("Hero", "str", 0);
    }

    @GET
    @Path("/str/increase")
    @Timed
    public GeneralResponse strIncrease() {
        return new GeneralResponse("Hero", "str", 10);
    }

    @GET
    @Path("/str/decrease")
    @Timed
    public GeneralResponse strDecrease() {
        return new GeneralResponse("Hero", "str", -10);
    }

    @GET
    @Path("/signal")
    @Timed
    public GeneralResponse signal() {
        this.dispatch("str");
        return new GeneralResponse("Hero", "signal", 0);
    }
}
