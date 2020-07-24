package fr.lavapower.simply.instructions;

import fr.lavapower.simply.objects.ExpressionType;
import fr.lavapower.simply.objects.Variable;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class VariableDeclaration implements Instruction, Opcodes
{
    private final Variable variable;

    public VariableDeclaration(Variable variable) {
        this.variable = variable;
    }

    @Override
    public void apply(MethodVisitor methodVisitor)
    {
        if(variable.getType() == ExpressionType.INT) {
            methodVisitor.visitIntInsn(BIPUSH, Integer.parseInt(variable.getValue()));
            methodVisitor.visitVarInsn(ISTORE, variable.getID());
        }
        else if(variable.getType() == ExpressionType.STRING) {
            methodVisitor.visitLdcInsn(variable.getValue());
            methodVisitor.visitVarInsn(ASTORE, variable.getID());
        }
        else if(variable.getType() == ExpressionType.VARIABLE) {
            String[] values = variable.getValue().split("-");
            if(values[1].equals("int")) {
                methodVisitor.visitVarInsn(ILOAD, Integer.parseInt(values[0]));
                methodVisitor.visitVarInsn(ISTORE, variable.getID());
            }
            else if(values[1].equals("string")) {
                methodVisitor.visitVarInsn(ALOAD, Integer.parseInt(values[0]));
                methodVisitor.visitVarInsn(ASTORE, variable.getID());
            }
        }
    }
}
