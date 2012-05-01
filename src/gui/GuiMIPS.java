package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Utils;
import memorias.MemBuilder;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import mips.MIPS;
import mips.MIPSBuilder;
import net.miginfocom.swing.MigLayout;

public class GuiMIPS {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;
	private JTextField textField_27;
	private JTextField textField_28;
	private JTextField textField_29;
	private JTextField textField_30;
	private JTextField textField_31;
	private JButton nextClock;
	private JButton btnNewButton;
	private MIPS mips;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFileChooser fc = new JFileChooser();
					int retVal = fc.showOpenDialog(null);
					if(retVal==JFileChooser.APPROVE_OPTION){
						File f = fc.getSelectedFile();
						List<String> instrucoes = Utils.lerInstrucoes(f); 
						MemoriaInstrucao memInstruction = MemBuilder.buildMemInstruction(instrucoes);
						MemoriaDados memData = new MemoriaDados();
						MIPS mips = MIPSBuilder.build(memInstruction, memData);
						GuiMIPS window = new GuiMIPS(mips);
						window.frame.setVisible(true);
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param mips 
	 */
	public GuiMIPS(MIPS mips) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 804, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][grow][][][][][][][][][][][][][][][][][][grow][][][][][grow]", "[][grow][][grow][][][][][][][][][][][][grow]"));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "cell 20 1 6 1,grow");
		
