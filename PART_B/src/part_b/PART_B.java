/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_b;

/**
 *
 * @author Rayhan
 */
import java.net.*;
import java.io.*;

public class PART_B {

    static final int PORT = 6789;

    public static void main(String[] args) throws IOException {

        ServerSocket serverConnect = new ServerSocket(PORT);
        System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
        while (true) {
            Socket s = serverConnect.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pr = new PrintWriter(s.getOutputStream());
//            String input = in.readLine();
//            System.out.println("Here Input : " + input);
            StringBuilder payload = new StringBuilder();
            while (in.ready()) {
                payload.append((char) in.read());
            }
            System.out.println(payload);
            pr.flush();

            try {
                File file = new File("in.html");
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                OutputStream os = s.getOutputStream();
                byte[] contents;
                long fileLength = file.length();
                //pr.println(String.valueOf(fileLength));		//These two lines are used
                //pr.flush();									//to send the file size in bytes.

                long current = 0;

                long start = System.nanoTime();
                while (current != fileLength) {
                    int size = 10000;
                    if (fileLength - current >= size) {
                        current += size;
                    } else {
                        size = (int) (fileLength - current);
                        current = fileLength;
                    }
                    contents = new byte[size];
                    bis.read(contents, 0, size);
                    os.write(contents);
                    System.out.println("Sending file ... " + (current * 100) / fileLength + "% complete!");

                }

                file = new File("Anhad NaadRaag.mp3");
                System.out.println("Date: " + new java.util.Date()); 
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                os = s.getOutputStream();

                fileLength = file.length();
                System.out.println(fileLength + "Rayhaan");
                //pr.println(String.valueOf(fileLength));		//These two lines are used
                //pr.flush();									//to send the file size in bytes.

                current = 0;

                start = System.nanoTime();
                while (current != fileLength) {
                    int size = 10000;
                    if (fileLength - current >= size) {
                        current += size;
                    } else {
                        size = (int) (fileLength - current);
                        current = fileLength;
                    }
                    contents = new byte[size];
                    bis.read(contents, 0, size);
                    os.write(contents);
                    System.out.println("Sending file ... " + (current * 100) / fileLength + "% complete!");

                }

                os.flush();
//                bis.close();
//                os.close();
                s.close();
                System.out.println("File sent successfully!");
            } catch (Exception e) {
                System.err.println("Could not transfer file.");
            }
        }

    }

}
