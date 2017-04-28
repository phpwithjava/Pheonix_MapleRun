package myeffect;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class Boom {

	public static Image[] img = ImageManager.boom;
	// 폭발 위치
	int x, y;
	// 이미지 index(array)
	int index = 0;

	public static int w = img[0].getWidth(null);
	public static int h = img[0].getHeight(null); // 폭발이미지 폭/높이

	int interval = 10;

	public boolean move() {
		if (interval == 1)
			index++;

		interval--;
		if (interval < 0)
			interval = 1;
		return (index >= 4); // 이미지를 끝까지 돌렸나 체크(true)
	}

	public void draw(Graphics g) {
		Image image = img[index];
		g.drawImage(image, (x - w / 2), (y - h / 2), null);

	}

}
