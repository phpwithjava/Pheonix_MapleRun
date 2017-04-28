package myitem;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import mymain.Common;

public class ItemManager {

	List<CoinBronze> bronzeList = new ArrayList<CoinBronze>();
	List<CoinGold> goldList = new ArrayList<CoinGold>();
	List<CoinBill> billList = new ArrayList<CoinBill>();
	List<CoinBundle> bundleList = new ArrayList<CoinBundle>();
	List<ItemRedPotion> redList = new ArrayList<ItemRedPotion>();
	List<ItemBluePotion> blueList = new ArrayList<ItemBluePotion>();
	public void removeAll()
	{
		bronzeList.clear();
		goldList.clear();
		billList.clear();
		bundleList.clear();
		redList.clear();
		blueList.clear();
	}
	
	public void make_bronze(int x, int y) {
		CoinBronze brz = new CoinBronze();

		brz.x = x;
		brz.y = y;

		brz.speed = Common.Game_Balance.SPEED;

		bronzeList.add(brz);
	}

	public void make_gold(int x, int y) {
		CoinGold gld = new CoinGold();

		gld.x = x;
		gld.y = y;

		gld.speed = Common.Game_Balance.SPEED;

		goldList.add(gld);
	}

	public void make_bill(int x, int y) {
		CoinBill bil = new CoinBill();

		bil.x = x;
		bil.y = y;

		bil.speed = Common.Game_Balance.SPEED;

		billList.add(bil);

	}

	public void make_bundle(int x, int y) {
		CoinBundle bld = new CoinBundle();

		bld.x = x;
		bld.y = y;

		bld.speed = Common.Game_Balance.SPEED;

		bundleList.add(bld);

	}

	public void make_red_potion(int x, int y) {
		ItemRedPotion item = new ItemRedPotion();

		item.x = x;
		item.y = y;

		item.speed = Common.Game_Balance.SPEED;

		redList.add(item);
	}

	public void make_blue_potion(int x, int y) {
		ItemBluePotion item = new ItemBluePotion();

		item.x = x;
		item.y = y;

		item.speed = Common.Game_Balance.SPEED;

		blueList.add(item);
	}

	public void draw_all(Graphics g) {

		for (int i = 0; i < bronzeList.size(); i++) {
			CoinBronze coin = bronzeList.get(i);
			coin.draw(g);
			coin.move();

			if (coin.walk() == true)
				bronzeList.remove(coin);
		}

		for (int i = 0; i < goldList.size(); i++) {
			CoinGold coin = goldList.get(i);
			coin.draw(g);
			coin.move();
			if (coin.walk() == true)
				goldList.remove(coin);
		}

		for (int i = 0; i < billList.size(); i++) {
			CoinBill coin = billList.get(i);
			coin.draw(g);
			coin.move();
			if (coin.walk() == true)
				billList.remove(coin);
		}

		for (int i = 0; i < bundleList.size(); i++) {
			CoinBundle coin = bundleList.get(i);
			coin.draw(g);
			coin.move();
			if (coin.walk() == true)
				bundleList.remove(coin);
		}

		// 포션류
		for (int i = 0; i < redList.size(); i++) {
			ItemRedPotion item = redList.get(i);
			item.draw(g);
			if (item.walk() == true)
				redList.remove(item);
		}

		for (int i = 0; i < blueList.size(); i++) {
			ItemBluePotion item = blueList.get(i);
			item.draw(g);
			if (item.walk() == true)
				blueList.remove(item);
		}
	}

	public void speed_adj(int speed) {

		for (int i = 0; i < bronzeList.size(); i++) {
			CoinBronze coin = bronzeList.get(i);
			coin.speed = speed;
		}

		for (int i = 0; i < goldList.size(); i++) {
			CoinGold coin = goldList.get(i);
			coin.speed = speed;
		}

		for (int i = 0; i < billList.size(); i++) {
			CoinBill coin = billList.get(i);
			coin.speed = speed;
		}

		for (int i = 0; i < bundleList.size(); i++) {
			CoinBundle coin = bundleList.get(i);
			coin.speed = speed;
		}

		// 포션류
		for (int i = 0; i < redList.size(); i++) {
			ItemRedPotion item = redList.get(i);
			item.speed = speed;
		}

		for (int i = 0; i < blueList.size(); i++) {
			ItemBluePotion item = blueList.get(i);
			item.speed = speed;
		}

	}
}
