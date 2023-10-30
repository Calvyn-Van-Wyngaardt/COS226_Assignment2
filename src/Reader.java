package src;
public class Reader extends Threads {
    private Client receiver;
    private Server server;
    private volatile BakeryLock baker;

    Reader(Server serv, Client rec, BakeryLock bakerLock)
    {
        this.server = serv;
        this.receiver = rec;
        this.baker = bakerLock;
    }

    public void run()       //Using the start() method instead of the run() method will leave you with "main" as the thread name
    {
            while (server.isWriting()) {}
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();;
            }
            
            baker.lock();
            try {
                server.checkQueue(receiver);
            }
            finally {
                baker.unlock();
        }
    }

    public Client getReceiver()
    {
        return receiver;
    }
}