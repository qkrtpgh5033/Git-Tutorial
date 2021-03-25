import java.util.Random;

public class CallBack {
	
	Player_CallBack pl_cb;

	Player pl;
	Computer com;
	Serial si;
	Map_Data map_dt;
	Wall_Generate wall_gen;
	int [][] map;
	boolean isrun = true;
	public CallBack(Serial si, int [][] map,Map_Data map_dt)
	{
		Random rd = new Random();
		this.map_dt = map_dt;
		this.si = si;
		this.map = map;
		

	
		
		int start_x = rd.nextInt(16)+1;
		int start_y = map.length-1;
		
		map[0][1] = 10;	// 탈출지점
	
		map[map.length-1][start_x] = 5;	// 출발지점
		
		map_dt.paint(0,1,"B");
		map_dt.paint(start_y,start_x,"B");	// 컴퓨터 출발지점
		map_dt.paint(start_y,start_x,"B");	// 플레이어 출발지점 
		
		
		pl = new Player(si, this, map, start_x, start_y-1);
		com = new Computer(si, this, map, start_x, start_y-1 );
		wall_gen = new Wall_Generate(si, this);
		
		pl_cb = new Player_CallBack()
		{

			@Override
			public void select_dir(int dir) {
				// TODO 자동 생성된 메소드 스텁
			
			}

			@Override
			public void jb_movable(boolean [] dir) {
				for(int i = 0 ; i < dir.length; i++)
				{
					if(dir[i] == true)
						pl.jb[i].setEnabled(true);
					else
						pl.jb[i].setEnabled(false);
				}
			}
			
		};
	}
	
	public boolean get_isrun()
	{
		return this.isrun;
	}
	public void set_isrun(boolean flag)
	{
		this.isrun = flag;
	}
}
