import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial implements Runnable {

	InputStream in;  // 해당하는 시리얼 포트에대해서 입력 스트림을 만든다.
	OutputStream out; // 해당하는 시리얼 포트에 대해서 출력 스트림을 만든다.
	
			//			R			G		B		갈색		보라색	   연한 보라색            하얀색,  분홍색
	String [] color = {"FF0000","000A00","0000FF","964B00","200020", "F500F5", "000000", "ff0063"};
	 
	public Serial()
	{ 
		super();
	}
	
	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		
		if (portIdentifier.isCurrentlyOwned()) 
		{
			System.out.println("Error: Port is currently in use");
		} 
		
		else 
		{
			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) 
			{
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();
				
				System.out.println("Connect!!");
			}
			else 
			{
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	String select_color(String color)
	{
		if(color.equals("R"))
			return this.color[0];
		
		else if(color.equals("G"))
			return this.color[1];
		
		else if(color.equals("B"))
			return this.color[2];
		
		else if(color.equals("Brown"))
			return this.color[3];
		
		else if(color.equals("Purple"))
			return this.color[4];
		else if(color.equals("high_Purple"))
			return this.color[5];
		else if(color.equals("Black"))
			return this.color[6];
		else if(color.equals("Pink"))
			return this.color[7];
		
		return null;
	}
	void on_color(String idx, String color)
	{
		String packet = "02C207" + idx + select_color(color);
		packet += xor(packet)+"03";		// 패킷 완성 
		byte [] bt  = hexStringToByteArray(packet);
		
		send_packet(bt);
		
	}
	public static String xor(String packet) {
		
		byte[] bt = hexStringToByteArray(packet);
		
		byte temp = bt[1];
		
		for(int i = 2; i < bt.length; i++)
		{
			temp = (byte) ((byte) temp ^ bt[i]);
		
		}
		
		
		return String.format("%02x", temp);
		
		
	}
	void send_packet(byte[] msg)
	{
		try {
			out.write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
    public void run() {
  
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = this.in.read(buffer)) > -1) // 코드가 꽂아져 있다면 계속해서 실행 
                System.out.print("run : " + new String(buffer, 0, len));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
