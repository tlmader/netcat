package csci4311.nc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Establishes a connection to the server on the given host name (or IP address) and port number and operates in one of
 * two modes: download and upload.
 *
 * @author tlmader.dev@gmail.com
 * @since 2017-02-27
 */
public class NetcatServer {

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
        runExample();
    }
}
