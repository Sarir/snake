package ru.Snake.game.frames;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import ru.Snake.SnakeMain;
import ru.Snake.game.MainLoop;
import ru.Snake.game.References;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Menu extends JFrame {

	public JPanel frame;
	
	private JSpinner spWidth;
	private JSpinner spHeight;
	private JLabel lblApplesToIncrease;
	private JSpinner spApple;
	private JCheckBox boxMusic;

	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public Menu() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		setResizable(false);
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 224, 177);
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		frame.setLayout(null);
		
		JLabel lblGFS = new JLabel("Game field size");
		lblGFS.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGFS.setBounds(10, 11, 113, 14);
		frame.add(lblGFS);
		
		spWidth = new JSpinner();
		spWidth.setModel(new SpinnerNumberModel(new Integer(10), new Integer(10), null, new Integer(1)));
		spWidth.setBounds(59, 29, 39, 20);
		frame.add(spWidth);
		
		spHeight = new JSpinner();
		spHeight.setModel(new SpinnerNumberModel(new Integer(10), new Integer(10), null, new Integer(1)));
		spHeight.setBounds(59, 59, 39, 20);
		frame.add(spHeight);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblWidth.setBounds(10, 32, 46, 14);
		frame.add(lblWidth);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblHeight.setBounds(10, 62, 46, 14);
		frame.add(lblHeight);
		
		lblApplesToIncrease = new JLabel("Apples increase the size by");
		lblApplesToIncrease.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblApplesToIncrease.setBounds(10, 93, 154, 14);
		frame.add(lblApplesToIncrease);
		
		spApple = new JSpinner();
		spApple.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spApple.setBounds(174, 90, 39, 20);
		frame.add(spApple);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				References.appleIncBy = (int) spApple.getValue();
				References.music = boxMusic.isSelected();
				SnakeMain.game = new MainLoop((int)spHeight.getValue(), (int)spWidth.getValue());
			}
		});
		btnStartGame.setBounds(10, 119, 203, 23);
		frame.add(btnStartGame);
		
		boxMusic = new JCheckBox("Music");
		boxMusic.setFont(new Font("Dialog", Font.PLAIN, 12));
		boxMusic.setSelected(true);
		boxMusic.setBounds(113, 41, 97, 23);
		frame.add(boxMusic);
	}
}
