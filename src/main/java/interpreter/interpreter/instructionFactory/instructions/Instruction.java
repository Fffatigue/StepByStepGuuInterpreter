package interpreter.interpreter.instructionFactory.instructions;

import interpreter.interpreter.variables.Variable;

import java.util.Map;

public abstract class Instruction {
    String[] vars;

    Instruction(String[] vars) {
        this.vars = vars;
    }

    public abstract InstructionReturnValue execute(Map<String, Variable> variables) throws InstructionException;
}
