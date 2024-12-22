import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        //  Uncomment this block to pass the first stage
        ServerSocket serverSocket;
        Socket clientSocket = null;
        int port = 6379;
        try {
            System.out.println("Port: " + port);
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            clientSocket = serverSocket.accept();
            System.out.println("Server: " + serverSocket + "\n clientSocket: " + clientSocket);


            InputStream inputStream = clientSocket.getInputStream();
            String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).trim();
            System.out.println("Received Command: " + text);
            if (text.equals("*1\\r\\n$4\\r\\nping\\r\\n")) {
                OutputStream outputStream = clientSocket.getOutputStream();
                String output = "PONG";
                outputStream.write(output.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

                System.out.println("Sent Respinse: " + output);
                outputStream.close();
                inputStream.close();
            } else {
                System.out.println("Not met");
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
