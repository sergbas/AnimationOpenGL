package my.apps.animationopengl;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "gl";
    private Cube cube;
    long ticks;

    private float rotationAngle;
    private long old = 0;
    private float delta = 0.4f;

    public OpenGLRenderer() {
        cube = new Cube();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        float fovy = 50.0f; // Field of view angle, in degrees, in the Y direction.
        float aspect = (float) width / (float) height;
        float zNear = 0.1f;
        float zFar = 100.0f;
        // Set up a perspective projection matrix
        GLU.gluPerspective(gl, fovy, aspect, zNear, zFar);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        long now = System.currentTimeMillis();

        if(ticks>0)
        {
            long delta_ms = now - old;
            if(delta_ms>0)
                delta = 360 / delta_ms / 10f; //1 оборот за 10 сек
        }

        rotationAngle -= delta;

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Replace the current matrix with the identity matrix.
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -6.0f);
        gl.glScalef(0.8f, 0.8f, 0.8f);
        gl.glRotatef(rotationAngle, 1.0f, 1.0f, 1.0f);
        cube.draw(gl);

        old = now;
        ticks++;
    }
}