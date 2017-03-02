package csci4311.nc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Binds to a specified port number on the local host and waits for a connection request from a client. Once a
 * connection is established, it operates in one of two modes: download and upload.
 *
 * @author tlmader.dev@gmail.com
 * @since 2017-02-27
 */
public class NetcatClient {

    private static Socket clientSocket;

    /**
     * Creates client socket and starts update loop.
     *
     * @param host a port number
     * @throws Exception
     */
    private static void start(String host, int port) throws Exception {
        clientSocket = null;
        while (true) {
            update(host, port);
        }
    }

    /**
     * Performs update loop to handle arbitrary sequence of clients making requests.
     *
     * @throws Exception
     */
    private static void update(String host, int port) throws Exception {
        if (clientSocket == null) {
            clientSocket = new Socket("localhost", 6789);
        }
        if (System.in.available() > 0) {
            download();
        } else {
            upload();
        }
    }

    /**
     * In download mode, server reads data from the socket and writes it to standard output.
     *
     * @throws Exception
     */
    private static void download() throws Exception {
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("FROM SERVER: " + inFromServer.readLine());
    }

    /**
     * In upload mode, server reads data from its standard input device and writes it to the socket.
     *
     * @throws Exception
     */
    private static void upload() throws Exception {
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        String file = new Scanner(new File("filename")).useDelimiter("\\Z").next();
        outToServer.writeBytes(file);
    }

    /**
     * Starts execution of the program, requiring a port number as an argument.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args[0] != null && args[1] != null) {
            start(args[0], Integer.parseInt(args[1]));
        } else {
            System.out.println("usage:\ndownload: java csci4311.nc.NetcatServer [port] < [input-file]\nupload: java csci4311.nc.NetcatServer [port] > [output-file]");
        }
    }
}
