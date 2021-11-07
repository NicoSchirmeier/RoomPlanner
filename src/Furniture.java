import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Furniture {

    private String name;
    private Color color;
    private int width;
    private int height;
    private double rotation;

    private boolean selectedToMove = false;
    private boolean selectedToRotate = false;

    private int x;
    private int y;
    private int diffX;
    private int diffY;

    private Color colorOld;

    public Furniture(int x, int y, int width, int height, String name, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.color = color;
        this.colorOld = color;
        this.rotation = 0.0;

    }

    public void move(Point mousePos) {
        if(mousePos == null || mousePos.x - diffX <= 0 || mousePos.y - diffY <= 0) return;
        x = mousePos.x - diffX;
        y = mousePos.y - diffY;
    }

    public Point getCenter() {
        return new Point((int)(x  + width/2), (int)(y + height/2));
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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

    public boolean isSelectedToMove() {
        return selectedToMove;
    }
    public boolean isSelectedToRotate() {
        return selectedToRotate;
    }

    public void selectToMove(Point mousePos) {
        this.selectedToMove = true;
        color = Color.RED;
        diffX = mousePos.x - x;
        diffY = mousePos.y - y;
    }

    public void selectToRotate() {
        this.selectedToRotate = true;
    }

    public void deselect() {
        this.selectedToMove = false;
        this.selectedToRotate = false;
        color = colorOld;
    }


}
