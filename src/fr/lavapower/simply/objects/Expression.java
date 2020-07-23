package fr.lavapower.simply.objects;

public class Expression
{
    private final String value;
    private final ExpressionType type;

    public Expression(String value, ExpressionType type)
    {
        this.value = value;
        this.type = type;
    }

    public String getValue() { return value; }
    public ExpressionType getType() { return type; }

    @Override
    public String toString()
    {
        return "Constant{" + "value='" + value + '\'' + ", type=" + type + '}';
    }
}
