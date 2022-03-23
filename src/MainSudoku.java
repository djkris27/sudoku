import java.awt.*;

public class MainSudoku {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuFrame("Sudoku by djkris27 V1");
            }
        });
    }
}
