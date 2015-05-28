package texaspoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;


/** 
 * @author  Ozman zhouxin.max@hotmail.com
 * @date 创建时间：2015年5月18日 下午1:53:00 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class HandPowerComputer {
	
	private List<Card> handCards= new ArrayList<Card>();//手牌
	private List<Card> communityCards= new ArrayList<Card>();//公共牌
	private List<Card> cards= new ArrayList<Card>();//手牌+公共牌
	private List<Card> currentStrongestHand= new ArrayList<Card>();
	private List<Card> potentialFlush = new ArrayList<Card>();
	private List<Card> potentialStraight = new ArrayList<Card>();
	private int[] countSuit = new int[4];//计算每个花色的数量
	private int[] countNumber = new int[15];//计算每种数字的数量
	
//	public HandPowerComputer(List<Card> handCards, List<Card> communityCards) {
//		this.handCards = handCards;
//		this.communityCards = communityCards;
//		cards.addAll(handCards);
//		if(communityCards != null)
//			cards.addAll(communityCards);
//	}
	
	public void add(Card c){
		cards.add(c);
		communityCards.add(c);
	}
	
	public void addHandCard(Card card){
		cards.add(card);
		this.handCards.add(card);
	}
	
	public List<Card> getHandCards(){
		return handCards;
	}
	
	public List<Card> getCommunityCards(){
		return this.communityCards;
	}
	
	public void clear()
	{
		cards.clear();
		communityCards.clear();
		handCards.clear();
	}
	
	private void count(){
		for(int i=0;i<4;i++){
			countSuit[i]=0;
		}
		for(int i=0;i<15;i++){
			countNumber[i]=0;
		}
		currentStrongestHand.clear();
		potentialFlush.clear();
		potentialStraight.clear();
		for(Card c:cards){
			countSuit[c.getSuit()]++;
			countNumber[c.getNumber()]++;
		}
		countNumber[1]=countNumber[14];
	}
	//计算手牌的强度
	public int computeHP(){
		count();
		if(isFlushStraight()!=0){
			return isFlushStraight();
		}else if(isFourOfAKind()!=0){
			return isFourOfAKind();
		}else if(isFullHouse()!=0){
			return isFullHouse();
		}else if(isFlush()!=0){
			return isFlush();
		}else if(isStraight()!=0){
			return isStraight();
		}else if(isThreeOfAKind()!=0){
			return isThreeOfAKind();
		}else if(isTwoPair()!=0){
			return isTwoPair();
		}else if(isOnePair()!=0){
			return isOnePair();
		}else{
			return isHighCard();
		}
	}
	//判断是否为高牌
	private int isHighCard() {
		int HP = 0;
		if(cards.size()==5||cards.size()==6){
			if(hasPotentialFlush()){
				if(intersection(potentialFlush, handCards)==2) HP+=2;
				else if(intersection(potentialFlush, handCards)==1) HP+=1;
			}
			if(hasPotentialStraight()){
				if(intersection(potentialStraight, handCards)==2) HP+=2;
				else if(intersection(potentialFlush, handCards)==1) HP+=1;
			}
			System.out.println(HP);
		}	
		return HP;
	}

	//判断是否为一对
	private int isOnePair() {
		int HP = 0;
		boolean flag = false;
		int doubleNumber = -1;
		int highNumber1 = -1;
		int highNumber2 = -1;
		int highNumber3 = -1;
		for(int i = 14;i > 1;i--){
			if(countNumber[i]==2){
				if(doubleNumber==-1) doubleNumber=i;
			}
			if(countNumber[i]==1){
				if(highNumber1==-1) highNumber1=i;
				else if(highNumber2==-1) highNumber2=i;
				else if(highNumber3==-1) highNumber3=i;
			}
		}
		if(doubleNumber!=-1) flag = true;
		if(flag){
			System.out.println("当前手牌为一对");
			currentStrongestHand.clear();
			for(Card c:cards){
				if(c.getNumber()==highNumber1||c.getNumber()==highNumber2||c.getNumber()==highNumber3||c.getNumber()==doubleNumber)
					currentStrongestHand.add(c);
			}
			if(!includeIgnoreSuit(communityCards, doubleNumber) && doubleNumber>highNumber1) HP=13;
			else if(includeIgnoreSuit(handCards, doubleNumber) && includeIgnoreSuit(handCards, highNumber1) && doubleNumber>highNumber1) HP=12;
			else if(!includeIgnoreSuit(handCards, doubleNumber) ) HP=10;
			else HP=11;
//			for(Card c:currentStrongestHand){
//				System.out.println(c);
//			}
			if(cards.size()==5||cards.size()==6){
				if(hasPotentialFlush()){
					if(intersection(potentialFlush, handCards)==2) HP=13;
					else if(intersection(potentialFlush, handCards)==1) HP+=1;
				}
				if(hasPotentialStraight()){
					if(intersection(potentialStraight, handCards)==2) HP=13;
					else if(intersection(potentialFlush, handCards)==1) HP+=1;
				}
			}
			System.out.println(HP);
		}		
		return HP;
	}

	//判断是否为两对
	private int isTwoPair() {
		int HP = 0;
		boolean flag = false;
		int doubleNumber1 = -1;
		int doubleNumber2 = -1;
		int highNumber = -1;
		for(int i = 14;i > 1;i--){
			if(countNumber[i]==2){
				if(doubleNumber1==-1) doubleNumber1=i;
				else if(doubleNumber2==-1) doubleNumber2=i;
			}
			if(countNumber[i]==1){
				if(highNumber==-1) highNumber=i;
			}
		}
		if(doubleNumber1!=-1 && doubleNumber2!=-1) flag = true;
		if(flag){
			System.out.println("当前手牌为两对");
			currentStrongestHand.clear();
			for(Card c:cards){
				if(c.getNumber()==highNumber||c.getNumber()==doubleNumber1||c.getNumber()==doubleNumber2)
					currentStrongestHand.add(c);
			}
//			for(Card c:currentStrongestHand){
//			System.out.println(c);
//		}
			if(includeIgnoreSuit(handCards, doubleNumber1) && includeIgnoreSuit(handCards, doubleNumber2)) HP=23;
			else if(!includeIgnoreSuit(communityCards, doubleNumber1) && doubleNumber1>highNumber) HP=22;
			else if(intersection(currentStrongestHand, communityCards)==5) HP = 20;
			else HP = 21;
			if(cards.size()==6){
					if(hasPotentialFlush()){
						if(intersection(potentialFlush, handCards)==2) HP=23;
						else if(intersection(potentialFlush, handCards)==1) HP+=1;
					}
					if(hasPotentialStraight()){
						if(intersection(potentialStraight, handCards)==2) HP=23;
						else if(intersection(potentialFlush, handCards)==1) HP+=1;
					}
			}
			System.out.println(HP);
		}	
		return HP;
	}

	//判断是否为三条
	private int isThreeOfAKind() {
		int HP = 0;
		boolean flag = false;
		int trebleNumber = -1;
		int highNumber1 = -1;
		int highNumber2 = -1;
		for(int i=14;i>1;i--){
			if(countNumber[i]==3){
				flag = true;
				trebleNumber = i;
			}
			if(countNumber[i]==1){
				if(highNumber1==-1) highNumber1=i;
				else if(highNumber2==-1) highNumber2=i;
			}
		}
		if(flag){
			System.out.println("当前手牌为三条");
			currentStrongestHand.clear();
			int maxNumber = -1;
			for(Card c:cards){
				if(trebleNumber==c.getNumber()||highNumber1==c.getNumber()||highNumber2==c.getNumber()) currentStrongestHand.add(c);
			}
//			for(Card c:currentStrongestHand){
//				System.out.println(c);
//			}
			if(intersection(currentStrongestHand, handCards)==2 &&
				includeIgnoreSuit(communityCards, highNumber1) &&
				includeIgnoreSuit(communityCards, highNumber2)) HP = 33;
			else if(intersection(currentStrongestHand, handCards)==2 &&
					includeIgnoreSuit(handCards, trebleNumber)) HP = 32;
			else if(intersection(currentStrongestHand, handCards)>=1 &&
					includeIgnoreSuit(handCards, highNumber1) &&
					highNumber1==14) HP = 31;
			else HP = 30;
			System.out.println(HP);
		}
		
		return HP;
	}

	//判断是否为顺子
	private int isStraight() {
		int HP = 0;
		boolean flag = false;
		int index = -1;		//连续牌的最高排位
		int temp = 0;
		for(int i=1;i<15;i++){
			if(countNumber[i]>=1) temp++;
			else temp = 0;
			if(temp>=5) {
				flag = true;
				index = i;
			}
		}
		
		if(flag){
			int straightNum = 0;
			System.out.println("当前手牌为顺子");	
			currentStrongestHand.clear();
			for(int i=index;i>=1;i--){
				if(countNumber[i]<1) break;
				if(i==1) i=14;
				for(Card c:cards){
					if(c.getNumber()==i && !includeIgnoreSuit(currentStrongestHand, c.getNumber())) {
						currentStrongestHand.add(c);	
						straightNum++;
					}
				}
				if(straightNum==5) {
					index=i;
					break;
				}
			}
			for(Card c:currentStrongestHand){
			System.out.println(c);
		}
			if(intersectionIgnoreSuit(currentStrongestHand, handCards)==2) HP=43;
			else if(intersectionIgnoreSuit(currentStrongestHand, handCards)==1){
				if(includeIgnoreSuit(handCards, index) && index<10) HP=41;
				else HP=42;
			}else HP=40;
			System.out.println(HP);
		}
		
		return HP;
		
	}

	//判断是否同花
	private int isFlush() {
		int HP = 0;
		boolean flag = false;
		int flushSuit = -1;
		for(int i=0;i<4;i++){
			if(countSuit[i]>=5){
				flushSuit = i;
				flag=true;
				break;
			}
		}
		if(flag){
			System.out.println("当前手牌为同花");
			int[] flushNumber = new int[7];
			int index = 0;
			currentStrongestHand.clear();
			for(Card c:cards){
				if(c.getSuit()==flushSuit) {
					flushNumber[index]=c.getNumber();
					index++;
				}
			}
			Arrays.sort(flushNumber);
			for(int i=6;i>=2;i--){
				for(Card c:cards){
					if(c.getNumber()==flushNumber[i]) {
						currentStrongestHand.add(c);
						break;
					}
				}
			}
			for(Card c:currentStrongestHand){
				System.out.println(c);
			}
			if(intersection(currentStrongestHand, handCards)==2) HP = 53;
			else if(intersection(currentStrongestHand, handCards) ==1 &&
					(includeIgnoreSuit(handCards, flushNumber[6])||
					includeIgnoreSuit(handCards, flushNumber[5]))) HP = 52;
			else if(intersection(currentStrongestHand, handCards) ==0) HP = 50;
			else HP = 51;
			System.out.println(HP);
		}	
		return HP;
	}

	//判断是否为葫芦
	private int isFullHouse() {
		int HP = 0;
		boolean flag = false;
		int doubleNumber = -1;
		int trebleNumber = -1;
		for(int i=14;i>1;i--){
			if(countNumber[i]==3){
				if(trebleNumber==-1) trebleNumber = i;
			}
			if(countNumber[i]>=2){
				if(doubleNumber==-1 && trebleNumber!=i) doubleNumber = i;
			}
		}
		if(doubleNumber!=-1 && trebleNumber!=-1) flag = true;
		if(flag){
			System.out.println("当前手牌为葫芦");
			currentStrongestHand.clear();
			for(Card c:cards){
				if(c.getNumber()==doubleNumber || c.getNumber()==trebleNumber) currentStrongestHand.add(c);
			}
			if(currentStrongestHand.size()==6){
				for(Card c:currentStrongestHand){
					if(c.getNumber()==doubleNumber) {
						currentStrongestHand.remove(c);
						break;
					}
				}
			}
//			for(Card c:currentStrongestHand){
//				System.out.println(c);
//			}
			if(intersection(currentStrongestHand, handCards)==2 && 
					includeIgnoreSuit(handCards,trebleNumber) && 
					includeIgnoreSuit(handCards, doubleNumber))  HP=63;
			else if(intersection(currentStrongestHand, handCards)>=1 &&
					((trebleNumber>doubleNumber && includeIgnoreSuit(handCards,trebleNumber)) ||
					(doubleNumber>trebleNumber && includeIgnoreSuit(handCards, doubleNumber)))) HP=62;
			else if(intersection(currentStrongestHand, handCards)==0) HP=60;
			else HP = 61;
			System.out.println(HP);
		}		
		return HP;
	}

	//判断是否为四条
	private int isFourOfAKind() {
		int HP = 0;
		boolean flag=false;
		int fourOfAKindNumber = -1;
		int maxNumber = -1;
		for(int i=14;i>1;i--){
			if(countNumber[i]==4){
				flag=true;
				fourOfAKindNumber=i;
			}else if(countNumber[i]>=1){
				if(maxNumber==-1) maxNumber=i;
			}
		}
		if(flag){
			System.out.println("当前手牌为四条");
			currentStrongestHand.clear();
			for(Card c:cards){
				if(fourOfAKindNumber==c.getNumber()) currentStrongestHand.add(c);
			}
			for(Card c:cards){
				if(c.getNumber()==maxNumber){
					currentStrongestHand.add(c);
					break;
				}
			}
//			for(Card c:currentStrongestHand){
//				System.out.println(c);
//			}
			if(includeIgnoreSuit(handCards, fourOfAKindNumber)) HP=71;
			else if(includeIgnoreSuit(handCards, maxNumber) &&
					((maxNumber==14 && fourOfAKindNumber<14) || 
					(maxNumber==13 && fourOfAKindNumber==14))) HP=71;
			else HP=70;
			System.out.println(HP);
		}
		return HP;
	}

	//判断是否为同花顺
	private int isFlushStraight() {
		int HP = 0;
		boolean flag=false;
		int flushSuit = -1;
		for(int i = 0;i < 4;i++){
			if(countSuit[i]>=5) {
				flushSuit=i;
				break;
			}	
		}
		if(flushSuit!=-1){
			List<Card> flushHand = new ArrayList<Card>();
			for(Card c:cards){
				if(c.getSuit()==flushSuit) flushHand.add(c);
				else continue;
			}
			int index = -1;		//连续牌的最高排位
			int temp = 0;
			int[] countFlushNumber = new int[15];
			for(Card c:flushHand){
				countFlushNumber[c.getNumber()]++;
			}
			countFlushNumber[1]=countFlushNumber[14];
			for(int i=1;i<15;i++){
				if(countFlushNumber[i]>=1) temp++;
				else temp = 0;
				if(temp>=5) {
					flag = true;
					index = i;
				}
			}
			
			if(flag){
				int straightNum = 0;
				System.out.println("当前手牌为同花顺");	
				currentStrongestHand.clear();
				for(int i=index;i>=1;i--){
					if(countFlushNumber[i]<1) break;
					if(i==1) i=14;
					for(Card c:cards){
						if(c.getNumber()==i) {
							currentStrongestHand.add(c);	
							straightNum++;
						}
					}
					if(straightNum==5) {
						index=i;
						break;
					}
				}
				for(Card c:currentStrongestHand){
					System.out.println(c);
				}
				if(intersection(currentStrongestHand, handCards)==2) HP=83;
				else if(intersection(currentStrongestHand, handCards)==1){
					if(include(handCards, new Card(flushSuit, index))) HP=81;
					else HP=82;
				}else HP=80;
					
			}
			System.out.println(HP);
		}
		
		return HP;
	}

	private boolean hasPotentialFlush(){
		boolean flag = false;
		int potentialFlushSuit = -1;
		for(int i=0;i<4;i++){
			if(countSuit[i]==4){
				flag=true;
				potentialFlushSuit=i;
				break;
			}
		}
		if(flag){
			potentialFlush.clear();
			for(Card c: cards){
				if(c.getSuit()==potentialFlushSuit) potentialFlush.add(c);
			}
		}
		return flag;
	}
	
	private boolean hasPotentialStraight(){
		boolean flag = false;
		int index = -1;		
		int temp = 0;
		for(int i=1;i<15;i++){
			if(countNumber[i]>=1) temp++;
			else temp = 0;
			if(temp==4) {
				flag = true;
				index = i;
			}
		}
		if(flag){
			potentialStraight.clear();
			for(int i=index;i>=(index-3);i--){
				for(Card c: cards){
					if(c.getNumber()==i) potentialStraight.add(c);
				}
			}
		}
		return flag ;
		
	}
	private int intersection(List<Card> currentStrongestHand,List<Card> handCards){
		int result = 0;
		for(Card c:handCards){
			if(include(currentStrongestHand, c)) result++;
		}
		return result;
	}
	private int intersectionIgnoreSuit(List<Card> currentStrongestHand,List<Card> handCards){
		int result = 0;
		for(Card c:handCards){
			if(includeIgnoreSuit(currentStrongestHand, c.getNumber())) result++;
		}
		return result;
	}
	private boolean include(List<Card> cards,Card card){
		boolean flag = false;
		for(Card c:cards){
			if(card.equals(c)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	private boolean includeIgnoreSuit(List<Card> cards,int cardNumber){
		boolean flag = false;
		for(Card c:cards){
			if(c.getNumber()==cardNumber){
				flag = true;
				break;
			}
		}
		return flag;
	}
}