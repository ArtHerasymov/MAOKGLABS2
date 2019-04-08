package sample;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.geom.AffineTransform;


public class Skeleton extends JPanel implements ActionListener{
    final static int xStart   = 50;
    final static int yStart   = 50;
    final static int bgHeight = 90;
    final static int bgWidth  = 200;
    final static int lineGirth = 5;
    final static int offset = 150;

    private enum DIRECTIONS{LEFT, RIGHT, UP, DOWN};
    private DIRECTIONS direction;


    Timer timer;

    private double tx = 50;
    private double ty = 50;

    private double angle = 0;
    private double radius = 50;

    private double scale = 1;
    private double delta = 0.01;

    private static int maxWidth;
    private static int maxHeight;

    public Skeleton() {
        timer = new Timer(80, this);
        direction = DIRECTIONS.RIGHT;
        timer.start();
    }

    /**
     * Draws picture from lab 1
     * @param g2d
     */
    public void paintPicture(Graphics2D g2d) {
        g2d.setColor(new Color(139, 0, 0));

        g2d.fillRect(xStart, yStart, bgWidth + lineGirth, bgHeight+2*lineGirth);
        g2d.setColor(new Color(255, 255, 0));

        // First vertical line
        g2d.fillRect(xStart+bgWidth/2, yStart, lineGirth, bgHeight/3);

        // Second vertical line
        g2d.fillRect(xStart + bgWidth/2, yStart+(bgHeight*2)/3+lineGirth*2,lineGirth, bgHeight/3);

        // First horizontal line
        g2d.fillRect(xStart, yStart+bgHeight/3, bgWidth+lineGirth,lineGirth);

        // Second horizontal line
        g2d.fillRect(xStart, yStart+(bgHeight*2)/3+lineGirth*2, bgWidth+lineGirth,lineGirth);

        // Third vertical line
        g2d.fillRect(xStart+bgWidth/4, yStart+bgHeight/3+lineGirth,lineGirth,bgHeight/3+lineGirth);

        // Fourth vertical line
        g2d.fillRect(xStart+3*bgWidth/4,yStart+bgHeight/3+lineGirth,lineGirth,bgHeight/3+lineGirth);
    }

    @Override
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.black);
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        paintPicture(g2d);


        BasicStroke bs = new BasicStroke(16, BasicStroke.JOIN_BEVEL, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs);
        g2d.setColor(Color.green);
        g2d.drawRect(650, 35, 425, 425);

        g2d.translate(840, 180);

        g2d.translate(tx, ty);


        double[][]points = new double[][]  {
                { -100, -15 }, { -25, -25 }, { 0, -90 }, { 25, -25 },
                { 100, -15 }, { 50, 25 }, { 60, 100 }, { 0, 50 },
                { -60, 100 }, { -50, 25 }, { -100, -15 }
        };

        GradientPaint gp = new GradientPaint(5, 25,
                new Color(255,255,0), 20, 2, new Color(0,0,255), true);
        g2d.setPaint(gp);

        GeneralPath star = new GeneralPath();
        star.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            star.lineTo(points[k][0], points[k][1]);

        star.closePath();

        g2d.scale(scale, 0.99);

        g2d.fill(star);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Lab2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(new Skeleton());

        frame.setVisible(true);


        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;

    }


    public void actionPerformed(ActionEvent e) {

        if(scale > 1.5){
            delta =  -delta;
        }
        else if(scale < 0.5){
            delta+= 0.03;
        }

        switch (direction){
            case RIGHT:
                tx -= 1;
                break;
            case LEFT:
                tx += 1;
                break;
            case UP:
                ty -= 1;
                break;
            case DOWN:
                ty += 1;
                break;
        }
        if(tx > 70 || ty > 170 || ty < 50 || tx < -10){
            switch(direction){
                case RIGHT:
                    direction = DIRECTIONS.UP;
                    break;
                case UP:
                    direction = DIRECTIONS.LEFT;
                    break;
                case LEFT:
                    direction = DIRECTIONS.DOWN;
                    break;
                case DOWN:
                    direction = DIRECTIONS.RIGHT;
                    break;
            }
        }

        scale += delta;

        repaint();
    }
}