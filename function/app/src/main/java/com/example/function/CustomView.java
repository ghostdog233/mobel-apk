package com.example.axischart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class CustomView extends View {
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }

    String strFunction;

    float ix,iy=1;
    float dx,dy=0;
    @Override
    protected void onDraw(Canvas canvas) {
        if(strFunction==null || strFunction.length()==0)
            return;
        Rect rect=new Rect();
        rect.left=0;
        rect.top=0;
        rect.right=getWidth();
        rect.bottom=getHeight();
        canvas.scale(ix, iy, getWidth()/2, getHeight()/2);
        canvas.translate(dx, dy);
        Axis axis=new Axis(rect);
        axis.draw(canvas);
        Plot plot=new Plot(axis,strFunction,"x");
        plot.draw(canvas);
    }
    protected void ZoomIn(){
        ix = ix +0.1f;
        iy = iy +0.1f;
    }
    protected void ZoomOut(){
        ix = ix -0.1f;
        iy = iy -0.1f;
    }
    protected void NoZoom(){
        ix = 2.0f;
        iy = 2.0f;
    }
    protected void up(){
        dy=dy+30;
    }
    protected void down(){
        dy=dy-30;
    }
    protected void right(){
        dx=dx-30;
    }
    protected void left(){
        dx=dx+30;
    }
}