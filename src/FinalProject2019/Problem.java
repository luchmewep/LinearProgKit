package FinalProject2019;

/**
 * Problem as OOP
 */

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import luchavez.SQLiteKit;

public class Problem {
	private boolean isMaximize;
	private int user_id, id, numVariables, numConstraints;
	private String title, description, errorMessage="", tempString;
	private String[] equation, temp1d;
	private String[][] constraints, temp2d;

	//CONSTRUCTOR FOR OLD PROBLEM
	public Problem(ArrayList<Object> row) {
		setAll(row);
	}

	//CONSTRUCTOR FOR NEW PROBLEM
	@SuppressWarnings("unchecked")
	public Problem(int user_id, String title, String description, boolean isMaximize, String[] equation, String[][] constraints) {
		if(FrameLogin.db.runQuery("insert into tbl_problems values (null, ?, ?, ?, ?, ?, ?)", new Object[] {user_id, title, description, isMaximize?1:0, convert1DArrayToString(equation), convert2DArrayToString(constraints)})) {
			setAll(FrameLogin.db.getOneRow("select * from tbl_problems where user_id = ? and title = ? and description = ? and isMaximize = ? and equation = ? and constraints = ?", new Object[] {user_id, title, description, isMaximize?1:0, convert1DArrayToString(equation), convert2DArrayToString(constraints)}));
			JOptionPane.showMessageDialog(null, "Successfully added new problem.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Failed to add new problem.", "FAILED", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	//SET ALL
	private void setAll(ArrayList<Object> row){
		if(row != null){
			id = Integer.valueOf(row.get(0).toString());
			user_id = Integer.valueOf(row.get(1).toString());
			title = (String) row.get(2);
			description = (String) row.get(3);
			isMaximize = Integer.valueOf(row.get(4).toString()) == 1;
			equation = convertStringTo1DArray((String) row.get(5));
			constraints = convertStringTo2DArray((String) row.get(6));
		}
	}

	//SETTERS
	public void setErrorMessage(String errorMessage) { this.errorMessage += (this.errorMessage.isEmpty()?"":"\n")+errorMessage; }
	public void setTitle(String title) {
		if(this.title.equals(title)) {
			JOptionPane.showMessageDialog(null, "New title is still the same with old one.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_problems set title = ? where id = ?", new Object[] {title, getId()})) {
			this.title = title;
			JOptionPane.showMessageDialog(null, "Successfully updated title!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Failed to update problem title", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setDescription(String description) {
		if(this.description.equals(description)) {
			JOptionPane.showMessageDialog(null, "New description is still the same with old one.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_problems set description = ? where id = ?", new Object[] {description, getId()})) {
			this.description = description;
			JOptionPane.showMessageDialog(null, "Successfully updated description!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Failed to update problem description", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setMaximize(boolean isMaximize) {
		if(this.isMaximize == isMaximize) {
			JOptionPane.showMessageDialog(null, "New isMaximize is still the same with old one.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_problems set isMaximize = ? where id = ?", new Object[] {isMaximize?1:0, getId()})) {
			this.isMaximize = isMaximize;
			JOptionPane.showMessageDialog(null, "Successfully updated isMaximize!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Failed to update problem isMaximize", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setEquation(String[] equation) {
		if(Arrays.deepEquals(this.equation, equation)) {
			JOptionPane.showMessageDialog(null, "New equation is still the same with old one.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_problems set equation = ? where id = ?", new Object[] {convert1DArrayToString(equation), getId()})) {
			this.equation = equation;
			JOptionPane.showMessageDialog(null, "Successfully updated equation!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Failed to update problem equation", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setConstraints(String[][] constraints) {
		if(Arrays.deepEquals(this.constraints, constraints)) {
			JOptionPane.showMessageDialog(null, "New constraints is still the same with old one.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_problems set constraints = ? where id = ?", new Object[] {convert2DArrayToString(constraints), getId()})) {
			this.constraints = constraints;
			JOptionPane.showMessageDialog(null, "Successfully updated constraints!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Failed to update problem constraints", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	//GETTERS
	public int getId() {
		return id;
	}
	public int getUser_id() {
		return Integer.valueOf(FrameLogin.db.getOneRow("select user_id from tbl_problems where id = "+getId()).get(0).toString());
	}
	public String getTitle() {
		return (String) FrameLogin.db.getOneRow("select title from tbl_problems where id = "+getId()).get(0);
	}
	public String getDescription() {
		return (String) FrameLogin.db.getOneRow("select description from tbl_problems where id = "+getId()).get(0);
	}
	public String getErrorMessage() {
		return !errorMessage.isEmpty() ? errorMessage : "No problems detected.";
	}
	public boolean isMaximize() {
		return Integer.valueOf(FrameLogin.db.getOneRow("select isMaximize from tbl_problems where id = "+getId()).get(0).toString()) == 1;
	}
	public boolean isSolvable() {
		if(numVariables==0) setErrorMessage("ERROR: Equation provided is blank.");
		if(numConstraints==0) setErrorMessage("ERROR: There are no constraints provided.");
		return errorMessage.isEmpty() ? true : false;
	}
	public String[] getEquation() {
		return convertStringTo1DArray((String) FrameLogin.db.getOneRow("select equation from tbl_problems where id = "+getId()).get(0));
	}
	public String[] getEquationAlias(){
		equation = getEquation();
		for (int i = 0; i < equation.length; i++) {
			equation[i] = equation[i].substring(equation[i].indexOf(" ")+1);
		}
		return equation;
	}
	public String[] getEquationValues(){
		equation = getEquation();
		for (int i = 0; i < equation.length; i++) {
			equation[i] = equation[i].split(" ")[0];
		}
		return equation;
	}
	public String[][] getConstraints() {
		return convertStringTo2DArray((String) FrameLogin.db.getOneRow("select constraints from tbl_problems where id = "+getId()).get(0));
	}
	public int getNumVariables() {
		equation = getEquation();
		return equation!=null ? equation.length : 0;  
	}
	public int getNumConstraints() {
		constraints = getConstraints();
		return constraints!=null ? constraints.length : 0;
	}

	//DISPLAY
	public void printOrigEquation() {
		equation = getEquation();
		System.out.println(equation == null ? "ERROR: Equation is null." : Arrays.toString(equation));
	}

	public void printOrigConstraints() {
		constraints = getConstraints();
		if(numConstraints > 0) {
			for (String[] integers : constraints) {
				System.out.println(Arrays.toString(integers));
			}
		}else {
			System.out.println("ERROR: Constraints is null.");
		}
	}

	public void displayInfo(){
		String message = "Title: "+getTitle()
		+ "\nDescription: "+getDescription()	
		+ "\nisMaximize: "+isMaximize()
		+ "\nequation: "+convert1DArrayToString(getEquation())
		+ "\nconstraints: "+convert2DArrayToString(getConstraints());
		JOptionPane.showMessageDialog(null, message, "Problem Info", JOptionPane.PLAIN_MESSAGE);
	}

	//CONVERTERS
	public String[] convertStringTo1DArray(String s) {
		if(s == null || s.isEmpty()) return null;
		return s.split(",");
	}
	public String convert1DArrayToString(String[] s) {
		if(s == null || s.length == 0) return "";
		tempString = "";
		for (int i = 0; i < s.length; i++) {
			tempString+=s[i];
			if(i != s.length-1) tempString+="," ;
		}
		return tempString;
	}
	public String[][] convertStringTo2DArray(String s){
		if(s == null || s.isEmpty()) return null;
		temp1d = s.split("/");
		temp2d = new String[temp1d.length][];
		for (int i = 0; i < temp1d.length; i++) {
			temp2d[i] = temp1d[i].split(",");
		}
		return temp2d;
	}
	public String convert2DArrayToString(String[][] s){
		if(s == null || s.length == 0 || s[0].length == 0) return "";
		tempString = "";
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				tempString+=s[i][j];
				if(j != s[i].length-1) tempString+=",";
			}
			if(i != s.length-1) tempString+="/";
		}
		return tempString;
	}
}
