import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class Player extends Thread implements Message_CallBack{
	
	Socket s;
	InputStream is;			// 받아오기
	DataInputStream dis;

	int id;
	
	OutputStream os;		// 쓰기
	DataOutputStream dos;
	Msg_Controll msg_ctl; // 메세지 컨트롤 하는 클래스 
	boolean state = true; // 현재 상태 
	
	boolean ready = false;
	Player_Management pl_mg;
	int score = 0;
	JTextArea jta;
	public Player(Socket s, Player_Management pl_mg, JTextArea jta, int id)
	{
		this.pl_mg = pl_mg;
		msg_ctl= new Msg_Controll(this, pl_mg);
		this.jta = jta;
		pl_mg.add(this); 
		this.id = id;
		this.s = s;
		
		try {
			is = s.getInputStream();
			dis = new DataInputStream(is);
			os = s.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void sendMsg(String msg)
	{
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(true)
		{
			
			try {
				String str = dis.readUTF();
				System.out.println("입장했다는데요?");
				jta.append(str+"   " + "Player " + id +"\n");
				msg_ctl.what_msg(id, str); // 메세지 판단 
			} catch (IOException e) {
//				pl_mg.remove(this); // Remove at ArrayList 
//				state = false;
				break;
			}
		}
	
	}



	@Override
	public void set_ready(boolean flag) {
		this.ready = flag;
	}



	@Override
	public boolean get_ready() {
		return ready;
	}

	@Override
	public void plus_score(int plus) {
		this.score += plus;
	}
	
	

}
