import java.util.*;

/** 
 * @author  Ozman zhouxin.max@hotmail.com
 * @date 创建时间：2015年5月18日 下午1:07:37 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class Deck {
	
	private List<Card> restCards = new ArrayList<Card>();
	
	public Deck(){
		this.initialise();
	}
	//初始化restcards
	public void initialise(){
		for(int i=0;i<4;i++){
			for(int j=2;j<15;j++){
				restCards.add(new Card(i,j));
			}
		}
	}
	//删除
	public void remove(List<Card> cards){
		restCards.removeAll(cards);
	}
	
	public List<Card> getRestCards(){
		return restCards;
	}
}

