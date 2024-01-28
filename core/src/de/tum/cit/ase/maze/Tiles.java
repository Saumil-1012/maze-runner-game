package de.tum.cit.ase.maze;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Tiles extends JFrame {

    private final int tileSize = 32; // size of each tile
    private final int rows = 10;
    private final int cols = 15;

    public Tiles() {
        setTitle("Background Tiles Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel and set a custom background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBackgroundTiles(g);
            }
        };

        add(panel);

        setSize(cols * tileSize, rows * tileSize);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void drawBackgroundTiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Create a BufferedImage for the tile
        BufferedImage tileImage = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D tileGraphics = tileImage.createGraphics();
        tileGraphics.setColor(Color.LIGHT_GRAY);
        tileGraphics.fillRect(0, 0, tileSize, tileSize);
        tileGraphics.setColor(Color.BLACK);
        tileGraphics.drawRect(0, 0, tileSize - 1, tileSize - 1);
        tileGraphics.dispose();

        // Draw the tiles on the panel
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * tileSize;
                int y = row * tileSize;
                g2d.drawImage(tileImage, x, y, this);
            }
        }
    }
}
