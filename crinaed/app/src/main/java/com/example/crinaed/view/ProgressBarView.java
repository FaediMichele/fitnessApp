package com.example.crinaed.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.crinaed.R;

import java.text.DecimalFormat;
public class ProgressBarView extends View {
    private Paint paint;
    private Paint backgroundPaint;
    protected Paint textPaint;

    private RectF rectF = new RectF();
    private RectF rectFBackground = new RectF();

    private float strokeWidth;
    private float backgroundStrokeWidth;

    private float progress = 0;
    private int max = 100;
    private int foregroundColor;
    private int backgroundColor;
    private int textColor = Color.rgb(66, 145, 241);
    private float textSize = 50;
    private float arcAngle = 90;
    private int startAngle = 0;
    private String suffixText = "%";
    private boolean showText = true;
    private final int min_size = (int) dp2px(getResources(), 100);
    private float arcBottomHeight;


    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "strokeWidth";
    private static final String INSTANCE_BACKGROUND_STROKE_WIDTH = "backgroundStrokeWidth";
    private static final String INSTANCE_TEXT_SIZE = "percentageTextSize";
    private static final String INSTANCE_TEXT_COLOR = "percentageTextColor";
    private static final String INSTANCE_SHOW_TEXT = "showPercentage";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_MAX = "angleMax";
    private static final String INSTANCE_FOREGROUND_STROKE_COLOR = "foregroundColor";
    private static final String INSTANCE_BACKGROUND_STROKE_COLOR = "backgroundColor";
    private static final String INSTANCE_ARC_ANGLE = "angle";
    private static final String INSTANCE_START_ANGLE = "startAngle";

    public ProgressBarView(Context context) {
        this(context, null);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressBarView, defStyleAttr, 0);
        initByAttributes(attributes, context);
        attributes.recycle();

