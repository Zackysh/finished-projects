package utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test {
  public static void main(String[] args) {
    JFrame f = new JFrame();
    JTextField tf = new JTextField();
    JButton b = new JButton("button");
    tf.setText("abcdefghijklmnop");
    b.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
           int caretPosition = tf.getCaretPosition();
            String currentText = tf.getText();
            String left = currentText.substring(0, caretPosition-1);
            String right = currentText.substring(caretPosition, currentText.length());
            String newText = left + right;
            tf.setText(newText);
            int caretPosition2 = tf.getCaretPosition();
            tf.requestFocus();
            tf.setCaretPosition(caretPosition2);
        }
    });
    JPanel p = (JPanel)f.getContentPane();
    p.setLayout(new BorderLayout());
    p.add(b, BorderLayout.EAST);
    p.add(tf, BorderLayout.CENTER);
    f.setSize(640, 400);
    f.setVisible(true);
  }
}