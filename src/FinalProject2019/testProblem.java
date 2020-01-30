package FinalProject2019;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.script.ScriptException;
import javax.swing.JFrame;

import luchavez.SQLiteKit;

public class testProblem {
	private static SQLiteKit db = FrameLogin.db;
	public static void main(String[] args){
		FrameLogin.db.connectionTest();
		String[] equation = new String[] {"10 x", "5 y", "6 z"};
		String[][] constraints = new String[][] {
			{"4", "2", "2", "<=", "30"},
			{"1", "1", "2", "<=", "40"},	
			{"1", "3", "2", "<=", "50"}
		};
		@SuppressWarnings("unchecked")
		Problem problem = new Problem(db.getOneRow("select * from tbl_problems where id = 3;"));
		DialogAddProblem add = new DialogAddProblem(new JFrame(), new User(FrameLogin.db.getOneRow("select * from tbl_users where user_id = 1")), problem.getId());
		add.setVisible(true);
		if(add.getProblem() != null){
			System.out.println("Amazing! The chosen problem ID is "+add.getProblem().getId());
		}else{
			System.out.println("Problem is null...");
		}
//		Problem problem = new Problem(1, "Data Structures", "This is a test.", true, equation, constraints);
//		problem.displayInfo();
//		problem.setTitle("Gowns A & B");
//		problem.setDescription("Get the number of Gowns A and Gowns B to get maximum profit.");
//		problem.setMaximize(false);
//		problem.setEquation(equation);
//		problem.setConstraints(constraints);
//		problem.printOrigEquation();
//		problem.printOrigConstraints();
//		primal.printTempEquation();
//		primal.printTempConstraints();
//		PrimalSimplex primal = new PrimalSimplex(problem);
//		try {
//			primal.execute();
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		} catch (ScriptException e) {
//			e.printStackTrace();
//		}
	}
}
