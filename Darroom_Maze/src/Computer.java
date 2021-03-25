import java.util.Random;
import java.util.Stack;

public class Computer extends Thread {
	
	Stack <Position> stack = new Stack <Position>();
	Position cur;

	Serial si;
	static int VISITED = 2; // �湮���� �� 
	static int BACK = 3; // �湮�ߴٰ� �ٽ� �ǵ��ƿ� ��
	static int WALL = 1;
	static int START = 5; // ��� ����
	static int UP = 0;
	static int RIGHT = 1;
	static int DOWN = 2;
	static int LEFT =3;
	int speed=200;
	int[][] maze;	// ��
	CallBack cb;
	int start_x;
	int	start_y;
	public Computer(Serial si, CallBack cb, int[][] maze, int start_x, int start_y)
	{
		this.start_x = start_x;
		this.start_y = start_y;
		this.cb = cb;
		this.si = si ;
		this.maze = maze;

		cur = new Position(start_x, start_y);
	}
	void move_color(String color)
	{
		int col = 19 - cur.y;
		int row = cur.x;
		
		String str_col = Integer.toHexString(col);
		String str_row = Integer.toHexString(row);
		
		if(str_col.length() == 1)
			str_col = "0" + str_col;
		if(str_row.length() == 1)
			str_row = "0" + str_row;
		String idx = str_col + str_row;
		si.on_color(idx, color);
	}
	
	void move_color(String color,int x, int y)
	{
		int col = 19 - y;
		int row = x;
		
		String str_col = Integer.toHexString(col);
		String str_row = Integer.toHexString(row);
		if(str_col.length() == 1)
			str_col = "0" + str_col;
		if(str_row.length() == 1)
			str_row = "0" + str_row;
		String idx = str_col + str_row;
		si.on_color(idx, color);
	}
	
	void switch_dir()
	{
		Random rd = new Random();
		int rand = rd.nextInt(4); // 0~3
		
		switch(rand)
		{
		case 0:		// �� ���⿡�� �ð����
			UP = 0;
			RIGHT = 1;
			DOWN = 2;
			LEFT = 3;
			break;
		case 1:		// ������ ���⿡�� �ð����
			UP = 3;
			RIGHT = 0;
			DOWN = 1;
			LEFT = 2;
			break;
		case 2:		// �Ʒ� ���⿡�� �ð����
			UP = 2;
			RIGHT = 3;
			DOWN = 0;
			LEFT = 1;
			break;
		case 3:		// ���� ���⿡�� �ð����
			UP = 1;
			RIGHT = 2;
			DOWN = 3;
			LEFT = 0;
			break;
		}
	}
	Position move_to(Position cur, int dir)
	{
		if(dir == UP)
		{
			cur.y--;
		}
		else if( dir == RIGHT)
		{
			cur.x++;
		}
		else if( dir == DOWN)
		{
			cur.y++;
		}
		else if( dir == LEFT)
		{
			cur.x--;
		}
		return cur;
	}
	boolean movable(Position cur, int dir)
	{
		int x = 0;
		int y = 0;
		String str_dir = "";
		if(dir == UP)
		{
			x = cur.x;
			y = cur.y - 1;
			str_dir = "UP";
		}
		else if (dir == RIGHT)
		{
			x = cur.x +1;
			y = cur.y;

			str_dir = "RIGHT";
		}
		else if(dir == DOWN)
		{
			x = cur.x;
			y = cur.y + 1;

			str_dir = "DOWN";
		}
		else if (dir == LEFT)
		{
			x = cur.x -1;
			y = cur.y;

			str_dir = "LEFT";
		}
		
		
		if(maze[y][x] != WALL && maze[y][x] != VISITED && maze[y][x] != BACK && maze[y][x] != START)	// ó������ ���� �� 
		{	
			
			if(cur.y == cb.pl.cur_y && cur.x == cb.pl.cur_x)
			{
				System.out.println("��ǥ�� �����ϴ�.");
				move_color("G", cb.pl.cur_x,  cb.pl.cur_y);
			}
			else
				move_color("Black");
			
			
			return true;
		}
		
		else				// ���̰ų� �̹� ������ �� 
			return false;
		
	}
	
	void set_speed(int ch)
	{
		if(ch == 1)
			speed = 300;
		else if(ch == 2)
			speed = 200;
		else if(ch == 3)
			speed = 100;
	}
	
	void restart()	// �湮�ϰų� BACK �� �κ� ���� 0���� �ٽ� �ʱ�ȭ ( �ٽ� ���� �� �� �ִٰ� ��ǻ�Ϳ� �˷��� ) 
	{
		for(int i = 0 ; i < maze.length; i++)
		{
			for(int j = 0 ; j < maze.length; j++)
			{
				if(maze[j][i] == VISITED || maze[j][i] == BACK)
				{
					maze[j][i] = 0;	// 0�� ���� �湮���� ���� ��.
				}
			}
		}
	}
	
	
	public void run()
	{
		boolean one_check = true;
		while(true)
		{
			if(!cb.isrun)
			{
				break;
			}
			
			
			if(maze[cur.y][cur.x] == 10)
			{
				cb.set_isrun(false);
				cb.map_dt.paint_remove();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cb.map_dt.paint_result("lose");
				cb.pl.remove_key();
				break;			
			}
			maze[cur.y][cur.x] = VISITED;	// �湮 ǥ�� 
			move_color("R");
		
			boolean forwarded = false;
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch_dir();
			for(int dir = 0 ; dir < 4 ; dir++)
			{
				if(movable(cur, dir))
				{
					int x = cur.x;
					int y = cur.y;
					stack.push(new Position(x,y));
					cur = move_to(cur, dir);
					forwarded = true;
					break;
				}
			}

			if(!forwarded)	// �������� ��� �����ε� ���� ������ �� BACK
			{
				maze[cur.y][cur.x] = BACK;
				
				if(cur.y == cb.pl.cur_y && cur.x == cb.pl.cur_x)	// ��ǥ�� ��ĥ ��� �÷��̾� ����(�ʷϻ�)
					move_color("G", cb.pl.cur_x,  cb.pl.cur_y);	

				else
					move_color("Black");
					
				if(stack.empty()) // ��� ���ƴٳ��� �� 
				{
					restart();
				}
				else	// ���ÿ� ����� ��ǥ�� ������ �� 
				{
					Position temp = stack.pop();
					cur.x = temp.x;
					cur.y = temp.y;
				}
			}
			
		
			
		}
	}
}
