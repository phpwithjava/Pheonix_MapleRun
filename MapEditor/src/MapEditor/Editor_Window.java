package MapEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import myitem.Item_Property;
import mymonsters.Mob_Property;
import track.Data;
import track.Tile_Property;

@SuppressWarnings("serial")
public class Editor_Window extends JFrame {
	JButton jbtn_Track_1, jbtn_Track_2, jbtn_Track_3, jbtn_Tile_1, jbtn_Tile_2, deselect; // 타일
	JButton flower_mob, pig_mob, dragon, tree_mob, mushroom_mob; // 몹
	JButton red_potion, blue_potion, bundle_coin, bill_coin, gold_coin, bronze_coin; // 아이템
	JButton delete_obj;
	public ArrayList<Tile_Property> tiles = new ArrayList<Tile_Property>();
	public List<Tile_Property> Tracks = new ArrayList<Tile_Property>();
	public List<Mob_Property> Mobs = new ArrayList<Mob_Property>();
	public List<Item_Property> items = new ArrayList<Item_Property>();
	JFrame me; // 버튼
	JLabel jlabel1; // 타일 선택 패널 내의 라벨
	JLabel jlabel2; // 타일 선택 패널 내의 라벨
	JLabel jlabel3;
	ActionListener Listener;
	JPanel jp; // 타일 선택 패널
	Tile_Property tile;
	JScrollBar scroller;
	JPanel Map; // 맵패널
	JPanel mobBox; // 몹 정보들
	JPanel itemBox;
	Image background;
	Image saveback;
	JSlider slider;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem Load = new JMenuItem("Map Load");
	JMenuItem Save = new JMenuItem("Map Save");
	// 타일 선택을 확인
	boolean selected = false;
	boolean delete = false;
	// 맵을 움직이는 좌표
	double back_off_x = 0;
	// 선택한 타일을 움직이는 좌표
	int x = 0, y = 0;

	BufferedImage bimg;
	Image img_tile;

	public Editor_Window() {
		super("MapEditor");
		me = this;
		Dimension dis = Toolkit.getDefaultToolkit().getScreenSize();
		background = Tile_Image_Manager.background;
		setSize(1200, 550);
		setVisible(true);
		setResizable(false);
		setLayout(new BorderLayout());
		setLocation(dis.width / 2 - 600, dis.height / 2 - 225);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Init_JButton_Listener();
		Init_Controls();
		Init_Map();
		Init_Listener();
		pack();
	}

