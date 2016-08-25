package vn.dinosys.mega645.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by huutai.
 * Since: 8/11/16 on 10:49 AM
 * Project: Mega645
 */
public class ImageLoaderUtil {

    public static void loadImage(Context pContext, String url, ImageView pImageView) {
        if (TextUtils.isEmpty(url))
            return;

        Glide.with(pContext).load(url).into(pImageView);
    }
}
