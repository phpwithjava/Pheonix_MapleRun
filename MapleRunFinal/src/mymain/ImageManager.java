package mymain;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageManager {

	// background
	public static Image back_ground;

	public static Image start_image;

	public static Image gameclear;

	public static Image gameover;

	public static Image ending;

	// 아이템
	// 동전
	// 브론즈
	public static Image[] coin_bronze = new Image[4];

	// 골드
	public static Image[] coin_gold = new Image[4];

	// 돈다발
	public static Image[] coin_bill = new Image[4];

	// 돈자루
	public static Image[] coin_bundle = new Image[4];

	// 포션
	public static Image red_potion;
	public static Image blue_potion;

	// 몬스터
	// 꽃
	public static Image[] flower = new Image[5];
	public static Image[] flower_die = new Image[8];
	// 버섯
	public static Image[] mushroom = new Image[11];
	public static Image[] mushroom_die = new Image[12];
	// 돼지
	public static Image[] pig = new Image[13];
	public static Image[] pig_die = new Image[8];
	// 나무
	public static Image[] tree = new Image[12];
	public static Image[] tree_die = new Image[10];
	// 드래곤
	public static Image[] dragon = new Image[10];
	public static Image[] dragon_die = new Image[8];

	// 챔피언
	//

	// 이펙트
	// crush
	public static Image[] crush = new Image[4];
	// smash
	public static Image[] smash = new Image[9];
	// boom
	public static Image[] boom = new Image[4];

	static {

		// 배경
		String path = "rc/background.png";
		back_ground = new ImageIcon(path).getImage();

		String path2 = "rc/start_image.png";
		start_image = new ImageIcon(path2).getImage();

		String path3 = "rc/gameclear.png";
		gameclear = new ImageIcon(path3).getImage();

		String path4 = "rc/gameover.png";
		gameover = new ImageIcon(path4).getImage();

		String path5 = "rc/ending.png";
		ending = new ImageIcon(path5).getImage();

		// 브론즈
		for (int i = 0; i < 4; i++) {
			String filename = String.format("rc/item/coin/coin_bronze/brz_%d.png", i + 1);
			coin_bronze[i] = new ImageIcon(filename).getImage();
		}

		// 골드
		for (int i = 0; i < 4; i++) {
			String filename = String.format("rc/item/coin/coin_gold/gld_%d.png", i + 1);
			coin_gold[i] = new ImageIcon(filename).getImage();
		}

		// 돈다발
		for (int i = 0; i < 4; i++) {
			String filename = String.format("rc/item/coin/coin_bills/bill_%d.png", i + 1);
			coin_bill[i] = new ImageIcon(filename).getImage();
		}

		// 돈자루
		for (int i = 0; i < 4; i++) {
			String filename = String.format("rc/item/coin/coin_bundle/bundle_%d.png", i + 1);
			coin_bundle[i] = new ImageIcon(filename).getImage();
		}

		// 몬스터 꽃
		for (int i = 0; i < 5; i++) {
			String filename = String.format("rc/monsters/flower/flower_%d.png", i + 1);
			flower[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < 8; i++) {
			String filename = String.format("rc/monsters/flower/die/flower_die_%d.png", i + 1);
			flower_die[i] = new ImageIcon(filename).getImage();
		}

		// 몬스터 버섯
		for (int i = 0; i < 11; i++) {
			String filename = String.format("rc/monsters/mushroom/mushroom_%d.png", i + 1);
			mushroom[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < 12; i++) {
			String filename = String.format("rc/monsters/mushroom/die/mushroom_die_%d.png", i + 1);
			mushroom_die[i] = new ImageIcon(filename).getImage();
		}

		// 몬스터 돼지
		for (int i = 0; i < 13; i++) {
			String filename = String.format("rc/monsters/pig/pig_%d.png", i + 1);
			pig[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < 8; i++) {
			String filename = String.format("rc/monsters/pig/die/pig_die_%d.png", i + 1);
			pig_die[i] = new ImageIcon(filename).getImage();
		}

		// 몬스터 나무
		for (int i = 0; i < 12; i++) {
			String filename = String.format("rc/monsters/tree/tree_%d.png", i + 1);
			tree[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < 10; i++) {
			String filename = String.format("rc/monsters/tree/die/tree_die_%d.png", i + 1);
			tree_die[i] = new ImageIcon(filename).getImage();
		}

		// 몬스터 드래곤
		for (int i = 0; i < 10; i++) {
			String filename = String.format("rc/monsters/dragon/dragon_%d.png", i + 1);
			dragon[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < 8; i++) {
			String filename = String.format("rc/monsters/dragon/die/dragon_die_%d.png", i + 1);
			dragon_die[i] = new ImageIcon(filename).getImage();
		}

		// 포션류
		red_potion = new ImageIcon("rc/item/potion/red_potion.png").getImage();
		blue_potion = new ImageIcon("rc/item/potion/blue_potion.png").getImage();

		// 이펙트류
		// crush
		for (int i = 0; i < 4; i++) {
			String filename = String.format("rc/effect/crush/crush_%d.png", i + 1);
			crush[i] = new ImageIcon(filename).getImage();
		}

		// smash
		for (int i = 0; i < 9; i++) {
			String filename = String.format("rc/effect/smash/smash_%d.png", i + 1);
			smash[i] = new ImageIcon(filename).getImage();
		}

		// boom
		for (int i = 0; i < 4; i++) {
			String filename = String.format("rc/effect/boom/boom_%d.png", i + 1);
			boom[i] = new ImageIcon(filename).getImage();
		}

	}

}
