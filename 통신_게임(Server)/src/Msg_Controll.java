
// �������� �޼����� ��Ʈ���ϴ� Ŭ����
public class Msg_Controll {

	String msg;
	Message_CallBack msg_callback;
	Player_Management pl_mg;
	public Msg_Controll(Message_CallBack msg_callback, Player_Management pl_mg)
	{
		this.pl_mg = pl_mg;
		this.msg_callback = msg_callback;
	}

	
	void what_msg(int id, String str)
	{
		
		if(str.equals("/ready"))			// ready
		{
			msg_callback.set_ready(true);
			pl_mg.get_judge().opt_rdy(id);	// ���濡�� ���� ǥ��
			pl_mg.get_judge().judge_start(); // ��� �����ߴ��� Ȯ��
			
		}
		else if(str.equals("/unready"))		// unready
		{
			msg_callback.set_ready(false);
			pl_mg.get_judge().opt_unrdy(id);	
		}
		
		else if(str.equals("/enter")) // �÷��̾� ���� 
			pl_mg.get_judge().opt_enter(id);
		
		else if(str.equals("/exit"))	// ������
		{
			pl_mg.get_judge().opt_exit(id);
		
		}
		else if(str.equals("/big_ball")) // ū�� ��������
		{
			pl_mg.get_judge().hit_big_ball(id);
			msg_callback.plus_score(50);
		}
		
		else if(str.equals("/mid_ball")) // �߰� ��
		{
			pl_mg.get_judge().hit_mid_ball(id);
			msg_callback.plus_score(100);
		}
		else if(str.equals("/small_ball")) // ���� ��
		{
			pl_mg.get_judge().hit_small_ball(id);
			msg_callback.plus_score(200);
		}
		else if(str.equals("/touch"))	// ���� ���� �¾��� �� 
		{
			pl_mg.get_judge().touch_ball(id);
		}
		else if(str.equals("/score"))	// ���ھ� ���ϱ� 
		{
			pl_mg.get_judge().opt_score(id);
		}
		else if(str.equals("/end"))		// ���� ����
		{
			pl_mg.get_judge().end(id);
		}
	}
	
	
	
}
