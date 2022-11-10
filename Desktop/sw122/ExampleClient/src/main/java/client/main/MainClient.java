package client.main;

import MessagesBase.MessagesFromClient.*;

import MessagesBase.MessagesFromServer.*;
import client.main.PathFinder.PathFinder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import MessagesBase.ResponseEnvelope;
import MessagesBase.UniquePlayerIdentifier;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClient {

	// ADDITIONAL TIPS ON THIS MATTER ARE GIVEN THROUGHOUT THE TUTORIAL SESSION!

	/*
	 * Below, you can find an example of how to use both required HTTP operations,
	 * i.e., POST and GET to communicate with the server.
	 * 
	 * Note, this is only an example. Hence, your own implementation should NOT
	 * place all the logic in a single main method!
	 * 
	 * Further, I would recommend that you check out: a) The JavaDoc of the network
	 * message library, which describes all messages, and their ctors/methods. You
	 * can find it here http://swe1.wst.univie.ac.at/ b) The informal network
	 * documentation is given in Moodle, which describes which messages must be used
	 * when and how.
	 */
	public static void main(String[] args) {
		


		String serverBaseUrl = args[1];// "http://swe1.wst.univie.ac.at:18235/"; //args[1];
		String gameId =  args[2];// "f2FV9"; //args[2];

		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		// template WebClient configuration, will be reused/customized for each
		// individual endpoint
		// TIP: create it once in the CTOR of your network class and subsequently use it
		// in each communication method


		WebClient baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
																							// XML
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

		//Registration
		Registration registration = new Registration(serverBaseUrl,gameId,baseWebClient);
		String playerId = registration.begin();

		canAct(serverBaseUrl, gameId, playerId, baseWebClient);

		logger.info("Converting map to server format");
		ArrayList<HalfMapNode> halfMapList = genMap();
		HalfMap halfMap = new HalfMap(playerId,halfMapList);
		logger.info("Converting is complete, Results: " + halfMap.getMapNodes());

		logger.info("Sending map");
		Mono<ResponseEnvelope> webAccess2 = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/halfmaps")
				.body(BodyInserters.fromValue(halfMap)) // specify the data which is sent to the server
				.retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

		ResponseEnvelope<GameState> resultMap = webAccess2.block();

		System.out.println( " ERROR " + resultMap.getExceptionMessage());
		//System.out.println("MAP STATE " + resultMap.getData().get());

		logger.info("Registration completed, waiting for server to give us permission to act(Send map)");
		canAct(serverBaseUrl, gameId, playerId, baseWebClient);
        try {
			getFullMapFromServer(serverBaseUrl, gameId, playerId, baseWebClient);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Player player = new Player(getPlayerPosition(serverBaseUrl, gameId, playerId, baseWebClient));
        System.out.println(player.getX() +  " " + player.getY());

		canAct(serverBaseUrl, gameId, playerId, baseWebClient);

        System.out.println(getPlayerPosition(serverBaseUrl, gameId, playerId, baseWebClient).getX() + " " + getPlayerPosition(serverBaseUrl, gameId, playerId, baseWebClient).getY());

		Coordinates cordS = new Coordinates(0,0);

		PathFinder pf = new PathFinder(fullmapList(serverBaseUrl, gameId, playerId, baseWebClient));
		Coordinates cordD = new Coordinates(player.getX(), player.getY());

		int fbw2 = pf.findByValue(cordS);


		for( int i = 0 ; i<50;i++){
			pf = new PathFinder(fullmapList(serverBaseUrl, gameId, playerId, baseWebClient));
			player = new Player(getPlayerPosition(serverBaseUrl, gameId, playerId, baseWebClient));
			cordD = new Coordinates(player.getX(), player.getY());

			System.out.println(" PLAYER " + getPlayerPosition(serverBaseUrl, gameId, playerId, baseWebClient).getX() + " " + getPlayerPosition(serverBaseUrl, gameId, playerId, baseWebClient).getY());

			int fbw1 = pf.findByValue(cordD);

			pf.calcPath(fbw1,fbw2);
			System.out.println(" FBW1 + 2 " + fbw1 + " " + fbw2);
			int cx = pf.getPathNextCord().getX();
			int cy = pf.getPathNextCord().getY();

 			System.out.println("NEXT MOVE " + cx + " " + cy);
			canAct(serverBaseUrl, gameId, playerId, baseWebClient);


			int buffx = player.getX() - cx;
			int buffy = player.getY()-cy;

			if((buffx == -1 )&& (buffy == 0)){
				sendMove(serverBaseUrl, gameId, playerId, baseWebClient, EMove.Right);
			}
			if((buffx == 1 )&& (buffy == 0)){
				sendMove(serverBaseUrl, gameId, playerId, baseWebClient, EMove.Left);
			}
			if((buffx == 0 )&& (buffy == 1)){
				sendMove(serverBaseUrl, gameId, playerId, baseWebClient, EMove.Up);
			}
			if((buffx == 0 )&& (buffy == -1)){
				sendMove(serverBaseUrl, gameId, playerId, baseWebClient, EMove.Down);
			}

			canAct(serverBaseUrl, gameId, playerId, baseWebClient);
		}






	}



	
	private static void sendMove(String serverBaseUrl, String gameId, String playerId, WebClient baseWebClient, EMove move){

		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);

		PlayerMove pm = PlayerMove.of(playerId,move);;
		
		
		System.out.println(pm.getMove());
		

		logger.info("Sending move");
		Mono<ResponseEnvelope> webAccess3 = baseWebClient.method(HttpMethod.POST).uri("/" + gameId + "/moves")
				.body(BodyInserters.fromValue(pm)) // specify the data which is sent to the server
				.retrieve().bodyToMono(ResponseEnvelope.class);
		logger.info("Move send");
		
		
		ResponseEnvelope resultMove = webAccess3.block();
		
		System.out.println("ERROR: " + resultMove.getExceptionMessage());
	}



	private static boolean canAct(String serverBaseUrl, String gameId, String playerId, WebClient baseWebClient){

		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		logger.info("Waiting for Must act");
		try {
			while(getState(serverBaseUrl, gameId, playerId, baseWebClient).equals("MustWait")){
				logger.info("Not yet, waiting for 400 milliseconds");
				Thread.sleep(400);
			}

		} catch (Exception e) {
			logger.warning("Something went wrong with getState");
			e.printStackTrace();
		}

		return true;
	}

	public static List<FullMapNode> fullmapList(String Url, String gId, String pId, WebClient baseWebClient){
		String baseUrl = Url;
		String gameId = gId;
		String playerId = pId;

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);
		ResponseEnvelope<GameState> requestResult = webAccess.block();

		FullMap fm = new FullMap(requestResult.getData().get().getMap().get().getMapNodes());
		List<FullMapNode> fmnList= new ArrayList<>(fm.getMapNodes());



		return fmnList;
	}
	public static String getFullMapFromServer(String Url, String gId, String pId, WebClient baseWebClient) throws Exception {
        String baseUrl = Url;
        String gameId = gId;
        String playerId = pId;

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);
        ResponseEnvelope<GameState> requestResult = webAccess.block();

        FullMap fm = new FullMap(requestResult.getData().get().getMap().get().getMapNodes());
        List<FullMapNode> fmnList= new ArrayList<>(fm.getMapNodes());

        for(int i = 0; i<fmnList.size();i++){
            System.out.println(fmnList.get(i));
        }

		return "";
	}

    public static Coordinates getPlayerPosition(String Url, String gId, String pId, WebClient baseWebClient){
        String baseUrl = Url;
        String gameId = gId;
        String playerId = pId;
        Coordinates cord = null;

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);
        ResponseEnvelope<GameState> requestResult = webAccess.block();

        FullMap fm = new FullMap(requestResult.getData().get().getMap().get().getMapNodes());
        List<FullMapNode> fmnList= new ArrayList<>(fm.getMapNodes());
        for(FullMapNode element: fmnList){
            if ((element.getPlayerPositionState() == EPlayerPositionState.BothPlayerPosition)||(element.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition)) {
                cord = new Coordinates(element.getX(), element.getY());
            }
        }


        return cord;
    }

    public static String getTerrainRight(String Url, String gId, String pId, WebClient baseWebClient,Coordinates cord){
        String baseUrl = Url;
        String gameId = gId;
        String playerId = pId;
        String terr = "";
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);
        ResponseEnvelope<GameState> requestResult = webAccess.block();

        FullMap fm = new FullMap(requestResult.getData().get().getMap().get().getMapNodes());
        List<FullMapNode> fmnList= new ArrayList<>(fm.getMapNodes());

        for(FullMapNode element: fmnList){
            if ((element.getX()==(cord.getX()+1))&&(element.getY()==cord.getY())) {
                terr =  element.getTerrain().toString();
            }
        }
		System.out.println( "TERR " + terr);
		return terr;
    }
    public static String getTerrainDown(String Url, String gId, String pId, WebClient baseWebClient,Coordinates cord){
        String baseUrl = Url;
        String gameId = gId;
        String playerId = pId;
        String terr = "";
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);
        ResponseEnvelope<GameState> requestResult = webAccess.block();

        FullMap fm = new FullMap(requestResult.getData().get().getMap().get().getMapNodes());
        List<FullMapNode> fmnList= new ArrayList<>(fm.getMapNodes());

        for(FullMapNode element: fmnList){
            if ((element.getX()==cord.getX())&&(element.getY()==(cord.getY()+1))) {
                terr =  element.getTerrain().toString();
            }
        }
        return terr;
    }

    public static String getTerrain(String Url, String gId, String pId, WebClient baseWebClient,Coordinates cord){
        String baseUrl = Url;
        String gameId = gId;
        String playerId = pId;
        String terr = "";
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);
        ResponseEnvelope<GameState> requestResult = webAccess.block();

        FullMap fm = new FullMap(requestResult.getData().get().getMap().get().getMapNodes());
        List<FullMapNode> fmnList= new ArrayList<>(fm.getMapNodes());

        for(FullMapNode element: fmnList){
            if ((element.getX()==cord.getX())&&(element.getY()==cord.getY())) {
                terr =  element.getTerrain().toString();
            }
        }
        return terr;
    }






	public static String getState(String Url, String gId, String pId, WebClient baseWebClient) throws Exception {

		String baseUrl = Url;
		String gameId = gId;
		String playerId = pId;


		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class);

		ResponseEnvelope<GameState> requestResult = webAccess.block();
		GameState gs = new GameState(requestResult.getData().get().getPlayers(),requestResult.getData().get().getGameStateId());

		List<PlayerState> mainList = new ArrayList<>();
		mainList.addAll(gs.getPlayers());

		PlayerState ps = null;

		for(int i = 0; i<mainList.size();i++) {
			if (mainList.get(i).getUniquePlayerID().equals(playerId)) {
				ps = mainList.get(i);
			}
		}

		if (requestResult.getState() == ERequestState.Error) {
			System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
		}
		System.out.println(gs.getPlayers());
		return ps.getState().toString();
	}

	public static void exampleForGetRequests(String Url,String gId,String pId,WebClient baseWebClient) throws Exception {
		// you will need to fill the variables with the appropriate information
		String baseUrl = Url;
		String gameId = gId;
		String playerId = pId;

		// TIP: Use a global instance of the base WebClient throughout each
		// communication
		// you can init it once in the CTOR and use it in each of the network
		// communication methods in your networking class


		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameId + "/states/" + playerId).retrieve().bodyToMono(ResponseEnvelope.class); // specify the
																											// object
																											// returned
																											// by the
																											// server

		// WebClient support asynchronous message exchange. In SE1 we use a synchronous
		// one for the sake of simplicity. So calling block is fine.
		ResponseEnvelope<GameState> requestResult = webAccess.block();
		System.out.println("STATE 1" + requestResult);
		System.out.println("STATE 2" + requestResult.getData().get().getPlayers());
		GameState gs = new GameState(requestResult.getData().get().getPlayers(),requestResult.getData().get().getGameStateId());
		System.out.println("STATE 3" + gs.getPlayers());

		List<PlayerState> mainList = new ArrayList<>();
		mainList.addAll(gs.getPlayers());
		System.out.println(mainList.get(0).getState());





		// always check for errors, and if some are reported, at least print them to the
		// console (logging should always be preferred!)
		// so that you become aware of them during debugging! The provided server gives
		// you constructive error messages.
		if (requestResult.getState() == ERequestState.Error) {
			System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
		}
	}

	public static ArrayList<HalfMapNode> genMap(){
		HalfMapGen half = new HalfMapGen();
		System.out.println(half.printHalf());
		ArrayList<HalfMapNode> halfMapList = new ArrayList<HalfMapNode>();

		Random rand = new Random();
		int k = rand.nextInt(4);
		int j = rand.nextInt(8);

		while(half.get_half()[k][j].getType()==TerrainEnum.Wasser||half.get_half()[k][j].getType()==TerrainEnum.Berg){

			k = rand.nextInt(4);
			j = rand.nextInt(8);

		}

		for(int i = 0; i<half.get_half().length; i++){
			for(int l = 0; l<half.get_half()[i].length; l++){
				HalfMapNode buff = null;

				if(half.get_half()[i][l].getType() == TerrainEnum.Berg){

					buff = new HalfMapNode(l, i, ETerrain.Mountain);
				}
				if(half.get_half()[i][l].getType() == TerrainEnum.Wasser){
					buff = new HalfMapNode(l, i, ETerrain.Water);
				}

				if(half.get_half()[i][l].getType() == TerrainEnum.Wiesen){

					if((i==k)&&(l==j)){
						buff = new HalfMapNode(l, i,true, ETerrain.Grass);

					}else {
						buff = new HalfMapNode(l, i, ETerrain.Grass);
					}

				}
				halfMapList.add(buff);
			}
		}

		return halfMapList;
	}
	

}
