package com.mycompany.atm.spg.unab;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondoResponsivo extends JPanel {

    private final Dimension designSize;
    private final Image backgroundImage;

    public PanelFondoResponsivo(String resourcePath, int designWidth, int designHeight) {
        this.designSize = new Dimension(designWidth, designHeight);
        URL resource = getClass().getResource(resourcePath);
        if (resource == null) {
            throw new IllegalArgumentException("No se encontró el recurso: " + resourcePath);
        }
        this.backgroundImage = new ImageIcon(resource).getImage();
        setLayout(new DisenoSuperpuestoRelativo(designSize));
        setOpaque(true);
    }

    public Rectangle getRenderBounds() {
        return DisenoSuperpuestoRelativo.computeCoverBounds(getWidth(), getHeight(), designSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        Rectangle bounds = getRenderBounds();
        g2.drawImage(backgroundImage, bounds.x, bounds.y, bounds.width, bounds.height, this);
        g2.dispose();
    }
}
