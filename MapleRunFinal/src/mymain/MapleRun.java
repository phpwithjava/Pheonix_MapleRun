package mymain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import champ.Bullet_Manager;
import champ.ChampImageManager;
import champ.ChampSelectManager;
import champ.Champ_Move_Manager;
import myeffect.EffectManager;
import myitem.ItemCollisionManager;
import myitem.ItemManager;
import myitem.Item_Property;
import mymonsters.Mob_Property;
import mymonsters.MonsterAnimationManager;
import mymonsters.MonsterCollisionManager;
import mymonsters.MonsterManager;
import track.Map_Manager;

public class MapleRun extends JFrame {

	// Back_Ground Img
	Image img;

	// ���� ������
	JPanel gamePan;

	// ������
	JPanel scorePan = new JPanel((new BorderLayout()));

	// ü�°� ���� ��
	JProgressBar hp_bar = new JProgressBar();
	JProgressBar mp_bar = new JProgressBar();

	// ���ӳ��� ���¿��
	boolean game_start = false; // ���� ����
	boolean champ_choose = false; // è�Ǿ� ����ȭ��

	boolean game_playing = false; // ���� ����
	boolean game_clear = false; // ������ Ŭ���� �ߴ°�
	boolean game_death = false; // �׾��°�
	boolean game_over = false; // ������ ���´°�
	boolean game_next = false; // ����ȭ������

	boolean drop = false; // ���� �������°�
	boolean track_drop = false; // �����̿� �����°�
	boolean attacked = false; // ��������
	boolean skill_cool = false;
	boolean slide = false;
	boolean top_trap = false;
	boolean play_bgm = false;
	boolean pause_bgm = false;

	int my_champ = 1; // ���õ� è�Ǿ�
	int stage = 0; // ���� ��������
	int max_stage = 2;

	// ���ھ��� ����
	public static int score = 0; // ����
	public static double hp = 100; // ü��
	public static double mp = 100; // ����
	public static double max_mp = 100; // ���� max

	// ���ȭ�� �̵� ����
	double back_off_x = 0;

	// è�Ǿ��� ��� �ִ� ��ġ
	int ground = Common.GamePan.GROUND;

	// è�Ǿ� ���� �߷�
	public final double v_speed = 15; // �ʱ� ���� �ӵ�(��) (�������� ���� ����)
	public final double angle = 90; // �ʱ� ����
	public double g = 0.4; // �߷� ���ӵ� (�ö󰡰� �������� �ӵ� ����)
	public double h = 1000; // �ð�
	public double vy; // �߷� ����
	int jumped = 0; // ���� Ƚ��, �ִ� 2��(0, 1)
	// ���� ��Ʈ�ѷ�
	public static Clip clip;
	// è�Ǿ��� �ʱ� ��ġ
	Point mePt = new Point(150, ground); // ������ġ

	// ������ ����
	ItemManager im = new ItemManager();

	// ���� �Ŵ��� ����
	MonsterManager mm = new MonsterManager();

	// è�Ǿ� �Ŵ��� ����
	Champ_Move_Manager chm = new Champ_Move_Manager(mm, im);

	// ���� ȿ�� �Ŵ���
	EffectManager em = new EffectManager();

	// �Ѿ� �׸���
	Bullet_Manager bm = new Bullet_Manager();

	// ���� �ִϸ��̼�
	MonsterAnimationManager mam = new MonsterAnimationManager();

	// ������ ������
	ItemCollisionManager icm = new ItemCollisionManager(im);

	// è�Ǿ� ���� �̹���
	ChampSelectManager csm = new ChampSelectManager();

	// ���Ͷ� �δ�ĥ��
	MonsterCollisionManager mcm = new MonsterCollisionManager(mm, chm, em, bm, mam);

	// ���� ���� Ÿ�̸�
	Timer timer;
	Timer StopTrapTimer;
	Timer StopTrapTimer2;
	Timer StopTrapTimer3;

