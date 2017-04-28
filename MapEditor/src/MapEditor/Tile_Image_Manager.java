package MapEditor;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class Tile_Image_Manager {
	public static Image result;
	public static String filename = "rc/background.png";

	public static Image background = new ImageIcon(filename).getImage();

	public static Image Load(int select, int idx) {
		if (select == 0) {
			String filename = String.format("rc/track/Track-Top-%d.png", idx);
			result = new ImageIcon(filename).getImage();

		} else if (select == 1) {
			String filename = String.format("rc/tile/Tile-Bottom-%d.png", idx);
			result = new ImageIcon(filename).getImage();

		} else if (select == 2) {
			String fn;
			if (idx == 1) {
				fn = String.format("rc/monsters/flower/flower_1.png", idx);
				result = new ImageIcon(fn).getImage();
			} else if (idx == 2) {
				fn = String.format("rc/monsters/tree/tree_1.png", idx);
				result = new ImageIcon(fn).getImage();

			} else if (idx == 3) {
				fn = String.format("rc/monsters/pig/pig_1.png", idx);
				result = new ImageIcon(fn).getImage();
			} else if (idx == 4) {
				fn = String.format("rc/monsters/dragon/dragon_1.png", idx);
				result = new ImageIcon(fn).getImage();
			} else if (idx == 5) {
				fn = String.format("rc/monsters/mushroom/mushroom_1.png", idx);
				result = new ImageIcon(fn).getImage();
			}
		}else if (select == 3) {
			String fn;
			if (idx == 1) {
				fn = String.format("rc/item/potion/red_potion.png", idx);
				result = new ImageIcon(fn).getImage();
			} else if (idx == 2) {
				fn = String.format("rc/item/potion/blue_potion.png", idx);
				result = new ImageIcon(fn).getImage();

			} else if (idx == 3) {
				fn = String.format("rc/item/coin/coin_bundle/bundle_1.png", idx);
				result = new ImageIcon(fn).getImage();
			} else if (idx == 4) {
				fn = String.format("rc/item/coin/coin_bills/bill_1.png", idx);
				result = new ImageIcon(fn).getImage();
			} else if (idx == 5) {
				fn = String.format("rc/item/coin/coin_gold/gld_1.png", idx);
				result = new ImageIcon(fn).getImage();
			}
			 else if (idx == 6) {
					fn = String.format("rc/item/coin/coin_bronze/brz_1.png", idx);
					result = new ImageIcon(fn).getImage();
			}
		}

		return result;
	}

	public static void LoadFile(File f) {

	}
}
