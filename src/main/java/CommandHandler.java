import java.io.*;
import java.net.Socket;

public class CommandHandler implements Runnable{
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Socket clientSocket;

    public CommandHandler(Socket clientSocket) {
        try{
            this.clientSocket = clientSocket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch (IOException ioe){
            closeGracefully(clientSocket, bufferedWriter, bufferedReader);
        }
    }

    @Override
    public void run() {
        while(clientSocket.isConnected()){
            try {
                String command = bufferedReader.readLine();
                System.out.println("Received Command: " + command);

                if(command.trim().equals("PING")){
                    bufferedWriter.write("+PONG\r\n");
                    bufferedWriter.flush();
                    System.out.println("Sent Response: " + "PONG");
                }
            } catch (IOException e) {
                closeGracefully(clientSocket, bufferedWriter, bufferedReader);
                break;
            }

        }
    }

    private void closeGracefully(Socket clientSocket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        if(bufferedReader != null)bufferedReader = null;
        if(bufferedWriter != null)bufferedWriter = null;
        if(clientSocket != null)clientSocket = null;
    }
}
