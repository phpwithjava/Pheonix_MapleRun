package myeffect;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class Crush {

	// ���� ��ġ
	int x, y;
	// �̹��� index(array)
	int index = 0;

	public static int w, h; // �����̹��� ��/����

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
		Image img = ImageManager.crush[index];
		g.drawImage(img, (x - w / 2), (y - h / 2), null);

	}

}
