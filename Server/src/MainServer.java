import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class MainServer extends JFrame implements Runnable {
	private JPanel contentPane;
	public static RSAPublicKey publicKey;
    public static RSAPrivateKey privateKey;
    public static JTextArea textArea = new JTextArea();
    public static JTextField textField;
    public static String decrypted;
    public static Thread second_thread;
    public static Message message;
    public static FileServer file_server = new FileServer();
    public static MainServer frame = new MainServer();
    private JLabel label;
	
	public static void main(String[] args) throws Exception {
		frame.setVisible(true);
		MessageServer message_server = new MessageServer();
		message = new Message();
		Thread one = new Thread(message_server);
		second_thread = new Thread(message);
		Thread third_thread = new Thread(file_server);
		while(true)
		{
		one.start();
		second_thread.start();
		third_thread.start();
		}
	}

	public MainServer()  {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton Decrypted = new JButton("Decrypt!");
		Decrypted.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Decrypted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("Decryption Success!\n");
				textArea.append("Message : " + decrypted);
			}
		});
		 
		Decrypted.setBounds(120, 150, 230, 50);
		contentPane.add(Decrypted);
		textArea.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		textArea.setBounds(33, 239, 428, 132);
		contentPane.add(textArea);
		textField = new JTextField();
		textField.setBounds(33, 94, 428, 45);
		contentPane.add(textField);
		textField.setColumns(10);
		label = new JLabel("");
		label.setIcon(new ImageIcon(MainServer.class.getResource("background2.png")));
		label.setBounds(0, 0, 500, 400);
		contentPane.add(label);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
