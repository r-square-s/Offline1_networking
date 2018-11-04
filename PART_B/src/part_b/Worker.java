/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_b;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rayhan
 */
public class Worker implements Runnable {

    private Socket connectionSocket;
    private int id;

    @Override
    public void run() {
        {
            BufferedReader in;
            PrintWriter pr;

            try {
                in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                pr = new PrintWriter(connectionSocket.getOutputStream());
                OutputStream os = connectionSocket.getOutputStream();
                StringBuilder requeststring = new StringBuilder();
                
                while (in.ready()) {
                    requeststring.append((char) in.read());
                }

                String input = requeststring.substring(0);
                if (input.contains("favicon.ico")) {

                    System.out.println("Getting it out!");
                    connectionSocket.close();
                    return;

                }
                System.out.println(input);
                pr.flush();
                if (input.startsWith("GET")) {
                    String a[] = input.split(" ");
//                    for(String x:a)
//                    {
//                        System.out.println("Rayhan: "+x);
//                    }
                    String FileName;
                    if (a[1].equals("/")) {
                        FileName = "index.html";

                    } else {
                        FileName = a[1].substring(1);
                        System.out.println(FileName);
                    }

                    String MimeType;
                    if (FileName.endsWith(".mp3")) {
                        MimeType = "audio/mp3";
                    } else if (FileName.endsWith(".mp4")) {
                        MimeType = "video/mp4";
                    } else if (FileName.endsWith(".html")) {
                        MimeType = "text/html";
                    } else if (FileName.endsWith(".bmp")) {
                        MimeType = "image/bmp";
                    } else if (FileName.endsWith(".jpg")) {
                        MimeType = "image/jpg";
                    } else if (FileName.endsWith(".pdf")) {
                        MimeType = "application/pdf";
                    } else if (FileName.endsWith(".png")) {
                        MimeType = "image/png";
                    } else {
                        MimeType = "application/octet-stream";
                    }
                    System.out.println(MimeType + MimeType);
                    File file = new File(FileName);
                    if (!file.exists()) {
                        FileName = "error.html";
                        MimeType = "text/html";
                    }
                    //MimeType = "application/octet-stream";
                    String HeaderString = "HTTP/1.1 200 OK\n"
                            + "Date: " + new java.util.Date() + "\n"
                            + "Content-Type: " + MimeType + "\n\n";
                    System.out.println(HeaderString);
                    File response = new File("MimeResponse.html");
                    FileWriter responseWriter = new FileWriter(response);
                    responseWriter.write(HeaderString);
                    responseWriter.close();

                    File log = new File("logfile.txt");
                    FileWriter logWriter = new FileWriter(log, true);
                    String LogEntry = "[GET] " + " [" + new java.util.Date() + "] " + " [" + FileName + "] \n";
                    logWriter.write(LogEntry);
                    logWriter.close();
                    SendFileUsingBytes("Mimeresponse.html");
                    SendFileUsingBytes(FileName);

                } else {
                    String a[] = input.split("\n");

                }
                connectionSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void SendFileUsingBytes(String FileName) {
        try {

            File file = new File(FileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = connectionSocket.getOutputStream();
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
            }

        } catch (Exception e) {
            System.err.println("Could not transfer file.");
        }
    }

    public Worker(Socket connectionSocket, int id) {
        this.connectionSocket = connectionSocket;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Worker{" + "connectionSocket=" + connectionSocket + '}';
    }

}
