package 안드로이드_서버;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import Management.CallBack;

public class TcpServer extends Thread {

	int port;
	JTextArea jta;
	ServerSocket ss;
	Socket socekt;
	What_Message msg;
	public TcpServer(int port, JTextArea jta, CallBack cb) {

		this.port = port;
		this.jta = jta;
		msg = new What_Message(cb);
		
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void connect()
	{
		try {
			System.out.println("연결 대기중");		
			socekt = ss.accept();
			System.out.println("연결 완료");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 송신
	public void sendMsg(String msg)
	{
		
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socekt.getOutputStream())),true);
		    out.println(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
	public void run() {
	
		String android = "android : ";
		while(true)
		{
			try {
				connect();
				//수신
				BufferedReader in = new BufferedReader(new InputStreamReader(socekt.getInputStream()));
				String str = in.readLine();
				msg.what(str);
				jta.append(android + str +"\n");
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}	
		}
	}

}

