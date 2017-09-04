package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;

public class NetPlayerMageSprite extends NetPlayerSprite {

    public NetPlayerMageSprite() {
        super();

        texture(HeroClass.spritesheet(HeroClass.MAGE));
        updateArmor(0);
        idle();

    }
}
