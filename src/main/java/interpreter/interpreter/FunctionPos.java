package interpreter.interpreter;

import interpreter.interpreter.instructionFactory.instructions.Instruction;

import java.util.List;

class FunctionPos {
    private final int pos;
    private final List<Instruction> function;

    FunctionPos(int pos, List<Instruction> function) {
        this.pos = pos;
        this.function = function;
    }

    int getPos() {
        return pos;
    }

    List<Instruction> getFunction() {
        return function;
    }
}
