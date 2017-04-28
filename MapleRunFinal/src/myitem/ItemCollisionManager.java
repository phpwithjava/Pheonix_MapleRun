package myitem;

import java.awt.Point;
import java.awt.Rectangle;

import mymain.MapleRun;
import mymonsters.Flower;

public class ItemCollisionManager {

	public int w = 50, h = 50;

	ItemManager im;

	public ItemCollisionManager() {
		// TODO Auto-generated constructor stub
	}

	public ItemCollisionManager(ItemManager im) {
		super();
		this.im = im;
	}

	public void collision_coin_bronze(Point mePt) {
		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < im.bronzeList.size(); i++) {
			CoinBronze coin = im.bronzeList.get(i);
			Rectangle msr = new Rectangle(coin.x, coin.y, coin.w, coin.h);

			if (mr.intersects(msr)) {
				im.bronzeList.remove(coin);
				MapleRun.score += 100;
			}
		}
	}

	public void collision_coin_gold(Point mePt) {
		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < im.goldList.size(); i++) {
			CoinGold coin = im.goldList.get(i);
			Rectangle msr = new Rectangle(coin.x, coin.y, coin.w, coin.h);

			if (mr.intersects(msr)) {
				im.goldList.remove(coin);
				MapleRun.score += 500;
			}
		}
	}

	public void collision_coin_bill(Point mePt) {
		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < im.billList.size(); i++) {
			CoinBill coin = im.billList.get(i);
			Rectangle msr = new Rectangle(coin.x, coin.y, coin.w, coin.h);

			if (mr.intersects(msr)) {
				im.billList.remove(coin);
				MapleRun.score += 1000;
			}
		}
	}

	public void collision_coin_bundle(Point mePt) {
		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < im.bundleList.size(); i++) {
			CoinBundle coin = im.bundleList.get(i);
			Rectangle msr = new Rectangle(coin.x, coin.y, coin.w, coin.h);

			if (mr.intersects(msr)) {
				im.bundleList.remove(coin);
				MapleRun.score += 2000;
			}
		}
	}

	public void collision_item_redpotion(Point mePt) {
		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < im.redList.size(); i++) {
			ItemRedPotion item = im.redList.get(i);
			Rectangle msr = new Rectangle(item.x, item.y, item.w, item.h);

			if (mr.intersects(msr)) {
				im.redList.remove(item);
				MapleRun.hp += 20;
				MapleRun.score += 100;
			}
		}
	}

	public void collision_item_bluepotion(Point mePt) {
		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < im.blueList.size(); i++) {
			ItemBluePotion item = im.blueList.get(i);
			Rectangle msr = new Rectangle(item.x, item.y, item.w, item.h);

			if (mr.intersects(msr)) {
				im.blueList.remove(item);
				MapleRun.mp += 50;
				MapleRun.score += 100;
			}
		}
	}

}
