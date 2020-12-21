package foo1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

@SuppressWarnings("serial")
public class GraphicsEg extends JPanel {
    private static final int PREF_W = 400;
    private static final int PREF_H = PREF_W;
    private static final Color SELECTED_COLOR = Color.RED;
    private static final Stroke SELECTED_STROKE = new BasicStroke(8f);
    private List<Shape> shapes = new ArrayList<>();
    private Map<Shape, Color> shapeColorMap = new HashMap<>();
    private Shape selectedShape = null;

    public GraphicsEg() {
        Shape shape = new Ellipse2D.Double(10, 10, 90, 90);
        shapeColorMap.put(shape, Color.GRAY);
        shapes.add(shape);

        shape = new Rectangle2D.Double(140, 140, 200, 200);
        shapeColorMap.put(shape, Color.BLUE);
        shapes.add(shape);

        shape = new RoundRectangle2D.Double(200, 200, 80, 80, 10, 10);
        shapeColorMap.put(shape, Color.GREEN);
        shapes.add(shape);

        addMouseListener(new MyMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape shape : shapes) {
            Color color = shapeColorMap.get(shape);
            g2.setColor(color);
            g2.fill(shape);
        }

        if (selectedShape != null) {
            Graphics2D newG2 = (Graphics2D) g2.create();
            newG2.setColor(SELECTED_COLOR);
            newG2.setStroke(SELECTED_STROKE);
            newG2.draw(selectedShape);
            newG2.dispose(); // because this is a created Graphics object
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(PREF_W, PREF_H);
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (shapes.get(i).contains(e.getPoint())) {
                    selectedShape = shapes.get(i);
                    repaint();
                    return;
                }
            }
        }
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("GraphicsEg");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GraphicsEg());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}