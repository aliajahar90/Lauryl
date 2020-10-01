package versatile.project.lauryl.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {
    private BaseActivity myActivity;
    public BaseFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViewModel();
        this.setHasOptionsMenu(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            this.myActivity= (BaseActivity) context;
        }
    }

    @Override
    public void onDetach() {
        this.myActivity=null;
        super.onDetach();
    }

    public BaseActivity getMyActivity() {
        return myActivity;
    }
    protected abstract void initializeViewModel();
}
