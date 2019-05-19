package interpreter.view;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;

public class LineNumbersView extends JComponent implements DocumentListener, CaretListener, ComponentListener {
    private static final int MARGIN_WIDTH_PX = 28;

    private JTextComponent editor;

    private Font font;

    private String selectedLine = null;

    LineNumbersView(JTextComponent editor) {
        this.editor = editor;

        editor.getDocument().addDocumentListener( this );
        editor.addComponentListener( this );
        editor.addCaretListener( this );
    }

    public void setSelectedLine(String selectedLine) {
        this.selectedLine = selectedLine;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent( g );

        Rectangle clip = g.getClipBounds();
        int startOffset = editor.viewToModel2D( new Point( 0, clip.y ) );
        int endOffset = editor.viewToModel2D( new Point( 0, clip.y + clip.height ) );

        while (startOffset <= endOffset) {
            try {
                String lineNumber = getLineNumber( startOffset );
                if (lineNumber != null) {
                    int x = getInsets().left + 2;
                    int y = getOffsetY( startOffset );

                    font = font != null ? font : new Font( Font.MONOSPACED, Font.BOLD, editor.getFont().getSize() );
                    g.setFont( font );
                    g.setColor( lineNumber.trim().equals( selectedLine ) ? Color.RED : Color.BLACK );

                    g.drawString( lineNumber, x, y );
                }

                startOffset = Utilities.getRowEnd( editor, startOffset ) + 1;
            } catch (BadLocationException e) {
                e.printStackTrace();
                // ignore and continue
            }
        }
    }

    /**
     * Returns the line number of the element based on the given (start) offset
     * in the editor model. Returns null if no line number should or could be
     * provided (e.g. for wrapped lines).
     */
    private String getLineNumber(int offset) {
        Element root = editor.getDocument().getDefaultRootElement();
        int index = root.getElementIndex( offset );
        Element line = root.getElement( index );

        return line.getStartOffset() == offset ? String.format( "%3d", index + 1 ) : null;
    }

    /**
     * Returns the y axis position for the line number belonging to the element
     * at the given (start) offset in the model.
     */
    private int getOffsetY(int offset) throws BadLocationException {
        FontMetrics fontMetrics = editor.getFontMetrics( editor.getFont() );
        int descent = fontMetrics.getDescent();

        Rectangle2D r = editor.modelToView2D( offset );

        return (int)(r.getY() + r.getHeight()) - descent;
    }

    /**
     * Schedules a refresh of the line number margin on a separate thread.
     */
    private void documentChanged() {
        SwingUtilities.invokeLater( this::repaint );
    }

    /**
     * Updates the size of the line number margin based on the editor height.
     */
    private void updateSize() {
        Dimension size = new Dimension( MARGIN_WIDTH_PX, editor.getHeight() );
        setPreferredSize( size );
        setSize( size );
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        documentChanged();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        updateSize();
        documentChanged();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
        updateSize();
        documentChanged();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
