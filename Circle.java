import java.awt.Color;
import java.awt.Graphics;
class Circle extends Drawable
{
    private Color color;
    private int width;
    public Circle(Color _color, int _width)
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
            int currentX = this.get(this.size()-1).x;
            int currentY = this.get(this.size()-1).y;
            int size = Math.min(Math.abs(startX-currentX), Math.abs(startY-currentY));
            if(startX>currentX)
                startX = startX-size;
            if(startY>currentY)
                startY = startY-size;
            g.setColor(this.color);
            g.fillOval(startX, startY, size, size);
        }
        catch(IndexOutOfBoundsException ioobe)
        {
            return;
        }
    }
}