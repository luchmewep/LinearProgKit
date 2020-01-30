package FinalProject2019;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class User {
	private int id;
	private String username, password, fname, lname;

	public User(ArrayList<Object> a) {
		setId(a.get(0));
		setUsername(a.get(1));
		setPassword(a.get(2));
		setFname(a.get(3));
		setLname(a.get(4));
	}

	public User(String username, String password, String fname, String lname) {
		if(username.trim().isEmpty() || password.trim().isEmpty() || fname.trim().isEmpty() || lname.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "CAUSE: One or more fields is/are empty. Please try again.", "Adding Failed", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("insert into tbl_users values (null, ?,?,?,?)", new Object[] {username, password, fname, lname})) {
			id = (int) FrameLogin.db.getOneRow("select * from tbl_users where username=? and password=? and fname=? and lname=?", new Object[] {username, password, fname, lname}).get(0);
			this.username = username;
			this.password = password;
			this.fname = fname;
			this.lname = lname;
			JOptionPane.showMessageDialog(null, "New user added successfully!", "Adding Success", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "CAUSE: Possible database error. Please try again.", "Adding Failed", JOptionPane.ERROR_MESSAGE);
		}
	}

	//SETTERS
	public void setId(Object id) { this.id = (int) id; }
	public void setUsername(Object username) { this.username = (String) username; }
	public void setPassword(Object password) { this.password = (String) password; }
	public void setFname(Object fname) { this.fname = (String) fname; }
	public void setLname(Object lname) { this.lname = (String) lname; }
	//SPECIAL SETTERS
	public void setUsername(String username) {
		if(this.username.equals(username)) {
			JOptionPane.showMessageDialog(null, "CAUSE: New username is the same with the old one.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_users set username = ? where user_id = ?", new Object[] {username, getId()})) {
			JOptionPane.showMessageDialog(null, "Username updated successfully!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "CAUSE: Possible database error. Please try again.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setPassword(String password) {
		if(this.password.equals(password)) {
			JOptionPane.showMessageDialog(null, "CAUSE: New password is the same with the old one.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_users set password = ? where user_id = ?", new Object[] {password, getId()})) {
			JOptionPane.showMessageDialog(null, "Password updated successfully!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "CAUSE: Possible database error. Please try again.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setFname(String fname) {
		if(this.fname.equals(fname)) {
			JOptionPane.showMessageDialog(null, "CAUSE: New first name is the same with the old one.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_users set fname = ? where user_id = ?", new Object[] {fname, getId()})) {
			JOptionPane.showMessageDialog(null, "First name updated successfully!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "CAUSE: Possible database error. Please try again.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setLname(String lname) {
		if(this.lname.equals(lname)) {
			JOptionPane.showMessageDialog(null, "CAUSE: New last name is the same with the old one.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
		else if(FrameLogin.db.runQuery("update tbl_users set lname = ? where user_id = ?", new Object[] {lname, getId()})) {
			JOptionPane.showMessageDialog(null, "Last name updated successfully!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "CAUSE: Possible database error. Please try again.", "Update Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	//GETTERS
	public int getId() { return id; }
	public String getUsername() {
		return (String) FrameLogin.db.getOneRow("select username from tbl_users where user_id = "+getId()).get(0);
	}
	public String getPassword() {
		return (String) FrameLogin.db.getOneRow("select password from tbl_users where user_id = "+getId()).get(0);
	}
	public String getFname() {
		return (String) FrameLogin.db.getOneRow("select fname from tbl_users where user_id = "+getId()).get(0);
	}
	public String getLname() {
		return (String) FrameLogin.db.getOneRow("select lname from tbl_users where user_id = "+getId()).get(0);
	}
	public String[] getDetails() { return new String[] {getId()+"", getUsername(), getPassword(), getFname(), getLname()};}
}
