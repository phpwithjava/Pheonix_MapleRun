package mymonsters;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mymain.Common;

// 몬스터들이 하는 기능을 관리
public class MonsterManager {
	
	// 몬스터들을 저장할 객체
	List<Flower> flowerList = new ArrayList<Flower>();
	List<Mushroom> mushroomList = new ArrayList<Mushroom>();
	List<Pig> pigList = new ArrayList<Pig>();
	List<Tree> treeList = new ArrayList<Tree>();
	List<Dragon> dragonList = new ArrayList<Dragon>();
	
	public void removeAll()
	{
		flowerList.clear();
		mushroomList.clear();
		pigList.clear();
		treeList.clear();
		dragonList.clear();
	}
	
	
	public void make_flower (Point mePt) {
		Flower monster = new Flower();
		
		monster.x = mePt.x;
		monster.y = mePt.y ;
		
		monster.speed = Common.Game_Balance.SPEED;
		
		flowerList.add(monster);
	}
	
	public void make_mushroom (Point mePt) {
		Mushroom monster = new Mushroom();
		
		monster.x = mePt.x;
		monster.y = mePt.y ;
		
		monster.speed = Common.Game_Balance.SPEED;
		
		mushroomList.add(monster);
	}
	
	public void make_pig (Point mePt) {
		Pig monster = new Pig();
		
		monster.x = mePt.x;
		monster.y = mePt.y ;
		
		monster.speed = Common.Game_Balance.SPEED;
		
		pigList.add(monster);
	}
	public void make_Tree (Point mePt) {
		Tree monster = new Tree();
		
		monster.x = mePt.x;
		monster.y = mePt.y ;
		
		monster.speed = Common.Game_Balance.SPEED;
		
		treeList.add(monster);
	}
	
	public void make_dragon (Point mePt) {
		Dragon monster = new Dragon();
		
		monster.x = mePt.x;
		monster.y = mePt.y ;
		
		monster.speed = Common.Game_Balance.SPEED;
		
		dragonList.add(monster);
	}
	
public void draw_all(Graphics g) {
		
		for (int i=0; i<flowerList.size(); i++) {
			Flower monster = flowerList .get(i);
			
			monster.draw(g);
			monster.move();
			if (monster.walk() == true)
				flowerList.remove(monster);
			
			
		}
		
		for (int i=0; i<mushroomList.size(); i++) {
			Mushroom monster = mushroomList  .get(i);
			monster.draw(g);
			monster.move();
			if (monster.walk() == true)
				mushroomList.remove(monster);
		}
		
		for (int i=0; i<pigList.size(); i++) {
			Pig monster = pigList.get(i);
			monster.draw(g);
			monster.move();
			
			if (monster.walk() == true)
				pigList.remove(monster);
		}
		
		for (int i=0; i<treeList.size(); i++) {
			Tree monster = treeList.get(i);
			monster.draw(g);
			monster.move();
			if (monster.walk() == true)
				treeList.remove(monster);
		}
		
		for (int i=0; i<dragonList.size(); i++) {
			Dragon monster = dragonList.get(i);
			monster.draw(g);
			monster.move();
			if (monster.walk() == true)
				dragonList.remove(monster);
		}
		
	}

public void speed_adj(int speed) {
	
	for (int i=0; i<flowerList.size(); i++) {
		Flower monster = flowerList .get(i);
		
		monster.speed = speed;

		
	}
	
	for (int i=0; i<mushroomList.size(); i++) {
		Mushroom monster = mushroomList  .get(i);
		monster.speed = speed;
	}
	
	for (int i=0; i<pigList.size(); i++) {
		Pig monster = pigList.get(i);
		monster.speed = speed;
	}
	
	for (int i=0; i<treeList.size(); i++) {
		Tree monster = treeList.get(i);
		monster.speed = speed;
	}
	
	for (int i=0; i<dragonList.size(); i++) {
		Dragon monster = dragonList.get(i);
		monster.speed = speed;
	}
	
}
	

}
