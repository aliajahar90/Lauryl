package versatile.project.lauryl.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

class Globals {

    companion object {

        fun showToastMsg(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun showPopoUpDialog(context: Context, titlMsg: String, msg: String) {

            val builder = AlertDialog.Builder(context)
            builder.setTitle(titlMsg)
            builder.setMessage(msg)

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()

        }
    }

}