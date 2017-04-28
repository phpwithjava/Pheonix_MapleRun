package track;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Tile_ImageManager {
	public static Image result;
	public static Image Load(int select,int idx){
		if(select==0)
		{
			String filename = String.format("rc/track/Track-Top-%d.png", idx);
			result = new ImageIcon(filename).getImage();
		
		}else if(select==1)
		{
			String filename = String.format("rc/tile/Tile-Bottom-%d.png", idx);
			result = new ImageIcon(filename).getImage();
		
		}
		
		return result;
	}
}
