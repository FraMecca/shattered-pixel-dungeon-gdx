package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;

public class NetPlayerRogueSprite extends NetPlayerSprite {

    public NetPlayerRogueSprite() {
        super();

        texture(HeroClass.spritesheet(HeroClass.ROGUE));
        updateArmor(0);
        idle();

    }
}

