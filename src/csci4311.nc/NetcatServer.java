package csci4311.nc;

import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Establishes a connection to the server on the given host name (or IP address) and port number and operates in one of
 * two modes: download and upload.
 *
 * @author tlmader.dev@gmail.com
 * @since 2017-02-27
 */
@SuppressWarnings("JavaDoc")
public class NetcatServer {

    private static Socket connectionSocket;
    private static ServerSocket welcomeSocket;

    /**
     * Creates welcome socket and starts update loop.
     *
     * @param port a port number
     * @throws Exception
     */
    private static void start(int port) throws Exception {
        connectionSocket = null;
        welcomeSocket = new ServerSocket(port, 0);
        while (true) {
            update();
        }
    }

    /**
     * Performs update loop to handle arbitrary sequence of clients making requests.
     *
     * @throws Exception
     */
    private static void update() throws Exception {
        if (connectionSocket == null) {
            connectionSocket = welcomeSocket.accept();
            System.out.println("Client Made Connection");
        }
        if (System.in.available() > 0) {
            download();
        } else {
            upload();
        }
        connectionSocket.close();
        System.out.println("Closing connection!");
        connectionSocket = null;
    }

    /**
     * In download mode, server reads data from the socket and writes it to standard output.
     *
     * @throws Exception
     */
    private static void download() throws Exception {
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        String file = new Scanner(new File("filename")).useDelimiter("\\Z").next();
        outToClient.writeBytes(file);
    }

    /**
     * In upload mode, server reads data from its standard input device and writes it to the socket.
     *
     * @throws Exception
     */
    private static void upload() throws Exception {

    }

    /**
     * Starts execution of the program, requiring a port number as an argument.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args[0] != null) {
            start(Integer.parseInt(args[0]));
        } else {
            System.out.println("usage:\ndownload: java main.java.nc.NetcatServer [port] < [original-file]\nupload: java main.java.nc.NetcatServer [port] > [uploaded-file]");
        }
    }
}
