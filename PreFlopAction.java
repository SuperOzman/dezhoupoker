
public class PreFlopAction {
	private int[][] IR2 ={{7,-351,-334,-314,-318,-308,-264,-217,-166,-113,-53,10,98},
			{-279,74,-296,-274,-277,-267,-251,-201,-148,-93,-35,27,116},
			{-263,-225,-142,-236,-240,-231,-209,-185,-130,-75,-17,46,134},
			{-244,-206,-169,207,-201,-189,-169,-148,-114,-55,2,68,153},
			{-247,-208,-171,-138,264,-153,-134,-108,-78,-43,19,85,154},
			{-236,-200,-162,-125,-91,324,-99,-72,-43,-6,37,104,176},
			{-192,-182,-143,-108,-75,-43,384,-39,-4,29,72,120,197},
			{-152,-134,-122,-84,-50,-17,16,440,28,65,106,155,215},
			{-104,-86,-69,-56,-19,12,47,81,499,102,146,195,254},
			{-52,-35,-19,0,11,46,79,113,149,549,161,212,271},
			{2,21,34,55,72,86,121,153,188,204,598,228,289},
			{63,79,98,116,132,151,168,200,235,249,268,647,305},
			{146,164,180,198,198,220,240,257,291,305,323,339,704}};
	private int[][] IR4 ={{-121,-440,-409,-382,-411,-432,-394,-357,-301,-259,-194,-116,16},
			{-271,-42,-345,-312,-340,-358,-371,-328,-277,-231,-165,-87,54},
			{-245,-183,52,-246,-269,-287,-300,-308,-252,-204,-135,-55,84},
			{-219,-151,-91,752,-200,-211,-227,-236,-227,-169,-104,-24,118},
			{-247,-177,-113,-52,256,-145,-152,-158,-152,-145,-74,9,99},
			{-261,-201,-129,-65,3,376,-76,-79,-68,-66,-44,48,148},
			{-226,-204,-140,-73,-2,66,503,0,15,24,45,84,194},
			{-191,-166,-147,-79,-5,68,138,647,104,113,136,177,241},
			{-141,-116,-91,-69,-4,75,150,235,806,226,255,295,354},
			{-89,-67,-41,-12,7,82,163,248,349,965,301,348,410},
			{-29,-3,22,51,80,108,185,274,379,423,1141,403,473},
			{47,76,101,128,161,199,230,318,425,473,529,1325,541},
			{175,211,237,266,249,295,338,381,491,539,594,655,1554}};
	private int[][] IR7 ={{-6,-462,-422,-397,-459,-495,-469,-433,-383,-336,-274,-188,-39},
			{-180,21,-347,-304,-365,-418,-447,-414,-356,-308,-248,-163, -1},
			{-148,-69,67,-227,-273,-323,-362,-391,-334,-287,-223,-133,32},
			{-121,-38,31,122,-198,-230,-270,-303,-309,-259,-200,-103,64},
			{-174,-95,-10,64,206,-151,-175,-204,-217,-235,-164,-72,23},
			{-208,-135,-47,35,108,298,-87,-106,-112,-128,-124,-26,72},
			{-184,-164,-83,2,93,168,420,-5,6,-10,-10,22,126},
			{-146,-128,-111,-26,64,153,245,565,134,118,118,151,189},
			{-88,-68,-46,-29,59,155,268,383,765,299,305,336,373},
			{-38, -15, 1, 30, 51, 147, 256, 377, 536, 996, 380, 420, 462},
			{35, 49, 72, 99, 127 ,162, 268,384, 553, 628, 1279, 529, 574},
			{117,141,167,190,223,261,304,423,591,669,764,1621,712},
			{269,304,333,363,313,365,416,475,644,720,815,934,2043}};
	private int[][] make2player={{-50,50,-50,50,-50,50},
								{150,50,50,50,0,0},
								{300,0,300,0,300,0}};
	private int[][] make3player={{50,50,50,25,50,10},
								{200,50,200,25,200,10},
								{580,0,580,0,580,0}};
	private int[][] make5player={{0,70,0,50,0,30},
								{450,50,450,25,450,10},
								{900,0,900,0,900,0}};
	public static final int MAKE1 = 0;
	public static final int MAKE2 = 1;
	public static final int MAKE4 = 2;
	public String GetStrategyPreflop(Card[] myhand, State state)
	{
		float E_num_players;
		int group ;
		float IR = 0 ;
		float thresh[] = new float[3];
		String strategy = null;
		/*¼ÆËãgroup*/
		E_num_players = (float) (state.numGuaranteed + 0.6 * (state.numInpot - state.numGuaranteed));
		if (state.numInpot < 2.5)	group = 2;
		else if (E_num_players >= 4.5) group = 5;
		else group = 3;
		/*¼ÆËãIR*/
		if(myhand[0].getSuit()==myhand[0].getSuit()){
			if(myhand[0].getNumber()>=myhand[1].getNumber()){
				if(group==2) IR = IR2[myhand[0].getNumber()-2][myhand[1].getNumber()-2];
				if(group==5) IR = IR7[myhand[0].getNumber()-2][myhand[1].getNumber()-2];
				if(group==3) IR = IR4[myhand[0].getNumber()-2][myhand[1].getNumber()-2];
			}else{
				if(group==2) IR = IR2[myhand[1].getNumber()-2][myhand[0].getNumber()-2];
				if(group==5) IR = IR7[myhand[1].getNumber()-2][myhand[0].getNumber()-2];
				if(group==3) IR = IR4[myhand[1].getNumber()-2][myhand[0].getNumber()-2];
			}
		}else{
			if(myhand[0].getNumber()>=myhand[1].getNumber()){
				if(group==2) IR = IR2[myhand[1].getNumber()-2][myhand[0].getNumber()-2];
				if(group==5) IR = IR7[myhand[1].getNumber()-2][myhand[0].getNumber()-2];
				if(group==3) IR = IR4[myhand[1].getNumber()-2][myhand[0].getNumber()-2];
			}else{
				if(group==2) IR = IR2[myhand[0].getNumber()-2][myhand[1].getNumber()-2];
				if(group==5) IR = IR7[myhand[0].getNumber()-2][myhand[1].getNumber()-2];
				if(group==3) IR = IR4[myhand[0].getNumber()-2][myhand[1].getNumber()-2];
			}
		}
		
		for(int i=0 ;i<3;i++){
			thresh[i] = SetThresholds(i,group,state.myPositin);
		}
		

		if (IR>=thresh[MAKE4])
			strategy = "raise"+ state.BB;
		else if (IR>=thresh[MAKE2]){
			if(state.callCost>state.BB) strategy = "call";
			else  strategy = "raise" + state.BB;
		}
		else if (IR>=thresh[MAKE1]){
			if(state.myPositin!=state.sbPosition&&state.myPositin!=state.bbPosition){
				if(state.numGuaranteed==0) strategy = "call";
				else if(state.not_already_voluntarily_put_money&&state.callCost>state.BB) 
					strategy = "fold";
				else if(state.not_already_voluntarily_put_money) 
					strategy = "call";
				else strategy = "check";
			}else{
				if(state.not_already_voluntarily_put_money&&state.callCost>state.BB) 
					strategy = "fold";
				else if(state.not_already_voluntarily_put_money) 
					strategy = "call";
				else strategy = "check";
			}
		}
		else{
			if(state.not_already_voluntarily_put_money)
				strategy = "fold";
			else strategy = "check";
		}
		
		return strategy;
	}
	private float SetThresholds(int strategy,int group,int position ){
		/*strategyÖ¸MAKE1£¬MAKE2£¬MAKE4*/
		float result;
		if(group==2) result = make2player[strategy][0]+position*make2player[strategy][1];
		else if(group==3) result = make3player[strategy][0]+position*make3player[strategy][1];
		else result = make5player[strategy][0]+position*make5player[strategy][1];
		return result;
	}
}

