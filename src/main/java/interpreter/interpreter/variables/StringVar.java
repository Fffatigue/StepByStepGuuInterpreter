package interpreter.interpreter.variables;

public class StringVar extends Variable<String> {

    public StringVar(String var) {
        super( var );
    }

    @Override
    public String printVariable() {
        return getVariable();
    }
}
