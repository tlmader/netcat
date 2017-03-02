package csci4311.nc;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * Establishes a connection to the server on the given host name (or IP address) and port number and operates in one of
 * two modes: download and upload.
 *
 * @author tlmader.dev@gmail.com
 * @since 2017-02-27
 */
@SuppressWarnings("JavaDoc")
public class NetcatUDPServer {

    private static DatagramSocket serverSocket;

    /**
     * Creates welcome socket and starts update loop to handle arbitrary sequence of clients making requests.
     *
     * @param port a port number
     * @throws Exception
     */
    @SuppressWarnings("InfiniteLoopStatement")
    private static void start(int port) throws Exception {
        serverSocket = new DatagramSocket(port);
        while (true) {
            if (System.in.available() > 0) {
                download();
            } else {
                upload();
            }
        }
    }

    /**
     * In download mode, server reads data from the socket and writes it to standard output.
     *
     * @throws Exception
     */
    @SuppressWarnings("Duplicates")
    private static boolean download() throws Exception {
        return true;
    }

    /**
     * In upload mode, server reads data from its standard input device and writes it to the socket.
     *
     * @throws Exception
     */
    private static void upload() throws Exception {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        System.out.println(Arrays.toString(receivePacket.getData()));
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
