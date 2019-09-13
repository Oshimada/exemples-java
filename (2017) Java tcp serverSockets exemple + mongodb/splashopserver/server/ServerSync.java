

package splashopserver.server;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import splashopserver.database.DBQuerrys;

public class ServerSync {
    
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static DBQuerrys db = null;

    private static final int maxClientsCount = 10000;
    private static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String args[]) {
      
        
      db = new DBQuerrys();
      int portNumber = 2222;
      if (args.length < 1) {
        System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n"
            + "Now using port number=" + portNumber);
      } else {
        portNumber = Integer.valueOf(args[0]).intValue();
      }
      
      try {
        serverSocket = new ServerSocket(portNumber);
      } catch (IOException e) {
        System.out.println(e);
      }

      ServerSync.getDB().FindwithinCircle(23,-6,0,"","");
      while (true) {
          try {
            clientSocket = serverSocket.accept();
            int i = 0;
            for (i = 0; i < maxClientsCount; i++) {
              if (threads[i] == null) {
                (threads[i] = new clientThread(clientSocket, threads)).start();
                break;
              }
            }
            if (i == maxClientsCount) {
              PrintStream os = new PrintStream(clientSocket.getOutputStream());
              os.println("Server too busy. Try later.");
              os.close();
              clientSocket.close();
            }
          } catch (IOException e) {
            System.out.println(e);
          }
      }
    }  

    public static DBQuerrys getDB() {
        return db;
    }
}
