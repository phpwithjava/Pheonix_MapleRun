package track;

import java.io.Serializable;

public class Tile_Property implements Serializable {
	public int x;
	public int y;
	public int tile_kind;
	public int idx;
	public Tile_Property(int tile_kind,int idx,int x ,int y)
	{
		this.tile_kind = tile_kind;
		this.idx = idx;
		this.x = x;
		this.y = y;
	}
}
