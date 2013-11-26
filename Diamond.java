import java.awt.Color;
import java.awt.Graphics;
class Diamond extends Drawable
{
    private Color color;
    private int width;
    public Diamond(Color _color, int _width)
    {
        super(_color, _width);
        color = _color;
        width = _width;
    }
    public void draw(Graphics g)
    {
        try
        {
            int startX = Math.min(this.get(0).x, this.get(this.size()-1).x);
            int startY = Math.min(this.get(0).y, this.get(this.size()-1).y);
            int length = Math.max(this.get(0).x, this.get(this.size()-1).x)-startX;
            int height = Math.max(this.get(0).y, this.get(this.size()-1).y)-startY;
            int[] xPoints = new int[] {startX+length/2, startX+length, startX+length/2, startX};
            int[] yPoints = new int[] {startY, startY+height/2, startY+height, startY+height/2};
            g.setColor(this.color);
            g.fillPolygon(xPoints, yPoints, 4);
        }
        catch(IndexOutOfBoundsException ioobe)
        {
            return;
        }
    }
}