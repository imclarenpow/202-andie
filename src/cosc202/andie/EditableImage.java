package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.JOptionPane;

import cosc202.andie.edit.EditActions;
import cosc202.andie.edit.EditActions.UndoAction;
import cosc202.andie.image.*;
import cosc202.andie.lang.*;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to
 * it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can
 * be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always
 * undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original
 * image
 * and the result of applying the current set of operations to it.
 * The operations themselves are stored on a {@link Stack}, with a second
 * {@link Stack}
 * being used to allow undone operations to be redone.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /**
     * The current image, the result of applying {@link ops} to {@link original}.
     */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;
    /** Provides support for multiple languages */
    private LanguageSupport lang = new LanguageSupport();

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference).
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        imageFilename = null;
        opsFilename = null;
    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage.
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that
     * assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally
     * used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so
     * the
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knoweldge of some details about the internals of the
     * BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href=
     * "https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to
     * <a href=
     * "https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under
     * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Gets a copy of image operations that have been applied to the current image
     * 
     * @return a clone of the image operations as a stack
     */
    @SuppressWarnings("unchecked")
    public Stack<ImageOperation> getImageOps() {
        return (Stack<ImageOperation>) ops.clone();
    }

    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file and resets the operations/redo
     * operations stacks.
     * Also tries to open a set of operations from the file with <code>.ops</code>
     * added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try
     * to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     * @throws NullPointerException If attempting to read or deepCopy a corrupt or
     *                              missing
     * @throws Exception            If something goes wrong.
     */
    public void open(String filePath) throws Exception {
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        try {
            original = ImageIO.read(imageFile);
            if (original.getWidth() <= Andie.MAX_DIMENSION_LIMIT && original.getHeight() <= Andie.MAX_DIMENSION_LIMIT) {
                current = deepCopy(original);
            } else {
                // Handles cases where the image is too large
                original = null;
                JOptionPane.showMessageDialog(null, lang.text("oversizedimgwarning"),
                        lang.text("oversizedimg"), JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NullPointerException badFile) {
            JOptionPane.showMessageDialog(null, lang.text("badfilewarning"),
                    lang.text("corruptfile"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
        }
        this.refresh();
    }

    /**
     * <p>
     * Save an image to a file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved
     * as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws Exception If something goes wrong.
     */
    public void save() throws Exception {
        if (this.hasImage()) {

            if (this.opsFilename == null) {
                this.opsFilename = this.imageFilename + ".ops";
            }
            // Write image file based on file extension
            String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
            ImageIO.write(original, extension, new File(imageFilename));
            // Write operations file
            FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(this.ops);
        
            objOut.close();
            fileOut.close();
        } else {
            Andie.throwGenericError();
        }
    }

    /**
     * <p>
     * Save an image to a specified file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws Exception If something goes wrong.
     */
    public void saveAs(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * <p>
     * Exports an image to a specified file.
     * </p>
     * 
     * <p>
     * Exports an edited image to the file provided as a parameter.
     * </p>
     * 
     * @param imageFilename The file location to export the image to.
     * @throws Exception If something goes wrong.
     */
    public void exportAs(String imageExportFilename) throws Exception {
        String extension = imageExportFilename.substring(1 + imageExportFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(current, extension, new File(imageExportFilename));
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * <p>
     * If the image is null no changes are applied and a popup warns the user that
     * no operations have been applied
     * </p>
     * 
     * @param op The operation to apply.
     */
    public boolean apply(ImageOperation op) {
        if (current != null) {
            current = op.apply(current);
            ops.add(op);
            return true;
        } else {
            ShowNoImageError();
            return false;
        }

    }

    /**
     * <p>
     * Warns the user that they have tried to edit an image when there is no image
     * present
     * </p>
     */
    public void ShowNoImageError() {
        PencilButton.disableDrawMode();
        JOptionPane.showMessageDialog(null, lang.text("noimagewarning"),
                lang.text("noimage"),
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * <p>
     * Warns the user that they have tried redo operations when there are none to
     * redo
     * </p>
     */
    private void ShowNoRedoOperationsError() {
        JOptionPane.showMessageDialog(null, lang.text("noredooperationswarning"),
                lang.text("noredooperation"),
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * <p>
     * Warns the user that they have tried undo operations when there are none to
     * undo
     * </p>
     */
    private void ShowNoUndoOperationsError() {
        JOptionPane.showMessageDialog(null, lang.text("noundooperationswarning"),
                lang.text("noundooperation"),
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {
        if (current != null) {
            if (ops.size() != 0) {
                redoOps.push(ops.pop());
                refresh();
            } else {
                ShowNoUndoOperationsError();
            }

        } else {
            ShowNoImageError();
        }
    }

    /**
     * Undoes all pencil drawings that have been applied to the current image
     */
    public void undoDrawings() {
        if (current != null) {
            Stack<ImageOperation> auxiliary = new Stack<ImageOperation>();
            while (ops.size() > 0) {
                ImageOperation operation = ops.pop();
                if (operation instanceof Pencil) {
                    redoOps.push(operation);
                } else {
                    auxiliary.push(operation);
                }
            }

            for (ImageOperation redoOperation : auxiliary) {
                ops.push(redoOperation);
            }

            refresh();
        }
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo() {
        if (current != null) {
            if (redoOps.size() != 0) {
                apply(redoOps.pop());
            } else {
                ShowNoRedoOperationsError();
            }
        } else {
            ShowNoImageError();
        }
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the
     *         {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in
     * sequence.
     * This is useful when undoing changes to the image, or in any other case where
     * {@link current}
     * cannot be easily incrementally updated.
     * </p>
     */
    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op : ops) {
            current = op.apply(current);
        }
    }
}
