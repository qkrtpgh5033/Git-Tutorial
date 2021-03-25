import java.util.ArrayList;

// 게임의 현재 상황을 판단하는 클래스 
public class Judge extends Thread {
	
	static int MAX_NUM = 2;
	ArrayList<Player>player_info;	// Player_information
	boolean is_run = true;
	boolean game_flag = false;
	
	public Judge(ArrayList<Player> player_info)
	{
		this.player_info = player_info;
	}
	
	boolean game_flag()
	{
		return game_flag;
	}
	
	public void go_start()		// 브로드 캐스팅
	{
		System.out.println("go_start");
		for(int i = 0 ; i < player_info.size(); i++)
			player_info.get(i).sendMsg("/start");
		
	}
	public void go_end()		// 브로드 캐스팅 
	{

		System.out.println("Game_end : run");
		for(int i = 0 ; i < player_info.size(); i++)
			player_info.get(i).sendMsg("/end");
	
	}
	
	public void judge_start() // 게임 시작판단	
	{
		System.out.println("게임시작 판단 ");
		int ready_cnt=0;  
		for(int i = 0 ; i < player_info.size(); i++)
		{
			if(player_info.get(i).ready) // 플레이어가 레디 상태인지 확인
				ready_cnt++;
		}
	
		if(ready_cnt == 2) // 모두 레디 상태인지 확인
		{
			go_start();
		}
		
	}
	
	
	
	
	public void judge_end() // 게임 종료
	{
		int exit_cnt=0;  
		for(int i = 0 ; i < player_info.size(); i++)
		{
			if(player_info.get(i).ready) // 플레이어가 모두 나간 상태인지 확인
				exit_cnt++;
		}
		if(exit_cnt == player_info.size()) // 모두 나갔는지 상태확인 
			go_end();
	}
	///////////////////////////////// 위까지는 브로드 캐스팅
	
	public void opt_enter(int id ) // 상대방 입장 체크
	{		
		
		if(player_info.size() == 1) //	입장인원 1명
		{
			player_info.get(0).sendMsg("/not_exist");
		}
		else if( player_info.size() == 2 ) // 꽉 찼을 때 
		{
			for(int i = 0 ; i <player_info.size(); i++)
			{
				if(player_info.get(i).ready)
					for(int j = 0 ; j  < player_info.size(); j++)
					{
						if( i != j)
							player_info.get(j).sendMsg("/opt_ready");
					}
				else
					for(int j = 0 ; j  < player_info.size(); j++)
					{
						if( i != j)
							player_info.get(j).sendMsg("/enter");
					}
			}
		}
		
	}
	
	public void opt_rdy(int id) // 상대방 레디
	{
		for(int i = 0; i < player_info.size(); i++)
		{

			if(player_info.get(i).id != id)
			{
				player_info.get(i).sendMsg("/opt_ready");
			}
		}
	}
	
	public void opt_exit(int id)
	{
		
		int size = player_info.size();
		for(int i = 0; i < size; i++)
		{
			if(player_info.get(i).id != id)	// id가 일치하지 않을 때 
				player_info.get(i).sendMsg("/exit");
			
			else if(player_info.get(i).id == id)	// id가 일치할 때 
				player_info.remove(i);
		}
	}
	
	public void opt_unrdy(int id)
	{
		for(int i = 0; i < player_info.size(); i++)
		{
			if(player_info.get(i).id != id)
			{
				player_info.get(i).sendMsg("/opt_un_ready");
			}
		}
	}
	
	public void hit_big_ball(int id)
	{
		for(int i = 0; i < player_info.size(); i++)
			if(player_info.get(i).id != id)
				player_info.get(i).sendMsg("/big_ball");
	}
	public void hit_mid_ball(int id)
	{
		for(int i = 0; i < player_info.size(); i++)
			if(player_info.get(i).id != id)
				player_info.get(i).sendMsg("/mid_ball");
	}
	public void hit_small_ball(int id)
	{
		for(int i = 0; i < player_info.size(); i++)
			if(player_info.get(i).id != id)
				player_info.get(i).sendMsg("/small_ball");
	}
	
	public void touch_ball(int id)
	{
		for(int i = 0; i < player_info.size(); i++)
			if(player_info.get(i).id != id)
				player_info.get(i).sendMsg("/touch");
	}
	
	public void opt_score(int id)
	{
		for(int i = 0; i < player_info.size(); i++)
			if(player_info.get(i).id != id)
				player_info.get(i).sendMsg("/score");
	}
	
	public void end(int id)
	{
		for(int i = 0 ; i < player_info.size(); i++)	// 해당 아이디 플레이어 종료 됐다고 표시.
			if(player_info.get(i).id == id)
			{
				player_info.get(i).state = false;
				
			}
		
		
		int end_num = 0;
		for(int i = 0 ; i < player_info.size(); i++)	// 모두가 종료 됐는지 체크 
		{
			if(!player_info.get(i).state)
				end_num++;
		
		}
		
		if(end_num == player_info.size())	// 게임이 종료 됐다고 플레이어 들에게 알림.
		{
			if(player_info.get(0).score > player_info.get(1).score)
			{
				player_info.get(0).sendMsg("/win");
				player_info.get(1).sendMsg("/lose");
			}
			else if(player_info.get(0).score < player_info.get(1).score)
			{
				player_info.get(0).sendMsg("/lose");
				player_info.get(1).sendMsg("/win");
			}
			else if(player_info.get(0).score == player_info.get(1).score)
			{
				player_info.get(0).sendMsg("/draw");
				player_info.get(1).sendMsg("/draw");
			
			}
		}
		
	}
//	public void run()
//	{
//		opt_enter();
//
//	}
	
}
