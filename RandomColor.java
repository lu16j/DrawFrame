import java.awt.Color;
import java.util.Random;
class RandomColor
{
    private Random r = new Random();
    private Color color;
    public RandomColor()
    { 
        color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }
    public Color getColor()
    {
        return color;
    }
}