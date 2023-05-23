package cosc202.andie.macros;

import java.util.Stack;

import cosc202.andie.image.ImageOperation;

public class Macro {
    private Stack <ImageOperation> macroOps;

    public void macroStart (){
        macroOps = new Stack<ImageOperation>();
        boolean recording = true;
    }
}
