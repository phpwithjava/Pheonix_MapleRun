package champ;

import java.awt.Graphics;
import java.awt.Image;

public class Bullet {

	public int x, y;
	int radius = 5;

	int direction = 0;
	int speed = 30;

	public boolean move() {

		if ((direction & Common.Champ.SKILL) == direction) {
			x += speed;
		} else if ((direction & Common.Champ.JUMP) == direction) {
			x += speed;
		}

		if (x - radius > Common.GamePan.W)
			return true;

		return false;
	}

	public void draw(Graphics g) {
		Image img = ChampImageManager.bullet_img;
		g.drawImage(img, x, y, null);
	}

}
