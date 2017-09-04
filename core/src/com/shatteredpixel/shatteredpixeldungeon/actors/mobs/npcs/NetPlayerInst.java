package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BlacksmithSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MirrorSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Signal;

import java.util.ArrayList;

public class NetPlayerInst  extends MirrorImage implements Signal.RestListener {

    {
        spriteClass = MirrorSprite.class;

        state = PASSIVE;
    }

    // instance for each other player on LAN
    @Override
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

    public static int spawnImages(Hero hero, int nImages){
        return spawnImages(hero, nImages, hero.pos);
    }
    public static int spawnImages(Hero hero, int nImages, int pos){
        // Same as scroll of mirror images, but spawn instances of this class

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

            NetPlayerInst mob = new NetPlayerInst();
            GameScene.add( mob );
            ScrollOfTeleportation.appear( mob, respawnPoints.get( index ) );

            respawnPoints.remove( index );
            nImages--;
            spawned++;
        }

        return spawned;
    }
    static enum Action {UP, DOWN, RIGHT, LEFT}
    @Override
    public void onSignal(Object o) {
        if (o instanceof Action) {
            Action action = (Action) o;
            switch (action) {
                case UP:
                    int pos = Dungeon.level.height() + this.pos;
                    super.move(pos);
                    break;

                case DOWN:
                    pos = -Dungeon.level.height() + this.pos;
                    super.move(pos);
                    break;

                case RIGHT:
                    pos = this.pos + 1;
                    super.move(pos);
                    break;

                case LEFT:
                    pos = this.pos - 1;
                    super.move(pos);
                    break;

            }
        }
    }



}
