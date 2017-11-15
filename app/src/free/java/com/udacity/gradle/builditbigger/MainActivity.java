/*
 * Copyright (c) 2017 Ian Buttimer.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import timber.log.Timber;


public class MainActivity extends AbstractMainActivity {

    private InterstitialAd mInterstitialAd;

    private String mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mJoke = null;

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(mAdListener);
        loadAd();
    }

    private void loadAd() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void showJoke(String joke) {
        if (!TextUtils.isEmpty(mJoke)) {
            // have joke from before ad
            super.showJoke(mJoke);
            mJoke = null;
        } else if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mJoke = joke;   // save for after ad
        } else {
            Timber.d("The interstitial wasn't loaded yet.");
            super.showJoke(joke);
        }
    }

    private AdListener mAdListener = new AdListener() {
        @Override
        public void onAdClosed() {
            // Code to be executed when when the interstitial ad is closed.
            showJoke(mJoke);

            loadAd();
        }
    };
}
