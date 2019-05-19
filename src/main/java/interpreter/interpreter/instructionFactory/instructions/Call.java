package interpreter.interpreter.instructionFactory.instructions;

import interpreter.interpreter.variables.Variable;

import java.util.Map;

public class Call extends Instruction {
    private static final int INPUT_VAR_COUNT = 1;

    public Call(String[] vars) {
        super( vars );
    }

    @Override
    public InstructionReturnValue execute(Map<String, Variable> variables) throws InstructionException {
        if(vars.length != INPUT_VAR_COUNT + 1){ // + 1 because var[0] - instruction name
            throw new InstructionException( "Wrong arguments count" );
        }
        return new InstructionReturnValue( vars[1], null );
    }
}
