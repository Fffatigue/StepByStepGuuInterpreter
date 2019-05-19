package interpreter.interpreter.variables;

public abstract class Variable<T> {

    private T var;

    Variable(T var) {
        this.var = var;
    }

    T getVariable() {
        return var;
    }

    public void setVariable(T var) {
        this.var = var;
    }

    public abstract String printVariable();
}
