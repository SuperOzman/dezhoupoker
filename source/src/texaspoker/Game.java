package texaspoker;

import java.net.*;
import java.io.*;

public class Game {
	private String serverIP, myIP;
	private int serverPort, myPort, myPid;
	/*
	 * serverIP = 127.0.0.1
	 * serverPort = 8888;
	 * myIP = 127.0.0.1
	 * myPort = 8889;
	 * myPid = sjx
	 * */
	private Socket client;
	private BufferedReader br;
	private BufferedWriter bw;
	
	private String rec;
	private State state;
	private HandPowerComputer HP;
	
//	private Map<Integer, Opponent> opposInfo = new HashMap<Integer, Opponent>();
//	private MyInfo myInfo = new MyInfo(Game.this.myPid);
//	private Desk desk = new Desk();
	
	
	public Game(String[] args){
		if(args.length != 5){
			System.out.println("wrong initial args");
			System.exit(0);
			return;
		}
		
		this.serverIP = args[0];
		this.serverPort = Integer.parseInt( args[1] );
		this.myIP = args[2];
		this.myPort = Integer.parseInt(args[3]);
		this.myPid = Integer.parseInt(args[4]);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Game game = new Game(args);
		game.connectServer();
	}
	
	private void connectServer() throws UnknownHostException, IOException {
		this.client = new Socket(this.serverIP, this.serverPort,
				InetAddress.getByName(this.myIP), this.myPort);
		this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		
//System.out.println("connect to server is successful.");
		//register to server
		
		
		
		bw.write("reg: " + Game.this.myPid + " pname(self define) \n");
		bw.flush();

		state = new State();
		state.myRestJetton = Integer.MAX_VALUE;
		HP = new HandPowerComputer();

		while(!(rec = br.readLine()).equals("game-over ")){
			this.handleMsg(rec);
		}
		if(rec.equals("game-over ")){
			bw.close();
			br.close();
			client.close();
		}
	}

	private void handleMsg(String headMeg) throws IOException {
		// TODO Auto-generated method stub
		int index = HeadMeg.map.get(headMeg);
		switch(index){
		case 0:
			//seat-info-meg and save parameters
			HandleMeg.handleSeatMeg(br, state, myPid);
			break;
		case 1:
			//blind-meg
			HandleMeg.handleBlindMeg(br, state, myPid);
			break;
		case 2:
			//hold-meg
			HandleMeg.handleHoldMeg(br, state, HP);
			break;
		case 3:
			//inquire-meg & send action-meg to server
System.out.println("enter inquire");
			HandleMeg.handleInquireMeg(br, bw, state, HP, myPid);
System.out.println("exit inquire");
			break;
		case 4:
			//flop-meg
System.out.println("enter flop");
			HandleMeg.handleFlopMeg(br, state, HP);
System.out.println("exit flop");
			break;
		case 5:
			HandleMeg.handleTurnMeg(br, state, HP);
			break;
		case 6:
			HandleMeg.handleRiverMeg(br, state, HP);
			break;
		case 7:
			HandleMeg.handleShowdownMeg(br);
			break;
		case 8:
			HandleMeg.handlePotwinMeg(br, HP);
			break;
		}
	}
}