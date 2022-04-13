import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.crypto.Cipher;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.security.interfaces.RSAPublicKey;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class MainClient extends JFrame {

	private JPanel contentPane;
	public static RSAPublicKey key;
    public static String input = "비밀의 문장";
    private JTextField mytext;
    private JTextField encrypted;
    Cipher cipher;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		MessageClient MC = new MessageClient();
		MC.run();
		System.out.println(key);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainClient frame = new MainClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 517, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton message = new JButton("Encrypt message");
		message.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		message.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					cipher = Cipher.getInstance("RSA");
					cipher.init(Cipher.ENCRYPT_MODE, key);
					input = mytext.getText();
					encrypted.setText(new String(cipher.doFinal(input.getBytes())));
					message.setEnabled(false);
				}catch(Exception exx){
					exx.printStackTrace();
				}
			}
		});
		message.setBounds(242, 75, 213, 46);
		contentPane.add(message);
		
		JButton file_encrypt_btn = new JButton("Encrypt File");
		file_encrypt_btn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		file_encrypt_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					cipher = Cipher.getInstance("RSA");
					cipher.init(Cipher.ENCRYPT_MODE, key);
					input = mytext.getText();
					encrypted.setText(new String(cipher.doFinal(input.getBytes())));
					file_encrypt_btn.setEnabled(false);
				}catch(Exception exx){
					exx.printStackTrace();
				}
			}
		});
		file_encrypt_btn.setBounds(242, 139, 213, 44);
		contentPane.add(file_encrypt_btn);
		
		JButton send = new JButton("Send");
		send.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(!message.isEnabled())
					{
		            String serverIp = "127.0.0.1";
		            Socket socket = new Socket(serverIp, 8888);
		           
		            // Encrypt
		            cipher = Cipher.getInstance("RSA");
		            cipher.init(Cipher.ENCRYPT_MODE, key);
		            byte[] arrCipherData = cipher.doFinal(input.getBytes());
		            
		            // Send encrypted data
		            OutputStream out = socket.getOutputStream();
		            out.write(arrCipherData);
		            encrypted.setText("Send complete!");
		            
		            socket.close();
					}
					if(!file_encrypt_btn.isEnabled())
					{
				        String serverIp = "127.0.0.1";
				        Socket socket2 = null;
				 
				        try {
				            socket2 = new Socket(serverIp, 9999);
				 
				            DataOutputStream dos;
				            FileInputStream fis;
				            BufferedInputStream bis;
				            dos = new DataOutputStream(socket2.getOutputStream());
				            
				            // Send name of the file
				            // You can change file.txt 
				            String fName = mytext.getText();
				            dos.writeUTF(fName);
				            System.out.printf("File name(%s) has been transmited.\n", fName);
				 
				            // Transmit file data
				            String directory = System.getProperty("user.dir");
				            Path path = Paths.get(directory+"/src/"+fName);
				            File f = new File(String.valueOf(path));
				            System.out.println(f);
				            fis = new FileInputStream(f);
				            bis = new BufferedInputStream(fis);
				 
				            int len;
				            int size = 245;
				            byte[] data = new byte[size];
				            while ((len = bis.read(data)) != -1) {
				            	dos.write(data, 0, len);
				            }
				         // Encryption
				            cipher = Cipher.getInstance("RSA");
				            cipher.init(Cipher.ENCRYPT_MODE, key);
				            byte[] arrCipherData = cipher.doFinal(data);
				            dos.write(arrCipherData);
				            
				            dos.flush();
				            dos.close();
				            bis.close();
				            fis.close();
				            System.out.println("File send complete!");
				            System.out.println("File size : " + f.length() + "bytes");
				        } catch (IOException ex) {
				            ex.printStackTrace();
				        }
					}
					
		        }catch (Exception ex) {
		            ex.printStackTrace();
		        }
			}
		});
		send.setBounds(186, 297, 105, 44);
		contentPane.add(send);
		
		mytext = new JTextField();
		mytext.setBounds(39, 75, 189, 108);
		contentPane.add(mytext);
		mytext.setColumns(10);
		
		encrypted = new JTextField();
		encrypted.setBounds(39, 195, 416, 90);
		contentPane.add(encrypted);
		encrypted.setColumns(10);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainClient.class.getResource("background.png")));
		label.setBounds(0, 0, 500, 376);
		contentPane.add(label);
		
		
		MessageClient MC = new MessageClient();
	}
}
