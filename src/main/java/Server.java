import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            if (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                CommandHandler commandHandler = new CommandHandler(clientSocket);
                System.out.println("Server : " + serverSocket);
                System.out.println("Client : " + clientSocket);
                Thread commandThread = new Thread(commandHandler);
                commandThread.start();
            }
        } catch (IOException ioe) {
            closeServer();
        }
    }

    public void closeServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
