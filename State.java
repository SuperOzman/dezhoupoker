/** 
 * @author  Ozman e-mail:zhouxin.max@hotmail.com
 * @date ����ʱ�䣺2015��5��24�� ����9:59:11 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class State {
	public static final int PREFLOP = 1;
	public static final int FLOP = 2;
	public static final int TURN = 3;
	public static final int RIVER = 4;
	
	public int round = 0; //��ǰ�غϣ���preFlop,flop,turn,river
	/*
	 * �������ݵ�ͳ��
	 */
	public int numInpot = 0;//�þ����������
	public int numGuaranteed;//�Ѿ���ע�������������raise��call��all_in(������äע)
	public int numRaisePlayer;//rasie�������
	public int numCallPlayer;//call�������
	public int numAllInPlayer;//all_in�������
	public boolean isFirstRoundBet = true;
	/*
	 * СäλnumInpot-1,��äλnumInpot-2,���˴�����ֱ��buttonλ��Ϊ0
	 * �����ҵ�λ����
	 */
	public int myPositin;//�ҵ�λ��  	
	public int sbPosition;//Сäλ
	public int bbPosition;//��äλ
	
	public int potSize;
	public int BB;//��ä���
	public int callCost;//call��Ҫ���ѵĶ�ע
	public int raiseCost;//raise��Ҫ���ѵĶ�ע
	public int myRestJetton;
	public boolean not_already_voluntarily_put_money = true; //������˼
	
	
}

