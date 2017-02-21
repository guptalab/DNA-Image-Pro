/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

//package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

/* ColorChooserDemo.java requires no other files. */
public class choose_color extends JPanel implements ChangeListener {

	protected JColorChooser tcc;
	protected JLabel banner;

	public choose_color() {
		super(new BorderLayout());

		// Set up the banner at the top of the window
		banner = new JLabel("", JLabel.CENTER);
		banner.setForeground(Color.yellow);
		banner.setBackground(Color.blue);
		banner.setOpaque(true);
		banner.setFont(new Font("SansSerif", Font.BOLD, 24));
		banner.setPreferredSize(new Dimension(100, 65));

		JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.add(banner, BorderLayout.CENTER);
		bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));

		// Set up color chooser for setting text color
		tcc = new JColorChooser(banner.getForeground());
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));

		add(bannerPanel, BorderLayout.CENTER);
		add(tcc, BorderLayout.PAGE_END);
	}

	public void stateChanged(ChangeEvent e) {
		Color newColor = tcc.getColor();
		banner.setBackground(newColor);

		if (which_arr == 0) {
			row_selector.newcolor = newColor;
			row_selector.arr[row_selector.button_num].setBackground(newColor);
		} else if (which_arr == 1) {
			transformation.newcolor = newColor;
			transformation.arr[transformation.button_num]
					.setBackground(newColor);
		} else if (which_arr == 2) {
			row_selector.newcolor = newColor;
			row_selector.arr[row_selector.button_num].setBackground(newColor);

		}

		frame.dispose();
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	static JFrame frame;
	static int which_arr = 0;

	public static void createAndShowGUI(int which) {
		// Create and set up the window.
		frame = new JFrame("Choose Color for tile");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		which_arr = which;
		// Create and set up the content pane.
		JComponent newContentPane = new choose_color();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

}