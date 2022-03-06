import java.awt.*;

public class Temp2 {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuFrame("Sudoku by djkris V1");
            }
        });
    }
}
