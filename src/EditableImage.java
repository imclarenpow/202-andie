import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
//import javax.imageio.stream.ImageOutputStream;

class EditableImage {
    private BufferedImage original;
    private BufferedImage current;
    private Stack<ImageOperation> ops;
    private Stack<ImageOperation> redoOps;
    private String imageFilename;
    private String opsFilename;

    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
    }

    public boolean hasImage() {
        return current != null;
    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public void open(String filePath) throws Exception {
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        original = ImageIO.read(imageFile);
        current = deepCopy(original);
        System.out.println("Opened " + filePath);
        
        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            ops = (Stack<ImageOperation>) objIn.readObject();
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("No operations loaded");
        }

        this.refresh();
    }

    public void save() throws Exception {
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        // Write image file based on file extension
        String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(original, extension, new File(imageFilename));
        // Write operations file
        FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.ops);
        objOut.close();
        fileOut.close();
    }


    public void saveAs(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    public void apply(ImageOperation op) {
        assert (current != null);
        current = op.apply(current);
        ops.add(op);
    }

    public void undo() {
        assert(ops.size() > 0);
        redoOps.push(ops.pop());
        refresh();
    }

    public void redo() {
        assert(redoOps.size() > 0);
        apply(redoOps.pop());
    }

    public BufferedImage getCurrentImage() {
        return current;
    }

    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op: ops) {
            current = op.apply(current);
        }
    }

}