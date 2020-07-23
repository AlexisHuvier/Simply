package fr.lavapower.simply.instructions;

import fr.lavapower.simply.objects.Expression;
import fr.lavapower.simply.objects.ExpressionType;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class PrintExpression implements Instruction, Opcodes
{
    private final Expression expression;

    public PrintExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void apply(MethodVisitor methodVisitor)
    {
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        if(expression.getType() == ExpressionType.INT)
        {
            methodVisitor.visitIntInsn(BIPUSH, Integer.parseInt(expression.getValue()));
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(I)V", false);
        }
        else if(expression.getType() == ExpressionType.STRING) {
            methodVisitor.visitLdcInsn(expression.getValue());
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
        }
    }
}