	public MapleRun() {

		// TODO Auto-generated constructor stub
		super("Maple Run!");

		// BGM Ʋ��
		play_bgm("rc/BGM/login_bgm.wav");

		// ���� �ʱ�ȭ
		init_gamePan();

		// ���� ����
		start_game();

		// Ű���� �̺�Ʈ
		init_key_event();

		// ������ â ��ġ
		setLocation(300, 200);

		// âũ�� ���� ����
		setResizable(false);

		// âũ��
		pack();

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	protected void start_game() {
		// TODO Auto-generated method stub

		// Ÿ�� �̹��� �ε�
		Map_Manager.last_track = 5000;
		img = Map_Manager.MapLoad(stage);

		// Draw_Tiles(); // Ÿ�� �׸���

		init_stage(); // ����, ������ �ʱ�ȭ

		init_timer(); // Ÿ�̸� ����
	}

	protected void restart_game() {
		// TODO Auto-generated method stub
		clip.start();

		// ���� �ʱ�ȭ
		Map_Manager.idx = 0;
		Map_Manager.trackidx = 0;
		mm.removeAll();
		im.removeAll();
		// �� �ʱ�ȭ
		if(stage==0)
			Map_Manager.last_track = 5000;
		else if(stage==1)
			Map_Manager.last_track = 8000;
		else
			Map_Manager.last_track = 17600;
		img = ImageManager.back_ground;

		// Ÿ�� �̹��� �ε�
		img = Map_Manager.MapLoad(stage);

		init_stage(); // ����, ������ �ʱ�ȭ

		init_timer(); // Ÿ�̸� ����
	}

	private void play_bgm(String fileName) {
		// TODO Auto-generated method stub

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();

		} catch (Exception ex) {

		}

	}

	private void init_stage() {
		// TODO Auto-generated method stub
		for (Mob_Property mob : Map_Manager.Mobs) {

			if (mob.mob_idx == 1)
				mm.make_flower(mob.mePt);
			else if (mob.mob_idx == 2)
				mm.make_Tree(mob.mePt);
			else if (mob.mob_idx == 3)
				mm.make_pig(mob.mePt);
			else if (mob.mob_idx == 4)
				mm.make_dragon(mob.mePt);
			else if (mob.mob_idx == 5)
				mm.make_mushroom(mob.mePt);
		}

		for (Item_Property item : Map_Manager.items) {
			if (item.idx == 1) {
				im.make_red_potion(item.mePt.x, item.mePt.y);
			} else if (item.idx == 2) {
				im.make_blue_potion(item.mePt.x, item.mePt.y);
			} else if (item.idx == 3) {
				im.make_bundle(item.mePt.x, item.mePt.y);
			} else if (item.idx == 4) {
				im.make_bill(item.mePt.x, item.mePt.y);
			} else if (item.idx == 5) {
				im.make_gold(item.mePt.x, item.mePt.y);
			} else if (item.idx == 6) {
				im.make_bronze(item.mePt.x, item.mePt.y);
			}
		}
	}

	// ���� �ʱ� ȭ��
	private void introScreen(Graphics g) {

		// ȭ�� �ʱ�ȭ
		g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.H);

