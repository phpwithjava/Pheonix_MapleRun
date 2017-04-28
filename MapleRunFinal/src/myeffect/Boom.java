package myeffect;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class Boom {

	public static Image[] img = ImageManager.boom;
	// ���� ��ġ
	int x, y;
	// �̹��� index(array)
	int index = 0;

	public static int w = img[0].getWidth(null);
	public static int h = img[0].getHeight(null); // �����̹��� ��/����

	int interval = 10;

	public boolean move() {
		if (interval == 1)
			index++;

		interval--;
		if (interval < 0)
			interval = 1;
		return (index >= 4); // �̹����� ������ ���ȳ� üũ(true)
	}

	public void draw(Graphics g) {
		Image image = img[index];
		g.drawImage(image, (x - w / 2), (y - h / 2), null);

	}

}
