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
		// Tile객체의 갯수
		int maxsize = Tiles.size() - 1;
		// idx가 만약 최대치라면
		if ((idx + 1) == maxsize)
			// 장애물이 아니다
			return false;
		// Tiles의 리스트에서 idx 번호의 타일 객체를 불러온다
		Tile_Property Tile = Tiles.get(idx);
		// Tiles의 리스트에서 idx + 1 번호의 타일 객체를 불러온다
		Tile_Property targetTile = Tiles.get(idx + 1);
		// Tile_ImageManager 내에서 이미지를 로드한다.
		Image[] tile_imgs = { Tile_ImageManager.Load(Tile.tile_kind, Tile.idx),
				Tile_ImageManager.Load(targetTile.tile_kind, targetTile.idx) };
		// Drop되는 범위를 구해온다.
		int DropStart = targetTile.x - (Tile.x + tile_imgs[0].getWidth(null));
		// Tile들의 사이 간격이 0보다 작거나 같다면
		if (DropStart <= 0) {
			// 인덱스 번호를 1을 더한다.
			idx++;
			// 장애물이 아니라며 반환을 해준다.
			return false;
		}
		// targetTile.x - (캐릭터 시작점 + 캐릭터 크기)
		// (캐릭터 시작점 + 캐릭터크기) = 150
		// 떨어지는 지점의 끝점
		int max = targetTile.x - 150;
		// 떨어지는 지점의 시작점
		DropStart = max - DropStart + 30;
		// System.out.printf("%d~%d\n",DropStart,max);
		Modify_Drop = (DropStart <= x) && (x < max);
		/*
		 * 떨어지는 지점은 DropStart ~ max이다. 예를들어 캐릭터 좌표가 특정 범위 내에 좌표에 있을 경우엔 떨어지는
		 * 장애물로 판단.
		 */

		// 만약 장애물이라면
		if (Modify_Drop == true) {
			// 카운트를 더한다.
			cnt++;
			// 장애물이라고 반환을 해준다.
			return true;
		} else if (Modify_Drop == false && cnt > 0) {
			// cnt를 0으로 초기화 해준다.
			cnt = 0;
			// idx 번호를 더해준다.
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
		// 맵 스테이지 경로를 통해 불러옴
		// ex) 프로그램 경로\map\stage_1.map

		String status_stage = String.format("map/stage_%d.map", stage);
		try {
			// map 파일을 불러온다
			File file = new File(status_stage);
			// 파일을 스트림으로 읽어온다.
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
