
// 유저들의 메세지를 컨트롤하는 클래스
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
			pl_mg.get_judge().opt_rdy(id);	// 상대방에게 레디 표시
			pl_mg.get_judge().judge_start(); // 모두 레디했는지 확인
			
		}
		else if(str.equals("/unready"))		// unready
		{
			msg_callback.set_ready(false);
			pl_mg.get_judge().opt_unrdy(id);	
		}
		
		else if(str.equals("/enter")) // 플레이어 입장 
			pl_mg.get_judge().opt_enter(id);
		
		else if(str.equals("/exit"))	// 나가기
		{
			pl_mg.get_judge().opt_exit(id);
		
		}
		else if(str.equals("/big_ball")) // 큰공 맞췄을떄
		{
			pl_mg.get_judge().hit_big_ball(id);
			msg_callback.plus_score(50);
		}
		
		else if(str.equals("/mid_ball")) // 중간 공
		{
			pl_mg.get_judge().hit_mid_ball(id);
			msg_callback.plus_score(100);
		}
		else if(str.equals("/small_ball")) // 작은 공
		{
			pl_mg.get_judge().hit_small_ball(id);
			msg_callback.plus_score(200);
		}
		else if(str.equals("/touch"))	// 공이 몸에 맞았을 때 
		{
			pl_mg.get_judge().touch_ball(id);
		}
		else if(str.equals("/score"))	// 스코어 더하기 
		{
			pl_mg.get_judge().opt_score(id);
		}
		else if(str.equals("/end"))		// 게임 종료
		{
			pl_mg.get_judge().end(id);
		}
	}
	
	
	
}
