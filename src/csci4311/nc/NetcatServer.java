package csci4311.nc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
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
public class NetcatServer {

    private static Socket connectionSocket;
    private static ServerSocket welcomeSocket;

    private static void start(int host) throws Exception {
        connectionSocket = null;
        welcomeSocket = new ServerSocket(host, 0);
        while (true) {
            update();
        }
    }

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

    private static void download() throws Exception {
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        String file = new Scanner(new File("filename")).useDelimiter("\\Z").next();
        outToClient.writeBytes(file);
    }

    private static void upload() {

    }

    private static void runExample() throws Exception {
        String clientSentence, capitalizedSentence;
        Socket connectionSocket = null;
        ServerSocket welcomeSocket = new ServerSocket(6789, 0);
        System.out.println("Server Ready for Connection");
        while (true) {
            if (connectionSocket == null) {
                connectionSocket = welcomeSocket.accept();
                System.out.println("Client Made Connection");
            }
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            System.out.println("Client sent: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
            if (clientSentence.equals(".")) {
                connectionSocket.close();
                System.out.println("Closing connection!");
                connectionSocket = null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args[1] != null) {
            start(Integer.parseInt(args[1]));
        } else {
            System.out.println("usage:\ndownload: java csci4311.nc.NetcatServer [port] < [input-file]\nupload: java csci4311.nc.NetcatServer [port] > [output-file]");
        }
    }
}
