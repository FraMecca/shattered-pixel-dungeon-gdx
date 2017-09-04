package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;

public class NetPlayerWarriorSprite extends NetPlayerSprite {

    public NetPlayerWarriorSprite() {
        super();

        texture( HeroClass.spritesheet(HeroClass.WARRIOR) );
        updateArmor( 0 );
        idle();
    }
}
