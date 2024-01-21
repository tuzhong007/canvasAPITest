package com.example.androidtest2.joinedshapes;

import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
/*
public class JoinedShapesView extends View {

    private Paint paint, paintsRand[];
    private int ovalRandLength = 10;
    float ovalX[] = {475.112f, 511.849f, 886.587f, 489.792f, 973.018f, 563.968f, 397.434f, 867.107f, 761.423f, 478.634f};
    float ovalY[] = {1489.138f, 1445.024f, 952.795f, 555.547f, 510.570f, 1699.536f, 1142.286f, 1623.847f, 850.804f, 830.666f};
    float ovalW[] = {284.537f, 202.317f, 201.970f, 273.231f, 249.606f, 225.721f, 246.249f, 213.740f, 252.013f, 279.255f};
    float ovalH[] = {264.983f, 250.412f, 204.920f, 297.133f, 223.917f, 277.319f, 268.429f, 253.292f, 204.557f, 264.659f};

    int paintR[] = {62, 210, 238, 62, 215, 34, 235, 196, 15, 91, };
    int paintG[] = {222, 84, 96, 158, 178, 62, 175, 203, 24, 127, };
    int paintB[] = {209, 245, 42, 218, 78, 20, 78, 43, 101, 250, };

    private RectF rects[], rects2[];

    static {
        System.loadLibrary("androidtest2");
    }
    public JoinedShapesView(Context context) {
        super(context);
        init();
    }

    public JoinedShapesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        rects = new RectF[ovalRandLength];
        rects2 = new RectF[ovalRandLength];
        for (int i = 0; i < ovalRandLength; ++i)
        {
            rects[i] = new RectF(ovalX[i] - ovalW[i] / 2.0f, ovalY[i] - ovalH[i] / 2.0f, ovalW[i] / 2.0f + ovalX[i], ovalH[i] / 2.0f + ovalY[i]);
            rects2[i] = new RectF(1000 - (ovalX[i] - ovalW[i] / 2.0f), 1500 - (ovalY[i] - ovalH[i] / 2.0f), 1000 - (ovalW[i] / 2.0f + ovalX[i]), 1500 - (ovalH[i] / 2.0f + ovalY[i]));
        }
        paintsRand = new Paint[ovalRandLength];
        for (int i = 0; i < ovalRandLength; ++i)
        {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(Color.rgb(paintR[i], paintG[i], paintB[i]));
            paintsRand[i] = paint;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        for (int i = 0; i < ovalRandLength; ++i)
        {
            canvas.drawOval(rects[i], paintsRand[i]);

            canvas.drawRoundRect(rects2[i], ovalW[i] / 4.0f, ovalH[i] / 4.0f, paintsRand[i]);
        }

        invalidate();
    }
}*/

enum TestMode
{
  ellipseMode,
  rrectMode,
  triangleMode,
  rectMode,
  triangleMode_drawPoint,
    quadraticMode,
};

public class JoinedShapesView extends View {

    private Paint paint, paintsRand[], ppaint;
    private int ovalRandLength, quadRandLength, paintR[], paintG[], paintB[];
    private float ovalX[], ovalY[], ovalW[], ovalH[];
    private float quadX1[], quadY1[], quadX2[], quadY2[], quadX3[], quadY3[];
    private RectF rects[];
    //private PointF trianglePts[][];
    private float trianglePts[];

    private Path ovalPath = new Path(), trianglePath[], quadraticPath[];

    private boolean pathMode = false;

    private TestMode testmode = TestMode.ellipseMode;

    private long lastFrameTime = 0;
    private int frameCount = 0;
    private int fps = 0;
    private static native float[] passRandData(int id);
    private static native int[] passRandRGB(int id);
    private static native int getArraySize(float[] ptr);

    static {
       System.loadLibrary("androidtest2");
    }


    public JoinedShapesView(Context context) {
        super(context);
        init();
    }

