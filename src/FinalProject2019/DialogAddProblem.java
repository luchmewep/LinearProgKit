package FinalProject2019;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.Box;
import javax.swing.ListSelectionModel;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class DialogAddProblem extends JDialog {

	private JPanel centerPanel;
	private Problem problem = null;
	private JPanel leftPanel;
	private JTextField txtTitle;
	private JTextArea txtDescription;
	private JComboBox<String> cmbMaximize;
	private JComboBox<String> cmbInequalities;
	private JTextField txtID;
	private int width, height;
	private JPanel centerPanelDetails;
	private JScrollPane scrollDescription;
	private JPanel centerPanelInitial;
	private JSpinner spinNumVars;
	private JSpinner spinNumCons;
	private JScrollPane scrollTitle;
	private JScrollPane scrollID;
	private JPanel panelEquation;
	private JPanel panelConstraints;
	private JTable tblEquation;
	private JTable tblConstraints;
	private JScrollPane scrollEquation;
	private JScrollPane scrollConstraints;
	private DefaultTableModel newTableModel, oldModelEqn, oldModelCons;
	private DefaultTableColumnModel newColModel;
	private int numVars, numCons, conNumRows, eqnNumCols, conNumCols, numRows, numCols, index;
	private String temp2D[][], temp1d[], tempString;
	private JPanel northPanel;
	private JLabel lblWelcome;
	private final String[] comparators = {"<=", ">=", "="};
	private Component verticalStrut;

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		DialogAddProblem dialog = new DialogAddProblem(new JFrame(), new User(FrameLogin.db.getOneRow("select * from tbl_users where user_id = 1")));
		dialog.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("unchecked")
	public DialogAddProblem(JFrame frame, User u, int problemID){
		this(frame, u);
		this.problem = new Problem(FrameLogin.db.getOneRow("select * from tbl_problems where id = "+problemID));
		setForm();
	}
	
	//GETTER
	public Problem getProblem() {
		return problem;
	}

	//SETTER
	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public DialogAddProblem(JFrame frame, User u) {
		super(frame, true);
		try {
			IconFontSwing.register(FontAwesome.getIconFont());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		setMinimumSize(new Dimension(816, 416));
		setTitle("Add Problem");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);
		getContentPane().setLayout(new BorderLayout());

		//GET ROOT PANE WIDTH AND HEIGHT
		width = getContentPane().getSize().width;
		height = getContentPane().getSize().width;

		leftPanel = new JPanel();
		leftPanel.setBackground(new Color(176, 196, 222));
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 0));
		leftPanel.setPreferredSize(new Dimension(320, 800));
		getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));
