import java.util.*;

public class SudokuSolver {
    private int [] originSudokuTable;
    private LinkedList<SudokuTable> solvedSudoku = new LinkedList<SudokuTable>();


    public SudokuSolver(int [] originSudokuTable){
        this.originSudokuTable = originSudokuTable;
    }

    public int [] getOriginSudokuTable(){
        return originSudokuTable;
    }

    private void resolveSudokuTable() {
        sudokuTableSolverV1(originSudokuTable, originSudokuTable, 0);
    }

    //This method resolve sudoku by generate all possibilities - all that is mean all by input numbers 1-9 to all zeros fields.
    //ofiginTable - table, which have got fields witch numbers 1-9 (non changeable) and numbers 0 (to resolve)
    //halfTable - temp table containt testing values, added for recurension
    //number - number of actually testing element in 1-dimension table.
    public void sudokuTablesSolverBruteForce(int [] originTable, int [] halfTable, int number){
        if (number >= halfTable.length){
            //if number if equals (or eventually greater than) as halfTable length, than we can create SudokuTable object
            //to check, if this sudoku is normal (checkTable() method)
            SudokuTable temp = new SudokuTable(halfTable);
            if (temp.checkTable()) {
//              temp.show();
//              System.out.println();
                solvedSudoku.add(temp);
            }
        }
        else {
            //if number < halfTable.length, method have to be executed recurention again
            if (originTable[number] != 0)
                sudokuTablesSolverBruteForce(originTable, halfTable, number+1);
            else {
                int [] tempTable = new int[halfTable.length];
                for (int i = 0; i < halfTable.length; i++){ //copy table halfTable to temporary tempTable
                    tempTable[i] = halfTable[i];
                }
                for (int i = 1; i <=9 ; i++){ //filling actually position of method by numbers matching to this position and recurention again...
                    tempTable[number] = i;
                    sudokuTablesSolverBruteForce(originTable, tempTable, number+1);
                }
            }
        }
    }

    //This method resolve sudoku by generate all good possibilities - all good, becouse method check, if in this field it can put given number.
    //ofiginTable - table, which have got fields witch numbers 1-9 (non changeable) and numbers 0 (to resolve)
    //halfTable - temp table containt testing values, added for recurension
    //number - number of actually testing element in 1-dimension table.
    public void sudokuTableSolverV1(int [] originTable, int [] halfTable, int number){
        SudokuTable tempTest = new SudokuTable(halfTable);
        if (number >= halfTable.length){
            //if number if equals (or eventually greater than) as halfTable length, than we can create SudokuTable object
            //to check, if this sudoku is normal (checkTable() method)
            if (tempTest.checkTable()){
                solvedSudoku.add(tempTest);
            }
        }
        else {
            //if number < halfTable.length, method have to be executed recurention again for next table number
            if (originTable[number] != 0)
                sudokuTableSolverV1(originTable, halfTable, number+1); //bypasses number other than 0 in originTable
            else {
                for (int i = 1; i <=9; i++){ //filling actually position of method by numbers matching to this position and recurention again...
                    if (!(tempTest.notAllowedNumber((number/9), (number%9)).contains(i))){
                        halfTable[number] = i;
                        sudokuTableSolverV1(originTable, halfTable.clone(), number + 1);
                    }
                }
            }
        }
    }

    public int quantityofSolution(){
        return solvedSudoku.size();
    }

    public void showAllSolutions(){
        for (SudokuTable s : solvedSudoku){
            s.show();
            System.out.println();
        }
    }

/*


    public static void main(String[] args) {

        int [] badTestTable = {
                5, 4, 3, 9, 8, 1, 6, 7, 2,
                9, 1, 7, 2, 5, 6, 4, 3, 8,
                6, 8, 2, 4, 7, 3, 1, 5, 9,
                2, 9, 4, 5, 3, 8, 7, 1, 6,
                7, 3, 6, 1, 4, 9, 2, 8, 5,
                1, 5, 8, 6, 2, 7, 9, 4, 3,
                8, 6, 5, 7, 1, 2, 3, 9, 4,
                4, 2, 1, 3, 9, 5, 8, 6, 0, //zamienione 7-1
                3, 7, 9, 8, 6, 4, 5, 2, 0 //zamienione 1-7
        };

        int [] testTable1 = {
                0, 0, 0, 0, 0, 0, 0, 0, 2,
                0, 0, 7, 2, 0, 0, 4, 0, 0,
                0, 8, 0, 0, 7, 0, 0, 5, 0,
                0, 9, 0, 0, 0, 0, 0, 0, 6,
                0, 3, 6, 1, 0, 0, 2, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 4, 3,
                8, 0, 0, 0, 0, 0, 3, 0, 0,
                4, 0, 0, 0, 9, 5, 0, 0,0,
                0, 0, 0, 8, 0, 0, 0, 2, 1
        };

        int [] testTable2 = {
                0, 1, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 9, 5, 0,
                0, 4, 0, 0, 1, 9, 7, 0, 0,
                5, 0, 0, 0, 7, 0, 0, 0, 9,
                3, 0, 0, 0, 0, 6, 2, 0, 0,
                2, 8, 0, 0, 0, 0, 6, 1, 0,
                9, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 6, 0, 8, 7, 0, 0, 0,
                0, 3, 0, 0, 0, 0, 4, 0, 2
        };

        int [] testTable4 = {
                0, 0, 0, 0, 0, 8, 0, 0, 7,
                0, 0, 0, 2, 0, 0, 0, 0, 0,
                4, 0, 0, 0, 5, 0, 0, 0, 0,
                0, 5, 0, 0, 0, 6, 0, 3, 4,
                7, 0, 0, 0, 4, 0, 5, 0, 0,
                9, 0, 0, 1, 3, 0, 0, 6, 0,
                0, 0, 0, 0, 0, 9, 0, 0, 0,
                0, 0, 1, 5, 0, 0, 4, 0, 0,
                0, 0, 8, 3, 0, 0, 0, 2, 0
        };

        int [] testTable0 = {
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
        };

        int [] testTable1Mod = {
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 7, 2, 0, 0, 4, 0, 0,
                0, 8, 0, 0, 7, 0, 0, 5, 0,
                0, 9, 0, 0, 0, 0, 0, 0, 6,
                0, 3, 6, 1, 0, 0, 2, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 4, 3,
                8, 0, 0, 0, 0, 0, 3, 0, 0,
                4, 0, 0, 0, 9, 5, 0, 0,0,
                0, 0, 0, 8, 0, 0, 0, 2, 1
        };


//        SudokuTable s = new SudokuTable(testTable1);
//        s.show();

        SudokuSolver ss = new SudokuSolver(testTable1);
        new SudokuTable(ss.getOriginSudokuTable()).show();
        System.out.println();
//        ss.sudokuTableSolverV1(ss.getOriginSudokuTable(), ss.getOriginSudokuTable(), 0);
        ss.resolveSudokuTable();

        System.out.println("Quantity of this sudoku solutions: " + ss.quantityofSolution());
        if (ss.quantityofSolution() > 0)
            ss.showAllSolutions();

    }

*/

}
