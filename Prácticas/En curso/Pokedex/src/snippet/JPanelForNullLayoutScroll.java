package snippet;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class JPanelForNullLayoutScroll extends JPanel {

    int h;

    @Override
    public Dimension getPreferredSize() {

        if (getComponentCount() > 0) {
            h = 0;

            for (Component c : getComponents()) {
                h += c.getHeight();
            }

        } else {
            h = 0;
        }

        // as I do not need width recount
        //I set it to be taken directly from the component itself
        Dimension d = new Dimension(getWidth(), h);

        return d;
    }

}