package api.rest;

import com.codahale.metrics.annotation.Timed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero.ACTION;

import java.util.Optional;

@Path("/hero")
@Produces(MediaType.APPLICATION_JSON)
public class HeroApi extends APIAbstract {

    @GET
    @Path("/str")
    @Timed
    public GeneralResponse str() {
        Integer i = (Integer) this.returningDispatch(Hero.ACTION.GETSTR);
        return new GeneralResponse("Hero", "str", i);
    }

    @GET
    @Path("/str/increase")
    @Timed
    public GeneralResponse strIncrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.INCSTR, n);
        return new GeneralResponse("Hero", "str", n.orElse(1));
    }

    @GET
    @Path("/str/decrease")
    @Timed
    public GeneralResponse strDecrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.DECSTR, n);
        return new GeneralResponse("Hero", "str", -n.orElse(1));
    }

    @GET
    @Path("/ht")
    @Timed
    public GeneralResponse ht() {
        Integer i = (Integer) this.returningDispatch(Hero.ACTION.GETHT);
        return new GeneralResponse("Hero", "ht", i);
    }

    @GET
    @Path("/ht/increase")
    @Timed
    public GeneralResponse htIncrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.INCHT, n);
        return new GeneralResponse("Hero", "ht", n.orElse(1));
    }

    @GET
    @Path("/ht/decrease")
    @Timed
    public GeneralResponse htDecrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.DECHT, n);
        return new GeneralResponse("Hero", "ht", -n.orElse(1));
    }

    @GET
    @Path("/hp/full")
    @Timed
    public GeneralResponse htFull() {
        Integer i = (Integer) this.returningDispatch(ACTION.FULLHP);
        return new GeneralResponse("Hero", "hp", i);
    }

    @GET
    @Path("/hp")
    @Timed
    public GeneralResponse hp(@QueryParam("n") Optional<Integer> n) {
        Integer i = (Integer) this.returningDispatch(Hero.ACTION.GETHP);
        return new GeneralResponse("Hero", "hp", i);
    }

    @GET
    @Path("/hp/increase")
    @Timed
    public GeneralResponse hpIncrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.INCHP, n);
        return new GeneralResponse("Hero", "hp", n.orElse(1));
    }

    @GET
    @Path("/hp/decrease")
    @Timed
    public GeneralResponse hpDecrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.DECHP, n);
        return new GeneralResponse("Hero", "hp", -n.orElse(1));
    }

    @GET
    @Path("/atk")
    @Timed
    public GeneralResponse atk() {
        Integer i = (Integer) this.returningDispatch(Hero.ACTION.GETATK);
        return new GeneralResponse("Hero", "atk", i);
    }

    @GET
    @Path("/atk/increase")
    @Timed
    public GeneralResponse atkIncrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.INCATK, n);
        return new GeneralResponse("Hero", "atk", n.orElse(1));
    }

    @GET
    @Path("/atk/decrease")
    @Timed
    public GeneralResponse atkDecrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.DECATK, n);
        return new GeneralResponse("Hero", "atk", -n.orElse(1));
    }

    @GET
    @Path("/def")
    @Timed
    public GeneralResponse def() {
        Integer i = (Integer) this.returningDispatch(Hero.ACTION.GETDEF);
        return new GeneralResponse("Hero", "def", i);
    }

    @GET
    @Path("/def/increase")
    @Timed
    public GeneralResponse defIncrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.INCDEF, n);
        return new GeneralResponse("Hero", "def", n.orElse(1));
    }

    @GET
    @Path("/def/decrease")
    @Timed
    public GeneralResponse defDecrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.DECDEF, n);
        return new GeneralResponse("Hero", "def", -n.orElse(1));
    }

    @GET
    @Path("/lvl")
    @Timed
    public GeneralResponse lvl() {
        Integer i = (Integer) this.returningDispatch(Hero.ACTION.GETLVL);
        return new GeneralResponse("Hero", "lvl", i);
    }

    @GET
    @Path("/lvl/increase")
    @Timed
    public GeneralResponse lvlIncrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.INCLVL, n);
        return new GeneralResponse("Hero", "lvl", n.orElse(1));
    }

    @GET
    @Path("/lvl/decrease")
    @Timed
    public GeneralResponse lvlDecrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.DECLVL, n);
        return new GeneralResponse("Hero", "lvl", -n.orElse(1));
    }

    @GET
    @Path("/exp")
    @Timed
    public GeneralResponse exp() {
        Integer i = (Integer) this.returningDispatch(Hero.ACTION.GETEXP);
        return new GeneralResponse("Hero", "exp", i);
    }

    @GET
    @Path("/exp/increase")
    @Timed
    public GeneralResponse expIncrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.INCEXP, n);
        return new GeneralResponse("Hero", "exp", n.orElse(1));
    }

    @GET
    @Path("/exp/decrease")
    @Timed
    public GeneralResponse expDecrease(@QueryParam("n") Optional<Integer> n) {
        this.dispatch(ACTION.DECEXP, n);
        return new GeneralResponse("Hero", "exp", -n.orElse(1));
    }

}
