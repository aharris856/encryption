//GUI FILE RUNNING THE ENCRYPT CLASS
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DropMode;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Dimension;

public class runEncrypt {

	private JFrame frame;
	private JTextField fileNameField;
	private JTextField newFileNameField;
	private JLabel progressLabel;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					runEncrypt window = new runEncrypt();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public String encryptText(String input, boolean level) {
		return (new Encryptor(input,true,level)).getEncrypt();
	}
	public String decryptText(String input, boolean level) {
		return (new Encryptor(input,false,level)).getDecrypt();
	}
	//file transfer encrypt/decrypt
	public void fileXfer(String fileName, String newFileName, boolean todo, boolean level) throws Exception {
		if(fileName.equalsIgnoreCase(newFileName)) {
			progressLabel.setText("ERROR: your destination file cannot be the same as your initial file.");
			return;
		}
		if(!endsWith(newFileName, ".txt")){
			progressLabel.setText("ERROR... your destination file must end with .txt");
			return;
		}
		//make sure file to read exists
		File f = new File(fileName);
		if(!f.exists()) {
			progressLabel.setText("ERROR... file: "+fileName+" does not exist.");
			return;
		}
		int prog = 0;
		//if destination exists, remove it
		f = new File(newFileName);
		if(f.exists())f.delete();
		//File Readers and Writers
		BufferedReader infile = new BufferedReader(new FileReader(fileName));
		FileWriter fw = new FileWriter(newFileName,true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		ArrayList<String> fileContent = new ArrayList<String>();
		while(infile.ready())
			fileContent.add(infile.readLine());
		infile.close();
		int size = fileContent.size();
		progressLabel.setText("0 / "+size);
		int progTr = size/100;
		int progTrH = size/100;
		//to do = true => encrypt ... to do = false; => decrypt
		if(todo) {//encrypt
			for(int i = 0; i < size; i++)
			{
				prog = i;
				if(prog==progTr) {//display progress on progressLabel every 1% of the file
					progressLabel.setText(prog+" / "+size);
					progressLabel.paintImmediately(progressLabel.getVisibleRect());
					progTr = progTr+progTrH;
				}
				pw.println(encryptText(fileContent.get(i),level));
			}
			pw.close();
			//state done
			progressLabel.setText(fileName+" > > ENCRYPTED TO > > "+newFileName+" COMPLETE");
			bw.close();
			fw.close();
		}else {
			for(int i = 0; i < size; i++)
			{
				prog = i;
				if(prog==progTr) {//display progress on progressLabel every 1% of the file
					progressLabel.setText(prog+" / "+size);
					progressLabel.paintImmediately(progressLabel.getVisibleRect());
					progTr = progTr+progTrH;
				}
				try {
					pw.println(decryptText(fileContent.get(i),level));	
				}catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("ERROR.");
					pw.close();
					bw.close();
					fw.close();
					return;
				}
			}
			pw.close();
			//state done
			progressLabel.setText(fileName+" > > DECRYPTED TO > > "+newFileName+" COMPLETE");
			bw.close();
			fw.close();
		}
	}
	//check if input ends with end.
	static boolean endsWith(String input, String end)throws Exception
	{
		int startPos = input.length()-end.length();
		if(end.length() >= input.length())return false;
		String ending = "";
		for(int i = startPos; i < input.length(); i++)
		{
			ending +=input.charAt(i);
		}
		return ending.equals(".txt");
	}
	
