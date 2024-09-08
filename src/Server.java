import java.net.*;
import java.io.*;
public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
//constructor....
    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waitting....");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
public void startReading()
{
    //thread to read data
  Runnable r1=()->{
      System.out.println("reader started");
      try {
          while (true) {

                  String msg = br.readLine();
                  if (msg.equals("exit")) {
                      System.out.println("client terminated the chat");
                      socket.close();
                      break;
                  }
                  System.out.println("client:" + msg);

          }
      }
      catch (Exception e) {
          e.printStackTrace();
      }

  };
    new Thread(r1).start();
}

public void startWriting()
{
    //thread to send data
    Runnable r2=()->{
        try {
            while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                if(content.equals("exit"))
                {
                    socket.close();
                    break;
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    };
    new Thread(r2).start();
}

    public static void main(String[] args)
    {
        System.out.println("this is server .....going to start server");
        new Server();
    }
}