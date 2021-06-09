package Task;

import java.io.IOException;

public class ConsoleClient {

    /*public static final int PORT_SERVER = 8289;
    public static final String HOST_SERVER = "localhost";
    private static Socket socketClient;*/

    public static void main(String[] args) {
        try{
            ClientClass client = new ClientClass();
            client.openConnection();
            client.sendMsg();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
     /*   try {
            connection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connection() throws IOException {
        socketClient = new Socket(HOST_SERVER, PORT_SERVER);
        DataInputStream inputStream = new DataInputStream(socketClient.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socketClient.getOutputStream());

        processConnectionServer(inputStream, outputStream);
    }

    private static void processConnectionServer(DataInputStream inputStream, DataOutputStream outputStream) {
        Thread threadIn = new Thread(() -> {
            while (true) {
                try {
                    String msgServer = inputStream.readUTF();
                    System.out.println("Client : " + msgServer);
                    if (msgServer.equals("/end")) {
                        inputStream.close();
                        outputStream.close();
                        close();
                       return;
                    }
                } catch (IOException e) {
                    close();
                    System.out.println("Connection has been closed");
                }
            }
        });

        Thread threadOut = new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String msgOut = scanner.nextLine();

                if (!msgOut.trim().isEmpty()) {
                    try {
                        outputStream.writeUTF(msgOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Message not send");
                    }
                }
            }
        });

            threadIn.start();
            threadOut.start();

    }

    public static void close() {
        try {
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

