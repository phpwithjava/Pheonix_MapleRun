package champ;

import java.awt.Graphics;
import java.awt.Image;

public class Champ3_move {

	final int NONE = 0;
	final int SKILL = 1;
	final int HIT = 2;
	final int JUMP = 3;
	final int SLIDE = 4;
	final int COLLISION = 5;
	final int HIT_SLIDE = 6;

	public int x, y;
	public int index1 = 0;
	public int index2 = 0;
	public int index3 = 0;
	int interval = 6;
	int interval1 = 20;

	public static int w = 80;
	public static int h = 80;

	public static int state = Common.Champ.state;

	public void move() {

		if (interval == 6) {
			index1++;
			if (index1 >= 4)
				index1 = 0;

			index2++;
			if (index2 >= 7)
				index2 = 0;
		}

		if (interval1 == 10) {
			index3++;
			if (index3 >= 4)
				index3 = 0;
		}

		interval--;
		interval1--;

		if (interval < 0) {
			interval = 6;
		}

		if (interval1 < 0) {
			interval1 = 10;
		}

	}

	public void draw(Graphics g) {

		if (state == NONE) {
			Image img = ChampImageManager.walk_img3[index1];
			g.drawImage(img, x, y, null);
		}

		else if (state == SKILL) {
			Image img = ChampImageManager.attack_img3[index2];
			g.drawImage(img, x, y - 10, null);
		}

		else if (state == HIT) {
			Image img = ChampImageManager.op_walk_img3[index3];
			g.drawImage(img, x, y, null);
		}

		else if (state == HIT_SLIDE) {
			Image img = ChampImageManager.op_slide_img3[index3];
			g.drawImage(img, x, y + 20, null);
		}

		else if (state == SLIDE) {
			Image img = ChampImageManager.slide_img3;
			g.drawImage(img, x, y + 20, null);
		}

		else if (state == COLLISION) {
			Image img = ChampImageManager.collision_img3;
			g.drawImage(img, x, y, null);
		}

	}

}
