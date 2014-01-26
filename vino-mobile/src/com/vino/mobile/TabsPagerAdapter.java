/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vino.mobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.vino.mobile.fragments.BottlesManagementFragment;
import com.vino.mobile.fragments.CellarManagementFragment;
import com.vino.mobile.fragments.DomainsManagementFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Domains
                return new DomainsManagementFragment();
            case 1:
                // Bottles
                return new BottlesManagementFragment();
            case 2:
                // cELLAR
                return new CellarManagementFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
 