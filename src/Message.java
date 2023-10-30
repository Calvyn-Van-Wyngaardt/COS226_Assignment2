package src;
public class Message {
    private Client from;
    private Client to;
    private String message;

    Message(Client from, Client to, String msg)
    {
        this.from = from;
        this.to = to;
        message = msg;
    }

    Message(Message msg)
    {
        this.from = msg.getFrom();
        this.to = msg.getTo();
        this.message = msg.getMessage();
    }

    public void setReceiver(Client rec)
    {
        to = rec;
    }

    public Client getTo()
    {
        return to;
    }

    public Client getFrom()
    {
        return from;
    }

    public String getMessage()
    {
        return message;
    }
}

