import javax.swing.*;
import java.awt.*;

public class SudokuFrame extends JFrame {

    public SudokuFrame(String name){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocation(50,50);
        setLayout(new GridLayout());

        JButton test = new JButton("Przycisk 1");
        add(test);

        setVisible(true);
    }

}
