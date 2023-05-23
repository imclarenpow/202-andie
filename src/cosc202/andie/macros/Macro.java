package cosc202.andie.macros;

import java.util.Stack;

import cosc202.andie.image.ImageOperation;


/** 
 * <p>
 * A class to represent a macro instance
 * Holds a stack of operations to represent the macro
 * </p>
 */
public class Macro {
    // Data fields
    private Stack <ImageOperation> macroOps;

    /**
     * <p>
     * Starts recording a macro
     * </p>
     */
    public void macroStart (){
        macroOps = new Stack<ImageOperation>();
        boolean recording = true;
    }
}
