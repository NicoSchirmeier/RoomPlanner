import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class UI extends JFrame {
    private DrawingBoard drawingBoard;
    private MenuPanel menu;
    private JPanel createRoom;
    private JPanel addFurniture;

    public UI() {

        super();
        setPreferredSize(new Dimension(1600, 900));
        setResizable(true);

        drawingBoard = new DrawingBoard();
        getContentPane().add(drawingBoard, BorderLayout.CENTER);

        menu = new MenuPanel(this);
        getContentPane().add(menu, BorderLayout.NORTH);

        initCreateRoom();
        initAddFurniture();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initCreateRoom() {
        createRoom = new JPanel();

        JLabel xL = new JLabel("x: ");
        JTextField x = new JTextField();
        x.setPreferredSize(new Dimension(65, 25));
        JLabel yL = new JLabel("y: ");
        JTextField y = new JTextField();
        y.setPreferredSize(new Dimension(65, 25));
        JLabel lengthL = new JLabel("Length: ");
        JTextField length = new JTextField();
        length.setPreferredSize(new Dimension(65, 25));

        createRoom.add(xL);
        createRoom.add(x);
        createRoom.add(yL);
        createRoom.add(y);
        createRoom.add(lengthL);
        createRoom.add(length);

        JLabel dir = new JLabel(" | Direcrion: ");
        createRoom.add(dir);

        ButtonGroup directionSelection = new ButtonGroup();
        JRadioButton up = new JRadioButton("UP");
        JRadioButton down = new JRadioButton("DOWN");
        JRadioButton left = new JRadioButton("LEFT");
        JRadioButton right = new JRadioButton("RIGHT");
        directionSelection.add(up);
        directionSelection.add(down);
        directionSelection.add(left);
        directionSelection.add(right);

        createRoom.add(up);
        createRoom.add(down);
        createRoom.add(left);
        createRoom.add(right);

        JButton addWall = new JButton("Add");
        addWall.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int Ix = Integer.parseInt(x.getText());
                int Iy = Integer.parseInt(y.getText());
                int Ilength = Integer.parseInt(length.getText());
                int direction = -1;
                if(up.isSelected()) {
                    direction = Wall.UP;
                    y.setText("" + (Iy - Ilength));
                } else if(down.isSelected()) {
                    direction = Wall.DOWN;
                    y.setText("" + (Iy + Ilength));
                } else if(left.isSelected()) {
                    direction = Wall.LEFT;
                    x.setText("" + (Ix - Ilength));
                } else if(right.isSelected()) {
                    direction = Wall.RIGHT;
                    x.setText("" + (Ix + Ilength));
                }
                drawingBoard.addToDraw(new Wall(Ix, Iy, Ilength, direction));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        createRoom.add(addWall);
    }

    private void initAddFurniture() {
        addFurniture = new JPanel();
        JLabel xL = new JLabel("x: ");
        JTextField x = new JTextField();
        x.setPreferredSize(new Dimension(65, 25));
        JLabel yL = new JLabel("y: ");
        JTextField y = new JTextField();
        y.setPreferredSize(new Dimension(65, 25));
        JLabel widthL = new JLabel("Width: ");
        JTextField width = new JTextField();
        width.setPreferredSize(new Dimension(65, 25));
        JLabel heightL = new JLabel("Height: ");
        JTextField height = new JTextField();
        height.setPreferredSize(new Dimension(65, 25));
        JLabel nameL = new JLabel("Name: ");
        JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(135, 25));

        JColorChooser colorChooser = new JColorChooser();
        JOptionPane colorPickerPane = new JOptionPane("Pick a color:");
        colorPickerPane.add(colorChooser);

        JButton colorBtn = new JButton("Pick Color");
        colorBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDialog pick = colorPickerPane.createDialog("Pick Color");
                //pick.add(colorChooser);
                pick.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addFurniture.add(xL);
        addFurniture.add(x);
        addFurniture.add(yL);
        addFurniture.add(y);
        addFurniture.add(widthL);
        addFurniture.add(width);
        addFurniture.add(heightL);
        addFurniture.add(height);
        addFurniture.add(nameL);
        addFurniture.add(name);
        addFurniture.add(colorBtn);

        JButton add = new JButton("Add");
        add.setPreferredSize(new Dimension(75, 50));
        add.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                drawingBoard.addToDraw(new Furniture(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), name.getText(), colorChooser.getColor()));
                drawingBoard.repaint();
                menu.setAddingFurniture(false);
                setAddFurnitureVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addFurniture.add(add);
    }

    public void setCreateRoomVisible(Boolean isCreatingRoom) {
        if(isCreatingRoom) {
            getContentPane().add(createRoom, BorderLayout.SOUTH);
        } else {
            try {
                getContentPane().remove(createRoom);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        revalidate();
    }

    public void setAddFurnitureVisible(boolean isAddingFurniture) {
        if(isAddingFurniture) {
            getContentPane().add(addFurniture, BorderLayout.SOUTH);
        } else {
            try {
                getContentPane().remove(addFurniture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        revalidate();
    }

    public void clearBoard() {
        drawingBoard.clear();
    }

    public void removeLast() {
        drawingBoard.removeLast();
    }

    public ArrayList<Object> getBoardConmtent() {
        return drawingBoard.getToDraw();
    }

    public void addToBoard(Object object) {
        drawingBoard.addToDraw(object);
    }
}
