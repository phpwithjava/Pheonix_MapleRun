package track;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import myitem.Item_Property;
import mymonsters.Mob_Property;

public class Data implements Serializable {
	public ArrayList<Tile_Property> tiles;
	public List<Tile_Property> Tiles;
	public List<Tile_Property> Tracks;
	public List<Mob_Property> Mobs;
	public List<Item_Property> items;
	public byte[] background;
	public byte[] save;
	 

	public Data() {

	}

	public Data(byte[] background, ArrayList<Tile_Property> tiles,List<Tile_Property> Tracks,List<Mob_Property> Mobs,List<Item_Property> items) {
		this.tiles = tiles;
		this.background = background;
		this.Tracks = Tracks;
		this.Mobs = Mobs;
		this.items = items;
	}

}
