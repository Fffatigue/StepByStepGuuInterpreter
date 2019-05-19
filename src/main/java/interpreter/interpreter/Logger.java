package interpreter.interpreter;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Logger {
    private class FunctionCall {
        private final int pos;
        private final String functionName;
        private int depth;

        FunctionCall(int pos, String functionName, int depth) {
            this.pos = pos;
            this.functionName = functionName;
            this.depth = depth;
        }
    }

    private List<FunctionCall> functionCalls = new ArrayList<>();

    public void addFunctionCall(int pos, String functionName, int depth) {
        functionCalls.add( new FunctionCall( pos, functionName, depth ) );
    }

    public String getTrace() {
        StringBuilder stringBuilder = new StringBuilder();
        for (FunctionCall call : functionCalls) {
            stringBuilder.append( IntStream.range( 0, call.depth ).mapToObj( i -> "-" )
                    .collect( Collectors.joining( "" ) ) ).append( " " );
            stringBuilder.append( call.pos ).append( " : " ).append( call.functionName ).append( "\n" );
        }
        return stringBuilder.toString();
    }
}
