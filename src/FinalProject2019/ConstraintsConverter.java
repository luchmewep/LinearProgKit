package FinalProject2019;

import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintsConverter {
	private static boolean maximize, normal;
	private static String method;
	private static int hold, numVariables, indexSymbol, ctrSlack, ctrArtificial;
	private static Integer[] equation;
	private static Object[][] constraints;
	private static ArrayList<Object> tempEquation;
	private static ArrayList<Object> holdConstraint;
	private static ArrayList<ArrayList<Object>> tempConstraints;
	public static void main(String[] args) {
		maximize = true;
		normal = true;
		method = "dual simplex";
		equation = new Integer[] {3, 4, 2};
		constraints = new Object[][] {
			{4, 2, 2, ">=", 20},
			{1, 1, 2, ">=", 12}, 
			{1, 3, 2, ">=", 18}
		};
		getNumVariables();
		copyEquationToArrayList();
		copyConstraintsToArrayList();
		System.out.println("BEFORE Conversion of Inequalities to Equalities:");
		printTempConstraints();
		convertConstraintsToEqualities();
		System.out.println("AFTER Conversion of Inequalities to Equalities:");
		printTempConstraints();
		System.out.println("\nOTHER DETAILS:");
		System.out.println("Optimization Type: "+(maximize ? "MAXIMIZATION" : "MINIMIZATION"));
		System.out.println("Constraint Type: "+(normal ? "NORMAL" : "ABNORMAL"));
		System.out.println("\nInitial Table Setup for "+method.toUpperCase());
		createTableau();
	}
	public static void getNumVariables() {
		numVariables = equation.length;
	}
	public static void copyEquationToArrayList() {
		tempEquation = new ArrayList<>(Arrays.asList(equation));
	}
	public static void copyConstraintsToArrayList() {
		tempConstraints = new ArrayList<ArrayList<Object>>();
		for (int i = 0; i < constraints.length; i++) {
			tempConstraints.add(new ArrayList<Object>(Arrays.asList(constraints[i])));
		}
	}
	public static void convertConstraintsToEqualities() {
		indexSymbol = numVariables;
		ctrSlack = 0;
		ctrArtificial = 0;
		for (int i = 0; i < tempConstraints.size(); i++) {
			System.out.println("\nPROCESSING...("+ (i+1) + " of "+tempConstraints.size() + ")");
			holdConstraint = tempConstraints.get(i);	
			switch (holdConstraint.get(indexSymbol).toString()) {
			case "<=":
			case "<":
				if(!maximize) normal = false;
				for (int j = 0; j < tempConstraints.size(); j++) {
					tempConstraints.get(j).add(indexSymbol-ctrArtificial, i==j ? 1 : 0);
				}
				ctrSlack++;
				indexSymbol++;
				break;
			case ">=":
			case ">":
				if(maximize) normal = false;
				for (int j = 0; j < tempConstraints.size(); j++) {
					tempConstraints.get(j).add(indexSymbol-ctrArtificial, i==j ? -1 : 0);
					tempConstraints.get(j).add(tempConstraints.get(j).size()-2, i==j ? 1 : 0);
				}
				ctrSlack++;
				ctrArtificial++;
				indexSymbol+=2;
				break;
			case "=":
				
				break;
			default:
				break;
			}
			tempConstraints.get(i).set(indexSymbol, "=");
			printTempConstraints();
			System.out.println("Slack: "+ctrSlack+" Artificial: "+ctrArtificial+"\n");
		}

		for (int i = 0; i < tempConstraints.size(); i++) { //Remove All Equality and Inequality Signs
			tempConstraints.get(i).remove(indexSymbol);
		}
	}
	public static void createTableau() { 
		for (int i = 0; i < ctrSlack; i++) {
			tempEquation.add(0);
		}
		for (int i = 0; i < ctrArtificial; i++) {
			tempEquation.add(-1);
		}
		tempEquation.add("RHS");
		switch(method) {
		case "simplex":
			break;
		case "dual simplex":
			tempEquation.set(tempEquation.size()-1, "");
			break;
		case "big m":
			for (int i = numVariables+ctrSlack; i < numVariables+ctrSlack+ctrArtificial; i++) {
				tempEquation.set(i, "-M");
			}
			break;
		case "two phase":
			for (int i = 0; i < numVariables+ctrSlack; i++) {
				tempEquation.set(i, "0");
			}
			break;
		default:
			break;
		}
		
		printTempEquation();
		printTempConstraints();
		
		if(method.equals("dual simplex")) {
			System.out.println("\nTransformed:");
			matrixTranspose();
			printTempConstraints();
		}
	}
	
	public static void matrixTranspose() {
		tempConstraints.add(0, tempEquation);
		
	}

	//CODES FOR DISPLAY ONLY (No Important Algo Here)
	public static void printOrigEquation() {
		System.out.println(Arrays.toString(equation));
	}
	public static void printTempEquation() {
		System.out.println(Arrays.toString(tempEquation.toArray()));
	}
	public static void printOrigConstraints() {
		for (Object[] integers : constraints) {
			System.out.println(Arrays.toString(integers));
		}
	}
	public static void printTempConstraints() {
		for (int i = 0; i < tempConstraints.size(); i++) {
			System.out.println(Arrays.toString(tempConstraints.get(i).toArray()));
		}
	}
}
