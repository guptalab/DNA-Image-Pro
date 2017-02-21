// SimpleFileChooser.java
// A simple file chooser to see what it takes to make one of these work.
//
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
//import java.io.FileFilter;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Runtilefile extends JFrame {

	public Runtilefile() {
				JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
				FileFilter filter = new FileNameExtensionFilter("tile files",
						"tiles");
				chooser.addChoosableFileFilter(filter);
				chooser.setFileFilter(filter);
				// chooser.get
				// chooser.setMultiSelectionEnabled(true);
				int option = chooser.showDialog(null, "Open tile file");
				if (option == JFileChooser.APPROVE_OPTION) {
					File sf = chooser.getSelectedFile();
					if(sf.getAbsolutePath().toLowerCase().endsWith(".tiles"))
					{
						executeCommand(sf.getAbsolutePath());
						dispose();
					}
					
				} 
			}
			
	private String executeCommand(String command) {
		StringBuffer output = new StringBuffer();
 		String[] cmd = { "xgrow_new/xgrow",
					command };

		Process p;
		try {
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));
 
                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output.toString();
 
	}

	public static void main(String args[]) {
		Runtilefile sfc = new Runtilefile();
		sfc.setVisible(true);
	}
}