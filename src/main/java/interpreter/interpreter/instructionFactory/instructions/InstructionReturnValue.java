package interpreter.interpreter.instructionFactory.instructions;

public class InstructionReturnValue {
    private final String calledFunction;
    private final String returnStatement;

    InstructionReturnValue(String calledFunction, String returnStatement) {
        this.calledFunction = calledFunction;
        this.returnStatement = returnStatement;
    }

    public String getCalledFunction() {
        return calledFunction;
    }

    public String getReturnStatement() {
        return returnStatement;
    }
}
