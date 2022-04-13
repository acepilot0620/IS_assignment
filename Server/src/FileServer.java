import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
 
class FileServer implements Runnable {
	
	RSAPrivateKey privateKey;   
    public FileServer() {
        
    }

	@Override
	public void run() {
		ServerSocket serverSocket = null;
        Socket socket = null;
 
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Acivate File server");
            socket = serverSocket.accept();
 
            // Receiving File
            DataInputStream dis;
            FileOutputStream fos;
            BufferedOutputStream bos;
            dis = new DataInputStream(socket.getInputStream());
            String fName = dis.readUTF();
            System.out.println("File name " + fName + " recieved");
 
            // Create file and file outputstream
            File f = new File(fName);
            fos = new FileOutputStream(f);
            bos = new BufferedOutputStream(fos);
            System.out.println("File name "+ fName + " has created.");
 
            // Record
            int len;
            int size = 256;
            byte[] data = new byte[256];
            while ((len = dis.read(data)) != -1) {
                ;
            }
            
            
            // Decryption
            Cipher cipher = Cipher.getInstance("RSA");
            this.privateKey = MainServer.privateKey;
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] arrData = cipher.doFinal(data);
            String directory = System.getProperty("user.dir");
            Path path = Paths.get(directory+"/"+fName);
            Files.write(path, arrData);
            String strResult = new String(arrData);
            System.out.println(strResult);
            MainServer.decrypted = strResult;
 
            bos.write(arrData);
            
            MainServer.textField.setText(new String(data));
            MainServer.textArea.setText("File has arrived");
            bos.flush();
            bos.close();
            fos.close();
            dis.close();
            socket.close();
            serverSocket.close();
            System.out.println("Received file size : " + f.length() + "bytes");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}