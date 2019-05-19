package interpreter.interpreter.instructionFactory;

import interpreter.interpreter.instructionFactory.instructions.Instruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class InstructionFactory {
    public static InstructionFactory getInstance() throws IOException {
        if (f == null)
            f = new InstructionFactory();
        return f;
    }

    public Instruction getInstruction(String[] splited) throws ClassNotFoundException {
        String id = splited[0];
        Class<?> c;
        Object instruction;
        try {
            c = Class.forName( instructions.getProperty( id ) );
            instruction = c.getDeclaredConstructor( String[].class ).newInstance( (Object) splited );
        } catch (Exception exc) {
            throw new ClassNotFoundException( "Unknown instruction : " + id );
        }
        return (Instruction) instruction;
    }

    private InstructionFactory() throws IOException {
        instructions = new Properties();
        try (InputStream is = getClass().getResourceAsStream( CONFIG_FILE_NAME )) {
            if (is != null) {
                try (BufferedReader br = new BufferedReader( new InputStreamReader( is ) )) {
                    instructions.load( br );
                } catch (IOException e) {
                    throw new IOException( "Missing config file!" );
                }
            }
        }
    }

    private static final String CONFIG_FILE_NAME = "instructions/config.properties";
    private static InstructionFactory f = null;
    private static Properties instructions;
}
