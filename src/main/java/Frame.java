import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Frame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField keyword;
	private JButton searchButton;
	private JTextArea output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		JLabel searchLabel = new JLabel("Keyword");
		panel.add(searchLabel);
		searchLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		keyword = new JTextField();
		panel.add(keyword);
		keyword.setToolTipText("Enter the Keyword here");
		keyword.setColumns(30);
		
		searchButton = new JButton("Search");
		panel.add(searchButton);
		searchButton.addActionListener(this);
		
		JPanel logpanel = new JPanel();
		contentPane.add(logpanel);
		
		
		output = new JTextArea();
		output.setWrapStyleWord(true);
		output.setRows(8);
		output.setColumns(50);
		output.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(output);
		logpanel.add(jsp);
		
		
		
		
	}

	
	
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource().equals(searchButton)){
			Streaming tStream = new Streaming(keyword.getText().trim().toLowerCase(),100);
			tStream.setOutput(output);
			Thread thread = new Thread(tStream);
			try{
				thread.start();
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
