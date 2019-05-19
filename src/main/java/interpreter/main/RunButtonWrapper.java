package interpreter.main;

import interpreter.view.InterpreterPanel;

import javax.swing.*;

public class RunButtonWrapper {
    private boolean run;
    private JToggleButton runButton;
    private InterpreterPanel interpreterPanel;

    RunButtonWrapper(boolean run, JToggleButton runToggleButton, InterpreterPanel interpreterPanel) {
        this.run = run;
        this.runButton = runToggleButton;
        this.interpreterPanel = interpreterPanel;
    }

    public void onRun(boolean run){
        if(this.run!=run){
            onRun();
        }
    }

    void onRun(){
        run = !run;
        runButton.setSelected( run );
        interpreterPanel.runInterpretation( run );
    }
}
