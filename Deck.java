import java.util.*;

/** 
 * @author  Ozman zhouxin.max@hotmail.com
 * @date ����ʱ�䣺2015��5��18�� ����1:07:37 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class Deck {
	
	private List<Card> restCards = new ArrayList<Card>();
	
	public Deck(){
		this.initialise();
	}
	//��ʼ��restcards
	public void initialise(){
		for(int i=0;i<4;i++){
			for(int j=2;j<15;j++){
				restCards.add(new Card(i,j));
			}
		}
	}
	//ɾ��
	public void remove(List<Card> cards){
		restCards.removeAll(cards);
	}
	
	public List<Card> getRestCards(){
		return restCards;
	}
}

