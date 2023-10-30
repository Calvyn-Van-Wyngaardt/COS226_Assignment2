package src;
public class Threads extends Thread {
    public volatile Threads next = null;
    public volatile boolean locked = false;
}
