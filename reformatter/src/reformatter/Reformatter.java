package reformatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Reformatter implements ActionListener {

	//gui stuff
	JButton openButton;
	JButton outputButton;
	JButton formatButton;
	JFileChooser fc;
	JFrame frame;
	JTextField inputName;
	JTextField outputName;
	JLabel inLabel;
	JLabel outLabel;
	ButtonGroup group;
	ButtonGroup group2;
	//JTextField outputFileName;
	
	//setup vars for input/output files
	String inputFile;
	String outputFile;
	 JLabel lblAnalysis;
	 JLabel lblHelp_2;
	 JLabel lblHelp_3;
	 JLabel lblWordProcessed;
	 JTextField textField;
	 JLabel lblHelp_4;
	 JLabel lblLines;
	 JTextField textField_1;
	 JLabel lblHelp_5;
	 JLabel lblBlankLines;
	 JTextField textField_2;
	 JLabel lblHelp_6;
	 JLabel lblAverageWordsline;
	 JTextField textField_3;
	 JLabel lblHelp_7;
	 JLabel lblAverageLineLength;
	 JLabel lblHelp_8;
	 JLabel lblHelp_9;
	 JLabel lblHelp_10;
	 JLabel spaces_added;
 JTextField textField_4;
 JTextField textField_5;
 private JRadioButton rdbtnLeftJustification;
 private JRadioButton rdbtnRightJustification;
 private JRadioButton rdbtnFullJustification;
 private JRadioButton singlebtn;
 private JRadioButton doublebtn;
	public static void main(String[] args) {
		Reformatter r = new Reformatter();
		r.MakeGUI();
		

	}
	
	void MakeGUI() {
		frame = new JFrame();
		frame.setTitle("Formatter");
		frame.setSize(300,400);
		frame.setVisible(true);
		
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setLayout(new GridLayout(12,3));
		
		inputName = new JTextField();
		outputName = new JTextField();
		fc = new JFileChooser();
		openButton = new JButton("Open a File");
		openButton.addActionListener(this);
		outputButton = new JButton("Select Output Directory");
		outputButton.addActionListener(this);
		formatButton = new JButton("Format");
		formatButton.addActionListener(this);
		inLabel = new JLabel("Input File: ");
		outLabel = new JLabel("Output File: ");
		//outputFileName = new JTextField("Enter output file name here");
		
		contentPane.add(openButton);
		contentPane.add(inLabel);
		contentPane.add(inputName);
		
		contentPane.add(outputButton);
		contentPane.add(outLabel);
		contentPane.add(outputName);
		
		contentPane.add(formatButton);
		
		//contentPane.add(outputFileName);
		frame.getContentPane().add(contentPane);
		
		rdbtnLeftJustification = new JRadioButton("Left Justification (Default)", true);
		contentPane.add(rdbtnLeftJustification, BorderLayout.SOUTH);
		
		rdbtnRightJustification = new JRadioButton("Right Justification", false);
		contentPane.add(rdbtnRightJustification, BorderLayout.SOUTH);
		
		lblHelp_2 = new JLabel("");
		contentPane.add(lblHelp_2, BorderLayout.SOUTH);
		
		rdbtnFullJustification = new JRadioButton("Full Justification", false);
		contentPane.add(rdbtnFullJustification, BorderLayout.SOUTH);
		
		lblHelp_3 = new JLabel("");
		contentPane.add(lblHelp_3, BorderLayout.SOUTH);
		
		lblHelp_8 = new JLabel("");
		contentPane.add(lblHelp_8, BorderLayout.SOUTH);
		
		singlebtn = new JRadioButton("Single Spacing", true);
		contentPane.add(singlebtn, BorderLayout.SOUTH);
		
		doublebtn = new JRadioButton("Double Spacing", false);
		contentPane.add(doublebtn, BorderLayout.SOUTH);
		
		lblAnalysis = new JLabel("Analysis: ");
		contentPane.add(lblAnalysis, BorderLayout.SOUTH);
		
		lblHelp_9 = new JLabel("");
		contentPane.add(lblHelp_9, BorderLayout.SOUTH);
		
		lblHelp_10 = new JLabel("");
		contentPane.add(lblHelp_10, BorderLayout.SOUTH);
		
		lblWordProcessed = new JLabel("# words processed");
		contentPane.add(lblWordProcessed, BorderLayout.SOUTH);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		lblHelp_4 = new JLabel("");
		contentPane.add(lblHelp_4, BorderLayout.SOUTH);
		
		lblLines = new JLabel("# lines");
		contentPane.add(lblLines, BorderLayout.SOUTH);
		
		textField_1 = new JTextField();
		contentPane.add(textField_1, BorderLayout.SOUTH);
		textField_1.setColumns(10);
		
		lblHelp_5 = new JLabel("");
		contentPane.add(lblHelp_5, BorderLayout.SOUTH);
		
		lblBlankLines = new JLabel("# blank lines removed");
		contentPane.add(lblBlankLines, BorderLayout.SOUTH);
		
		textField_2 = new JTextField();
		contentPane.add(textField_2, BorderLayout.SOUTH);
		textField_2.setColumns(10);
		
		lblHelp_6 = new JLabel("");
		contentPane.add(lblHelp_6, BorderLayout.SOUTH);
		
		lblAverageWordsline = new JLabel("Average words/line");
		contentPane.add(lblAverageWordsline, BorderLayout.SOUTH);
		
		textField_3 = new JTextField();
		contentPane.add(textField_3, BorderLayout.SOUTH);
		textField_3.setColumns(10);
		
		lblHelp_7 = new JLabel("");
		contentPane.add(lblHelp_7, BorderLayout.SOUTH);
		
		lblAverageLineLength = new JLabel("Average line length");
		contentPane.add(lblAverageLineLength, BorderLayout.SOUTH);
		
		textField_4 = new JTextField();
		contentPane.add(textField_4, BorderLayout.SOUTH);
		textField_4.setColumns(10);
		
		lblHelp_7 = new JLabel("");
		contentPane.add(lblHelp_7, BorderLayout.SOUTH);
		
		spaces_added = new JLabel("# of Spaces Added");
		contentPane.add(spaces_added, BorderLayout.SOUTH);
		
		textField_5 = new JTextField();
		contentPane.add(textField_5, BorderLayout.SOUTH);
		textField_5.setColumns(10);
		
		group = new ButtonGroup();
		group.add(rdbtnLeftJustification);
		group.add(rdbtnRightJustification);
		group.add(rdbtnFullJustification);
		
		group2 = new ButtonGroup();
		group2.add(singlebtn);
		group2.add(doublebtn);
		frame.pack();
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == openButton) {
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				inputFile = file.getAbsolutePath();
				inputName.setText(inputFile);
			}
			
		}
		if(e.getSource() == outputButton) {
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					outputFile = fc.getSelectedFile().getCanonicalPath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				outputName.setText(outputFile);
			}
		}
		if(e.getSource() == formatButton) {
			int just;
			int length = 60; //needs to be set by a input box, just doing this now for testing and correct method call
			
			if (rdbtnRightJustification.isSelected()) {
				just = 1;
			}
			else if (rdbtnFullJustification.isSelected()) {
				just = 2;
			}
			else {
				just = 0;
			}
			
			
			int[] stats = UpdateFormat.updateFormat(inputFile, just, length, outputFile);
			String wordz = String.valueOf(stats[0]);
			String lines = String.valueOf(stats[1]);
			String blankLines = String.valueOf(stats[2]);
			String Avg_Words = String.valueOf(stats[3]);
			String Avg_line_length = String.valueOf(stats[4]);
			textField.setText(wordz);
			textField_1.setText(lines);
			textField_2.setText(blankLines);
			textField_3.setText(Avg_Words);
			textField_4.setText(Avg_line_length);
			//stats will be in order of 0: words processed
		   //1: number of lines, 2: blank lines removed
		   //3:avg words per line, 4: avg line length
			//
			
		}
		
	}

}
