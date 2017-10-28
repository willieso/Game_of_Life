package gol;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;


public class Game extends JFrame
{
	private final Map map;    
	private static JDialog infoDialog;
	URL url = this.getClass().getResource("background.wav");
	AudioClip audioClip = Applet.newAudioClip(url);
	private static int count = 0;
	
	
	public Game(int rows, int columns)
	{
		map = new Map(rows, columns);
		
		new Thread(map).start();
		this.getContentPane().add(map, BorderLayout.CENTER);               //NORTH为什么不行？
		//add(map);                        //在窗口中加载map
		this.getContentPane().addMouseListener(new MyMouseListener());     //不增加一个面板来添加监听的话程序打开的很慢是为什么？？
		
	}
	
	public static void main(String[] args)
	{
		//游戏主窗口
		Game frame = new Game(40, 40);
		
		//info对话框
	    infoDialog = new JDialog(frame, "About this Game");        //偏要设置成static???
	    infoDialog.setBounds(400, 200, 350, 150);
	    infoDialog.setLocationRelativeTo(frame);
	    
	    //info对话框显示的内容
	    JLabel lab = new JLabel("Retr0's Game of Life. ©2017Willieso", SwingConstants.CENTER);
	    infoDialog.add(lab);
	    
		
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		
		JMenu start = new JMenu("Start");
		menu.add(start);
		
		JMenu defaultpic = new JMenu("Default");
		start.add(defaultpic);
		
		JMenuItem sakura = defaultpic.add("Sakura");
		sakura.addActionListener(frame.new SakuraActionListener());
		
		JMenuItem diamond = defaultpic.add("Diamond");
		diamond.addActionListener(frame.new DiamondActionListener());
		
		JMenuItem square = defaultpic.add("Square");
		square.addActionListener(frame.new SquareActionListener());
		
		JMenuItem random = start.add("Random");
		random.addActionListener(frame.new RandomActionListener());
		
		start.addSeparator();                                       //加入分割线
		
		JMenuItem go = start.add("GO!");
		go.addActionListener(frame.new GoActionListener());
		
		JMenuItem pause = start.add("Pause");
		pause.addActionListener(frame.new PauseActionListener());
		
		JMenuItem clear = start.add("Clear");
		clear.addActionListener(frame.new ClearActionListener());
		
		JMenu speed = new JMenu("Speed");
		menu.add(speed);
		
		JMenuItem x1 = speed.add("0.5X");
		x1.addActionListener(frame.new X1SpeedActionListener());
		
		JMenuItem x2 = speed.add("1X");
		x2.addActionListener(frame.new X2SpeedActionListener());
		
		JMenuItem x3 = speed.add("2X");
		x3.addActionListener(frame.new X3SpeedActionListener());
		
		JMenu help = new JMenu("Help");
		menu.add(help); 
		
		JMenuItem info = help.add("About this game");
		info.addActionListener(frame.new InfoActionListener());
		
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(806,860);
		frame.setResizable(false);
		frame.setTitle("Life is a game");
	}
	
	public void playMusic()
	{
		//audioClip.play();
		audioClip.loop();
	}
	
	class SakuraActionListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		map.setSakura();
    		if(count == 0)
				playMusic();
    		count++;
    	}
    }
	
	class DiamondActionListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		map.setDiamond();
    		if(count == 0)
				playMusic();
    		count++;
    	}
    }
	
	class SquareActionListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		map.setSquare();
    		if(count == 0)
				playMusic();
    		count++;
    	}
    }
	
	class RandomActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			map.setRandom();
			if(count == 0)
				playMusic();
			count++;
		}
	}
	
	class GoActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			map.setPic();
			if(count == 0)
				playMusic();
			count++;
			//System.out.println(count);
		}
	}
	
	class PauseActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			map.pause();
		}
	}
	
	class ClearActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			map.clearMap();
			audioClip.stop();
			count = 0;
		}
	}
	
	class InfoActionListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		infoDialog.setVisible(true);
    	}
    }
	
	class X1SpeedActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			map.changeSpeed(20);
		}
	}
	
	class X2SpeedActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			map.changeSpeed(10);
		}
	}
	
	class X3SpeedActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			map.changeSpeed(5);
		}
	}
	
	class MyMouseListener extends MouseAdapter
	{
			public void mouseClicked(MouseEvent e){
				//System.out.println(e.getX()+","+e.getY()+"   "+e.getX()%20+", "+e.getY()%20);
				map.setDots(e.getY()/20, e.getX()/20);
			}
	}
	
}