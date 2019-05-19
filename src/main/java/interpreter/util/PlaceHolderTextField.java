package interpreter.util;

import javax.swing.*;
import java.awt.*;

public class PlaceHolderTextField extends JTextField {
    private String placeholder;

    public PlaceHolderTextField(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent( pG );

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        g.setColor( getDisabledTextColor() );
        g.drawString( placeholder, getInsets().left, pG.getFontMetrics()
                .getMaxAscent() + getInsets().top );
    }
}
