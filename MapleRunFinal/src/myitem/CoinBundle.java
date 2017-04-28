package myitem;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class CoinBundle {
	Image[] img = ImageManager.coin_bundle;
	public int x, y; 
	public int index = 0;
	
	public int speed;
	
	public int w = img[0].getWidth(null);
	public int h = img[0].getHeight(null);
	
	public int interval = 5;


	public void move() {
		if (interval == 5)
			index++;

		interval--;
		if (interval < 0)
			interval = 5;

		if (index >= img.length)
			index = 0;
	}
	
	public boolean walk() {
		x -= speed;
		return (x < -w);
	}

	public void draw(Graphics g) {
		Image img = ImageManager.coin_bundle[index];
		g.drawImage(img, x, y, null);
	}

}
