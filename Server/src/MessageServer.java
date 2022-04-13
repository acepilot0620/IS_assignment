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
class MessageServer implements Runnable{
       public MessageServer(){
             
       }
       
       RSAPublicKey publicKey;
       RSAPrivateKey privateKey;
       
	@Override
	public void run() {
		try{ 
    	 	// Create socket
            ServerSocket server = new ServerSocket(7777);
            Socket sock = server.accept();
            InetAddress  inetaddr = sock.getInetAddress();
            System.out.println("Connected by client IP: "+inetaddr.getHostAddress());
            
            // Define key generator with RSA algorithm
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            
            // Randomly created public key and private key
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            publicKey = (RSAPublicKey) keyPair.getPublic();
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            MainServer.publicKey = this.publicKey;
            MainServer.privateKey = this.privateKey;
            
            // Send public key to the client
            OutputStream out = sock.getOutputStream();
            out.write(publicKey.toString().getBytes());
            
            // Get encrypted message from client
            InputStream in = sock.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            byte []data = new byte[256];
            while(dis.read(data) != -1);
            
            String encrypted_message = new String(data);
            System.out.println("Encrypted message from client : " + encrypted_message);
    
            // Decryption
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] arrData = cipher.doFinal(data);
            String decrypted_message = new String(arrData);
            System.out.println(decrypted_message);
            
            // socket close
            dis.close();

            
     } catch(Exception e){
            System.out.println(e);
     }
		
	}
}