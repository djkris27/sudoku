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

public class SudokuFrame extends JFrame {
    private int [] originSudokuTable = new int[81];
    private SudokuSolver ss = null;
    private int CurrentSolutionNumber = 0;


    private boolean readOriginSudokuTableFromFile(File inputFile){
        try {
            Scanner myReader = new Scanner(inputFile);
            for (int i = 0; i < originSudokuTable.length; i++)
                originSudokuTable[i] = myReader.nextInt();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when read originSudokuTable from file. File not found?");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void fillJTextAreaIntTable(JTextArea jTextArea, int [] table){
        jTextArea.setText("");
        for (int i = 0; i < table.length; i++) {
            jTextArea.append(table[i] + " ");
            if ((i+1)%9 == 0)
                jTextArea.append("\n");
        }
    }

    public SudokuFrame(String name){
        super(name);
        for (int i = 0; i < originSudokuTable.length; i++)
            originSudokuTable[i] = 0;
        ss = new SudokuSolver(originSudokuTable);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocation(50,50);
        setLayout(new FlowLayout());



        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Plik");
        JMenuItem openFile = new JMenuItem("Otwórz plik");
        JMenuItem clear = new JMenuItem("Wyczyść");
        JMenu edit = new JMenu("Edycja");
        JMenuItem copy = new JMenuItem("Kopiuj");
        JMenuItem paste = new JMenuItem("Wklej");
        JMenuItem cut = new JMenuItem("Wytnij");







        JTextArea originSudoku = new JTextArea();

        //read file to originSudoku
        if (readOriginSudokuTableFromFile(new File("testTable.txt"))){
            fillJTextAreaIntTable(originSudoku, originSudokuTable);
        }
        else {
            originSudoku.setText("Błąd \nwczytywania \npliku.");
        }

        originSudoku.setFont(new Font("Arial", Font.BOLD, 20));
        originSudoku.setForeground(Color.BLUE);

        JTextArea resolvedSudoku = new JTextArea(9, 9);
        resolvedSudoku.setFont(new Font("Arial", Font.BOLD, 20));
        resolvedSudoku.setForeground(Color.BLUE);
        resolvedSudoku.setEditable(false);

        JLabel quantityOfSolution = new JLabel("Rozwiązanie: ");


        JButton resolve = new JButton("Rozwiąż sudoku");
        JButton previous = new JButton("Poprzednie");
        JButton next = new JButton("Następne");

        resolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ss = new SudokuSolver(originSudokuTable);
                boolean goodSudokuNumbers = true;
                Scanner myReader = new Scanner(originSudoku.getText());
                for (int i = 0; i < originSudokuTable.length; i++) {
                    try {
                        originSudokuTable[i] = myReader.nextInt();
                        if ((originSudokuTable[i] < 0) || (originSudokuTable[i] > 9))
                            goodSudokuNumbers = false;
                    } catch (Exception e){
                        resolvedSudoku.setText("Błędnie \nwprowadzone \nliczby.");
                        goodSudokuNumbers = false;
                    }
                }
                myReader.close();

                //temporary showing origininSudokuTable in standard sout - after test delete this section
                for (int i : originSudokuTable)
                    System.out.print(i + " ");
                System.out.println();
                /////////////////////////////////////////////////////////////////////////////////////////

                if (goodSudokuNumbers){
                    ss.resolveSudokuTable();

                    quantityOfSolution.setText("Rozwiązanie: " + (CurrentSolutionNumber + 1) + "/" + ss.quantityOfSolution()); //you must change numeration of resolved Sudoku
                    System.out.println();
                    System.out.println(ss.quantityOfSolution());

                    if (ss.quantityOfSolution() >0 ){
                        fillJTextAreaIntTable(resolvedSudoku, ss.getAllSolutioins().get(CurrentSolutionNumber).returnTable());
                    }
                    else
                        resolvedSudoku.setText("BRAK \nROZWIĄZANIA");
                }
                else
                    resolvedSudoku.setText("Błędnie \nwprowadzone \nliczby.");
            }
        });


        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc =new JFileChooser();
                fc.showOpenDialog(null);
                File inputTempFile = fc.getSelectedFile();

                if (readOriginSudokuTableFromFile(fc.getSelectedFile())){
                    fillJTextAreaIntTable(originSudoku, originSudokuTable);
                }
                else {
                    originSudoku.setText("Problem \nwitch \nfile");
                }
                resolvedSudoku.setText("");
            }
        });

        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        menuBar.add(file);
        file.add(openFile);
        file.add(clear);
        menuBar.add(edit);
        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        add(menuBar);


        add(originSudoku);
        add(resolvedSudoku);

        add(quantityOfSolution);
        add(resolve);
        add(previous);
        add(next);

        setVisible(true);
    }

}
