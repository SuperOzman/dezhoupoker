import java.util.ArrayList;
import java.util.List;

/** 
 * @author  Ozman e-mail:zhouxin.max@hotmail.com
 * @date 创建时间：2015年5月24日 上午11:00:17 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class PostFlopAction {
	
	private List<Card> communityCards= new ArrayList<Card>();//公共牌
	private int[] countSuit = new int[4];//计算每个花色的数量
	private int[] countNumber = new int[15];//计算每种数字的数量
	private int numberOfPairs;
	private int numberOfSameSuit;
	private int numberOfSet;
	private boolean oneCardToStraight = false;
	private boolean twoCardsToStraight = false;
	public PostFlopAction(List<Card> communityCards) {
		this.communityCards = communityCards;
	}
	
	public void add(Card c){
		communityCards.add(c);
	}
	
	private void count(){
		for(int i=0;i<4;i++){
			countSuit[i]=0;
		}
		for(int i=0;i<15;i++){
			countNumber[i]=0;
		}
		for(Card c:communityCards){
			countSuit[c.getSuit()]++;
			countNumber[c.getNumber()]++;
		}
		countNumber[1]=countNumber[14];
		numberOfPairs = numberOfPairs();
		numberOfSameSuit = numberOfSameSuit();
		numberOfSet = numberOfSet();
		potentialStraight();
	}
	
	private int numberOfPairs(){
		int result = 0;
		for(int i=14;i>1;i--){
			if(countNumber[i]==2) result++;		
		}
		return result;
	}
	
	private int numberOfSameSuit(){
		int result = 0;
		for(int i=14;i>1;i--){
			if(countNumber[i]==4){	
				if(result!=4) result=4;
			}
			else if(countNumber[i]==3){
				if(result==0) result=3;
			}
		}
		return result;
	}
	private int numberOfSet(){
		int result = 0;
		for(int i=14;i>1;i--){
			if(countNumber[i]==3) result++;		
		}
		return result;
	}
	private void potentialStraight(){
		int temp=0;
		for(int i=1;i<15;i++){
			if(countNumber[i]>=1) temp++;
			else temp=0;
			if(temp==3) twoCardsToStraight = true;
			if(temp==4){
				twoCardsToStraight=false;
				oneCardToStraight=true;
			}
		}
	}
	public String makeAction(int handPower,State state){
		this.count();
		//同花顺action
		if(handPower>=80){
			if(handPower%10>=2){
				if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
				else return "all_in";
			}else if(handPower%10==1){
				if(state.numGuaranteed==0) return "check";
				else{
					if(state.callCost<state.myRestJetton) return "call";
					else return "all_in";
				}
			}else{
				if(state.numGuaranteed==0) return "check";
				else return "fold";	
			}
		}
		//四条Action
		else if(handPower>=70){
			if(handPower%10==1){
				if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
				else return "all_in";
			}else{
				if(state.numGuaranteed==0) return "check";
				else return "fold";	
			}
		}
		//葫芦Action
		else if(handPower>=60){
			if(handPower%10==3){
				if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
				else return "all_in";
			}else if(handPower%10==2){
				if(state.numGuaranteed==0) return "check";
				else{
					if(state.callCost<state.myRestJetton) return "call";
					else return "all_in";
				}
			}else{
				if(state.numGuaranteed==0) return "check";
				else return "fold";	
			}
		}
		//同花Action
		else if(handPower>=50){
			if(numberOfPairs==2 || numberOfSet==1){
				if(handPower%10==3){
					if(state.numGuaranteed==0) return "check";
					else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
					else return "fold";
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";	
				}
			}
			else if(numberOfPairs==1){
				if(handPower%10==3){
					if(Math.random()>0.3){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
					else{
						if(state.callCost<state.myRestJetton) return "call";
						else return "all_in";
					}
				}
				else if(handPower%10==2){
					if(state.numGuaranteed==0) return "check";
					else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/3) return "call";
					else return "fold";
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else{
				if(handPower%10==3){
					if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
					else return "all_in";
				}
				else if(handPower%10==2){
					if(state.numGuaranteed==0) return "check";
					else if(state.callCost<state.myRestJetton) return "call";
					else return "all_in";
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
		}
		//顺子Action
		else if(handPower>=40){
			if(numberOfPairs==2 || numberOfSet==1 ||numberOfSameSuit==4){
				if(handPower%10==3){
					if(state.numGuaranteed==0) return "check";
					else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
					else return "fold";
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else if(numberOfPairs==1 || numberOfSameSuit==3){
				if(handPower%10==3){
					if(Math.random()>0.5){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
					else{
						if(state.callCost<state.myRestJetton) return "call";
						else return "all_in";
					}
				}
				else if(handPower%10==2){
					if(state.numGuaranteed==0) return "check";
					else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
					else return "fold";
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else{
				if(handPower%10==3){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
				}
				else if(handPower%10==2){
					if(state.numGuaranteed==0) return "check";
					else{
						if(state.callCost<state.myRestJetton) return "call";
						else return "all_in";
					}	
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
		}
		//三条Action
		else if(handPower>=30){
			if(oneCardToStraight || numberOfSameSuit==4){
				if(handPower%10==3){
					if(Math.random()<0.5){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
					}
					else{
						if(state.numGuaranteed==0) return "check";
						else return "fold";
					}
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else if(twoCardsToStraight || numberOfSameSuit==3){
				if(handPower%10==3){
					if(Math.random()>0.75){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
					else{
						if(state.callCost<state.myRestJetton) return "call";
						else return "all_in";
					}
				}
				else if(handPower%10==2){
					if(Math.random()>0.5){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
					}
					else{
						if(state.numGuaranteed==0) return "check";
						else return "fold";
					}
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else{
				if(handPower%10==3){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
				}
				else if(handPower%10==2){
					if(Math.random()<0.3){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
					}
					else{
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
		}
		//两对Action
		else if(handPower>=20){
			if(oneCardToStraight || numberOfSameSuit==4){
				if(handPower%10==3){
					if(Math.random()<0.5){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
					}
					else{
						if(state.numGuaranteed==0) return "check";
						else return "fold";
					}
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else if(twoCardsToStraight || numberOfSameSuit==3){
				if(handPower%10==3){
					if(Math.random()>0.5){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
					else{
						if(state.callCost<state.myRestJetton) return "call";
						else return "all_in";
					}
				}
				else if(handPower%10==2){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else{
				if(handPower%10==3){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
				}
				else if(handPower%10==2){
					if(Math.random()>0.25){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
					}
					else{
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
		}
		//一对Action
		else if(handPower>=10){
			if(oneCardToStraight || numberOfSameSuit==4){
				if(handPower%10==3){
					if(Math.random()<0.5){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
					}
					else{
						if(state.numGuaranteed==0) return "check";
						else return "fold";
					}
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else if(twoCardsToStraight || numberOfSameSuit==3){
				if(handPower%10==3){
					if(Math.random()>0.5){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
					else{
						if(state.callCost<state.myRestJetton) return "call";
						else return "all_in";
					}
				}
				else if(handPower%10==2){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
			else{
				if(handPower%10==3){
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
				}
				else if(handPower%10==2){
					if(Math.random()>0.25){
						if(state.numGuaranteed==0) return "check";
						else if(state.callCost<=3*state.BB && state.callCost<=state.potSize/4) return "call";
						else return "fold";
					}
					else{
						if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
						else return "all_in";
					}
				}
				else{
					if(state.numGuaranteed==0) return "check";
					else return "fold";
				}
			}
		}
		//高牌Action
		else{
			if(handPower==2){
				if(state.raiseCost<state.myRestJetton) return "raise" + 2*state.BB;
				else return "all_in";
			}
			else if(handPower==1){
				if(state.numGuaranteed==0) return "check";
				else{
					if(state.callCost<state.myRestJetton) return "call";
					else return "all_in";
				}
			}
			else{
				if(state.numGuaranteed==0) return "check";
				else return "fold";
			}
		}
	}
}

