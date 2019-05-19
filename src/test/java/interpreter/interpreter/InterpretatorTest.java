package interpreter.interpreter;

import interpreter.interpreter.instructionFactory.instructions.InstructionException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class InterpretatorTest {
    private static final Reader r = new StringReader( "sub main\n" +
            "    set a 1\n" +
            "    call foo\n" +
            "    print a\n" +
            "\n" +
            "sub foo\n" +
            "    set a 2" );

    private Logger logger;

    @Before
    public void initLogger() {
        logger = new Logger();
    }

    @Test
    public void testInterpreterStepOver() throws InterpreterException, IOException, ClassNotFoundException, InstructionException {
        Interpreter interpreter = new Interpreter( logger, r );
        interpreter.executeInto();
        interpreter.executeInto();
        interpreter.executeOver();
        final String actual = interpreter.getVariables().get( "a" ).printVariable();
        final String expected = "2";
        assertEquals( actual, expected );
    }

    @Test
    public void testInterpreterStepInto() throws InterpreterException, IOException, ClassNotFoundException, InstructionException {
        Interpreter interpreter = new Interpreter( logger, r );
        interpreter.executeInto();
        interpreter.executeInto();
        final String actual = interpreter.getVariables().get( "a" ).printVariable();
        final String expected = "1";
        assertEquals( expected, actual );
    }

    @Test
    public void testInterpreterGetPos() throws InterpreterException, IOException, ClassNotFoundException, InstructionException {
        Interpreter interpreter = new Interpreter( logger, r );
        int actual = interpreter.getCurrentPos();
        int expected = 0;
        assertEquals( expected, actual );
        interpreter.executeInto();
        actual = interpreter.getCurrentPos();
        expected = 1;
        assertEquals( expected, actual );
        interpreter.executeInto();
        interpreter.executeOver();
        actual = interpreter.getCurrentPos();
        expected = 3;
        assertEquals( expected, actual );
    }
}
