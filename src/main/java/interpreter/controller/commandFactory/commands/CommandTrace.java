package interpreter.controller.commandFactory.commands;

import interpreter.interpreter.Interpreter;
import interpreter.interpreter.Logger;

import javax.swing.*;

public class CommandTrace implements Command{
    @Override
    public void execute(JTextPane consolePane, JTextPane editorPane, Interpreter interpreter, Logger logger) throws CommandException {
        if (interpreter == null) {
            throw new CommandException( "The program is not running" );
        }
        consolePane.setText( consolePane.getText() + "Stack trace : \n" + logger.getTrace());
    }
}
