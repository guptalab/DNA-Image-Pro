import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Box;
import javax.swing.JTextField;

public class transformation extends JFrame implements ActionListener {

	static Color newcolor;

	static int n = 5;
	static JButton arr[];
	static int button_num;
	static int flag = 0;

	public transformation() {
		// flag = f;
		n = getn();
		if(n==0)
			return;
		arr = new JButton[n + 1];
		initUI();
	}

	public final void initUI() {

		JPanel panel = new JPanel();
		// int n = 10;
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panel.setLayout(new GridLayout(1, 10));

		for (int i = 1; i <= n; i++) {
			arr[i] = new JButton("" + i);
			arr[i].setOpaque(true);
			arr[i].setBorderPainted(false);
			panel.add(arr[i]);
			arr[i].setBackground(new Color(random255(),random255(),random255()));
			arr[i].addActionListener(this);
		}
		JButton done = new JButton("Done");
		done.addActionListener(this);
		panel.add(done);

		add(panel);

		setTitle("Input tile Base Row");
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
			int row_trans[] = getrowtrans();

			int new_shifts[] = new int[shifts.length];
			new_shifts[0] = shifts[0];
			for (int x = 1; x < new_shifts.length; x++) {
				new_shifts[x] = (new_shifts[x - 1] + shifts[x]) % n;
			}

			int shifts2[] = new int[shifts.length];
			shifts2[0] = new_shifts[row_trans[0] - 1];
			int prev = shifts2[0];
			for (int x = 1; x < n; x++) {

				shifts2[x] = (new_shifts[row_trans[x] - 1] - prev + n) % n;
				prev += shifts2[x];
				prev %= n;
			}

			String filename = getfilename();
			setfirstrowtrans(colors, colors.length - shifts2[0]);
			uniform_shiftgen.create_tile_file(n, filename, shifts2, colors, 1);
			JOptionPane.showMessageDialog(null,filename+".tiles file is created.");

			setVisible(false); // you can't see me!
			dispose(); // Destroy the JFrame object
			return;
		}

		button_num = Integer.parseInt(e.getActionCommand());
		newcolor = arr[button_num].getBackground();
		choose_color.createAndShowGUI(1);
	}

	private void setfirstrowtrans(int[] colors, int i) {

		rev(colors, 0, i - 1);
		rev(colors, i, colors.length - 1);
		rev(colors, 0, colors.length - 1);
	}

	private void rev(int[] colors, int i, int length) {
		while (i < length) {
			colors[i] = colors[length] + colors[i]
					- (colors[length] = colors[i]);
			i++;
			length--;
		}
	}

	// private int[] getrowtrans() {
	// 	String str = JOptionPane.showInputDialog(null,
	// 			"Enter space seperated value for Row Transformation",
	// 			"Transformation 'T' ", 1);
	// 	while (str == null || !areIntegers(str) || str.split(" ").length != n) {
	// 		JOptionPane.showMessageDialog(null, "Please Enter " + n
	// 				+ "positive integers value for Transformation T");
	// 		str = JOptionPane.showInputDialog(null,
	// 				"Enter space seperated value for Row Transformation",
	// 				"Transformation 'T' ", 1);
	// 	}
	// 	int trans[] = new int[n];
	// 	String s_arr[] = str.split(" ");

	// 	for (int x = 0; x < s_arr.length && x < n; x++) {
	// 		trans[x] = Integer.parseInt(s_arr[x]);
	// 	}
	// 	return trans;
	// }

	private boolean all_one_to_n(int arr[])
	{
		int temp[] = new int[arr.length+1];
		for(int x=0;x<arr.length;x++)
		{
			if(arr[x]>0 && arr[x]<=n)
			{
				temp[arr[x]]++;
			}
		}
		for(int x=0;x<=n;x++)
		{
			System.out.println(" "+temp[x]);
		}
		System.out.println("");
		for(int x=1;x<=n;x++)
			if(temp[x]!=1)
			{
				System.out.println("Problem : "+x);
				return false;
			}
		return true;
	}

	 private int[] getrowtrans() {
		JPanel myPanel = new JPanel();

		JTextField shiftField[] = new JTextField[n];
		for (int x = 0; x < n; x++) {
			shiftField[x] = new JTextField(3);
			shiftField[x].setText((x+1)+"");
			myPanel.add(shiftField[x]);
			myPanel.add(Box.createHorizontalStrut(3)); // a spacer
		}

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Enter new row Permutation",
				JOptionPane.OK_CANCEL_OPTION);
		// Shift value of base row is considered as 0 so it is already included
		int return_arr[] = null;
		if (result == JOptionPane.OK_OPTION) {
			return_arr = new int[n];
			String combine_string = "";
			for (int x = 0; x < n; x++) {
				combine_string += (shiftField[x].getText() + " ");
			}
			System.out.println(combine_string);
			if (areIntegers(combine_string) ) {
				for (int x = 0; x < n; x++) {
					return_arr[x] = Integer.parseInt(shiftField[x]
							.getText());
				}
				if (!all_one_to_n(return_arr))
				{
					JOptionPane.showMessageDialog(null,
					"It should contain all values from 1 to "+n);
					return getrowtrans();		
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"It should contain all values from 1 to "+n);
				return getrowtrans();
			}
		}
		return return_arr;

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


	private int getn() {
		String str = JOptionPane
				.showInputDialog(
						null,
						"Input tile details \n Enter the number of Tiles in Base Row (N)",
						"Value of N", 1);
		if (str!=null) {
			if( !isInteger(str,1))
			{
				JOptionPane.showMessageDialog(null,
					"Please Enter some positive integer value for N");
				return getn();
			}
			return Integer.parseInt(str);
		}
		else{
			return 0;
		}
		
	}
}
