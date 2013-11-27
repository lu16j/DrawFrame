import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
class Line extends Drawable
{
    private Color color;
    private int width;
    public Line(Color _color, int _width)
    {
        super(_color, _width);
        color = _color;
        width = _width;
    }
    public void draw(Graphics g)
    {
        try
        {
            int startX = this.get(0).x;
            int startY = this.get(0).y;
            int endX = this.get(this.size()-1).x;
            int endY = this.get(this.size()-1).y;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(this.color);
            g2d.setStroke(new BasicStroke(this.width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(startX, startY, endX, endY);
        }
        catch(IndexOutOfBoundsException ioobe)
        {
            return;
        }
    }
    public void done() {}
}