	public runEncrypt() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 102, 153));
		frame.setBounds(100, 100, 1233, 778);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel welcomeLabel = new JLabel("      Welcome to my basic Encryptor! As this is a work in progress right now it will only encrypt letters.");
		welcomeLabel.setBackground(Color.LIGHT_GRAY);
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		welcomeLabel.setBounds(41, 0, 1258, 48);
		frame.getContentPane().add(welcomeLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 46, 1199, 685);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		//TEXT ENCRYPT AREA
		JLabel labelText1 = new JLabel("Text to Encrypt or Decrypt:");
		labelText1.setBackground(Color.LIGHT_GRAY);
		labelText1.setBounds(10, 0, 278, 33);
		labelText1.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelText1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel.add(labelText1);
		
		JTextArea textInput = new JTextArea();
		textInput.setMaximumSize(new Dimension(489, 2147483647));
		textInput.setText("Enter your text to encrypt here.\r\nYou can do more than one line\r\nby pressing the 'enter' key!\r\n(For large amounts of text\r\nplease save it to a text file\r\nand use the file encrypt section)");
		textInput.setBounds(10, 31, 489, 189);
		textInput.setLineWrap(true);
        textInput.setWrapStyleWord(true);
		panel.add(textInput);
		
		JLabel outputLabel = new JLabel("vv Output vv");
		outputLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		outputLabel.setBounds(318, 221, 90, 33);
		panel.add(outputLabel);
		
		JTextArea textOutput = new JTextArea();
		textOutput.setMaximumSize(new Dimension(489, 2147483647));
		textOutput.setText("Enter and encrypt or decrypt\r\nin the above text area to \r\ngenerate an output.");
		textOutput.setBounds(10, 268, 489, 412);
		textOutput.setLineWrap(true);
        textOutput.setWrapStyleWord(true);
        panel.add(textOutput);

		
		//Encrypt Text Button
		JButton encryptTextB = new JButton("Encrypt");
		encryptTextB.setFont(new Font("Tahoma", Font.BOLD, 11));
		encryptTextB.setBackground(new Color(204, 0, 51));
		encryptTextB.setBounds(10, 219, 91, 39);
		encryptTextB.addActionListener(new ActionListener(){
			@Override 
		    public void actionPerformed(ActionEvent e){  
				textOutput.setText(encryptText(textInput.getText(),false));
		   }  
		});  
		panel.add(encryptTextB);
		
		//Decrypt TextButton
		JButton decryptTextB = new JButton("Decrypt");
		decryptTextB.setFont(new Font("Tahoma", Font.BOLD, 11));
		decryptTextB.setBackground(new Color(0, 102, 204));
		decryptTextB.setBounds(131, 219, 91, 39);
		decryptTextB.addActionListener(new ActionListener(){
			@Override 
		    public void actionPerformed(ActionEvent e){  
				textOutput.setText(decryptText(textInput.getText(),false));
	    }  
	    });  
		panel.add(decryptTextB);
		//FILE ENCRYPT AREA
		JLabel fileLabel = new JLabel("Enter your file name to Encrypt or Decrypt (ex. file.txt):");
		fileLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		fileLabel.setBounds(609, 26, 541, 39);
		panel.add(fileLabel);
		
		fileNameField = new JTextField();
		fileNameField.setBounds(609, 75, 556, 33);
		panel.add(fileNameField);
		fileNameField.setColumns(10);
		
		JLabel newFileLabel = new JLabel("Enter your destination file name (ex. encryptedFile.txt):");
		newFileLabel.setVerticalTextPosition(SwingConstants.TOP);
		newFileLabel.setVerticalAlignment(SwingConstants.TOP);
		newFileLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		newFileLabel.setBounds(609, 127, 556, 52);
		panel.add(newFileLabel);
		
		newFileNameField = new JTextField();
		newFileNameField.setBounds(609, 157, 556, 33);
		panel.add(newFileNameField);
		newFileNameField.setColumns(10);
		
		//encrypt file button
		JButton encryptFileB = new JButton("Encrypt");
		encryptFileB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fileXfer(fileNameField.getText(),newFileNameField.getText(),true,false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		encryptFileB.setFont(new Font("Tahoma", Font.BOLD, 11));
		encryptFileB.setBackground(new Color(204, 0, 51));
		encryptFileB.setBounds(859, 284, 126, 39);
		panel.add(encryptFileB);
		
		//Decrypt file button
		JButton decryptFileB = new JButton("Decrypt");
		decryptFileB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fileXfer(fileNameField.getText(),newFileNameField.getText(),false,false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		decryptFileB.setFont(new Font("Tahoma", Font.BOLD, 11));
		decryptFileB.setBackground(new Color(0, 102, 204));
		decryptFileB.setBounds(1003, 284, 126, 39);
		panel.add(decryptFileB);
		
		JLabel warningLabel = new JLabel("WARNING: Choosing to write into an existing file will delete the old file.");
		warningLabel.setFont(new Font("Segoe UI Black", Font.BOLD | Font.ITALIC, 17));
		warningLabel.setBounds(556, 189, 607, 77);
		panel.add(warningLabel);
		
		progressLabel = new JLabel("0 / 0");
		progressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		progressLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		progressLabel.setBounds(619, 247, 531, 39);
		panel.add(progressLabel);
		//encrypt with extra variables
		JButton extraEncrypt = new JButton("Extra Encrypt");
		extraEncrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fileXfer(fileNameField.getText(),newFileNameField.getText(),true,true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		extraEncrypt.setFont(new Font("Tahoma", Font.BOLD, 11));
		extraEncrypt.setBackground(new Color(204, 0, 51));
		extraEncrypt.setBounds(859, 353, 126, 39);
		panel.add(extraEncrypt);
		//decrypt with extra variables
		JButton extraDecrypt = new JButton("Extra Decrypt");
		extraDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileXfer(fileNameField.getText(),newFileNameField.getText(),false,true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		extraDecrypt.setFont(new Font("Tahoma", Font.BOLD, 11));
		extraDecrypt.setBackground(new Color(0, 102, 204));
		extraDecrypt.setBounds(1003, 353, 126, 39);
		panel.add(extraDecrypt);
		
		JLabel extraEncryptLabel = new JLabel("'Extra Encrypt' will encrypt even more than 'Encrypt'. Extra Encrypted must be Extra Decrypted.\r\n");
		extraEncryptLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		extraEncryptLabel.setBounds(593, 402, 541, 39);
		panel.add(extraEncryptLabel);
		
		JLabel extraEncryptWarning = new JLabel(">> WARNING << EXTRA ENCRYPT/DECRYPT TAKES NOTICEABLY LONGER IN LARGER FILES");
		extraEncryptWarning.setFont(new Font("Tahoma", Font.BOLD, 12));
		extraEncryptWarning.setBounds(573, 435, 577, 28);
		panel.add(extraEncryptWarning);
		
	}
}
