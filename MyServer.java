package test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private ServerSocket server = null;
    private final int port;
    private final ClientHandler clientHandler;
    private volatile boolean stop;

    public MyServer(int port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
        this.stop = false;
    }

    public void start() {
        createServer();

        new Thread(() -> {
            while(!this.stop) {
                try (Socket aClient = this.server.accept()) {
                    handleClientConnection(aClient);
                } catch (IOException e) {
                    if (!this.stop) {
                        System.out.println("Error handling client: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    private void createServer() {
        try {
            this.server = new ServerSocket(this.port);
            this.server.setSoTimeout(1000);
        } catch (IOException e) {
            System.out.println("Error creating the server: " + e.getMessage());
        }
    }

    private void handleClientConnection(Socket aClient) throws IOException {
        try (InputStream inFromClient = aClient.getInputStream(); OutputStream outToClient = aClient.getOutputStream()) {

            this.clientHandler.handleClient(inFromClient, outToClient);

        } catch (IOException e) {
            System.out.println("Error handling client connection: " + e.getMessage());
        }
    }

    public void close() {
        this.stop = true;

        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing server socket: " + e.getMessage());
        }
    }
}