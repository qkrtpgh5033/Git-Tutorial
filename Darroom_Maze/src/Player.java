import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Player extends JFrame implements KeyListener, ActionListener{
	
	Container ct;
	Serial si;
	JButton jb_start;
	JButton [] jb_speed;
	JButton [] jb;
	CallBack cb;
	int cur_x;
	int cur_y;
	int [][] map;
	JPanel jp;
	public Player(Serial si, CallBack cb, int [][] map, int start_x, int start_y)
	{
		this.map = map;
		this.cb = cb;
		this.si = si;
		cur_x = start_x;
		cur_y= start_y;
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		jp = new JPanel(new GridLayout(2,3));
		
		String []  str = {"▲", "▶", "▼", "◀"};
		jb = new JButton[4];
		

		for(int i = 0; i < jb.length; i++)
		{
			jb[i] = new JButton(str[i]);
			jb[i].setEnabled(false);
			jb[i].setFocusable(false);
		
		}
		
		jp.add(new JLabel(""));
		jp.add(jb[0]);
		jp.add(new JLabel(""));
		jp.add(jb[3]);
		jp.add(jb[2]);
		jp.add(jb[1]);
		
		
		JPanel jp_grid = new JPanel(new GridLayout(2,0));
		JPanel jp_grid2 = new JPanel(new GridLayout(0,3));
		JPanel jp_bor = new JPanel(new BorderLayout());
		JLabel jl_speed = new JLabel("SPEED", JLabel.CENTER);
		jb_speed = new JButton[3];
		for(int i = 0 ; i < jb_speed.length; i++)
		{
			jb_speed[i] = new JButton((i+1)+"");
			jb_speed[i].addActionListener(this);
			jp_grid2.add(jb_speed[i]);
			jb_speed[i].setFocusable(false);
		}
		jp_bor.add(jl_speed, BorderLayout.CENTER);
		jp_bor.add(jp_grid2, BorderLayout.EAST);
		jb_start = new JButton("START");
		jb_start.addActionListener(this);
		jp_grid.add(jp_bor);
		jp_grid.add(jb_start);
		ct.add(jp_grid,BorderLayout.NORTH);
		
		
		ct.add(jp,BorderLayout.CENTER);
		
		setSize(300,300);
		setVisible(true);
		move_color("G");
		jb_start.setFocusable(false);
//		addKeyListener(this);
//		setFocusable(true);
	}

	void remove_key()
	{
		this.removeKeyListener(this);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int key_code = e.getKeyCode();
		int x=0;
		int y=0;
		// 조명 빨강 처리 
	
	
		if(key_code == KeyEvent.VK_UP)
		{
			x = cur_x;
			y = cur_y-1;
		}
		else if(key_code == KeyEvent.VK_RIGHT)
		{
			x = cur_x+1;
			y = cur_y;
			
		}
		else if(key_code == KeyEvent.VK_DOWN)
		{
			x = cur_x;
			y = cur_y+1;
		}
		else if(key_code == KeyEvent.VK_LEFT)
		{
			x = cur_x -1;
			y = cur_y;
		}
		
		
		if(map[y][x] == 10 )		// 도착 지점
		{
			cb.set_isrun(false);
			cb.map_dt.paint_remove();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cb.map_dt.paint_result("win");
			this.removeKeyListener(this);
			this.setFocusable(false);
			
		}
		else if(map[y][x] != 1)
		{
			move_color("Black");
			cur_x = x;
			cur_y = y;
			move_color("G");
			
		}
		
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	void move_color(String color)
	{
		int col = 19 - cur_y;
		int row = cur_x;
		
		String str_col = Integer.toHexString(col);
		String str_row = Integer.toHexString(row);
		if(str_col.length() == 1)
			str_col = "0" + str_col;
		if(str_row.length() == 1)
			str_row = "0" + str_row;
		String idx = str_col + str_row;
		si.on_color(idx, color);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource().equals(jb_start))
		{
			jb_start.setEnabled(false);
			
			for(int i = 0 ; i < jb.length; i++)
				jb[i].setEnabled(true);
			
			

			cb.com.start();
			cb.wall_gen.start();
			addKeyListener(this);
			setFocusable(true);
			
		}
		else if(e.getSource().equals(jb_speed[0]))	// 속도 1
		{
			cb.com.set_speed(1);
		}
		else if(e.getSource().equals(jb_speed[1]))	// 속도 2
		{
			cb.com.set_speed(2);
		}
		else if(e.getSource().equals(jb_speed[2]))	// 속도 3
		{
			cb.com.set_speed(3);	
		}
		
	}


}