    public JoinedShapesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private RadialGradient radialGradient;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Create a RadialGradient object
        radialGradient = new RadialGradient(
                w / 2f, h / 2f, // Center of the gradient
                Math.min(w, h) / 2f, // Radius of the gradient
                new int[]{Color.RED, Color.GREEN, Color.BLUE}, // Colors to use for the gradient
                null, // Positions for each color (null means evenly spaced)
                Shader.TileMode.CLAMP // Tile mode (CLAMP means the edge color extends to infinity)
        );
        paint.setShader(radialGradient);
    }

    private void init() {
        paint = new Paint();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        ppaint = new Paint();
        ppaint.setColor(Color.BLACK);

        ppaint.setTextSize(50);
        ppaint.setAntiAlias(true);
        ovalX = passRandData(0);
        ovalY = passRandData(1);
        ovalW = passRandData(2);
        ovalH = passRandData(3);
        ovalRandLength = getArraySize(ovalX);
        quadRandLength = ovalRandLength;
        paintR = passRandRGB(0);
        paintG = passRandRGB(1);
        paintB = passRandRGB(2);
        rects = new RectF[ovalRandLength];
        int cnt = 0;
        for (int i = 0; i < ovalRandLength; ++i)
        {
            rects[i] = new RectF(ovalX[i] - ovalW[i] / 2.0f, ovalY[i] - ovalH[i] / 2.0f, ovalW[i] / 2.0f + ovalX[i], ovalH[i] / 2.0f + ovalY[i]);
            //for (int j = 0; j < 3; ++j) trianglePts[i][j] = new PointF();
            switch (testmode)
            {
                case ellipseMode:
                    ovalPath.addOval(rects[i], Path.Direction.CW);
                    break;
                case rrectMode:
                    //ovalPath.addRoundRect(rects[i], ovalW[i] / 4.0f, ovalH[i] / 4.0f, Path.Direction.CW);
                    break;
                case triangleMode:
                    trianglePath[i].moveTo(quadX1[i], quadY1[i]);
                    trianglePath[i].lineTo(quadX2[i], quadY2[i]);
                    trianglePath[i].lineTo(quadX3[i], quadY3[i]);
                    trianglePath[i].close();
                    break;
                case triangleMode_drawPoint:
                    trianglePts[cnt] = quadX1[i]; ++cnt;
                    trianglePts[cnt] = quadY1[i]; ++cnt;
                    trianglePts[cnt] = quadX2[i]; ++cnt;
                    trianglePts[cnt] = quadY2[i]; ++cnt;
                    trianglePts[cnt] = quadX3[i]; ++cnt;
                    trianglePts[cnt] = quadY3[i]; ++cnt;
                    break;
                case quadraticMode:
                    quadraticPath[i].moveTo(quadX1[i], quadY1[i]);
                    quadraticPath[i].quadTo(quadX2[i], quadY2[i], quadX3[i], quadY3[i]);
                    break;
            }
        }
        paintsRand = new Paint[ovalRandLength];
        for (int i = 0; i < ovalRandLength; ++i)
        {
            //paint.setColor(0Xff000000 + (paintR[i] << 16) + (paintG[i] << 8) + (paintB[i]));
            paint = new Paint();
            //paint.setColor(Color.BLACK);
            paint.setStrokeWidth(1);
            paint.setColor(Color.rgb(paintR[i], paintG[i], paintB[i]));
            float x1 = (float) (ovalX[i] - ovalW[i] / 2.0), y1 = (float) (ovalY[i] - ovalH[i] / 2.0);
            float x2 = (float) (ovalX[i] + ovalW[i] / 2.0), y2 = (float) (ovalY[i] + ovalH[i] / 2.0);


            if ((i % 300) < 100) {

            }
            else if ((i % 300) < 200) {

            }
            else {
                Shader shader = new LinearGradient(x1, y1, x2, y2, Color.rgb(paintR[i], paintG[i], paintB[i]), Color.WHITE, Shader.TileMode.CLAMP);
            //    paint.setShader(shader);
            }
            paintsRand[i] = paint;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Calculate time between frames
        long currentTime = System.nanoTime();
        long deltaTime = currentTime - lastFrameTime;

        // Update frame count
        frameCount++;

        // Calculate FPS every second
        if (deltaTime >= TimeUnit.SECONDS.toNanos(1)) {
            fps = (int) (frameCount / TimeUnit.NANOSECONDS.toSeconds(deltaTime));
            frameCount = 0;
            lastFrameTime = currentTime;
        }

        super.onDraw(canvas);

       /* int lev = 0;

        canvas.clipRect(0, 0, 1500 - lev * 100, 900);*/
        int lev = 0;

        canvas.clipRect(0, 0, 900, 1500 - lev * 100);

        switch (testmode)
        {
            case ellipseMode:
                if (pathMode)
                {
                    canvas.drawPath(ovalPath, paintsRand[0]);
                }
                else
                {
                    for (int i = 0; i < ovalRandLength; ++i)
                    {
                        canvas.drawOval(rects[i], paintsRand[i]);
                        //canvas.drawCircle(rects[i].centerX(), rects[i].centerY(), rects[i].width() / 2.0f, paintsRand[i%3]);
                    }

                }
                //
                break;
            case rrectMode:
                if (pathMode)
                {
                    canvas.drawPath(ovalPath, paintsRand[0]);
                }
                else
                {
                //    canvas.save();
                    for (int i = 0; i < ovalRandLength; ++i)
                    {
                        //canvas.drawRoundRect(rects[i], ovalW[i] / 4.0f, ovalH[i] / 4.0f, paintsRand[i]);
                        if ((i % 300) < 100) {
                            canvas.drawOval(rects[i], paintsRand[i]);
                        }
                        else if ((i % 300) < 200) {
                            canvas.drawRoundRect(rects[i], ovalW[i] / 4.0f, ovalH[i] / 4.0f, paintsRand[i]);
                        }
                        else {
                            canvas.drawRoundRect(rects[i], ovalW[i] / 4.0f, ovalH[i] / 4.0f, paintsRand[i]);
                        }
                    }
                //    canvas.restore();
                }
                break;
            case triangleMode:
                for (int i = 0; i < quadRandLength; ++i)
                {
                    canvas.drawPath(trianglePath[i], paintsRand[i]);
                }
                break;
            case triangleMode_drawPoint:
                for (int i = 0; i < quadRandLength; ++i)
                {
                //    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, trianglePts.length, trianglePts, 0, null, 0, null, 0, null, 0, 0, paintsRand[0]);
                //    canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 6, trianglePts, 6 * i, null, 0, null, 0, null, 0, 0, paintsRand[i]); // failed
                }
                break;
            case rectMode:
                for (int i = 0; i < ovalRandLength; ++i)
                {
                    canvas.drawRect(rects[i], paintsRand[i]);
                }
                break;
            case quadraticMode:
                for (int i = 0; i < quadRandLength; ++i)
                {
                    canvas.drawPath(quadraticPath[i], paintsRand[i]);
                }
                break;
        }

        String fpsText = "FPS: " + fps;
        canvas.drawText(fpsText, 10, 60, ppaint);

        invalidate();
    }
    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint();
        paint.setColor(Color.BLUE); // Set the color of the arc
        paint.setStrokeWidth(10); // Set the stroke width
        paint.setStyle(Paint.Style.STROKE); // Set the style to stroke to draw only the arc outline
        // You can also use Paint.Style.FILL for a solid arc

        // Define the dimensions and angles for the arc
        float left = 100;   // x-coordinate of the left side of the rectangle
        float top = 100;    // y-coordinate of the top side of the rectangle
        float right = 300;  // x-coordinate of the right side of the rectangle
        float bottom = 200; // y-coordinate of the bottom side of the rectangle
        float startAngle = 0;   // Starting angle (in degrees) where the arc begins
        float sweepAngle = 120; // Sweep angle (in degrees) measured clockwise
        // Draw the arc
        canvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, false, paint);
        invalidate();
    }*/
}
