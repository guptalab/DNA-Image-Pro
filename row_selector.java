import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class row_selector extends JFrame implements ActionListener {

	static Color newcolor;

	static int n = 5;
	static JButton arr[];
	static int button_num;
	static int flag = 0;
	static String filename = "filename";

	public row_selector(int f) {
		flag = f;
		String return_arr[] = getn();
		if (return_arr[0] != null) {
			n = Integer.parseInt(return_arr[1]);
			filename = return_arr[0];
			arr = new JButton[n + 1];
			initUI();
		}
	}

	public final void initUI() {

		JPanel panel = new JPanel();
		// int n = 10;
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panel.setLayout(new GridLayout(1, 10));

		for (int i = 1; i <= n; i++) {
			arr[i] = new JButton("" + i);
			arr[i].setBorderPainted(false);
			arr[i].setBackground(new Color(random255(),random255(),random255()));
			arr[i].addActionListener(this);
			arr[i].setOpaque(true);
			panel.add(arr[i]);
		}
		JButton done = new JButton("Done");
		done.addActionListener(this);
		panel.add(done);
		add(panel);
		setTitle("Base Row");
		setSize(n * 100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public int random255()
	{
		 return (int)(Math.random() * (256));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("Done")) {
			int colors[] = getcolors();
			int shifts[] = getshifts();

			if (shifts == null) {
				setVisible(false); // you can't see me!
				dispose(); // Destroy the JFrame object
				return;
			}
			uniform_shiftgen.create_tile_file(n, filename, shifts, colors, flag);
			
			JOptionPane.showMessageDialog(null,
						filename+".tiles file is created.");

			setVisible(false); // you can't see me!
			dispose(); // Destroy the JFrame object
			return;
		}

		button_num = Integer.parseInt(e.getActionCommand());
		newcolor = arr[button_num].getBackground();
		choose_color.createAndShowGUI(0);
	}

	public static boolean isInteger(String s,int min) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only came here if we didn't return false
		return Integer.parseInt(s) >= min;
	}

	public static boolean areIntegers(String s) {
		String arr[] = s.split(" ");
		boolean ans = true;
		for (int x = 0; x < arr.length; x++) {
			ans = ans & isInteger(arr[x],0);
		}
		return ans;
	}

	private String getfilename() {

		String f_name = JOptionPane.showInputDialog(null,
				"Enter File name for tile file");
		while (f_name == null) {

			f_name = JOptionPane.showInputDialog(null,
					"Enter File name for tile file");
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

	private int[] getcolors() {
		int crr[] = new int[n];
		for (int x = 1; x <= n; x++) {
			Color temp_clr = arr[x].getBackground();
			int temp_pixel = temp_clr.getAlpha();
			temp_pixel = temp_pixel << 24;
			temp_pixel += temp_clr.getRGB();
			crr[n - x] = temp_pixel;
		}
		return crr;
	}

	private int[] getshifts() {

		JPanel myPanel = new JPanel();
		// for uniform shift show only one box
		if (flag == 0) {
			JTextField uniformshiftField = new JTextField(3);
			uniformshiftField.setText("0");
			myPanel.add(uniformshiftField);

			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Please enter uniform shift value",
					JOptionPane.OK_CANCEL_OPTION);
			// Shift value of base row is considered as 0 so it is already
			// included
			int return_arr[] = null;
			if (result == JOptionPane.OK_OPTION) {
				return_arr = new int[n];
				String combine_string = "";

				combine_string += (uniformshiftField.getText());

				if (isInteger(combine_string,0)) {
					return_arr[1] = Integer.parseInt(uniformshiftField
							.getText());
				} else {
					JOptionPane.showMessageDialog(null,
							"Please Enter positive integers value for S");
					return getshifts();
				}
			}
			return return_arr;

		}
		JTextField shiftField[] = new JTextField[n - 1];
		for (int x = 0; x < n - 1; x++) {
			shiftField[x] = new JTextField(3);
			shiftField[x].setText("0");
			myPanel.add(shiftField[x]);
			myPanel.add(Box.createHorizontalStrut(3)); // a spacer
		}

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please enter shift values from previous row",
				JOptionPane.OK_CANCEL_OPTION);
		// Shift value of base row is considered as 0 so it is already included
		int return_arr[] = null;
		if (result == JOptionPane.OK_OPTION) {
			return_arr = new int[n];
			String combine_string = "";
			for (int x = 0; x < n - 1; x++) {
				combine_string += (shiftField[x].getText() + " ");
			}
			if (areIntegers(combine_string)) {
				for (int x = 0; x < n - 1; x++) {
					return_arr[x + 1] = Integer.parseInt(shiftField[x]
							.getText());
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Please Enter positive integers value for S");
				return getshifts();
			}
		}
		return return_arr;
	}

	private String[] getn() {
		// String str = JOptionPane.showInputDialog(null,
		// "Enter the number of Tiles in Base Row (N)",
		// "Value of N", 1);
		
		final JTextField filenameField = new JTextField(8);
		final JTextField nField = new JTextField(3);
		nField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				if(flag==0)
				{
					filenameField.setText("uniform_"+nField.getText());
				}
				else
				{
					filenameField.setText("nonuniform_"+nField.getText());
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				if(flag==0)
				{
					filenameField.setText("uniform_"+nField.getText());
				}
				else
				{
					filenameField.setText("nonuniform_"+nField.getText());
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				if(flag==0)
				{
					filenameField.setText("uniform_"+nField.getText());
				}
				else
				{
					filenameField.setText("nonuniform_"+nField.getText());
				}
			}
		    // implement the methods
		});
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Value of N : "));
		myPanel.add(nField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("Filename : "));
		myPanel.add(filenameField);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please Enter Filename name and value of N",
				JOptionPane.OK_CANCEL_OPTION);
		String return_arr[] = new String[2];
		if (result == JOptionPane.OK_OPTION) {
			System.out.println("Filename " + filenameField.getText());
			System.out.println("N :  " + nField.getText());
			String fname = filenameField.getText();
			String nvalue = nField.getText();

			if (fname != null && !fname.equals("") && nvalue != null
					&& isInteger(nvalue,2)) {
				String cur_dir = System.getProperty("user.dir");
				try {
					File file = new File(cur_dir + "/" + fname + ".tiles");
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					} else {
						String choosen_opps = JOptionPane
								.showInputDialog(null,
										"This file already exists. Do you want to overwrite ? \n 1. Yes \n 2. No ");

						int choosen_opp = Integer.parseInt(choosen_opps);
						if (choosen_opp == 2) {
							return getn();
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				return_arr[0] = fname;
				return_arr[1] = nvalue;
			} else {
				JOptionPane.showMessageDialog(null,
						"Please Enter positive integers value for N > 1");
				return getn();
			}
		}

		return return_arr;
	}
}
