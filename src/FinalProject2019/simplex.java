package FinalProject2019;

/**
 * Simplex Procedre for All
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JPanel;

public class simplex {
	private ArrayList<ArrayList<String>> tempTableau;
	private ArrayList<String[][]> tempTableaus;
	private String[][] temp2d;
	private int cols, rows, EVIndex, LVIndex;
	private double sumCol, temp, EV, LV, pivot;
	private String hold;
	final static ScriptEngine js = new ScriptEngineManager().getEngineByName("javascript");
	public simplex(ArrayList<ArrayList<String>> tempTableau) throws NumberFormatException, ScriptException {
		setTempTableau(tempTableau);
	}
	public void setTempTableau(ArrayList<ArrayList<String>> tempTableau) throws NumberFormatException, ScriptException {
		this.tempTableau = tempTableau;
		tempTableaus = new ArrayList<String[][]>();
		rows = tempTableau.size();
		cols = tempTableau.get(0).size();
		do {
			setEnteringValueIndex();
			setLeavingValueIndex();
			computeForZ();
			save2dToArrayList();
//			printTempTableau();
//			tempTableaus.add((ArrayList<ArrayList<String>>) tempTableau.stream().collect(Collectors.toList()));
			setNextTableau();
		}while(EV > 0);
		printAllTableaus();
	}
	private void setEnteringValueIndex() throws NumberFormatException, ScriptException {
		for (int i = 1; i < cols-1; i++) { //for each column
			sumCol = 0;
			for (int j = 1; j < rows-1; j++) { //for each row
				sumCol += eval(j, i) * eval(j, 0);
			}
			temp = eval(0, i) - sumCol;
			tempTableau.get(rows-1).set(i, String.format("%.2f", temp));
			if(i == 1) {
				EV = temp;
				EVIndex = i;
			}
			else if(temp > EV) {
				EV = temp;
				EVIndex = i;
			}
		}
	}
	private void setLeavingValueIndex() throws NumberFormatException, ScriptException {
		for (int i = 1; i < rows-1; i++) { //for each row
			temp = eval(i, cols-1);
			try {
				temp = temp / eval(i, EVIndex);
			} catch (Exception e) {
				temp = -1;
			}
			if(i == 1) {
				LV = temp; //set default LV
				LVIndex = i;
			}
			else if(temp < LV) {
				LV = temp;
				LVIndex = i;
			}
		}
	}
	private void computeForZ() throws NumberFormatException, ScriptException {
		sumCol = 0;
		for (int i = 1; i < rows-1; i++) { //for each row
			sumCol += eval(i, 0) * eval(i, cols-1);
		}
		tempTableau.get(rows-1).set(cols-1, sumCol+"");
	}
	private void setNextTableau() throws NumberFormatException, ScriptException {
		tempTableau.get(LVIndex).set(0, tempTableau.get(0).get(EVIndex));
		hold = getData(LVIndex, EVIndex);
		for (int i = 1; i < cols; i++) {
			tempTableau.get(LVIndex).set(i, String.format("(%s/%s)", getData(LVIndex, i), hold));
		}
		for (int i = 1; i < rows-1; i++) {
			if(i != LVIndex) {
				hold = String.format("-1*%s", getData(i, EVIndex));
				for (int j = 1; j < cols; j++) {
					tempTableau.get(i).set(j, String.format("(%s*%s+%s)", hold, getData(LVIndex, j), getData(i, j)));
				}
			}
		}
	}
	private void printAllTableaus() throws ScriptException {
		
		for (String[][] a : tempTableaus) {
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					System.out.print(a[i][j]+"\t");
				}
				System.out.println();
			}
		}
	}
	private void save2dToArrayList() throws NumberFormatException, ScriptException {
		temp2d = new String[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if(i == 0 || j == 0) {
					temp2d[i][j] = tempTableau.get(i).get(j);
				}else {
					temp2d[i][j] = String.format("%.2f", eval(i, j));
				}
			}
		}
		tempTableaus.add(temp2d);
	}
	public String getData(int row, int col) throws NumberFormatException, ScriptException {
		return tempTableau.get(row).get(col).split(" ")[0];
	}
	public double eval(int row, int col) throws NumberFormatException, ScriptException {
		return (double) Double.parseDouble(js.eval(getData(row, col)).toString());
	}
}
