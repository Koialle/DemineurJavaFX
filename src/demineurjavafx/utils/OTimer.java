
package demineurjavafx.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Aurélien FERNANDES
 */
public class OTimer {
	
	private static Timer timer = null;
	private static boolean isStoped = true;
	private static TimeChoice timeSet = null;
	private static int maxSeconde = -1;
	private static long currentTime = 0;

	private OTimer(){}
	
	public enum TimeChoice 
	{ 
		  MINUTE(60000)
		, SECONDE(1000)
		, MILLISECONDE(1); 
		
		public int tick;
		TimeChoice(int tick)
		{
			this.tick = tick;
		}
	}
	
	/**
	 * Permet de démarrer le chrono
	 */
	public static void start()
	{
		start(true);
	}
	
	/**
	 * Permet de démarrer le chrono
	 * @param restart le chrono doit recommencer à zéro ? (true|false)
	 */
	public static void start(boolean restart)
	{
		if (timeSet != null)
		{
			timer = new Timer(true);
			
			if (restart) currentTime = 0;
			isStoped = false;
			
			OTimer.timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					long timeTempSeconde = getSecondeByTimeSet();				
					
					if (timeTempSeconde < OTimer.maxSeconde)
						currentTime++;
					else
						isStoped = true;
					
					if (isStoped())
					{
						cancel();
						//RESET ?
					}
					
				}
			}, 0, OTimer.timeSet.tick);
		}
	}
	
	/**
	 * Permet de stoper le timer
	 */
	public static void stop() {	isStoped = true; }
	
	/**
	 * GET / SET
	 */
	
	public static Timer getTimer() { return timer; }

	public static boolean isStoped() { return isStoped; }

	public static void setMaxSeconde(int maxSeconde) { OTimer.maxSeconde = maxSeconde; }

	public static long getCurrentTime() { return currentTime; }

	public static void setTimeSet(TimeChoice timeSet) { OTimer.timeSet = timeSet; }
	
	private static long getSecondeByTimeSet()
	{
		long seconde = -1;
		switch (OTimer.timeSet)
		{
			case MILLISECONDE:
				seconde = currentTime / 1000;
				break;
			case MINUTE:
				seconde = currentTime * 60;
				break;
			case SECONDE:
				seconde = currentTime;
				break;
			default:
				seconde = currentTime;
				break;
		}
		return seconde;
	}
	
	/**
	 * Permet de récupérer le nombre de minute écoulée depuis le start (ex : 01 | 10 | 42)
	 * @return le nombre de minute au format string
	 */
	public static String getMinute()
	{
		long time = getSecondeByTimeSet();
		long minute = time / 60;
		
		String min = "";
		
		if (minute < 10)
			min += "0";
		
		return min + minute;
		
	}
	
	/**
	 * Permet de récupérer le nombre de seconde écoulée depuis le start (ex : 01 | 10 | 42)
	 * @return le nombre de seconde au format string
	 */
	public static String getSeconde()
	{
		long time = getSecondeByTimeSet();
		long minute = time / 60;
		long seconde = time - (minute * 60);
		
		String sec = "";

		if (seconde < 10)
			sec += "0";
		
		return sec + seconde;
		
	}
	
	/**
	 * Permet de récupérer le temps écoulé depuis le start au format MM:SS (ex : 00:06 | 01:42 | 12:21)
	 * @return le temps écoulé au format string
	 */
	public static String getMinuteSecondeFormat()
	{
		return getMinute() + ":" + getSeconde();	
	}
	
}
