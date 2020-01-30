package FinalProject2019;

import java.util.ArrayList;
import java.util.Arrays;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class simplexProcedure {
	private ArrayList<ArrayList<String>> tempTableau;
	private ArrayList<ArrayList<ArrayList<String>>> tempTableaus;
	private int cols, rows, EVIndex, LVIndex;
	private double sumCol, temp, EV, LV, pivot;
	final static ScriptEngine js = new ScriptEngineManager().getEngineByName("js");
	public simplexProcedure(ArrayList<ArrayList<String>> tempTableau) throws NumberFormatException, ScriptException {
		setTempTableau(tempTableau);
	}
	public void setTempTableau(ArrayList<ArrayList<String>> tempTableau) throws NumberFormatException, ScriptException {
		this.tempTableau = tempTableau;
		rows = tempTableau.size();
		cols = tempTableau.get(0).size();
		do {
			setEnteringValueIndex();
			setLeavingValueIndex();
			setPivot();
			printTempTableau();
			setNextTableau();
		}while(EV > 0);
	}
	private void setEnteringValueIndex() throws NumberFormatException, ScriptException {
		for (int i = 1; i < cols-1; i++) { //for each column
			sumCol = 0;
			for (int j = 1; j < rows-1; j++) { //for each row
				sumCol += getData(j, i) * getData(j, 0);
			}
			temp = getData(0, i) - sumCol;
			tempTableau.get(rows-1).set(i, String.format("%.2f", temp));
			if(i == 1) {
				EV = temp;
				EVIndex = i;
			}
			if(i > 1 && temp > EV) {
				EV = temp;
				EVIndex = i;
			}
		}
	}
	private void setLeavingValueIndex() throws NumberFormatException, ScriptException {
		for (int i = 1; i < rows-1; i++) { //for each row
			temp = getData(i, cols-1);
			try {
				temp = temp / getData(i, EVIndex);
			} catch (Exception e) {
				temp = -1;
			}
			if(i == 1) {
				LV = temp; //set default LV
				LVIndex = i;
			}
			if(i > 1 && temp < LV) {
				LV = temp;
				LVIndex = i;
			}
		}
	}
	private void setPivot() throws NumberFormatException, ScriptException {
		pivot = getData(LVIndex, EVIndex);
	}
	private void setNextTableau() throws NumberFormatException, ScriptException {
		tempTableau.get(LVIndex).set(0, tempTableau.get(0).get(EVIndex));
		for (int i = 1; i < cols; i++) {
			tempTableau.get(LVIndex).set(i, String.format("(%.2f/%.2f)", getData(LVIndex, i), pivot));
		}
		for (int i = 1; i < rows-1; i++) {
			if(i != LVIndex) {
				temp = -1*getData(i, EVIndex);
				for (int j = 1; j < cols; j++) {
					tempTableau.get(i).set(j, String.format("(%.2f*%s+%.2f)", temp, tempTableau.get(LVIndex).get(j), getData(i, j)));
				}
			}
		}
	}
	public void printTempTableau() {
		System.out.println("New Temp Tableau: ");
		for (int i = 0; i < tempTableau.size(); i++) {
			System.out.println(Arrays.toString(tempTableau.get(i).toArray()));
		}
	}
	public Double parseDouble(String s) {
		return Double.parseDouble(s);
	}
	public double getData(int row, int col) throws NumberFormatException, ScriptException {
		return Double.parseDouble(js.eval(tempTableau.get(row).get(col).split(" ")[0]).toString());
	}
}
