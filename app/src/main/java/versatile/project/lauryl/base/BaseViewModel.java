package versatile.project.lauryl.base;

import android.text.TextUtils;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public boolean basicInputValidation(String keyToValidate, int length) {
        if (!TextUtils.isEmpty(keyToValidate) && keyToValidate.length() == length) {
            return true;
        }
        return false;
    }
}
