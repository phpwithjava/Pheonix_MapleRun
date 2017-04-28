package MapEditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
public class Map_Edit_Manager {

	public static void Move_Map(Graphics g,double back_off_x,Image background)
	{
		g.drawImage(background
				, 0, 0, 1100, 550
				 , (int)back_off_x, 0
				 , (int)back_off_x + 1100, 550, null);
	}
	public static void SelectedBlock(Graphics g,int x,int y,Image img)
	{
		g.drawImage(img, x, y, null);
	}
	public static Image Draw_Tile(Image background,int x,int y,Image tile)
	{
		Image save = background;
		BufferedImage bimg = new BufferedImage(background.getWidth(null), background.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bimg.createGraphics();
		graphics.drawImage(save, 0, 0, null);
		graphics.drawImage(tile, x, y, null);
		
		background = new ImageIcon(bimg).getImage();
		return background;
	}
}
