package src;
public class Client {
    private String name;
    private static int num = 0;
    Message msg;

    Client()
    {
        name = "Client-" + num++;
    }

    public void addMessage(Message msg)
    {
        this.msg = msg;
        this.msg.setReceiver(this);
    }

    public int getNum()
    {
        return Integer.parseInt(name.substring(7));
    }
    
    public String toString()
    {
        return name;
    }
}