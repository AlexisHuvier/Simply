package fr.lavapower.simply;

import fr.lavapower.simply.bytecode.ByteCodeGenerator;
import fr.lavapower.simply.parser.Parser;

import java.io.*;

public class Simply
{
    public static void main(String[] argv) {
        if(argv.length == 1) {
            File file = new File(argv[0]);
            String[] fileParts = file.getName().split("\\.");
            if(!file.exists() || !file.isFile() || !(fileParts.length > 1) || !fileParts[fileParts.length - 1].equals("sy"))
                new Error("FileError", "Give existing simply file in arguments\n - File Gived : "+argv[0]);
            try {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while((line = bufferedReader.readLine()) != null )
                    stringBuilder.append(line).append("\n");
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                compile(file, stringBuilder.toString());
            }
            catch(IOException e)
            {
                new Error("FileError", "Give existing simply file in arguments\n - File Gived : "+argv[0], e);
            }
        }
        else
            new Error("FileError", "Give existing simply file in arguments.\n - File Gived : null");
    }

    private static void compile(File file, String program) {
        String filename = file.getPath().replace(".sy", ".class");
        try
        {
            OutputStream os = new FileOutputStream(filename);
            os.write(ByteCodeGenerator.generate(Parser.parse(program), file.getName().replace(".sy", "")));
            os.close();
        }
        catch(IOException e)
        {
            new Error("FileError", "Cannot write class field.\n - File Gived : "+filename, e);
        }
        System.out.println("Program Compiled");
    }
}
