import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

class Erase extends Drawable
{
    private int width;
    
    public Erase(Color _color, int _width)
    {
        super(_color, _width);
        width = _width;
    }
    
    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(this.width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int j=0; j<this.size()-1; j++)
        {
            g2d.drawLine(this.get(j).x, this.get(j).y, this.get(j+1).x, this.get(j+1).y);
        }
    }
}