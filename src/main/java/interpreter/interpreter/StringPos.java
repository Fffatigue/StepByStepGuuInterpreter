package interpreter.interpreter;

public class StringPos {
    private final String string;
    private final int pos;

    public StringPos(String string, int pos) {
        this.string = string;
        this.pos = pos;
    }

    public String getString() {
        return string;
    }

    public int getPos() {
        return pos;
    }
}
