package Serial;
import java.util.Queue;

import UI.Set_Board;
import UI.Set_Time;

public class Controller extends Thread {
	
	Serial si;

	Queue<Packet> p;
	Set_Board board;
	Set_Time time;
	public Controller(Queue<Packet> p)
	{
		this.p = p;
	}
	
	public void callback_board(Set_Board board)
	{
		this.board = board;
	}
	
	public void callback_time(Set_Time time)
	{
		this.time = time;
	}
	
	public void what_packet(byte[] bt) // � ��Ŷ�� ����Դ��� Ȯ���ϴ� �޼���
	{
		if(packet(bt[0]).equals("02") && packet(bt[29]).equals("03")) // �ùٸ� ��Ŷ
		{

			if(packet(bt[1]).equals("02") && packet(bt[3]).equals("53"))		// �������� ��������
			{
				set_sensor(bt);
				set_time_sensor(bt);
			}
			
			else if(packet(bt[1]).equals("01"))	// ���κ��� ������ 
			{
				if(packet(bt[3]).equals("53")) // Led state üũ
				{
					set_on_off(bt);		// On/Off
					set_time_main(bt);	// �ð�
				}
				
				else if(packet(bt[3]).equals("73")) // Led Data üũ
				{
					set_main(bt);
				}
				
				else if(packet(bt[3]).equals("52"))
				{
					set_time(bt);
				}
				
			}
		}
	}
	

	String packet(byte bt)
	{
		return String.format("%02x", bt);
	}
	
	public void set_time(byte[] bt)
	{
		String [] red = new String[2];
		String [] blue = new String[2];
		String [] fan = new String[2];
		
		red[0] = packet(bt[5]);	// ��
		red[0] += " : " + packet(bt[6]);	// ��
		
		red[1] = packet(bt[7]);	// ��
		red[1] += " : " + packet(bt[8]);	// ��
		
		blue[0] = packet(bt[10]);
		blue[0] += " : " + packet(bt[11]);
		
		blue[1] = packet(bt[12]);
		blue[1] += " : " + packet(bt[13]);
		
		fan[0] = packet(bt[15]);
		fan[0] += " : " + packet(bt[16]);
		
		fan[1] = packet(bt[17]);
		fan[1] += " : " + packet(bt[18]);
		
		System.out.println(fan[1]);
		System.out.println(red[1]);
		System.out.println(blue[1]);
		time.set_led(red, blue, fan);
		
		
	}
	
	public void set_sensor(byte[] bt)
	{
		String hour; // �� 
		String min; // ��
		String hum; // ����
		String tem; // �µ�
		String co2; // �̻�ȭź��
		String gas; // ����
		String lux; // �� 
		
		hour = packet(bt[7]);
		min = packet(bt[8]);	
		tem = packet(bt[10]).substring(1) + packet(bt[11]).substring(1) +"." +  packet(bt[12]).substring(1) + "��C";
		hum = packet(bt[14]).substring(1) + packet(bt[15]).substring(1) + "." + packet(bt[16]).substring(1) + "%";
		co2 = packet(bt[18]).substring(1) + packet(bt[19]).substring(1) + packet(bt[20]).substring(1)+ packet(bt[21]).substring(1);
		lux = packet(bt[23]).substring(1) + packet(bt[24]).substring(1)+ packet(bt[25]).substring(1) + packet(bt[26]).substring(1);
		gas = packet(bt[27]).substring(1) + packet(bt[28]).substring(0); // �̰� �ٽ� ���ߵ�
	
		board.set_sensor(hour, min, tem, hum, co2, lux, gas);
	}
	
	public void set_main(byte[] bt)
	{		
		String red_duty = packet(bt[5]);
		String red_pwm = packet(bt[8]);
		
		String blue_duty = packet(bt[12]);
		String blue_pwm = packet(bt[15]);
		
		String fan_duty = packet(bt[19]);
		String fan_pwm = packet(bt[22]);
		
		int red_bright =(Integer.parseInt(red_duty, 16) * 100 )/ 255 ; 
		int blue_bright = (Integer.parseInt(blue_duty, 16) * 100 )/ 255 ; 
		int fan_bright =(Integer.parseInt(fan_duty, 16) * 100 )/ 255 ; 
		
	
		
		board.set_main(red_bright, blue_bright, fan_bright);
		
	}
	
	public void set_on_off(byte[] bt)
	{
		boolean [] state = new boolean[3];
		
		for(int i = 0 ; i < state.length; i++) // �Ҹ� ���� �ʱ�ȭ
			state[i] = false;
		
		for(int i = 8, j = 0; i < 11; i++, j++)
		{
			if(packet(bt[i]).equals("00"))
				state[j] = false;
			else if(packet(bt[i]).equals("01"))
				state[j] = true;
		}
		
		board.set_led_state(state);
	}
	
	public void set_time_main(byte[] bt)
	{
		System.out.println("���ο� ���� ��Ŷ ");
		for(int i = 0 ; i < bt.length; i++)
		{
			System.out.print(packet(bt[i])+" ");
		}
		
		String hour = packet(bt[5]);
		String minute = packet(bt[6]);		
		
		System.out.println("����  : hour" + hour);
		System.out.println("����  : minute" + minute);
		
		time.set_time_main(hour, minute);
	}
	
	public void set_time_sensor(byte[] bt)
	{
		String hour = packet(bt[7]);
		String minute = packet(bt[8]);		
		time.set_time_sensor(hour, minute);
	}
	
	
}
