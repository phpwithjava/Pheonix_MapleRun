package track;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import myitem.Item_Property;
import mymonsters.Mob_Property;

public class Map_Manager {


	public static List<Tile_Property> Tiles = new ArrayList<Tile_Property>();
	public static List<Tile_Property> Tracks = new ArrayList<Tile_Property>();
	public static List<Mob_Property> Mobs = new ArrayList<Mob_Property>();
	public static List<Item_Property> items = new ArrayList<Item_Property>();

	public static int tile_idx = 0;
	public static boolean Modify_Drop = false;
	public static int cnt = 0;
	public static int idx = 0;
	public static int trackidx = 0;
	public static int Track_cnt = 0;
	public static Image background;
	public static int last_track;

	public static boolean Modify_Drop(int x) {
		// Tile��ü�� ����
		int maxsize = Tiles.size() - 1;
		// idx�� ���� �ִ�ġ���
		if ((idx + 1) == maxsize)
			// ��ֹ��� �ƴϴ�
			return false;
		// Tiles�� ����Ʈ���� idx ��ȣ�� Ÿ�� ��ü�� �ҷ��´�
		Tile_Property Tile = Tiles.get(idx);
		// Tiles�� ����Ʈ���� idx + 1 ��ȣ�� Ÿ�� ��ü�� �ҷ��´�
		Tile_Property targetTile = Tiles.get(idx + 1);
		// Tile_ImageManager ������ �̹����� �ε��Ѵ�.
		Image[] tile_imgs = { Tile_ImageManager.Load(Tile.tile_kind, Tile.idx),
				Tile_ImageManager.Load(targetTile.tile_kind, targetTile.idx) };
		// Drop�Ǵ� ������ ���ؿ´�.
		int DropStart = targetTile.x - (Tile.x + tile_imgs[0].getWidth(null));
		// Tile���� ���� ������ 0���� �۰ų� ���ٸ�
		if (DropStart <= 0) {
			// �ε��� ��ȣ�� 1�� ���Ѵ�.
			idx++;
			// ��ֹ��� �ƴ϶�� ��ȯ�� ���ش�.
			return false;
		}
		// targetTile.x - (ĳ���� ������ + ĳ���� ũ��)
		// (ĳ���� ������ + ĳ����ũ��) = 150
		// �������� ������ ����
		int max = targetTile.x - 150;
		// �������� ������ ������
		DropStart = max - DropStart + 30;
		// System.out.printf("%d~%d\n",DropStart,max);
		Modify_Drop = (DropStart <= x) && (x < max);
		/*
		 * �������� ������ DropStart ~ max�̴�. ������� ĳ���� ��ǥ�� Ư�� ���� ���� ��ǥ�� ���� ��쿣 ��������
		 * ��ֹ��� �Ǵ�.
		 */

		// ���� ��ֹ��̶��
		if (Modify_Drop == true) {
			// ī��Ʈ�� ���Ѵ�.
			cnt++;
			// ��ֹ��̶�� ��ȯ�� ���ش�.
			return true;
		} else if (Modify_Drop == false && cnt > 0) {
			// cnt�� 0���� �ʱ�ȭ ���ش�.
			cnt = 0;
			// idx ��ȣ�� �����ش�.
			idx++;
			return false;
		}

		return false;
	}
	

	public static boolean Modify_Track(int x, int y) {
		int max = Tracks.size();
		if ((trackidx) == max)
			return false;
		Tile_Property Track = Tracks.get(trackidx);
		Image track_img = Tile_ImageManager.Load(Track.tile_kind, Track.idx);
		int start_posX = Track.x;
		int end_posX = Track.x + track_img.getWidth(null) - 31;
		int start_posY = 0;
		int end_posY = 470 + (track_img.getWidth(null) + Track.y);
		boolean track_Check = (start_posX <= x) && (x <= end_posX + 1) && (start_posY <= y) && (y <= end_posY);
		boolean trackX_Check = (start_posX <= x) && (x <= end_posX + 1);
		boolean trackY_Check = (start_posY <= y) && (y <= end_posY);

		if (trackX_Check == true) {
			Track_cnt++;
		} else if (trackX_Check == false && Track_cnt > 0) {
			Track_cnt = 0;
			trackidx++;
		}

		if (track_Check) {
			return true;
		}
		return false;
	}

	public static Image MapLoad(int stage) {
		// �� �������� ��θ� ���� �ҷ���
		// ex) ���α׷� ���\map\stage_1.map

		String status_stage = String.format("map/stage_%d.map", stage);
		try {
			// map ������ �ҷ��´�
			File file = new File(status_stage);
			// ������ ��Ʈ������ �о�´�.
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Data data = (Data) ois.readObject();
			Map_Manager.Tracks = data.Tracks;
			Map_Manager.items = data.items;
			Map_Manager.Mobs = data.Mobs;
			ByteArrayInputStream bios = new ByteArrayInputStream(data.background);
			InputStream is = bios;
			BufferedImage bi = ImageIO.read(is);
			Map_Manager.Tiles = data.tiles;
			Map_Manager.Tracks = data.Tracks;
			
			Map_Manager.items = data.items;
			Map_Manager.Mobs = data.Mobs;
			
			Map_Manager.background = new ImageIcon(bi).getImage();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Map_Manager.background;
	}

}
