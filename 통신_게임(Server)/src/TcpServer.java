
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class TcpServer extends Thread {

	int port;
	JTextArea jta;
	ServerSocket ss;
	Socket s;

	Player_Management play_mg;
	Judge judge;
	int input_id =1;
	public TcpServer(int port, JTextArea jta) {
		play_mg = new Player_Management();

		this.port = port;
		this.jta = jta;

		try {
			ss = new ServerSocket(port);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		while (true) {
			System.out.println("현재 플레이어 수 : " + play_mg.player_info.size());
			Socket s2 = null;
			try {
				s2 = ss.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (play_mg.player_info.size() < 2) {

				Player pl = new Player(s2, play_mg, jta, input_id++);
				pl.start();
				jta.append("클라이언트 입장" + "\n");
			
			}
			else	// 접속 불가 
			{
				System.out.println("1vs1 입니다.");
				// sendMsg
			}

		}

	}

}
