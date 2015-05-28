package texaspoker;

import java.util.HashMap;
import java.util.Map;

public class HeadMeg {
	public static final Map<String, Integer> map = new HashMap<String, Integer>();
	static {
		map.put("seat/ ", 0);
		map.put("blind/ ", 1);
		map.put("hold/ ", 2);
		map.put("inquire/ ", 3);
		map.put("flop/ ", 4);
		map.put("turn/ ", 5);
		map.put("river/ ", 6);
		map.put("showdown/ ", 7);
		map.put("pot-win/ ", 8);
		map.put("game-over/ ", 9);
	}
}
