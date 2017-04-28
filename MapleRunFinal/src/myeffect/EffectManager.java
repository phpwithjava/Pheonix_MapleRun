package myeffect;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class EffectManager {

	List<Crush> crushList = new ArrayList<Crush>();
	List<Smash> smashList = new ArrayList<Smash>();
	List<Boom> boomList = new ArrayList<Boom>();

	public void make_crush(int x, int y) {
		Crush crush = new Crush();

		crush.x = x;
		crush.y = y;

		crushList.add(crush);
	}

	public void make_smash(int x, int y) {
		Smash smash = new Smash();

		smash.x = x;
		smash.y = y;

		smashList.add(smash);
	}

	public void make_boom(int x, int y) {
		Boom boom = new Boom();

		boom.x = x;
		boom.y = y;

		boomList.add(boom);
	}

	public void draw(Graphics g) {

		for (int i = 0; i < crushList.size(); i++) {
			Crush crush = crushList.get(i);
			crush.draw(g);

			if (crush.move() == true) {
				crushList.remove(crush);
			}

		}

		for (int i = 0; i < smashList.size(); i++) {
			Smash smash = smashList.get(i);
			smash.draw(g);

			if (smash.move() == true) {
				smashList.remove(smash);
			}

		}

		for (int i = 0; i < boomList.size(); i++) {
			Boom boom = boomList.get(i);
			boom.draw(g);

			if (boom.move() == true) {
				boomList.remove(boom);
			}

		}
	}

}