	private void Init_Map() {

		Map = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Map_Edit_Manager.Move_Map(g, back_off_x, background);

				if (selected == true) {
					Image img = null;

					Map_Edit_Manager.SelectedBlock(g, x, y, img_tile);
					repaint();

				}
			}
		};
		Map.setPreferredSize(new Dimension(1100, 550));
		this.add(Map, "Center");

	}

	private void Init_Controls() {

		tile = new Tile_Property(0, 0, 0, 0);

		jlabel1 = new JLabel(" Map 타일정보   ");
		jlabel1.setOpaque(true);
		jlabel1.setBackground(Color.DARK_GRAY);
		jlabel1.setForeground(Color.white);

		jlabel2 = new JLabel("     Mob정보     ");
		jlabel2.setOpaque(true);
		jlabel2.setBackground(Color.RED);
		jlabel2.setForeground(Color.white);

		jlabel3 = new JLabel("   Item정보     ");
		jlabel3.setOpaque(true);
		jlabel3.setBackground(Color.BLUE);
		jlabel3.setForeground(Color.white);

		jbtn_Track_1 = new JButton("트랙1");
		jbtn_Track_1.addActionListener(Listener);

		jbtn_Track_2 = new JButton("트랙2");
		jbtn_Track_2.addActionListener(Listener);
		jbtn_Track_3 = new JButton("트랙3");
		jbtn_Track_3.addActionListener(Listener);
		jbtn_Tile_1 = new JButton("타일1");
		jbtn_Tile_1.addActionListener(Listener);

		jbtn_Tile_2 = new JButton("타일2");
		jbtn_Tile_2.addActionListener(Listener);

		flower_mob = new JButton("꽃");
		flower_mob.addActionListener(Listener);

		tree_mob = new JButton("나무");
		tree_mob.addActionListener(Listener);

		dragon = new JButton("드래곤");
		dragon.addActionListener(Listener);

		pig_mob = new JButton("돼지");
		pig_mob.addActionListener(Listener);

		mushroom_mob = new JButton("버섯");
		mushroom_mob.addActionListener(Listener);

		red_potion = new JButton("빨간포션");
		red_potion.addActionListener(Listener);

		blue_potion = new JButton("파란포션");
		blue_potion.addActionListener(Listener);

		bundle_coin = new JButton("돈다발");
		bundle_coin.addActionListener(Listener);

		bill_coin = new JButton("지폐");
		bill_coin.addActionListener(Listener);

		gold_coin = new JButton("금색동전");
		gold_coin.addActionListener(Listener);

		bronze_coin = new JButton("동색 동전");
		bronze_coin.addActionListener(Listener);

		deselect = new JButton("선택안함");
		deselect.addActionListener(Listener);

		delete_obj = new JButton("삭제");
		delete_obj.addActionListener(Listener);
		;
		slider = new JSlider(JSlider.HORIZONTAL, 0, background.getWidth(null) - 1100, 1);
		menuBar = new JMenuBar();
		menu = new JMenu("파일");

		Load.addActionListener(Listener);
		Save.addActionListener(Listener);

		menu.add(Load);
		menu.add(Save);
		menuBar.add(menu);

		JPanel obj_box = new JPanel(new BorderLayout());
		jp = new JPanel(new GridLayout(7, 2));
		jp.add(jlabel1);
		jp.add(jbtn_Tile_1);
		jp.add(jbtn_Tile_2);
		jp.add(jbtn_Track_1);
		jp.add(jbtn_Track_2);
		jp.add(jbtn_Track_3);
		jp.add(deselect);

		mobBox = new JPanel(new GridLayout(7, 1));

		mobBox.add(jlabel2);
		mobBox.add(delete_obj);
		mobBox.add(flower_mob);
		mobBox.add(tree_mob);
		mobBox.add(pig_mob);
		mobBox.add(dragon);
		mobBox.add(mushroom_mob);
		itemBox = new JPanel(new GridLayout(7, 1));
		itemBox.add(jlabel3);
		itemBox.add(red_potion);
		itemBox.add(blue_potion);
		itemBox.add(bundle_coin);
		itemBox.add(bill_coin);
		itemBox.add(gold_coin);
		itemBox.add(bronze_coin);

		obj_box.setSize(200, 500);
		obj_box.add(jp, "West");

		obj_box.add(mobBox, "Center");
		obj_box.add(itemBox, "East");

		this.add(obj_box, "East");

		this.add(slider, "South");
		setJMenuBar(menuBar);
	}

	public void Init_JButton_Listener() {
		Listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				delete = false;
				if (obj == jbtn_Tile_1) {

					tile.tile_kind = 1;
					tile.idx = 1;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
					selected = true;

				} else if (obj == jbtn_Tile_2) {
					tile.tile_kind = 1;
					tile.idx = 2;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
					selected = true;

				} else if (obj == jbtn_Track_1) {
					tile.tile_kind = 0;
					tile.idx = 1;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
					selected = true;

				} else if (obj == jbtn_Track_2) {
					tile.tile_kind = 0;
					tile.idx = 2;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
					selected = true;

				} else if (obj == jbtn_Track_3) {
					tile.tile_kind = 0;
					tile.idx = 3;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
					selected = true;

				} else if (obj == deselect) {
					selected = false;

				} else if (obj == delete_obj) {
					selected = false;
					delete = true;

				} else if (obj == flower_mob) {
					tile.tile_kind = 2;
					tile.idx = 1;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);

				} else if (obj == tree_mob) {
					tile.tile_kind = 2;
					tile.idx = 2;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == pig_mob) {
					tile.tile_kind = 2;
					tile.idx = 3;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == dragon) {
					tile.tile_kind = 2;
					tile.idx = 4;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);

				} else if (obj == mushroom_mob) {
					tile.tile_kind = 2;
					tile.idx = 5;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);

				} else if (obj == red_potion) {
					tile.tile_kind = 3;
					tile.idx = 1;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == blue_potion) {
					tile.tile_kind = 3;
					tile.idx = 2;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == bundle_coin) {
					tile.tile_kind = 3;
					tile.idx = 3;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == bill_coin) {
					tile.tile_kind = 3;
					tile.idx = 4;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == gold_coin) {
					tile.tile_kind = 3;
					tile.idx = 5;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == bronze_coin) {
					tile.tile_kind = 3;
					tile.idx = 6;
					img_tile = Tile_Image_Manager.Load(tile.tile_kind, tile.idx);
				} else if (obj == Load) {
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("맵파일 로드");
					FileFilter filter = new FileNameExtensionFilter("게임 맵 파일", "map");
					chooser.addChoosableFileFilter(filter);
					int Loaded = chooser.showOpenDialog(me);

					if (Loaded == 0) {
						File file = chooser.getSelectedFile();
						try {

							FileInputStream fis = new FileInputStream(file);
							ObjectInputStream ois = new ObjectInputStream(fis);
							Data data = new Data();
							data = (Data) ois.readObject();
							tiles = data.tiles;
							fis.close();
							ois.close();
							ByteArrayInputStream bios = new ByteArrayInputStream(data.background);
							InputStream is = bios;
							BufferedImage bi = ImageIO.read(is);
							background = new ImageIcon(bi).getImage();
							saveback = new ImageIcon(bi).getImage();
							repaint();
							Tracks = data.Tracks;
							Mobs = data.Mobs;
							items = data.items;
							bios = new ByteArrayInputStream(data.background);
							is = bios;
							bi = ImageIO.read(is);
							saveback = new ImageIcon(bi).getImage();
							for(Mob_Property m:Mobs)
							{
								Image mob = Tile_Image_Manager.Load(2, m.mob_idx);
								System.out.println(mob.getWidth(null));
								background = Map_Edit_Manager.Draw_Tile(background, m.mePt.x, m.mePt.y, mob);
							
							}
							for(Item_Property i:items)
							{
								Image mob = Tile_Image_Manager.Load(3, i.idx);
								background = Map_Edit_Manager.Draw_Tile(background,i.mePt.x, i.mePt.y, mob);	
							}
							repaint();
							
						} catch (IOException | ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				} else if (obj == Save) {
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("맵파일 저장");
					FileFilter filter = new FileNameExtensionFilter("맵파일", "map");
					chooser.addChoosableFileFilter(filter);
					int Saved = chooser.showSaveDialog(me);
					if (Saved == 0) {
						File filename = chooser.getSelectedFile();

						try {
							FileOutputStream fos = new FileOutputStream(filename);
							ObjectOutputStream oos = new ObjectOutputStream(fos);

							BufferedImage bi = new BufferedImage(background.getWidth(null), background.getHeight(null),
									BufferedImage.TYPE_INT_ARGB);
							Graphics2D g = (Graphics2D) bi.getGraphics();
							g.drawImage(saveback, 0, 0, null);

							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ImageIO.write(bi, "jpg", baos);
							// 타일 내림차순 정렬
							Collections.sort(tiles, new Comparator<Tile_Property>() {
								@Override
								public int compare(Tile_Property o1, Tile_Property o2) {
									if (o1.x > o2.x)
										return 1;
									else if (o1.x < o2.x)
										return -1;
									return 0;
								}
							});
							// 트랙 내림차순 정렬
							Collections.sort(Tracks, new Comparator<Tile_Property>() {
								@Override
								public int compare(Tile_Property o1, Tile_Property o2) {
									if (o1.x > o2.x)
										return 1;
									else if (o1.x < o2.x)
										return -1;
									return 0;
								}
							});

							Data data = new Data();
							data.background = baos.toByteArray();
							data.items = items;
							data.Mobs = Mobs;
							data.Tracks = Tracks;
							BufferedImage bi2 = new BufferedImage(background.getWidth(null), background.getHeight(null),
									BufferedImage.TYPE_INT_ARGB);
							Graphics2D g2 = (Graphics2D) bi.getGraphics();
							g.drawImage(saveback, 0, 0, null);

							ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
							ImageIO.write(bi, "jpg", baos2);
							data.save = baos2.toByteArray();
							ArrayList<Tile_Property> Tiles = new ArrayList<Tile_Property>();
							for (Tile_Property t : tiles) {
								if (t.tile_kind == 1)
									Tiles.add(t);
							}
							data.tiles = Tiles;
							System.out.println(Mobs.size());
							System.out.println(Tracks.size());
							System.out.println(items.size());

							oos.writeObject(data);
							fos.close();
							oos.close();

						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}

				}
			}
		};
	}

	public void Init_Listener() {

		Map.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if (selected == true) {

					x = e.getX() - img_tile.getWidth(null) / 2;

					y = e.getY() - img_tile.getHeight(null) / 2;
					if (tile.tile_kind == 1)
						y = 405;
					if (tile.tile_kind == 0 && tile.idx > 1)
						y = 0;
					repaint();

				}

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Map.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (selected == false && delete == true) {

					// TODO Auto-generated method stub

					int x = e.getX();
					int y = e.getY();

					int idx = 0;
					for (Tile_Property object : tiles) {
						Image obj = Tile_Image_Manager.Load(object.tile_kind, object.idx);
						boolean rangeX = (object.x <= x + back_off_x)
								&& (x + back_off_x <= object.x + obj.getWidth(null));
						boolean rangeY = (object.y <= y) && (y <= object.y + obj.getHeight(null));

						if (rangeX && rangeY) {
							System.out.println("삭제중...");

							background = Tile_Image_Manager.background;

							tiles.remove(tiles.indexOf(object));
							for (Tile_Property retile : tiles) {

								Image redrawTile = Tile_Image_Manager.Load(retile.tile_kind, retile.idx);
								background = Map_Edit_Manager.Draw_Tile(background, retile.x, retile.y, redrawTile);

							}
							Map.repaint();

							System.out.println("삭제완료");
							break;
						}

					}

					return;
				} else if (selected == false)
					return;
				background = Map_Edit_Manager.Draw_Tile(background, x + (int) back_off_x, y, img_tile);
		
				Tile_Property my_tile = new Tile_Property(0, 0, 0, 0);
				my_tile.x = x + (int) back_off_x;
				my_tile.y = y;
				my_tile.tile_kind = tile.tile_kind;
				my_tile.idx = tile.idx;
				if(my_tile.tile_kind!=3 && my_tile.tile_kind!=2)
				{
					saveback = background;
				}
				tiles.add(my_tile);
				int tile_kind = my_tile.tile_kind;
				int idx = my_tile.idx;
				repaint();

				if (tile_kind == 2) {

					Mob_Property mob;
					Point mePt = new Point(my_tile.x, my_tile.y);
					mob = new Mob_Property(mePt, my_tile.idx);

					Mobs.add(mob);
				} else if (tile_kind == 3) {
					Item_Property item;
					Point mePt = new Point(my_tile.x, my_tile.y);
					item = new Item_Property(mePt, my_tile.idx);
					items.add(item);
				} else if (tile_kind == 0 && idx == 1) {
					Tracks.add(my_tile);
				}
				System.out.printf("%d,%d\n", tile_kind, idx);

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				back_off_x = slider.getValue();
				Map.repaint();

			}
		});

	}

	public static void main(String[] args) {
		new Editor_Window();
	}
}
