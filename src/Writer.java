package src;
import java.util.Random;

public class Writer extends Threads {
    private Client from;
    private Client to;
    private volatile BakeryLock baker;
    private String[] messages;
    private Server server;
    private int id;
    private static volatile int num = 0;

    Writer(Server server, Client client, Client to, BakeryLock bakerLock)
    {
        id = num++;
        this.server = server;
        from = client;
        this.to = to;
        this.baker = bakerLock;

        messages = new String[10];
        messages[0] = "Sounds like a skill issue...";
        messages[1] = "There is now then no condemnation to those that are in Christ Jesus";
        messages[2] = "NOOT NOOT";
        messages[3] = "Oh no... Anyway";
        messages[4] = "Who's gonna carry the boats?";
        messages[5] = "Stand up straight with your shoulders back";
        messages[6] = "Tell the truth - or, at least, don't lie";
        messages[7] = "The Way, The Truth and the Life";
        messages[8] = "Sounds like a skill issue...";
        messages[9] = "My Grace is sufficient for you";
    }

    public void run()
    {
        sendMessage();
    }

    public void sendMessage()
    {
        server.setWriting(true, id, false);
        Random rand = new Random();
        System.out.println("(SEND) " + Thread.currentThread().getName() + ": {sender: " + from.toString() + ", recipient: " + to.toString() + "}"); 
        //Lock
        baker.lock();
        try {
           server.addMessage(new Message(from, to, messages[rand.nextInt(10)]));
           
        }
        finally {
            //Unlock
            server.setWriting(false,id, true);
            baker.unlock();
        }
    }
}
