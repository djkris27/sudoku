public class Temp {


	public void generateTablesMiniSudokuTest(int [] originTable, int [] halfTable, int number){
		if (number >= halfTable.length){ //jeśli number jest równe (lub awaryjnie większe) od wielkości tablicy, to wtedy można zacząć tworzyć obiekty SudokuTable(halfTable
			//i sprawdzać, czy dana SudokuTable jest prawidłowa (metoda checkTable());
			MiniSudokuTest temp = new MiniSudokuTest(halfTable);
			if (temp.checkTable()) {
				temp.show();
				System.out.println();
			}
		}
		else { //jeśli number < halfTable.length, należy dalej wykonywać rekurencję dalej...
			if (originTable[number] != 0)
				generateTablesMiniSudokuTest(originTable, halfTable, number+1);
			else {
				int [] tempTable = new int[halfTable.length];
				for (int i = 0; i < halfTable.length; i++){ //kopiowanie tablicy halfTable do tymczasowej tempTable
					tempTable[i] = halfTable[i];
				}
				for (int i = 1; i <=9 ; i++){ //wypełnianie odpowiedniego miejsca w tablicy możliwymi liczbami i rekurencja...
					tempTable[number] = i;
					generateTablesMiniSudokuTest(originTable, tempTable, number+1);
				}
			}
		}
	}



	public static void main(String [] args) {
		Temp t = new Temp();
		MiniSudokuTest ms = new MiniSudokuTest();
		ms.show();
		ms.put(2, 2, 2);

		t.generateTablesMiniSudokuTest(ms.returnTableMiniSudokuTest(), ms.returnTableMiniSudokuTest(), 0);

	}
}
