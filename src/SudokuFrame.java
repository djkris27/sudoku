/*
Elementy, które należy umieścić:
- element obrazujący aktualny postęp pracy (w przypadku długotrwałego rozwiązywania sudoku) oraz zakończenie rozwiązywania sudoku
*/


import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuFrame extends JFrame {
    private int [] originSudokuTable = new int[81];
    private SudokuSolver ss = null;
    private int currentSolutionNumber = 0;


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
            if (((i+1)%9 == 0) && ((i+1)%81 != 0))
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
        JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
            copy.setText("Kopiuj");
            copy.setMnemonic(KeyEvent.VK_C);
        JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
            paste.setText("Wklej");
            paste.setMnemonic(KeyEvent.VK_V);
        JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
            cut.setText("Wytnij");
            cut.setMnemonic(KeyEvent.VK_X);







        JTextArea originSudoku = new JTextArea(9, 9);

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
                    currentSolutionNumber = 1;

                    quantityOfSolution.setText("Rozwiązanie: " + currentSolutionNumber + "/" + ss.quantityOfSolution());

                    if (ss.quantityOfSolution() >0 ){
                        fillJTextAreaIntTable(resolvedSudoku, ss.getAllSolutioins().get(currentSolutionNumber-1).returnTable());
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
                ss = new SudokuSolver(originSudokuTable);
                resolvedSudoku.setText("");
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < originSudokuTable.length; i++)
                    originSudokuTable[i] = 0;
                fillJTextAreaIntTable(originSudoku, originSudokuTable);
                ss = new SudokuSolver(originSudokuTable);
                resolvedSudoku.setText("");
                quantityOfSolution.setText("Rozwiązanie: ");
            }
        });

        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (ss.quantityOfSolution() > 0) {
                    if (currentSolutionNumber > 1)
                        currentSolutionNumber--;
                    fillJTextAreaIntTable(resolvedSudoku, ss.getAllSolutioins().get(currentSolutionNumber-1).returnTable());
                    quantityOfSolution.setText("Rozwiązanie: " + currentSolutionNumber + "/" + ss.quantityOfSolution());
                }
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (ss.quantityOfSolution() > 0) {
                    if (currentSolutionNumber < ss.quantityOfSolution())
                        currentSolutionNumber++;
                    fillJTextAreaIntTable(resolvedSudoku, ss.getAllSolutioins().get(currentSolutionNumber-1).returnTable());
                    quantityOfSolution.setText("Rozwiązanie: " + currentSolutionNumber + "/" + ss.quantityOfSolution());
                }
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
