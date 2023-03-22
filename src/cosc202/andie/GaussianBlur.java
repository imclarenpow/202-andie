package cosc202.andie;

import java.awt.image.*;
import java.awt.*;

/** Using MeanFilter Code as a template to start the layout of the filter */
public class GaussianBlur implements ImageOperation, java.io.Serializable{
    private int radius;
    private double variance;
    
    GaussianBlur(int radius){
        this.radius = radius;
        variance = ((double)radius)/3;
    }
    GaussianBlur(){
        this(1);
        variance = radius/3;
    }
    
    public BufferedImage apply(BufferedImage input){
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        double[][] weights = weightMatrix();
        for(int x=0; x<input.getWidth(); x++){
            for(int y=0; y<input.getHeight(); y++){
                //writing rgb values
                double[][] red = new double[radius][radius];
                double[][] green = new double[radius][radius];
                double[][] blue = new double[radius][radius];

                for(int i=0; i<weights.length; i++){
                    for(int j=0; j<weights[i].length; j++){
                        //try in case we go out of bounds with out blur
                        try{
                        int sampleX = x+i - (weights.length/2);
                        int sampleY = y+i - (weights.length/2);
                        double currentWeight = weights[x][y];
                        Color sampledColour = new Color(input.getRGB(sampleX, sampleY));
                        
                        red[i][j] = currentWeight * sampledColour.getRed();
                        blue[i][j] = currentWeight * sampledColour.getBlue();
                        green[i][j] = currentWeight * sampledColour.getGreen();
                        }catch (Exception e){
                            System.out.println("Out of bounds");
                        }
                        
                    }
                }
                output.setRGB(x, y, new Color(getWeightedColourValue(red), getWeightedColourValue(green), getWeightedColourValue(blue)).getRGB());
            }
        }
        return output;
    }
    private int getWeightedColourValue(double[][] weightedColour){
        double total = 0;

        for(int i=0; i< weightedColour.length; i++){
            for(int j=0; j<weightedColour[i].length; j++){
                total += weightedColour[i][j];
            }
        }
        return (int)total;
    }
    public double[][] weightMatrix(){
        double[][] weights = new double[radius][radius];
        double summation = 0;
        for(int i = 0; i<weights.length; i++){
            for(int j = 0; j<weights[i].length; j++){
                weights[i][j] = maths(i-radius/2, j-radius/2, variance);
                summation += weights[i][j];
            }
        }
        for(int i = 0; i < weights.length; i++){
            for(int j = 0; j < weights[i].length; j++){
                weights[i][j] /= summation;
            }
        }
        return weights;
    }
    double maths(double x, double y, double variance){
        return (1/(2 * Math.PI * Math.pow(variance, 2)) * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(variance, 2))));
    }

}