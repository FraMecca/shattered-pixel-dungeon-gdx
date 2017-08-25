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
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class Rope extends EquipableItem {

	//public static final String AC_DESCEND	= "DESCEND";
	public static final String AC_DESCEND = "DESCEND";
	//public static final float TIME_TO_DESCEND = 1;
	public static final float TIME_TO_DESCEND = 1;
	{
		image = Assets.ROPE;
		
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
// TODO determine if useful in this case
		// super.execute( hero, action );

		// if (action.equals( AC_DESCEND )) {

		// hero.spend( TIME_TO_DESCEND );
		// hero.busy();

		// hero.sprite.operate( hero.pos );
		// }
	}

	@Override
	public boolean doEquip(Hero hero) {
		return true;
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
		return 200 * quantity;
	}

}
