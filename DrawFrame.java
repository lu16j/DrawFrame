import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.TreeMap;
import java.util.Date;
import java.util.regex.Pattern;
public class DrawFrame extends JFrame implements Runnable
{
    Color bgColor;
    Color penColor;
    int lineWidth;
    String currentShape;
    TreeMap<Long, Drawable> drawables;
    long currentID;
    DrawPanel drawPanel;
    JDialog penDialog;
    JDialog bgDialog;
    JColorChooser penChooser;
    JColorChooser bgChooser;
    JTextField pen;
    JTextField bg;
    JTextField line;
    File currentFile;
    boolean changed;
    public DrawFrame()
    {
        super("DrawFrame");
        drawables = new TreeMap<Long, Drawable>();
        
        penColor = Color.black;
        bgColor = Color.white;
        lineWidth = 1;
        currentShape = "Line";
        currentFile = null;
        changed = false;
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                if(changed)
                {
                    int option = JOptionPane.showConfirmDialog(DrawFrame.this, "Save current work?");
                    if(option==JOptionPane.YES_OPTION)
                    {
                        try
                        {
                            saveCurrentFile();
                        }
                        catch(FileNotFoundException fnfe)
                        {
                            return;
                        }
                        catch(IOException ioe)
                        {
                            return;
                        }
                    }
                    else if(option==JOptionPane.CANCEL_OPTION)
                        return;
                }
                System.exit(0);
            }
        });
    }
    public void run()
    {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        /***********************************Declare stuff**********************************/
        drawPanel = new DrawPanel();
        JMenuBar mbar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu shape = new JMenu("Shape");
        JMenu penMenu = new JMenu("Pen color hex:");
        JMenu bgMenu = new JMenu("Background color hex:");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Open...");
        JMenuItem save = new JMenuItem("Save...");
        JMenuItem saveAs = new JMenuItem("Save As...");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem clear = new JMenuItem("Clear");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem linear = new ShapeItem("Line");
        JMenuItem squiggle = new ShapeItem("Squiggle");
        JMenuItem square = new ShapeItem("Square");
        JMenuItem rectangle = new ShapeItem("Rectangle");
        JMenuItem roundRect = new ShapeItem("Round Rectangle");
        JMenuItem diamond = new ShapeItem("Diamond");
        JMenuItem circle = new ShapeItem("Circle");
        JMenuItem oval = new ShapeItem("Oval");
        JMenuItem eraser = new ShapeItem("Eraser");
        JMenuItem penCustom = new JMenuItem("Customize...");
        JMenuItem bgCustom = new JMenuItem("Customize...");
        pen = new JTextField("000000");
        bg = new JTextField("ffffff");
        line = new JTextField("1");
        penChooser = new JColorChooser(penColor);
        bgChooser = new JColorChooser(bgColor);
        /*************************************Set stuff**************************************/
        pen.setMaximumSize(new Dimension(60,20));
        bg.setMaximumSize(new Dimension(60,20));
        line.setMaximumSize(new Dimension(26,22));
        file.setMnemonic(KeyEvent.VK_F);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        pen.setFocusAccelerator('p');
        bg.setFocusAccelerator('b');
        line.setFocusAccelerator('l');
        /*******************************Menu actionListeners*******************************/
        newFile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(changed)
                {
                    int option = JOptionPane.showConfirmDialog(DrawFrame.this, "Save current work?");
                    if(option==JOptionPane.YES_OPTION)
                    {
                        try
                        {
                            saveCurrentFile();
                        }
                        catch(FileNotFoundException fnfe)
                        {
                            return;
                        }
                        catch(IOException ioe)
                        {
                            return;
                        }
                    }
                    else if(option==JOptionPane.CANCEL_OPTION)
                        return;
                }
                drawables.clear();
                penColor = Color.black;
                bgColor = Color.white;
                lineWidth = 1;
                currentShape = "Line";
                changed = false;
                setState();
                currentFile = null;
                setTitle("DrawFrame");
            }
        });
        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(changed)
                {
                    int option = JOptionPane.showConfirmDialog(DrawFrame.this, "Save current work?");
                    if(option==JOptionPane.YES_OPTION)
                    {
                        try
                        {
                            saveCurrentFile();
                        }
                        catch(FileNotFoundException fnfe)
                        {
                            return;
                        }
                        catch(IOException ioe)
                        {
                            return;
                        }
                    }
                    else if(option==JOptionPane.CANCEL_OPTION)
                        return;
                }
                JFileChooser jfc = new JFileChooser();
                FileNameExtensionFilter fnef = new FileNameExtensionFilter("DrawFrame *.df Files", "df");
                jfc.setFileFilter(fnef);
                int opt = jfc.showOpenDialog(DrawFrame.this);
                if(opt==JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().exists())
                {
                    currentFile = jfc.getSelectedFile();
                    try
                    {
                        readCurrentFile();
                    }
                    catch(ClassNotFoundException cnfe)
                    {
                        cnfe.printStackTrace();
                        return;
                    }
                    catch(IOException ioe)
                    {
                        ioe.printStackTrace();
                        return;
                    }
                }
                else if(opt==JFileChooser.CANCEL_OPTION)
                    return;
            }
        });
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    saveCurrentFile();
                }
                catch(FileNotFoundException fnfe)
                {
                    return;
                }
                catch(IOException ioe)
                {
                    return;
                }
            }
        });
        saveAs.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    setCurrentFile();
                    writeCurrentFile();
                }
                catch(FileNotFoundException fnfe)
                {
                    return;
                }
                catch(IOException ioe)
                {
                    return;
                }
            }
        });
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(changed)
                {
                    int option = JOptionPane.showConfirmDialog(DrawFrame.this, "Save current work?");
                    if(option==JOptionPane.YES_OPTION)
                    {
                        try
                        {
                            saveCurrentFile();
                        }
                        catch(FileNotFoundException fnfe)
                        {
                            return;
                        }
                        catch(IOException ioe)
                        {
                            return;
                        }
                    }
                    else if(option==JOptionPane.CANCEL_OPTION)
                        return;
                }
                System.exit(0);
            }
        });
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                drawables.clear();
                drawPanel.repaint();
                changed = true;
            }
        });
        undo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(drawables.size() > 0) {
                    drawables.remove(drawables.lastKey());
                    drawPanel.repaint();
                    changed = true;
                }
            }
        });
        /****************************Text/hex actionListeners*************************/
        pen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                String hexCode = toHex(pen.getText());
                boolean b = Pattern.matches("[0-9a-fA-F]{6}", hexCode);
                if(b==true)
                {
                    penColor = Color.decode("0x" + hexCode);
                }
                else
                {
                    JOptionPane.showMessageDialog(DrawFrame.this, pen.getText() + " is not a valid hex code!", "You silly", JOptionPane.ERROR_MESSAGE);
                    pen.setText(Integer.toHexString(penColor.getRGB() & 0x00ffffff));
                }
                penChooser.setColor(penColor);
                changed = true;
            }
        });
        bg.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                String hexCode = toHex(bg.getText());
                boolean b = Pattern.matches("[0-9a-fA-F]{6}", hexCode);
                if(b==true)
                {
                    bgColor = Color.decode("0x" + hexCode);
                    drawPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(DrawFrame.this, bg.getText() + " is not a valid hex code!", "You silly", JOptionPane.ERROR_MESSAGE);
                    bg.setText(Integer.toHexString(bgColor.getRGB() & 0x00ffffff));
                }
                bgChooser.setColor(bgColor);
                changed = true;
            }
        });
        line.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int n = Integer.valueOf(line.getText()).intValue();
                    if(n<=50 && n>0)
                    {
                        lineWidth = n;
                    }
                    else if(n>50)
                    {
                        line.setText("50");
                        lineWidth = 50;
                    }
                    else
                    {
                        line.setText("1");
                        lineWidth = 1;
                    }
                }
                catch(NumberFormatException nfe)
                {
                    JOptionPane.showMessageDialog(DrawFrame.this, line.getText() + " is not a number!", "You silly", JOptionPane.ERROR_MESSAGE);
                    line.setText("1");
                    lineWidth = 1;
                }
                changed = true;
            }
        });
        /******************************Color choosers***********************/
        ActionListener setPen = new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                penColor = penChooser.getColor();
                pen.setText(Integer.toHexString(penColor.getRGB() & 0x00ffffff));
                changed = true;
            }
        };
        ActionListener setBg = new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                bgColor = bgChooser.getColor();
                drawPanel.repaint();
                bg.setText(Integer.toHexString(bgColor.getRGB() & 0x00ffffff));
                changed = true;
            }
        };
        ActionListener cancel = new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                return;
            }
        };
        penDialog = JColorChooser.createDialog(drawPanel, "Pen Color", true, penChooser, setPen, cancel);
        penCustom.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                penDialog.setVisible(true);
            }
        });
        bgDialog = JColorChooser.createDialog(drawPanel, "Background Color", true, bgChooser, setBg, cancel);
        bgCustom.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                bgDialog.setVisible(true);
            }
        });
        /******************************Add and set stuff****************************/
        file.add(newFile);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(undo);
        file.add(clear);
        file.add(quit);
        shape.add(linear);
        shape.add(squiggle);
        shape.add(square);
        shape.add(rectangle);
        shape.add(roundRect);
        shape.add(diamond);
        shape.add(circle);
        shape.add(oval);
        shape.add(eraser);
        penMenu.add(penCustom);
        bgMenu.add(bgCustom);
        mbar.add(file);
        mbar.add(shape);
        mbar.add(penMenu);
        mbar.add(pen);
        mbar.add(bgMenu);
        mbar.add(bg);
        mbar.add(new JMenu("Brush width:"));
        mbar.add(line);
        setJMenuBar(mbar);
        this.getContentPane().add(BorderLayout.CENTER, drawPanel);
        setSize(800,480);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        DrawFrame df = new DrawFrame();
        javax.swing.SwingUtilities.invokeLater(df);
    }
    public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener
    {
        Drawable temp;
        
        public DrawPanel()
        {
            super();
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        public void paintComponent(Graphics g)
        {
            //Paint background
            g.setColor(bgColor);
            g.fillRect(0,0,getWidth(),getHeight());
            //Paint lines
            for(long d: drawables.keySet()) {
                if(drawables.get(d) instanceof Erase)
                    g.setColor(bgColor);
                drawables.get(d).draw(g);
            }
        }
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e)
        {
            if(currentShape=="Line")
                temp = new Line(penColor, lineWidth);
            if(currentShape=="Squiggle")
                temp = new Squiggle(penColor, lineWidth);
            if(currentShape=="Square")
                temp = new Square(penColor, lineWidth);
            if(currentShape=="Rectangle")
                temp = new Rectangle(penColor, lineWidth);
            if(currentShape=="Round Rectangle")
                temp = new RoundedRectangle(penColor, lineWidth);
            if(currentShape=="Diamond")
                temp = new Diamond(penColor, lineWidth);
            if(currentShape=="Circle")
                temp = new Circle(penColor, lineWidth);
            if(currentShape=="Oval")
                temp = new Oval(penColor, lineWidth);
            if(currentShape=="Eraser")
                temp = new Erase(penColor, lineWidth);
            currentID = (new Date()).getTime();
            drawables.put(currentID, temp);
            drawables.get(currentID).add(e.getPoint());
            
            temp.clear();
            repaint();
        }
        public void mouseReleased(MouseEvent e)
        {
            drawables.get(currentID).done();
        }
        public void mouseClicked(MouseEvent e){}
        public void mouseMoved(MouseEvent e){}
        public void mouseDragged(MouseEvent e)
        {
            drawables.get(currentID).add(e.getPoint());
            repaint();
            changed = true;
        }
    }
    public class ShapeItem extends JMenuItem
    {
        public ShapeItem(final String _name)
        {
            super(_name);
            addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    currentShape = _name;
                }
            });
        }
    }
    public String toHex(String _string)
    {
        int n = _string.length();
        if(n>2 && _string.substring(0,2).equals("0x")==true)
        {
            _string = _string.substring(2,n);
            n = _string.length();
        }
        if(n>1 && _string.substring(0,1).equals("#")==true)
        {
            _string = _string.substring(1,n);
            n = _string.length();
        }
        if(n<6)
        {
            for(int k=0; k<6-n; k++)
            {
                _string = "0" + _string;
            }
        }
        return _string;
    }
    public void saveCurrentFile() throws FileNotFoundException, IOException
    {
        if(currentFile==null)
        {
            if(setCurrentFile())
                writeCurrentFile();
        }
        else
            writeCurrentFile();
    }
    public boolean setCurrentFile()
    {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("DrawFrame *.df Files", "df");
        jfc.setFileFilter(fnef);
        int option = jfc.showSaveDialog(DrawFrame.this);
        if(option == JFileChooser.APPROVE_OPTION)
        {
            currentFile = jfc.getSelectedFile();
            setTitle("DrawFrame: " + currentFile.getAbsolutePath());
            changed = false;
            return true;
        }
        else if(option == JFileChooser.CANCEL_OPTION)
            return false;
        else
            return false;
    }
    public void writeCurrentFile() throws FileNotFoundException, IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentFile));
        oos.writeObject(bgColor);
        oos.writeObject(drawables);
        oos.writeObject(penColor);
        oos.writeInt(lineWidth);
        oos.close();
        changed = false;
    }
    @SuppressWarnings("unchecked")
    public void readCurrentFile() throws ClassNotFoundException, IOException
    {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentFile));
        bgColor = (Color) ois.readObject();
        drawables = (TreeMap<Long, Drawable>) ois.readObject();
        penColor = (Color) ois.readObject();
        lineWidth = (int) ois.readInt();
        ois.close();
        setTitle("DrawFrame: " + currentFile.getAbsolutePath());
        for(long k: drawables.keySet())
            drawables.get(k).done();
        setState();
        changed = false;
    }
    public void setState()
    {
        drawPanel.repaint();
        pen.setText(Integer.toHexString(penColor.getRGB() & 0x00ffffff));
        bg.setText(Integer.toHexString(bgColor.getRGB() & 0x00ffffff));
        line.setText(String.valueOf(lineWidth));
        penChooser.setColor(penColor);
        bgChooser.setColor(bgColor);
    }
}