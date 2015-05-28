/**  
 * @date ����ʱ�䣺2015��5��18�� ����1:02:48 
 * @version 1.0 
 * @parameter  suit��ɫ��number���� 
 * @return  
 */
public class Card {

	private int suit;/*0-3����ں컨Ƭ*/
	private int number;/*2-14��2-10��J��Q��K��A*/
		
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
