import java.awt.Color;
import java.awt.Graphics;
class Rectangle extends Drawable
{
    public Rectangle(Color _color, int _width)
    {
        super(_color, _width);
    }
    public void draw(Graphics g)
    {
        try
        {
            int startX = Math.min(this.get(0).x, this.get(this.size()-1).x);
            int startY = Math.min(this.get(0).y, this.get(this.size()-1).y);
            int length = Math.max(this.get(0).x, this.get(this.size()-1).x)-startX;
            int height = Math.max(this.get(0).y, this.get(this.size()-1).y)-startY;
            g.setColor(this.color);
            g.fillRect(startX, startY, length, height);
        }
        catch(IndexOutOfBoundsException ioobe)
        {
            return;
        }
    }
    public void done() {}
}