package versatile.project.lauryl.dialogs;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import versatile.project.lauryl.R;
import versatile.project.lauryl.databinding.BottomsheetDialogErrorBinding;
import versatile.project.lauryl.dialogs.data.SharedErrorData;
import versatile.project.lauryl.profile.ProfileFragment;
import versatile.project.lauryl.profile.data.ProfileSharedData;
import versatile.project.lauryl.utils.AllConstants;

public class ErrorBottomSheetDialog extends BottomSheetDialogFragment {
    public static final String TAG = ErrorBottomSheetDialog.class.getName();
    private SharedErrorData sharedErrorData;
    private BottomsheetDialogErrorBinding bottomsheetDialogErrorBinding;
    private DialogClickListener dialogClickListener;

    public static ErrorBottomSheetDialog newInstance(String errorData, DialogClickListener dialogClickListener) {
        ErrorBottomSheetDialog errorBottomSheetDialog = new ErrorBottomSheetDialog();
        try {
            errorBottomSheetDialog.sharedErrorData = new Gson().fromJson(errorData, SharedErrorData.class);
        } catch (Exception e) {
            Log.d("Error", "Null Pointer");
        }
        errorBottomSheetDialog.dialogClickListener = dialogClickListener;
        return errorBottomSheetDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomsheetDialogErrorBinding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_dialog_error, container, false);
        return bottomsheetDialogErrorBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (sharedErrorData.getNameValuePairs().getLogoId() != -1) {
            bottomsheetDialogErrorBinding.imgErrorLogo.setImageResource(sharedErrorData.getNameValuePairs().getLogoId());
        }
        bottomsheetDialogErrorBinding.imgErrorLogo.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(sharedErrorData.getNameValuePairs().getTile())) {
            bottomsheetDialogErrorBinding.txtTitle.setVisibility(View.VISIBLE);
            bottomsheetDialogErrorBinding.txtTitle.setText(sharedErrorData.getNameValuePairs().getTile());
        }
        if (!TextUtils.isEmpty(sharedErrorData.getNameValuePairs().getMsg())) {
            bottomsheetDialogErrorBinding.txtMsg.setVisibility(View.VISIBLE);
            bottomsheetDialogErrorBinding.txtMsg.setText(sharedErrorData.getNameValuePairs().getMsg());
        }
        if (!TextUtils.isEmpty(sharedErrorData.getNameValuePairs().getCancelTxt())) {
            bottomsheetDialogErrorBinding.btnCancel.setVisibility(View.VISIBLE);
            bottomsheetDialogErrorBinding.btnCancel.setText(sharedErrorData.getNameValuePairs().getCancelTxt());
        }
        if (!TextUtils.isEmpty(sharedErrorData.getNameValuePairs().getProceedTxt())) {
            bottomsheetDialogErrorBinding.btnProceed.setVisibility(View.VISIBLE);
            bottomsheetDialogErrorBinding.btnProceed.setText(sharedErrorData.getNameValuePairs().getProceedTxt());
        }
        bottomsheetDialogErrorBinding.btnProceed.setOnClickListener(view -> {
            dismiss();
            dialogClickListener.dialogProceed();
        });

        bottomsheetDialogErrorBinding.btnCancel.setOnClickListener(view -> {
            dismiss();
            dialogClickListener.dialogCancelled();
        });
    }

    public interface DialogClickListener {
        void dialogProceed();

        void dialogCancelled();
    }
}
