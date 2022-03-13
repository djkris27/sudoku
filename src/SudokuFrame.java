/*
Elementy, które należy umieścić:
- obszar tekstowy do wpisywania sudoku
- obszar tekstowy do wpisywania rozwiązanego sudoku
- przycisk do wywołania rozwiązywania sudoku
- przyciski przewijania rozwiązań (w przypadku więcej niż jednego rozwiązania
- obszar tekstowy pokazujący ilość rozwiązań oraz aktualnie wyświetlane rozwiązanie (np. 1/3)
- element obrazujący aktualną pracę (w przypadku długotrwałego rozwiązywania sudoku) oraz zakończenie rozwiązywania sudoku
*/


import javax.swing.*;
import java.awt.*;

public class SudokuFrame extends JFrame {

    public SudokuFrame(String name){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocation(50,50);
        setLayout(new FlowLayout());

        JTextArea originSudoku = new JTextArea(
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 \n" +
                " 0, 0, 0, 0, 0, 0, 0, 0, 0 "
        );
        JTextArea resolvedSudoku = new JTextArea(
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? \n" +
                " ?, ?, ?, ?, ?, ?, ?, ?, ? "
        );
        JButton resolve = new JButton("Rozwiąż sudoku");

        add(originSudoku);
        add(resolve);
        add(resolvedSudoku);

        setVisible(true);
    }

}
