package versatile.project.lauryl.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayDeque;
import java.util.Queue;

import versatile.project.lauryl.R;
import versatile.project.lauryl.home.HomeFragment;
import versatile.project.lauryl.payment.PaymentErrorFragment;
import versatile.project.lauryl.payment.PaymentFragment;
import versatile.project.lauryl.payment.PaymentSuccessFragment;
import versatile.project.lauryl.profile.ProfileFragment;
import versatile.project.lauryl.screens.HomeScreen;
import versatile.project.lauryl.utils.AllConstants;


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
    public void addPaymentSuccessFragment(String bundleJsonData) {
        FragmentTransaction fragmentTransaction= ourInstance.mFragmentManager.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString(AllConstants.Payment.PaymentData,bundleJsonData);
        PaymentSuccessFragment paymentSuccessFragment= PaymentSuccessFragment.newInstance();
        paymentSuccessFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragContainer, paymentSuccessFragment,PaymentSuccessFragment.TAG);
        disableBackButton();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void addPaymentErrorFragment(String bundleJsonData){
        FragmentTransaction fragmentTransaction= ourInstance.mFragmentManager.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString(AllConstants.Payment.PaymentData,bundleJsonData);
        PaymentErrorFragment paymentErrorFragment= PaymentErrorFragment.newInstance();
        paymentErrorFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragContainer, paymentErrorFragment,PaymentErrorFragment.TAG);
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
    public void addHomeFragment(HomeFragment homeFragment){
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,homeFragment,HomeFragment.class.getName());
        fragmentTransaction.addToBackStack(HomeFragment.class.getName());
        fragmentTransaction.commit();
        if((context instanceof HomeScreen)){
            ((HomeScreen)context).selectHomeDashboard();
        }
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
