package 안드로이드_서버;

import Management.CallBack;

public class What_Message {

	CallBack cb;
	public What_Message(CallBack cb)
	{
		this.cb = cb;
	}
	
	public void what(String str)
	{
	
		
		if(str.equals("state_plz"))	// 안드로이드 -> 상태값 요청 
		{
			cb.si.si_writer.sensor_check();
		}
		
		else if(str.equals("on_red_plz"))
		{
			cb.si.si_writer.on_led(0);
		}
		else if(str.equals("off_red_plz"))
		{
			cb.si.si_writer.off_led(0);
		}
		
		else if(str.equals("on_blue_plz"))
		{
			cb.si.si_writer.on_led(1);
		}
		else if(str.equals("off_blue_plz"))
		{
			cb.si.si_writer.off_led(1);
		}
		
		else if(str.equals("on_fan_plz"))
		{
			cb.si.si_writer.on_led(2);
		}
		else if(str.equals("off_fan_plz"))
		{
			cb.si.si_writer.off_led(2);
		}
		else if(str.equals("update_brihgt_plz"))
		{
			cb.si.si_writer.bright_check();
		}
		else if(str.equals("update_on_off_plz"))
		{
			cb.si.si_writer.on_off_check();
		}
		else
		{
			String [] arr = str.split("/");
			String command = arr[0];
			String intensity = arr[1];
			
			if(command.equals("bright_red_plz"))
			{
				cb.si.si_writer.adjust_bright("01", intensity);
			}
			else if(command.equals("bright_blue_plz"))
			{
				cb.si.si_writer.adjust_bright("02", intensity);
			}
			
			else if(command.equals("bright_fan_plz"))
			{
				cb.si.si_writer.adjust_bright("03", intensity);
			}
			
		}
		
		
		
		
		
		
		
	}
}
