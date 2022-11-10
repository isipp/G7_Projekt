package client.main.PathFinder;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromServer.FullMap;
import MessagesBase.MessagesFromServer.FullMapNode;
import MessagesBase.MessagesFromServer.GameState;
import MessagesBase.ResponseEnvelope;
import client.main.Coordinates;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.main.PathFinder.Dijkstra.getLi;
import static client.main.PathFinder.Dijkstra.shortestPath;


//REMEMBER TO REFERENCE https://gist.github.com/alexlaurence/44ef6b8958e1144ff087f42571b1e580 !!!!!!!
//REFERENCE!!!!!!!!!!!!!!!!!!!
//TODO

public class PathFinder {
    List<FullMapNode> rawMapAsList;
    Map<Integer, Coordinates> mapHash = new HashMap<Integer, Coordinates>();

    public PathFinder(List<FullMapNode> rawMapAsList){
        this.rawMapAsList = rawMapAsList;
        this.mapHash = convertToHash(rawMapAsList);

    }

    public Map<Integer, Coordinates> convertToHash(List<FullMapNode> map){
        Map<Integer, Coordinates> mapBuff = new HashMap<Integer, Coordinates>();
        int l = 1;

        for(int i = 0; i<map.size();i++){
            if(!map.get(i).getTerrain().equals(ETerrain.Water)){
                Coordinates cord = new Coordinates(map.get(i).getX(),map.get(i).getY());
                mapBuff.put(l, cord);
                l++;
            }
        }
        return mapBuff;
    }

    public Map<Integer, Coordinates> getMapHash() {
        return mapHash;
    }

    public List<Integer> getPathList(){
        return getLi();
    }
    public Integer getPathNext(){
        if(getLi().size()>2){
            return  getLi().get(1);
        }
        return getLi().get(0);
    }
    public Coordinates getPathNextCord(){
        Coordinates cord = new Coordinates(getMapHash().get(getPathNext()).getX(),getMapHash().get(getPathNext()).getY());

        return cord;
    }
    public void calcPath(int source, int dest){


        List<Edge> liE = mapCoverter(rawMapAsList);
        final int N = mapHash.size()+1;
        Graph graph = new Graph(liE, N);
        shortestPath(graph, source, N,dest);
        System.out.println(getLi());

    }

    public List<Edge> mapCoverter(List<FullMapNode> map){
        List<Edge> buff = new ArrayList<>();


        for(int i = 0; i<map.size();i++){
            Coordinates currentCord = new Coordinates(map.get(i).getX(), map.get(i).getY());

            int current = findByValue( currentCord);

            if(map.get(i).getTerrain()!= ETerrain.Water){

                if(checkUp(map.get(i),map)){
                    int xUp= map.get(i).getX();
                    int yUp = map.get(i).getY()+1;
                    Coordinates cord = new Coordinates(xUp, yUp);
                    int up = findByValue(cord);
                    Edge EdgeUp = new Edge(current, up, 1);
                    buff.add(EdgeUp);
                }
                if(checkDown(map.get(i),map)){

                    int xDown= map.get(i).getX();
                    int yDown = map.get(i).getY()-1;
                    Coordinates cord = new Coordinates(xDown, yDown);
                    int down = findByValue(cord);
                    Edge EdgeDown = new Edge(current, down, 1);
                    buff.add(EdgeDown);
                }
                if(checkLeft(map.get(i),map)){
                    int xLeft= map.get(i).getX()-1;
                    int yLeft = map.get(i).getY();
                    Coordinates cord = new Coordinates(xLeft, yLeft);
                    int left = findByValue(cord);
                    Edge EdgeLeft = new Edge(current, left, 1);
                    buff.add(EdgeLeft);
                }
                if(checkRight(map.get(i),map)){
                    int xRight= map.get(i).getX()+1;
                    int yRight = map.get(i).getY();
                    Coordinates cord = new Coordinates(xRight, yRight);
                    int right = findByValue(cord);
                    Edge EdgeRight = new Edge(current, right, 1);
                    buff.add(EdgeRight);
                }
            }
        }

        return buff;
    }


    public int  findByValue( Coordinates cord){

        for (Map.Entry<Integer, Coordinates> entry : mapHash.entrySet()) {
            if ((cord.getX()==entry.getValue().getX())&&(cord.getY()==entry.getValue().getY())) {

                return entry.getKey();
            }
        }
        return 0;
    }
/*
    public int calcWeight(ETerrain source, ETerrain dest){

        return ;
    }
*/

        public static String getTerrain(List<FullMapNode> map, Coordinates cord){

        String terr = "";

        for(FullMapNode element: map){
            if ((element.getX()==cord.getX())&&(element.getY()==cord.getY())) {
                terr =  element.getTerrain().toString();
            }
        }
        return terr;
    }

    public boolean checkUp(FullMapNode node, List<FullMapNode> map){
        int buffX = node.getX();
        int buffY = node.getY();

        buffY++;
        Coordinates cc = new Coordinates(buffX, buffY);
        if(findByValue(cc)!=0){
            return true;
        }
        return false;
    }

    public boolean checkDown(FullMapNode node, List<FullMapNode> map){
        int buffX = node.getX();
        int buffY = node.getY();

        buffY--;
        Coordinates cc = new Coordinates(buffX, buffY);
        if(findByValue(cc)!=0){
            return true;
        }
        return false;
    }
    public boolean checkRight(FullMapNode node, List<FullMapNode> map){
        int buffX = node.getX();
        int buffY = node.getY();

        buffX++;
        Coordinates cc = new Coordinates(buffX, buffY);
        if(findByValue(cc)!=0){
            return true;
        }
        return false;
    }
    public boolean checkLeft(FullMapNode node, List<FullMapNode> map){
        int buffX = node.getX();
        int buffY = node.getY();

        buffX--;
        Coordinates cc = new Coordinates(buffX, buffY);
        if(findByValue(cc)!=0){
            return true;
        }
        return false;
    }

    public void checkNull(List<FullMapNode> map , int x, int y){



    }

}


