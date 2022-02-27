import java.util.HashSet;

public class MiniSudokuTest {
    public static int [][] table = new int [3][3];

    public MiniSudokuTest(){
        for (int i = 0; i< table.length; i++)
            for (int j = 0; j<table[i].length; j++)
                table[i][j] = 0;
    }

    //konstruktor przyjmujący tablicę jednowymiarową
    public MiniSudokuTest(int [] longTable){
        putAll(longTable);
    }

    //wstawia określoną wartość value w odpowiednim wierszu row oraz columnie column tabeli
    public void put(int row, int column, int value) {
        table[row-1][column-1] = value;
    }

    //wprowadza tablicę jednowymiarową do tablicy wielowymiarowej - dla ułatwienia kolejnych działań
    public void putAll(int [] longtable) {
        int count = 0;
        for (int i = 0; i < table.length; i++)
            for (int j = 0; j < table[0].length; j++)
//				System.out.println("a" + longtable[i+j]);
                table[i][j] = longtable[count++];
    }

    public int [] returnTableMiniSudokuTest(){
        int [] temp = new int[(table.length*table[0].length)];
        int counter = 0;
        for (int i = 0; i < table.length; i++)
            for (int j = 0; j < table[0].length; j++) {
                temp[counter] = table[i][j];
                counter++;
            }
        return temp;
    }

    public boolean checkTable(){
        HashSet<Integer> tempSet = new HashSet<Integer>();
        int sum = 0;
        for (int i = 0; i < table.length; i++) { //mnożniki triRow i triColumn powodują przeszukiwanie odpowiedniego pomniejszego kwadratu
            for (int j = 0; j < table[0].length; j++) {
                tempSet.add(table[i][j]);
                sum += table[i][j];
            }
        }
        if ((sum == 45) && (tempSet.size() == 9))
            return true;
        else
            return false;
    }

    public void show() {
        for (int i = 0; i< table.length; i++) {
            for (int j = 0; j<table[i].length; j++)
                System.out.print(" " + table[i][j]);
            System.out.println();
        }
    }
}
