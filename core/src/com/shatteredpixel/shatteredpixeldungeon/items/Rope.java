/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be DESCENDful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.shatteredpixel.shatteredpixeldungeon.items;


import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class Rope extends Item {

	//public static final String AC_DESCEND	= "DESCEND";
	public static final String AC_DESCEND = "DESCEND";
	//public static final float TIME_TO_DESCEND = 1;
	public static final float TIME_TO_DESCEND = 1;

	{
		//image = ItemSpriteSheet.ROPE;
		
		stackable = true;
		
		defaultAction = AC_DESCEND;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_DESCEND );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {
		super.execute( hero, action );

		if (action.equals( AC_DESCEND )) {
			System.out.println("descended! removing rope");
			detach(hero.belongings.backpack);

			hero.spend( TIME_TO_DESCEND );
			hero.busy();

			hero.sprite.operate( hero.pos );
		}
	}

	@Override
	public boolean doPickUp( Hero hero ) {
		if (hero.belongings.backpack.rope == null) {
			if (collect(hero.belongings.backpack)) {

				hero.belongings.backpack.rope = this;
				GameScene.pickUp(this);
				Sample.INSTANCE.play(Assets.SND_ITEM);
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;

			}
		}
		return false;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int price() {
		return 10 * quantity;
	}

}
