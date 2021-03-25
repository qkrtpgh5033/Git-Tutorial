package Serial;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial implements Runnable {

	InputStream in;  // 해당하는 시리얼 포트에대해서 입력 스트림을 만든다.
	OutputStream out; // 해당하는 시리얼 포트에 대해서 출력 스트림을 만든다.
	Queue <Packet> p = new LinkedList<>();
	
	public Serial_Reader si_read;
	public Serial_Writer si_writer;
	public Controller ctl;
	public Serial()	
	{ 
		super();
		connect("COM3");
	}
	
	public void connect(String portName) {
		CommPortIdentifier portIdentifier;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			
			
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
					
					ctl = new Controller(p);
					si_read = new Serial_Reader(ctl, in);
					si_writer = new Serial_Writer(p, out);
					
					si_read.start();
					si_writer.start();
					
				}
				else 
				{
					System.out.println("Error: Only serial ports are handled by this example.");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
    
}
