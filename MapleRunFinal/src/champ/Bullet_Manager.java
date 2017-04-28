package champ;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Bullet_Manager {

	public static final int BULLET_INTERVAL = 7;
	public int bullet_interval = BULLET_INTERVAL;

	public List<Bullet> bulletList = new ArrayList<Bullet>();

	public void makeBullet(Point mePt) {

		if (bullet_interval == BULLET_INTERVAL) {
			Bullet b = new Bullet();

			if (b.direction == 0)
				b.direction = Common.Champ.SKILL;

			b.x = mePt.x + 40;
			b.y = mePt.y - 40;
			b.radius = 5;
			bulletList.add(b);
		}

		bullet_interval--;

		if (bullet_interval < 0)
			bullet_interval = BULLET_INTERVAL;
	}

	public void draw_all(Graphics g) {
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet b = bulletList.get(i);

			b.draw(g);
			if (b.move()) {
				bulletList.remove(b);
			}

		}
	}

}
