package game.master;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import embgine.core.scripts.Master;
import embgine.graphics.Sound;

public class CrushyMaster extends Master{

	public static final int TITLELAYERS = 4;
	public static final int GAMELAYERS = 4;
	
	public static final int LAYER_BACKGROUND = 0;
	public static final int LAYER_DECOR = 1;
	public static final int LAYER_GAME = 2;
	public static final int LAYER_GUI = 3;
	
	public static final int LAYER_TITLE_BACKGROUND = 0;
	public static final int LAYER_TITLE_CAT = 1;
	public static final int LAYER_TITLE_TEXT = 2;
	public static final int LAYER_TITLE_BUTTONS = 3;
	
	public static final float GRAVITY = 8;

	public static long TIMER;
	public static long BEST_TIME;
	public static String BEST_STRING;
	
	public static Sound game_music;
	public static Sound menu_music;
	public static Sound noise;
	
	@Override
	public void beginGame() {
		game_music = new Sound("game/sounds/game_song.wav");
		menu_music = new Sound("game/sounds/menu_song.wav");
		noise = new Sound("game/sounds/noise.wav");
		noise.play(true);
		readBestTime();
	}
	
	
	public static void readBestTime() {
		//make sure our directories exist
				File d = new File("data");
				if(!d.exists()) {
					d.mkdir();
				}
				
				File f = new File("data/record.txt");
				try {
					if(!f.exists()) {
						FileOutputStream out = new FileOutputStream(f);
						out.close();
						BEST_TIME = -1;
						BEST_STRING = "  NO TIME  ";
					}else {
						FileInputStream in = new FileInputStream(f);
						byte[] bytes = new byte[in.available()];
						in.read(bytes);
						if(bytes.length > 0) {
							BEST_STRING = new String(bytes);
							
							BEST_TIME += Integer.valueOf(BEST_STRING.substring(0, 2 )) * 3600000;
							BEST_TIME += Integer.valueOf(BEST_STRING.substring(3, 5 )) * 60000;
							BEST_TIME += Integer.valueOf(BEST_STRING.substring(6, 8 )) * 1000;
							BEST_TIME += Integer.valueOf(BEST_STRING.substring(9, 11)) * 10;
							
							BEST_TIME *= 1000000;
							
						}else {
							BEST_STRING = "  NO TIME  ";
							BEST_TIME = -1;
						}
						in.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	}
	
	public static void compareTimes() {
		if( BEST_TIME == -1 || (TIMER < BEST_TIME)) {
			BEST_TIME = TIMER;
			writeBestTime();
		}
		TIMER = 0;
	}
	
	private static void writeBestTime() {
		try {
			File f = new File("data/record.txt");
			FileOutputStream out = new FileOutputStream(f);
			out.write(toTime(BEST_TIME).getBytes());
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * fills a character array you give it with the time from an amount of nanoseconds.
	 * Make sure your fill array is [1]*[11]
	 * 
	 * @param fill - the array to fill
	 * @param nanos - the time to format and fill it with
	 */
	public static void charTime(char[][] fill, long nanos){
		nanos /= 1000000;
		char[] line = fill[0];
		charTime(line, 1, (nanos /= 10) % 100);
		line[2] = ':';
		charTime(line, 4, (nanos /= 100) % 60);
		line[5] = ':';
		charTime(line, 7, (nanos /= 60) % 60);
		line[8] = ':';
		charTime(line, 10, (nanos /= 60) % 100);
	}
	
	public static String toTime(long d) {
		long time = d / 1000000;
		int decimal = (int)(time / 10)%100;
		int seconds = (int)(time / 1000)%60;
		int minutes = (int)(time / 60000)%60;
		int hours   = (int)(time / 3600000);
		return(st(hours)+":"+st(minutes)+":"+st(seconds)+":"+st(decimal));
	}
	
	/**
	 * puts two chars into a char array that are the digit char representation of a long.
	 * Only works correctly for values 0 - 99 inclusive
	 * 
	 * @param place - the character array to place the digits in
	 * @param end - where to put the last digit
	 * @param value - the long to get digits from
	 */
	public static void charTime(char[] place, int end, long value) {
		
		//places the last didit of the number at end
		//adds 48 to bring it to the ascii number characters
		place[end] = (char) (value % 10 + 48);
		
		//go until the value is 10 or less
		while(value < 11) {
			//chop off a digit until we're left with the first digit
			value /= 10;
		}
		
		//place the first digit one behind the last digit
		place[end - 1] = (char) (value + 48);
		
	}
	
	private static String st(int in) {
		String s = Integer.toString(in);
		if(in<10) {
			return "0"+s;
		}else {
			return s;
		}
	}
	
}
