package interpreter.controller.commandFactory.commands;

import interpreter.interpreter.Interpreter;
import interpreter.interpreter.InterpreterException;
import interpreter.interpreter.Logger;
import interpreter.interpreter.instructionFactory.instructions.InstructionException;

import javax.swing.*;

public interface Command {
    void execute(JTextPane consolePane, JTextPane editorPane, Interpreter interpreter, Logger logger) throws InstructionException, InterpreterException, CommandException;
}
