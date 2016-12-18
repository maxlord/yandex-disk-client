package ru.yandex.diskclient.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import static android.util.TypedValue.applyDimension;

/**
 * Хелпер по размерностям
 *
 * @author Lord (Kuleshov M.V.)
 * @since 21.10.16
 */
public class DimensionHelper {
	/**
	 * Переводит значение в dp в значение px для текущего контекста
	 *
	 * @param context контекст
	 * @param dp значение в dp
	 * @return
	 */
	public static int dp2px(Context context, int dp) {
		Resources r = context.getResources();

		return (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
	}
}
