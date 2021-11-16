package chess;

import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;



/**
 * 
 * <p>
 * Title: ChessDraw.java
 * </p>
 * <p>
 * Description: Ê×ÏÈ»­³öÒ»¸öÆåÅÌ£¬¶ÔÓ¦Êý¾Ý½á¹¹Îª³µ1,Âí2,Ïó3,Ê¿4,½«5,ÅÚ6,±ø7,¿Õ¸ñÎª0¡£¶Ô·½ÔÚ´Ë»ù´¡ÉÏ¼Ó7
 * <p>
 * @author dedong
 * <p>
 * @date 2017/ ÏÂÎç2:15:02
 *
 */
public class ChessDraw extends JFrame {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	private JButton advanced = new JButton("Độ khó");
	private JButton cancel = new JButton("Hủy");
	private JButton music = new JButton("Nhạc");
	private int maxDepth = 2;
	private int ainum = 1; // Ä¬ÈÏÈË»ú¶ÔÞÄ
	private List<String> history = new ArrayList<String>();
	private List<int[][]> back = new ArrayList<>();
	private static String[] name = { "", "xe", "mã", "tượng", "sĩ", "tướng", "pháo", "tốt" };
	private static String[] name2 = { "", "xe", "mã", "tượng", "sĩ", "tướng", "pháo", "tốt" };
	private static String[] numname = { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
	private Image[] pics = new Image[15];
	private Timer timer;
	private int num;
	private int[][] raw;
	private AudioClip clip;
	private Thread thread;
	private int[][] data = { 
			{ 8, 9, 10, 11, 12, 11, 10, 9, 8 },{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 13, 0, 0, 0, 0, 0, 13, 0 }, { 14, 0, 14, 0, 14, 0, 14, 0, 14 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 7, 0, 7, 0, 7, 0, 7, 0, 7 }, { 0, 6, 0, 0, 0, 0, 0, 6, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 2, 3, 4, 5, 4, 3, 2, 1 } };
	
	

	public ChessDraw() {
		raw = BackData(data);
		final Board panel = new Board();
		add(panel, BorderLayout.CENTER);
		JPanel panel2 = new JPanel();
		panel2.add(advanced);
		panel2.add(cancel);
		panel2.add(music);
		add(panel2, BorderLayout.SOUTH);
		advanced.setBackground(new Color(216, 196, 152));
		cancel.setBackground(new Color(216, 196, 152));
		music.setBackground(new Color(216, 196, 152));
		panel2.setBackground(new Color(216, 196, 152));
		panel.setBackground(new Color(216, 196, 152));
		pics[1] = Toolkit.getDefaultToolkit().getImage("images/车1.png");
		pics[2] = Toolkit.getDefaultToolkit().getImage("images/马1.png");
		pics[3] = Toolkit.getDefaultToolkit().getImage("images/相1.png");
		pics[4] = Toolkit.getDefaultToolkit().getImage("images/士1.png");
		pics[5] = Toolkit.getDefaultToolkit().getImage("images/帅.png");
		pics[6] = Toolkit.getDefaultToolkit().getImage("images/炮1.png");
		pics[7] = Toolkit.getDefaultToolkit().getImage("images/兵.png");
		pics[8] = Toolkit.getDefaultToolkit().getImage("images/车2.png");
		pics[9] = Toolkit.getDefaultToolkit().getImage("images/马2.png");
		pics[10] = Toolkit.getDefaultToolkit().getImage("images/象2.png");
		pics[11] = Toolkit.getDefaultToolkit().getImage("images/士2.png");
		pics[12] = Toolkit.getDefaultToolkit().getImage("images/将.png");
		pics[13] = Toolkit.getDefaultToolkit().getImage("images/炮2.png");
		pics[14] = Toolkit.getDefaultToolkit().getImage("images/卒.png");
		back.add(BackData(data));

		advanced.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (ainum != 1)
					return;
				history.clear();
				for (int i = 0; i < back.size() - 1; i++)
					back.remove(back.size() - 1);
				panel.tip = false;
				panel.isSelected = false;
				panel.yourTurn = true;
				maxDepth = maxDepth == 2 ? 4 : 2;
				data = new int[][] { { 8, 9, 10, 11, 12, 11, 10, 9, 8 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 13, 0, 0, 0, 0, 0, 13, 0 }, { 14, 0, 14, 0, 14, 0, 14, 0, 14 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 7, 0, 7, 0, 7, 0, 7, 0, 7 },
						{ 0, 6, 0, 0, 0, 0, 0, 6, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 2, 3, 4, 5, 4, 3, 2, 1 } };
				repaint();
				if (maxDepth == 4)
					JOptionPane.showMessageDialog(null, "Màn khó");
				else
					JOptionPane.showMessageDialog(null, "Màn dễ");
			}
		});

	
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.tip = false;
				panel.isSelected = false;
				if (ainum == 0) {
					// Á½ÈË¶ÔÞÄ,»ÚÒ»²½
					if (back.size() <= 1) {
						JOptionPane.showMessageDialog(null, "quay lại");
						return;
					}
					back.remove(back.size() - 1);
					history.remove(history.size() - 1);
					data = back.get(back.size() - 1);
					panel.yourTurn = !panel.yourTurn;
					repaint();
				} else if (ainum == 1) {
					// »ÚÁ½²½
					if (back.size() <= 2) {
						JOptionPane.showMessageDialog(null, "không quay lại được nữa");
						return;
					}
					back.remove(back.size() - 1);
					back.remove(back.size() - 1);
					history.remove(history.size() - 1);
					history.remove(history.size() - 1);
					data = back.get(back.size() - 1);
					repaint();
				}
			}
		});


		music.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (thread != null && thread.isAlive()) {
					thread.stop();
					return;
				}
				File dir = new File("music");
				File[] files = dir.listFiles();
				thread = new AudioPlayer(files);
				thread.start();
			}
		});
	}

	private List<int[][]> open() {
		List<int[][]> datas = new ArrayList<>();
		JFileChooser jf = new JFileChooser(new File("chessFile"));
		jf.setFileFilter(new ChooseFile());
		if (jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file2 = jf.getSelectedFile();
			try {
				Scanner in = new Scanner(file2);
				int num = 0;
				int[][] tmp = new int[10][9];
				while (in.hasNextLine()) {
					if (num % 10 == 0 && num != 0) {
						datas.add(tmp);
						num %= 10;
						tmp = new int[10][9];
					}
					String line = in.nextLine();
					String[] parts = line.split(" ");
					for (int i = 0; i < parts.length; i++) {
						tmp[num][i] = Integer.parseInt(parts[i]);
					}
					num++;
				}
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;
	}

	public static int[][] BackData(int[][] data) {
		int[][] sub = new int[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				sub[i][j] = data[i][j];// Éî¶È¸´ÖÆ
			}
		}
		return sub;
	}

	private class ChooseFile extends FileFilter {

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			return f.getName().endsWith(".chess");
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "*.chess";
		}

	}

	/*
	 * »­³öÒ»¸öÆåÅÌ
	 */
	private class Board extends JPanel implements MouseListener {
		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		private int col;
		private int row;
		private boolean tip;
		private boolean isSelected;
		private int precol;
		private int prerow;
		private boolean preIsRed;
		private boolean yourTurn = true;

		/** @author sgy **/
		/** ¼Ù¶¨ÆåÅÌµÄ¸ß¿í **/
		private int height = 700;
		private int width = 630;
		private int chuheX = 70, chuheY = 330;
		private int hanjieX = 330, hanjieY = 330;
		private int redX = 570, blackX = 620;

		public Board() {
			// TODO Auto-generated constructor stub
			addMouseListener(this);
		}

		/*
		 * »æÖÆÆåÅÌÏß
		 */
		private void moreDraw(int row, int col, int width, int height, Graphics g) {
			// 1/2 2/3 1/3
			int baseX = width * col / 11;
			int baseY = height * row / 12;
			int wlen = width / 110;
			int hlen = height / 120;
			int w4len = width / 44;
			int h4len = height / 48;

			if (col != 9) {
				g.drawLine(baseX + wlen, baseY - hlen, baseX + wlen, baseY - h4len);
				g.drawLine(baseX + wlen, baseY - hlen, baseX + w4len, baseY - hlen);

				g.drawLine(baseX + wlen, baseY + hlen, baseX + w4len, baseY + hlen);
				g.drawLine(baseX + wlen, baseY + hlen, baseX + wlen, baseY + h4len);
			}

			if (col != 1) {
				g.drawLine(baseX - wlen, baseY - hlen, baseX - wlen, baseY - h4len);
				g.drawLine(baseX - wlen, baseY - hlen, baseX - w4len, baseY - hlen);

				g.drawLine(baseX - wlen, baseY + hlen, baseX - w4len, baseY + hlen);
				g.drawLine(baseX - wlen, baseY + hlen, baseX - wlen, baseY + h4len);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			// ÏÈ»­ÊúÏß9¸ö£¬ºáÏß10
			g.drawLine(width / 11, height / 12, width / 11, height * 10 / 12);
			g.drawLine(width * 9 / 11, height / 12, width * 9 / 11, height * 10 / 12);
			for (int i = 0; i < 7; i++) {
				g.drawLine(width * (i + 2) / 11, height / 12, width * (i + 2) / 11, height * 5 / 12);
				g.drawLine(width * (i + 2) / 11, height / 2, width * (i + 2) / 11, height * 10 / 12);
			}
			for (int i = 0; i < 10; i++) {
				g.drawLine(width / 11, height * (i + 1) / 12, width * 9 / 11, height * (i + 1) / 12);
			}



			// »­Ê¿µÄÏß
			g.drawLine(width * 4 / 11, height / 12, width * 6 / 11, height / 4);
			g.drawLine(width * 6 / 11, height / 12, width * 4 / 11, height / 4);
			g.drawLine(width * 4 / 11, height * 10 / 12, width * 6 / 11, height * 8 / 12);
			g.drawLine(width * 6 / 11, height * 10 / 12, width * 4 / 11, height * 8 / 12);

			moreDraw(3, 2, width, height, g);
			moreDraw(3, 8, width, height, g);
			moreDraw(8, 2, width, height, g);
			moreDraw(8, 8, width, height, g);// ÅÚ

			moreDraw(4, 1, width, height, g);
			moreDraw(4, 3, width, height, g);
			moreDraw(4, 5, width, height, g);
			moreDraw(4, 7, width, height, g);
			moreDraw(4, 9, width, height, g);
			moreDraw(7, 1, width, height, g);
			moreDraw(7, 3, width, height, g);
			moreDraw(7, 5, width, height, g);
			moreDraw(7, 7, width, height, g);
			moreDraw(7, 9, width, height, g);// ±ø

			// »­Í¼Ïñ
			for (int i = 1; i <= 10; i++) {
				for (int j = 1; j <= 9; j++) {
					int baseX = width * j / 11;
					int baseY = height * i / 12;
					g.drawImage(pics[data[i - 1][j - 1]], baseX - width / 22, baseY - height / 24, width / 11,
							height / 12, this);
				}
			}

			// »­Ò»¸öÌáÊ¾
			if (tip) {
				g.setColor(Color.green);
				
			
				Graphics2D g2d = (Graphics2D) g;
				Stroke st = g2d.getStroke();
				Stroke bs;
				bs = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 16, 4 }, 0);
				g2d.setStroke(bs);
				
				g2d.drawLine(col * width / 11 - width / 22, row * height / 12 - height / 24,
						col * width / 11 - width / 44, row * height / 12 - height / 24);
				g2d.drawLine(col * width / 11 - width / 22, row * height / 12 - height / 24,
						col * width / 11 - width / 22, row * height / 12 - height / 48);
				g2d.drawLine(col * width / 11 + width / 22, row * height / 12 - height / 24,
						col * width / 11 + width / 44, row * height / 12 - height / 24);
				g2d.drawLine(col * width / 11 + width / 22, row * height / 12 - height / 24,
						col * width / 11 + width / 22, row * height / 12 - height / 48);
				g2d.drawLine(col * width / 11 + width / 22, row * height / 12 + height / 24,
						col * width / 11 + width / 44, row * height / 12 + height / 24);
				g2d.drawLine(col * width / 11 + width / 22, row * height / 12 + height / 24,
						col * width / 11 + width / 22, row * height / 12 + height / 48);
				g2d.drawLine(col * width / 11 - width / 22, row * height / 12 + height / 24,
						col * width / 11 - width / 44, row * height / 12 + height / 24);
				g2d.drawLine(col * width / 11 - width / 22, row * height / 12 + height / 24,
						col * width / 11 - width / 22, row * height / 12 + height / 48);
				
				g2d.setStroke(st);
				g.setColor(Color.black);
			}

			// »­¼ÇÂ¼
			g.setFont(new Font("Á¥Êé", Font.BOLD, 28));
			if (history.size() > 0 && history.size() <= 20) {
				for (int i = 0; i < history.size(); i++) {
					if (i % 2 == 0) {
						// ºì·½ 570
						g.setColor(Color.red);
						g.drawString(history.get(i), redX, 100 + 30 * i);
					} else {
						// ºÚ·½ 620
						g.setColor(Color.blue);
						g.drawString(history.get(i), blackX, 100 + 30 * i);
					}
				}
			} else if (history.size() > 20) {
				for (int i = history.size() - 20; i < history.size(); i++) {
					if (i % 2 == 0) {
						// ºì·½
						g.setColor(Color.red);
						g.drawString(history.get(i), redX, 60 + 30 * (i + 20 - history.size()));
					} else {
						g.setColor(Color.blue);
						g.drawString(history.get(i), blackX, 60 + 30 * (i + 20 - history.size()));
					}
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (!yourTurn && ainum == 1)
				return;

			// È·¶¨Î»ÖÃ
			int x = e.getX();
			int y = e.getY();
			// ÅÐ¶Ï x µÄ col
			for (int i = 1; i <= 9; i++) {
				if (Math.abs(width * i / 11 - x) < width / 22) {
					col = i;
					break;
				}
			}
			// ÅÐ¶ÏyµÄrow
			for (int i = 1; i <= 10; i++) {
				if (Math.abs(height * i / 12 - y) < height / 24) {
					row = i;
					break;
				}
			}
			if (isSelected) {
				// ÒªÏëÈ¥ÕâÀï
				// Ö»ÒªÅÐ¶ÏÄÜ²»ÄÜµ½¼´¿É
				int label = data[prerow - 1][precol - 1];
				int rawlabel = data[row - 1][col - 1];
				switch (label) {
				case 1:
				case 8:
					// ÊÇ³µ
					if ((preIsRed && data[row - 1][col - 1] < 8 && data[row - 1][col - 1] > 0)
							|| (!preIsRed && data[row - 1][col - 1] >= 8)) {
						isSelected = true;
						precol = col;
						prerow = row;
						preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
						repaint();
						return;// Í¬ÑÕÉ«
					}
					if (row < prerow && col == precol) {
						// ÏëÍùÉÏ×ß
						for (int i = row + 1; i < prerow; i++) {
							if (data[i - 1][col - 1] != 0)
								return;// ÓÐ×Óµ²ÁË
						}
					} else if (col < precol && row == prerow) {
						// ÏëÍù×ó×ß
						for (int i = col + 1; i < precol; i++)
							if (data[row - 1][i - 1] != 0)
								return;
					} else if (row > prerow && col == precol) {
						// ÏëÍùÏÂ×ß
						for (int i = prerow + 1; i < row; i++)
							if (data[i - 1][col - 1] != 0)
								return;
					} else if (col > precol && row == prerow) {
						// ÏëÍùÓÒ×ß
						for (int i = precol + 1; i < col; i++)
							if (data[row - 1][i - 1] != 0)
								return;
					} else {
						if (data[row - 1][col - 1] == 0)
							return;
						else if ((data[row - 1][col - 1] < 8 && preIsRed)
								|| (data[row - 1][col - 1] >= 8 && preIsRed)) {
							// »»×ÓÑ¡Ôñ
							isSelected = true;
							precol = col;
							prerow = row;
							preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
							repaint();
						}
						return;
					}
					// ÏÂÃæÊÇÕý³£Çé¿ö
					data[row - 1][col - 1] = data[prerow - 1][precol - 1];
					data[prerow - 1][precol - 1] = 0;
					repaint();
					break;
				case 2:
				case 9:
					// ÊÇÂí
					if ((preIsRed && data[row - 1][col - 1] < 8 && data[row - 1][col - 1] > 0)
							|| (!preIsRed && data[row - 1][col - 1] >= 8)) {
						isSelected = true;
						precol = col;
						prerow = row;
						preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
						repaint();
						return;
					}
					if (col - precol == 2 && prerow - row == 1) {
						// ÌÉÈÕ¶«±±·½Ïò
						if (data[prerow - 1][precol] != 0)
							return;
					} else if (col - precol == 2 && row - prerow == 1) {
						// ÌÉÈÕ¶«ÄÏ·½Ïò
						if (data[prerow - 1][precol] != 0)
							return;
					} else if (precol - col == 2 && row - prerow == 1) {
						// ÌÉÈÕÎ÷ÄÏ·½Ïò
						if (data[prerow - 1][precol - 2] != 0)
							return;
					} else if (precol - col == 2 && prerow - row == 1) {
						// ÌÉÈÕÎ÷±±·½Ïò
						if (data[prerow - 1][precol - 2] != 0)
							return;
					} else if (col - precol == 1 && prerow - row == 2) {
						// ÌøÈÕ¶«±±
						if (data[prerow - 2][precol - 1] != 0)
							return;
					} else if (col - precol == 1 && row - prerow == 2) {
						// ÌøÈÕ¶«ÄÏ
						if (data[prerow][precol - 1] != 0)
							return;
					} else if (precol - col == 1 && row - prerow == 2) {
						if (data[prerow][precol - 1] != 0)
							return;
					} else if (precol - col == 1 && prerow - row == 2) {
						if (data[prerow - 2][precol - 1] != 0)
							return;
					} else {
						if (data[row - 1][col - 1] == 0)
							return;
						else if ((data[row - 1][col - 1] < 8 && preIsRed)
								|| (data[row - 1][col - 1] >= 8 && preIsRed)) {
							// »»×ÓÑ¡Ôñ
							isSelected = true;
							precol = col;
							prerow = row;
							preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
							repaint();
						}
						return;
					}
					data[row - 1][col - 1] = data[prerow - 1][precol - 1];
					data[prerow - 1][precol - 1] = 0;
					repaint();
					break;
				case 3:
				case 10:
					// ÊÇÏà
					if ((preIsRed && data[row - 1][col - 1] < 8 && data[row - 1][col - 1] > 0)
							|| (!preIsRed && data[row - 1][col - 1] >= 8)) {
						isSelected = true;
						precol = col;
						prerow = row;
						preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
						repaint();
						return;
					}
					if (preIsRed) {
						if (row <= 5)
							return;// ²»ÄÜ³öÈ¥
					} else {
						if (row > 5)
							return;
					}
					if (col - precol == 2 && prerow - row == 2) {
						// Íù¶«±±·½Ïò
						if (data[prerow - 2][precol] != 0)
							return;
					} else if (col - precol == 2 && row - prerow == 2) {
						// Íù¶«ÄÏ·½Ïò
						if (data[prerow][precol] != 0)
							return;
					} else if (precol - col == 2 && row - prerow == 2) {
						// ÍùÎ÷ÄÏ·½Ïò
						if (data[prerow][precol - 2] != 0)
							return;
					} else if (precol - col == 2 && prerow - row == 2) {
						// ÍùÎ÷±±·½Ïò
						if (data[prerow - 2][precol - 2] != 0)
							return;
					} else {
						if (data[row - 1][col - 1] == 0)
							return;
						else if ((data[row - 1][col - 1] < 8 && preIsRed)
								|| (data[row - 1][col - 1] >= 8 && preIsRed)) {
							// »»×ÓÑ¡Ôñ
							isSelected = true;
							precol = col;
							prerow = row;
							preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
							repaint();
						}
						return;
					}
					data[row - 1][col - 1] = data[prerow - 1][precol - 1];
					data[prerow - 1][precol - 1] = 0;
					repaint();
					break;
				case 4:
				case 11:
					// Ê¿
					if ((preIsRed && data[row - 1][col - 1] < 8 && data[row - 1][col - 1] > 0)
							|| (!preIsRed && data[row - 1][col - 1] >= 8)) {
						isSelected = true;
						precol = col;
						prerow = row;
						preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
						repaint();
						return;
					}
					if (col > 6 || col < 4)
						return;
					if (preIsRed) {
						if (row < 8)
							return;
					} else {
						if (row > 3)
							return;
					}
					if (col - precol == 1 && row - prerow == 1) {
						// Íù¶«ÄÏ·½Ïò
					} else if (col - precol == 1 && prerow - row == 1) {
						// Íù¶«±±·½Ïò
					} else if (precol - col == 1 && row - prerow == 1) {
						// ÍùÎ÷ÄÏ·½Ïò
					} else if (precol - col == 1 && prerow - row == 1) {
						// ÍùÎ÷±±·½Ïò
					} else {
						if (data[row - 1][col - 1] == 0)
							return;
						else if ((data[row - 1][col - 1] < 8 && preIsRed)
								|| (data[row - 1][col - 1] >= 8 && preIsRed)) {
							// »»×ÓÑ¡Ôñ
							isSelected = true;
							precol = col;
							prerow = row;
							preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
							repaint();
						}
						return;
					}
					data[row - 1][col - 1] = data[prerow - 1][precol - 1];
					data[prerow - 1][precol - 1] = 0;
					repaint();
					break;
				case 5:
				case 12:
					// ÊÇ½«
					if ((preIsRed && data[row - 1][col - 1] < 8 && data[row - 1][col - 1] > 0)
							|| (!preIsRed && data[row - 1][col - 1] >= 8)) {
						isSelected = true;
						precol = col;
						prerow = row;
						preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
						repaint();
						return;
					}
					if (col > 6 || col < 4)
						return;
					if (preIsRed) {
						if (row < 8)
							return;
					} else {
						if (row > 3)
							return;
					}
					if (col - precol == 1 && row == prerow) {
						// ÓÒ±ß
					} else if (col == precol && prerow - row == 1) {
						// ÉÏ
					} else if (precol - col == 1 && prerow == row) {
						// ×ó
					} else if (precol == col && row - prerow == 1) {
						// ÏÂ
					} else {
						if (data[row - 1][col - 1] == 0)
							return;
						else if ((data[row - 1][col - 1] < 8 && preIsRed)
								|| (data[row - 1][col - 1] >= 8 && preIsRed)) {
							// »»×ÓÑ¡Ôñ
							isSelected = true;
							precol = col;
							prerow = row;
							preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
							repaint();
						}
						return;
					}
					data[row - 1][col - 1] = data[prerow - 1][precol - 1];
					data[prerow - 1][precol - 1] = 0;
					repaint();
					break;
				case 6:
				case 13:
					// ÊÇÅÚ
					if (data[row - 1][col - 1] == 0) {
						// Èç¹ûÊÇ¿Õ¸ñ£¬Ïñ³µÒ»Ñù
						if (row < prerow && col == precol) {
							// ÏëÍùÉÏ×ß
							for (int i = row + 1; i < prerow; i++) {
								if (data[i - 1][col - 1] != 0)
									return;// ÓÐ×Óµ²ÁË
							}
						} else if (col < precol && row == prerow) {
							// ÏëÍù×ó×ß
							for (int i = col + 1; i < precol; i++)
								if (data[row - 1][i - 1] != 0)
									return;
						} else if (row > prerow && col == precol) {
							// ÏëÍùÏÂ×ß
							for (int i = prerow + 1; i < row; i++)
								if (data[i - 1][col - 1] != 0)
									return;
						} else if (col > precol && row == prerow) {
							// ÏëÍùÓÒ×ß
							for (int i = precol + 1; i < col; i++)
								if (data[row - 1][i - 1] != 0)
									return;
						} else {
							return;
						}
					} else if ((preIsRed && data[row - 1][col - 1] < 8) || (!preIsRed && data[row - 1][col - 1] >= 8)) {
						isSelected = true;
						precol = col;
						prerow = row;
						preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
						repaint();
						return;
					} else {
						// ÊÇ¶Ô·½µÄ×Ó
						// ±ØÐëÇ¡ºÃ¸ôÒ»¸ö
						int num = 0;
						if (row < prerow && col == precol) {
							// ÉÏ
							for (int i = row + 1; i < prerow; i++) {
								if (data[i - 1][col - 1] != 0)
									num++;
							}
						} else if (row > prerow && col == precol) {
							// ÏÂ
							for (int i = prerow + 1; i < row; i++)
								if (data[i - 1][col - 1] != 0)
									num++;
						} else if (row == prerow && col > precol) {
							// ÓÒ
							for (int i = precol + 1; i < col; i++)
								if (data[row - 1][i - 1] != 0)
									num++;
						} else if (row == prerow && col < precol) {
							// ×ó
							for (int i = col + 1; i < precol; i++)
								if (data[row - 1][i - 1] != 0)
									num++;
						}
						if (num != 1)
							return;
					}
					// ÏÖÔÚ¿ÉÒÔÁË
					data[row - 1][col - 1] = data[prerow - 1][precol - 1];
					data[prerow - 1][precol - 1] = 0;
					repaint();
					break;
				case 7:
				case 14:
					// ÊÇ±ø
					if ((preIsRed && data[row - 1][col - 1] < 8 && data[row - 1][col - 1] > 0)
							|| (!preIsRed && data[row - 1][col - 1] >= 8)) {
						isSelected = true;
						precol = col;
						prerow = row;
						preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
						repaint();
						return;
					}
					if (preIsRed) {
						if (prerow > 5) {
							// ÔÚ×Ô¼ºµÄÕóµØÉÏ ²»ÄÜ×óÓÒÒÆ¶¯
							if (prerow - row == 1 && precol == col) {
								// Ç°
							} else if (data[row - 1][col - 1] < 8) {
								isSelected = true;
								precol = col;
								prerow = row;
								preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
								repaint();
								return;
							} else {
								return;
							}
						} else {
							// ÔÚ¶Ô·½ÕóµØÉÏ
							if (prerow - row == 1 && precol == col) {
								// 
							} else if (prerow == row && precol - col == 1) {
								// ×ó
							} else if (prerow == row && col - precol == 1) {
								// ÓÒ
							} else {
								isSelected = true;
								precol = col;
								prerow = row;
								preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
								repaint();
								return;
							}
						}
					} else {
						if (prerow <= 5) {
							// ÔÚ×Ô¼ºµÄÕóµØÉÏ ²»ÄÜ×óÓÒÒÆ¶¯
							if (prerow - row == -1 && precol == col) {
								// Ç°
							} else if (data[row - 1][col - 1] >= 8) {
								isSelected = true;
								precol = col;
								prerow = row;
								preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
								repaint();
								return;
							} else {
								return;
							}
						} else {
							// ÔÚ¶Ô·½ÕóµØÉÏ
							if (prerow - row == -1 && precol == col) {
								// 
							} else if (prerow == row && precol - col == 1) {
								// ×ó
							} else if (prerow == row && col - precol == 1) {
								// ÓÒ
							} else {
								isSelected = true;
								precol = col;
								prerow = row;
								preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
								repaint();
								return;
							}
						}
					}
					data[row - 1][col - 1] = data[prerow - 1][precol - 1];
					data[prerow - 1][precol - 1] = 0;
					repaint();
					break;
				default:
					break;
				}
				isSelected = false;
				if (ainum == 1) {
					yourTurn = false;
					String tmp = "";
					tmp += name2[label];
					switch (label) {
					case 1:
					case 6:
					case 7:
					case 5:
						// ³µÅÚ
						if (prerow == row) {
							// Æ½
							tmp += numname[10 - precol] + "bình" + numname[10 - col];
						} else if (precol == col) {
							if (prerow > row) {
								tmp += numname[11 - prerow] + "tấn" + numname[11 - row];
							} else {
								tmp += numname[11 - prerow] + "thoái" + numname[11 - row];
							}
						}
						break;
					default:
						if (prerow > row) {
							tmp += numname[10 - precol] + "tấn" + numname[10 - col];
						} else {
							tmp += numname[10 - precol] + "thoái" + numname[10 - col];
						}
						break;
					}
					// ºì·½ÃèÊö
					history.add(tmp);
					back.add(BackData(data));
					data = new Chess(data).compute(maxDepth);
					if (data == null) {
						data = back.get(back.size() - 1);
						try {
							PrintWriter out = new PrintWriter("chessFile/"
									+ new Date().toLocaleString().replace(" ", "").replace(":", "") + ".chess");
							for (int i = 0; i < back.size(); i++) {
								for (int j = 0; j < back.get(i).length; j++) {
									for (int k = 0; k < back.get(i)[j].length; k++) {
										out.print(back.get(i)[j][k] + " ");
									}
									out.println();
								}
							}
							out.close();// ±£´æ
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "congratulate,you win.");
						return;
					} else {
						boolean isShuai = false;
						for (int i = 7; i <= 9; i++)
							for (int j = 3; j <= 5; j++)
								if (data[i][j] == 5) {
									isShuai = true;
								}
						if (!isShuai) {
							JOptionPane.showMessageDialog(null, "sorry,you lose.");
							try {
								PrintWriter out = new PrintWriter("chessFile/"
										+ new Date().toLocaleString().replace(" ", "").replace(":", "") + ".chess");
								back.add(BackData(data));
								for (int i = 0; i < back.size(); i++) {
									for (int j = 0; j < back.get(i).length; j++) {
										for (int k = 0; k < back.get(i)[j].length; k++) {
											out.print(back.get(i)[j][k] + " ");
										}
										out.println();
									}
								}
								out.close();// ±£´æ
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							back.remove(back.size() - 1);
							return;
						}
					}
					history.add(findName(back.get(back.size() - 1), data));
					back.add(BackData(data));
					tip = false;
					repaint();
					yourTurn = true;
				} else if (ainum == 0) {
					if (yourTurn) {
						// ºì·½
						String tmp = "";
						tmp += name2[label];
						if (rawlabel == 0 || rawlabel >= 8) {
							yourTurn = !yourTurn;
							switch (label) {
							case 1:
							case 6:
							case 7:
							case 5:
								// ³µÅÚ
								if (prerow == row) {
									// Æ½
									tmp += numname[10 - precol] + "bình" + numname[10 - col];
								} else if (precol == col) {
									if (prerow > row) {
										tmp += numname[11 - prerow] + "tấn" + numname[11 - row];
									} else {
										tmp += numname[11 - prerow] + "thoái" + numname[11 - row];
									}
								}
								break;
							default:
								if (prerow > row) {
									tmp += numname[10 - precol] + "thoái" + numname[10 - col];
								} else {
									tmp += numname[10 - precol] + "thoái" + numname[10 - col];
								}
								break;
							}
							history.add(tmp);
							back.add(BackData(data));
						}
					} else {
						// ºÚ·½
						String tmp = "";
						tmp += name[label - 7];
						if (rawlabel <= 7) {
							yourTurn = !yourTurn;
							switch (label) {
							case 8:
							case 13:
							case 14:
							case 12:
								// ³µÅÚ
								if (prerow == row) {
									// Æ½
									tmp += numname[precol] + "bình" + numname[col];
								} else if (precol == col) {
									if (prerow < row) {
										tmp += numname[prerow] + "tấn" + numname[row];
									} else {
										tmp += numname[prerow] + "thoái" + numname[row];
									}
								}
								break;
							default:
								if (prerow < row) {
									tmp += numname[precol] + "tấn" + numname[col];
								} else {
									tmp += numname[precol] + "thoái" + numname[col];
								}
								break;
							}
							history.add(tmp);
							back.add(BackData(data));
						}
					}
				}
				//
				return;
			}
			precol = col;
			prerow = row;
			if (precol == 0 || prerow == 0)
				return;
			preIsRed = data[prerow - 1][precol - 1] < 8 ? true : false;
			if (ainum == 1) {// Ö»ÄÜÖ´ºì
				if (data[prerow - 1][precol - 1] == 0 || data[prerow - 1][precol - 1] >= 8)
					return;
			} else if (ainum == 0) {// Á½ÈË¶ÔÞÄ
				if (data[prerow - 1][precol - 1] == 0) {
					return;
				} else if (!yourTurn && data[prerow - 1][precol - 1] <= 7) {
					return;
				} else if (yourTurn && data[prerow - 1][precol - 1] >= 8) {
					return;
				}
			}
			tip = true;
			isSelected = true;
			repaint();// ½â¾öÁËÑ¡ÖÐÎÊÌâ
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	// pao´æÔÚÎÊÌâ

	public String findName(int[][] data, int[][] sub) {
		int prerow = -1, precol = -1, row = -1, col = -1, label = -1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				if (sub[i][j] == 0 && data[i][j] >= 8) {
					prerow = i + 1;
					precol = j + 1;
					label = data[prerow - 1][precol - 1];
				} else if (data[i][j] <= 7 && sub[i][j] >= 8) {
					row = i + 1;
					col = j + 1;
				}
			}
		}
		String tmp = "";
		tmp += name[label - 7];
		switch (label) {
		case 8:
		case 13:
		case 14:
		case 12:
			// ³µÅÚ
			if (prerow == row) {
				// Æ½
				tmp += numname[precol] + "bình" + numname[col];
			} else if (precol == col) {
				if (prerow < row) {
					tmp += numname[prerow] + "tấn" + numname[row];
				} else {
					tmp += numname[prerow] + "thoái" + numname[row];
				}
			}
			break;
		default:
			if (prerow < row) {
				tmp += numname[precol] + "tấn" + numname[col];
			} else {
				tmp += numname[precol] + "thoái" + numname[col];
			}
			break;
		}
		return tmp;
	}
	// ´æÔÚÑ­»·×ß²½£¬×¢Òâ¿´×îºóÒ»¸öÎÄ¼þ
	// ¶øÇÒÐ§Òæµ½ºóÆÚ¸Ð¾õ²»Ã÷ÏÔ
	// ¶à´Î½«¾üÎÊÌâ£¬ÒÔ¼°²»³ÔË§µÄÇé¿ö

	private class AudioPlayer extends Thread {
		Player player;
		File[] musicFile;
		int time = 0;

		public AudioPlayer(File[] file) {
			// TODO Auto-generated constructor stub
			musicFile = file;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				while (true) {
					play();
					time = (time + 1) % musicFile.length;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		public void play() throws FileNotFoundException, JavaLayerException {
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(musicFile[time]));
			player = new Player(inputStream);
			player.play();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new ChessDraw();
		frame.setTitle("ÏóÆå");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 700);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

// ÐèÒªÍêÉÆ
// 1.¼ÓÈë¶ÔÆÀ¼Û²»´óµÄÔòËæ»ú×ßÆå
// 2.¼ÓÈëÁÐ±íÀ´²¥·Åmp3ÒôÀÖ
// 3.¼ÓÈëÈÃ×Ó¹¦ÄÜ
// 4.¼ÓÈëË­ÏÈ×ßµÄ¹¦ÄÜ
// 5.×ö³öÁ½AI¶ÔÞÄ£¬Ê¹µÃËã·¨¸üÍêÉÆ
// 6.½â¾ö3²½²»ÄÜÁ¬½«Çé¿ö
// 7.¼ÓÈë½«¾ü½±Àø»úÖÆ¡¢ÅäºÏ²ßÂÔ¡¢Õ½Êõ²ßÂÔµÈµÈ
// 8.½â¾öÂ¼Ïñ×îºóÒ»²½²»ÏÔÊ¾µÄÎÊÌâ
// 9.½â¾öÄªÃûÆäÃîµÄ²½·¨