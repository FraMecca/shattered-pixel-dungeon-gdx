package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import api.rest.RestSharedData;
import api.rest.Subscriber;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroAction;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroAction.*;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.Key;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.AlchemyPot;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.*;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Signal;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;

public class NetPlayerInst  extends NPC implements Signal.RestListener {

    public boolean ready = false;
    private boolean damageInterrupt = true;
    public HeroAction curAction = null;
    public HeroAction lastAction = null;

    private Char enemy;

    private Item theKey;

    public boolean resting = false;

    public MissileWeapon rangedWeapon = null;

    private static final float TIME_TO_REST		= 1f;
    private static final float TIME_TO_SEARCH	= 2f;
    protected String apiId;
    public Belongings belongings;

    // instance for each other player on LAN
    {
        //spriteClass = NetPlayerWarriorSprite.class;
        state = PASSIVE;

    }

    public NetPlayerInst (HeroClass heroCl, String name) {
        super ();
        Subscriber.getSubscriber().add(this);
        switch (heroCl) {
            case MAGE:
                this.spriteClass = NetPlayerMageSprite.class;
                break;
            case WARRIOR:
                this.spriteClass = NetPlayerWarriorSprite.class;
                break;
            case ROGUE:
                this.spriteClass = NetPlayerRogueSprite.class;
                break;
            case HUNTRESS:
                this.spriteClass = NetPlayerHuntressSprite.class;
                break;
        }

        this.apiId = name;

        RestSharedData.getRestIstance().multipApi.add(this);

    }

    //@Override
    public boolean interact() {
        return false;
    }

    @Override
    public Object signalAndGet(Object o) {
        return null;
    }

    @Override
    public void onSignal(Object o, Integer i) {

    }

    @Override
    public boolean checkId(String id) {
        return this.apiId.equals(id);
    }

    public static int spawnImages(HeroClass heroCl, String name){
        return spawnImages(heroCl, name, Dungeon.hero.pos);
    }

