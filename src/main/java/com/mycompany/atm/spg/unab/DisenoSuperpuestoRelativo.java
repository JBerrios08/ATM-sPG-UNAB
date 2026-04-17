package com.mycompany.atm.spg.unab;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.LayoutManager2;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JLabel;

public class DisenoSuperpuestoRelativo implements LayoutManager2 {

    private static final float MIN_FONT_SIZE = 10.0f;

    private final Dimension designSize;
    private final Map<Component, RestriccionesRelativas> constraintsByComponent = new LinkedHashMap<>();
    private final Map<Component, Font> baseFonts = new LinkedHashMap<>();

    public DisenoSuperpuestoRelativo(Dimension designSize) {
        this.designSize = new Dimension(designSize);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (!(constraints instanceof RestriccionesRelativas rc)) {
            throw new IllegalArgumentException("Use RestriccionesRelativas para agregar componentes.");
        }
        constraintsByComponent.put(comp, rc);
        if (comp.getFont() != null) {
            baseFonts.put(comp, comp.getFont());
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException("Debe usar constraints relativas.");
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        constraintsByComponent.remove(comp);
        baseFonts.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(designSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(designSize.width / 2, designSize.height / 2);
    }

    @Override
    public void layoutContainer(Container parent) {
        Rectangle renderBounds = computeCoverBounds(parent.getWidth(), parent.getHeight(), designSize);
        double scale = Math.min((double) renderBounds.width / designSize.width, (double) renderBounds.height / designSize.height);

        for (Map.Entry<Component, RestriccionesRelativas> entry : constraintsByComponent.entrySet()) {
            Component comp = entry.getKey();
            RestriccionesRelativas rc = entry.getValue();

            int x = renderBounds.x + (int) Math.round(renderBounds.width * rc.x());
            int y = renderBounds.y + (int) Math.round(renderBounds.height * rc.y());
            int w = Math.max(1, (int) Math.round(renderBounds.width * rc.width()));
            int h = Math.max(1, (int) Math.round(renderBounds.height * rc.height()));

            comp.setBounds(x, y, w, h);

            Font base = baseFonts.get(comp);
            if (base != null) {
                float scaledSize = (float) Math.max(MIN_FONT_SIZE, base.getSize2D() * scale);
                Font scaledFont = base.deriveFont(scaledSize);
                comp.setFont(ajustarFuenteParaContenido(comp, scaledFont, w, h));
            }
        }
    }

    private Font ajustarFuenteParaContenido(Component comp, Font initialFont, int width, int height) {
        String text = extractText(comp);
        if (text == null || text.isBlank()) {
            return initialFont;
        }

        Font current = initialFont;
        int maxTextWidth = Math.max(1, (int) (width * 0.94));
        int maxTextHeight = Math.max(1, (int) (height * 0.90));

        while (current.getSize2D() > MIN_FONT_SIZE) {
            FontMetrics fm = comp.getFontMetrics(current);
            if (fm.stringWidth(text) <= maxTextWidth && fm.getHeight() <= maxTextHeight) {
                return current;
            }
            current = current.deriveFont(Math.max(MIN_FONT_SIZE, current.getSize2D() - 1.0f));
        }

        return current;
    }

    private String extractText(Component comp) {
        if (comp instanceof JLabel label) {
            return sanitizarTexto(label.getText());
        }
        if (comp instanceof AbstractButton button) {
            return sanitizarTexto(button.getText());
        }
        return null;
    }

    private String sanitizarTexto(String rawText) {
        if (rawText == null) {
            return null;
        }
        return rawText.replaceAll("<[^>]*>", "").replace("\n", " ").trim();
    }

    public static Rectangle computeCoverBounds(int containerWidth, int containerHeight, Dimension design) {
        double scale = Math.max((double) containerWidth / design.width, (double) containerHeight / design.height);
        int drawW = (int) Math.round(design.width * scale);
        int drawH = (int) Math.round(design.height * scale);
        int x = (containerWidth - drawW) / 2;
        int y = (containerHeight - drawH) / 2;
        return new Rectangle(x, y, drawW, drawH);
    }
}
