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
	//JTextField outputFileName;
	
	//setup vars for input/output files
	String inputFile;
	String outputFile;
	
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
		contentPane.setLayout(new GridLayout(2,3));
		
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
		frame.add(contentPane);
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
			int[] stats = UpdateFormat.updateFormat(inputFile, 0, outputFile); //stats will be in order of 0: words processed
																			   //1: number of lines, 2: blank lines removed
																			   //3:avg words per line, 4: avg line length
		}
		
	}

}
