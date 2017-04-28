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

	// 메인 게임판
	JPanel gamePan;

	// 점수판
	JPanel scorePan = new JPanel((new BorderLayout()));

	// 체력과 마나 바
	JProgressBar hp_bar = new JProgressBar();
	JProgressBar mp_bar = new JProgressBar();

	// 게임내의 상태요소
	boolean game_start = false; // 게임 시작
	boolean champ_choose = false; // 챔피언 선택화면

	boolean game_playing = false; // 게임 진행
	boolean game_clear = false; // 게임을 클리어 했는가
	boolean game_death = false; // 죽었는가
	boolean game_over = false; // 게임을 끝냈는가
	boolean game_next = false; // 다음화면으로

	boolean drop = false; // 땅에 떨어졌는가
	boolean track_drop = false; // 구덩이에 빠졌는가
	boolean attacked = false; // 무적상태
	boolean skill_cool = false;
	boolean slide = false;
	boolean top_trap = false;
	boolean play_bgm = false;
	boolean pause_bgm = false;

	int my_champ = 1; // 선택된 챔피언
	int stage = 0; // 진행 스테이지
	int max_stage = 2;

	// 스코어판 변수
	public static int score = 0; // 점수
	public static double hp = 100; // 체력
	public static double mp = 100; // 마나
	public static double max_mp = 100; // 마나 max

	// 배경화면 이동 변수
	double back_off_x = 0;

	// 챔피언이 밟고 있는 위치
	int ground = Common.GamePan.GROUND;

	// 챔피언 점프 중력
	public final double v_speed = 15; // 초기 점프 속도(힘) (낮을수록 낮게 점프)
	public final double angle = 90; // 초기 각도
	public double g = 0.4; // 중력 가속도 (올라가고 떨어지는 속도 조절)
	public double h = 1000; // 시간
	public double vy; // 중력 공식
	int jumped = 0; // 점프 횟수, 최대 2번(0, 1)
	// 음악 컨트롤러
	public static Clip clip;
	// 챔피언의 초기 위치
	Point mePt = new Point(150, ground); // 기존위치

	// 아이템 생성
	ItemManager im = new ItemManager();

	// 몬스터 매니저 생성
	MonsterManager mm = new MonsterManager();

	// 챔피언 매니저 생성
	Champ_Move_Manager chm = new Champ_Move_Manager(mm, im);

	// 각종 효과 매니저
	EffectManager em = new EffectManager();

	// 총알 그리기
	Bullet_Manager bm = new Bullet_Manager();

	// 몬스터 애니매이션
	MonsterAnimationManager mam = new MonsterAnimationManager();

	// 아이템 먹을때
	ItemCollisionManager icm = new ItemCollisionManager(im);

	// 챔피언 선택 이미지
	ChampSelectManager csm = new ChampSelectManager();

	// 몬스터랑 부닥칠때
	MonsterCollisionManager mcm = new MonsterCollisionManager(mm, chm, em, bm, mam);

	// 게임 진행 타이머
	Timer timer;
	Timer StopTrapTimer;
	Timer StopTrapTimer2;
	Timer StopTrapTimer3;

	public MapleRun() {

		// TODO Auto-generated constructor stub
		super("Maple Run!");

		// BGM 틀기
		play_bgm("rc/BGM/login_bgm.wav");

		// 게임 초기화
		init_gamePan();

		// 게임 시작
		start_game();

		// 키보드 이벤트
		init_key_event();

		// 윈도우 창 위치
		setLocation(300, 200);

		// 창크기 조절 금지
		setResizable(false);

		// 창크기
		pack();

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	protected void start_game() {
		// TODO Auto-generated method stub

		// 타일 이미지 로드
		Map_Manager.last_track = 5000;
		img = Map_Manager.MapLoad(stage);

		// Draw_Tiles(); // 타일 그리기

		init_stage(); // 몬스터, 아이템 초기화

		init_timer(); // 타이머 가동
	}

	protected void restart_game() {
		// TODO Auto-generated method stub
		clip.start();

		// 함정 초기화
		Map_Manager.idx = 0;
		Map_Manager.trackidx = 0;
		mm.removeAll();
		im.removeAll();
		// 맵 초기화
		if(stage==0)
			Map_Manager.last_track = 5000;
		else if(stage==1)
			Map_Manager.last_track = 8000;
		else
			Map_Manager.last_track = 17600;
		img = ImageManager.back_ground;

		// 타일 이미지 로드
		img = Map_Manager.MapLoad(stage);

		init_stage(); // 몬스터, 아이템 초기화

		init_timer(); // 타이머 가동
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

	// 실행 초기 화면
	private void introScreen(Graphics g) {

		// 화면 초기화
		g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.H);

		// 시작화면 이미지 그리기
		g.drawImage(ImageManager.start_image, 0, 0, null);

	}

	private void champ_choose_screen(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(ImageManager.back_ground, 0, 0, null);

		g.setColor(Color.white);
		Font font = new Font("굴림체", Font.ITALIC, Common.GamePan.W / 15);
		g.setFont(font);
		g.drawString("챔피언을 선택하세요 !", 180, 100);

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

	// 승리 화면
	private void game_win_screen(Graphics g) {
		// 승리화면 그리기
		g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.W);
		g.drawImage(ImageManager.gameclear, 0, 0, null);

		Font font1 = new Font("맑은고딕", Font.BOLD, 30);

		String health_score = String.format("체력 보너스 :  %d (hp : %d)", (int) (hp * 100), (int) hp);
		String crrent_score = String.format("기 존 점 수   :  %d", score);
		String final_score = String.format("합  계            :  %d", score + (int) (hp * 100));

		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString(health_score, 550, 270);
		g.drawString(crrent_score, 550, 340);
		g.drawString(final_score, 550, 410);
	}

	// 올클리어 화면
	protected void game_ending_screen(Graphics g) {
		// TODO Auto-generated method stub
		g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.H);
		g.drawImage(ImageManager.ending, 0, 0, null);

		Font font1 = new Font("맑은고딕", Font.BOLD, 30);

		String health_score = String.format("체력 보너스 :  %d (hp : %d)", (int) (hp * 100), (int) hp);
		String crrent_score = String.format("기 존 점 수   :  %d", score);
		String final_score = String.format("최 종 점 수   :  %d", score + (int) (hp * 100));

		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString(health_score, 550, 270);
		g.drawString(crrent_score, 550, 340);
		g.drawString(final_score, 550, 410);
	}

	// 패배화면
	private void game_death_screen(Graphics g) {
		// TODO Auto-generated method stub

		// 패배화면 그리기
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
				// 게임 시작 & 일시정지 & 진행
				if (key == KeyEvent.VK_ENTER) {

					// 초기화면이라면
					if (game_start == false) {
						// 챔피언 선택화면으로 간다.
						game_start = true;

					}

					else if (game_start == true && champ_choose == false) {
						champ_choose = true;
						play_bgm = false;
					}

					// 게임 일시정지, 해제
					if (game_start == true && champ_choose == true) {
						// 다음화면으로 진행
						if (game_clear || game_over || game_death) {
							game_next = true;
							play_bgm = false;
						} else
							game_playing = !game_playing;
						if (!timer.isRunning())
							timer.start();
					}

				}

				// Jump : 2단으로 제한
				if (key == KeyEvent.VK_SPACE) {
					if (jumped < 2) {
						h = 3;
						jumped++;
					}
				}

				// 챔피언 선택
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

				// Q 누를시 스킬 사용
				if (key == KeyEvent.VK_Q) {
					if (skill_cool == true) {
						chm.skill();
						mp = 0;
						skill_cool = false;
						clip.stop();
					}

				}
				// W 누를시 Slide Motion
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
				// W 떼면 Slide Motion 끝내고 다시 Walk Motion
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
				// 연산(위치, 상태 변경)
				process();

				// 다시그리기
				gamePan.repaint();
			}
		};

		timer = new Timer(15, listener);
		timer.start();

	}

	protected void process() {
		// TODO Auto-generated method stub

		// 게임 진행
		move_background();

		// 체력 소모
		init_hp();

		// 마나 소모
		init_mp();

		// 시간지날 수록 점수 증가
		get_score();

		// 챔피언 점프
		move_position();

		// 총알 발사의 true/false 여부에 따라 총알 그려줌
		if (my_champ == 2) {
			if (chm.bFire == true) {
				bm.makeBullet(mePt);
			}
		}

		// 체력, 마나창 업데이트
		hp_bar.setValue((int) hp);
		mp_bar.setValue((int) mp);

		// 챔피언 상태 업데이트
		attacked = mcm.attacked;

		// 몬스터 이벤트 생성
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
		// mp 자동 증가량
		if (mp >= 0 && mp < max_mp) {
			mp += 0.2;
		} else {
			mp = max_mp;
			skill_cool = true;
		}
	}

	private void move_position() {
		// TODO Auto-generated method stub

		// 챔피언 중력 이용한 점프 공식
		if (drop == true && jumped == 0 && track_drop == false) {
			h = 30;
			track_drop = true;

		}

		h += 1.8;
		vy = -(v_speed * Math.sin(angle * Math.PI / 180) - g * h);
		mePt.y += vy;

		// 챔피언이 땅에 닿으면 점프 초기화
		if (mePt.y >= ground) {

			mePt.y = ground;
			jumped = 0;
		}

		// 다시 그리기
		chm.make_champ_move(mePt.x, mePt.y, my_champ);
	}

	int score_interval = 10; // 시간당 획득 점수 조절

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

	int hp_interval = 10; // 체력 감소 시간 조절

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

		// 시작전엔 멈춰있기
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

		// 구덩이를 만났을때
		if ((Map_Manager.Modify_Drop((int) back_off_x + chm.w + mePt.x - 150)) && attacked == false) {
			drop = true;
			ground = 1000;

		}
		// 구덩이 상태에서 탈출했을때
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

				// 게임 시작 화면
				if (game_start == false && champ_choose == false) {

					introScreen(g);
					// 게임이 시작 안되면 무한 대기
				}

				// 챔피언 선택화면
				else if (game_start == true && champ_choose == false) {

					champ_choose_screen(g);

				}

				// 게임 화면
				else if (game_start == true && champ_choose == true) {

					// 게임이 시작되어야지 체력, 마나바가 보임
					scorePan.setVisible(true);

					g.clearRect(0, 0, Common.GamePan.W, Common.GamePan.H);

					g.drawImage(img, 0, 0, Common.GamePan.W, Common.GamePan.H, (int) back_off_x, 0,
							(int) back_off_x + Common.GamePan.W, Common.GamePan.H, this);

					// 아이템 그리기
					im.draw_all(g);

					// 몬스터 그리기
					mm.draw_all(g);

					// 챔피언 그리기
					chm.draw(g);

					// 효과 그리기
					em.draw(g);

					// 2번 챔피언 스킬 = 총알 그리기
					bm.draw_all(g);

					// 몬스터 애니매이션
					mam.draw(g);

					// 화면에 점수 출력
					String alive_score_s = String.format("SCORE:%d", score);
					Font font1 = new Font("굴림체", Font.BOLD, 20);
					g.setFont(font1);
					g.setColor(Color.white);
					g.drawString(alive_score_s, (Common.GamePan.W / 7) * 6, Common.GamePan.H / 20);

					if (play_bgm == false) {
						clip.stop();
						play_bgm("rc/BGM/game_bgm.wav");
						play_bgm = true;
					}

					// 화면에 현재스테이지 출력
					String crrent_stage;
					if (stage == 0) {
						crrent_stage = String.format("Tutorial");
					} else if (stage == max_stage) {
						crrent_stage = String.format("Last Stage!!!");
					} else {
						crrent_stage = String.format("Stage : %s ", stage);
					}

					Font font3 = new Font("고딕체", Font.BOLD, 30);
					g.setFont(font3);
					g.setColor(Color.white);
					g.drawString(crrent_stage, 20, Common.GamePan.H / 20);

					// 일시 정지
					if (game_playing == false) {
						String game_break = String.format("일 시 정 지");
						Font font2 = new Font("굴림체", Font.BOLD, 100);
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

				// 게임 승리
				if (game_clear == true) {
					clip.stop();
					scorePan.setVisible(false);
					game_playing = false;
					game_win_screen(g);
					// enter를 누루면 게임 재시작
					if (game_next == true) {
						game_continue();
						game_next = false;
					}

					return;
				}

				// 게임 패배
				if (game_death == true) {
					clip.stop();
					scorePan.setVisible(false);
					game_playing = false;
					game_death_screen(g);
					// enter를 누루면 게임 재시작
					if (game_next == true) {
						game_continue();
						game_next = false;
					}

					return;
				}

				// 게임 올클리어
				if (game_over == true) {
					clip.stop();
					scorePan.setVisible(false);
					game_playing = false;
					game_ending_screen(g);
					// enter를 누루면 게임 재시작
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
		// 초기화
		drop = false; // 땅에 떨어졌는가
		track_drop = false; // 구덩이에 빠졌는가
		attacked = false; // 무적상태

		// 챔피언 위치, 맵 초기화
		back_off_x = 0;
		ground = Common.GamePan.GROUND;
		mePt = new Point(150, ground);

		// 죽어서 끝났다면?
		if (game_death == true) {
			// chm.timer.stop(); //스킬 쓰고 있다면 멈추기
			game_start = false; // 게임 시작 해제, 즉 처음부터
			champ_choose = false; // 챔피언 선택화면 초기화

			hp = 100; // 체력 초기화
			my_champ = 1; // 챔피언 초기선택
			score = 0; // 점수 초기화
			game_death = false;
			stage = 0;
		}
		// 클리어를 했다면?
		else if (game_clear == true) {
			// chm.timer.stop(); //스킬 쓰고 있다면 멈추기
			stage++; // 스테이지 증가
			score = score + (int) (hp * 100);
			hp = 100;
			game_clear = false; // 게임클리어 해제
			game_playing = true; // 게임플레이 계속
		}
		// 게임을 전부 클리어 했다면?
		else if (game_over == true) {
			// chm.timer.stop(); //스킬 쓰고 있다면 멈추기
			game_start = false; // 게임 시작 초기화
			champ_choose = false; // 챔피언 선택화면 초기화
			my_champ = 1; // 챔피언
			score = 0;
			stage = 0;
			game_over = false; // 게임끝난거 초기화

		}

		restart_game();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MapleRun();
	}
}
