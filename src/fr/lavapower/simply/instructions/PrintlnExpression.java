package fr.lavapower.simply.instructions;

import fr.lavapower.simply.objects.Expression;
import fr.lavapower.simply.objects.ExpressionType;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class PrintlnExpression implements Instruction, Opcodes
{
    private final Expression expression;

    public PrintlnExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void apply(MethodVisitor methodVisitor)
    {
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        if(expression.getType() == ExpressionType.INT)
        {
            methodVisitor.visitIntInsn(BIPUSH, Integer.parseInt(expression.getValue()));
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        }
        else if(expression.getType() == ExpressionType.STRING) {
            methodVisitor.visitLdcInsn(expression.getValue());
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
        else if(expression.getType() == ExpressionType.VARIABLE) {
            String[] values = expression.getValue().split("-");
            if(values[1].equals("int")) {
                methodVisitor.visitVarInsn(ILOAD, Integer.parseInt(values[0]));
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            }
            else if(values[1].equals("string")) {
                methodVisitor.visitVarInsn(ALOAD, Integer.parseInt(values[0]));
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
        }
    }
}
