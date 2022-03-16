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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SudokuFrame extends JFrame {
//    SudokuSolver sudokuSolver = new SudokuSolver();

    public SudokuFrame(String name){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocation(50,50);
        setLayout(new FlowLayout());


        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open File");
        menuBar.add(file);
        file.add(openFile);
        add(menuBar);



        JTextArea originSudoku = new JTextArea();
        int [] originSudokuTable = new int[81];
        //read file to originSudoku
        try {
            File inputFile = new File("testTable.txt");
            Scanner myReader = new Scanner(inputFile);

            for (int i = 0; i < originSudokuTable.length; i++)
                originSudokuTable[i] = myReader.nextInt();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found.");
            e.printStackTrace();
        }
        for (int i = 0; i < originSudokuTable.length; i++){
            originSudoku.append(originSudokuTable[i] + " ");
            if ((i+1)%9 == 0)
                originSudoku.append("\n");
        }


        originSudoku.setFont(new Font("Arial", Font.BOLD, 20));
        originSudoku.setForeground(Color.BLUE);

        JTextArea resolvedSudoku = new JTextArea(9, 9);
        resolvedSudoku.setFont(new Font("Arial", Font.BOLD, 20));
        resolvedSudoku.setForeground(Color.BLUE);
        resolvedSudoku.setEditable(false);
        JButton resolve = new JButton("Rozwiąż sudoku");

        resolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("ActionListener test");
                resolvedSudoku.setText("BRAK \nROZWIĄZANIA");
            }
        });


        add(originSudoku);
        add(resolve);
        add(resolvedSudoku);

        setVisible(true);
    }

}
