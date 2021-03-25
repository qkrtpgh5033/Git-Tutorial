package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Serial.Serial;




public class GUI extends JFrame implements ActionListener, ChangeListener, MouseListener {

	Serial si;
	Container ct;

	JLabel jl_grass;
	JLabel jl_bright;
	
	JLabel jl_te; // 온도
	JLabel jl_hy; // 습도
	JLabel jl_tm; // 시간
	JLabel jl_Fo2; // 이산화탄소
	JLabel jl_gas; // 가스
	JLabel jl_lux; // 빛

	JLabel jl_te_value; // 온도
	JLabel jl_hy_value; // 습도
	JLabel jl_tm_value; // 시간
	JLabel jl_Fo2_value; // 이산화탄소
	JLabel jl_gas_value; // 가스
	JLabel jl_lux_value; // 빛
	
	
	JLabel jl_R;	// 0 번째 강도 1번째 pwm 2번 째 duty
	JLabel jl_B;
	JLabel jl_F;	
	
	
	
	JButton jb_alarm; // 알람
	JButton jb_refresh; 	// 새로고침

	ImageIcon ig;
	JLabel sprout;
	
	ImageIcon ig_on;
	ImageIcon ig_off;
	ImageIcon on_red;
	ImageIcon off_red;
	ImageIcon on_blue;
	ImageIcon off_blue;
	ImageIcon on_fan;
	ImageIcon off_fan;
	
	JButton bt_red_switch;
	JButton bt_blue_switch;
	JButton bt_fan_switch;
	
	JButton bt_refresh;
	JButton bt_watch;
	
	JSlider js_red;
	JSlider js_blue;
	JSlider js_fan;
	
	Font ft2;
	
	
	int bt_height;
	int bt_width;
	