    public static int spawnImages(HeroClass heroCl, String name, int pos){
        // Same as scroll of mirror images, but spawn instances of this classA

        int nImages = 1;
        ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

        while (PathFinder.NEIGHBOURS8 == null); // spin lock
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = pos + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
                respawnPoints.add( p );
            }
        }

        int spawned = 0;
        while (nImages > 0 && respawnPoints.size() > 0) {
            int index = Random.index( respawnPoints );

            NetPlayerInst mob = new NetPlayerInst(heroCl, name);
            GameScene.add( mob );
            ScrollOfTeleportation.appear( mob, respawnPoints.get( index ) );

            respawnPoints.remove( index );
            nImages--;
            spawned++;
        }

        return spawned;
    }

    public void move (int from, int to) {
        // not sure if useful,
        // but move
        // move sprite,
        // stop motion

        this.pos = pos;
        super.move(to);
        this.moveSprite(from, to);
        this.sprite.idle();
    }

    @Override
    public void onSignal(Object o) {
        if (o instanceof HeroAction) {
            this.curAction = (HeroAction) o;
            this.interact(this.curAction);
            this.curAction = null;
        }
    }

    //@Override
    public boolean interact (HeroAction action) {
        System.out.println(action);
        if (curAction instanceof HeroAction.Move) {
            return actMove((HeroAction.Move) curAction);
        } else
                if (curAction instanceof HeroAction.Interact) {

            return actInteract( (HeroAction.Interact)curAction );

        } else
                if (curAction instanceof HeroAction.Buy) {

            return actBuy( (HeroAction.Buy)curAction );

        }else
                if (curAction instanceof HeroAction.PickUp) {

            return actPickUp( (HeroAction.PickUp)curAction );

        } else
                if (curAction instanceof HeroAction.OpenChest) {

            return actOpenChest( (HeroAction.OpenChest)curAction );

        } else
                if (curAction instanceof HeroAction.Unlock) {

            return actUnlock((HeroAction.Unlock) curAction);

        } else
                if (curAction instanceof HeroAction.Descend) {

            return actDescend( (HeroAction.Descend)curAction );

        } else
                if (curAction instanceof HeroAction.Ascend) {

            return actAscend( (HeroAction.Ascend)curAction );

        } else
                if (curAction instanceof HeroAction.Attack) {

            return actAttack( (HeroAction.Attack)curAction );

        } else
                if (curAction instanceof HeroAction.Cook) {

            return actCook( (HeroAction.Cook)curAction );

        }
        return false;
    }


    /*
    @Override
    public boolean act() {
        super.act();

        if (paralysed > 0) {

            curAction = null;

            spendAndNext( TICK );
            return false;
        }

        if (curAction == null) {

            ready();
            return false;

        } else {

            resting = false;

            ready = false;

            if (curAction instanceof HeroAction.Move) {

                return actMove( (HeroAction.Move)curAction );

            } else
            if (curAction instanceof HeroAction.Interact) {

                return actInteract( (HeroAction.Interact)curAction );

            } else
            if (curAction instanceof HeroAction.Buy) {

                return actBuy( (HeroAction.Buy)curAction );

            }else
            if (curAction instanceof HeroAction.PickUp) {

                return actPickUp( (HeroAction.PickUp)curAction );

            } else
            if (curAction instanceof HeroAction.OpenChest) {

                return actOpenChest( (HeroAction.OpenChest)curAction );

            } else
            if (curAction instanceof HeroAction.Unlock) {

                return actUnlock((HeroAction.Unlock) curAction);

            } else
            if (curAction instanceof HeroAction.Descend) {

                return actDescend( (HeroAction.Descend)curAction );

            } else
            if (curAction instanceof HeroAction.Ascend) {

                return actAscend( (HeroAction.Ascend)curAction );

            } else
            if (curAction instanceof HeroAction.Attack) {

                return actAttack( (HeroAction.Attack)curAction );

            } else
            if (curAction instanceof HeroAction.Cook) {

                return actCook( (HeroAction.Cook)curAction );

            }
        }

        return false;
    }*/

    private boolean actMove(Move curAction) {
        System.out.println(curAction.dst + " from " + this.pos);
        move(this.pos, curAction.dst);

        return false;
    }

    private void ready() {
        if (sprite.looping()) sprite.idle();
        curAction = null;
        damageInterrupt = true;
        ready = true;

        //AttackIndicator.updateState();
        //GameScene.ready();
    }

    public void spendAndNext(float tick) {
    }

    private boolean actInteract( HeroAction.Interact action ) {

        NPC npc = action.npc;

        if (Dungeon.level.adjacent( pos, npc.pos )) {

            ready();
            sprite.turnTo( pos, npc.pos );
            return npc.interact();

        } else {

            if (Level.fieldOfView[npc.pos] && getCloser( npc.pos )) {

                return true;

            } else {
                ready();
                return false;
            }

        }
    }

    private boolean actBuy( HeroAction.Buy action ) {
        int dst = action.dst;
        if (pos == dst || Dungeon.level.adjacent( pos, dst )) {

            ready();
            return false;

        } else if (getCloser( dst )) {

            return true;

        } else {
            ready();
            return false;
        }
    }

    private boolean actCook( HeroAction.Cook action ) {
        int dst = action.dst;
        if (Dungeon.visible[dst]) {

            ready();
            AlchemyPot.operate( this, dst );
            return false;

        } else if (getCloser( dst )) {

            return true;

        } else {
            ready();
            return false;
        }
    }

    private boolean actPickUp( HeroAction.PickUp action ) {
        int dst = action.dst;
        if (pos == dst) {

            Heap heap = Dungeon.level.heaps.get( pos );
            if (heap != null) {
                Item item = heap.peek();
                if (item.doPickUp( this )) {
                    heap.pickUp();

                    if (item instanceof Dewdrop
                            || item instanceof TimekeepersHourglass.sandBag
                            || item instanceof DriedRose.Petal
                            || item instanceof Key) {
                        //Do Nothing
                    } else {

                        boolean important =
                                ((item instanceof ScrollOfUpgrade || item instanceof ScrollOfMagicalInfusion) && ((Scroll)item).isKnown()) ||
                                        ((item instanceof PotionOfStrength || item instanceof PotionOfMight) && ((Potion)item).isKnown());
                        if (important) {
                            GLog.p( Messages.get(this, "you_now_have", item.name()) );
                        } else {
                            GLog.i( Messages.get(this, "you_now_have", item.name()) );
                        }
                    }

                    if (!heap.isEmpty()) {
                        GLog.i( Messages.get(this, "something_else") );
                    }
                    curAction = null;
                } else {
                    heap.sprite.drop();
                    ready();
                }
            } else {
                ready();
            }

            return false;

        } else if (getCloser( dst )) {

            return true;

        } else {
            ready();
            return false;
        }
    }

    private boolean actOpenChest( HeroAction.OpenChest action ) {
        int dst = action.dst;
        if (Dungeon.level.adjacent( pos, dst ) || pos == dst) {

            Heap heap = Dungeon.level.heaps.get( dst );
            if (heap != null && (heap.type != Heap.Type.HEAP && heap.type != Heap.Type.FOR_SALE)) {

                if ((heap.type == Heap.Type.LOCKED_CHEST || heap.type == Heap.Type.CRYSTAL_CHEST)
                        && Notes.keyCount(new GoldenKey(Dungeon.depth)) < 1) {

                    GLog.w( Messages.get(this, "locked_chest") );
                    ready();
                    return false;

                }

                switch (heap.type) {
                    case TOMB:
                        Sample.INSTANCE.play( Assets.SND_TOMB );
                        Camera.main.shake( 1, 0.5f );
                        break;
                    case SKELETON:
                    case REMAINS:
                        break;
                    default:
                        Sample.INSTANCE.play( Assets.SND_UNLOCK );
                }

                spend( Key.TIME_TO_UNLOCK );
                sprite.operate( dst );

            } else {
                ready();
            }

            return false;

        } else if (getCloser( dst )) {

            return true;

        } else {
            ready();
            return false;
        }
    }

    private boolean actUnlock( HeroAction.Unlock action ) {
        int doorCell = action.dst;
        if (Dungeon.level.adjacent( pos, doorCell )) {

            boolean hasKey = false;
            int door = Dungeon.level.map[doorCell];

            if (door == Terrain.LOCKED_DOOR
                    && Notes.keyCount(new IronKey(Dungeon.depth)) > 0) {

                hasKey = true;

            } else if (door == Terrain.LOCKED_EXIT
                    && Notes.keyCount(new SkeletonKey(Dungeon.depth)) > 0) {

                hasKey = true;

            }

            if (hasKey) {

                spend( Key.TIME_TO_UNLOCK );
                sprite.operate( doorCell );

                Sample.INSTANCE.play( Assets.SND_UNLOCK );

            } else {
                GLog.w( Messages.get(this, "locked_door") );
                ready();
            }

            return false;

        } else if (getCloser( doorCell )) {

            return true;

        } else {
            ready();
            return false;
        }
    }

    private boolean actDescend( HeroAction.Descend action ) {
        int stairs = action.dst;
        if (pos == stairs && pos == Dungeon.level.exit) {

            curAction = null;

            Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
            if (buff != null) buff.detach();

            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
            Game.switchScene( InterlevelScene.class );

            return false;

        } else if (getCloser( stairs )) {

            return true;

        } else {
            ready();
            return false;
        }
    }

    private boolean actAscend( HeroAction.Ascend action ) {
        int stairs = action.dst;
        if (pos == stairs && pos == Dungeon.level.entrance) {

            if (Dungeon.depth == 1) {

                if (belongings.getItem( Amulet.class ) == null) {
                    GameScene.show( new WndMessage( Messages.get(this, "leave") ) );
                    ready();
                } else {
                    Dungeon.win( Amulet.class );
                    Dungeon.deleteGame( Dungeon.hero.heroClass, true );
                    Game.switchScene( SurfaceScene.class );
                }

            } else {

                curAction = null;

                Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
                if (buff != null) buff.detach();

                InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
                Game.switchScene( InterlevelScene.class );
            }

            return false;

        } else if (getCloser( stairs )) {

            return true;

        } else {
            ready();
            return false;
        }
    }

    private boolean actAttack( HeroAction.Attack action ) {

        enemy = action.target;

        if (enemy.isAlive() && canAttack( enemy ) && !isCharmedBy( enemy )) {

            Invisibility.dispel();
            spend( attackDelay() );
            sprite.attack( enemy.pos );

            return false;

        } else {

            if (Level.fieldOfView[enemy.pos] && getCloser( enemy.pos )) {

                return true;

            } else {
                ready();
                return false;
            }

        }
    }

    public Char enemy(){
        return enemy;
    }
}
