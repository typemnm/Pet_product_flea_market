package com.example.pet_products_flea_market;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;
import androidx.appcompat.widget.AppCompatImageView;

public class ZoomImageView extends AppCompatImageView {

    private Matrix matrix = new Matrix();
    private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.0f;

    // 드래그(이동) 변수
    private PointF lastTouch = new PointF();
    private PointF startTouch = new PointF();
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private int mode = NONE;

    private boolean isInit = false; // 초기화 여부 체크

    public ZoomImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        setScaleType(ScaleType.MATRIX);

        // 뷰가 화면에 그려질 때 이미지를 화면 중앙에 맞추는 리스너 추가
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isInit) {
                    fitToScreen(); // 화면에 꽉 차게 맞추기
                    isInit = true; // 한 번만 실행
                }
            }
        });
    }

    /**
     * 이미지를 화면 크기에 맞춰 중앙 정렬하고 스케일링하는 함수
     */
    private void fitToScreen() {
        Drawable d = getDrawable();
        if (d == null) return;

        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int drawableWidth = d.getIntrinsicWidth();
        int drawableHeight = d.getIntrinsicHeight();

        if (viewWidth == 0 || viewHeight == 0 || drawableWidth == 0 || drawableHeight == 0) return;

        float scale;
        float dx, dy;

        // 이미지와 뷰의 비율을 비교하여 스케일 계산
        if (drawableWidth * viewHeight > viewWidth * drawableHeight) {
            scale = (float) viewWidth / (float) drawableWidth;
            dx = 0;
            dy = (viewHeight - drawableHeight * scale) * 0.5f;
        } else {
            scale = (float) viewHeight / (float) drawableHeight;
            dx = (viewWidth - drawableWidth * scale) * 0.5f;
            dy = 0;
        }

        matrix.setScale(scale, scale);
        matrix.postTranslate(dx, dy);
        setImageMatrix(matrix);

        // 현재 배율 저장 (줌 기능에서 사용)
        scaleFactor = scale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        PointF currentTouch = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouch.set(currentTouch);
                startTouch.set(lastTouch);
                mode = DRAG;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    float dx = currentTouch.x - lastTouch.x;
                    float dy = currentTouch.y - lastTouch.y;

                    // 이동 적용
                    matrix.postTranslate(dx, dy);
                    setImageMatrix(matrix);

                    lastTouch.set(currentTouch.x, currentTouch.y);
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            scaleFactor *= scale;

            // 배율 제한 (너무 작아지거나 커지지 않게)
            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 5.0f));

            matrix.postScale(scale, scale, detector.getFocusX(), detector.getFocusY());
            setImageMatrix(matrix);
            return true;
        }
    }
}