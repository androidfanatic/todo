package androidfanatic.todo.pref;

import android.content.Context;
import android.util.AttributeSet;

import androidfanatic.todo.R;

public class ColorPickerPreference extends android.preference.DialogPreference {

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


    @Override protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
    }
}
