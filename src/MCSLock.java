package src;
import java.util.concurrent.atomic.AtomicReference;

public class MCSLock {
    public volatile AtomicReference<Threads> tail;
    public volatile ThreadLocal<Threads> node;

    public MCSLock() 
    {
        tail = new AtomicReference<Threads>(null);
        node = new ThreadLocal<Threads>() {
            protected Threads initialValue() {
                return new Threads();
            }
        };
    }

    public void lock() {
        Threads qnode = node.get();
        Threads predNode = tail.getAndSet(qnode);
        if (predNode != null)
        {
            qnode.locked = true;
            predNode.next = qnode;
            
            while (qnode.locked) {} //Wait until the predecessor gives up the lock...
        }
    }

    public void unlock() 
    {
        Threads qnode = node.get();
        if (qnode.next == null)
        {
            if (tail.compareAndSet(qnode, null))
            {
                return;
            }

            while (qnode.next == null) {} //Wait until sucessor fills in the next field
        }
        qnode.next.locked = false;
        qnode.next = null;
    }
}