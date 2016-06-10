package androidfanatic.todo.pref;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidfanatic.todo.R;
import androidfanatic.todo.utils.ColorUtil;
import androidfanatic.todo.widget.TaskWidgetProvider;

public class ColorPickerPreference extends android.preference.DialogPreference implements SeekBar.OnSeekBarChangeListener {

    private SeekBar redSeekbar;
    private SeekBar greenSeekbar;
    private SeekBar blueSeekbar;
    private SeekBar alphaSeekbar;
    private View colorView;
    private Drawable drawable;

    public ColorPickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ColorPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ColorPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorPickerPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setDialogLayoutResource(R.layout.color_picker_dialog);
    }


    @Override protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        redSeekbar = (SeekBar) view.findViewById(R.id.sb_red);
        greenSeekbar = (SeekBar) view.findViewById(R.id.sb_green);
        blueSeekbar = (SeekBar) view.findViewById(R.id.sb_blue);
        alphaSeekbar = (SeekBar) view.findViewById(R.id.sb_alpha);
        colorView = view.findViewById(R.id.view_color);

        // handlers
        redSeekbar.setOnSeekBarChangeListener(this);
        greenSeekbar.setOnSeekBarChangeListener(this);
        blueSeekbar.setOnSeekBarChangeListener(this);
        alphaSeekbar.setOnSeekBarChangeListener(this);

        // set and remove colors

        String colorStr = getPersistedString(ColorUtil.defaultColorHex);

        Log.e("heuy", colorStr);

        int color = Color.parseColor(colorStr);
        float r = (color >> 16) & 0xFF;
        float g = (color >> 8) & 0xFF;
        float b = (color >> 0) & 0xFF;
        float a = (color >> 24) & 0xFF;

        setPreviewColor(color);

        redSeekbar.setProgress((int) (r * 100 / 255));
        greenSeekbar.setProgress((int) (g * 100 / 255));
        blueSeekbar.setProgress((int) (b * 100 / 255));
        alphaSeekbar.setProgress((int) (a * 100 / 255));
    }

    @Override protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            persistString(ColorUtil.intToHex(getColorFromSeekbars()));
        }
        TaskWidgetProvider.updateWidgets(getContext().getApplicationContext());
    }

    private int getColorFromSeekbars() {
        int a = (int) (alphaSeekbar.getProgress() * 2.55);
        int r = (int) (redSeekbar.getProgress() * 2.55);
        int g = (int) (greenSeekbar.getProgress() * 2.55);
        int b = (int) (blueSeekbar.getProgress() * 2.55);
        return Color.argb(a, r, g, b);
    }

    @Override public void onProgressChanged(SeekBar seekBar, int i, boolean be) {
        setPreviewColor(getColorFromSeekbars());
    }

    private void setPreviewColor(int color) {
        colorView.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

