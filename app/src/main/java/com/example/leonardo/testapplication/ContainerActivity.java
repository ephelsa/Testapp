package com.example.leonardo.testapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.leonardo.testapplication.fragment.MainFragment;

public class ContainerActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        fragmentManager = getSupportFragmentManager();

        addFragment(new MainFragment().newInstance("", ""), false, true, R.id.content_view);
    }

    @Override
    public void onBackPressed() {
        final int FRAGMENT_STACK = fragmentManager.getBackStackEntryCount();

        if (FRAGMENT_STACK >= 1) {
            fragmentManager.popBackStack();
        } else {
            addFragment(new MainFragment().newInstance("", ""), false, true, R.id.content_view);
        }
    }

    /**
     * Default method to open the fragment
     * @param fragment
     * @param addBackStack
     * @param invalidateOptionsMenu
     * @param containerId
     */
    public void addFragment(Fragment fragment, boolean addBackStack, boolean invalidateOptionsMenu, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        if (addBackStack) {
            String backStackName = fragment.getClass().getName();

            transaction.replace(containerId, fragment, backStackName);
            transaction.addToBackStack(backStackName);
        } else {
            transaction.replace(containerId, fragment);
        }

        if (invalidateOptionsMenu) {
            invalidateOptionsMenu();
        }

        transaction.commit();
    }

    /**
     * This method is to send serializable arguments to the fragment.
     * @param fragment
     * @param bundle
     * @param addBackStack
     * @param invalidateOptionsMenu
     * @param containerId
     */
    public void addFragment(Fragment fragment, @Nullable Bundle bundle, boolean addBackStack, boolean invalidateOptionsMenu, int containerId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragment.setArguments(bundle);

        if (addBackStack) {
            String backStackName = fragment.getClass().getName();

            transaction.replace(containerId, fragment, backStackName);
            transaction.addToBackStack(backStackName);
        } else {
            transaction.replace(containerId, fragment);
        }

        if (invalidateOptionsMenu) {
            invalidateOptionsMenu();
        }

        transaction.commit();
    }

    /**
     * To close itself.
     * @param fragment
     */
    public void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
    }

}
