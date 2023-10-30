package src;
import java.util.HashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args)
    {
        int numThreads = 12;
        int numClients = 6;
        
        //HashSet containing the integer numbers of clients already assigned to a Writer
        HashSet<Integer> alreadyContains = new HashSet<>(numClients);
        Random rand = new Random();
        int to;
        
        Client toClient;
        Client[] clients = new Client[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client();
        }
        
        Server server = new Server(clients);
        BakeryLock bakermaker = new BakeryLock(numThreads);

        //Threads
        Reader[] readers = new Reader[numClients];
        Writer[] writers = new Writer[numClients];

        for (int i = 0; i < clients.length; i++) {
            to = rand.nextInt(numClients-1);
            while (alreadyContains.contains(to))        //HashSet was chosen to store numbers based on efficiency of the "contains" method - O(1)
            {
                to = rand.nextInt(numClients);
            }
            
            alreadyContains.add(to);
            toClient = server.getClients()[to];

            //System.out.println(toClient == server.getClients()[to]);
            
            readers[i] = new Reader(server, clients[i], bakermaker);
            writers[i] = new Writer(server, clients[i], toClient, bakermaker);
        }

        for (int i = 0; i < numClients; i++)
        {
            writers[i].start();
            readers[i].start();
        }
    }
}