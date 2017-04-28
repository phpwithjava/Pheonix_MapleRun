package mymonsters;

import java.awt.Graphics;
import java.awt.Image;

import mymain.ImageManager;

public class Pig {

	Image[] img = ImageManager.pig;
	Image[] die = ImageManager.pig_die;
	

	// 상태 상수
	public static final int MOVE = 0;
	public static final int DEAD = 1;

	// 몬스터 스탯
	public int x, y; // 위치
	public int index = 0; // 평상모습 모션
	public int die_index = 0; // 죽는모습 모션
	public int speed; // 속도
	public int hp = 5; // 체력

	public int state = MOVE; // 초기값 : 평상 모습

	// 몬스터 크기
	public int w = img[0].getWidth(null);
	public int h = img[0].getHeight(null);

	// 애니매이션 속도
	public int interval = 5;

	// 몬스터 평상 모습 그리기
	public void move() {
		if (interval == 8)
			index++;

		interval--;
		if (interval < 0)
			interval = 8;

		if (index >= img.length)
			index = 0;
	}

	// 몬스터 이동
	public boolean walk() {
		x -= speed;
		return (x < -w);
	}

	// 몬스터 죽는 모습 그리기
	public boolean dead() {
		if (interval == 8)
			die_index++;

		interval--;
		if (interval < 0)
			interval = 8;

		x -= speed;

		return die_index >= die.length;
	}

	// 상태에 따라 다르게 그리기
	public void draw(Graphics g) {

		if (state == MOVE) {
			Image image = img[index];
			g.drawImage(image, x, y, null);
		}

		else if (state == DEAD) {
			Image image = die[die_index];
			g.drawImage(image, x, y, null);
		}

		// g.fillRect(x, y, w, h);
	}
}