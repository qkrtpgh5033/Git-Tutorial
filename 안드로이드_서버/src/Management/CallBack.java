package Management;

import Serial.Serial;
import 안드로이드_서버.TcpServer;

public class CallBack {
	
	public TcpServer tcp;
	public Serial si;
	
	public CallBack()
	{
	}
	
	public void set_tcp(TcpServer tcp)
	{
		this.tcp = tcp;
	}
	
	public void set_serial(Serial si)
	{
		this.si = si;
	}
}
