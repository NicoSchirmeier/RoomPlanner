import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPanel extends JPanel {
    private JButton drawRoom;
    private JButton addFurniture;
    private JButton clear;
    private JButton removeLast;
    private JButton save;
    private JButton load;

    private boolean isDrawingRoom = false;
    private boolean isAddingFurniture = false;

    public MenuPanel(UI ui) {
        drawRoom = new JButton("Draw Room");
        drawRoom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isDrawingRoom = !isDrawingRoom;
                if(isAddingFurniture) {
                    isAddingFurniture = false;
                    ui.setAddFurnitureVisible(isAddingFurniture);
                }

                ui.setCreateRoomVisible(isDrawingRoom);
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
        add(drawRoom);

        addFurniture = new JButton("add Piece");
        addFurniture.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isAddingFurniture = !isAddingFurniture;
                if(isDrawingRoom) {
                    isDrawingRoom = false;
                    ui.setCreateRoomVisible(false);
                }
                ui.setAddFurnitureVisible(isAddingFurniture);
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

        add(addFurniture);

        clear = new JButton("Clear Board");
        clear.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.clearBoard();
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

        add(clear);

        removeLast = new JButton("Remove Last");
        removeLast.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.removeLast();
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

        add(removeLast);

        save = new JButton("Save");
        save.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileDialog fileDialog = new FileDialog(ui, "Save", FileDialog.SAVE);
                fileDialog.setFile("flat.csv");
                fileDialog.setVisible(true);

                FileWriter fileWriter = null;

                String filename = fileDialog.getFile();
                String dir = fileDialog.getDirectory();
                try {
                    fileWriter = new FileWriter(dir + filename);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if(!fileWriter.equals(null)) {
                    try {
                        for(Object drawable : ui.getBoardConmtent()) {
                                if(drawable instanceof Furniture) {
                                    Furniture furniture = (Furniture) drawable;
                                    fileWriter.write(furniture.getClass().toString() + ',' + furniture.getX() + ',' + furniture.getY() + ',' + furniture.getWidth() + ',' + furniture.getHeight() + ',' + furniture.getName() + ',' + furniture.getColor().getRGB() + "\n");
                                } else if (drawable instanceof Wall) {
                                    Wall wall = (Wall) drawable;
                                    fileWriter.write(wall.getClass().toString() + ',' + wall.getX() + ',' + wall.getY() + ',' + wall.getLength() + ',' + wall.getDirection() + "\n");
                                }
                        }
                        fileWriter.close();
                        System.out.println("File Saved: " + dir + filename);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                }

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
        add(save);

        load = new JButton("Open");
        load.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileDialog fileDialog = new FileDialog(ui, "Open", FileDialog.LOAD);
                fileDialog.setVisible(true);

                if(fileDialog.getFile().equals(null)) return;
                File file = new File(fileDialog.getDirectory() + fileDialog.getFile());
                try {
                    Scanner scanner = new Scanner(file);
                    while(scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String readClass = "";
                        for(int i = 0; i < line.length(); i++) {
                            if(line.charAt(i) == ',') {
                                readClass = line.substring(0, i);
                                System.out.println(i + " - " + line);
                                System.out.println(readClass);
                                break;
                            }
                        }
                        ArrayList<String> split = seperate(line);
                        if(readClass.equals(Furniture.class.toString())) {
                            ui.addToBoard(new Furniture(Integer.parseInt(split.get(0)), Integer.parseInt(split.get(1)), Integer.parseInt(split.get(2)), Integer.parseInt(split.get(3)), split.get(4), new Color(Integer.parseInt(split.get(5)))));
                        } else if (readClass.equals(Wall.class.toString())) {
                            ui.addToBoard(new Wall(Integer.parseInt(split.get(0)), Integer.parseInt(split.get(1)), Integer.parseInt(split.get(2)), Integer.parseInt(split.get(3))));
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

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
        add(load);
    }

    private ArrayList<String> seperate(String line) {
        ArrayList<String> split = new ArrayList<>();
        int last = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == ',') {
                if(last != 0) {
                    split.add(line.substring(last+1, i));
                }
                last = i;
            }
        }
        split.add(line.substring(last+1, line.length()));
        System.out.println(split.toString());

        return split;
    }

    public void setAddingFurniture(boolean addingFurniture) {
        isAddingFurniture = addingFurniture;
    }

}
