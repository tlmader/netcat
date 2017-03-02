package csci4311.nc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Binds to a specified port number on the local host and waits for a connection request from a client. Once a
 * connection is established, it operates in one of two modes: download and upload.
 *
 * @author tlmader.dev@gmail.com
 * @since 2017-02-27
 */
@SuppressWarnings("JavaDoc")
public class NetcatClient {

    private static Socket clientSocket;

    /**
     * Creates client socket makes request to the server.
     *
     * @throws Exception
     */
    private static void start(String host, int port) throws Exception {
        if (clientSocket == null) {
            clientSocket = new Socket(host, port);
        }
        if (System.in.available() > 0) {
            upload();
        } else {
            download();        }
        clientSocket.close();
    }

    /**
     * In download mode, client reads data from the socket and writes it to standard output.
     *
     * @throws Exception
     */
    private static void download() throws Exception {
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println(inFromServer.readLine());
    }

    /**
     * In upload mode, client reads data from its standard input device and writes it to the socket.
     *
     * @throws Exception
     */
    private static void upload() throws Exception {
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        String file = new Scanner(System.in).useDelimiter("\\Z").next();
        outToServer.writeBytes(file);
    }
    /**
     * Starts execution of the program, requiring a host name and port number as an argument.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args[0] != null && args[1] != null) {
            start(args[0], Integer.parseInt(args[1]));
        } else {
            System.out.println("usage:\ndownload: java main.java.nc.NetcatClient [host] [port] > [downloaded-file]\nupload: java main.java.nc.NetcatClient [host] [port] > [original-file]");
        }
    }
}
