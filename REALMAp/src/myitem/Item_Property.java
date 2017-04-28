package myitem;

import java.awt.Point;
import java.io.Serializable;

public class Item_Property implements Serializable  {
	public Point mePt;
	public int idx;
	public Item_Property(Point mePt,int idx)
	{
		this.mePt = mePt;
		this.idx = idx;
		
	}
}
