import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Scanner;
import javax.crypto.Cipher;

class MessageClient implements Runnable{

	public static RSAPublicKey key;
	
	public void run() {
		try{
            String serverIp = "127.0.0.1";
            Socket socket = new Socket(serverIp, 7777);
            System.out.println("Connected IP : " + serverIp);
             
            // Open input stream
            InputStream in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            // Get public key from server
            String fist_line = br.readLine();
            String second_line = br.readLine();
            String third_line = br.readLine();
            
            // Parse string input
            String modulus_input = third_line.substring(2);
            System.out.println(modulus_input);
            String [] inputString = modulus_input.split("modulus: ");
            System.out.println(inputString[1]);
            
            // Create public key
            BigInteger modulus = new BigInteger(String.valueOf( inputString[1]));
            BigInteger exponent = new BigInteger(String.valueOf("65537"));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, exponent);
            key = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
            MainClient.key = this.key;
            in.close();
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
