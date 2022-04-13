import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
class Message implements Runnable{
	RSAPublicKey publicKey;
    RSAPrivateKey privateKey;   
	public Message(){
       }  
	@Override
	public void run() {
		try{ 
            ServerSocket server = new ServerSocket(8888);
            Socket sock = server.accept();
            InetAddress  inetaddr = sock.getInetAddress();
            System.out.println("Connected by IP: "+inetaddr.getHostAddress());
            InputStream in = sock.getInputStream();
            DataInputStream dis = new DataInputStream(in);  
            this.privateKey = MainServer.privateKey;
            this.publicKey = MainServer.publicKey;
            while(true){
            	int check = 1;
            	byte []data = new byte[256];
            while(dis.read(data) != -1)
            {;
            check = 0;
            }	
            if(check == 0)
            {
            	String encrypted_message = new String(data);
            System.out.println("Encrypted message from Client : " + encrypted_message);
            MainServer.textField.setText(encrypted_message);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] arrData = cipher.doFinal(data);
            String encrypted = new String(arrData);
            MainServer.decrypted = encrypted;
            MainServer.textArea.setText("Encrypted Message has recieved.\n");   
            }
            }
     } catch(Exception e){
            System.out.println(e);
     }
		
	}
}