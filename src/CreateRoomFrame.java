import javax.swing.*;
import java.awt.*;

public class CreateRoomFrame extends JFrame {
    JPanel Pos;
    JPanel Cross;
    JFormattedTextField x;
    JFormattedTextField y;

    public CreateRoomFrame() {
        setPreferredSize(new Dimension(900, 500));
        setSize(900, 500);
        setResizable(false);

        x = new JFormattedTextField("X: ");
        y = new JFormattedTextField("y: ");

        Pos = new JPanel();
        Pos.add(x);
        Pos.add(y);
        getContentPane().add(Pos);
        initCross();

        pack();
        setVisible(true);
    }

    private void initCross() {
        Cross = new JPanel();
        JButton up = new JButton("Up");
        up.setPreferredSize(new Dimension(50, 50));
        JButton down = new JButton("Down");
        down.setSize(50, 50);
        JButton left = new JButton("Left");
        left.setSize(50, 50);
        JButton right = new JButton("Right");
        right.setSize(50, 50);

        Cross.add(up);
        Cross.add(down);
        Cross.add(left);
        Cross.add(right);

        getContentPane().add(Cross);
    }
}