		JButton btnPlay = new JButton("");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runMIPS();
			}
		});
		btnPlay.setIcon(new ImageIcon(GuiMIPS.class.getResource("/small/Play16.gif")));
		panel.add(btnPlay);
		nextClock = new JButton(new ImageIcon(GuiMIPS.class.getResource("/small/StepForward16.gif")));
		panel.add(nextClock);
		nextClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runMIPSStep();
			}

		});
		
		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(GuiMIPS.class.getResource("/small/Pause16.gif")));
		panel.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "cell 2 3 14 7,grow");
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "cell 18 3 8 6,grow");
		panel_1.setLayout(new MigLayout("", "[][45.00,grow][][45.00,grow][][45.00,grow][66.00]", "[][][][][][][][]"));
		
		
		JLabel lblR = new JLabel("R0");
		panel_1.add(lblR, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		textField.setText("5");
		panel_1.add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JLabel lblR_8 = new JLabel("R8");
		panel_1.add(lblR_8, "cell 2 0,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setText("-9475");
		panel_1.add(textField_1, "cell 3 0,growx");
		textField_1.setColumns(10);
		
		JLabel lblR_16 = new JLabel("R16");
		panel_1.add(lblR_16, "cell 4 0,alignx trailing");
		
		textField_4 = new JTextField();
		textField_4.setText("-8237");
		panel_1.add(textField_4, "cell 5 0,growx");
		textField_4.setColumns(10);
		
		JLabel lblR_24 = new JLabel("R24");
		panel_1.add(lblR_24, "flowx,cell 6 0");
		
		JLabel lblR_1 = new JLabel("R1");
		panel_1.add(lblR_1, "cell 0 1,alignx trailing");
		
		textField_2 = new JTextField();
		textField_2.setText("3283");
		panel_1.add(textField_2, "cell 1 1,growx");
		textField_2.setColumns(10);
		
		JLabel lblR_9 = new JLabel("R9");
		panel_1.add(lblR_9, "cell 2 1,alignx trailing");
		
		textField_6 = new JTextField();
		textField_6.setText("4736");
		panel_1.add(textField_6, "cell 3 1,growx");
		textField_6.setColumns(10);
		
		JLabel lblR_17 = new JLabel("R17");
		panel_1.add(lblR_17, "cell 4 1,alignx trailing");
		
		textField_8 = new JTextField();
		textField_8.setText("2387");
		panel_1.add(textField_8, "cell 5 1,growx");
		textField_8.setColumns(10);
		
		JLabel lblR_25 = new JLabel("R25");
		panel_1.add(lblR_25, "flowx,cell 6 1");
		
		JLabel lblR_2 = new JLabel("R2");
		panel_1.add(lblR_2, "cell 0 2,alignx trailing");
		
		textField_3 = new JTextField();
		textField_3.setText("2347");
		panel_1.add(textField_3, "cell 1 2,growx");
		textField_3.setColumns(10);
		
		JLabel lblR_10 = new JLabel("R10");
		panel_1.add(lblR_10, "cell 2 2,alignx trailing");
		
		textField_7 = new JTextField();
		textField_7.setText("-4756");
		panel_1.add(textField_7, "cell 3 2,growx");
		textField_7.setColumns(10);
		
		JLabel lblR_18 = new JLabel("R18");
		panel_1.add(lblR_18, "cell 4 2,alignx trailing");
		
		textField_12 = new JTextField();
		textField_12.setText("1284");
		panel_1.add(textField_12, "cell 5 2,growx");
		textField_12.setColumns(10);
		
		JLabel lblR_26 = new JLabel("R26");
		panel_1.add(lblR_26, "flowx,cell 6 2");
		
		JLabel lblR_3 = new JLabel("R3");
		panel_1.add(lblR_3, "cell 0 3,alignx trailing");
		
		textField_9 = new JTextField();
		textField_9.setText("3472");
		panel_1.add(textField_9, "cell 1 3,growx");
		textField_9.setColumns(10);
		
		JLabel lblR_11 = new JLabel("R11");
		panel_1.add(lblR_11, "cell 2 3,alignx trailing");
		
		textField_10 = new JTextField();
		textField_10.setText("2398");
		panel_1.add(textField_10, "cell 3 3,growx");
		textField_10.setColumns(10);
		
		JLabel lblR_19 = new JLabel("R19");
		panel_1.add(lblR_19, "cell 4 3,alignx trailing");
		
		textField_15 = new JTextField();
		textField_15.setText("-5739");
		panel_1.add(textField_15, "cell 5 3,growx");
		textField_15.setColumns(10);
		
		JLabel lblR_27 = new JLabel("R27");
		panel_1.add(lblR_27, "flowx,cell 6 3");
		
		JLabel lblR_4 = new JLabel("R4");
		panel_1.add(lblR_4, "cell 0 4,alignx trailing");
		
		textField_19 = new JTextField();
		textField_19.setText("-232");
		panel_1.add(textField_19, "cell 1 4,growx");
		textField_19.setColumns(10);
		
		JLabel lblR_12 = new JLabel("R12");
		panel_1.add(lblR_12, "cell 2 4,alignx trailing");
		
		textField_14 = new JTextField();
		textField_14.setText("9876");
		panel_1.add(textField_14, "cell 3 4,growx");
		textField_14.setColumns(10);
		
		JLabel lblR_20 = new JLabel("R20");
		panel_1.add(lblR_20, "cell 4 4,alignx trailing");
		
		textField_17 = new JTextField();
		textField_17.setText("3884");
		panel_1.add(textField_17, "cell 5 4,growx");
		textField_17.setColumns(10);
		
		JLabel lblR_28 = new JLabel("R28");
		panel_1.add(lblR_28, "flowx,cell 6 4");
		
		JLabel lblR_5 = new JLabel("R5");
		panel_1.add(lblR_5, "cell 0 5,alignx trailing");
		
		textField_20 = new JTextField();
		textField_20.setText("777");
		panel_1.add(textField_20, "cell 1 5,growx");
		textField_20.setColumns(10);
		
		JLabel lblR_13 = new JLabel("R13");
		panel_1.add(lblR_13, "cell 2 5,alignx trailing");
		
		textField_21 = new JTextField();
		textField_21.setText("4747");
		panel_1.add(textField_21, "cell 3 5,growx");
		textField_21.setColumns(10);
		
		JLabel lblR_21 = new JLabel("R21");
		panel_1.add(lblR_21, "cell 4 5,alignx trailing");
		
		textField_22 = new JTextField();
		textField_22.setText("-901");
		panel_1.add(textField_22, "cell 5 5,growx");
		textField_22.setColumns(10);
		
		JLabel lblR_29 = new JLabel("R29");
		panel_1.add(lblR_29, "flowx,cell 6 5");
		
		JLabel lblR_6 = new JLabel("R6");
		panel_1.add(lblR_6, "cell 0 6,alignx trailing");
		
		textField_24 = new JTextField();
		textField_24.setText("3829");
		panel_1.add(textField_24, "cell 1 6,growx");
		textField_24.setColumns(10);
		
		JLabel lblR_14 = new JLabel("R14");
		panel_1.add(lblR_14, "cell 2 6,alignx trailing");
		
		textField_25 = new JTextField();
		textField_25.setText("-9281");
		panel_1.add(textField_25, "cell 3 6,growx");
		textField_25.setColumns(10);
		
		JLabel lblR_22 = new JLabel("R22");
		panel_1.add(lblR_22, "cell 4 6,alignx trailing");
		
		textField_26 = new JTextField();
		panel_1.add(textField_26, "cell 5 6,growx");
		textField_26.setColumns(10);
		
		JLabel lblR_30 = new JLabel("R30");
		panel_1.add(lblR_30, "flowx,cell 6 6");
		
		JLabel lblR_7 = new JLabel("R7");
		panel_1.add(lblR_7, "cell 0 7,alignx trailing");
		
		textField_28 = new JTextField();
		textField_28.setText("345");
		panel_1.add(textField_28, "cell 1 7,growx");
		textField_28.setColumns(10);
		
		JLabel lblR_15 = new JLabel("R15");
		panel_1.add(lblR_15, "cell 2 7,alignx trailing");
		
		textField_29 = new JTextField();
		textField_29.setText("1231");
		panel_1.add(textField_29, "cell 3 7,growx");
		textField_29.setColumns(10);
		
		JLabel lblR_23 = new JLabel("R23");
		panel_1.add(lblR_23, "cell 4 7,alignx trailing");
		
		textField_30 = new JTextField();
		panel_1.add(textField_30, "cell 5 7,growx");
		textField_30.setColumns(10);
		
		JLabel lblR_31 = new JLabel("R31");
		panel_1.add(lblR_31, "flowx,cell 6 7");
		
		textField_5 = new JTextField();
		textField_5.setText("1238");
		panel_1.add(textField_5, "cell 6 0");
		textField_5.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setText("-1923");
		panel_1.add(textField_11, "cell 6 1");
		textField_11.setColumns(10);
		
		textField_13 = new JTextField();
		textField_13.setText("-9231");
		panel_1.add(textField_13, "cell 6 2");
		textField_13.setColumns(10);
		
		textField_16 = new JTextField();
		textField_16.setText("1238");
		panel_1.add(textField_16, "cell 6 3");
		textField_16.setColumns(10);
		
		textField_18 = new JTextField();
		textField_18.setText("1212");
		panel_1.add(textField_18, "cell 6 4");
		textField_18.setColumns(10);
		
		textField_23 = new JTextField();
		textField_23.setText("12");
		panel_1.add(textField_23, "cell 6 5");
		textField_23.setColumns(10);
		
		textField_27 = new JTextField();
		panel_1.add(textField_27, "cell 6 6");
		textField_27.setColumns(10);
		
		textField_31 = new JTextField();
		panel_1.add(textField_31, "cell 6 7");
		textField_31.setColumns(10);
	}

	protected void runMIPSStep() {
		this.mips.runStep();
		
	}
	
	protected void runMIPS() {
		while(!this.mips.isFinished()&&!this.mips.isStopped()){
			this.runMIPSStep();
		}
		
	}

	public Icon getNextClockIcon() {
		return nextClock.getIcon();
	}
	public void setNextClockIcon(Icon icon) {
		nextClock.setIcon(icon);
	}
	public Icon getBtnNewButtonIcon() {
		return btnNewButton.getIcon();
	}
	public void setBtnNewButtonIcon(Icon icon_1) {
		btnNewButton.setIcon(icon_1);
	}
}
