import java.util.ArrayList;


// �÷��̾� ����. 
public class Player_Management {
	public ArrayList <Player> player_info;
	Judge judge;
	public Player_Management()
	{
		
//		System.out.println("Player_Management");
		player_info = new ArrayList<Player>();
		judge = new Judge(player_info);
	}
	public void add(Player p) // �÷��̾� �߰� ( ���� ) 
	{
		player_info.add(p);
		System.out.println("add �� : ���� ������ manage : " + player_info.size());

	}
	
	public void remove(Player p)	// �÷��̾� ���� ( ������ ) 
	{
		player_info.remove(p);
		System.out.println("���� �� ������ :" + player_info.size());
	}
	Judge get_judge()
	{
		return this.judge;
	}
//	public void run()
//	{
//		while(true)
//		{
//			if(judge.game_flag())
//			{
//				System.out.println("�����");
//				judge.go_start();
//			}
//			
//		}
//	}
}
