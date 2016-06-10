package androidfanatic.todo.utils;

import android.graphics.Color;

public class ColorUtil {

    public static String defaultColorHex = "#FFFFFFFF";
    public static int defaultColorInt = 0xFFFFFFFF;

    public static String intToHex(int color) {
        return String.format("#%08X", (defaultColorInt & color));
    }

    public static int hex2int(String hex) {
        try {
            return Color.parseColor(hex);
        } catch (Exception e) {
            return defaultColorInt;
        }
    }

}
