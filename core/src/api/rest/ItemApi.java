package api.rest;

import com.codahale.metrics.annotation.Timed;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemApi extends APIAbstract {

    @GET
    @Path("/ankh")
    @Timed
    public GeneralResponse ankh () {
        // TODO: Cursed, other buffs
        Ankh a = new Ankh();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Ankh", 1);
    }

    @GET
    @Path("/chains")
    @Timed
    public GeneralResponse chains () {
        // TODO: Cursed, other buffs
        EtherealChains a = new EtherealChains();
        signal.dispatch(a);
        return new GeneralResponse("Item", "EtherealChains", 1);
    }

    @GET
    @Path("/torch")
    @Timed
    public GeneralResponse torch () {
        // TODO: Cursed, other buffs
        Torch a = new Torch();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Torch", 1);
    }

}
