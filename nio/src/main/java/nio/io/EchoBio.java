package nio.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoBio {

    public static void main(String[] args) throws IOException {
        final ServerSocket socket = new ServerSocket(9999);
        while (true) {
            final Socket clientSocket = socket.accept();
            System.out.println("accept from " + clientSocket);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        while (true) {
                            if (!clientSocket.isClosed()) {
                                String line = reader.readLine();
                                writer.println(line);
                                writer.flush();
                                if (line == null) {
                                    System.out.println("Closing socket " + clientSocket);
                                    clientSocket.close();
                                }

                                System.out.println(line);
                            }
                            else {
                                System.out.println("Socket has closed " + clientSocket);
                                break;
                            }

                        }
                    }
                    catch (IOException e) {
                        System.out.println("exception ");
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

}
