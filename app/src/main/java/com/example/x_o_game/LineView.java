package com.example.x_o_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineView extends View {
    private Paint paint=new Paint();
    private View viewA,viewB;
    int color;
    int distance_middle_row=0;
    int distance_endRow=0;
    int distance_middle_coloumn=0;
    int distance__endColoumn=0;
    int distance_diagonalX=0;
    int distance_diagonalY=0;

    public void setDistance_diagonalX(int distance_diagonalX) {
        this.distance_diagonalX = distance_diagonalX;
    }

    public void setDistance_diagonalY(int distance_diagonalY) {
        this.distance_diagonalY = distance_diagonalY;
    }

    public void setDistance_middle_coloumn(int distance_middle_coloumn) {
        this.distance_middle_coloumn = distance_middle_coloumn;
    }

    public void setDistance__endColoumn(int distance__endColoumn) {
        this.distance__endColoumn = distance__endColoumn;
    }

    public void setDistance_endRow(int distance_endRow) {
        this.distance_endRow = distance_endRow;
    }

    public void setDistanceMiddleRow(int distance) {
        this.distance_middle_row = distance;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setViewA(View viewA) {
        this.viewA = viewA;
    }

    public void setViewB(View viewB) {
        this.viewB = viewB;
    }

    public LineView(Context context) {
        super(context);

    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(getResources().getColor(color));
      //  paint.setColor(Color.WHITE);
        paint.setStrokeWidth(20);
        canvas.drawLine(viewA.getX()+distance_middle_coloumn+distance_diagonalX,viewA.getY()+distance_middle_row,viewB.getX()+distance_endRow+distance_middle_coloumn+distance_diagonalX,viewB.getY()+distance_middle_row+distance__endColoumn+distance_diagonalY,paint);
        super.onDraw(canvas);
    }
    public void draw(){
        invalidate();
        requestLayout();
    }
}
