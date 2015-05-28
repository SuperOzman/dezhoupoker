/** 
 * @author  Ozman e-mail:zhouxin.max@hotmail.com
 * @date 创建时间：2015年5月24日 上午9:59:11 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class State {
	public static final int PREFLOP = 1;
	public static final int FLOP = 2;
	public static final int TURN = 3;
	public static final int RIVER = 4;
	
	public int round = 0; //当前回合，即preFlop,flop,turn,river
	/*
	 * 各种数据的统计
	 */
	public int numInpot = 0;//该局所有玩家数
	public int numGuaranteed;//已经下注的玩家数，包括raise，call，all_in(不包括盲注)
	public int numRaisePlayer;//rasie的玩家数
	public int numCallPlayer;//call的玩家数
	public int numAllInPlayer;//all_in的玩家数
	public boolean isFirstRoundBet = true;
	/*
	 * 小盲位numInpot-1,大盲位numInpot-2,依此次类推直到button位置为0
	 * 计算我的位置数
	 */
	public int myPositin;//我的位置  	
	public int sbPosition;//小盲位
	public int bbPosition;//大盲位
	
	public int potSize;
	public int BB;//大盲金额
	public int callCost;//call需要花费的赌注
	public int raiseCost;//raise需要花费的赌注
	public int myRestJetton;
	public boolean not_already_voluntarily_put_money = true; //字面意思
	
	
}

