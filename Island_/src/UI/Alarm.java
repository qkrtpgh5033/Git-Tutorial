package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Alarm extends JFrame implements ActionListener{

	JLabel jl_alarm;
	JButton jb_back;
	
	Container ct;
	JButton jb_red;
	JButton jb_blue;
	JButton jb_fan;
	JButton jb_sensor;
	JButton jb_main;

	JLabel jl_now_red[];
	JLabel jl_now_blue[];
	JLabel jl_now_fan[];
	JLabel jl_now_sensor[];
	JLabel jl_now_main[];

	JLabel jl_alarm_red[];
	JLabel jl_alarm_blue[];
	JLabel jl_alarm_fan[];
	JLabel jl_alarm_sensor[];
	JLabel jl_alarm_main[];

	JComboBox<String> []box_on_red;		// 0¹øÂ° hour, 1¹øÂ° minute
	JComboBox<String> []box_on_blue;
	JComboBox<String> []box_on_fan;
	JComboBox<String> []box_on_sensor;
	JComboBox<String> []box_on_main;
	
	JComboBox<String> []box_off_red;		// 0¹øÂ° hour, 1¹øÂ° minute
	JComboBox<String> []box_off_blue;
	JComboBox<String> []box_off_fan;
	JComboBox<String> []box_off_sensor;
	JComboBox<String> []box_off_main;
	GUI gui;
	String hour[] = {"0h","1h","2h","3h","4h","5h","6h","7h","8h","9h","10h","11h","12h", "13h", "14h","15h","16h","17h","18h","19h","20h","21h","22h","23h"};
	String minute[];

	
	public Alarm(GUI gui) {
		
	
		init_minute();
		init_combobox();
		this.gui = gui;
		callback();
		
		ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(Color.white);
		init();

		setSize(800, 650);
		setTitle("¾Ë¶÷");
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}
	
	void init_minute()
	{
		minute = new String[60];
		for(int i = 0 ; i <minute.length; i++)
		{
			minute[i] = Integer.toString(i)+"m";
		}
	}
	
	void init_combobox()
	{
		box_on_red = new JComboBox[2];
		box_on_blue = new JComboBox[2];
		box_on_fan = new JComboBox[2];
		box_on_sensor = new JComboBox[2];
		box_on_main = new JComboBox[2];
	
		box_off_red = new JComboBox[2];
		box_off_blue = new JComboBox[2];
		box_off_fan = new JComboBox[2];
		box_off_sensor = new JComboBox[2];
		box_off_main = new JComboBox[2];
		
		for(int i = 0;  i < box_on_red.length; i++)
		{
			box_on_red[i] = new JComboBox();
			box_on_blue[i] = new JComboBox();
			box_on_fan[i] = new JComboBox();
			box_on_sensor[i] = new JComboBox();
			box_on_main[i] = new JComboBox();
		
			box_off_red[i] = new JComboBox();
			box_off_blue[i] = new JComboBox();
			box_off_fan[i] = new JComboBox();
			box_off_sensor[i] = new JComboBox();
			box_off_main[i] = new JComboBox();
			
			
		}
		
		box_on_red[0].setModel(new DefaultComboBoxModel(hour));
		box_on_blue[0].setModel(new DefaultComboBoxModel(hour));
		box_on_fan[0].setModel(new DefaultComboBoxModel(hour));
		box_on_sensor[0].setModel(new DefaultComboBoxModel(hour));
		box_on_main[0].setModel(new DefaultComboBoxModel(hour));
		
		box_on_red[1].setModel(new DefaultComboBoxModel(minute));
		box_on_blue[1].setModel(new DefaultComboBoxModel(minute));
		box_on_fan[1].setModel(new DefaultComboBoxModel(minute));
		box_on_sensor[1].setModel(new DefaultComboBoxModel(minute));
		box_on_main[1].setModel(new DefaultComboBoxModel(minute));
		

		box_off_red[0].setModel(new DefaultComboBoxModel(hour));
		box_off_blue[0].setModel(new DefaultComboBoxModel(hour));
		box_off_fan[0].setModel(new DefaultComboBoxModel(hour));
		box_off_sensor[0].setModel(new DefaultComboBoxModel(hour));
		box_off_main[0].setModel(new DefaultComboBoxModel(hour));
		
		box_off_red[1].setModel(new DefaultComboBoxModel(minute));
		box_off_blue[1].setModel(new DefaultComboBoxModel(minute));
		box_off_fan[1].setModel(new DefaultComboBoxModel(minute));
		box_off_sensor[1].setModel(new DefaultComboBoxModel(minute));
		box_off_main[1].setModel(new DefaultComboBoxModel(minute));
		

	}
	void callback()
	{
		gui.si.ctl.callback_time(new Set_Time()
		{
			@Override
			public void set_led(String[] red, String[] blue, String[] fan) {
				for(int i = 0 ; i < red.length; i++)
				{
					jl_now_red[i].setText(red[i]);
					jl_now_blue[i].setText(blue[i]);
					jl_now_fan[i].setText(fan[i]);
				}
				
			}
			@Override
			public void set_time_sensor(String hour, String minute) {
				jl_now_sensor[0].setText(hour + " : " + minute);
				
			}

			@Override
			public void set_time_main(String hour, String minute) {
				jl_now_main[0].setText(hour + " : " + minute);
			}
			
		});
	}
	public ImageIcon trans_img(String name, int w, int h) {
		ImageIcon origin = new ImageIcon(name);
		Image origin_img = origin.getImage();
		Image changedImg = origin_img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changedImg);
		return icon;
	}

	public ImageIcon trans_img(BufferedImage ig, int w, int h) {
		ImageIcon origin = new ImageIcon(ig);
		Image origin_img = origin.getImage();
		Image changedImg = origin_img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changedImg);
		return icon;
	}
	void init_button()
	{
		int size = 70;
		String name = "push.png";
		jb_red = new JButton(trans_img(name,70,70));
		jb_red.addActionListener(this);
		jb_red.setBorderPainted(false);		
		jb_red.setContentAreaFilled(false);
		jb_red.setFocusPainted(false);
		
		jb_blue = new JButton(trans_img(name,70, 70));
		jb_blue.addActionListener(this);
		jb_blue.setBorderPainted(false);		
		jb_blue.setContentAreaFilled(false);
		jb_blue.setFocusPainted(false);
		
		jb_fan = new JButton(trans_img(name,70, 70));
		jb_fan.addActionListener(this);
		jb_fan.setBorderPainted(false);		
		jb_fan.setContentAreaFilled(false);
		jb_fan.setFocusPainted(false);
		
		jb_sensor = new JButton(trans_img(name,70, 70));
		jb_sensor.addActionListener(this);
		jb_sensor.setBorderPainted(false);		
		jb_sensor.setContentAreaFilled(false);
		jb_sensor.setFocusPainted(false);
		
		jb_main = new JButton(trans_img(name,70, 70));
		jb_main.addActionListener(this);
		jb_main.setBorderPainted(false);		
		jb_main.setContentAreaFilled(false);
		jb_main.setFocusPainted(false);
		
		jb_back = new JButton(trans_img("back.png",100, 100));
		jb_back.addActionListener(this);
		jb_back.setBorderPainted(false);		
		jb_back.setContentAreaFilled(false);
		jb_back.setFocusPainted(false);
		
	}

	void start() {
		Font ft = new Font("¸¼Àº °íµñ", Font.PLAIN, 20);
		jl_now_red = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_now_red[i] = new JLabel("", JLabel.CENTER);
			jl_now_red[i].setFont(ft);
		}

		jl_now_blue = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_now_blue[i] = new JLabel("", JLabel.CENTER);
			jl_now_blue[i].setFont(ft);
		}

		jl_now_fan = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_now_fan[i] = new JLabel("", JLabel.CENTER);
			jl_now_fan[i].setFont(ft);
		}

		jl_now_sensor = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_now_sensor[i] = new JLabel("", JLabel.CENTER);
			jl_now_sensor[i].setFont(ft);
		}

		jl_now_main = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_now_main[i] = new JLabel("", JLabel.CENTER);
			jl_now_main[i].setFont(ft);
		}

		/////////////////////////

		jl_alarm_red = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_alarm_red[i] = new JLabel("", JLabel.CENTER);
			jl_alarm_red[i].setFont(ft);
		}

		jl_alarm_blue = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_alarm_blue[i] = new JLabel("", JLabel.CENTER);
			jl_alarm_blue[i].setFont(ft);
		}

		jl_alarm_fan = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_alarm_fan[i] = new JLabel("", JLabel.CENTER);
			jl_alarm_fan[i].setFont(ft);
		}

		jl_alarm_sensor = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_alarm_sensor[i] = new JLabel("", JLabel.CENTER);
			jl_alarm_fan[i].setFont(ft);
		}

		jl_alarm_main = new JLabel[2];
		for (int i = 0; i < jl_now_red.length; i++)
		{
			jl_alarm_main[i] = new JLabel("", JLabel.CENTER);
			jl_alarm_main[i].setFont(ft);
		}

	}

	void repeat(JLabel[] jl) {
		System.out.println("?");
		jl = new JLabel[2];

		for (int i = 0; i < jl.length; i++)
			jl[i] = new JLabel("", JLabel.CENTER);

	}

	void repeat(JPanel jp, JLabel[] jl) {
		for (int i = 0; i < jl.length; i++) {
			jp.add(jl[i]);
		}
	}

	void repeat(JPanel jp, JComboBox[]box)
	{
		for (int i = 0; i < box.length; i++) {
			jp.add(box[i]);
		}
	}
	
	void repeat(JPanel jp, JPanel jp2)
	{
		jp.add(jp2);
	}
	
	void init() {
		Font ft = new Font("¸¼Àº °íµñ" , Font.BOLD, 20);
		Font ft2 = new Font("¸¼Àº °íµñ" , Font.PLAIN, 20);
		
		jl_alarm = new JLabel(trans_img("watch.png", 150, 150));
		
		init_button();
		
		start();
		JPanel jp_grid = new JPanel(new GridLayout(4, 6, 10, 10));
		jp_grid.setBackground(Color.white);

		JLabel jl = new JLabel("");
		JLabel jl_img1 = new JLabel("", JLabel.CENTER);
		JLabel jl_img2 = new JLabel("", JLabel.CENTER);
		JLabel jl_img3 = new JLabel("", JLabel.CENTER);
		JLabel jl_img4 = new JLabel("Sensor", JLabel.CENTER);
		JLabel jl_img5 = new JLabel("Main", JLabel.CENTER);
		
		int size = 70;
		ImageIcon ig1 = trans_img("red_led.png",size,size);
		ImageIcon ig2 = trans_img("blue_led.png",size,size);
		ImageIcon ig3 = trans_img("fan.png",size,size);
	
		jl_img1.setIcon(ig1);
		jl_img2.setIcon(ig2);
		jl_img3.setIcon(ig3);
		jl_img4.setFont(ft);
		jl_img5.setFont(ft);
		
		
		// 1 Çà
		jp_grid.add(jl);
		jp_grid.add(jl_img1);
		jp_grid.add(jl_img2);
		jp_grid.add(jl_img3);
		jp_grid.add(jl_img4);
		jp_grid.add(jl_img5);

		// 2Çà

		JPanel[] jp_grid2 = new JPanel[6];
		for (int i = 0; i < jp_grid2.length; i++) {
			jp_grid2[i] = new JPanel(new GridLayout(2, 0));
			jp_grid2[i].setBackground(Color.white);
		}

		JLabel jl_on = new JLabel("ON", JLabel.CENTER);
		JLabel jl_off = new JLabel("OFF", JLabel.CENTER);
		
		jl_on.setFont(ft2);
		jl_off.setFont(ft2);

		jp_grid2[0].add(jl_on);
		jp_grid2[0].add(jl_off);

		repeat(jp_grid2[1], jl_now_red);
		repeat(jp_grid2[2], jl_now_blue);
		repeat(jp_grid2[3], jl_now_fan);
		repeat(jp_grid2[4], jl_now_sensor);
		repeat(jp_grid2[5], jl_now_main);

		jp_grid.add(jp_grid2[0]);
		jp_grid.add(jp_grid2[1]);
		jp_grid.add(jp_grid2[2]);
		jp_grid.add(jp_grid2[3]);
		jp_grid.add(jp_grid2[4]);
		jp_grid.add(jp_grid2[5]);

		for (int i = 0; i < jp_grid2.length; i++) {
			jp_grid.add(jp_grid2[i]);
		}

		// 3Çà

		JPanel[] jp_grid3 = new JPanel[6];
		JPanel[] jp_col = new JPanel[10];
		for (int i = 0; i < jp_grid2.length; i++) {
			jp_grid3[i] = new JPanel(new GridLayout(2, 0));
			jp_grid3[i].setBackground(Color.white);

		}
		for(int i = 0 ; i < jp_col.length; i++)
		{
			jp_col[i] = new JPanel(new GridLayout(0,2));
			jp_col[i].setBackground(Color.white);
		}

		JLabel jl_on2 = new JLabel("ON", JLabel.CENTER);
		JLabel jl_off2 = new JLabel("OFF", JLabel.CENTER);
		
		jl_on2.setFont(ft2);
		jl_off2.setFont(ft2);
		
		jp_grid3[0].add(jl_on2);
		jp_grid3[0].add(jl_off2);

		
		repeat(jp_col[0], box_on_red);
		repeat(jp_col[1], box_off_red);
		repeat(jp_col[2], box_on_blue);
		repeat(jp_col[3], box_off_blue);
		repeat(jp_col[4], box_on_fan);
		repeat(jp_col[5], box_off_fan);
		repeat(jp_col[6], box_on_sensor);
		repeat(jp_col[7], box_off_sensor);
		repeat(jp_col[8], box_on_main);
		repeat(jp_col[9], box_off_main);
		
		jp_grid3[1].add(jp_col[0]);
		jp_grid3[1].add(jp_col[1]);
		
		jp_grid3[2].add(jp_col[2]);
		jp_grid3[2].add(jp_col[3]);
		
		jp_grid3[3].add(jp_col[4]);
		jp_grid3[3].add(jp_col[5]);
		
		jp_grid3[4].add(jp_col[6]);
		jp_grid3[5].add(jp_col[8]);

		jp_grid.add(jp_grid3[0]);
		jp_grid.add(jp_grid3[1]);
		jp_grid.add(jp_grid3[2]);
		jp_grid.add(jp_grid3[3]);
		jp_grid.add(jp_grid3[4]);
		jp_grid.add(jp_grid3[5]);
		
		/// 4Çà
		
		JLabel jl3 = new JLabel("");
		jp_grid.add(jl3); // °ø¹é
		jp_grid.add(jb_red);
		jp_grid.add(jb_blue);
		jp_grid.add(jb_fan);
		jp_grid.add(jb_sensor);
		jp_grid.add(jb_main);
		
		////////////////////
		
		JLabel jl_now = new JLabel(trans_img("now.png",size,size));
		JLabel jl_setting = new JLabel(trans_img("setting.png",size-17,size-15));
		
		jl_now.setBounds(8, 317, size, size);
		jl_setting.setBounds(8, 415, size, size);
		
		jp_grid.setBounds(70, 200, 700, 400);
		
		
		jb_back.setBounds(0, 0, 90, 90);
		jl_alarm.setBounds(270, 0, 250, 200);
		
		ct.add(jb_back);
		ct.add(jl_alarm);
		ct.add(jp_grid);
		ct.add(jl_now);
		ct.add(jl_setting);
	}

	void set_alarm(JComboBox[] on_jbox, JComboBox[] off_jbox,  String [] hour, String [] minute)
	{
		hour[0] = (String) on_jbox[0].getSelectedItem();
		if(hour[0].length() == 2)
			hour[0] = "0"+hour[0];
		hour[0] = hour[0].substring(0, 2);
		
		minute[0] = (String) on_jbox[1].getSelectedItem();
		if(minute[0].length() == 2)
			minute[0] = "0"+minute[0];
		minute[0] = minute[0].substring(0, 2);
		
		hour[1] = (String) off_jbox[0].getSelectedItem();
		if(hour[1].length() == 2)
			hour[1] = "0"+hour[1];
		hour[1] = hour[1].substring(0, 2);
		
		minute[1] = (String) off_jbox[1].getSelectedItem();
		if(minute[1].length() == 2)
			minute[1] = "0"+minute[1];
		minute[1] = minute[1].substring(0, 2);
	}
	
	public void set_time_sensor()
	{
		String hour = (String)box_on_sensor[0].getSelectedItem();
		String minute = (String)box_on_sensor[1].getSelectedItem();
		
		if(hour.length() == 2 )
			hour = "0"+hour;
		hour = hour.substring(0, 2);
		
		if(minute.length() == 2)
			minute = "0"+minute;
		minute = minute.substring(0, 2);
		
		gui.si.si_writer.set_time_sensor(hour, minute);
	}
	
	public void set_time_main()
	{
		String hour = (String)box_on_main[0].getSelectedItem();
		String minute = (String)box_on_main[1].getSelectedItem();
		
		if(hour.length() == 2 )
			hour = "0"+hour;
		hour = hour.substring(0, 2);
		
		if(minute.length() == 2)
			minute = "0"+minute;
		minute = minute.substring(0, 2);
		
		gui.si.si_writer.set_time_main(hour, minute);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String hour[] = new String[2];
		String minute[] = new String[2];
		if(e.getSource().equals(jb_red))
		{		
			set_alarm(box_on_red,box_off_red, hour, minute);
			gui.si.si_writer.set_alarm(1, hour, minute);
		}
		else if(e.getSource().equals(jb_blue))
		{
			set_alarm(box_on_blue,box_off_blue, hour, minute);
			gui.si.si_writer.set_alarm(2, hour, minute);
		}
		else if(e.getSource().equals(jb_fan))
		{
			set_alarm(box_on_fan,box_off_fan, hour, minute);
			gui.si.si_writer.set_alarm(3, hour, minute);
		}
		else if(e.getSource().equals(jb_sensor))
		{
			set_time_sensor();
		}	
		
		else if(e.getSource().equals(jb_main))
		{
			set_time_main();
		}
		else if(e.getSource().equals(jb_back))
		{
			setVisible(false);
			gui.setVisible(true);
		}

	}

}