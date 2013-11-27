import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
/**
 * An extension of ArrayList<Point> and Drawable; draws a squiggle for a set of points given a color and stroke width.
 */
class Squiggle extends Drawable
{
    private Color color;
    private int width;
    private int minX;
    private int minY;
    /**
     * Creates a Squiggle with a given color and stroke width
     * @param _color the color of the squiggle
     * @param _width the stroke width of the squiggle
     */
    public Squiggle(Color _color, int _width)
    {
        super(_color, _width);
        color = _color;
        width = _width;
    }
    /**
     * Draws the squiggle
     */
    public void draw(Graphics g)
    {
        if(this.buffer != null)
            g.drawImage(this.buffer, minX, minY, null);
        else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(this.color);
            g2d.setStroke(new BasicStroke(this.width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for(int j=0; j<this.size()-1; j++)
            {
                g2d.drawLine(this.get(j).x, this.get(j).y, this.get(j+1).x, this.get(j+1).y);
            }
        }
    }
    public void done() {
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;
        for(Point p: this) {
            minX = Math.min(minX, p.x-width);
            minY = Math.min(minY, p.y-width);
            maxX = Math.max(maxX, p.x+width);
            maxY = Math.max(maxY, p.y+width);
        }
        this.buffer = new BufferedImage(maxX-minX, maxY-minY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) this.buffer.getGraphics();
        g2d.setColor(this.color);
        g2d.setStroke(new BasicStroke(this.width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int j=0; j<this.size()-1; j++) {
            g2d.drawLine(this.get(j).x-minX, this.get(j).y-minY, this.get(j+1).x-minX, this.get(j+1).y-minY);
        }
    }
}