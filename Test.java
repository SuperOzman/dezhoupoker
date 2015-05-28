import java.util.*;

/** 
 * @author  Ozman e-mail:zhouxin.max@hotmail.com
 * @date 创建时间：2015年5月19日 下午7:55:47 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Card> handCards = new ArrayList<Card>();
		List<Card> communityCards = new ArrayList<Card>();
		handCards.add(new Card(1, 14));
		handCards.add(new Card(1, 2));
		communityCards.add(new Card(0, 3));
		communityCards.add(new Card(0, 4));
		communityCards.add(new Card(0, 5));
		communityCards.add(new Card(0, 6));
//		communityCards.add(new Card(1, 10));
		HandPowerComputer hsc = new HandPowerComputer(handCards, communityCards);
	}

}

