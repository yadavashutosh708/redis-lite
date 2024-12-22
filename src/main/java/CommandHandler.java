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
                    bufferedWriter.write("PONG");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    System.out.println("Sent Response: " + "PONG");
                }else{
                    System.out.println("Not fulfilled");
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


    /*


            InputStream inputStream = clientSocket.getInputStream();
            String text = new String(inputStream.readAlclientSocketlBytes(), StandardCharsets.UTF_8).trim();
            System.out.println("Received Command: " + text);

            if (text.equals("*1\\r\\n$4\\r\\nping\\r\\n")) {
                OutputStream outputStream = clientSocket.getOutputStream();
                String output = "PONG";
                outputStream.write(output.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

                System.out.println("Sent Respinse: " + output);
                outputStream.close();
                inputStream.close();
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
     */
}
