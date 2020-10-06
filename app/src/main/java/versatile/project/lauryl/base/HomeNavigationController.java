package versatile.project.lauryl.base;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayDeque;
import java.util.Queue;

import versatile.project.lauryl.R;
import versatile.project.lauryl.payment.PaymentErrorFragment;
import versatile.project.lauryl.payment.PaymentFragment;
import versatile.project.lauryl.payment.PaymentSuccessFragment;


public class HomeNavigationController implements FragmentManager.OnBackStackChangedListener {

    private Context context;
    FragmentManager mFragmentManager;
    Queue<DeferredFragmentTransaction> deferredFragmentTransactions=new ArrayDeque<>();
    private static final HomeNavigationController ourInstance = new HomeNavigationController();

   public static HomeNavigationController getInstance(Context context) {
        ourInstance.context=context;
        ourInstance.mFragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
        return ourInstance;
    }

    public void addPaymentFragment(){
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,PaymentFragment.newInstance(PaymentFragment.PaymentTypeUpi),PaymentFragment.TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addPaymentSuccessFragment(){
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, PaymentSuccessFragment.newInstance(),PaymentSuccessFragment.TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addPaymentErrorFragment(){
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, PaymentErrorFragment.newInstance(),PaymentErrorFragment.TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private HomeNavigationController() {
    }
    @Override
    public void onBackStackChanged() {

    }

    public Queue<DeferredFragmentTransaction> getDeferredFragmentTransactions() {
        return deferredFragmentTransactions;
    }

    public void setDeferredFragmentTransactions(Queue<DeferredFragmentTransaction> deferredFragmentTransactions) {
        this.deferredFragmentTransactions = deferredFragmentTransactions;
    }
}
