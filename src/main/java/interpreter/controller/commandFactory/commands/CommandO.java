package interpreter.controller.commandFactory.commands;

import interpreter.interpreter.Interpreter;
import interpreter.interpreter.InterpreterException;
import interpreter.interpreter.Logger;
import interpreter.interpreter.StringPos;
import interpreter.interpreter.instructionFactory.instructions.InstructionException;

import javax.swing.*;

public class CommandO implements Command {
    @Override
    public void execute(JTextPane consolePane, JTextPane editorPane, Interpreter interpreter, Logger logger) throws InstructionException, InterpreterException, CommandException {
        if (interpreter == null) {
            throw new CommandException( "The program is not running" );
        }
        StringPos stringPos = interpreter.executeOver();
        if (stringPos.getString() != null) {
            consolePane.setText( consolePane.getText() + stringPos.getString() + "\n" );
        }
    }
}
