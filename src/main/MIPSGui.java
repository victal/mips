package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mips.MIPS;
import registradores.Reg;

public class MIPSGui extends JFrame {
	private MIPS mips;
	private JLabel[] regslabels = new JLabel[32];
	private JTextField[] regs= new JTextField[32];
	private JButton play = new JButton();
	private JButton nextClock = new JButton();
	private JLabel memories = new JLabel("Last Used Memories");
	private JTextField[] memory = new JTextField[5];
	private JLabel pclabel = new JLabel("PC");
	private JTextField pc = new JTextField("0"); 
	private JLabel instcomplabel = new JLabel("Instruções completadas");
	private JTextField instcomp = new JTextField("0");
	private JLabel prodlabel = new JLabel("Produtividade");
	private JTextField prod = new JTextField("?");
	private JCheckBox bypass = new JCheckBox("Bypass");
	private JLabel[] phaseslabels = new JLabel[5];
	private JTextField[] instructions = new JTextField[5];
	private JLabel piclabel;
	
	
	public MIPSGui(MIPS mips){
		super("MIPSim");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 768);
		setLayout(new GridLayout(1, 2));
		this.mips=mips;
		for(int i=0;i<32;i++){
			regslabels[i] = new JLabel("R"+i);
			regs[i] = new JTextField();
			regs[i].setEditable(false);
		}
		for(int i =0;i<5;i++){
			memory[i]=new JTextField();
			memory[i].setEditable(false);
			instructions[i]=new JTextField();
			instructions[i].setEditable(false);
		}
		phaseslabels[0] = new JLabel("IF");
		phaseslabels[1] = new JLabel("ID");
		phaseslabels[2] = new JLabel("EX");
		phaseslabels[3] = new JLabel("MEM");
		phaseslabels[4] = new JLabel("WB");
		pc.setEditable(false);
		prod.setEditable(false);
		instcomp.setEditable(false);
		play.setIcon(new ImageIcon(MIPSGui.class.getResource("/small/Play16.gif")));
		nextClock.setIcon(new ImageIcon(MIPSGui.class.getResource("/small/StepForward16.gif")));
		addActionListeners();
		JPanel rightSide = rightSideLayout();
		add(rightSide);
		setVisible(true);
	}

	private JPanel rightSideLayout() {
		JPanel buttons = buttonsLayout();
		JPanel regs = regsLayout(); 
		JPanel memories = memoriesLayout();
		JPanel info = infoLayout();
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.add(info);
		panel.add(buttons);
		panel.add(memories);
		panel.add(regs);
		return panel;
	}

	private JPanel infoLayout() {
		JPanel panel = new JPanel(new GridLayout(3,2));
		panel.add(pclabel);
		panel.add(pc);
		panel.add(instcomplabel);
		panel.add(instcomp);
		panel.add(prodlabel);
		panel.add(prod);
		return panel;
	}

	private JPanel memoriesLayout() {
		JPanel panel = new JPanel(new GridLayout(6,1));
		panel.add(memories);
		for(int i = 0;i<5;i++)
			panel.add(memory[i]);
		return panel;
	}

	private JPanel regsLayout() {
		JPanel panel = new JPanel();
		panel.setSize(200, 300);
		panel.setLayout(new GridLayout(8, 4));
		for(int i =0;i<32;i++){
			panel.add(regslabels[i]);
			panel.add(regs[i]);
		}
		return panel;
	}


	private JPanel buttonsLayout() {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		panel.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(play)
						.addComponent(nextClock))
						.addComponent(bypass));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(play)
						.addComponent(nextClock))
						.addComponent(bypass));
		return panel;
	}

	private void addActionListeners() {
		this.bypass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bypass.isSelected())
					mips.setBypass(true);
				else mips.setBypass(false);
			}
		});
		this.play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runMIPS();
			}
		});
		this.nextClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!mips.isFinished()&&!mips.isStopped())
					runMIPSStep();
			}
		});
	}

	protected void runMIPSStep() {
		this.mips.runStep();
		updateInfos();
	}
	protected void runMIPS() {
		while(!mips.isFinished()&&!mips.isStopped())
			runMIPSStep();
	}

	private void updateInfos() {
		List<Reg> regs = mips.getRegs();
		for(int i =0;i<32 && regs.get(i)!=null;i++){
			this.regs[i].setText(regs.get(i).getValue().toString());
		}
		Integer pc = mips.getIFCircuit().getPC()/4;
		this.pc.setText(pc.toString());
		int numcycles = mips.getCycles();
		Integer done = mips.getWBCircuit().countCompleteInstructions();
		this.instcomp.setText(done.toString());
		Float ratio = new Float(done)/new Float(numcycles);
		this.prod.setText(ratio.toString());
	}




}
