package api.rest;

import com.codahale.metrics.annotation.Timed;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.*;

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

    @GET
    @Path("/potioninvisibility")
    @Timed
    public GeneralResponse invisibility () {
        // TODO: Cursed, other buffs
        PotionOfInvisibility a = new PotionOfInvisibility();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of Invisibility", 1);
    }

    @GET
    @Path("/potionlevitation")
    @Timed
    public GeneralResponse levitation () {
        // TODO: Cursed, other buffs
        PotionOfLevitation a = new PotionOfLevitation();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of Levitation", 1);
    }

    @GET
    @Path("/potionexperience")
    @Timed
    public GeneralResponse potion_exp () {
        // TODO: Cursed, other buffs
        PotionOfExperience a = new PotionOfExperience();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of Experience", 1);
    }

    @GET
    @Path("/potionfrost")
    @Timed
    public GeneralResponse frost () {
        // TODO: Cursed, other buffs
        PotionOfFrost a = new PotionOfFrost();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of Frost", 1);
    }

    @GET
    @Path("/potionliquidflame")
    @Timed
    public GeneralResponse liquidflame () {
        // TODO: Cursed, other buffs
        PotionOfLiquidFlame a = new PotionOfLiquidFlame();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of LiquidFlame", 1);
    }

    @GET
    @Path("/potionmight")
    @Timed
    public GeneralResponse might () {
        // TODO: Cursed, other buffs
        PotionOfMight a = new PotionOfMight();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of Might", 1);
    }

    @GET
    @Path("/potionmindvision")
    @Timed
    public GeneralResponse mindvision () {
        // TODO: Cursed, other buffs
        PotionOfMindVision a = new PotionOfMindVision();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of MindVision", 1);
    }

    @GET
    @Path("/potionparalyticGas")
    @Timed
    public GeneralResponse paralyticGas () {
        // TODO: Cursed, other buffs
        PotionOfParalyticGas a = new PotionOfParalyticGas();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of ParalyticGas", 1);
    }

    @GET
    @Path("/potionpurity")
    @Timed
    public GeneralResponse purity () {
        // TODO: Cursed, other buffs
        PotionOfPurity a = new PotionOfPurity();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of Purity", 1);
    }

    @GET
    @Path("/potionpotionstrength")
    @Timed
    public GeneralResponse potionstrength () {
        // TODO: Cursed, other buffs
        PotionOfStrength a = new PotionOfStrength();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of Strength", 1);
    }

    @GET
    @Path("/potiontoxicGas")
    @Timed
    public GeneralResponse toxicGas () {
        // TODO: Cursed, other buffs
        PotionOfToxicGas a = new PotionOfToxicGas();
        signal.dispatch(a);
        return new GeneralResponse("Item", "Potion of ToxicGas", 1);
    }
}
