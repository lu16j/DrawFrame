import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

class Erase extends Squiggle
{
    public Erase(Color _color, int _width)
    {
        super(_color, _width);
    }
    public void setColor(Color _color) {
        if(!this.color.equals(_color)) {
            this.color = _color;
        }
    }
}