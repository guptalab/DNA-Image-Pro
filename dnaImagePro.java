import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class dnaImagePro extends JFrame implements ActionListener {

  public static final String b1 = "Uniform Shift Generator";
  public static final String b2 = "Non-Uniform Shift Generator";
  public static final String b3 = "Image Tile Generator";
  public static final String b4 = "Transformation Generator";
  public static final String b5 = "Run a Tile File";  

  public dnaImagePro() {
    initUI();
  }

  public final void initUI() {

    JMenuBar menubar = new JMenuBar();

    JMenu file = new JMenu("Menu");
    file.setMnemonic(KeyEvent.VK_F);

    JMenuItem fileuniform = new JMenuItem(b1);
    fileuniform.setMnemonic(KeyEvent.VK_U);
    fileuniform.addActionListener(this);

    JMenuItem filenonuniform = new JMenuItem(b2);
    filenonuniform.setMnemonic(KeyEvent.VK_N);
    filenonuniform.addActionListener(this);

    JMenuItem filetrans = new JMenuItem(b4);
    filetrans.setMnemonic(KeyEvent.VK_T);
    filetrans.addActionListener(this);

    JMenuItem fileimage = new JMenuItem(b3);
    fileimage.setMnemonic(KeyEvent.VK_I);
    fileimage.addActionListener(this);

    JMenuItem runtilefile = new JMenuItem(b5);
    runtilefile.setMnemonic(KeyEvent.VK_R);
    runtilefile.addActionListener(this);

    JMenuItem fileExit = new JMenuItem("Exit");
    fileExit.setMnemonic(KeyEvent.VK_C);
    fileExit.setToolTipText("Exit application");
    fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
        ActionEvent.CTRL_MASK));

    fileExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.exit(0);
      }

    });

    file.add(fileuniform);
    file.add(filenonuniform);
    file.add(filetrans);
    file.add(fileimage);
    file.add(runtilefile);
    file.add(fileExit);

    menubar.add(file);

    setJMenuBar(menubar);

    setTitle("DNA ImagePro");
    setSize(400, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        dnaImagePro ex = new dnaImagePro();
        ex.setVisible(true);
      }
    });
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equalsIgnoreCase(b1)) {

      row_selector ex = new row_selector(0);
      ex.setVisible(true);

    } else if (e.getActionCommand().equalsIgnoreCase(b2)) {
      row_selector ex = new row_selector(1);
      ex.setVisible(true);

    } else if (e.getActionCommand().equalsIgnoreCase(b3)) {
      file_selector sfc = new file_selector();

    } else if (e.getActionCommand().equalsIgnoreCase(b4)) {
      transformation tf = new transformation();
      tf.setVisible(true);
    }
    else if (e.getActionCommand().equalsIgnoreCase(b5)) {
      Runtilefile rc = new Runtilefile();
  
    }

  }
}