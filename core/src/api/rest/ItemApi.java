package api.rest;

import com.codahale.metrics.annotation.Timed;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
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
        System.out.println("HHHHH");
        // TODO: Cursed, other buffs
        Ankh a = new Ankh();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Ankh", 1);
    }

}
