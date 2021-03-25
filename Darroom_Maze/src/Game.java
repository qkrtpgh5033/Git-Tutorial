public class Game {

	Computer mz;
	Serial si;
	Map_Data map_dt;

	public Game()
	{
		
		si = new Serial();

		try {
			si.connect("COM3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		map_dt = new Map_Data(si);
		
		
		map_dt.paint_remove();
		
		
		
		
		
		
		map_dt.start();
		
	}
}

