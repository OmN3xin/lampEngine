package gameLamp;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import sun.font.TrueTypeFont;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class Window {
    protected String title = "Game";
    protected Vector3 backgroundcolor = new Vector3(0,0,0);
    private long window;
    public List<GameObject> objs;
    public void run() {

        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public boolean[] keys;
    public void init() {
        keys = new boolean[348];
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(700, 700, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        //glfwPollEvents();
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop

            keys[key] = (action == GLFW_PRESS || action == GLFW_REPEAT);
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );

        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);


        objs = new ArrayList<GameObject>();
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

    }
    public boolean isColliding(gameLamp.GameObject a, gameLamp.GameObject b){

        return !(
                ((a.position.y + a.scale.y) < (b.position.y - b.scale.y)) ||
                        ((a.position.y - a.scale.y) > (b.position.y + b.scale.y)) ||
                        ((a.position.x + a.scale.x) < (b.position.x - b.scale.x)) ||
                        ((a.position.x - a.scale.x) > (b.position.x + b.scale.x))
        );
    }

    private void Update()
    {
        onUpdate();
    }

    protected abstract void onUpdate();

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(backgroundcolor.x,
                backgroundcolor.y,
                backgroundcolor.z, 0);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.



        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            Update();
            for (GameObject obj : objs) {
                float v[] = new float[obj.s.v.length];
                v = obj.s.v;
                FloatBuffer vBuff = BufferUtils.createFloatBuffer(v.length);
                vBuff.put(v);
                vBuff.flip();
                int vertsListed = 0;
                int c = 0;
                for(int i =0;i<v.length;i++){
                    c++;
                    if(c == 3){
                        vertsListed++;
                        c = 0;
                    }
                }
                glLoadIdentity();
                float[] colors = new float[v.length+vertsListed];
                if(obj.ImagePath == ""){
                    int count = 0;
                    for (int i = 0;i<colors.length;i++){

                        if(count == 2){
                            colors[i] = obj.color.c.z;
                        }
                        if(count == 1)
                            colors[i] = obj.color.c.y;
                        if(count == 0)
                            colors[i] = obj.color.c.x;
                        if(count == 3){
                            if(!obj.visable)
                                colors[i] = 0;
                            else
                                colors[i] = 255;
                            count = 0;
                            continue;
                        }

                        count++;
                    }
                }
                else{

                }

                FloatBuffer cBuff = BufferUtils.createFloatBuffer(colors.length);
                cBuff.put(colors);
                cBuff.flip();
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable( GL_BLEND );
                glEnableClientState(GL_VERTEX_ARRAY);
                if(obj.ImagePath == "")
                glEnableClientState(GL_COLOR_ARRAY);
                Vector3 pos = obj.position;
                Vector3 scale = obj.scale;
                Vector3 rotation = obj.rotation;
                glTranslatef(pos.x, pos.y, pos.z);
                glRotatef(rotation.x,1,0,0);
                glRotatef(rotation.y,0,1,0);
                glRotatef(rotation.z,0,0,1);
                glScalef(scale.x, scale.y, scale.z);
                glVertexPointer(3, GL_FLOAT, 0, vBuff);

                if(obj.ImagePath == "")
                glColorPointer(4, GL_FLOAT, 0,cBuff);
                glDrawArrays(GL_POLYGON, 0, vertsListed);

                if(obj.ImagePath == "")
                glDisableClientState(GL_COLOR_ARRAY);
                glDisableClientState(GL_VERTEX_ARRAY);
            }

            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();
        }
    }
}
