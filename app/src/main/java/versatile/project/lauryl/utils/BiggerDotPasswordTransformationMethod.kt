package versatile.project.lauryl.utils

import android.text.method.PasswordTransformationMethod
import android.view.View

class BiggerDotPasswordTransformationMethod: PasswordTransformationMethod() {
    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(super.getTransformation(source, view))
    }

    private class PasswordCharSequence(
        val transformation: CharSequence
    ) : CharSequence by transformation {
        override fun get(index: Int): Char = if (transformation[index] == DOT) {
            BIGGER_DOT
        } else {
            transformation[index]
        }
    }

    companion object {
        private const val DOT = '\u2022'
        private const val BIGGER_DOT = '●'
    }
}