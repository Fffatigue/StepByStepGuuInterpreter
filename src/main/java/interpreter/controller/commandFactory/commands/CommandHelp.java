package interpreter.controller.commandFactory.commands;

import interpreter.interpreter.Interpreter;
import interpreter.interpreter.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandHelp implements Command {
    private static final String HELP_PATH = "help.txt";

    @Override
    public void execute(JTextPane consolePane, JTextPane editorPane, Interpreter interpreter, Logger logger) throws CommandException {
        try (InputStream is = getClass().getResourceAsStream( HELP_PATH )) {
            if (is != null) {
                try (BufferedReader br = new BufferedReader( new InputStreamReader( is ) )) {
                    String s;
                    StringBuilder sb = new StringBuilder();
                    while ((s = br.readLine()) != null) {
                        sb.append( s ).append( "\n" );
                    }
                    consolePane.setText( consolePane.getText() + sb.toString() );
                }
            }
        } catch (IOException e) {
            throw new CommandException( "Something wrong with help file" );
        }
    }
}
