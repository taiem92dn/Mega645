package vn.dinosys.mega645.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by huutai.
 * Since: 5/16/16 on 4:30 PM
 * Project: DinoHR_Android
 */
public class DialogHelper {

    public interface DialogCallback {
        void onPositiveClick();
    }

    public static AlertDialog createWarningDialog(Context pContext, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(pContext);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(pContext.getString(android.R.string.ok), null);
        return builder.create();
    }

    public static AlertDialog createConfirmDialog(Context pContext, String title, String message, DialogCallback pCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(pContext);

        builder.setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(pContext.getString(android.R.string.yes), (pDialogInterface, pI) -> {
                    if (pCallback != null) {
                        pCallback.onPositiveClick();
                    }
                })
                .setNegativeButton(pContext.getString(android.R.string.no), null);
        return builder.create();
    }

    public static AlertDialog createWarningDialog(Context pContext, int titleStrId, int messageStrId) {
        String title = pContext.getString(titleStrId);
        String message = pContext.getString(messageStrId);

        return createWarningDialog(pContext, title, message);
    }
}
