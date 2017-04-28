package champ;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ChampImageManager {
	// 1번 챔피언 Motion
	public static Image[] walk_img1 = new Image[4];
	public static Image[] attack_img1 = new Image[7];
	public static Image[] op_walk_img1 = new Image[4];
	public static Image[] op_slide_img1 = new Image[4];
	public static Image slide_img1;
	public static Image collision_img1;
	// 2번 챔피언 Motion
	public static Image[] walk_img2 = new Image[4];
	public static Image[] attack_img2 = new Image[9];
	public static Image[] op_walk_img2 = new Image[4];
	public static Image[] op_slide_img2 = new Image[4];
	public static Image slide_img2;
	public static Image collision_img2;
	public static Image bullet_img;
	// 3번 챔피언 Motion
	public static Image[] walk_img3 = new Image[4];
	public static Image[] attack_img3 = new Image[7];
	public static Image[] op_walk_img3 = new Image[4];
	public static Image[] op_slide_img3 = new Image[4];
	public static Image slide_img3;
	public static Image collision_img3;

	// 1번 챔피언 이미지 로드
	static {

		for (int i = 0; i < walk_img1.length; i++) {
			String filename = String.format("rc/champ/champ1/walk_%d.png", i + 1);
			walk_img1[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < attack_img1.length; i++) {
			String filename = String.format("rc/champ/champ1/attack/attack_%d.png", i + 1);
			attack_img1[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < op_walk_img1.length; i++) {
			String filename = String.format("rc/champ/champ1/opacity/walk_%d.png", i + 1);
			op_walk_img1[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < op_slide_img1.length; i++) {
			String filename = String.format("rc/champ/champ1/opacity_slide/opacity_slide_%d.png", i + 1);
			op_slide_img1[i] = new ImageIcon(filename).getImage();
		}

		String path = "rc/champ/champ1/Slide.png";
		slide_img1 = new ImageIcon(path).getImage();

		String path2 = "rc/champ/champ1/Collision.png";
		collision_img1 = new ImageIcon(path2).getImage();
	}

	// 2번 챔피언 이미지 로드
	static {

		for (int i = 0; i < walk_img2.length; i++) {
			String filename = String.format("rc/champ/champ2/walk_%d.png", i + 1);
			walk_img2[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < attack_img2.length; i++) {
			String filename = String.format("rc/champ/champ2/attack/attack_%d.png", i + 1);
			attack_img2[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < op_walk_img2.length; i++) {
			String filename = String.format("rc/champ/champ2/opacity/walk_%d.png", i + 1);
			op_walk_img2[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < op_slide_img2.length; i++) {
			String filename = String.format("rc/champ/champ2/opacity_slide/opacity_slide_%d.png", i + 1);
			op_slide_img2[i] = new ImageIcon(filename).getImage();
		}

		String path = "rc/champ/champ2/Slide.png";
		slide_img2 = new ImageIcon(path).getImage();

		String path2 = "rc/champ/champ2/Collision.png";
		collision_img2 = new ImageIcon(path2).getImage();
		// 2번 챔피언만 총알 발사
		String path3 = "rc/champ/bullet/bullet.png";
		bullet_img = new ImageIcon(path3).getImage();
	}

	// 3번 챔피언 이미지 로드
	static {

		for (int i = 0; i < walk_img3.length; i++) {
			String filename = String.format("rc/champ/champ3/walk_%d.png", i + 1);
			walk_img3[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < attack_img3.length; i++) {
			String filename = String.format("rc/champ/champ3/attack/attack_%d.png", i + 1);
			attack_img3[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < op_walk_img3.length; i++) {
			String filename = String.format("rc/champ/champ3/opacity/walk_%d.png", i + 1);
			op_walk_img3[i] = new ImageIcon(filename).getImage();
		}

		for (int i = 0; i < op_slide_img3.length; i++) {
			String filename = String.format("rc/champ/champ3/opacity_slide/opacity_slide_%d.png", i + 1);
			op_slide_img3[i] = new ImageIcon(filename).getImage();
		}

		String path = "rc/champ/champ3/Slide.png";
		slide_img3 = new ImageIcon(path).getImage();

		String path2 = "rc/champ/champ3/Collision.png";
		collision_img3 = new ImageIcon(path2).getImage();
	}

}
