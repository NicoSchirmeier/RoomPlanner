import java.awt.*;

public class Wall {
    private int x;
    private int y;
    private int eX;
    private int eY;
    private int length;
    private int direction;
    private Color color;


    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public Wall(int x, int y, int length, int direction) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.direction = direction;
        this.color = Color.GRAY;
        switch (direction) {
            case UP:
                this.eY = y - length;
                this.eX = x;
                break;
            case DOWN:
                this.eY = y + length;
                this.eX = x;
                break;
            case LEFT:
                this.eX = x - length;
                this.eY = y;
                break;
            case  RIGHT:
                this.eX = x + length;
                this.eY = y;
                break;
            default:
                System.err.println("ERROR: Impossible direction!");
        }
        System.err.println("Creating Wall at y: " + y + " x: " + x);
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

    public int geteX() {
        return eX;
    }

    public void seteX(int eX) {
        this.eX = eX;
    }

    public int geteY() {
        return eY;
    }

    public void seteY(int eY) {
        this.eY = eY;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
