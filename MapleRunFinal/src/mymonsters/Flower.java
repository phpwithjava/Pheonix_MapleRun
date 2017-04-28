package mymonsters;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class Flower {

	// �̹��� ȣ��
	Image[] img = ImageManager.flower; // �����
	Image[] die = ImageManager.flower_die; // �״� ���

	// ���� ���
	public static final int MOVE = 0;
	public static final int DEAD = 1;

	// ���� ����
	public int x, y; // ��ġ
	public int index = 0; // ����� ���
	public int die_index = 0; // �״¸�� ���
	public int speed; // �ӵ�
	public int hp = 5; // ü��

	public int state = MOVE; // �ʱⰪ : ��� ���

	// ���� ũ��
	public int w = img[0].getWidth(null);
	public int h = img[0].getHeight(null);

	// �ִϸ��̼� �ӵ�
	public int interval = 5;

	// ���� ��� ��� �׸���
	public void move() {
		if (interval == 8)
			index++;

		interval--;
		if (interval < 0)
			interval = 8;

		if (index >= img.length)
			index = 0;
	}

	// ���� �̵�
	public boolean walk() {
		x -= speed;
		return (x < -w);
	}

	// ���� �״� ��� �׸���
	public boolean dead() {
		if (interval == 8)
			die_index++;

		interval--;
		if (interval < 0)
			interval = 8;

		x -= speed;

		return die_index >= die.length;
	}

	// ���¿� ���� �ٸ��� �׸���
	public void draw(Graphics g) {

		if (state == MOVE) {
			Image image = img[index];
			g.drawImage(image, x, y, null);
		}

		else if (state == DEAD) {
			Image image = die[die_index];
			g.drawImage(image, x, y, null);
		}
	}
}
