package demo.base.android.com.weight.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by fanlongbo on 2017/7/31.
 * 有圆角的ImageView
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    private float radiusX = 10;
    private float radiusY = 10;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }

    public void setRadiusY(float radiusY) {
        this.radiusY = radiusY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        RectF rectF = new RectF(rect);
        path.addRoundRect(rectF, radiusX, radiusY, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);
        super.onDraw(canvas);
    }
}