        initPainters();
    }

    protected void initByAttributes(TypedArray attributes, final Context context) {
        foregroundColor = attributes.getColor(R.styleable.ProgressBarView_foregroundColor, getThemeColor(context, R.attr.colorPrimary));
        backgroundColor = attributes.getColor(R.styleable.ProgressBarView_backgroundColor, getThemeColor(context, R.attr.colorPrimaryDark));
        textColor = attributes.getColor(R.styleable.ProgressBarView_percentageTextColor, getThemeColor(context, R.attr.colorPrimaryDark));
        textSize = attributes.getDimension(R.styleable.ProgressBarView_percentageTextSize, textSize);
        arcAngle = attributes.getFloat(R.styleable.ProgressBarView_angle, arcAngle);
        setMax(attributes.getInt(R.styleable.ProgressBarView_angleMax, max));
        setProgress(attributes.getFloat(R.styleable.ProgressBarView_progress, progress));
        strokeWidth = attributes.getDimension(R.styleable.ProgressBarView_strokeWidth, strokeWidth);
        backgroundStrokeWidth = attributes.getDimension(R.styleable.ProgressBarView_backgroundStrokeWidth, strokeWidth);
        if(backgroundStrokeWidth > strokeWidth){
            backgroundStrokeWidth = strokeWidth/2;
        }
        showText = attributes.getBoolean(R.styleable.ProgressBarView_showPercentage, showText);
        startAngle = attributes.getInteger(R.styleable.ProgressBarView_startAngle, startAngle);
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT); // set the arc line style(rounded o not)

        backgroundPaint = new Paint();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStrokeWidth(backgroundStrokeWidth);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeCap(Paint.Cap.BUTT); // set the arc line style(rounded o not)
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public float getBackgroundStrokeWidth() {
        return backgroundStrokeWidth;
    }

    public void setBackgroundStrokeWidth(float backgroundStrokeWidth) {
        this.backgroundStrokeWidth = Math.min(backgroundStrokeWidth, this.strokeWidth);
        this.invalidate();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String stringDecimal = decimalFormat.format(progress);
        stringDecimal = stringDecimal.replace(",",".");
        this.progress = Float.parseFloat(stringDecimal);

        if (this.progress > getMax()) {
            Log.d("ProgressBarView", "progress: "+ this.progress  + ", max: " + getMax());
            this.progress = getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
        this.invalidate();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.invalidate();
    }

    public float getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
        this.invalidate();
    }

    public boolean getShowPercentage() {
        return showText;
    }

    public void setShowPercentage(boolean showText) {
        this.showText = showText;
        this.invalidate();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return min_size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int finalWidth;
        int finalHeight;
        float backgroundStrokeDiff = (strokeWidth - backgroundStrokeWidth) / 2f;
        int incDim = backgroundStrokeDiff > 0 ? 0 : (int) backgroundStrokeDiff * -2;
        if (originalWidth > originalHeight)
        {
            finalWidth = originalHeight;
            finalHeight = originalHeight;
        }
        else
        {
            finalWidth = originalWidth;
            finalHeight = originalWidth;
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth + incDim, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight + incDim, MeasureSpec.EXACTLY));
        rectF.set(strokeWidth / 2f, strokeWidth / 2f, finalWidth - strokeWidth / 2f, finalHeight - strokeWidth / 2f);
        rectFBackground.set(backgroundStrokeWidth / 2f +backgroundStrokeDiff, backgroundStrokeWidth / 2f + backgroundStrokeDiff, finalWidth - backgroundStrokeWidth / 2f -backgroundStrokeDiff, finalHeight - backgroundStrokeWidth / 2f - backgroundStrokeDiff);
        float radius = finalWidth / 2f;
        float angle = (360 - arcAngle) / 2f;
        arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 270 - arcAngle + this.startAngle;
        float finishedSweepAngle = (progress < 1 ? 1f : progress) / (float) getMax() * arcAngle;
        float finishedStartAngle = startAngle;
        if(progress == 0){
            finishedStartAngle = 0.01f + this.startAngle;
        }
        backgroundPaint.setColor(backgroundColor);
        canvas.drawArc(rectFBackground, startAngle, arcAngle, false, backgroundPaint);

        paint.setColor(foregroundColor);
        canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, paint);

        // Write the percentage
        if(showText) {
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            float textHeight = textPaint.descent() + textPaint.ascent();
            float textBaseline = (getHeight() - textHeight) / 2.0f;
            canvas.drawText(Float.toString(progress) + suffixText, (getWidth() - textPaint.measureText(Float.toString(progress)) - textPaint.measureText(suffixText)) / 2.0f, textBaseline, textPaint);
        }


        if(arcBottomHeight == 0) {
            float radius = getWidth() / 2f;
            float angle = (360 - arcAngle) / 2f;
            arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
        bundle.putFloat(INSTANCE_BACKGROUND_STROKE_WIDTH, getBackgroundStrokeWidth());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_PROGRESS, getProgress());
        bundle.putBoolean(INSTANCE_SHOW_TEXT, getShowPercentage());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_FOREGROUND_STROKE_COLOR, getForegroundColor());
        bundle.putInt(INSTANCE_BACKGROUND_STROKE_COLOR, getBackgroundColor());
        bundle.putFloat(INSTANCE_ARC_ANGLE, getArcAngle());
        bundle.putInt(INSTANCE_START_ANGLE, getStartAngle());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
            backgroundStrokeWidth = bundle.getFloat(INSTANCE_BACKGROUND_STROKE_WIDTH);
            if(backgroundStrokeWidth > strokeWidth){
                backgroundStrokeWidth = strokeWidth/2;
            }
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            showText = bundle.getBoolean(INSTANCE_SHOW_TEXT);
            foregroundColor = bundle.getInt(INSTANCE_FOREGROUND_STROKE_COLOR);
            backgroundColor = bundle.getInt(INSTANCE_BACKGROUND_STROKE_COLOR);
            arcAngle = bundle.getFloat(INSTANCE_ARC_ANGLE);
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getFloat(INSTANCE_PROGRESS));
            setStartAngle(bundle.getInt(INSTANCE_START_ANGLE));
            initPainters();
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int getThemeColor (final Context context, final int id) {
        final TypedValue value = new TypedValue ();
        context.getTheme ().resolveAttribute (id, value, true);
        return value.data;
    }
}
