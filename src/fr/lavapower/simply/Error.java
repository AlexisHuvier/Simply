package fr.lavapower.simply;

public class Error
{
    public Error(String name, String message, Exception e) {
        System.err.println("ERROR : "+name+"\n"+message);
        e.printStackTrace();
    }

    public Error(String name, String message) {
        System.err.println("ERROR : "+name+"\n"+message);
        System.exit(1);
    }
}
