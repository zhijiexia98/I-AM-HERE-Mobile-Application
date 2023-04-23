package edu.northestern.cs5520_teamproject_iamhere.StickItToEm;

public class MessageRecord
{
    private String sender;
    private String receiver;
    private String sticker;
    private String time;
    public MessageRecord(){}
    public MessageRecord(String sender, String receiver, String sticker, String time)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.sticker = sticker;
        this.time = time;
    }

    public String getSender()
    {
        return this.sender;
    }

    public String getReceiver()
    {
        return this.receiver;
    }

    public String getSticker()
    {
        return this.sticker;
    }

    public String getTime()
    {
        return this.time;
    }

}
