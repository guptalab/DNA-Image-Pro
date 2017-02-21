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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class file_selector extends JFrame {

  private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

	public file_selector() {
		JFileChooser chooser = new JFileChooser();
		ImagePreviewPanel preview = new ImagePreviewPanel();
		chooser.setAccessory(preview);
		chooser.addPropertyChangeListener(preview);
		
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir") +  System.getProperty("line.separator")));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		int option = chooser.showDialog(null, "Open image file");
		if (option == JFileChooser.APPROVE_OPTION) {
			File sf = chooser.getSelectedFile();
			if (!validate(sf.getName())) {
        		JOptionPane.showMessageDialog(null, "Select image file only");
				dispose();
        		new file_selector();
        		return;
      		}

			int type = getCompressionType();
			String inp = getfilename(sf.getName().substring(0, sf.getName().lastIndexOf('.')));
			if (type == 1) {
				image_comp.compress_image(sf.getAbsolutePath(),32,32);
				image_generator.create_image_tile("output.png", inp);
			}
			else if (type == 2) {
				image_comp.compress_image(sf.getAbsolutePath(),64,64);
				image_generator.create_image_tile("output.png", inp);
			} else {
				image_generator.create_image_tile(sf.getAbsolutePath(),
						inp);
			}
			
			JOptionPane.showMessageDialog(null,
				inp+".tiles file is created.");
			dispose();
		} 	
	}

	private int getCompressionType() {
		String choosen_opps = JOptionPane
				.showInputDialog(
						null,
						"Select Generation Type : \n  1. Rapid Image Generation \n  2. Intermediate Image Generation\n  3. Standard Image Generation ");
		while (choosen_opps == null || !isInteger(choosen_opps)) {
			choosen_opps = JOptionPane
					.showInputDialog(
							null,
							"Select Generation Type : \n  1. Rapid Image Generation \n  2. Intermediate Image Generation\n  3. Standard Image Generation ");
		}
		return Integer.parseInt(choosen_opps);
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return Integer.parseInt(s) >= 1 && Integer.parseInt(s) <= 3;
	}

	public static void main(String args[]) {
		file_selector sfc = new file_selector();
		sfc.setVisible(true);
	}

	private String getfilename(String name) {

		String f_name = JOptionPane.showInputDialog(null,
				"Enter File name for tile file",name);

		while (f_name == null) {

			f_name = JOptionPane.showInputDialog(null,
					"Enter File name for tile file",name);
		}
		String cur_dir = System.getProperty("user.dir");
		try {
			File file = new File(cur_dir + "/" + f_name + ".tiles");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} else {
				String choosen_opps = JOptionPane
						.showInputDialog(null,
								"This file already exists. Do you want to overwrite ? \n 1. Yes \n 2. No ");

				int choosen_opp = Integer.parseInt(choosen_opps);
				if (choosen_opp == 2) {

					f_name = JOptionPane.showInputDialog(null,
							"Enter File name for tile file");
					boolean new_file_choosen = false;
					while (!new_file_choosen) {
						file = new File(cur_dir + "/" + f_name + ".tiles");
						if (!file.exists()) {
							file.createNewFile();
							new_file_choosen = true;
						} else {
							choosen_opps = JOptionPane
									.showInputDialog(null,
											"This file already exists. Do you want to overwrite ? \n 1. Yes \n 2. No ");
							choosen_opp = Integer.parseInt(choosen_opps);
							if (choosen_opp == 1) {
								break;
							}

						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return f_name;
	}
	 public boolean validate(String paramString) {
    Pattern localPattern = Pattern.compile(IMAGE_PATTERN);

    Matcher localMatcher = localPattern.matcher(paramString);
    return localMatcher.matches();
  }
}