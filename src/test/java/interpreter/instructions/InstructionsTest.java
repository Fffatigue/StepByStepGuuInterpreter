package interpreter.instructions;

import interpreter.interpreter.instructionFactory.InstructionFactory;
import interpreter.interpreter.instructionFactory.instructions.*;
import interpreter.interpreter.variables.StringVar;
import interpreter.interpreter.variables.Variable;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class InstructionsTest {
    private Map<String, Variable> vars = new HashMap<>();

    @Before
    public void clearVars() {
        vars.clear();
        vars.put( "a", new StringVar( "5" ) );
        vars.put( "b", new StringVar( "8" ) );
    }

    @Test
    public void testInstructionPrint() throws InstructionException {
        String[] inputVars = {"print", "a"};
        Instruction instruction = new Print( inputVars );
        InstructionReturnValue irv = instruction.execute( vars );
        final String expected = "5";
        final String actual = irv.getReturnStatement();
        assertEquals( expected, actual );
        assertNull( irv.getCalledFunction() );
    }

    @Test
    public void testInstructionSet() throws InstructionException {
        String[] inputVars = {"set", "a", "10"};
        Instruction instruction = new Set( inputVars );
        InstructionReturnValue irv = instruction.execute( vars );
        final String expected = new StringVar( "10" ).printVariable();
        final String actual = vars.get( "a" ).printVariable();
        assertEquals( expected,actual );
        assertNull( irv.getReturnStatement() );
        assertNull( irv.getCalledFunction() );
    }

    @Test
    public void testInstructionCall() throws InstructionException {
        String[] inputVars = {"call", "func"};
        Instruction instruction = new Call( inputVars );
        InstructionReturnValue irv = instruction.execute( vars );
        final String expected = "func";
        final String actual = irv.getCalledFunction();
        assertEquals( expected,actual );
        assertNull( irv.getReturnStatement() );
    }

    @Test
    public void testInstructionsFactory() throws IOException, ClassNotFoundException {
        String[] str1 = {"call"};
        Instruction call = InstructionFactory.getInstance().getInstruction( str1 );
        String[] str2 = {"print"};
        Instruction print = InstructionFactory.getInstance().getInstruction( str2 );
        String[] str3 = {"set"};
        Instruction set = InstructionFactory.getInstance().getInstruction( str3 );
        assertTrue( call instanceof Call );
        assertTrue( print instanceof Print );
        assertTrue( set instanceof Set );
    }
}
