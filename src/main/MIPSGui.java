package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
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
	private JButton pause = new JButton();
	private JLabel memories = new JLabel("Last Used Memories");
	private JTextField[] memory = new JTextField[5];
	private JLabel clock = new JLabel("Clock");
	private JTextField clkfield = new JTextField("0");
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
	private BufferedImage myPicture;
	private MIPSRunner runner;
	
	public MIPSGui(MIPS mips){
		super("MIPSim");
		runner = new MIPSRunner(this,mips);
		runner.execute();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 480);
		addActionListeners();
		this.mips=mips;
		for(int i=0;i<32;i++){
			regslabels[i] = new JLabel("R"+i);
			regs[i] = new JTextField();
			regs[i].setEditable(false);
		}
		for(int i =0;i<5;i++){
			memory[i]=new JTextField();
			memory[i].setEditable(false);
			memory[i].setPreferredSize(new Dimension(220,getPreferredSize().height));
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
		clkfield.setEditable(false);
		play.setIcon(new ImageIcon("resources/img/small/Play16.gif"));
		nextClock.setIcon(new ImageIcon("resources/img/small/StepForward16.gif"));
		pause.setIcon(new ImageIcon("resources/img/small/Pause16.gif"));
		try {
			myPicture = ImageIO.read(new File("resources/img/mipsnormal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		piclabel = new JLabel(new ImageIcon(myPicture));
//		piclabel.setSize(myPicture.getWidth(),myPicture.getHeight());
		piclabel.setPreferredSize(new Dimension(myPicture.getWidth(),myPicture.getHeight()));
		
		JPanel buttons = buttonsLayout();
		JPanel regs = regsLayout(); 
		JPanel memories = memoriesLayout();
		JPanel info = infoLayout();
		JPanel instpanel = instLayout();
		GroupLayout mainLayout = new GroupLayout(this.getContentPane());
		mainLayout.setAutoCreateContainerGaps(true);
		mainLayout.setAutoCreateGaps(true);
		mainLayout.setHorizontalGroup(mainLayout.createSequentialGroup()
				.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(instpanel)
						.addComponent(piclabel))
				.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(buttons)
						.addComponent(info)
						.addComponent(memories)
						.addComponent(regs)));	
		mainLayout.setVerticalGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addGroup(mainLayout.createSequentialGroup()
						.addComponent(instpanel)
						.addComponent(piclabel))
				.addGroup(mainLayout.createSequentialGroup()
						.addComponent(buttons)
						.addComponent(info)
						.addComponent(memories)
						.addComponent(regs)));	
		setLayout(mainLayout);
		setVisible(true);
	}

	private JPanel instLayout() {
		JPanel panel = new JPanel(new GridLayout(2,5));
		for(int i = 0;i<5;i++){
			panel.add(phaseslabels[i]);
		}
		for(int i = 0;i<5;i++){
			panel.add(instructions[i]);
		}
		return panel;
	}

	private JPanel infoLayout() {
		JPanel panel = new JPanel(new GridLayout(4,2));
		
		panel.add(clock);
		panel.add(clkfield);
		clkfield.setPreferredSize(new Dimension(60,getPreferredSize().height));
		clkfield.setSize(new Dimension(60,getPreferredSize().height));
		panel.add(pclabel);
		panel.add(pc);
		pc.setPreferredSize(new Dimension(60,getPreferredSize().height));
		pc.setSize(new Dimension(60,getPreferredSize().height));
		panel.add(instcomplabel);
		panel.add(instcomp);
		instcomp.setPreferredSize(new Dimension(60,getPreferredSize().height));
		instcomp.setSize(new Dimension(60,getPreferredSize().height));
		panel.add(prodlabel);
		panel.add(prod);
		prod.setPreferredSize(new Dimension(60,getPreferredSize().height));
		prod.setSize(new Dimension(60,getPreferredSize().height));
		Dimension d = new Dimension(220,panel.getHeight());
		panel.setSize(d);
		panel.setPreferredSize(d);
		return panel;
	}

	private JPanel memoriesLayout() {
		JPanel panel = new JPanel(new GridLayout(6,1));
		panel.add(memories);
		for(int i = 0;i<5;i++)
			panel.add(memory[i]);
		Dimension d = new Dimension(220,panel.getHeight());
		panel.setSize(d);
		panel.setPreferredSize(d);
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
						.addComponent(nextClock)
						.addComponent(pause))
				.addComponent(bypass));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(play)
						.addComponent(nextClock)
						.addComponent(pause))
				.addComponent(bypass));
		Dimension d = new Dimension(220,panel.getHeight());
		panel.setSize(d);
		panel.setPreferredSize(d);
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
				if(mips==null){
					MIPS mips = Main.createMIPS();
					setMips(mips);
					System.err.println(mips==null);
					runner.resetMips(mips);
				}
				else runMIPS();
			}
		});
		this.nextClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mips==null){
					MIPS mips = Main.createMIPS();
					setMips(mips);
					runner.resetMips(mips);
				}
				if(!runner.isRunning())
					runMIPSStep();
			}
		});
		this.pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseMIPS();
			}
		});
	}

	protected void runMIPSStep() {
		runner.runMIPSStep();
	}
	
	
	
	protected void runMIPS() {
		runner.setRun(true);
		if(mips.isStopped())
			mips.resume();
	}
	protected void pauseMIPS() {
		if(mips!=null)mips.stop();
	}

	public void updateInfos() {
		List<Reg> regs = mips.getRegs();
		for(int i =0;i<32 && regs.get(i)!=null;i++){
			this.regs[i].setText(regs.get(i).getValue().toString());
		}
		Integer pc = mips.getIFCircuit().getPC();
		this.pc.setText(pc.toString());
		Integer numcycles = mips.getCycles();
		clkfield.setText(numcycles.toString());
		Integer done = mips.getWBCircuit().countCompleteInstructions();
		this.instcomp.setText(done.toString());
		Float ratio = new Float(done)/new Float(numcycles);
		String ratiostr = ratio.toString();
		if(ratiostr.length()>4)ratiostr = ratiostr.substring(0,3);
		this.prod.setText(ratiostr);
		List<Integer> mems = mips.getMEMCircuit().getLastPositions();
		List<Integer> values = mips.getMEMCircuit().getLastValues();
		for(int i = 0;i<mems.size();i++){
			memory[4-i].setText("address: "+mems.get(i)+" value: "+values.get(i));
		}
	}
	public void setMips(MIPS mips){
		this.mips = mips;
	}




}
