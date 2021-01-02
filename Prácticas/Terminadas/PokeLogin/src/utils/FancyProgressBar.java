package utils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class FancyProgressBar extends BasicProgressBarUI {

        @Override
        protected Dimension getPreferredInnerVertical() {
            return new Dimension(20, 146);
        }

        @Override
        protected Dimension getPreferredInnerHorizontal() {
            return new Dimension(146, 20);
        }



        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {

            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int iStrokWidth = 3;
            g2d.setStroke(new BasicStroke(iStrokWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setBackground(progressBar.getBackground());

            int width = progressBar.getWidth();
            int height = progressBar.getHeight();

            RoundRectangle2D outline = new RoundRectangle2D.Double((iStrokWidth / 2), (iStrokWidth / 2),
                    width - iStrokWidth, height - iStrokWidth,
                    height, height);
            
            g2d.setColor(Color.gray);
            g2d.draw(outline);
            
            int iInnerHeight = height - (iStrokWidth * 4);
            int iInnerWidth = width - (iStrokWidth * 4);

            double dProgress = progressBar.getPercentComplete();
            if (dProgress < 0) {
                dProgress = 0;
            } else if (dProgress > 1) {
                dProgress = 1;
            }

            iInnerWidth = (int) Math.round(iInnerWidth * dProgress);

            RoundRectangle2D fill = new RoundRectangle2D.Double(iStrokWidth * 2, iStrokWidth * 2,
                    iInnerWidth, iInnerHeight, iInnerHeight, iInnerHeight);
            RoundRectangle2D fillFull = new RoundRectangle2D.Double(iStrokWidth * 2, iStrokWidth * 2,
            		187, iInnerHeight, iInnerHeight, iInnerHeight);

            g2d.setColor(Color.white);
            g2d.fill(fillFull);
            g2d.setColor(new Color(48, 167, 215));
            g2d.fill(fill);

            g2d.dispose();
        }

        @Override
        protected void paintIndeterminate(Graphics g, JComponent c) {
            super.paintIndeterminate(g, c); //To change body of generated methods, choose Tools | Templates.
        }

    }
