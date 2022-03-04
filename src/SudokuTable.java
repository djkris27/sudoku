import java.util.*;

public class SudokuTable {
	private int [][] table = new int [9][9];

	//constructor create table filled "0"
	public SudokuTable(){
		for (int i = 0; i< table.length; i++)
			for (int j = 0; j<table[i].length; j++)
				table[i][j] = 0;
	}

	//constructor create table filled by 1-dimensional table longTable
	public SudokuTable(int [] longTable){
		putAll(longTable);
	}

//	//put only one value in one position described by row and column (numeration of row and column: 0-8)
//	public void put(int row, int column, int value) {
//		table[row][column] = value;
//	}

	//put 1-dimension longtable to 2-dimension table
	public void putAll(int [] longtable) {
		int count = 0;
		for (int i = 0; i < table.length; i++)
			for (int j = 0; j < table[0].length; j++)
				table[i][j] = longtable[count++];
	}

	//show Sudoku table
	public void show() {
		for (int i = 0; i< table.length; i++) {
			for (int j = 0; j<table[i].length; j++)
			System.out.print(" " + table[i][j]);
			System.out.println();
		}
	}

	//check, if element sum in row is correct (should be 45) - it is first phase of checking. (numeration of row and column: 0-8)
	private boolean goodRowSum(int row) {
		int counter = 0;
		for (int i = 0; i<table[row].length; i++)
			counter += table[row][i];
		return counter == 45;
	}

	//check, if element sum in column is correct (should be 45) - it is first phase of checking. (numeration of row and column: 0-8)
	private boolean goodColumnSum(int column) {
		int counter = 0;
		for (int i = 0; i<table.length; i++)
			counter += table[i][column];
		return counter == 45;
	}	

	//check, if row has good sum and no duplicates. (numeration of row and column: 0-8)
	private boolean checkRow(int row) {
		if (!goodRowSum(row))
			return false;
		for (int i = 0; i<table[row].length; i++) {
			for (int j = i+1; j<table[row].length; j++) {
				if (table[row][i] == table[row][j]) { //check if row have got duplicates
					return false;
				}
			}
		}
		return true;
	}

	//check, if column has good sum and no duplicates. (numeration of row and column: 0-8)
	private boolean checkColumn(int column) {
		if (!goodColumnSum(column))
			return false;
		for (int i = 0; i<table.length; i++) {
			for (int j = i+1; j<table.length; j++) {
				if (table[i][column] == table[j][column]) { //check if column have got duplicates
					return false;
				}
			}
		}
		return true;
	}


	private boolean checkAllRows() {
		for (int i = 0; i < 9; i++) {
			if (!checkRow(i))
				return false;
		}
		return true;
	}

	private boolean checkAllColumn() {
		for (int i = 0; i < 9; i++) {
			if (!checkColumn(i))
				return false;
		}
		return true;
	}

	//check small squares, if sum is correct and if contains duplicates
	private boolean checkSmallSquares() {
		for (int triRow = 0; triRow<=2; triRow++) {
			for (int triColumn = 0; triColumn <= 2; triColumn++) {
				HashSet<Integer> tempSet = new HashSet<Integer>();
				int sum = 0;
				for (int i = triRow * 3; i < (triRow * 3 + 3); i++) { //this and next line set iterator in appropriate small square
					for (int j = triColumn * 3; j < (triColumn * 3 + 3); j++) {
						tempSet.add(table[i][j]);
						sum += table[i][j];
					}
				}
				if (!(tempSet.size() == 9 && sum == 45))
					return false;
			}
		}
		return true;
	}

	//check all table: rows, columns and squares (sum and duplicates)
	public boolean checkTable(){
		if (checkAllRows() && checkAllColumn() && checkSmallSquares())
			return true;
		else
			return false;
	}

	//return 1-dimension table temp from 2-dimension table
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

	//return HashSet contains numbers, which we cant put in appropriate position (row, column, numeration of row and column: 0-8)
	public HashSet<Integer> notAllowedNumber(int row, int column){
		HashSet<Integer> tempHashSet = new HashSet<Integer>();
		for (int i = 0; i<table.length; i++) //check used numbers in row
			tempHashSet.add(table[row][i]);
		for (int i = 0; i<table[0].length; i++) //check used numbers in column
		tempHashSet.add(table[i][column]);
		for (int i = (row/3)*3; i < ((row/3)*3)+3; i++) //check used numbers in small square
			for (int j = (column/3)*3; j < ((column/3)*3)+3; j++)
				tempHashSet.add(table[i][j]);
		tempHashSet.remove(0);
		return tempHashSet;
	}

}
