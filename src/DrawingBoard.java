import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DrawingBoard extends JPanel {

    private ArrayList<Object> toDraw;

    public DrawingBoard() {
        setBorder(new LineBorder(Color.GRAY));
        toDraw = new ArrayList<>();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    for(Object drawable : toDraw) {
                        if(drawable instanceof Furniture) {
                            if(((Furniture) drawable).getX() <= e.getX() && e.getX() <= ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() && e.getY() >= ((Furniture) drawable).getY() && e.getY() <= ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight()) {
                              int width = ((Furniture) drawable).getHeight();
                              int height = ((Furniture) drawable).getWidth();
                              ((Furniture) drawable).setWidth(width);
                              ((Furniture) drawable).setHeight(height);
                              System.out.println("Rotated " + ((Furniture) drawable).getName() + "\n    Width: " + ((Furniture) drawable).getWidth() + "\n    Height: " + ((Furniture) drawable).getHeight());
                              repaint();
                              break;
                            }
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    boolean selected = false;
                    for(Object drawable : toDraw) {
                        if(drawable instanceof Furniture) {
                            if(!((Furniture) drawable).isSelected()) {
                                if(((Furniture) drawable).getX() <= e.getX() && e.getX() <= ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() && e.getY() >= ((Furniture) drawable).getY() && e.getY() <= ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight()) {
                                    selected = true;
                                    ((Furniture) drawable).setSelected(true);
                                    System.err.println("Selected: " + ((Furniture) drawable).getName());
                                }
                            }
                        }
                    }
                    if(!selected) {
                        setAlignmentY(e.getY());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    for(Object drawable : toDraw) {
                        if(drawable instanceof Furniture) {
                            if(((Furniture) drawable).isSelected()) {
                                ((Furniture) drawable).setSelected(false);
                                System.err.println("Deselected: " + ((Furniture) drawable).getName());
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        Thread repaintThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int differenceX = 0;
                int differenceY = 0;
                while(true) {
                    boolean loop = false;
                    Furniture selected = null;
                    for(Object drawable : toDraw) {
                        if(drawable instanceof Furniture) {
                            if(((Furniture) drawable).isSelected()) {
                                loop = true;
                                selected = (Furniture) drawable;
                                break;
                            }
                        }
                    }
                    if(!loop) {
                        try {
                            Thread.sleep(350);
                            //System.err.println("Nothing selected - Sleeping 350ms");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(!selected.equals(null)) {
                            selected.setX(getMousePosition().x);
                            selected.setY(getMousePosition().y);
                        }
                    }
                    repaint();
                }
            }
        });

        repaintThread.setName("repaintThread");
        repaintThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the rectangle here

        for(Object drawable : toDraw) {
            if (drawable instanceof Furniture) {
                g.setColor(((Furniture) drawable).getColor());
                g.drawString(((Furniture) drawable).getName(), (((Furniture) drawable).getX() + (((Furniture) drawable).getWidth() / 2) - 10 * ((Furniture) drawable).getName().length()), ((Furniture) drawable).getY() + (((Furniture) drawable).getHeight() / 2));
                g.drawRect(((Furniture) drawable).getX(), ((Furniture) drawable).getY(), ((Furniture) drawable).getWidth(), ((Furniture) drawable).getHeight());
                if (getMousePosition() != null && ((Furniture) drawable).getX() <= getMousePosition().x && getMousePosition().x <= ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() && getMousePosition().y >= ((Furniture) drawable).getY() && getMousePosition().y <= ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight()) {
                    g.drawString("" + ((Furniture) drawable).getHeight(), ((Furniture) drawable).getX() - ("" + ((Furniture) drawable).getHeight()).length() * 10, ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight() / 2);
                    g.drawString("" + ((Furniture) drawable).getWidth(), ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() / 2, ((Furniture) drawable).getY() - 2 * ("" + ((Furniture) drawable).getWidth()).length());
                }
            } else if (drawable instanceof Wall) {
                    g.setColor(Color.GRAY);
                    switch (((Wall) drawable).getDirection()) {
                        case Wall.UP:
                            g.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() - 2 * ("" + ((Wall) drawable).getLength()).length(), ((Wall) drawable).getY() - ((Wall) drawable).getLength() / 2);
                            break;
                        case Wall.DOWN:
                            g.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() + 5, ((Wall) drawable).getY() + ((Wall) drawable).getLength() / 2);
                            break;
                        case Wall.LEFT:
                            g.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() - ((Wall) drawable).getLength() / 2, ((Wall) drawable).getY() - 15);
                            break;
                        case Wall.RIGHT:
                            g.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() + ((Wall) drawable).getLength() / 2, ((Wall) drawable).getY() + 15);
                            break;
                        default:
                            System.err.println("ERROR: Impossible direction!");
                    }
                    g.drawLine(((Wall) drawable).getX(), ((Wall) drawable).getY(), ((Wall) drawable).geteX(), ((Wall) drawable).geteY());
            }
        }
        g.drawRect(50, 50, 100, 100);
        g.drawString("100cm", 10, 105);
        g.drawString("100cm", 85, 165);
    }

    public void clear() {
        toDraw = new ArrayList<>();
        repaint();
    }

    public void addToDraw(Object object) {
        toDraw.add(object);
        repaint();
    }

    public void removeLast() {
        if(toDraw.size() > 0)toDraw.remove(toDraw.size() - 1);
        repaint();
    }

    public ArrayList<Object> getToDraw() {
        return toDraw;
    }
}
