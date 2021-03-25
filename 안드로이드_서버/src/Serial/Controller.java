package Serial;
import java.util.Queue;

import Management.CallBack;


public class Controller extends Thread {
	
	Serial si;
	CallBack cb;
	Queue<Packet> p;

	public Controller(Queue<Packet> p, CallBack cb)
	{
		this.p = p;
		this.cb = cb;
	}
	
	public void what_packet(byte[] bt) // 어떤 패킷이 날라왔는지 확인하는 메서드
	{
		if(packet(bt[0]).equals("02") && packet(bt[29]).equals("03")) // 올바른 패킷
		{

			if(packet(bt[1]).equals("02") && packet(bt[3]).equals("53"))		// 센서보드 프로토콜
			{
				set_sensor(bt);
//				set_time_sensor(bt);
			}
			
			else if(packet(bt[1]).equals("01"))	// 메인보드 프토콜 
			{
				if(packet(bt[3]).equals("53")) // Led state 체크
				{
					set_on_off(bt);		// On/Off
					set_time_main(bt);	// 시간
				}
				
				else if(packet(bt[3]).equals("73")) // Led Data 체크
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
		
		red[0] = packet(bt[5]);	// 시
		red[0] += " : " + packet(bt[6]);	// 분
		
		red[1] = packet(bt[7]);	// 시
		red[1] += " : " + packet(bt[8]);	// 분
		
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

	}
	
	public void set_sensor(byte[] bt)
	{
	
		String hum; // 습도
		String tem; // 온도
		String co2; // 이산화탄소
		String gas; // 가스
		String lux; // 빛 
			
		tem = packet(bt[10]).substring(1) + packet(bt[11]).substring(1) +"." +  packet(bt[12]).substring(1);
		hum = packet(bt[14]).substring(1) + packet(bt[15]).substring(1) + "." + packet(bt[16]).substring(1);
		co2 = packet(bt[18]).substring(1) + packet(bt[19]).substring(1) + packet(bt[20]).substring(1)+ packet(bt[21]).substring(1);
		gas = packet(bt[27]).substring(1) + packet(bt[28]).substring(0);
		lux = packet(bt[23]).substring(1) + packet(bt[24]).substring(1)+ packet(bt[25]).substring(1) + packet(bt[26]).substring(1);
		
		System.out.print("아일랜드 : ");
		for(int i = 0 ; i < bt.length; i++)
		{
			System.out.print(packet(bt[i])+" ");
		}
		String send = tem+"/"+hum+"/"+co2+"/"+gas+"/"+lux;
		
		cb.tcp.sendMsg(send);
		
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
		
		String send = "update_bright"+"/"+red_bright+"/"+blue_bright+"/"+fan_bright;
		cb.tcp.sendMsg(send);

	}
	
	public void set_on_off(byte[] bt)
	{
		boolean [] state = new boolean[3];
		
		for(int i = 0 ; i < state.length; i++) // 불린 변수 초기화
			state[i] = false;
		
		for(int i = 8, j = 0; i < 11; i++, j++)
		{
			if(packet(bt[i]).equals("00"))
				state[j] = false;
			else if(packet(bt[i]).equals("01"))
				state[j] = true;
		}
		
		String send = "update_on_off"+"/"+Boolean.toString(state[0])+"/"+Boolean.toString(state[1])+"/"+Boolean.toString(state[2]);
		cb.tcp.sendMsg(send);
	}
	
	public void set_time_main(byte[] bt)
	{
		System.out.println("메인에 들어온 패킷 ");
		for(int i = 0 ; i < bt.length; i++)
		{
			System.out.print(packet(bt[i])+" ");
		}
		
		String hour = packet(bt[5]);
		String minute = packet(bt[6]);		
		
		System.out.println("메인  : hour" + hour);
		System.out.println("메인  : minute" + minute);

	}
	
	public void set_time_sensor(byte[] bt)
	{
		String hour = packet(bt[7]);
		String minute = packet(bt[8]);		

	}
	
	
}