//		System.out.println(leftPanel.getPreferredSize());

		centerPanel = new JPanel();
		centerPanel.setBackground(new Color(176, 196, 222));
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.setPreferredSize(new Dimension(width*3/5, height));
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		//GET LEFT PANEL WIDTH AND HEIGHT
		width = leftPanel.getPreferredSize().width;
		height = leftPanel.getPreferredSize().height;

		centerPanelDetails = new JPanel();
		centerPanelDetails.setBackground(new Color(32, 178, 170));
		centerPanelDetails.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Problem Details", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		centerPanelDetails.setPreferredSize(new Dimension(width*2/5, height));
		FlowLayout flowLayout = (FlowLayout) centerPanelDetails.getLayout();
		flowLayout.setVgap(10);
		leftPanel.add(centerPanelDetails, BorderLayout.CENTER);

		scrollID = new JScrollPane();
		scrollID.setViewportBorder(new TitledBorder(null, "ID", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		scrollID.setPreferredSize(new Dimension((width-30)/2, 50));
		scrollID.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollID.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		centerPanelDetails.add(scrollID);
		
		txtID = new JTextField();
		txtID.setName("ID");
		scrollID.setViewportView(txtID);
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setEnabled(false);

		//inequalities
		cmbInequalities = new JComboBox<String>();
		cmbInequalities.setModel(new DefaultComboBoxModel<>(comparators));

		cmbMaximize = new JComboBox<String>();
		cmbMaximize.setPreferredSize(new Dimension((width-30)/2, 50));
		cmbMaximize.setName("Type");
		cmbMaximize.setMaximumRowCount(2);
		centerPanelDetails.add(cmbMaximize);
		cmbMaximize.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Type", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		((JLabel)cmbMaximize.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		cmbMaximize.setModel(new DefaultComboBoxModel<>(new String[]{"Maximize", "Minimize"}));

		scrollTitle = new JScrollPane();
		scrollTitle.setPreferredSize(new Dimension(width-20, 50));
		scrollTitle.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollTitle.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollTitle.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Title", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		centerPanelDetails.add(scrollTitle);

		txtTitle = new JTextField();
		txtTitle.setName("Title");
		scrollTitle.setViewportView(txtTitle);
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);

		scrollDescription = new JScrollPane();
		scrollDescription.setPreferredSize(new Dimension(width-20, 80));
		scrollDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDescription.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Description", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		centerPanelDetails.add(scrollDescription);

		txtDescription = new JTextArea();
		txtDescription.setName("Description");
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		scrollDescription.setViewportView(txtDescription);

		spinNumVars = new JSpinner();
		spinNumVars.setPreferredSize(new Dimension((width-30)/2, 40));
		spinNumVars.setName("Variables");
		spinNumVars.setModel(new SpinnerNumberModel(3, 1, 100, 1));
		spinNumVars.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Variables", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		centerPanelDetails.add(spinNumVars);

		spinNumCons = new JSpinner();
		spinNumCons.setPreferredSize(new Dimension((width-30)/2, 40));
		spinNumCons.setName("Constraints");
		spinNumCons.setModel(new SpinnerNumberModel(3, 1, 100, 1));
		spinNumCons.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Constraints", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		centerPanelDetails.add(spinNumCons);

		centerPanelInitial = new JPanel();
		centerPanelInitial.setBackground(new Color(176, 196, 222));
		centerPanel.add(centerPanelInitial, BorderLayout.CENTER);
		centerPanelInitial.setLayout(new BoxLayout(centerPanelInitial, BoxLayout.Y_AXIS));

		panelEquation = new JPanel();
		panelEquation.setBackground(new Color(60, 179, 113));
		panelEquation.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Objective Function", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		centerPanelInitial.add(panelEquation);
		panelEquation.setLayout(new BorderLayout(0, 0));

		scrollEquation = new JScrollPane();
		panelEquation.add(scrollEquation);

		tblEquation = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0) return false;
				return true;
			}
			@Override
			public Class<?> getColumnClass(int column) {
				if(column == 0) return String.class;
				return Integer.class;
			}
		};
		tblEquation.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblEquation.setCellSelectionEnabled(true);
		tblEquation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollEquation.setViewportView(tblEquation);
		
		verticalStrut = Box.createVerticalStrut(20);
		centerPanelInitial.add(verticalStrut);

		panelConstraints = new JPanel();
		panelConstraints.setBackground(new Color(60, 179, 113));
		panelConstraints.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Constraints", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		centerPanelInitial.add(panelConstraints);
		panelConstraints.setLayout(new BorderLayout(0, 0));

		scrollConstraints = new JScrollPane();
		panelConstraints.add(scrollConstraints);

		tblConstraints = new JTable();
		tblConstraints.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblConstraints.setCellSelectionEnabled(true);
		tblConstraints.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollConstraints.setViewportView(tblConstraints);
		
		northPanel = new JPanel();
		northPanel.setBackground(new Color(32, 178, 170));
		getContentPane().add(northPanel, BorderLayout.NORTH);
		
		lblWelcome = new JLabel("Create New Problem");
		lblWelcome.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		northPanel.add(lblWelcome);

		//SOUTH PANEL
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(32, 178, 170));
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		JButton btnChoose = new JButton("Choose");
		getRootPane().setDefaultButton(btnChoose);
		btnChoose.addActionListener(e->{
			if(btnChoose.getText().equals("Save")){
				
			}
			else if(btnChoose.getText().equals("Choose")){
				dispose();
			}
		});
		southPanel.add(btnChoose);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(e->{
			setProblem(null);
			dispose();
		});
		southPanel.add(cancelButton);

		//Listeners
		spinNumVars.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				spinnerChanged();
			}
		});
		spinNumCons.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				spinnerChanged();
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeAllComponents();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setProblem(null);
			}
		});
		//Final Touches
		repaint();
		revalidate();
	}
	
	private void setForm(){
		if(problem != null){
			this.setTitle("Open Problem");
			txtID.setText(problem.getId()+"");
			txtTitle.setText(problem.getTitle());
			txtDescription.setText(problem.getDescription());
			cmbMaximize.setSelectedItem(problem.isMaximize()?"Maximize":"Minimize");
			spinNumVars.setValue(problem.getNumVariables());
			spinNumCons.setValue(problem.getNumConstraints());
			spinnerChanged();
			//Copy Input from Previous Model to New Model --- equation
			newTableModel = (DefaultTableModel) tblEquation.getModel();
			temp1d = problem.getEquationAlias();
			for (int j = 0; j < temp1d.length; j++) {
				newTableModel.setValueAt(temp1d[j], 0, j);
			}
			temp1d = problem.getEquationValues();
			for (int j = 0; j < temp1d.length; j++) {
				newTableModel.setValueAt(temp1d[j], 1, j);
			}
			//Copy Input from Previous Model to New Model --- constraints
			newTableModel = (DefaultTableModel) tblConstraints.getModel();
			newColModel = (DefaultTableColumnModel) tblConstraints.getColumnModel();
			temp2D = problem.getConstraints();
			for (int i = 0; i < temp2D.length; i++) {
				for (int j = 0; j < temp2D[0].length; j++) {
					newTableModel.setValueAt(temp2D[i][j], i, j);
				}
			}
			JComponent[] obj = new JComponent[] {txtTitle, txtDescription, cmbMaximize, spinNumVars, spinNumCons};
			for (JComponent component : obj) {
				component.setEnabled(false);
				component.setToolTipText("Click to edit.");
				component.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						component.setToolTipText("Press 'Enter' to save.");
						component.setEnabled(true);
					}
				});
				component.addKeyListener(new KeyAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == 10){
							switch (component.getName()) {
							case "Type":
								problem.setMaximize(((JComboBox<String>) component).getSelectedItem().toString().equals("Maximize"));
								break;
							case "Title":
								problem.setTitle(((JTextField) component).getText().trim());
								break;
							case "Description":
								problem.setDescription(((JTextArea) component).getText().trim());
								break;
							default:
								break;
							}
							component.setEnabled(false);
							component.setToolTipText("Click to edit.");
							repaint();
							revalidate();
						}

					}
				});
			}
		}
	}

	private void spinnerChanged(){
		//Get Spinner Numbers
		numVars = (int) spinNumVars.getValue();
		numCons = (int) spinNumCons.getValue();
		//Get Old Models
		oldModelEqn = (DefaultTableModel) tblEquation.getModel();
		oldModelCons = (DefaultTableModel) tblConstraints.getModel();
		//Get Col and Row Counts
		eqnNumCols = oldModelEqn.getColumnCount();
		conNumRows = oldModelCons.getRowCount();
		conNumCols = oldModelCons.getColumnCount();
		//replace old models with new models --- equation
		newTableModel = new DefaultTableModel(2, numVars);
		newTableModel.addColumn("Variable", new Object[]{"Alias", "Value"});
		newTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				index = arg0.getColumn();
				newTableModel = (DefaultTableModel) tblEquation.getModel();
				newColModel = (DefaultTableColumnModel) tblConstraints.getColumnModel();
				tempString = tblEquation.getModel().getValueAt(0, index)+"";
				if(!tempString.trim().isEmpty() && !tempString.equals("null")){
					tempString = newColModel.getColumn(index).getHeaderValue().toString().split(" ")[0]+" ("+tempString+")";
					newColModel.getColumn(index).setHeaderValue(tempString);
					repaint();
					revalidate();
				}
			}
		});
		index = newTableModel.getColumnCount()-1;
		tblEquation.setModel(newTableModel);
		tblEquation.getColumnModel().moveColumn(index, 0);
		//replace old models with new models --- constraints
		newTableModel = new DefaultTableModel(numCons, numVars){
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return Integer.class;
			}
		};
		newTableModel.addColumn("");
		newTableModel.addColumn("RHS");
		index = newTableModel.getColumnCount()-2;
		tblConstraints.setModel(newTableModel);
		tblConstraints.getColumnModel().getColumn(index).setCellEditor(new DefaultCellEditor(cmbInequalities));
		if(eqnNumCols != 0){
			//Copy Input from Previous Model to New Model --- equation
			newTableModel = (DefaultTableModel) tblEquation.getModel();
			numCols = eqnNumCols < newTableModel.getColumnCount() ? eqnNumCols : newTableModel.getColumnCount();
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < numCols-1; j++) {
					newTableModel.setValueAt(oldModelEqn.getValueAt(i, j), i, j);
				}
			}
			//Copy Input from Previous Model to New Model --- constraints
			newTableModel = (DefaultTableModel) tblConstraints.getModel();
			newColModel = (DefaultTableColumnModel) tblConstraints.getColumnModel();
			numCols = conNumCols < newTableModel.getColumnCount() ? conNumCols : newTableModel.getColumnCount();
			numRows = conNumRows < newTableModel.getRowCount() ? conNumRows : newTableModel.getRowCount();
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numCols-2; j++) {
					newTableModel.setValueAt(oldModelCons.getValueAt(i, j), i, j);
				}
			}
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < 2; j++) {
					newTableModel.setValueAt(oldModelCons.getValueAt(i, oldModelCons.getColumnCount()-2+j), i, newTableModel.getColumnCount()-2+j);
				}
			}
		}
	}
	
	private String[][] getDataFromTable(JTable tbl){
		numRows = tbl.getRowCount();
		numCols = tbl.getColumnCount();
		if(numRows > 0 && numCols > 0){
			temp2D = new String[numRows][numCols];
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numCols; j++) {
					temp2D[i][j] = (String) tbl.getValueAt(i, j);
				}
			}
			return temp2D;
		}
		return null;
	}

	private void resizeAllComponents(){
		//GET ROOT PANE SIZE
		width = getContentPane().getSize().width;
		height = getContentPane().getSize().width;

		leftPanel.setPreferredSize(new Dimension(width*2/5, height));
		centerPanel.setPreferredSize(new Dimension(width*3/5, height));
//		System.out.println("Program: "+this.getSize());
//		System.out.println("Left: "+leftPanel.getPreferredSize());
		
		width = centerPanelDetails.getSize().width;
		scrollID.setPreferredSize(new Dimension((width-30)/2, 50));
		cmbMaximize.setPreferredSize(new Dimension((width-30)/2, 50));
		scrollTitle.setPreferredSize(new Dimension(width-20, 50));
		scrollDescription.setPreferredSize(new Dimension(width-20, 80));
		spinNumVars.setPreferredSize(new Dimension((width-30)/2, 40));
		spinNumCons.setPreferredSize(new Dimension((width-30)/2, 40));
		//RIGHT SIDE
		width = centerPanelInitial.getSize().width;
		height = centerPanelInitial.getSize().height;
		scrollConstraints.setPreferredSize(new Dimension(centerPanelInitial.getSize().width, centerPanelInitial.getSize().height/2*5));
		//		scrollPane_2.setPreferredSize(new Dimension(centerPanelInitial.getSize().width, centerPanelInitial.getSize().height/3*5));
		//		System.out.println(scrollPane_1.getSize().height+" + "+scrollPane_2.getSize().height+" = "+(scrollPane_1.getSize().height+scrollPane_2.getSize().height)+" vs "+height);
		repaint();
		revalidate();
	}
}
