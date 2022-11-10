package client.main;

public class Player {
   private int x;
   private int y;

    public Player(Coordinates cord){
        this.x = cord.getX();
        this.y = cord.getY();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
