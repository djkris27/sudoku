public class SudokuSolver {

    //metoda do rozwiązywania sudoku poprzez wstawianie w miejsce zer wszystkich możliwości (numery 1-9). Poprawność sudoku sprawdzana jest dla wszystkich możliwości
    //originTable - tabela oryginalna, wartości 0 to wartości, gdzie można wstawić możliwości;
    //halfTable - tabela wstępnie uzupełniona, stosowana do rekurencji,
    //number - numer elementu sudoku jako numer w tablicy jednowymiarowej
    public void sudokuTablesSolverBruteForce(int [] originTable, int [] halfTable, int number){
        if (number >= halfTable.length){ //jeśli number jest równe (lub awaryjnie większe) od wielkości tablicy, to wtedy można zacząć tworzyć obiekty SudokuTable(halfTable
            //i sprawdzać, czy dana SudokuTable jest prawidłowa (metoda checkTable());
            SudokuTable temp = new SudokuTable(halfTable);
            if (temp.checkTable()) {
                temp.show();
                System.out.println();
            }
        }//
        else { //jeśli number < halfTable.length, należy dalej wykonywać rekurencję dalej...
            if (originTable[number] != 0)
                sudokuTablesSolverBruteForce(originTable, halfTable, number+1);
            else {
                int [] tempTable = new int[halfTable.length];
                for (int i = 0; i < halfTable.length; i++){ //kopiowanie tablicy halfTable do tymczasowej tempTable
                    tempTable[i] = halfTable[i];
                }
                for (int i = 1; i <=9 ; i++){ //wypełnianie odpowiedniego miejsca w tablicy możliwymi liczbami i rekurencja...
                    tempTable[number] = i;
                    sudokuTablesSolverBruteForce(originTable, tempTable, number+1);
                }
            }
        }
    }

    //metoda do rozwiązywania sudoku poprzez wstawianie w miejsce zer wszystkich poprawnych możliwości (numery 1-9). Możliwość wstawienia odpowiedniej cyfry jeset sprawdzana
    //na etapie generowania możliwości - możliwości niepoprawne sa od razu pomijane (np. nie wstawimy drugi raz dwójki w wierszu, w którym jest już dwójka - metoda
    //sprawdzi, czy dwójka może być tam wstawiona i jeśli nie, to przejdzie do kolejnej opcji.
    //originTable - tabela oryginalna, wartości 0 to wartości, gdzie można wstawić możliwości;
    //halfTable - tabela wstępnie uzupełniona, stosowana do rekurencji,
    //number - numer elementu sudoku jako numer w tablicy jednowymiarowej
    public void sudokuTablesSolverV1(int [] originTable, int [] halfTable, int number){

        if (number >= halfTable.length){ //jeśli number jest równe (lub awaryjnie większe) od wielkości tablicy, to wtedy można zacząć tworzyć obiekty SudokuTable(halfTable
            //i sprawdzać, czy dana SudokuTable jest prawidłowa (metoda checkTable());
            SudokuTable temp = new SudokuTable(halfTable);
            if (temp.checkTable()) {
                temp.show();
                System.out.println();
            }
        }
        else { //jeśli number < halfTable.length, należy dalej wykonywać rekurencję dalej...
            if (originTable[number] != 0)
                sudokuTablesSolverV1(originTable, halfTable, number+1);
            else {
                int [] tempTable = new int[halfTable.length];
                for (int i = 0; i < halfTable.length; i++){ //kopiowanie tablicy halfTable do tymczasowej tempTable
                    tempTable[i] = halfTable[i];
                }
                for (int i = 1; i <=9; i++){ //wypełnianie odpowiedniego miejsca w tablicy możliwymi liczbami i rekurencja...
                    SudokuTable temp = new SudokuTable(halfTable); //inicjalizacja zmiennej SudokuTable temp musi się odbyć tutaj, bo jeśli będzie "wyżej", to nie wykonują się
                    //wszystkie możliwe rozwiązania (tak jakby zmienna zapamiętywała poprzednie parametry czy coś:-p)
//                    System.out.println("Number: " + number + ", row: " + ((number/9)+1) + ", column: " + ((number%9)+1) + ", i: " + i);
                    if (temp.notAllowedNumber((number/9), (number%9)).contains(i)) {
//                        System.out.println("contains, Continue...");
//                        temp.show();
                    }
                    else {
                        tempTable[number] = i;
                        sudokuTablesSolverV1(originTable, tempTable, number + 1);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {

        int [] badTestTable = {
                5, 4, 3, 9, 8, 1, 6, 7, 0,
                9, 1, 7, 2, 5, 6, 4, 3, 0,
                6, 8, 2, 4, 7, 3, 1, 5, 0,
                2, 9, 4, 5, 3, 8, 7, 1, 0,
                7, 3, 6, 1, 4, 9, 2, 8, 0,
                1, 5, 8, 6, 2, 7, 9, 4, 0,
                8, 6, 5, 7, 1, 2, 3, 9, 0,
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


        SudokuTable s = new SudokuTable(testTable4);
        s.show();
        System.out.println();
        SudokuSolver ss = new SudokuSolver();
        ss.sudokuTablesSolverV1(s.returnTable(), s.returnTable(), 0);



    }

}
