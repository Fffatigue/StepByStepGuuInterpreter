package interpreter.interpreter.instructionFactory.instructions;

import interpreter.interpreter.variables.Variable;

import java.util.Map;

public class Print extends Instruction {
    private static final int INPUT_VAR_COUNT = 1;

    public Print(String[] vars) {
        super( vars );
    }

    @Override
    public InstructionReturnValue execute(Map<String, Variable> variables) throws InstructionException {
        if(vars.length != INPUT_VAR_COUNT + 1){ // + 1 because var[0] - instruction name
            throw new InstructionException( "Wrong arguments count" );
        }
        Variable var = variables.get( vars[1] );
        if(var == null){
            throw new InstructionException("Unknown variable : " + vars[1]);
        }
        return new InstructionReturnValue( null, variables.get( vars[1] ).printVariable() );
    }
}
