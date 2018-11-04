/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_a;

import java.util.Base64;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import java.net.*;
import java.util.Base64;
import java.util.Scanner;
/**
 *
 * @author Rayhan
 */
public class PART_A {



    public static void main(String[] args) throws UnknownHostException, IOException {
        String mailServer = "webmail.buet.ac.bd";
        //String mailServer ="mx1.hotmail.com";
        InetAddress mailHost = InetAddress.getByName(mailServer);
        InetAddress localHost = InetAddress.getLocalHost();
        Socket smtpSocket = new Socket(mailHost,25);
        BufferedReader in =  new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
        PrintWriter pr = new PrintWriter(smtpSocket.getOutputStream(),true);
        String initialID = in.readLine();
        System.out.println(initialID);
        pr.println("HELO "+localHost.getHostName());
        pr.flush();
        String welcome = in.readLine();
        System.out.println(welcome);
        
        // TODO code application logic here
        pr.println("MAIL FROM:<mohdrakibulhasan@gmail.com>");
        welcome = in.readLine();
        System.out.println(welcome);
//        pr.println("RSET");
//        welcome = in.readLine();
//        System.out.println(welcome);
//                pr.println("MAIL FROM:<mohdrakibulhasan@gmail.com>");
//        welcome = in.readLine();
//        System.out.println(welcome);
//                pr.println("RSLLSS");
//        welcome = in.readLine();
//        System.out.println(welcome);
        pr.println("RCPT TO:<awesomerayhan@gmail.com>");
        welcome = in.readLine();
        System.out.println(welcome);
        pr.println("RCPT TO:<mohaimin0312sam@gmail.com>");
        welcome = in.readLine();
        System.out.println(welcome);
        pr.println("DATA");
        welcome = in.readLine();
        System.out.println(welcome);
        Scanner scanner=new Scanner(System.in);
        String fileName = "Anhad NaadRaag.mp3";
        
        String Msg="Subject: MIME test\n"
                + "MIME-Version: 1.0\n"
                + "Content-type: multipart/mixed;boundary=sep\n"
                + "--sep\n"
                + "Content-Type:application/octet-stream;name=\""+fileName+"\"\n"
                + "Content-Transfer-Encoding:base64\n"
                + "Content-Disposition:attachment;filename=\""+fileName+"\"\n"
                + "\n"
                + encodeFileToBase64Binary(new File(fileName))
                + "\n\n"
                + "--sep--\n\n"
                + ".";
        /*String Msg = "Subject: Bad Syntax Solve Test\n"
                + "\n"
                + "Test it..\n"
                + ".";*/
        //System.out.println(Msg);
        pr.println(Msg);
        pr.flush();
        welcome = in.readLine();
        System.out.println(welcome);
        pr.println("QUIT");
        welcome = in.readLine();
        System.out.println(welcome);
        
    }
    private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encodeToString(bytes).toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }
}
