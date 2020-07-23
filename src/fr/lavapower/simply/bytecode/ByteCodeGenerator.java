package fr.lavapower.simply.bytecode;

import fr.lavapower.simply.instructions.Instruction;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.Queue;

public class ByteCodeGenerator implements Opcodes
{
    public static byte[] generate(Queue<Instruction> instructions, String name) {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        //    version ,      access,              name, signature,     base class,        interfaces
        cw.visit(52, ACC_PUBLIC + ACC_SUPER, name, null, "java/lang/Object", null);
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            final int maxStack = 100;
            for(Instruction instruction: instructions) {
                instruction.apply(mv);
            }
            mv.visitInsn(RETURN);
            mv.visitMaxs(maxStack, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
