package texaspoker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandleMeg {
	private static String rec;
	private static String[] subs;
	
	public static void handleSeatMeg(BufferedReader br,State state, int myPid) throws IOException{
		int playerCount = 0, pid, jetton, money;
		while( !(rec = br.readLine()).equals("/seat ") ){
			playerCount++;
			subs = rec.split(" ");
			switch(playerCount){
			case 1:
				pid = Integer.parseInt(subs[1]);
				jetton = Integer.parseInt(subs[2]);
				money = Integer.parseInt(subs[3]);
				//desk.setButton(pid);
				break;
			case 2:
//				pid = Integer.parseInt(subs[2]);
//				jetton = Integer.parseInt(subs[3]);
//				money = Integer.parseInt(subs[4]);
//				desk.setBigBlind(pid);
//				break;
			case 3:
				pid = Integer.parseInt(subs[2]);
				jetton = Integer.parseInt(subs[3]);
				money = Integer.parseInt(subs[4]);
				//desk.setSmallBlind(pid);
				break;
			default:
				pid = Integer.parseInt(subs[0]);
				jetton = Integer.parseInt(subs[1]);
				money = Integer.parseInt(subs[2]);
			}
			if(pid == myPid){
				state.myRestJetton = jetton;
				state.myPosition = playerCount;
			}
//			else{
//				oppos.put(pid, new Opponent(pid, jetton, money));
//			}
		}
		state.numInpot = playerCount;
		switch(state.myPosition){
		case 1:
			state.myPosition = 0;
			break;
		default:
			state.myPosition = 1 + playerCount - state.myPosition;
		}
		state.sbPosition = state.numInpot - 1;
		state.bbPosition = state.numInpot - 2;
		state.myBet = 0;
	}

	public static void handleBlindMeg(BufferedReader br, State state, int myPid) throws IOException{
		// TODO Auto-generated method stub
		//int playerCount = desk.getPlayerCount();
		int playerCount = state.numInpot;
		switch(playerCount){
		case 2:
			rec = br.readLine();
			subs = rec.split(" ");
			//desk.setBlind(Integer.parseInt(rec));
			state.BB = Integer.parseInt(subs[1]);
			//我是盲注
			if(myPid == Integer.parseInt(subs[0])){
				state.myRestJetton -= Integer.parseInt(subs[1]);
				state.myBet = Integer.parseInt(subs[1]);
			}
			break;
		default:
			rec = br.readLine();
			subs = rec.split(" ");
			//我是小盲注
			if(myPid == Integer.parseInt(subs[0].substring(0, subs[0].length() - 1))){
				state.myRestJetton -= (Integer.parseInt(subs[1]) / 2);
				state.myBet = Integer.parseInt(subs[1]) / 2;
			}
			rec = br.readLine();
			subs = rec.split(" ");
			//desk.setBlind(Integer.parseInt(rec));
			state.BB = Integer.parseInt(subs[1]);
			//我是大盲注
			if(myPid == Integer.parseInt(subs[0].substring(0, subs[0].length() - 1))){
				state.myRestJetton -= Integer.parseInt(subs[1]);
				state.myBet = Integer.parseInt(subs[1]);
			}
		}
		br.readLine();
System.out.println("my bet: " + state.myBet);
	}

	public static void handleHoldMeg(BufferedReader br, State state, HandPowerComputer HP) throws IOException {
		// TODO Auto-generated method stub
		while( !(rec = br.readLine()).equals("/hold ") ){
			subs = rec.split(" ");
			int color = Card.color2Number.get(subs[0]);
			int point = Card.point2Number.get(subs[1]);
			
			HP.addHandCard( new Card(color, point) );
//			Card card;
//			card = new Card(color, point);
//			myInfo.addHoldCards(card);
		}
		
		state.round = State.PREFLOP;
	}
	
	public static void handleInquireMeg(BufferedReader br, BufferedWriter bw,
			State state, HandPowerComputer HP, int myPid) throws IOException {
		
		// TODO Auto-generated method stub
		int prebet = state.BB, raiseCount = 0, callCount = 0, allinCount = 0, guaranteedCount = 0;
		while( !(rec = br.readLine()).equals("/inquire ")){
			subs = rec.split(" ");
			if( !subs[0].equals("total") ){
//				Opponent opp = opposInfo.get(Integer.parseInt(subs[0]));
//				opp.setJetton(Integer.parseInt(subs[1]));
//				opp.setMoney(Integer.parseInt(subs[2]));
//				opp.setTotalBet(Integer.parseInt(subs[3]));
//				opp.addAction(subs[subs.length - 1]);
				int bet = Integer.parseInt(subs[3]);
				if(bet > prebet)	prebet = bet;
				switch(ActionMap.map.get(subs[4])){
				case 2:
					callCount++;
					guaranteedCount++;
					break;
				case 3:
					raiseCount++;
					guaranteedCount++;
					break;
				case 4:
					allinCount++;
					guaranteedCount++;
					break;
				}
				
			}
			else{
				//desk.setTotalPot(Integer.parseInt(subs[2]));
				state.potSize = Integer.parseInt(subs[2]);
				state.numRaisePlayer = raiseCount;
				state.numCallPlayer = callCount;
				state.numAllInPlayer = allinCount;
				state.numGuaranteed = guaranteedCount;
				state.callCost = prebet - state.myBet;
				state.raiseCost = state.callCost + state.BB * 2;
System.out.println("call cost: " + state.callCost);
			}
		}
		//此处需要调用策略等方法
//		bw.write("call \n");
//		bw.flush();
		Card[] cards = new Card[2];
		cards = HP.getHandCards().toArray(cards);
		String send = null;
		switch(state.round){
		case State.PREFLOP:
			send = new PreFlopAction().GetStrategyPreflop(cards, state);
System.out.println(send);
			bw.write(send + " \n");
			bw.flush();
			break;
		default:
			send = new PostFlopAction(HP.getCommunityCards()).makeAction(state.handPower, state);
System.out.println("exit from action");
			bw.write(send + " \n");
			bw.flush();
		}
		switch(ActionMap.map.get(send.split(" ")[0])){
		case 2:
			state.myBet += state.callCost;
		case 3:
			state.myBet = state.myBet + state.callCost + Integer.parseInt(send.split(" ")[1]);
		}
		
		if( state.not_already_voluntarily_put_money && 
				!( send.equals("check"))||send.equals("fold") )
			state.not_already_voluntarily_put_money = false;
	}

	public static void handleFlopMeg(BufferedReader br, State state, HandPowerComputer HP) throws IOException {
		// TODO Auto-generated method stub
		while( !(rec = br.readLine()).equals("/flop ") ){
			subs = rec.split(" ");
			HP.add(new Card(Card.color2Number.get(subs[0]), Card.point2Number.get(subs[1])));
		}
		state.round = State.FLOP;
		state.handPower = HP.computeHP();
	}

	public static void handleTurnMeg(BufferedReader br, State state, HandPowerComputer HP) throws IOException{
		// TODO Auto-generated method stub
		while( !(rec = br.readLine()).equals("/turn ") ){
			subs = rec.split(" ");
			HP.add(new Card(Card.color2Number.get(subs[0]), Card.point2Number.get(subs[1])));
		}
		state.round = State.TURN;
		state.handPower = HP.computeHP();
	}

	public static void handleRiverMeg(BufferedReader br, State state, HandPowerComputer HP) throws IOException {
		// TODO Auto-generated method stub
		while( !(rec = br.readLine()).equals("/river ") ){
			subs = rec.split(" ");
			HP.add(new Card(Card.color2Number.get(subs[0]), Card.point2Number.get(subs[1])));
		}
		state.round = State.RIVER;
		state.handPower = HP.computeHP();
	}

	public static void handleShowdownMeg(BufferedReader br)  throws IOException {
		// TODO Auto-generated method stub
		while( !(rec = br.readLine()).equals("/showdown ") ){
			System.out.println(rec);
		}
	}

	public static void handlePotwinMeg(BufferedReader br, HandPowerComputer HP) throws IOException {
		// TODO Auto-generated method stub
		while( !(rec = br.readLine()).equals("/pot-win ") ){}
		HP.clear();
System.out.println("this is end");
	}
}