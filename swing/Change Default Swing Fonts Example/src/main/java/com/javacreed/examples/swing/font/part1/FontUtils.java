package com.javacreed.examples.swing.font.part1;

import java.util.Map.Entry;

import javax.swing.UIDefaults.ActiveValue;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class FontUtils {

    public static void adjustDefaultFontSize(final float adjustment) {
        for (final Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
            final Object key = entry.getKey();
            if (key instanceof String && ((String) key).toLowerCase().contains("font")) {
                final Object value = entry.getValue();
                if (value instanceof FontUIResource) {
                    final FontUIResource fur = (FontUIResource) value;
                    final FontUIResource adjusted = new FontUIResource(fur.deriveFont(fur.getSize2D() + adjustment));
                    entry.setValue(adjusted);
                } else if (value instanceof ActiveValue) {
                    final ActiveValue av = (ActiveValue) value;
                    final FontUIResource fur = (FontUIResource) av.createValue(null);
                    final FontUIResource adjusted = new FontUIResource(fur.deriveFont(fur.getSize2D() + adjustment));
                    entry.setValue(adjusted);
                }
            }
        }
    }

    private FontUtils() {}

}
