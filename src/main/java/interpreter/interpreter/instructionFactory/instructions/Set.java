package interpreter.interpreter.instructionFactory.instructions;

import interpreter.interpreter.variables.StringVar;
import interpreter.interpreter.variables.Variable;

import java.util.Map;

public class Set extends Instruction {
    private static final int INPUT_VAR_COUNT = 2;

    public Set(String[] vars) {
        super( vars );
    }

    @Override
    public InstructionReturnValue execute(Map<String, Variable> variables) throws InstructionException {
        if (vars.length != INPUT_VAR_COUNT + 1) { // + 1 because var[0] - instruction name
            throw new InstructionException( "Wrong arguments count" );
        }
        variables.put( vars[1], new StringVar( vars[2] ) );
        return new InstructionReturnValue( null, null );
    }
}
