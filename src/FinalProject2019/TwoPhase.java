package FinalProject2019;

import java.util.ArrayList;
import java.util.Arrays;

import javax.script.ScriptException;

public class TwoPhase {
	private Problem p;
	private int indexSymbol, ctrSlack;
	private ArrayList<String> tempEquation = null, tempFooter = null;
	private ArrayList<ArrayList<String>> tempConstraints = null;
	private ArrayList<ArrayList<String>> tempTableau = null;
	public TwoPhase(Problem problem) {
		setProblem(problem);
		setTempEquation();
		setTempConstraints();
	}

	//SETTERS
	public void setProblem(Problem problem) {
		p = problem;
	}
	public void setTempEquation() {
		if(p.getNumVariables()>0) {
			tempEquation = new ArrayList<>(Arrays.asList(p.getEquation()));
			tempEquation.add(0, "");
			tempEquation.add("RHS");
		}else {
			tempEquation = null;
		}
	}
	public void setTempConstraints() {
		if(p.getNumConstraints()>0) {
			tempConstraints = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < p.getNumConstraints(); i++) {
				tempConstraints.add(new ArrayList<String>(Arrays.asList(p.getConstraints()[i])));
				tempConstraints.get(i).add(0, "");
			}
		}else {
			tempConstraints = null;
		}
	}

	//GETTERS
	public Problem getProblem() {
		return p;
	}
	public boolean isNormalConstraints() {
		for (int i = 0; i < p.getNumConstraints(); i++) {
			if(p.getConstraints()[i][p.getNumVariables()].equals(">=")) {
				return false;
			}
		}
		return true;
	}
	public ArrayList<ArrayList<String>> getTempConstraints() { return tempConstraints; }
	public ArrayList<String> getTempEquation() { return tempEquation; }

	//DISPLAY
	public void printTempEquation() {
		System.out.println("Temp Equation: ");
		System.out.println(tempEquation == null ? "ERROR: Temp Equation is null." : Arrays.toString(tempEquation.toArray()));
	}
	public void printTempConstraints() {
		System.out.println("Temp Constraints: ");
		for (int i = 0; i < p.getNumConstraints(); i++) {
			System.out.println(Arrays.toString(tempConstraints.get(i).toArray()));
		}
		if(p.getNumConstraints() == 0) System.out.println("ERROR: Temp Constraints is null.");
	}
	public void printTempTableau() {
		System.out.println("Temp Tableau: ");
		for (int i = 0; i < tempTableau.size(); i++) {
			System.out.println(Arrays.toString(tempTableau.get(i).toArray()));
		}
		if(p.getNumConstraints() == 0) System.out.println("ERROR: Temp Constraints is null.");
	}

	//DEEP ALGORITHMS
	public void execute() throws NumberFormatException, ScriptException {
		if(p.isSolvable() && isNormalConstraints()) {
			convertConstraintsToEqualities();
			setTempFooter();
			setInitialTableau();
			new simplex(tempTableau);
		}else {
			System.out.println(p.getErrorMessage());
		}
	}

	private void convertConstraintsToEqualities() {
		ctrSlack = 0;
		indexSymbol = p.getNumVariables() + 1;
		for (int i = 0; i < p.getNumConstraints(); i++) {
			for (int j = 0; j < p.getNumConstraints(); j++) {
				tempConstraints.get(j).add(indexSymbol, i==j ? "1" : "0");
			}
			tempEquation.add(indexSymbol, "0 s"+(i+1));
			tempConstraints.get(i).set(0, tempEquation.get(indexSymbol));
			ctrSlack++;
			indexSymbol = p.getNumVariables() + 1 + ctrSlack;
			tempConstraints.get(i).set(indexSymbol, "=");
		}
		for (int i = 0; i < p.getNumConstraints(); i++) {
			tempConstraints.get(i).remove(indexSymbol);
		}
	}
	private void setTempFooter() {
		tempFooter = new ArrayList<>();
		for (int i = 0; i < tempEquation.size(); i++) {
			tempFooter.add("");
		}
	}
	private void setInitialTableau() {
		tempTableau = new ArrayList<>();
		tempTableau.add(tempEquation);
		tempTableau.addAll(tempConstraints);
		tempTableau.add(tempFooter);
	}
}