	Alarm alarm;
	public GUI() {
		
		ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(Color.white);
		init();
		si = new Serial();

		callback();

		alarm = new Alarm(this);
		setSize(780, 900);
		setTitle("아일랜드");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}
	void callback()
	{
		si.ctl.callback_board(new Set_Board() 
		{

			@Override
			public void set_sensor(String hour, String min, String tem, String hum, String co2, String lux, String gas) {
				jl_tm_value.setText(hour+" : " + min);
				jl_te_value.setText(tem);
				jl_hy_value.setText(hum);
				jl_Fo2_value.setText(co2);
				jl_lux_value.setText(lux);
				jl_gas_value.setText(gas);
				
			}

			@Override
			public void set_main(int red_bright, int blue_bright, int fan_bright) {
				
				jl_R.setText(red_bright+"");
				jl_B.setText(blue_bright+"");
				jl_F.setText(fan_bright+"");
				
				js_red.setValue(red_bright);
				js_blue.setValue(blue_bright);
				js_fan.setValue(fan_bright);
				
			}

			@Override
			public void set_led_state(boolean[] state) {
				// TODO Auto-generated method stub
				
				if(state[0]) // red
					bt_red_switch.setIcon(ig_on);
				else if(!state[0])
					bt_red_switch.setIcon(ig_off);
				
				if(state[1]) // blue
					bt_blue_switch.setIcon(ig_on);
				else if(!state[1])
					bt_blue_switch.setIcon(ig_off);
				
				if(state[2]) // fan
					bt_fan_switch.setIcon(ig_on);
				else if(state[2])
					bt_fan_switch.setIcon(ig_off);
			}
			
		});
		
		si.si_writer.sensor_check();
		System.out.println("띠요오오오오오오오오오오오옹");
		
	}
	public ImageIcon trans_img(String name, int w, int h )
	{
		ImageIcon origin = new ImageIcon(name);
		Image origin_img = origin.getImage();
		Image changedImg = origin_img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changedImg);
		return icon;
	}
	
	public ImageIcon trans_img(BufferedImage ig, int w, int h )
	{
		ImageIcon origin = new ImageIcon(ig);
		Image origin_img = origin.getImage();
		Image changedImg = origin_img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(changedImg);
	
		return icon;
	}
	
	
	void input_arr(JPanel jp_grid)
	{
		jp_grid.add(jl_R);
		jp_grid.add(jl_B);
		jp_grid.add(jl_F);
		
	}
	void init()
	{
		
		Font ft1 = new Font("맑은 고딕", Font.BOLD, 15);	// 제목
		ft2 = new Font("맑은 고딕", Font.BOLD, 15);	// 값
		
		
		jl_R = new JLabel("0", JLabel.CENTER);
		jl_B = new JLabel("0", JLabel.CENTER);
		jl_F = new JLabel("0", JLabel.CENTER); 
		
		jl_R.setFont(ft2);
		jl_B.setFont(ft2);
		jl_F.setFont(ft2);
		
		sprout = new JLabel(trans_img("새싹.png",250,250));
//		jl_bright = new JLabel(trans_img("조명.png",250,250));
		jl_bright = new JLabel("");
		
		jl_te = new JLabel("온도 ", JLabel.CENTER); // 온도
		jl_hy = new JLabel("습도 ", JLabel.CENTER); // 습도
		jl_tm = new JLabel("시간 ", JLabel.CENTER);  // 시간
		jl_Fo2 = new JLabel("Co2 ", JLabel.CENTER); // 이산화탄소
		jl_gas = new JLabel("Gas ", JLabel.CENTER); // 가스
		jl_lux = new JLabel("Lux ", JLabel.CENTER); // 빛
		

		jl_te.setFont(ft2);
		jl_hy.setFont(ft2);
		jl_tm.setFont(ft2);
		jl_Fo2.setFont(ft2);
		jl_gas.setFont(ft2);
		jl_lux.setFont(ft2);


		jl_te_value = new JLabel("??.? C",JLabel.CENTER); // 온도
		jl_hy_value = new JLabel("??.? %", JLabel.CENTER); // 습도
		jl_tm_value = new JLabel("??:??", JLabel.CENTER); // 시간
		jl_Fo2_value = new JLabel("?? PPM", JLabel.CENTER); // 이산화탄소
		jl_gas_value = new JLabel("?? ", JLabel.CENTER); // 가스
		jl_lux_value = new JLabel("?? ", JLabel.CENTER); // 빛
		

		jl_te_value.setFont(ft2);
		jl_hy_value.setFont(ft2);
		jl_tm_value.setFont(ft2);
		jl_Fo2_value.setFont(ft2);
		jl_gas_value.setFont(ft2);
		jl_lux_value.setFont(ft2);

		JPanel jp_grid1 = new JPanel(new GridLayout(2,6));
		
		jp_grid1.add(jl_te);
		jp_grid1.add(jl_hy);
		jp_grid1.add(jl_tm);
		jp_grid1.add(jl_Fo2);
		jp_grid1.add(jl_gas);
		jp_grid1.add(jl_lux);
		
		jp_grid1.add(jl_te_value);
		jp_grid1.add(jl_hy_value);
		jp_grid1.add(jl_tm_value);
		jp_grid1.add(jl_Fo2_value);
		jp_grid1.add(jl_gas_value);
		jp_grid1.add(jl_lux_value);
		
		jp_grid1.setBackground(Color.white);
		
		
		JPanel jp_grid2 = new JPanel(new GridLayout(2,4,5,5));
		jp_grid2.setBackground(Color.white);
		String [] str = {" ", "Red", "Blue", "Fan" };
		
		for(int i = 0 ; i < 4; i++)
		{
			JLabel jl = new JLabel(str[i], JLabel.CENTER);
			jl.setFont(ft2);
			jp_grid2.add(jl);
		}
		
		JLabel jl_r = new JLabel("Intensity(%)", JLabel.CENTER);
		jl_r.setFont(ft2);
		
		jp_grid2.add(jl_r);
		input_arr(jp_grid2);
		
		JPanel jp_grid3 = new JPanel(new GridLayout(2,0,10,30));
		jp_grid3.setBackground(Color.white);
		jp_grid3.add(jp_grid1);
		jp_grid3.add(jp_grid2);
		
		sprout.setBounds(0, 50 , 300 , 300);
		jl_bright.setBounds(35, 50, 300, 300);
		jp_grid3.setBounds(sprout.getWidth()-30, 80, 450, 230);		
		
		main_borad_ui();
	

		light();
		ct.add(jp_grid3);
		ct.add(sprout);
		ct.add(jl_bright);
		
	}
	
	void light()
	{
		int size = 70;
		
		JLabel jl_light = new JLabel(trans_img("전구2.png",80, size));
		jl_light.setBounds(100, 45, 80, size);
		ct.add(jl_light);
	}
	
	void main_borad_ui()
	{
		
		
		int size = 70;
		
		
		bt_height = 50;
		bt_width = 100;

		
			
		ig_on = trans_img("on.png", 70, 50);
		ig_off = trans_img("off.png", 70, 50);
		
		bt_red_switch = new JButton(ig_off);
		bt_blue_switch = new JButton(ig_off);
		bt_fan_switch = new JButton(ig_off);

		
		bt_red_switch.setBorderPainted(false);		
		bt_red_switch.setContentAreaFilled(false);
		bt_red_switch.setFocusPainted(false);
		
		
		bt_blue_switch.setBorderPainted(false);		
		bt_blue_switch.setContentAreaFilled(false);
		bt_blue_switch.setFocusPainted(false);
		
		bt_fan_switch.setBorderPainted(false);		
		bt_fan_switch.setContentAreaFilled(false);
		bt_fan_switch.setFocusPainted(false);
		

		
		
		bt_red_switch.addActionListener(this);
		bt_blue_switch.addActionListener(this);
		bt_fan_switch.addActionListener(this);
	
		js_red = new JSlider(0, 100, 0);
		js_blue = new JSlider(0, 100, 0);
		js_fan = new JSlider(0, 100, 0);
			
		js_red.setBackground(Color.white);
		js_blue.setBackground(Color.white);
		js_fan.setBackground(Color.white);
			
		js_red.setPaintLabels(true);
		js_red.setPaintTicks(true);
		js_red.setMajorTickSpacing(20);
			
		js_blue.setPaintLabels(true);
		js_blue.setPaintTicks(true);
		js_blue.setMajorTickSpacing(20);
			
		js_fan.setPaintLabels(true);
		js_fan.setPaintTicks(true);
		js_fan.setMajorTickSpacing(20);
			
		js_red.addChangeListener(this);
		js_blue.addChangeListener(this);
		js_fan.addChangeListener(this);
		
		js_red.addMouseListener(this);
		js_blue.addMouseListener(this);
		js_fan.addMouseListener(this);
		
		
		
		JPanel main_board = new JPanel(new GridLayout(3,4));
		
		ImageIcon ig_red_led = trans_img("red_led.png",size,size);
		ImageIcon ig_blue_led = trans_img("blue_led.png",size,size);
		ImageIcon ig_fan_led = trans_img("Fan.png",size,size);
		
		
		
		
		
		// 1행
		main_board.add(new JLabel(""));
		main_board.add(new JLabel(ig_red_led, JLabel.CENTER));
		main_board.add(new JLabel(ig_blue_led, JLabel.CENTER));
		main_board.add(new JLabel(ig_fan_led, JLabel.CENTER));
		
		//2행
		JLabel jl1 = new JLabel("Switch", JLabel.CENTER);
		jl1.setFont(ft2);
		main_board.add(jl1);
		main_board.add(bt_red_switch);
		main_board.add(bt_blue_switch);
		main_board.add(bt_fan_switch);
		
		
		//3행
		JLabel jl3 = new JLabel("Intensity(%)", JLabel.CENTER);
		jl3.setFont(ft2);
		main_board.add(jl3);
		main_board.add(js_red);
		main_board.add(js_blue);
		main_board.add(js_fan);
		

		
		
		main_board.setBackground(Color.white);
		main_board.setBounds(0, sprout.getHeight() ,750, 470);
		
		
		down_side();
		ct.add(main_board);
	}
	
	void down_side()
	{
		JPanel jp_grid = new JPanel(new GridLayout(0,2));
		jp_grid.setBackground(Color.white);
		
		
		bt_refresh = new JButton(trans_img("refresh.png",70, 70));
		bt_refresh.addActionListener(this);
		bt_refresh.setBorderPainted(false);		
		bt_refresh.setContentAreaFilled(false);
		bt_refresh.setFocusPainted(false);
		
		bt_watch = new JButton(trans_img("watch.png",70, 70));
		bt_watch.addActionListener(this);
		bt_watch.setBorderPainted(false);		
		bt_watch.setContentAreaFilled(false);
		bt_watch.setFocusPainted(false);
		
		jp_grid.add(bt_refresh);
		jp_grid.add(bt_watch);
		
		
		jp_grid.setBounds(0, 765, 750, 70);
		ct.add(jp_grid);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index;
		if(e.getSource().equals(bt_red_switch))  // red On/Off
		{
			index = 0;
			if(bt_red_switch.getIcon().equals(ig_on))		// ON
			{
				bt_red_switch.setIcon(ig_off);
				si.si_writer.off_led(index);
			}
			else if(bt_red_switch.getIcon().equals(ig_off))		// OFF
			{
				bt_red_switch.setIcon(ig_on);
				si.si_writer.on_led(index);
			}
			
		}
		
		else if(e.getSource().equals(bt_blue_switch)) // blue On/Off
		{
			index = 1; 
			if(bt_blue_switch.getIcon().equals(ig_on))		// ON
			{
				bt_blue_switch.setIcon(ig_off);
				si.si_writer.off_led(index);
			}
			else if(bt_blue_switch.getIcon().equals(ig_off))		// OFF
			{
				bt_blue_switch.setIcon(ig_on);
				si.si_writer.on_led(index);
			}
		}
		
		else if(e.getSource().equals(bt_fan_switch)) // fan On/Off
		{
			index = 2;
			if(bt_fan_switch.getIcon().equals(ig_on))		// ON
			{
				bt_fan_switch.setIcon(ig_off);
				si.si_writer.off_led(index);
			}
			else if(bt_fan_switch.getIcon().equals(ig_off))		// OFF
			{
				bt_fan_switch.setIcon(ig_on);
				si.si_writer.on_led(index);
			}
		}
		
		if(e.getSource().equals(bt_refresh))
			si.si_writer.refresh();
		
		if(e.getSource().equals(bt_watch))
		{
			setVisible(false);
			alarm.setVisible(true);
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
	
		

		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	
		if(e.getSource().equals(js_red))		// red 슬라이더에서 마우스를 뗐을 때 
		{
			String ch = "01";
			
			int led_bright = 255 * js_red.getValue() / 100;
			String bright = Integer.toHexString(led_bright);
			if(bright.length() == 1)
				bright = "0"+bright;
			
			si.si_writer.adjust_bright(ch, bright);
			jl_R.setText(js_red.getValue()+"");
		}
		else if(e.getSource().equals(js_blue))	
		{
			String ch = "02";
			
			int led_bright = 255 * js_blue.getValue() / 100;
			String bright = Integer.toHexString(led_bright);
			if(bright.length() == 1)
				bright = "0"+bright;
			
			si.si_writer.adjust_bright(ch, bright);
			jl_B.setText(js_blue.getValue()+"");
		}
		else if(e.getSource().equals(js_fan))
		{
			String ch = "03";
			
			int led_bright = 255 * js_fan.getValue() / 100;
			String bright = Integer.toHexString(led_bright);
			if(bright.length() == 1)
				bright = "0"+bright;
			
			si.si_writer.adjust_bright(ch, bright);
			jl_F.setText(js_fan.getValue()+"");
		}
		bright();
		
	}
	
	void bright()
	{
		int size = 70;
		int red = 255;
		int blue = 255;
		int green = 255;
		boolean flag = false; 

		
		if(js_red.getValue() > 0  && js_blue.getValue() > 0 ) // 보라색
		{
			if(js_red.getValue() >= size && js_blue.getValue() >= size) // 둘다 넘었을 때 
			{
				red = ((255 * js_red.getValue()) / 100);
				blue = ((255 * js_blue.getValue()) / 100);
//				green = 255 - ( ((255 * js_red.getValue()) /100) + ((255 * js_blue.getValue()) /100));
				green = 5;
				if(green < 0 )
					green = 0;
			}
			
			else if(js_red.getValue() >= size && js_blue.getValue() <= size)	// 한쪽만 넘었을 때 ( 빨간색 )
			{
				red = ((255 * js_red.getValue()) / 100);
				blue = ((255 * js_blue.getValue()) / 100);
				green = 20;
			}
			else if(js_blue.getValue() >= size && js_red.getValue() <= size)	// 한쪽만 넘었을 때 
			{
				red = ((255 * js_red.getValue()) / 100);
				blue = ((255 * js_blue.getValue()) / 100);
				green = 20;
			}
			if(js_red.getValue() < size && js_blue.getValue() < size)
			{
				red = 255;
				blue = 255;
//				green = 100 +(  js_red.getValue() + js_blue.getValue());
				green = 255 - (js_red.getValue() + js_blue.getValue());
			}
			System.out.println("red : " + red);
			System.out.println("blue : " + blue);
			System.out.println("green : " + green);
		}
		else
		{
			if(js_red.getValue() > 0 )		// 빨간색
			{
				red = 255;
				blue = 255 - ((255 * js_red.getValue()) /100);
				green = 255 - (255 * js_red.getValue()) /100 ;
			}
			
			if(js_blue.getValue() > 0 )		// 파란색 
			{
				blue = 255;
				red = 255 - (255 * js_blue.getValue()) /100;
				green = 255 -  (255 * js_blue.getValue()) /100 ;
			
			}
		}

		File file = new File("조명.png");
		try {
			BufferedImage bi = ImageIO.read(file);
			
			Color color = new Color(red ,green , blue);
		
			int num = 0; 
			for(int y = 0; y < bi.getHeight(); y++)
			{
				if(num == 35)
				{
					num = 0;;
					red += 5;
					green += 5;
					blue += 5;
			
					if(red>= 255)
						red = 255;
					if(green >= 255)
						green = 255;
					if(blue >= 255)
						blue = 255;
									
					color = new Color(red , green , blue);

				}
				num++;
				
				int rgb = color.getRGB();
				
				for(int x = 0 ; x <bi.getWidth(); x++)
				{
					if(bi.getRGB(x, y) != 0)	// 이미지에 색이 없으면 0
					{
						bi.setRGB(x, y, rgb);
					}
				}
			}
			
			jl_bright.setIcon(trans_img(bi,250,250));

		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			System.out.println("파일 생성 실패");
		}
	}
}
