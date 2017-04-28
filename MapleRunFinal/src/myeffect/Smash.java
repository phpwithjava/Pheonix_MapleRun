package myeffect;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class Smash {
	// 폭발 위치
	int x, y;
	// 이미지 index(array)
	int index = 0;

	public static int w, h; // 폭발이미지 폭/높이

	int interval = 10;

	public boolean move() {
		if (interval == 1)
			index++;

		interval--;
		if (interval < 0)
			interval = 1;
		return (index >= 9); // 이미지를 끝까지 돌렸나 체크(true)
	}

	public void draw(Graphics g) {
		Image img = ImageManager.smash[index];
		g.drawImage(img, (x - w / 2), (y - h / 2), null);

	}

}