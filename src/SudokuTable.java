import java.util.*;

public class SudokuTable {
	private static int [][] table = new int [9][9];

	//konstruktor tablcę sudoku wypełnioną zerami
	public SudokuTable(){
		for (int i = 0; i< table.length; i++)
			for (int j = 0; j<table[i].length; j++)
				table[i][j] = 0;
	}

	//konstruktor przyjmujący tablicę jednowymiarową
	public SudokuTable(int [] longTable){
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
	
	//metoda wyświetla tablicę Sudoku
	public void show() {
		for (int i = 0; i< table.length; i++) {
			for (int j = 0; j<table[i].length; j++)
			System.out.print(" " + table[i][j]);
			System.out.println();
		}
	}
	
	//sprawdza, czy suma elementów w wierszu jest poprawna (powinno być 45) - to pierwsza faza sprawdzania
	private boolean goodRowSum(int row) {
		int counter = 0;
		for (int i = 0; i<table[row-1].length; i++)
			counter += table[row-1][i];
		return counter == 45;
	}
	
	//sprawdza, czy suma elementów w kolumnie jest poprawna (powinno być 45) - to pierwsza faza sprawdzania
	private boolean goodColumnSum(int column) {
		int counter = 0;
		for (int i = 0; i<table.length; i++)
			counter += table[i][column-1];
		return counter == 45;
	}	
	
	//metoda sprawdza, czy w wierszu row zgadza się suma i liczby się nie powtarzają; 
	private boolean checkRow(int row) {
		if (!goodRowSum(row))
			return false; //sprawdza, czy suma elementów w wierszu jest prawidłowa - jeśli nie, to znaczy, że występują w nim liczby nieprawidłowe (<=0; >9) lub duplikaty;
		for (int i = 0; i<table[row-1].length; i++) {
			for (int j = i+1; j<table[row-1].length; j++) {
				if (table[row-1][i] == table[row-1][j]) { //sprawdza, czy aktualnie sprawdzany element jest identyczny jak któryś kolejny w wierszu - jeśli tak, to zwraca false
//					System.out.println("PROBLEM: row: " + row + ", column: " + (j+1));
					return false;
				}
			}
		}
		return true; //zwraca true jeśli wczesniej nie wykryło jakiegoś problemu w wierszu;
	}
	
	//sprawdza wszystkie wiersze
	private boolean checkAllRow() {
		for (int i = 1; i < 10; i++) {
			if (!checkRow(i))
				return false;
		}
		return true;
	}
	
	//metoda sprawdza, czy w kolumnie column zgadza się suma i liczby się nie powtarzają; 
	private boolean checkColumn(int column) {
		if (!goodColumnSum(column))
			return false; //sprawdza, czy suma elementów w kolumnie jest prawidłowa - jeśli nie, to znaczy, że występują w nim liczby nieprawidłowe (<=0; >9) lub duplikaty;
		for (int i = 0; i<table.length; i++) {
			for (int j = i+1; j<table.length; j++) {
				if (table[i][column-1] == table[j][column-1]) { //sprawdza, czy aktualnie sprawdzany element jest identyczny jak któryś kolejny w kolumnie - jeśli tak, to zwraca false
//					System.out.println("PROBLEM: row: " + row + ", column: " + (j+1));
					return false;
				}
			}
		}
		return true; //zwraca true jeśli wczesniej nie wykryło jakiegoś problemu w wierszu;
	}
	
	//sprawdza wszystkie kolumny
	private boolean checkAllColumn() {
		for (int i = 1; i < 10; i++) {
			if (!checkColumn(i))
				return false;
		}
		return true;
	}

	//metoda sprawdza poniejsze kwadraty w celu sprawdzenia, czy suma wartości jest poprawne oraz czy nie ma powtórzeń.
	private boolean checkSmallSquares() {
		for (int triRow = 0; triRow<=2; triRow++) {
			for (int triColumn = 0; triColumn <= 2; triColumn++) {
				HashSet<Integer> tempSet = new HashSet<Integer>();
				int sum = 0;
				for (int i = triRow * 3; i < (triRow * 3 + 3); i++) { //mnożniki triRow i triColumn powodują przeszukiwanie odpowiedniego pomniejszego kwadratu
					for (int j = triColumn * 3; j < (triColumn * 3 + 3); j++) {
						tempSet.add(table[i][j]);
//						System.out.println("table[" + i + "][" + j + "]");
						sum += table[i][j];
					}
				}
				if (!(tempSet.size() == 9 && sum == 45))
					return false;
			}
		}
		return true;
	}

	//sprawdza całą tablicę sudoku, czy spełnia wszystkie założenia i jest prawidłowa.
	public boolean checkTable(){
		if (checkAllRow() && checkAllColumn() && checkSmallSquares())
			return true;
		else
			return false;
	}

	//zwraca tabelę jednowymiarową o wartości całej planszy sudoku w formie jednowymiarowej
	public int [] returnTable(){
		int [] temp = new int[(table.length*table[0].length)];
		int counter = 0;
		for (int i = 0; i < table.length; i++)
			for (int j = 0; j < table[0].length; j++) {
				temp[counter] = table[i][j];
				counter++;
			}
		return temp;
	}

	//zwraca HashSet zawierający wszystkie wartości, które sa niedozwolone w komórce wskazanej przez row i column.
	public HashSet<Integer> notAllowedNumber(int row, int column){
		HashSet<Integer> tempHashSet = new HashSet<Integer>();
		for (int i = 0; i<table.length; i++) //sprawdza użyte cyfry w wierszu
			tempHashSet.add(table[row-1][i]);
		for (int i = 0; i<table[0].length; i++) //sprawdza użyte cyfry w kolumnie
		tempHashSet.add(table[i][column-1]);
		for (int i = ((row-1)/3)*3; i < (((row-1)/3)*3)+3; i++) //sprawdza użyte cyfry w małym kwadracie sudoku
			for (int j = ((column-1)/3)*3; j < (((column-1)/3)*3)+3; j++)
				tempHashSet.add(table[i][j]);
		tempHashSet.remove(0);
		return tempHashSet;
	}
	
	
	
	
	
	
	//moteda testowa main do sprawdzania działania klasy Show
//	public static void main (String [] args) {
//		int [] goodTestTable = {
//				5, 4, 3, 9, 8, 1, 6, 7, 2,
//				9, 1, 7, 2, 5, 6, 4, 3, 8,
//				6, 8, 2, 4, 7, 3, 1, 5, 9,
//				2, 9, 4, 5, 3, 8, 7, 1, 6,
//				7, 3, 6, 1, 4, 9, 2, 8, 5,
//				1, 5, 8, 6, 2, 7, 9, 4, 3,
//				8, 6, 5, 7, 1, 2, 3, 9, 4,
//				4, 2, 1, 3, 9, 5, 8, 6, 7,
//				3, 7, 9, 8, 6, 4, 5, 2, 1
//				};
//
//		int [] goodTestTable2 = {
//				7, 1, 9, 5, 2, 8, 3, 6, 4,
//				8, 2, 3, 7, 6, 4, 9, 5, 1,
//				6, 4, 5, 3, 1, 9, 7, 2, 8,
//				5, 6, 4, 1, 7, 2, 8, 3, 9,
//				3, 9, 1, 8, 5, 6, 2, 4, 7,
//				2, 8, 7, 9, 4, 3, 6, 1, 5,
//				9, 7, 2, 4, 3, 1, 5, 8, 6,
//				4, 5, 6, 2, 8, 7, 1, 9, 3,
//				1, 3, 8, 6, 9, 5, 4, 7, 2
//		};
//
//		int [] badTestTable = {
//				5, 4, 3, 9, 8, 1, 6, 7, 2,
//				9, 1, 7, 2, 5, 6, 4, 3, 8,
//				6, 8, 2, 4, 7, 3, 1, 5, 9,
//				2, 9, 4, 5, 3, 8, 7, 1, 6,
//				7, 3, 6, 1, 4, 9, 2, 8, 5,
//				1, 5, 8, 6, 2, 7, 9, 4, 3,
//				8, 6, 5, 7, 1, 2, 3, 9, 4,
//				4, 2, 1, 3, 9, 5, 8, 6, 7, //zamienione 7-1
//				3, 7, 9, 8, 6, 4, 5, 2, 1 //zamienione 1-7
//				};
//
//
//
//		SudokuTable s = new SudokuTable(badTestTable);
//
//		if (s.checkTable())
//			System.out.println("Wszystko ok.");
//		else
//			System.out.println("Coś jest nie tak...");
//
//		s.show();
//	}
}
