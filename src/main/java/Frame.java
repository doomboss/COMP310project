import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class Frame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField keyword;
	private JButton searchButton;
	private JButton endButton;
	private JButton compileButton;
	private JTextArea output;
	private Streaming tStream;
	private Thread streamingThread;

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
		setBounds(100, 100, 1180, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
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
		
		endButton = new JButton("End Search");
		panel.add(endButton);
		endButton.addActionListener(this);
		
		compileButton = new JButton("Compile Results");
		panel.add(compileButton);
		compileButton.addActionListener(this);
		
		JPanel logpanel = new JPanel();
		contentPane.add(logpanel);
		
		output = new JTextArea();
		output.setWrapStyleWord(true);
		output.setRows(32);
		output.setColumns(100);
		output.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(output);
		logpanel.add(jsp);	
	}

	//events
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getSource().equals(searchButton)){
			tStream = new Streaming(keyword.getText().trim().toLowerCase() );
			tStream.setOutput(output);
			streamingThread = new Thread(tStream);
			try{
				streamingThread.start();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if(ae.getSource().equals(endButton)){
			System.out.println("Attempting to end the stream...");
			try{
				//streamingThread.interrupt();
				tStream.terminate();//this isn't built yet. I don't know how.
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else if(ae.getSource().equals(compileButton)){
			try{
				ArrayList<TwitterData> td = tStream.getTwitterDataCollection();
				DataHandler dh = new DataHandler(td);
				output.append(dh.compileDataAsString() );
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
