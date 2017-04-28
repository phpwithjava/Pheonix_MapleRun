package mymonsters;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import mymain.Common;

public class MonsterAnimationManager {

	// ���͵��� ������ ��ü
	List<Flower> flowerList = new ArrayList<Flower>();
	List<Mushroom> mushroomList = new ArrayList<Mushroom>();
	List<Pig> pigList = new ArrayList<Pig>();
	List<Tree> treeList = new ArrayList<Tree>();
	List<Dragon> dragonList = new ArrayList<Dragon>();

	// ���� �״� ��� ����
	public void make_flower(int x, int y) {
		Flower monster = new Flower();

		monster.x = x;
		monster.y = y;
		monster.speed = Common.Game_Balance.SPEED;
		monster.state = monster.DEAD;

		flowerList.add(monster);
	}

	public void make_mushroom(int x, int y) {
		Mushroom monster = new Mushroom();

		monster.x = x;
		monster.y = y;
		monster.speed = Common.Game_Balance.SPEED;
		monster.state = monster.DEAD;

		mushroomList.add(monster);
	}

	public void make_pig(int x, int y) {
		Pig monster = new Pig();

		monster.x = x;
		monster.y = y;
		monster.speed = Common.Game_Balance.SPEED;
		monster.state = monster.DEAD;

		pigList.add(monster);
	}

	public void make_tree(int x, int y) {
		Tree monster = new Tree();

		monster.x = x;
		monster.y = y;
		monster.speed = Common.Game_Balance.SPEED;
		monster.state = monster.DEAD;

		treeList.add(monster);
	}

	public void make_dragon(int x, int y) {
		Dragon monster = new Dragon();

		monster.x = x;
		monster.y = y;
		monster.speed = Common.Game_Balance.SPEED;
		monster.state = monster.DEAD;

		dragonList.add(monster);
	}

	// �׷��ֱ�
	public void draw(Graphics g) {

		// ��
		for (int i = 0; i < flowerList.size(); i++) {
			Flower monster = flowerList.get(i);
			monster.draw(g);

			if (monster.dead() == true) {
				flowerList.remove(monster);
			}
		}

		// ����
		for (int i = 0; i < mushroomList.size(); i++) {
			Mushroom monster = mushroomList.get(i);
			monster.draw(g);

			if (monster.dead() == true) {
				mushroomList.remove(monster);
			}
		}

		// ����
		for (int i = 0; i < pigList.size(); i++) {
			Pig monster = pigList.get(i);
			monster.draw(g);

			if (monster.dead() == true) {
				pigList.remove(monster);
			}
		}

		// ����
		for (int i = 0; i < treeList.size(); i++) {
			Tree monster = treeList.get(i);
			monster.draw(g);

			if (monster.dead() == true) {
				treeList.remove(monster);
			}
		}

		// �巡��
		for (int i = 0; i < dragonList.size(); i++) {
			Dragon monster = dragonList.get(i);
			monster.draw(g);

			if (monster.dead() == true) {
				dragonList.remove(monster);
			}
		}
	}

}
