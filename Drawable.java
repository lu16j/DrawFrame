import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.ArrayList;
/**
 * An abstract extension of ArrayList<Point>; allows child classes to draw a shape with a given color and stroke width.
 */
public abstract class Drawable extends ArrayList<Point>
{
    protected Color color;
    protected int width;
    protected BufferedImage buffer;
    /**
     * @param _color the color of the shape
     * @param _width the stroke width of the shape
     */
    public Drawable(Color _color, int _width)
    {
        color = _color;
        width = _width;
    }
    public abstract void draw(Graphics g);
    public abstract void done();
}