package cosc202.andie.Macros;

import java.awt.image.ImageObserver;
import java.util.Stack;

import cosc202.andie.image.ImageOperation;

public class Macro {
    private Stack <ImageOperation> macroOps;

    public void macroStart (){
        macroOps = new Stack<ImageOperation>();
        boolean recording = true;
    }
}
