package com.mycompany.atm.spg.unab;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Layout manager que coloca componentes en capas usando posiciones relativas
 * dentro del área visible de un diseño base.
 */
public class RelativeOverlayLayout implements LayoutManager2 {

    private final Dimension designSize;
    private final Map<Component, RelativeConstraints> constraintsByComponent = new LinkedHashMap<>();
    private final Map<Component, Font> baseFonts = new LinkedHashMap<>();

    public RelativeOverlayLayout(Dimension designSize) {
        this.designSize = new Dimension(designSize);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (!(constraints instanceof RelativeConstraints rc)) {
            throw new IllegalArgumentException("Use RelativeConstraints para agregar componentes.");
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
        // No-op
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
        double scale = Math.min((double) renderBounds.width / designSize.width,
                (double) renderBounds.height / designSize.height);

        for (Map.Entry<Component, RelativeConstraints> entry : constraintsByComponent.entrySet()) {
            Component comp = entry.getKey();
            RelativeConstraints rc = entry.getValue();

            int x = renderBounds.x + (int) Math.round(renderBounds.width * rc.x());
            int y = renderBounds.y + (int) Math.round(renderBounds.height * rc.y());
            int w = (int) Math.round(renderBounds.width * rc.width());
            int h = (int) Math.round(renderBounds.height * rc.height());

            comp.setBounds(x, y, w, h);

            Font base = baseFonts.get(comp);
            if (base != null) {
                float newSize = (float) Math.max(11.0, base.getSize2D() * scale);
                comp.setFont(base.deriveFont(newSize));
            }
        }
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
