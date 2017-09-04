package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import api.rest.RestSharedData;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.*;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Signal;

import java.util.ArrayList;

public class NetPlayerInst  extends NPC implements Signal.RestListener {

    protected String apiId;
    // instance for each other player on LAN
    {
        //spriteClass = NetPlayerWarriorSprite.class;
        state = PASSIVE;

    }

    public NetPlayerInst (HeroClass heroCl, String name) {
        super ();
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
        // Same as scroll of mirror images, but spawn instances of this classA

        int nImages = 1;
        int pos = Dungeon.hero.pos;
        ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

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

    public static enum ACTION {UP, DOWN, RIGHT, LEFT}
    @Override
    public void onSignal(Object o) {
        if (o instanceof ACTION) {
            ACTION action = (ACTION) o;
            System.out.println(action);
            int oldPos = this.pos;
            switch (action) {
                case UP:
                    this.pos = Dungeon.level.height() + this.pos;
                    this.move(oldPos, this.pos);
                    break;

                case DOWN:
                    this.pos = -Dungeon.level.height() + this.pos;
                    this.move(oldPos, this.pos);
                    break;

                case RIGHT:
                    this.pos = this.pos + 1;
                    this.move(oldPos, this.pos);
                    break;

                case LEFT:
                    this.pos = this.pos - 1;
                    this.move(oldPos, this.pos);
                    break;

            }
        }
    }



}
