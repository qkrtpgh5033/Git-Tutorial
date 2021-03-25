package UI;

public interface Set_Board {
	public void set_sensor(String hour, String min, String tem, String hum, String co2, String lux, String gas);
	public void set_main(int red_bright, int blue_bright, int fan_bright);
	public void set_led_state(boolean[] state); // led on/off Check
}
