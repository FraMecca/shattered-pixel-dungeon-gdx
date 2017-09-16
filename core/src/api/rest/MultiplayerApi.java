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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.Optional;

@Path("/multi")
@Produces(MediaType.APPLICATION_JSON)
public class MultiplayerApi extends APIAbstract {

    private static Integer nPlayers = 0;

    public static String getnPlayers () {
        return nPlayers.toString ();
    }

    @GET
    @Path("/spawn/rogue")
    @Timed
    public GeneralResponse spawnRogue(@QueryParam("id") Optional<String> n) {
        nPlayers++;
        NetPlayerInst.spawnImages(HeroClass.ROGUE, n.orElse(getnPlayers()));
        // use name or count as identification for api
        return new GeneralResponse(n.orElse(getnPlayers()), "rogue", 0L);
    }

    @GET
    @Path("/spawn/warrior")
    @Timed
    public GeneralResponse spawnWarrior(@QueryParam("id") Optional<String> n) {
        nPlayers++;
        NetPlayerInst.spawnImages(HeroClass.WARRIOR, n.orElse(getnPlayers()));
        return new GeneralResponse(n.orElse(getnPlayers()), "warrior", 0L);
    }

    @GET
    @Path("/spawn/huntress")
    @Timed
    public GeneralResponse spawnHuntress(@QueryParam("id") Optional<String> n) {
        nPlayers++;
        NetPlayerInst.spawnImages(HeroClass.HUNTRESS, n.orElse(getnPlayers()));
        return new GeneralResponse(n.orElse(getnPlayers()), "huntress", 0L);
    }

    @GET
    @Path("/spawn/mage")
    @Timed
    public GeneralResponse spawnmage(@QueryParam("id") Optional<String> n) {
        nPlayers++;
        NetPlayerInst.spawnImages(HeroClass.MAGE, n.orElse(getnPlayers()));
        return new GeneralResponse(n.orElse(getnPlayers()), "mage", 0L);
    }
    /*
    @GET
    @Path("/move/up")
    @Timed
    public GeneralResponse up(@QueryParam("id") String id) {
        this.dispatchTarget (id, NetPlayerInst.ACTION.UP);
        return new GeneralResponse(id, "up");
    }

    @GET
    @Path("/move/down")
    @Timed
    public GeneralResponse down(@QueryParam("id") String id) {
        this.dispatchTarget (id, NetPlayerInst.ACTION.DOWN);
        return new GeneralResponse(id, "down");
    }

    @GET
    @Path("/move/right")
    @Timed
    public GeneralResponse right(@QueryParam("id") String id) {
        this.dispatchTarget (id, NetPlayerInst.ACTION.RIGHT);
        return new GeneralResponse(id, "right");
    }

    @GET
    @Path("/move/left")
    @Timed
    public GeneralResponse left(@QueryParam("id") String id) {
        this.dispatchTarget (id, NetPlayerInst.ACTION.LEFT);
        return new GeneralResponse(id, "left");
    }

    @GET
    @Path("/action")
    @Timed
    public GeneralResponse genericAction (@QueryParam("id") String id, @QueryParam("action") String action) {
        try {
            byte[] decoded = Base64.getDecoder().decode(action);
            ByteArrayInputStream dc = new ByteArrayInputStream(decoded);
            ObjectInputStream in = new ObjectInputStream(dc);
            Hero h = (Hero) in.readObject();
            System.out.println(h);
        } catch (Exception e) {
            System.err.println("Error deserializing: " + e.getMessage());
        }

            this.dispatchTarget (id, NetPlayerInst.ACTION.LEFT);
        return new GeneralResponse(id, "left");
    }
    */
}
