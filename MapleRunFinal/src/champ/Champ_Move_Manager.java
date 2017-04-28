package champ;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

import myitem.ItemManager;
import mymain.MapleRun;
import mymonsters.MonsterManager;

public class Champ_Move_Manager {

	Champ1_move champ_move1 = new Champ1_move();
	Champ2_move champ_move2 = new Champ2_move();
	Champ3_move champ_move3 = new Champ3_move();	
	
	MonsterManager mm;
	ItemManager im;
	Clip clip;
	
	public Champ_Move_Manager() {
		super();
	}

	public Champ_Move_Manager(MonsterManager mm, ItemManager im) {
		super();
		this.mm = mm;
		this.im = im;
	}

	public static int w=80;
	public static int h=80;
	// 총알 발사 여부 확인
	public boolean bFire = false;
	
	public int champ;
	
	public void play_bgm(String fileName) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
			timer.start();

		} catch (Exception ex) {

		}

	}
	
	public void make_champ_move(int x, int y, int champ) {
		
		this.champ = champ;
		
		if (champ == 1) {
			champ_move1.x = x;
			champ_move1.y = y+10 - champ_move1.h;
		}
		
		else if (champ == 2) {
			champ_move2.x = x;
			champ_move2.y = y - champ_move1.h;
		}
		
		else if (champ == 3) {
			champ_move3.x = x;
			champ_move3.y = y - champ_move1.h + 20;
		}
	}
	
	public void champ_opacity(int state) {
		
		if (champ == 1) {
			champ_move1.state = state;
		}
		
		else if (champ == 2) {
			champ_move2.state = state;
		}
		
		else if (champ == 3) {
			champ_move3.state = state;
		}
	}

	public void skill() {
		
		if (champ == 1) {
			play_bgm("rc/BGM/Reinhardt.wav");
			clip.start();
		} else if (champ == 2) {
			play_bgm("rc/BGM/Mccree.wav");
			clip.start();
		} else if (champ == 3) {
			mymain.Common.Game_Balance.SPEED = 15;
			mm.speed_adj(mymain.Common.Game_Balance.SPEED);
			im.speed_adj(mymain.Common.Game_Balance.SPEED);
			
			play_bgm("rc/BGM/Ripper.wav");
			clip.start();
		}
		
		champ_opacity(Common.Champ.SKILL);
		
		timer.start();
		bFire = true;
	}
	public Timer timer = new Timer(3000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			MapleRun.clip.start();
			
			timer_1.start();
			timer.stop();
			
		}
	});
	
	public Timer timer_1 = new Timer(2000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			champ_opacity(Common.Champ.NONE);
			mymain.Common.Game_Balance.SPEED = 7;
			mm.speed_adj(mymain.Common.Game_Balance.SPEED);
			im.speed_adj(mymain.Common.Game_Balance.SPEED);
			bFire=false;
			clip.stop();
			timer_1.stop();
			
			
			
		}
	});

	public void draw(Graphics g) {
	
		if (champ == 1) {
			champ_move1.draw(g);
			champ_move1.move();
		}
		
		else if (champ == 2) {
			champ_move2.draw(g);
			champ_move2.move();
		}
		
		else if (champ == 3) {
			champ_move3.draw(g);
			champ_move3.move();
		}
		
	}
	
}
	


