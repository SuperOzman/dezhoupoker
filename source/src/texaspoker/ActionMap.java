package texaspoker;

import java.util.HashMap;
import java.util.Map;

public class ActionMap {
	public static final Map<String, Integer> map = new HashMap<String, Integer>();
	static {
		map.put("check", 1);
		map.put("call", 2);
		map.put("raise", 3);
		map.put("all_in", 4);
		map.put("fold", 5);
	}
}
