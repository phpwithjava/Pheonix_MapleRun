package mymonsters;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import champ.Bullet;
import champ.Bullet_Manager;
import champ.Champ_Move_Manager;
import champ.Common;
import myeffect.EffectManager;
import mymain.MapleRun;

public class MonsterCollisionManager {

	public int w = 40, h = 50;

	public boolean attacked = false;

	MonsterManager mm;

	Champ_Move_Manager chm;

	EffectManager em;

	Bullet_Manager bm;

	MonsterAnimationManager mam;

	public MonsterCollisionManager() {
		// TODO Auto-generated constructor stub
	}

	public MonsterCollisionManager(MonsterManager mm, Champ_Move_Manager chm, EffectManager em, Bullet_Manager bm,
			MonsterAnimationManager mam) {
		super();
		this.mm = mm;
		this.chm = chm;
		this.em = em;
		this.bm = bm;
		this.mam = mam;
	}

	public Timer timer_1 = new Timer(500, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (chm.bFire == false)
				chm.champ_opacity(Common.Champ.HIT);
			timer_3.start();
			timer_1.stop();

		}
	});

	Timer timer_3 = new Timer(2500, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			attacked = false;
			if (chm.bFire == false)
				chm.champ_opacity(Common.Champ.NONE);
			timer_3.stop();
		}
	});

	// ²É°ú Á¢ÃË ÀÌº¥Æ®
	public void collision_flower(Point mePt) {
		if (chm.bFire == true && chm.champ == 1)
			w = 150;
		else
			w = 40;

		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < mm.flowerList.size(); i++) {
			Flower monster = mm.flowerList.get(i);
			Rectangle msr = new Rectangle(monster.x, monster.y, monster.w, monster.h);

			if (mr.intersects(msr) && attacked == false) {

				if (chm.bFire == false) {
					chm.champ_opacity(Common.Champ.COLLISION);
					em.make_crush(mr.x, mr.y - (h / 2));
					MapleRun.hp -= 10;
					attacked = true;
					timer_1.start();
				}

				// 1¹ø Ã¨ÇÁ°¡ ½ºÅ³À» ¾µ¶§?
				else if (chm.bFire == true && chm.champ == 1) {

					em.make_smash(mr.x, mr.y - 30);
					em.make_smash(mr.x, mr.y - 60);
					em.make_smash(mr.x, mr.y - 90);

					mam.make_flower(msr.x, msr.y);
					em.make_boom(msr.x, msr.y);
					mm.flowerList.remove(i);
				}

			}

			// ÃÑ¿¡ ¸ÂÀ¸¸é?
			for (int k = 0; k < bm.bulletList.size(); k++) {
				Bullet bullet = bm.bulletList.get(k);
				Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
				if (bl.intersects(msr)) {
					bm.bulletList.remove(k);
					em.make_crush(bl.x, bl.y - 20);
					monster.hp--;
					if (monster.hp <= 0) {
						mam.make_flower(msr.x, msr.y);
						em.make_boom(msr.x, msr.y);
						mm.flowerList.remove(i);
					}
				}
			}
		}
	}

	// ¹ö¼¸
	public void collision_mushroom(Point mePt) {
		if (chm.bFire == true && chm.champ == 1)
			w = 150;
		else
			w = 40;

		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < bm.bulletList.size(); i++) {
			Bullet bullet = bm.bulletList.get(i);
			Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
		}

		for (int i = 0; i < mm.mushroomList.size(); i++) {
			Mushroom monster = mm.mushroomList.get(i);
			Rectangle msr = new Rectangle(monster.x, monster.y, monster.w, monster.h);

			if (mr.intersects(msr) && attacked == false) {

				if (chm.bFire == false) {
					chm.champ_opacity(Common.Champ.COLLISION);
					em.make_crush(mr.x, mr.y - (h / 2));
					MapleRun.hp -= 10;
					attacked = true;
					timer_1.start();
				}

				// 1¹ø Ã¨ÇÁ°¡ ½ºÅ³À» ¾µ¶§?
				else if (chm.bFire == true && chm.champ == 1) {

					em.make_smash(mr.x, mr.y - 30);
					em.make_smash(mr.x, mr.y - 60);
					em.make_smash(mr.x, mr.y - 90);

					mam.make_mushroom(msr.x, msr.y);
					em.make_boom(msr.x, msr.y);
					mm.mushroomList.remove(i);
				}

			}

			// ¹ö¼¸ ÃÑ
			for (int k = 0; k < bm.bulletList.size(); k++) {
				Bullet bullet = bm.bulletList.get(k);
				Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
				if (bl.intersects(msr)) {
					bm.bulletList.remove(k);
					em.make_crush(bl.x, bl.y - 20);
					monster.hp--;
					if (monster.hp <= 0) {
						mam.make_mushroom(msr.x, msr.y);
						em.make_boom(msr.x, msr.y);
						mm.mushroomList.remove(i);
					}
				}
			}

		}

	}

	// µÅÁö
	public void collision_pig(Point mePt) {
		if (chm.bFire == true && chm.champ == 1)
			w = 150;
		else
			w = 40;

		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < bm.bulletList.size(); i++) {
			Bullet bullet = bm.bulletList.get(i);
			Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
		}

		for (int i = 0; i < mm.pigList.size(); i++) {
			Pig monster = mm.pigList.get(i);
			Rectangle msr = new Rectangle(monster.x, monster.y, monster.w, monster.h);

			if (mr.intersects(msr) && attacked == false) {

				if (chm.bFire == false) {
					chm.champ_opacity(Common.Champ.COLLISION);
					em.make_crush(mr.x, mr.y - (h / 2));
					MapleRun.hp -= 10;
					attacked = true;
					timer_1.start();
				}

				// 1¹ø Ã¨ÇÁ°¡ ½ºÅ³À» ¾µ¶§?
				else if (chm.bFire == true && chm.champ == 1) {

					em.make_smash(mr.x, mr.y - 30);
					em.make_smash(mr.x, mr.y - 60);
					em.make_smash(mr.x, mr.y - 90);

					em.make_boom(msr.x, msr.y);
					mam.make_pig(msr.x, msr.y);
					mm.pigList.remove(i);
				}

			}

			// µÅÁö ÃÑ
			for (int k = 0; k < bm.bulletList.size(); k++) {
				Bullet bullet = bm.bulletList.get(k);
				Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
				if (bl.intersects(msr)) {
					bm.bulletList.remove(k);
					em.make_crush(bl.x, bl.y - 20);
					monster.hp--;
					if (monster.hp <= 0) {
						mam.make_pig(msr.x, msr.y);
						em.make_boom(msr.x, msr.y);
						mm.pigList.remove(i);
					}
				}
			}

		}
	}

	// ³ª¹«
	public void collision_tree(Point mePt) {
		if (chm.bFire == true && chm.champ == 1)
			w = 150;
		else
			w = 40;

		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < bm.bulletList.size(); i++) {
			Bullet bullet = bm.bulletList.get(i);
			Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
		}

		for (int i = 0; i < mm.treeList.size(); i++) {
			Tree monster = mm.treeList.get(i);
			Rectangle msr = new Rectangle(monster.x, monster.y + 40, monster.w - 40, monster.h - 40);

			if (mr.intersects(msr) && attacked == false) {

				if (chm.bFire == false) {
					chm.champ_opacity(Common.Champ.COLLISION);
					em.make_crush(mr.x, mr.y - (h / 2));
					MapleRun.hp -= 10;
					attacked = true;
					timer_1.start();
				}

				// 1¹ø Ã¨ÇÁ ½ºÅ³
				else if (chm.bFire == true && chm.champ == 1) {

					em.make_smash(mr.x, mr.y - 30);
					em.make_smash(mr.x, mr.y - 60);
					em.make_smash(mr.x, mr.y - 90);

					mam.make_tree(msr.x, msr.y);
					em.make_boom(msr.x, msr.y);
					mm.treeList.remove(i);
				}

			}

			// ³ª¹« ÃÑ
			for (int k = 0; k < bm.bulletList.size(); k++) {
				Bullet bullet = bm.bulletList.get(k);
				Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
				if (bl.intersects(msr)) {
					bm.bulletList.remove(k);
					em.make_crush(bl.x, bl.y - 20);
					monster.hp--;
					if (monster.hp <= 0) {
						mam.make_tree(msr.x, msr.y);
						em.make_boom(msr.x, msr.y);
						mm.treeList.remove(i);
					}
				}
			}

		}

	}

	// ¿ë
	public void collision_dragon(Point mePt) {
		if (chm.bFire == true && chm.champ == 1)
			w = 150;
		else
			w = 40;

		Rectangle mr = new Rectangle(mePt.x, mePt.y - h, w, h);

		for (int i = 0; i < bm.bulletList.size(); i++) {
			Bullet bullet = bm.bulletList.get(i);
			Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
		}

		for (int i = 0; i < mm.dragonList.size(); i++) {
			Dragon monster = mm.dragonList.get(i);
			Rectangle msr = new Rectangle(monster.x, monster.y, monster.w, monster.h);

			if (mr.intersects(msr) && attacked == false) {

				if (chm.bFire == false) {
					chm.champ_opacity(Common.Champ.COLLISION);
					em.make_crush(mr.x, mr.y - (h / 2));
					MapleRun.hp -= 10;
					attacked = true;
					timer_1.start();
				}

				else if (chm.bFire == true && chm.champ == 1) {

					em.make_smash(mr.x, mr.y - 30);
					em.make_smash(mr.x, mr.y - 60);
					em.make_smash(mr.x, mr.y - 90);

					mam.make_dragon(msr.x, msr.y);
					em.make_boom(msr.x, msr.y);
					mm.dragonList.remove(i);
				}

			}

			// ¿ë ÃÑ
			for (int k = 0; k < bm.bulletList.size(); k++) {
				Bullet bullet = bm.bulletList.get(k);
				Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 10);
				if (bl.intersects(msr)) {
					bm.bulletList.remove(k);
					em.make_crush(bl.x, bl.y - 20);
					monster.hp--;
					if (monster.hp <= 0) {
						mam.make_dragon(msr.x, msr.y);
						em.make_boom(msr.x, msr.y);
						mm.dragonList.remove(i);
					}
				}
			}

		}

	}

}
