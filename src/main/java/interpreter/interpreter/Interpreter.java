package interpreter.interpreter;


import interpreter.controller.commandFactory.commands.CommandException;
import interpreter.interpreter.instructionFactory.instructions.Instruction;
import interpreter.interpreter.instructionFactory.InstructionFactory;
import interpreter.interpreter.instructionFactory.instructions.InstructionException;
import interpreter.interpreter.variables.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private Function mainFunction;
    private Logger logger;
    private Map<String, Variable> variables = new HashMap<>();

    private static final String FUNCTION_BEGIN_INSTRUCTION = "sub";
    private static final String MAIN_FUNCTION_NAME = "main";

    public Interpreter(Logger logger, Reader r) throws IOException, ClassNotFoundException, InterpreterException {
        this.logger = logger;
        mainFunction = parse( r );
    }

    /**
     * @return -1 if program terminated, else number of line from 0
     */
    public int getCurrentPos() {
        if(programTerminated()){
            return -1;
        }
        return mainFunction.getCurrentPos();
    }

    public StringPos executeInto() throws InstructionException, CommandException {
        if (programTerminated()) {
            throw new CommandException( "The program has finished working" );
        }
        return mainFunction.executeInto( logger, 1 );
    }

    public StringPos executeOver() throws InstructionException, CommandException {
        if (programTerminated()) {
            throw new CommandException( "The program has finished working" );
        }
        return mainFunction.executeOver( logger, 1 );
    }

    private boolean programTerminated() {
        return mainFunction.isFunctionEnd();
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }


    private Function parse(Reader r) throws IOException, ClassNotFoundException, InterpreterException {
        Map<String, FunctionPos> functions = new HashMap<>();
        BufferedReader br = new BufferedReader( r );

        int currentFunctionBegin = 0;
        String currentFunctionName = null;
        List<Instruction> currentFunctionInstructionList = null;
        int instructionPos = 0;
        String str;
        while ((str = br.readLine()) != null) {
            String[] splited = getInstruction( str );
            ++instructionPos;
            if (splited == null) {
                continue;
            }
            if (splited[0].equals( FUNCTION_BEGIN_INSTRUCTION )) {
                if (currentFunctionName != null) {
                    functions.put( currentFunctionName, new FunctionPos( currentFunctionBegin, currentFunctionInstructionList ) );
                }
                currentFunctionInstructionList = new ArrayList<>();
                currentFunctionName = splited[1];
                currentFunctionBegin = instructionPos;
                continue;
            }
            if (currentFunctionName == null) {
                throw new InterpreterException( "The program should begin with \"sub <function name>\"" );
            }
            currentFunctionInstructionList.add( InstructionFactory.getInstance().getInstruction( splited ) );
        }

        if (currentFunctionName == null) {
            throw new InterpreterException( "The program should begin with \"sub <function name>\"" );
        }

        functions.put( currentFunctionName, new FunctionPos( currentFunctionBegin, currentFunctionInstructionList ) );

        FunctionPos fp = functions.get( MAIN_FUNCTION_NAME );
        if (fp == null) {
            throw new InterpreterException( "There is no \"main\" function in the program" );
        }
        logger.addFunctionCall( fp.getPos(), MAIN_FUNCTION_NAME, 0 );
        return new Function( fp.getFunction(), functions, fp.getPos(), variables );
    }

    private String[] getInstruction(String st) {
        String[] instructions = st.trim().split( "\\s+" );
        if (instructions[0].equals( "" )) {
            return null;
        }
        return instructions;
    }
}
