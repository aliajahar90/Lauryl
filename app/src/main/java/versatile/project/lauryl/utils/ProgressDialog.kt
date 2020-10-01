package versatile.project.lauryl.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import versatile.project.lauryl.R


object ProgressDialogHelper {
    lateinit var dialog: Dialog
    fun progressDialog(context: Context): Dialog {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null)
       // (view.findViewById(R.id.tvMessage) as TextView).text = message
        dialog.setContentView(view)
        dialog.setCancelable(false)
//        dialog.window!!.setBackgroundDrawable(
//                ColorDrawable(Color.TRANSPARENT)
//        )
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        return dialog
    }
}

data class LoadingStatus(
        var message: String,
        val isLoading: Boolean
)