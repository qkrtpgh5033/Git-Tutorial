package Serial;
import java.io.IOException;
import java.io.InputStream;

public class Serial_Reader extends Thread{
	
	Controller ctl;
	InputStream in;  // 해당하는 시리얼 포트에대해서 입력 스트림을 만든다.
	public Serial_Reader(Controller ctl, InputStream in)
	{
		this.ctl = ctl;
		this.in = in;
		
	}

	public byte[] normal_packet(byte[] bt)
	{
		byte[] normal_bt = new byte[30];
		
		int str=0;
		int end=0;
		int length = normal_bt.length; // 30
		
		for(int i = 0; i < bt.length; i++) 
		{
			if(bt[i] == 0x02 && bt[i + (length-1)]  == 0x03)
			{
				str = i;
				end = i+(length-1);
				break;
			}
		}
		
		for(int i = str, j = 0; i <= end; i++, j++)
		{
			normal_bt[j] = bt[i]; 
		}
		
		print_byte(normal_bt);

		return normal_bt;
	}
	void print_byte(byte[] bt)
	{
		for(int i = 0 ; i < bt.length; i++)
			System.out.format("%02x ", bt[i]);		
		
		System.out.println();
	}
	   public void run() {
		   
	        byte[] buffer = new byte[128];
	        int len = -1;

	            try {
					while ((len = in.read(buffer)) != -1) // 코드가 꽂아져 있다면 계속해서 실행 
					{
						
						if(len >= 30)
						{
							byte[] normal_packet = normal_packet(buffer);
							ctl.what_packet(normal_packet);
						}
						
						
					
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      
	    }
}
