package com.example.gzoomr.gzoom;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

/**
 * 工具类
 *
 *
 */
public class Util
{

	/**
	 * 计算目标dp在当前屏幕上的实际尺寸
	 * dp2px
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp)
	{
		int px = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
						.getDisplayMetrics()));
		Log.d("fish","px="+px);
		return px;
	}

}
