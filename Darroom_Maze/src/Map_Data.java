import java.util.Random;

public class Map_Data extends Thread implements Map_CallBack {
	Serial si;
	Computer com;
	boolean isrun = true;
	CallBack cb;
	static int[][] map;
	private int start_x;
	private int start_y;
	
	static int[][] win = 
	{ 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0 },
			{ 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0 },
			{ 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0 },
			{ 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
	};
	
	static int[][] lose = 
		{ 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0 },
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 },
				{ 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		};                                                                                                                                      
	
	public Map_Data(Serial si) {
		this.si = si;

	}


	public void paint_result(String str_result) {
		
		int [][] arr=null;
		if(str_result.equals("win"))
			arr = win;
		else if(str_result.equals("lose"))
			arr = lose;
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				int col = 19 - i;
				int row = j;

				String str_col = Integer.toHexString(col);
				String str_row = Integer.toHexString(row);
				if (str_col.length() == 1)
					str_col = "0" + str_col;
				if (str_row.length() == 1)
					str_row = "0" + str_row;

				String idx = str_col + str_row;

				if (arr[i][j] == 1)
					si.on_color(idx, "Brown");

			}
		}
	}

	public void paint_remove() {
		si.send_packet(si.hexStringToByteArray("02C005000000C503"));
	}

	int[][] get_map() {
		return map;
	}
	
	void show_setting()
	{
		for (int y = 0; y < map.length; y++) // 미로 만들기 전, 길을 다 막아버림
		{
			for (int x = 0; x < map.length; x++) {
				int col = 19 - y;
				int row = x;

				String str_col = Integer.toHexString(col);
				String str_row = Integer.toHexString(row);
				if (str_col.length() == 1)
					str_col = "0" + str_col;
				if (str_row.length() == 1)
					str_row = "0" + str_row;
				String idx = str_col + str_row;
				if (map[y][x] == 1)
					si.on_color(idx, "Purple");
			}
		}
	}
	
	
	void setting()	// 격자 모양으로 셋팅 
	{
		for (int y = 0; y < map.length; y++) // 미로 만들기 전, 길을 다 막아버림
		{
			for (int x = 0; x < map.length; x++) {
				if (x % 2 == 0 || y % 2 == 0)
					map[y][x] = 1;
				else
					map[y][x] = 0;
			}
		}
	}
	
	
	
	public void run()
	{
		System.out.println("실행 중,,");
		map = new int[19][19];
		setting();	//초기 격자 셋팅
		show_setting();	// 셋팅 값 보여주기 

		Random rd = new Random();

		for (int y = 0; y < map.length; y++)
		{
			for (int x = 0; x < map.length; x++) {
				
				int col = 19 - y;
				int row = x;
			
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (x % 2 == 0 || y % 2 == 0)
					continue;

				if (x == map.length - 2 && y == map.length - 2)
					continue;

				if (x == map.length - 2) { 
					String str_col = Integer.toHexString(col-1);
					String str_row = Integer.toHexString(row);
					
					if (str_col.length() == 1)
						str_col = "0" + str_col;
					if (str_row.length() == 1)
						str_row = "0" + str_row;
					
					String idx = str_col + str_row;
					map[y + 1][x] = 0;
					si.on_color(idx, "Black");
					continue;
				}

				if (y == map.length - 2) {
					String str_col = Integer.toHexString(col);
					String str_row = Integer.toHexString(row+1);
					
					if (str_col.length() == 1)
						str_col = "0" + str_col;
					if (str_row.length() == 1)
						str_row = "0" + str_row;
					
					String idx = str_col + str_row;
					map[y][x + 1] = 0;
					si.on_color(idx, "Black");
					continue;
				}

				else {
					if (rd.nextInt(2) == 0)
					{
						String str_col = Integer.toHexString(col-1);
						String str_row = Integer.toHexString(row);
						
						if (str_col.length() == 1)
							str_col = "0" + str_col;
						if (str_row.length() == 1)
							str_row = "0" + str_row;
						
						String idx = str_col + str_row;
				
						si.on_color(idx, "Black");
						map[y + 1][x] = 0;
					}
					else
					{
						String str_col = Integer.toHexString(col);
						String str_row = Integer.toHexString(row+1);
						
						if (str_col.length() == 1)
							str_col = "0" + str_col;
						if (str_row.length() == 1)
							str_row = "0" + str_row;
						
						String idx = str_col + str_row;
						si.on_color(idx, "Black");
						map[y][x + 1] = 0;
					}
				}

			}
		

		}
		

		cb = new CallBack(si, map.clone(), this);

		
	}
	public int get_start_x()
	{
		return start_x;
	}
	public int get_start_y()
	{
		return start_y;
	}
	
	void paint(int c , int row, String color)
	{
		int col = 19 - c;
		
		String str_col = Integer.toHexString(col);
		String str_row = Integer.toHexString(row);
		
		if (str_col.length() == 1)
			str_col = "0" + str_col;
		if (str_row.length() == 1)
			str_row = "0" + str_row;
		
		String idx = str_col + str_row;
		si.on_color(idx, color);
	}


	@Override
	public void paint_clear() {
		// TODO Auto-generated method stub
		
	}
}
