import java.util.Random;

public class Wall_Generate extends Thread{

	Serial si;
	CallBack cb;
	int [][] map;
	int wall_y;
	int wall_x;
	public Wall_Generate(Serial si, CallBack cb)
	{
		this.si = si;
		this.cb = cb;
		map = cb.map;
	}
	
	void find()	// ���� ���� ��ǥ�� ã�� �Լ�.
	{
		Random rd = new Random();
		
		int y = 0;
		int x = 0;
		while(true)
		{
			y = rd.nextInt(16)+1;
			x = rd.nextInt(16)+1;
			
			if(map[y][x] != 1) // ���� �ƴҰ��
				break;
		}
	
		wall_y = y;	// �� y ��ǥ
		wall_x = x;	// �� x ��ǥ

	}
	
	
	
	void move_color(String color)
	{
		int col = 19 - wall_y;
		int row = wall_x;
		
		String str_col = Integer.toHexString(col);
		String str_row = Integer.toHexString(row);
		if(str_col.length() == 1)
			str_col = "0" + str_col;
		if(str_row.length() == 1)
			str_row = "0" + str_row;
		String idx = str_col + str_row;
		si.on_color(idx, color);
	}
	
	
	
	public void run()
	{
		while(true)
		{
			if(!cb.get_isrun())
				break;
			
			find();

			try {

				move_color("high_Purple");		// �ӽ� �� 2�ʵ��� ����
				map[wall_y][wall_x] = 1;
				Thread.sleep(2000);

				move_color("Black");;
				map[wall_y][wall_x] = 0;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}
	}
}
