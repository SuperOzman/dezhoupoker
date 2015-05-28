package texaspoker;
/**  
 * @date ����ʱ�䣺2015��5��18�� ����1:02:48 
 * @version 1.0 
 * @parameter  suit��ɫ��number���� 
 * @return  
 */

import java.util.HashMap;
import java.util.Map;
public class Card {

	private int suit;/*0-3����ں컨Ƭ*/
	private int number;/*2-14��2-10��J��Q��K��A*/
		
	public Card(int suit, int number) {
		this.suit = suit;
		this.number = number;
	}
	
	public static final Map<String, Integer> color2Number = new HashMap<String, Integer>();
	static{
		color2Number.put("SPADES", 0);
		color2Number.put("HEARTS", 1);
		color2Number.put("CLUBS", 2);
		color2Number.put("DIAMONDS", 3);
	}
	
	public static final Map<String, Integer> point2Number = new HashMap<String, Integer>();
	static{
		point2Number.put("2", 2);
		point2Number.put("3", 3);
		point2Number.put("4", 4);
		point2Number.put("5", 5);
		point2Number.put("6", 6);
		point2Number.put("7", 7);
		point2Number.put("8", 8);
		point2Number.put("9", 9);
		point2Number.put("10", 10);
		point2Number.put("J", 11);
		point2Number.put("Q", 12);
		point2Number.put("K", 13);
		point2Number.put("A", 14);
	}
	
	public int getSuit() {
		return suit;
	}
	public int getNumber() {
		return number;
	}

	@Override
	public boolean equals(Object obj) {
		Card c = (Card) obj;
		return (this.getNumber()==c.getNumber() && this.getSuit()==c.getSuit());
	}
		
	@Override
	public String toString() {
		String sSuit = null,sNumber = null;
		switch (suit) {
		case 0:
			sSuit="����";
			break;
		case 1:
			sSuit="����";
			break;
		case 2:
			sSuit="÷��";
			break;
		case 3:
			sSuit="��Ƭ";
			break;
		}
		switch (number) {
		case 11:
			sNumber="J";
			break;
		case 12:
			sNumber="Q";
			break;
		case 13:
			sNumber="K";
			break;
		case 14:
			sNumber="A";
			break;
		default:
			sNumber=String.valueOf(number);
			break;
		}
		return sSuit+sNumber;
	}
	
}
