import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromServer.*;
import client.main.Coordinates;
import client.main.MainClient;
import client.main.PathFinder.Edge;
import client.main.PathFinder.PathFinder;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathFinderTest {
    FullMap fm;

    public PathFinderTest() {
        FullMapNode fmn1 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 0, 0);
        FullMapNode fmn2 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 1, 0);
        FullMapNode fmn3 = new FullMapNode(ETerrain.Mountain, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 2, 0);
        FullMapNode fmn4 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 3, 0);
        FullMapNode fmn5 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 4, 0);
        FullMapNode fmn6 = new FullMapNode(ETerrain.Water, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 0, 1);
        FullMapNode fmn7 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 1, 1);
        FullMapNode fmn8 = new FullMapNode(ETerrain.Water, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 2, 1);
        FullMapNode fmn9 = new FullMapNode(ETerrain.Water, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 3, 1);
        FullMapNode fmn10 = new FullMapNode(ETerrain.Mountain, EPlayerPositionState.MyPlayerPosition, ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 4, 1);
        ArrayList<FullMapNode> ccc = new ArrayList<>();

        ccc.add(fmn1);
        ccc.add(fmn2);
        ccc.add(fmn3);
        ccc.add(fmn4);
        ccc.add(fmn5);
        ccc.add(fmn6);
        ccc.add(fmn7);
        ccc.add(fmn8);
        ccc.add(fmn9);
        ccc.add(fmn10);

        fm = new FullMap(ccc);

    }
    @Test
    void demoTest(){
        List<FullMapNode> fmnList= new ArrayList<>(fm.getMapNodes());

        PathFinder pft = new PathFinder(fmnList);
       // List<Edge> liEdge = pft.mapCoverter(fmnList);

        pft.calcPath(6,2);
        System.out.println(pft.getPathList());
        System.out.println(pft.getPathNext());
        System.out.println(pft.getPathNextCord().getX() + " " + pft.getPathNextCord().getY());

        pft.calcPath(2,6);
        System.out.println(pft.getPathList());
        System.out.println(pft.getPathNext());
        System.out.println(pft.getPathNextCord().getX() + " " + pft.getPathNextCord().getY());

        System.out.println("1 KEY " + pft.getMapHash().get(1).getX() + " " + pft.getMapHash().get(1).getY());
        System.out.println("2 KEY " + pft.getMapHash().get(2).getX()+ " " + pft.getMapHash().get(2).getY());
        System.out.println("3 KEY " + pft.getMapHash().get(3).getX()+ " " + pft.getMapHash().get(3).getY());
        System.out.println("4 KEY " + pft.getMapHash().get(4).getX()+ " " + pft.getMapHash().get(4).getY());
        System.out.println("5 KEY " + pft.getMapHash().get(5).getX()+ " " + pft.getMapHash().get(5).getY());
        System.out.println("6 KEY " + pft.getMapHash().get(6).getX()+ " " + pft.getMapHash().get(6).getY());
        System.out.println("7 KEY" + pft.getMapHash().get(7).getX()+ " " + pft.getMapHash().get(7).getY());


        assertTrue(true);
    }


}
