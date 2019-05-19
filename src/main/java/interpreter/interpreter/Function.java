package interpreter.interpreter;

import interpreter.interpreter.instructionFactory.instructions.Instruction;
import interpreter.interpreter.instructionFactory.instructions.InstructionException;
import interpreter.interpreter.instructionFactory.instructions.InstructionReturnValue;
import interpreter.interpreter.variables.Variable;

import java.util.List;
import java.util.Map;

class Function {
    private List<Instruction> instructions;
    private Map<String, FunctionPos> functions;
    private Map<String, Variable> variables;
    private final int beginPos;
    private int pos = -1;
    private Function currentCalledFunction = null;

    Function(List<Instruction> instructions, Map<String, FunctionPos> functions, int beginPos, Map<String, Variable> variables) {
        this.variables = variables;
        this.beginPos = beginPos;
        this.instructions = instructions;
        this.functions = functions;
    }

    StringPos executeInto(Logger logger, int depth) throws InstructionException {
        if (pos == -1) { //-1 means that we only made a function call and now we are on the line with its declaration
            ++pos;
            return new StringPos( null, pos );
        }
        if (currentCalledFunction != null) {
            String returnValue = currentCalledFunction.executeInto( logger, depth + 1 ).getString();
            if (currentCalledFunction.isFunctionEnd()) {
                currentCalledFunction = null;
            }
            return new StringPos( returnValue, pos );
        }
        InstructionReturnValue irv = instructions.get( pos ).execute( variables );
        if (irv.getCalledFunction() != null) { //if instructions calls another function
            logger.addFunctionCall( beginPos + pos + 1, irv.getCalledFunction(), depth );
            FunctionPos fp = functions.get( irv.getCalledFunction() );
            if (fp == null) {
                throw new InstructionException( "Unknown function : " + irv.getCalledFunction() );
            }
            currentCalledFunction = new Function( fp.getFunction(), functions, fp.getPos(), variables );
        }
        ++pos;
        return new StringPos( irv.getReturnStatement(), pos );

    }

    StringPos executeOver(Logger logger, int depth) throws InstructionException {
        if (currentCalledFunction != null) {
            String returnStatement = currentCalledFunction.executeOver( logger, depth + 1 ).getString();
            if (currentCalledFunction.isFunctionEnd()) {
                currentCalledFunction = null;
            }
            return new StringPos( returnStatement, pos );
        }
        StringPos stringPos = executeInto( logger, depth );
        if (currentCalledFunction != null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (stringPos.getString() != null) {
                stringBuilder.append( stringPos );
            }
            while (currentCalledFunction!=null) {
                String s = executeInto( logger, depth ).getString();
                if (s != null) {
                    stringBuilder.append( s );
                }
            }
            return new StringPos( stringBuilder.toString(), pos );
        }
        return stringPos;
    }

    boolean isFunctionEnd() {
        if (currentCalledFunction == null) {
            return pos == instructions.size();
        } else {
            return false;
        }
    }

    int getCurrentPos() {
        if (currentCalledFunction != null) {
            return currentCalledFunction.getCurrentPos();
        }
        return beginPos + pos;
    }
}
