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
import versatile.project.lauryl.profile.ProfileFragment;
import versatile.project.lauryl.screens.HomeScreen;


public class HomeNavigationController implements FragmentManager.OnBackStackChangedListener {

    private Context context;
    FragmentManager mFragmentManager;
    Queue<DeferredFragmentTransaction> deferredFragmentTransactions=new ArrayDeque<>();
    private static HomeNavigationController ourInstance=new HomeNavigationController();

   public static HomeNavigationController getInstance(Context context) {
           ourInstance.context = context;
           ourInstance.mFragmentManager = ((HomeScreen) ourInstance.context).getSupportFragmentManager();
        return ourInstance;
    }

    public void addPaymentFragment(){
        FragmentTransaction fragmentTransaction= ourInstance.mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,PaymentFragment.newInstance(PaymentFragment.PaymentTypeUpi),PaymentFragment.TAG);
        enableBackButton();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addPaymentSuccessFragment(){
        FragmentTransaction fragmentTransaction= ourInstance.mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, PaymentSuccessFragment.newInstance(),PaymentSuccessFragment.TAG);
        disableBackButton();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addPaymentErrorFragment(){
        FragmentTransaction fragmentTransaction= ourInstance.mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, PaymentErrorFragment.newInstance(),PaymentErrorFragment.TAG);
        disableBackButton();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addProfileFragment(){
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, ProfileFragment.newInstance(),ProfileFragment.TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private HomeNavigationController() {
    }
    @Override
    public void onBackStackChanged() {

    }
    public void disableBackButton(){
       if(context instanceof HomeScreen){
           ((HomeScreen)context).hideBackButton();
       }
    }
    public void enableBackButton(){
        if(context instanceof HomeScreen){
            ((HomeScreen)context).showBackButton();
        }
    }

    public Queue<DeferredFragmentTransaction> getDeferredFragmentTransactions() {
        return deferredFragmentTransactions;
    }

    public void setDeferredFragmentTransactions(Queue<DeferredFragmentTransaction> deferredFragmentTransactions) {
        this.deferredFragmentTransactions = deferredFragmentTransactions;
    }
}
