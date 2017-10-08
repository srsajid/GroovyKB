package ui;

import java.awt.*;
import javax.swing.*;

/// creating the Button with custom look
class CButton extends JButton
{

    BasicStroke basicStroke = new BasicStroke(2.0f);
    public CButton(String txt) {
        super(txt);
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(Font.BOLD, 13));
        setContentAreaFilled(false);
        setBorder(null);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0xFFAA00));

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(basicStroke);

        int archH =  (getHeight()-4)/2;
        g2d.drawRoundRect(3, 3, getWidth()-4, getHeight()-4, archH, archH);

        if(getModel().isRollover())
        {
            g2d.fillRoundRect(3, 3, getWidth()-4, getHeight()-4, archH, archH);
            setForeground(Color.black);

        }
        else
        {
            setForeground(Color.white);
        }
        g2d.dispose();

        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

    }



}


