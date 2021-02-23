package gameLamp.Shape;

public class Shapes {
    public static Shape cube;
    public static Shape rect;
    public static Shape triangle;
    static {
        cube = new Shape(new float[]{
                //front
                -1,1,1,
                1,1,1,
                1,-1,1,
                -1,-1,1,
                //back
                -1,1,-1,
                1,1,-1,
                1,-1,-1,
                -1,-1,-1,
                //left
                -1,1,1,
                -1,1,-1,
                -1,-1,-1,
                -1,-1,1,
                //right
                1,1,1,
                1,1,-1,
                1,-1,-1,
                1,-1,1,
                //top
                -1,1,1,
                -1,1,-1,
                1,1,-1,
                1,1,1,
                //bottom
                -1,-1,1,
                -1,-1,-1,
                1,-1,-1,
                1,-1,1
        });
        rect = new Shape(new float[]{
                -1,-1,0,
                1,-1,0,
                1,1,0,
                -1,1,0
        });
        triangle = new Shape(new float[]{
           0,1,0,
           1,-1,0,
           -1,-1,0

        });
    }
}
