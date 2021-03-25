import java.util.ArrayList;


// 플레이어 관리. 
public class Player_Management {
	public ArrayList <Player> player_info;
	Judge judge;
	public Player_Management()
	{
		
//		System.out.println("Player_Management");
		player_info = new ArrayList<Player>();
		judge = new Judge(player_info);
	}
	public void add(Player p) // 플레이어 추가 ( 입장 ) 
	{
		player_info.add(p);
		System.out.println("add 후 : 현재 사이즈 manage : " + player_info.size());

	}
	
	public void remove(Player p)	// 플레이어 삭제 ( 나가기 ) 
	{
		player_info.remove(p);
		System.out.println("삭제 후 사이즈 :" + player_info.size());
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
//				System.out.println("여기야");
//				judge.go_start();
//			}
//			
//		}
//	}
}
