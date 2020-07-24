package fr.lavapower.simply.objects;

public class Variable
{
    private final String value;
    private final ExpressionType type;
    private final int id;

    public Variable(int id, String value, ExpressionType type)
    {
        this.value = value;
        this.type = type;
        this.id = id;
    }

    public String getValue() { return value; }
    public ExpressionType getType() { return type; }
    public int getID() { return id; }

    @Override
    public String toString()
    {
        return "Variable{" + "value='" + value + "', type=" + type + ", id='" + id + "'}";
    }
}
