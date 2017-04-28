package myitem;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class ItemBluePotion {

	Image img = ImageManager.blue_potion;
	public int x, y;
	public int index = 0;

	public int speed;

	public int w = img.getWidth(null);
	public int h = img.getHeight(null);

	public boolean walk() {
		x -= speed;
		return (x < -w);
	}

	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
	}

}
