package api.rest;

import com.codahale.metrics.annotation.Timed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NetPlayerInst;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/multi")
@Produces(MediaType.APPLICATION_JSON)
public class MultiplayerApi extends APIAbstract {

    @GET
    @Path("/spawn/rogue")
    @Timed
    public GeneralResponse spawnRogue(@QueryParam("n") Optional<Integer> n) {
        NetPlayerInst.spawnImages(HeroClass.ROGUE, n.orElse(1));
        return new GeneralResponse("Hero", "rogue", n.orElse(1));
    }

    @GET
    @Path("/spawn/warrior")
    @Timed
    public GeneralResponse spawnWarrior(@QueryParam("n") Optional<Integer> n) {
        NetPlayerInst.spawnImages(HeroClass.WARRIOR, n.orElse(1));
        return new GeneralResponse("Hero", "warrior", n.orElse(1));
    }

    @GET
    @Path("/spawn/huntress")
    @Timed
    public GeneralResponse spawnHuntress(@QueryParam("n") Optional<Integer> n) {
        NetPlayerInst.spawnImages(HeroClass.HUNTRESS, n.orElse(1));
        return new GeneralResponse("Hero", "huntress", n.orElse(1));
    }

    @GET
    @Path("/spawn/mage")
    @Timed
    public GeneralResponse spawnmage(@QueryParam("n") Optional<Integer> n) {
        NetPlayerInst.spawnImages(HeroClass.MAGE, n.orElse(1));
        return new GeneralResponse("Hero", "mage", n.orElse(1));
    }

    @GET
    @Path("/move/up")
    @Timed
    public GeneralResponse up() {
        this.dispatch (NetPlayerInst.ACTION.UP);
        return new GeneralResponse("Multiplayer", "up");
    }

    @GET
    @Path("/move/down")
    @Timed
    public GeneralResponse down() {
        this.dispatch (NetPlayerInst.ACTION.DOWN);
        return new GeneralResponse("Multiplayer", "down");
    }

    @GET
    @Path("/move/right")
    @Timed
    public GeneralResponse right() {
        this.dispatch (NetPlayerInst.ACTION.RIGHT);
        return new GeneralResponse("Multiplayer", "right");
    }

    @GET
    @Path("/move/left")
    @Timed
    public GeneralResponse left() {
        this.dispatch (NetPlayerInst.ACTION.LEFT);
        return new GeneralResponse("Multiplayer", "left");
    }
}
