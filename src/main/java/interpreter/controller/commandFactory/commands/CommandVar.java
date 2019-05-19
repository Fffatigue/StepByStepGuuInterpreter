package interpreter.controller.commandFactory.commands;

import interpreter.interpreter.Interpreter;
import interpreter.interpreter.Logger;

import javax.swing.*;

public class CommandVar implements Command{

    @Override
    public void execute(JTextPane consolePane, JTextPane editorPane, Interpreter interpreter, Logger logger) throws CommandException {
        if (interpreter == null) {
            throw new CommandException( "The program is not running" );
        }
        StringBuilder sb = new StringBuilder(  ).append( "variables : \n" );
        interpreter.getVariables().forEach( (k,v)->sb.append( k ).append( " " ).append( v.printVariable() ).append( "\n" ) );
        consolePane.setText( consolePane.getText() + sb.toString());
    }
}
