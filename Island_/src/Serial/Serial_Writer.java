package Serial;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;

public class Serial_Writer extends Thread {

	
	OutputStream out;
	boolean isrun;
	Queue<Packet> p ;
	public Serial_Writer(Queue<Packet> p, OutputStream out)
	{
		this.out = out;
		isrun = true;
		this.p = p;

	}
	
	public void sensor_check()
	{
		send_packet(new Packet("0202ff53ff00ffffffffffffffffffffffffffffffffffffffffffffff03")); // Check Sensor Board 
		send_packet(new Packet("0201ff53ff00ffffffffffffffffffffffffffffffffffffffffffffff03")); // Check Led on/off
		send_packet(new Packet("0201ff73ff00ffffffffffffffffffffffffffffffffffffffffffffff03")); // Check Main Board			send_packet(new Packet("0201FF52FF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03"));
		send_packet(new Packet("0201ff52ff00ffffffffffffffffffffffffffffffffffffffffffffff03"));
	}
	
	void send_packet(Packet p)
	{
		this.p.offer(p);		// 패킷 저장
	}
	
	
	static byte[] hexStringToByteArray(String s) 
	{
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public void on_led(int i)
	{
		if( i == 0 ) // red
			send_packet(new Packet("0201ff4cff01ff01ffffffffffffffffffffffffffffffffffffffffff03"));
		
		else if( i == 1) // blue
			send_packet(new Packet("0201ff4cff02ff01ffffffffffffffffffffffffffffffffffffffffff03"));

		else if( i == 2) // fan
			send_packet(new Packet("0201ff4cff03ff01ffffffffffffffffffffffffffffffffffffffffff03"));
	}
	
	public void off_led(int i )
	{
		if( i == 0 ) // red
			send_packet(new Packet("0201ff4cff01ff00ffffffffffffffffffffffffffffffffffffffffff03"));
		
		else if( i == 1) // blue
			send_packet(new Packet("0201ff4cff02ff00ffffffffffffffffffffffffffffffffffffffffff03"));

		else if( i == 2) // fan
			send_packet(new Packet("0201ff4cff03ff00ffffffffffffffffffffffffffffffffffffffffff03"));
	
	}
	
	public void refresh()
	{
		send_packet(new Packet("0202ff53ff00ffffffffffffffffffffffffffffffffffffffffffffff03"));
	}
	
	public void adjust_bright(String ch, String bright) // 밝기 조절
	{
		String packet ="";
		
		if(ch.equals("01")) // Red
			packet = "0201ff50ff"+ch+"ff"+"0000ff"+"0000"+bright+"ffffffffffffffffffffffffffffffff03";

		else if(ch.equals("02"))
			packet =  "0201ff50ff"+ch+"ff"+"0000ff"+"0000"+bright+"ffffffffffffffffffffffffffffffff03";
		
		else if(ch.equals("03"))
			packet =  "0201ff50ff"+ch+"ff"+"0000ff"+"0000"+bright+"ffffffffffffffffffffffffffffffff03";
				
		send_packet(new Packet(packet));
	}
	
	
	public void set_alarm(int chanel , String[] hour, String[] minute)
	{
		String packet="";
		if(chanel == 1)
		{
			packet = "0201ff55ff01ff"+hour[0]+minute[0]+hour[1]+minute[1]+"ffffffffffffffffffffffffffffffffffff03";
		}
		else if(chanel == 2)
		{
			packet = "0201ff55ff02ff"+hour[0]+minute[0]+hour[1]+minute[1]+"ffffffffffffffffffffffffffffffffffff03";	
		}
		else if(chanel == 3)
		{
			packet = "0201ff55ff03ff"+hour[0]+minute[0]+hour[1]+minute[1]+"ffffffffffffffffffffffffffffffffffff03";
		}
		send_packet(new Packet(packet));

		String send_packet = "0201ff52ff00ffffffffffffffffffffffffffffffffffffffffffffff03";	// update
		send_packet(new Packet(send_packet));
	}
	
	public void set_time_main(String hour, String minute)
	{
		String packet = "0201ff54ff00ff"+hour+minute+"ffffffffffffffffffffffffffffffffffffffff03";
		send_packet(new Packet(packet));
		
	}
	public void set_time_sensor(String hour, String minute)
	{
		String packet = "0202ff54ff00ff"+hour+minute+"ffffffffffffffffffffffffffffffffffffffff03";
		send_packet(new Packet(packet));
		
	}
	
	
	public void run()
	{
		while(isrun)
		{
			if(p.size() > 0 )
			{
				Packet packet = p.peek();
				
				try {
					out.write(hexStringToByteArray(packet.packet));
					p.poll();
			
					Thread.sleep(500);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			else
				System.out.print("");
		}
	}
}
