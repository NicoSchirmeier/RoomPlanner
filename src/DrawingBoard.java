import javax.swing.*;
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
                if(SwingUtilities.isLeftMouseButton(e)) {
                    boolean selected = false;
                    for(Object drawable : toDraw) {
                        if(drawable instanceof Furniture) {
                            if(!((Furniture) drawable).isSelectedToMove()) {
                                if(((Furniture) drawable).getX() <= e.getX() && e.getX() <= ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() && e.getY() >= ((Furniture) drawable).getY() && e.getY() <= ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight()) {
                                    selected = true;
                                    ((Furniture) drawable).selectToMove(getMousePosition());
                                    System.err.println("SelectedToMove: " + ((Furniture) drawable).getName());
                                }
                            }
                        }
                    }
                    if(!selected) {
                        setAlignmentY(e.getY());
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    for(Object drawable : toDraw) {
                        if(drawable instanceof Furniture) {
                            if(!((Furniture) drawable).isSelectedToMove()) {
                                if(((Furniture) drawable).getX() <= e.getX() && e.getX() <= ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() && e.getY() >= ((Furniture) drawable).getY() && e.getY() <= ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight()) {
                                    ((Furniture) drawable).selectToRotate();
                                    System.err.println("SelectedToRotate: " + ((Furniture) drawable).getName());
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e)) {
                    for(Object drawable : toDraw) {
                        if(drawable instanceof Furniture) {
                            if(((Furniture) drawable).isSelectedToMove() || ((Furniture) drawable).isSelectedToRotate()) {
                                ((Furniture) drawable).deselect();
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
                            if(((Furniture) drawable).isSelectedToMove()) {
                                loop = true;
                                selected = (Furniture) drawable;
                                break;
                            } else if (((Furniture) drawable).isSelectedToRotate()) {
                                loop = true;
                                selected = (Furniture) drawable;
                                break;
                            }
                        }
                    }
                    if(loop == false) {
                        try {
                            Thread.sleep(150);
                            //System.err.println("Nothing selected - Sleeping 150ms");
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
                            if(selected.isSelectedToMove()) {
                                selected.move(getMousePosition());
                            } else if (selected.isSelectedToRotate() && getMousePosition() != null) {
                                selected.setRotation(Math.atan2(getMousePosition().y, getMousePosition().x));
                            }
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        for(Object drawable : toDraw) {
            if(drawable == null || g2d == null) return;
            if (drawable instanceof Furniture) {
                g2d.drawString(((Furniture) drawable).getName(), (((Furniture) drawable).getX() + (((Furniture) drawable).getWidth() / 2) - 10 * ((Furniture) drawable).getName().length()), ((Furniture) drawable).getY() + (((Furniture) drawable).getHeight() / 2));
                //if(((Furniture) drawable).getRotation() != 0.0)g2d.rotate(((Furniture) drawable).getRotation() );

                g2d.setColor(((Furniture) drawable).getColor());
                g2d.drawRect(((Furniture) drawable).getX(), ((Furniture) drawable).getY(), ((Furniture) drawable).getWidth(), ((Furniture) drawable).getHeight());

                if(getMousePosition() != null) {
                    if (((Furniture) drawable).getX() <= getMousePosition().x && getMousePosition().x <= ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() && getMousePosition().y >= ((Furniture) drawable).getY() && getMousePosition().y <= ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight()) {
                        g2d.drawString("" + ((Furniture) drawable).getHeight(), ((Furniture) drawable).getX() - ("" + ((Furniture) drawable).getHeight()).length() * 10, ((Furniture) drawable).getY() + ((Furniture) drawable).getHeight() / 2);
                        g2d.drawString("" + ((Furniture) drawable).getWidth(), ((Furniture) drawable).getX() + ((Furniture) drawable).getWidth() / 2, ((Furniture) drawable).getY() - 2 * ("" + ((Furniture) drawable).getWidth()).length());
                    }
                    if (((Furniture) drawable).isSelectedToRotate()) {
                        System.out.println(((Furniture) drawable).getRotation());
                        g2d.fillOval(((Furniture) drawable).getCenter().x-5, ((Furniture) drawable).getCenter().y-5, 10, 10);
                        g2d.drawLine(((Furniture) drawable).getCenter().x, ((Furniture) drawable).getCenter().y, getMousePosition().x, getMousePosition().y);
                        g2d.fillOval(getMousePosition().x-5, getMousePosition().y-5, 10, 10);
                    }
                }
                g2d.rotate(0);
            } else if (drawable instanceof Wall) {
                    g2d.setColor(Color.GRAY);
                    switch (((Wall) drawable).getDirection()) {
                        case Wall.UP:
                            g2d.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() - 2 * ("" + ((Wall) drawable).getLength()).length(), ((Wall) drawable).getY() - ((Wall) drawable).getLength() / 2);
                            break;
                        case Wall.DOWN:
                            g2d.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() + 5, ((Wall) drawable).getY() + ((Wall) drawable).getLength() / 2);
                            break;
                        case Wall.LEFT:
                            g2d.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() - ((Wall) drawable).getLength() / 2, ((Wall) drawable).getY() - 15);
                            break;
                        case Wall.RIGHT:
                            g2d.drawString("" + ((Wall) drawable).getLength(), ((Wall) drawable).getX() + ((Wall) drawable).getLength() / 2, ((Wall) drawable).getY() + 15);
                            break;
                        default:
                            System.err.println("ERROR: Impossible direction!");
                    }
                    g2d.drawLine(((Wall) drawable).getX(), ((Wall) drawable).getY(), ((Wall) drawable).geteX(), ((Wall) drawable).geteY());
            }
        }
        g2d.drawRect(50, 50, 100, 100);
        g2d.drawString("100cm", 10, 105);
        g2d.drawString("100cm", 85, 165);
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
