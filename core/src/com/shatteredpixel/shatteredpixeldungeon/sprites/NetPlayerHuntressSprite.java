package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;

public class NetPlayerHuntressSprite extends NetPlayerSprite {

    public NetPlayerHuntressSprite() {
        super();

        texture(HeroClass.spritesheet(HeroClass.HUNTRESS));
        updateArmor(0);
        idle();

    }
}
