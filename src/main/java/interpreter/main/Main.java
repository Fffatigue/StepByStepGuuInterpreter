package interpreter.main;

import interpreter.view.InterpreterPanel;
import interpreter.view.MainFrame;

import java.awt.event.KeyEvent;
import java.io.*;

import javax.swing.*;

public class Main extends MainFrame {
    private boolean run = false;
    private RunButtonWrapper runButtonWrapper;
    private InterpreterPanel interpreterPanel = new InterpreterPanel();

    private Main() {
        super( 800, 800, "Step by step Guu interpreter" );

        try {
            addSubMenu( "File", KeyEvent.VK_F );
            addMenuItem( "File/New", "Create new file", KeyEvent.VK_N, "new.png", "onNew" );
            addMenuItem( "File/Open", "Open file", KeyEvent.VK_L, "open.png", "onOpen" );
            addMenuItem( "File/Save", "Save file", KeyEvent.VK_S, "save.png", "onSave" );
            addMenuItem( "File/Exit", "Exit application", KeyEvent.VK_X, "exit.png", "onExit" );
            addSubMenu( "Tools", KeyEvent.VK_T );
            addMenuItem( "Tools/Run", "Run application", KeyEvent.VK_R, "run.png", "onRun" );

            addSubMenu( "Help", KeyEvent.VK_H );
            addMenuItem( "Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "about.png", "onAbout" );

            addToolBarButton( "File/New" );
            addToolBarButton( "File/Open" );
            addToolBarButton( "File/Save" );
            addToolBarButton( "File/Exit" );

            addToolBarSeparator();

            JToggleButton runButton = addToolBarToggleButton( "Tools/Run" );

            runButtonWrapper = new RunButtonWrapper( false, runButton, interpreterPanel );

            interpreterPanel.setRunButtonWrapper( runButtonWrapper );

            addToolBarSeparator();

            addToolBarButton( "Help/About..." );

            add( interpreterPanel.getRootPanel() );

            pack();

        } catch (Exception e) {
            throw new RuntimeException( e );
        }
    }

    public void onAbout() {
        JOptionPane.showMessageDialog( this, "Step by step guu interpreter, version 1.0\nCopyright 2019 Aleksandr Chmil, fffatigue@gmail.com", "About Interpreter", JOptionPane.INFORMATION_MESSAGE );
    }

    public void onExit() {
        System.exit( 0 );
    }

    public void onNew() {
        if (run) {
            JOptionPane.showMessageDialog( this, "The program is running, to apply this option you need to stop the program." );
            return;
        }
        interpreterPanel.clear();
    }

    public void onSave() {
        File f = getSaveFileName( "guu", "guu files" );
        if (f == null) {
            return;
        }
        try {
            interpreterPanel.saveProgram( f );
        } catch (IOException e) {
            JOptionPane.showMessageDialog( this, "Something wrong happens : " + e.getMessage() );
        }
    }

    public void onOpen() {
        if (run) {
            JOptionPane.showMessageDialog( this, "The program is running, to apply this option you need to stop the program." );
            return;
        }
        File f = getOpenFileName( "guu", "guu files" );
        if (f == null) {
            return;
        }
        try {
            interpreterPanel.loadProgram( f );
        } catch (IOException e) {
            JOptionPane.showMessageDialog( this, "Something wrong happens : " + e.getMessage() );
        }
    }

    public void onRun() {
        runButtonWrapper.onRun();
    }

    public static void main(String[] args) {
        Main mainFrame = new Main();
        mainFrame.setVisible( true );
    }
}
