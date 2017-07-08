package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import com.bignerdranch.android.criminalintent.CrimeListFragment;

/**
 * Created by sonalisahu on 4/28/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
