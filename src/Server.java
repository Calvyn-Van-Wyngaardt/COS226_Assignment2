package src;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    private Client[] clients;
    //public Message[] messages;
    public volatile Queue<Message> messages = new LinkedList<>();
    private MCSLock lock;
    private volatile boolean is_writing = false;
    private volatile boolean[] done_writing;

    Server(Client[] clients) {
        this.clients = clients;
        //messages = new Message[clients.length];
        done_writing = new boolean[clients.length];
        for (int i = 0; i < clients.length; i++) {
            done_writing[i] = false;
        }
        lock = new MCSLock();
    }

    public void setWriting(boolean writing, int id, boolean done)
    {
        is_writing = writing;
        done_writing[id] = done;
    }

    public boolean doneWriting()
    {
        for (int i = 0; i < done_writing.length; i++) {
            if (done_writing[i] == false)
                return false;
        }

        return true;
    }

    public boolean messagesEmpty()
    {
        return messages.isEmpty();
    }

    public boolean isWriting()
    {
        return is_writing;
    }

    public boolean addMessage(Message msg)
    {
        lock.lock();
        try 
        {

            //ARRAY IMPLEMENTATION...
            //if (msg.getTo().getNum() < 0 || msg.getTo().getNum() > clients.length)
            //  return false;
            
            //messages[msg.getTo().getNum()] = new Message(msg);

            //QUEUE IMPLEMENTATION...
            if (messages.add(msg))
            {
                System.out.println("(SEND) " + Thread.currentThread().getName() + ": SUCCESSFUL");
                return true;
            }
            
            return false;
            
        } 
        finally 
        {
            lock.unlock();
        }
    }

    public void checkQueue(Client client)
    {
        lock.lock();
        try 
        {

            //Array Implementation
            // for (int i = 0; i < messages.length; i++) {
            //     if (messages[i] != null)
            //     {
            //         if (messages[i].getTo().getNum() == client.getNum())
            //         {
            //             System.out.println("(RECEIVE) " + Thread.currentThread().getName() + ": {recipient: " + client.toString() + ", sender: " + messages[i].getFrom().toString() + "}");
            //             break;
            //         }
            //     }
            // }

            //Queue implementation
            Queue<Message> newQ = new LinkedList<>();
            newQ.addAll(messages);
            while (newQ.peek() != null)
            {
                Message node = newQ.poll();
                if (node.getTo() == client)
                {
                    System.out.println("(RECEIVE) " + Thread.currentThread().getName() + ": {recipient: " + client.toString() + ", sender: " + node.getFrom().toString() + "}");
                    messages.remove(node);
                    break;
                }
            }

        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
        finally 
        {
            lock.unlock();
        }
    }
    
    public Client[] getClients()
    {
        return clients;
    }
}