package fr.lavapower.simply.instructions;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class PrintNumber implements Instruction, Opcodes
{
    private int number;

    public PrintNumber(int number) {
        this.number = number;
    }

    @Override
    public void apply(MethodVisitor methodVisitor)
    {
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodVisitor.visitIntInsn(BIPUSH, number);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
    }
}
