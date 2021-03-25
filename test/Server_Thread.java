package TCP_달력_변환기_서버;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JTextArea;

public class Server_Thread extends Thread{
	int port;
	ServerSocket ss;
	Socket s;
	BufferedReader br;
	BufferedWriter bw;
	
	JTextArea jta;
	String day="";
	String content="";
	boolean ch = true; // 체크
	public Server_Thread(int port, JTextArea jta)
	{
		this.port = port;
		this.jta = jta;
		try {
			ss = new ServerSocket(port);

			s = ss.accept();
			jta.append("클라이언트 입장!(생성자)"+"\r\n");
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void new_socket()
	{

		try {
			
			ss = new ServerSocket(port);
			s = ss.accept();
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			if(!ss.isClosed())
			{
				System.out.println("서버가 열렸습니다.");
			}
			jta.append("클라이언트 입장!"+"\r\n");
			
			ch = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void close_socket()
	{
		try {
			ch = false;
			ss.close();
		
			if(ss.isClosed())
			{
				System.out.println("서버가 닫혔습니다.");
			}
			else
			{
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void message()
	{
		
		try {
			bw.write(content);
			bw.newLine();
			bw.flush();
			jta.append("달력 전송 완료"+"\r\n");
			content ="";
			close_socket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	void calendar()
	{
		if(!day.equals(null))
		{
			System.out.println("캘린더");
			String[] t = day.split("-");
			int year = Integer.parseInt(t[0]);
			int month =  Integer.parseInt(t[1]);
			GregorianCalendar cal = new GregorianCalendar(year,month-1,1);
			int maxd = cal.getActualMaximum(Calendar.DATE);
			int sday = cal.get(Calendar.DAY_OF_WEEK);
			
			content+="<"+t[0]+"년 " + month+"월>"+"\r\n";
			content +="Sun\tMon\tTue\tWen\tThu\tFri\tSat"+"\r\n";
			for(int i = 1 ; i< sday ; i++)
				content+="\t";
			
			for(int i= 1; i <=maxd; i++)
			{
				if(i>0 && i<10)
					content+=i+"\t";
				
				else
					content+=i+"\t";
				
				if(sday % 7 ==0)
					content+="\r\n";
				
				sday++;
			}
		}
		
	
	}
	public void run()  // 계속해서 read
	{

		while(true)
		{
		
			try {
				
				
						day = br.readLine(); // 대기 : 외부로부터 누군가 오기를 기다리는 ( 메세지가 오면 읽는거임 ) 즉, 수신을 위한 대기  
						jta.append(day+"\r\n");
						calendar();
						message();
						day ="";

	
					
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
	}
}
