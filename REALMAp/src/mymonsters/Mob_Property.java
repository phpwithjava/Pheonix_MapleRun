package mymonsters;

import java.awt.Point;
import java.io.Serializable;

public class Mob_Property  implements Serializable  {
	public Point mePt;
	public int mob_idx;
	public Mob_Property(Point mePt,int mob_idx)
	{
		this.mePt = mePt;
		this.mob_idx = mob_idx;
	}
}
