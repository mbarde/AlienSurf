package org.fingertipmagic.AlienSurf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

// a square with a side length of 1
public class Square {

	private final String vertexShaderCode =
        // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +        

        "attribute vec4 vPosition;" +
        "attribute vec2 a_TexCoordinate;" +	
        
        "varying vec2 v_TexCoordinate;" +
    
        "void main() {" +
        // the matrix must be included as a modifier of gl_Position
        "  gl_Position = uMVPMatrix * vPosition;" +
        "  v_TexCoordinate = a_TexCoordinate;" +
        "}";

    private final String fragmentShaderCode =
        "precision mediump float;" +
        "uniform sampler2D u_Texture;" +
        "uniform vec4 vColor;" +
        
        "varying vec2 v_TexCoordinate;"+
        "void main() {" +
        "  gl_FragColor = vColor * texture2D(u_Texture, v_TexCoordinate);" +
        "}";

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    
    // originally it was also final
    private FloatBuffer textureCoordinates;
    
    // handles to pass the values to the shader
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int textureCoordinateHandle;
    private int mTextureUniformHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    private float squareCoords[] = { 	0, 0, 0, 
    									0, 0, 0, 
    									0, 0, 0,
    									0, 0, 0   };
 
    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
    
	private float[] textureCoordData =
	{												
			// Front face
			0.0f, 0.0f, 				
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f	};
    
	// texture id
    private int mTextureDataHandle;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };
    
    float width, height;
    
    public Square(float x, float y, float z, float width, float height, int textureHandle,
    				float x0, float y0, float x1, float y1,
    				float x2, float y2, float x3, float y3) {
    	if (x0 + y0 + x1 + y1 + x2 + y2 + x3 + y3 != 0) 
    		setTexCoords(x0, y0, x1, y1, x2, y2, x3, y3);
    	
    	this.setColor(1.0f, 1.0f, 1.0f);
    	
    	this.width = width;
    	this.height = height;
    	
         squareCoords[0] = x - (width/2); squareCoords[1] = y + (height/2); 	// top left
         squareCoords[3] = x - (width/2); squareCoords[4] = y - (height/2); 	// bottom left
         squareCoords[6] = x + (width/2); squareCoords[7] = y - (height/2);		// bottom right
         squareCoords[9] = x + (width/2); squareCoords[10] = y + (height/2);	// top right
    	
        // initialize vertex byte buffer for shape coordinates
        // (# of coordinate values * 4 bytes per float)
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        
        textureCoordinates = ByteBuffer.allocateDirect(textureCoordData.length * 4)
		.order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates.put(textureCoordData).position(0);

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                                                   vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                                                     fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
        
        mTextureDataHandle = textureHandle;
    }
    
    public Square(float x, float y, float z, float width, float height, int textureHandle) {
    	this(x, y, z, width, height, textureHandle, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);
        
        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        GLES20.glUniform1i(mTextureUniformHandle, 0);   

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        
        textureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
        
        // Pass in the texture coordinate information
        textureCoordinates.position(0);
        GLES20.glVertexAttribPointer(textureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 
        		0, textureCoordinates);
        
        GLES20.glEnableVertexAttribArray(textureCoordinateHandle);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
                              GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    
    public void setColor(float r, float g, float b) {
    	color[0] = r;
    	color[1] = g;
    	color[2] = b;
    }
    
    public void setTexCoords(	float x0, float y0, float x1, float y1,
    							float x2, float y2, float x3, float y3 ) {
    	textureCoordData = new float[8];
    	textureCoordData[0] = x0; textureCoordData[1] = y0;
    	textureCoordData[2] = x1; textureCoordData[3] = y1;
    	textureCoordData[4] = x2; textureCoordData[5] = y2;
    	textureCoordData[6] = x3; textureCoordData[7] = y3;
    	
        textureCoordinates = ByteBuffer.allocateDirect(textureCoordData.length * 4)
		.order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates.put(textureCoordData).position(0);
    }
    
    public void setTexCoords(float[] texCoords) {
    	if (texCoords.length != 8) return;
    	textureCoordData = texCoords.clone();
    	
        textureCoordinates = ByteBuffer.allocateDirect(textureCoordData.length * 4)
		.order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates.put(textureCoordData).position(0);
    }
    
    public void setTextureHandle(int textureHandle) {
    	mTextureDataHandle = textureHandle;
    }
    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
