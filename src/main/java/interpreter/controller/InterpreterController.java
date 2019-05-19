package interpreter.controller;

import interpreter.controller.commandFactory.CommandFactory;
import interpreter.controller.commandFactory.commands.CommandException;
import interpreter.interpreter.Interpreter;
import interpreter.interpreter.InterpreterException;
import interpreter.interpreter.Logger;
import interpreter.interpreter.instructionFactory.instructions.InstructionException;
import interpreter.view.LineNumbersView;

import javax.swing.*;
import java.io.IOException;
import java.io.StringReader;

public class InterpreterController {
    private JTextPane consolePane;
    private JTextPane editorPane;
    private Interpreter interpreter = null;
    private Logger logger = null;
    private LineNumbersView lineNumbersView;

    public InterpreterController(JTextPane consolePane, JTextPane editorPane, LineNumbersView lineNumbersView) {
        this.lineNumbersView = lineNumbersView;
        this.consolePane = consolePane;
        this.editorPane = editorPane;
    }

    public void run() throws IOException, ClassNotFoundException, InterpreterException {
        logger = new Logger();
        interpreter = new Interpreter( logger, new StringReader( editorPane.getText() ) );
        lineNumbersView.setSelectedLine( String.valueOf( interpreter.getCurrentPos() + 1 ) );
    }

    public void stop() {
        lineNumbersView.setSelectedLine( null );
        logger = null;
        interpreter = null;
    }

    public void executeCommand(String command) throws IOException, ClassNotFoundException, InstructionException, InterpreterException, CommandException {
        CommandFactory.getInstance().getCommand( command ).execute( consolePane, editorPane, interpreter, logger );
        if (interpreter != null) {
            lineNumbersView.setSelectedLine( String.valueOf( interpreter.getCurrentPos() + 1 ) );
        }
    }
}
