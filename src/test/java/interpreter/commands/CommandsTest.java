package interpreter.commands;

import interpreter.controller.commandFactory.CommandFactory;
import interpreter.controller.commandFactory.commands.*;
import interpreter.interpreter.Interpreter;
import interpreter.interpreter.InterpreterException;
import interpreter.interpreter.Logger;
import interpreter.interpreter.StringPos;
import interpreter.interpreter.instructionFactory.instructions.InstructionException;
import interpreter.interpreter.variables.StringVar;
import interpreter.interpreter.variables.Variable;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CommandsTest {
    private Interpreter interpreter;
    private Logger logger = new Logger();
    private final JTextPane consolePane = new JTextPane();
    private final JTextPane editorPane = new JTextPane();

    @Before
    public void clearPanes() {
        consolePane.setText( "" );
        editorPane.setText( "" );
    }

    @Test
    public void testCommandI() throws InstructionException, InterpreterException, CommandException {
        Command commandI = new CommandI();
        commandI.execute( consolePane, editorPane, interpreter, logger );
        final String expected = "checkInto\n";
        final String actual = consolePane.getText();
        assertEquals( expected, actual );
    }

    @Test
    public void testCommandO() throws InstructionException, InterpreterException, CommandException {
        Command commandO = new CommandO();
        commandO.execute( consolePane, editorPane, interpreter, logger );
        final String expected = "checkOver\n";
        final String actual = consolePane.getText();
        assertEquals( expected, actual );
    }


    @Test
    public void testCommandVar() throws InstructionException, InterpreterException, CommandException {
        Command commandVar = new CommandVar();
        commandVar.execute( consolePane, editorPane, interpreter, logger );
        final String expected = "variables : \na 5\nb 6\n";
        final String actual = consolePane.getText();
        assertEquals( expected, actual );
    }

    @Test
    public void testCommandTrace() throws InstructionException, InterpreterException, CommandException {
        Command commandTrace = new CommandTrace();
        logger.addFunctionCall( 1, "check1", 1 );
        logger.addFunctionCall( 2, "check2", 2 );
        commandTrace.execute( consolePane, editorPane, interpreter, logger );
        final String expected = "Stack trace : \n" + logger.getTrace();
        final String actual = consolePane.getText();
        assertEquals( expected, actual );
    }

    @Test
    public void testCommandFactory() throws IOException, ClassNotFoundException {
        Command i = CommandFactory.getInstance().getCommand( "i" );
        Command o = CommandFactory.getInstance().getCommand( "o" );
        Command var = CommandFactory.getInstance().getCommand( "var" );
        Command trace = CommandFactory.getInstance().getCommand( "trace" );
        assertTrue( i instanceof CommandI );
        assertTrue( o instanceof CommandO );
        assertTrue( var instanceof CommandVar );
        assertTrue( trace instanceof CommandTrace );
    }


    {
        try {
            interpreter = new Interpreter( logger, new StringReader( "sub main" ) ) {
                @Override
                public StringPos executeInto() {
                    return new StringPos( "checkInto", 1 );
                }

                @Override
                public StringPos executeOver() {
                    return new StringPos( "checkOver", 1 );
                }

                @Override
                public Map<String, Variable> getVariables() {
                    Map<String, Variable> variableMap = new HashMap<>();
                    variableMap.put( "a", new StringVar( "5" ) );
                    variableMap.put( "b", new StringVar( "6" ) );
                    return variableMap;
                }
            };
        } catch (Exception ignored) {
        }
    }


}

