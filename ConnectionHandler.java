import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ConnectionHandler extends Thread{
    private Socket sock;
    private static BlockingQueue<String> queue;
    private ClientInfo info = null;
    private DataInputStream is = null;

    public ConnectionHandler(ClientInfo info, BlockingQueue<String> q, Socket sock){
        this.sock = sock;
        this.info = info;
        this.queue = q;

    }

    @Override
    public void run(){
        // Collect input
        try{
            is = new DataInputStream(sock.getInputStream());
        }catch(IOException e){
            System.out.println("Connection already closed");
            e.printStackTrace();
        }

        // Keep processing messages sent from client
        while (true) {
            String line;
            try {
                line = is.readLine();
                System.out.println("Received " + line);
            } catch (IOException e1) {
                // Connection probably broke?
                e1.printStackTrace();
                System.out.println("Connection broke");
                break;
            }

            if (line != null){
                try{
                    queue.put(line);
                }catch(InterruptedException e){
                    // Fine, since interrupted while putting something in
                    // execution should resume when it comes back to the thread?
                }

            }
            else{
                System.out.println("Null result found, no message" + info.getName());
            }


        }

        // Connection broken, don't need thread anymore
        System.out.println("Thread stopped " + info.getName());
        Thread.currentThread().stop();
    }
}
