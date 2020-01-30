package FinalProject2019;

import java.util.ArrayList;
import java.util.Arrays;

import javax.script.ScriptException;

public class primalSimplexMethod {
	private boolean isMaximize, isNormalConstraints, isSolvable;
	private int numVariables, numConstraints, indexSymbol, ctrSlack;
	private String errorMessage = "", successMessage = "";
	private String[] equation;
	private String[][] constraints;
	private ArrayList<String> tempEquation, tempFooter;
	private ArrayList<ArrayList<String>> tempConstraints, tempTableau;
	private simplexProcedure simplexProcedure;

	public static void main(String[] args) throws NumberFormatException, ScriptException {
		String[] equation = new String[] {"3 x", "4 y", "2 z"};
		String[][] constraints = new String[][] {
			{"4", "2", "2", "<=", "20"},
			{"1", "1", "2", "<=", "12"}, 
			{"1", "3", "2", "<=", "18"}
		};
		primalSimplexMethod simplex = new primalSimplexMethod(true, equation, constraints);
		simplex.printTempEquation();
		simplex.execute();
		simplex.printOrigEquation();
		simplex.printTempEquation();
		simplex.printOrigConstraints();
		simplex.printTempConstraints();
		simplex.printErrorMessage();
		simplex.printSuccessMessage();
	}
	
	//Parameterized Constructor
	public primalSimplexMethod(boolean isMaximize, String[] equation, String[][] constraints) {
		setMaximize(isMaximize);
		setEquation(equation);
		setConstraints(constraints);
		checkSolvable();
	}

	//Setters
	public void setErrorMessage(String errorMessage) {
		this.errorMessage += (this.errorMessage.isEmpty()?"":"\n")+errorMessage;
	}
	public void setMaximize(boolean isMaximize) {
		this.isMaximize = isMaximize;
	}
	public void setEquation(String[] equation) {
		numVariables = equation!=null ? equation.length : 0;
		this.equation = equation;
		setTempEquation();
	}
	public void setTempEquation() {
		if(numVariables>0) {
			tempEquation = new ArrayList<>(Arrays.asList(equation));
			tempEquation.add(0, "");
			tempEquation.add("RHS");
		}else {
			tempEquation = null;
		}
	}
	public void setConstraints(String[][] constraints) {
		numConstraints = constraints!=null ? constraints.length : 0;
		this.constraints = constraints;
		checkConstraints();
		setTempConstraints();
	}
	public void setTempConstraints() {
		if(numConstraints>0) {
			tempConstraints = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < numConstraints; i++) {
				tempConstraints.add(new ArrayList<String>(Arrays.asList(constraints[i])));
				tempConstraints.get(i).add(0, "");
			}
		}else {
			tempConstraints = null;
		}
	}
	//Getters
	public String getErrorMessage() {
		return !errorMessage.isEmpty() ? errorMessage : "No problems detected.";
	}
	public String getSuccessMessage() {
		return !successMessage.isEmpty() ? successMessage : "Not yet done.";
	}

	//Deep Algorithms
	public void checkConstraints() {
		for (int i = 0; i < numConstraints; i++) {
			if(constraints[i][indexSymbol].equals(">=")) {
				isNormalConstraints = false;
				return;
			}
		}
		isNormalConstraints = true;
	}
	public void checkSolvable() {
		if(numVariables==0) setErrorMessage("ERROR: Equation provided is blank.");
		if(numConstraints==0) setErrorMessage("ERROR: There are no constraints provided.");
		if(!isNormalConstraints) setErrorMessage("ERROR: Primal Simplex can't process constraints with '>=' operator.");
		if(!isMaximize) setErrorMessage("ERROR: Primal Simplex can only solve maximization problems.");
		isSolvable = errorMessage.isEmpty() ? true : false;
	}
	public void convertConstraintsToEqualities() {
		ctrSlack = 0;
		indexSymbol = numVariables + 1;
		for (int i = 0; i < numConstraints; i++) {
//			System.out.println("PROCESSING...("+ (i+1) + " of "+numConstraints+ ")");
			for (int j = 0; j < numConstraints; j++) {
				tempConstraints.get(j).add(indexSymbol, i==j ? "1" : "0");
			}
			tempEquation.add(indexSymbol, "0 s"+(i+1));
			tempConstraints.get(i).set(0, tempEquation.get(indexSymbol));
			ctrSlack++;
			indexSymbol = numVariables + 1 + ctrSlack;
			tempConstraints.get(i).set(indexSymbol, "=");
//			printTempEquation();
//			printTempConstraints();
//			System.out.println("Slack: "+ctrSlack+"\n");
		}
		for (int i = 0; i < numConstraints; i++) { //Remove All Equality and Inequality Signs
			tempConstraints.get(i).remove(indexSymbol);
		}
//		printTempEquation();
//		printTempConstraints();
	}
	public void convertEquationToHeader() {
		tempFooter = new ArrayList<>();
		for (int i = 0; i < tempEquation.size(); i++) {
			tempFooter.add("");
		}
	}
	
	public void setInitialTableau() {
		tempTableau = new ArrayList<>();
		tempTableau.add(tempEquation);
		tempTableau.addAll(tempConstraints);
		tempTableau.add(tempFooter);
//		printTempTableau();
	}
	public void execute() throws NumberFormatException, ScriptException {
		if(!isSolvable) {
			printErrorMessage();
			return;
		}
		convertConstraintsToEqualities();
		convertEquationToHeader();
		setInitialTableau();
		start();
	}
	private void start() throws NumberFormatException, ScriptException {
		simplexProcedure = new simplexProcedure(tempTableau);
//		setEnteringValueIndex();
//		setLeavingValueIndex();
//		setPivot();
	}

	//Display
	public void printOrigEquation() {
		System.out.println("Equation: ");
		System.out.println(equation == null ? "ERROR: Equation is null." : Arrays.toString(equation));
	}
	public void printTempEquation() {
		System.out.println("Temp Equation: ");
		System.out.println(tempEquation == null ? "ERROR: Temp Equation is null." : Arrays.toString(tempEquation.toArray()));
	}
	public void printOrigConstraints() {
		System.out.println("Constraints: ");
		if(numConstraints > 0) {
			for (String[] integers : constraints) {
				System.out.println(Arrays.toString(integers));
			}
		}else {
			System.out.println("ERROR: Constraints is null.");
		}
	}
	public void printTempConstraints() {
		System.out.println("Temp Constraints: ");
		for (int i = 0; i < numConstraints; i++) {
			System.out.println(Arrays.toString(tempConstraints.get(i).toArray()));
		}
		if(numConstraints == 0) System.out.println("ERROR: Temp Constraints is null.");
	}
	public void printTempTableau() {
		System.out.println("Temp Tableau: ");
		for (int i = 0; i < tempTableau.size(); i++) {
			System.out.println(Arrays.toString(tempTableau.get(i).toArray()));
		}
		if(numConstraints == 0) System.out.println("ERROR: Temp Constraints is null.");
	}
	public void printErrorMessage() {
		System.out.println(getErrorMessage());
	}
	public void printSuccessMessage() {
		System.out.println(getSuccessMessage());
	}
}