		// ����ȭ�� �̹��� �׸���
		g.drawImage(ImageManager.start_image, 0, 0, null);

	}

	private void champ_choose_screen(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(ImageManager.back_ground, 0, 0, null);

		g.setColor(Color.white);
		Font font = new Font("����ü", Font.ITALIC, Common.GamePan.W / 15);
		g.setFont(font);
		g.drawString("è�Ǿ��� �����ϼ��� !", 180, 100);

		if (my_champ == 1) {
			g.drawImage(ChampImageManager.walk_img2[1], 500, 400 - chm.h, null);
			g.drawImage(ChampImageManager.walk_img3[1], 800, 400 - chm.h + 20, null);

			csm.draw(g, my_champ, 200, 320);
		}

		if (my_champ == 2) {
			g.drawImage(ChampImageManager.walk_img1[1], 200, 400 - chm.h, null);
			g.drawImage(ChampImageManager.walk_img3[1], 800, 400 - chm.h + 20, null);

			csm.draw(g, my_champ, 500, 320);

		}

		if (my_champ == 3) {
			g.drawImage(ChampImageManager.walk_img1[1], 200, 400 - chm.h, null);
			g.drawImage(ChampImageManager.walk_img2[1], 500, 400 - chm.h, null);

			csm.draw(g, my_champ, 800, 320);

		}

	}

	// �¸� ȭ��
	private void game_win_screen(Graphics g) {
		// �¸�ȭ�� �׸���
		g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.W);
		g.drawImage(ImageManager.gameclear, 0, 0, null);

		Font font1 = new Font("�������", Font.BOLD, 30);

		String health_score = String.format("ü�� ���ʽ� :  %d (hp : %d)", (int) (hp * 100), (int) hp);
		String crrent_score = String.format("�� �� �� ��   :  %d", score);
		String final_score = String.format("��  ��            :  %d", score + (int) (hp * 100));

		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString(health_score, 550, 270);
		g.drawString(crrent_score, 550, 340);
		g.drawString(final_score, 550, 410);
	}

	// ��Ŭ���� ȭ��
	protected void game_ending_screen(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.H);
		g.drawImage(ImageManager.ending, 0, 0, null);

		Font font1 = new Font("�������", Font.BOLD, 30);

		String health_score = String.format("ü�� ���ʽ� :  %d (hp : %d)", (int) (hp * 100), (int) hp);
		String crrent_score = String.format("�� �� �� ��   :  %d", score);
		String final_score = String.format("�� �� �� ��   :  %d", score + (int) (hp * 100));

		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString(health_score, 550, 270);
		g.drawString(crrent_score, 550, 340);
		g.drawString(final_score, 550, 410);
	}

	// �й�ȭ��
	private void game_death_screen(Graphics g) {
		// TODO Auto-generated method stub

		// �й�ȭ�� �׸���
		g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.H);
		g.drawImage(ImageManager.gameover, 0, 0, null);

	}

	private void init_key_event() {
		// TODO Auto-generated method stub

		KeyAdapter adapter = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				// ���� ���� & �Ͻ����� & ����
				if (key == KeyEvent.VK_ENTER) {

					// �ʱ�ȭ���̶��
					if (game_start == false) {
						// è�Ǿ� ����ȭ������ ����.
						game_start = true;

					}

					else if (game_start == true && champ_choose == false) {
						champ_choose = true;
						play_bgm = false;
					}

					// ���� �Ͻ�����, ����
					if (game_start == true && champ_choose == true) {
						// ����ȭ������ ����
						if (game_clear || game_over || game_death) {
							game_next = true;
							play_bgm = false;
						} else
							game_playing = !game_playing;
						if (!timer.isRunning())
							timer.start();
					}

				}

				// Jump : 2������ ����
				if (key == KeyEvent.VK_SPACE) {
					if (jumped < 2) {
						h = 3;
						jumped++;
					}
				}

				// è�Ǿ� ����
				if (game_start == true && champ_choose == false) {
					if (key == KeyEvent.VK_RIGHT) {
						my_champ++;
						if (my_champ >= 4)
							my_champ = 3;
					}

					if (key == KeyEvent.VK_LEFT) {
						my_champ--;
						if (my_champ <= 0)
							my_champ = 1;
					}
				}

				if (key == KeyEvent.VK_C) {
					System.out.println("game_start : " + game_start);
					System.out.println("champ_choose : " + champ_choose);
					System.out.println("game_playing : " + game_playing);
					System.out.println("game_clear : " + game_clear);
					System.out.println("game_death : " + game_death);
					System.out.println("game_over : " + game_over);
					System.out.println("drop : " + drop);
					System.out.println("track_drop : " + track_drop);
					System.out.println("hp : " + hp);
					System.out.println("mePt.y : " + mePt.y);
					System.out.println("stage : " + stage);
					System.out.println("game_next : " + game_next);
					System.out.println("--------------------------------");

				}

				// Q ������ ��ų ���
				if (key == KeyEvent.VK_Q) {
					if (skill_cool == true) {
						chm.skill();
						mp = 0;
						skill_cool = false;
						clip.stop();
					}

				}
				// W ������ Slide Motion
				if (key == KeyEvent.VK_W) {
					if (jumped == 0) {
						if (attacked == true)
							chm.champ_opacity(champ.Common.Champ.HIT_SLIDE);
						else
							chm.champ_opacity(champ.Common.Champ.SLIDE);

						chm.timer.stop();
						slide = true;
						chm.bFire = false;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				// W ���� Slide Motion ������ �ٽ� Walk Motion
				if (key == KeyEvent.VK_W) {
					if (attacked == true)
						chm.champ_opacity(champ.Common.Champ.HIT);
					else
						chm.champ_opacity(champ.Common.Champ.NONE);
					slide = false;
				}
			}

		};
		this.addKeyListener(adapter);
	}

	private void init_timer() {
		// TODO Auto-generated method stub

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// ����(��ġ, ���� ����)
				process();

				// �ٽñ׸���
				gamePan.repaint();
			}
		};

		timer = new Timer(15, listener);
		timer.start();

	}

	protected void process() {
		// TODO Auto-generated method stub

		// ���� ����
		move_background();

		// ü�� �Ҹ�
		init_hp();

		// ���� �Ҹ�
		init_mp();

		// �ð����� ���� ���� ����
		get_score();

		// è�Ǿ� ����
		move_position();

		// �Ѿ� �߻��� true/false ���ο� ���� �Ѿ� �׷���
		if (my_champ == 2) {
			if (chm.bFire == true) {
				bm.makeBullet(mePt);
			}
		}

		// ü��, ����â ������Ʈ
		hp_bar.setValue((int) hp);
		mp_bar.setValue((int) mp);

		// è�Ǿ� ���� ������Ʈ
		attacked = mcm.attacked;

		// ���� �̺�Ʈ ����
		for (Mob_Property mob : Map_Manager.Mobs) {

			if (mob.mob_idx == 1)
				mcm.collision_flower(mePt);
			else if (mob.mob_idx == 2)
				mcm.collision_tree(mePt);
			else if (mob.mob_idx == 3)
				mcm.collision_pig(mePt);
			else if (mob.mob_idx == 4)
				mcm.collision_dragon(mePt);
			else if (mob.mob_idx == 5)
				mcm.collision_mushroom(mePt);
		}

		icm.collision_coin_bill(mePt);
		icm.collision_coin_bronze(mePt);
		icm.collision_coin_bundle(mePt);
		icm.collision_coin_gold(mePt);

		icm.collision_item_redpotion(mePt);
		icm.collision_item_bluepotion(mePt);

	}

	private void init_mp() {
		// max_mp = 100
		// mp �ڵ� ������
		if (mp >= 0 && mp < max_mp) {
			mp += 0.2;
		} else {
			mp = max_mp;
			skill_cool = true;
		}
	}

	private void move_position() {
		// TODO Auto-generated method stub

		// è�Ǿ� �߷� �̿��� ���� ����
		if (drop == true && jumped == 0 && track_drop == false) {
			h = 30;
			track_drop = true;

		}

		h += 1.8;
		vy = -(v_speed * Math.sin(angle * Math.PI / 180) - g * h);
		mePt.y += vy;

		// è�Ǿ��� ���� ������ ���� �ʱ�ȭ
		if (mePt.y >= ground) {

			mePt.y = ground;
			jumped = 0;
		}

		// �ٽ� �׸���
		chm.make_champ_move(mePt.x, mePt.y, my_champ);
	}

	int score_interval = 10; // �ð��� ȹ�� ���� ����

	private void get_score() {
		// TODO Auto-generated method stub

		if (!game_playing)
			return;

		score_interval--;
		if (score_interval <= 0) {
			score += 10;
			score_interval = 10;
		}

	}

	int hp_interval = 10; // ü�� ���� �ð� ����

	private void init_hp() {
		// TODO Auto-generated method stub

		if (!champ_choose)
			return;

		hp_interval--;
		if (hp_interval <= 0 && game_playing == true) {
			hp -= 0.5;
			hp_interval = 10;
		}

		if (hp <= 0) {
			hp = 0;
		}

		if (hp >= 100) {
			hp = 100;
		}

	}

	private void move_background() {
		// TODO Auto-generated method stub

		// �������� �����ֱ�
		if (!champ_choose)
			return;

		if ((back_off_x + Common.GamePan.W) > Map_Manager.last_track + 100) {
			mePt.x += Common.Game_Balance.SPEED;
			chm.make_champ_move(mePt.x, mePt.y, my_champ);

		}

		else
			back_off_x += Common.Game_Balance.SPEED;

		falling_trap();
		trap();
	}

	private void trap() {
		// TODO Auto-generated method stub
		if (!slide) {
			if (top_trap == false && Map_Manager.Modify_Track((int) back_off_x + chm.w + mePt.x - 150, mePt.y)
					&& attacked == false) {
				hp -= 10;
				attacked = true;
				top_trap = true;
				chm.champ_opacity(champ.Common.Champ.COLLISION);
				Stop_Trap();
			}
		}
	}

	private void Stop_Trap() {

		StopTrapTimer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chm.champ_opacity(champ.Common.Champ.HIT);
				Stop_Trap2();
			}
		});
		StopTrapTimer.start();

	}

	private void Stop_Trap2() {
		StopTrapTimer.stop();
		StopTrapTimer2 = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				chm.champ_opacity(champ.Common.Champ.HIT);
				Stop_Trap3();
			}
		});
		StopTrapTimer2.start();
	}

	private void Stop_Trap3() {
		StopTrapTimer2.stop();
		StopTrapTimer3 = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				chm.champ_opacity(champ.Common.Champ.HIT);
				Stop_Trap_Final();
			}
		});
		StopTrapTimer3.start();
	}

	private void Stop_Trap_Final() {
		StopTrapTimer3.stop();
		chm.champ_opacity(champ.Common.Champ.NONE);
		attacked = false;
		top_trap = false;
	}

	private void falling_trap() {
		// TODO Auto-generated method stub

		// �����̸� ��������
		if ((Map_Manager.Modify_Drop((int) back_off_x + chm.w + mePt.x - 150)) && attacked == false) {
			drop = true;
			ground = 1000;

		}
		// ������ ���¿��� Ż��������
		else if (!(Map_Manager.Modify_Drop((int) back_off_x + chm.w + mePt.x - 150))
				&& mePt.y < Common.GamePan.GROUND) {
			drop = false;
			ground = Common.GamePan.GROUND;
			track_drop = false;
		}

	}

	private void init_gamePan() {
		// TODO Auto-generated method stub

		gamePan = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub

				// ���� ���� ȭ��
				if (game_start == false && champ_choose == false) {

					introScreen(g);
					// ������ ���� �ȵǸ� ���� ���
				}

				// è�Ǿ� ����ȭ��
				else if (game_start == true && champ_choose == false) {

					champ_choose_screen(g);

				}

				// ���� ȭ��
				else if (game_start == true && champ_choose == true) {

					// ������ ���۵Ǿ���� ü��, �����ٰ� ����
					scorePan.setVisible(true);

					g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.H);

					g.drawImage(img, 0, 0, Common.GamePan.W, Common.GamePan.H, (int) back_off_x, 0,
							(int) back_off_x + Common.GamePan.W, Common.GamePan.H, this);

					// ������ �׸���
					im.draw_all(g);

					// ���� �׸���
					mm.draw_all(g);

					// è�Ǿ� �׸���
					chm.draw(g);

					// ȿ�� �׸���
					em.draw(g);

					// 2�� è�Ǿ� ��ų = �Ѿ� �׸���
					bm.draw_all(g);

					// ���� �ִϸ��̼�
					mam.draw(g);

					// ȭ�鿡 ���� ���
					String alive_score_s = String.format("SCORE:%d", score);
					Font font1 = new Font("����ü", Font.BOLD, 20);
					g.setFont(font1);
					g.setColor(Color.white);
					g.drawString(alive_score_s, (Common.GamePan.W / 7) * 6, Common.GamePan.H / 20);

					if (play_bgm == false) {
						clip.stop();
						play_bgm("rc/BGM/game_bgm.wav");
						play_bgm = true;
					}

					// ȭ�鿡 ���罺������ ���
					String crrent_stage;
					if (stage == 0) {
						crrent_stage = String.format("Tutorial");
					} else if (stage == max_stage) {
						crrent_stage = String.format("Last Stage!!!");
					} else {
						crrent_stage = String.format("Stage : %s ", stage);
					}

					Font font3 = new Font("���ü", Font.BOLD, 30);
					g.setFont(font3);
					g.setColor(Color.white);
					g.drawString(crrent_stage, 20, Common.GamePan.H / 20);

					// �Ͻ� ����
					if (game_playing == false) {
						String game_break = String.format("�� �� �� ��");
						Font font2 = new Font("����ü", Font.BOLD, 100);
						g.setFont(font2);
						g.setColor(Color.black);
						g.drawString(game_break, Common.GamePan.W / 4, Common.GamePan.H / 2);
						timer.stop();
					}

					if (mePt.x >= Common.GamePan.W) {
						if (stage == max_stage)
							game_over = true;
						else
							game_clear = true;

					}

					if ((hp <= 0 || mePt.y - chm.h > Common.GamePan.H)) {
						game_death = true;
					}
				}

				// ���� �¸�
				if (game_clear == true) {
					clip.stop();
					scorePan.setVisible(false);
					game_playing = false;
					game_win_screen(g);
					// enter�� ����� ���� �����
					if (game_next == true) {
						game_continue();
						game_next = false;
					}

					return;
				}

				// ���� �й�
				if (game_death == true) {
					clip.stop();
					scorePan.setVisible(false);
					game_playing = false;
					game_death_screen(g);
					// enter�� ����� ���� �����
					if (game_next == true) {
						game_continue();
						game_next = false;
					}

					return;
				}

				// ���� ��Ŭ����
				if (game_over == true) {
					clip.stop();
					scorePan.setVisible(false);
					game_playing = false;
					game_ending_screen(g);
					// enter�� ����� ���� �����
					if (game_next == true) {
						game_continue();
						game_next = false;
					}

					return;

				}

			}
		};

		hp_bar.setForeground(Color.red);
		mp_bar.setForeground(Color.blue);

		scorePan.add(mp_bar, "West");
		scorePan.add(hp_bar, "Center");
		gamePan.add(scorePan, "North");
		scorePan.setVisible(false);
		this.add(gamePan);

		gamePan.setPreferredSize(new Dimension(Common.GamePan.W, Common.GamePan.H));
	}

	protected void game_continue() {
		// TODO Auto-generated method stub
		// �ʱ�ȭ
		drop = false; // ���� �������°�
		track_drop = false; // �����̿� �����°�
		attacked = false; // ��������

		// è�Ǿ� ��ġ, �� �ʱ�ȭ
		back_off_x = 0;
		ground = Common.GamePan.GROUND;
		mePt = new Point(150, ground);

		// �׾ �����ٸ�?
		if (game_death == true) {
			// chm.timer.stop(); //��ų ���� �ִٸ� ���߱�
			game_start = false; // ���� ���� ����, �� ó������
			champ_choose = false; // è�Ǿ� ����ȭ�� �ʱ�ȭ

			hp = 100; // ü�� �ʱ�ȭ
			my_champ = 1; // è�Ǿ� �ʱ⼱��
			score = 0; // ���� �ʱ�ȭ
			game_death = false;
			stage = 0;
		}
		// Ŭ��� �ߴٸ�?
		else if (game_clear == true) {
			// chm.timer.stop(); //��ų ���� �ִٸ� ���߱�
			stage++; // �������� ����
			score = score + (int) (hp * 100);
			hp = 100;
			game_clear = false; // ����Ŭ���� ����
			game_playing = true; // �����÷��� ���
		}
		// ������ ���� Ŭ���� �ߴٸ�?
		else if (game_over == true) {
			// chm.timer.stop(); //��ų ���� �ִٸ� ���߱�
			game_start = false; // ���� ���� �ʱ�ȭ
			champ_choose = false; // è�Ǿ� ����ȭ�� �ʱ�ȭ
			my_champ = 1; // è�Ǿ�
			score = 0;
			stage = 0;
			game_over = false; // ���ӳ����� �ʱ�ȭ

		}

		restart_game();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MapleRun();
	}
}
