import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
class Circle extends Drawable
{
    private int startX = 0;
    private int startY = 0;
    private int size = 0;
    
    public Circle(Color _color, int _width)
    {
        super(_color, _width);
    }
    public void draw(Graphics g)
    {
        if(this.buffer != null)
            g.drawImage(this.buffer, this.startX, this.startY, null);
        else if(this.size() > 0) {
            startX = this.get(0).x;
            startY = this.get(0).y;
            int currentX = this.get(this.size()-1).x;
            int currentY = this.get(this.size()-1).y;
            size = Math.min(Math.abs(startX-currentX), Math.abs(startY-currentY));
            if(startX>currentX)
                startX = startX-size;
            if(startY>currentY)
                startY = startY-size;
            g.setColor(this.color);
            g.fillOval(startX, startY, size, size);
        }
    }
    public void done() {
        this.buffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics g = this.buffer.getGraphics();
        g.setColor(this.color);
        g.fillOval(0, 0, size, size);
    }
}