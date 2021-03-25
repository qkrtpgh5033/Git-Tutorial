import java.util.ArrayList;

// ������ ���� ��Ȳ�� �Ǵ��ϴ� Ŭ���� 
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
	
	public void go_start()		// ��ε� ĳ����
	{
		System.out.println("go_start");
		for(int i = 0 ; i < player_info.size(); i++)
			player_info.get(i).sendMsg("/start");
		
	}
	public void go_end()		// ��ε� ĳ���� 
	{

		System.out.println("Game_end : run");
		for(int i = 0 ; i < player_info.size(); i++)
			player_info.get(i).sendMsg("/end");
	
	}
	
	public void judge_start() // ���� �����Ǵ�	
	{
		System.out.println("���ӽ��� �Ǵ� ");
		int ready_cnt=0;  
		for(int i = 0 ; i < player_info.size(); i++)
		{
			if(player_info.get(i).ready) // �÷��̾ ���� �������� Ȯ��
				ready_cnt++;
		}
	
		if(ready_cnt == 2) // ��� ���� �������� Ȯ��
		{
			go_start();
		}
		
	}
	
	
	
	
	public void judge_end() // ���� ����
	{
		int exit_cnt=0;  
		for(int i = 0 ; i < player_info.size(); i++)
		{
			if(player_info.get(i).ready) // �÷��̾ ��� ���� �������� Ȯ��
				exit_cnt++;
		}
		if(exit_cnt == player_info.size()) // ��� �������� ����Ȯ�� 
			go_end();
	}
	///////////////////////////////// �������� ��ε� ĳ����
	
	public void opt_enter(int id ) // ���� ���� üũ
	{		
		
		if(player_info.size() == 1) //	�����ο� 1��
		{
			player_info.get(0).sendMsg("/not_exist");
		}
		else if( player_info.size() == 2 ) // �� á�� �� 
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
	
	public void opt_rdy(int id) // ���� ����
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
			if(player_info.get(i).id != id)	// id�� ��ġ���� ���� �� 
				player_info.get(i).sendMsg("/exit");
			
			else if(player_info.get(i).id == id)	// id�� ��ġ�� �� 
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
		for(int i = 0 ; i < player_info.size(); i++)	// �ش� ���̵� �÷��̾� ���� �ƴٰ� ǥ��.
			if(player_info.get(i).id == id)
			{
				player_info.get(i).state = false;
				
			}
		
		
		int end_num = 0;
		for(int i = 0 ; i < player_info.size(); i++)	// ��ΰ� ���� �ƴ��� üũ 
		{
			if(!player_info.get(i).state)
				end_num++;
		
		}
		
		if(end_num == player_info.size())	// ������ ���� �ƴٰ� �÷��̾� �鿡�� �˸�.
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
