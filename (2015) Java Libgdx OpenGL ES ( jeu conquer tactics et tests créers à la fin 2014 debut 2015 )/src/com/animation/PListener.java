package com.animation;

import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline.Key;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Player.PlayerListener;
import com.brashmonkey.spriter.PlayerTweener;

public class PListener implements PlayerListener {

	protected PlayerTweener tweener;
	protected boolean attacked = false, dep = false;
	protected static boolean waiting = false;

	public PListener(PlayerTweener tweener, boolean dep) {
		this.tweener = tweener;
		this.dep = dep;
	}

	public PListener(PlayerTweener tweener) {
		this.tweener = tweener;
	}

	@Override
	public void animationFinished(Animation animation) {
		if (!dep) {
			if (tweener.getWeight() == 0) {
				tweener.setWeight(1);
				if (attacked)
					waiting = true;
			} else if (tweener.getWeight() == 1 && !attacked) {
				tweener.setWeight(0);
				attacked = true;
			}
		} else {
			if (tweener.getWeight() == 0)
				tweener.setWeight(1);
			else if (tweener.getWeight() == 1 && waiting) {
				tweener.setWeight(0);
				waiting = false;
			}
		}
	}

	@Override
	public void animationChanged(Animation oldAnim, Animation newAnim) {

	}

	@Override
	public void preProcess(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postProcess(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mainlineKeyChanged(Key prevKey, Key newKey) {
		// TODO Auto-generated method stub

	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

}
