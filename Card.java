/**  
 * @date 创建时间：2015年5月18日 下午1:02:48 
 * @version 1.0 
 * @parameter  suit花色，number数字 
 * @return  
 */
public class Card {

	private int suit;/*0-3代表黑红花片*/
	private int number;/*2-14代2-10、J、Q、K、A*/
		
	public Card(int suit, int number) {
		this.suit = suit;
		this.number = number;
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
			sSuit="黑桃";
			break;
		case 1:
			sSuit="红桃";
			break;
		case 2:
			sSuit="梅花";
			break;
		case 3:
			sSuit="方片";
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
