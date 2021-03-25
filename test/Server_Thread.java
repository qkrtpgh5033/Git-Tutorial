package TCP_�޷�_��ȯ��_����;

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
	boolean ch = true; // üũ
	public Server_Thread(int port, JTextArea jta)
	{
		this.port = port;
		this.jta = jta;
		try {
			ss = new ServerSocket(port);

			s = ss.accept();
			jta.append("Ŭ���̾�Ʈ ����!(������)"+"\r\n");
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
				System.out.println("������ ���Ƚ��ϴ�.");
			}
			jta.append("Ŭ���̾�Ʈ ����!"+"\r\n");
			
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
				System.out.println("������ �������ϴ�.");
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
			jta.append("�޷� ���� �Ϸ�"+"\r\n");
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
			System.out.println("Ķ����");
			String[] t = day.split("-");
			int year = Integer.parseInt(t[0]);
			int month =  Integer.parseInt(t[1]);
			GregorianCalendar cal = new GregorianCalendar(year,month-1,1);
			int maxd = cal.getActualMaximum(Calendar.DATE);
			int sday = cal.get(Calendar.DAY_OF_WEEK);
			
			content+="<"+t[0]+"�� " + month+"��>"+"\r\n";
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
	public void run()  // ����ؼ� read
	{

		while(true)
		{
		
			try {
				
				
						day = br.readLine(); // ��� : �ܺηκ��� ������ ���⸦ ��ٸ��� ( �޼����� ���� �д°��� ) ��, ������ ���� ���  
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
