package champ;

import java.awt.Graphics;
import java.awt.Image;

public class ChampSelectManager {

	int index = 0;
	int index_skill1 = 0;
	int index_skill2 = 0;
	int index_skill3 = 0;
	int interval = 4;

	public void move() {
		if (interval == 10) {
			index++;
			if (index >= 4)
				index = 0;
		}

		interval--;

		if (interval < 0) {
			interval = 10;
		}

	}

	public void draw(Graphics g, int champ, int x, int y) {

		if (interval == 10) {
			index++;
			if (index >= 4)
				index = 0;
		}

		interval--;

		if (interval < 0) {
			interval = 10;
		}

		double w, h;

		if (champ == 1) {
			Image img = ChampImageManager.walk_img1[index];
			Image skill = ChampImageManager.attack_img1[index];
			g.drawImage(skill, x, y - 130, null);
			w = img.getWidth(null) * 1.5;
			h = img.getHeight(null) * 1.5;
			g.drawImage(img, x, y, (int) w, (int) h, null);
		}

		else if (champ == 2) {
			Image img = ChampImageManager.walk_img2[index];
			Image skill = ChampImageManager.attack_img2[index];
			g.drawImage(skill, x, y - 130, null);
			w = img.getWidth(null) * 1.5;
			h = img.getHeight(null) * 1.5;
			g.drawImage(img, x, y, (int) w, (int) h, null);
		}

		else if (champ == 3) {
			Image img = ChampImageManager.walk_img3[index];
			Image skill = ChampImageManager.attack_img3[index];
			g.drawImage(skill, x, y - 130, null);
			w = img.getWidth(null) * 1.5;
			h = img.getHeight(null) * 1.5;
			g.drawImage(img, x, y, (int) w, (int) h, null);
		}

	}
}
