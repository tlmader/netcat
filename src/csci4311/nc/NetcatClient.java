package csci4311.nc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Binds to a specified port number on the local host and waits for a connection request from a client. Once a
 * connection is established, it operates in one of two modes: download and upload.
 *
 * @author tlmader.dev@gmail.com
 * @since 2017-02-27
 */
public class NetcatClient {

    public static void main(String argv[]) throws Exception {
        String sentence, modifiedSentence;
        Socket clientSocket = null;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Client ready for input");
        while ((sentence = inFromUser.readLine()) != null) {
            if (clientSocket == null) {
                if (argv.length > 1) {
                    clientSocket = new Socket(argv[1], 6789);
                } else {
                    clientSocket = new Socket("localhost", 6789);
                }
            }
            DataOutputStream outToServer = new DataOutputStream(
                    clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            //clientSocket.close();
        }
    }
}
