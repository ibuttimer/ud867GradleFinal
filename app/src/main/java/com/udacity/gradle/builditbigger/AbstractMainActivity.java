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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ie.ianbuttimer.androidjoker.LibraryActivity;
import ie.ianbuttimer.joker.Joker;
import timber.log.Timber;


public abstract class AbstractMainActivity extends AppCompatActivity {

    @Nullable private Joker mJoker;

    private Button mButton;

    private ImplementationStep mStep;

    protected IMainActivityFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.tell_joke_button);

        mStep = ImplementationStep.getConfiguredStep(this);

        switch (mStep) {
            case STEP_1:
                mJoker = new Joker();
                break;
            case STEP_2:
                mJoker = new Joker();
                execute(EndpointsAsyncTask.makeSayHiArg("Manfred"));
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Click handler for button
     * @param view  View clicked
     */
    public void tellJoke(View view) {
        switch (mStep) {
            case STEP_1:
                if (mJoker != null) {
                    Toast.makeText(this, mJoker.getAJoke(), Toast.LENGTH_LONG).show();
                }
                break;
            case STEP_2:
                if (mJoker != null) {
                    showJoke(mJoker.getAJoke());
                }
                break;
            default:
                execute(EndpointsAsyncTask.makeTellAJokeArg());
                break;
        }
    }

    /**
     * Execute an asynctask request
     * @param args  Task arguments
     */
    protected void execute(Bundle args) {
        EndpointsAsyncTask mAsyncTask = new EndpointsAsyncTask(this, mStep, mInterface);
        mAsyncTask.execute(args);
        if (mStep == ImplementationStep.STEP_3) {
            showInProgress();
        }
    }

    /**
     * Show the joke
     * @param joke  Joke to show
     */
    protected void showJoke(String joke) {
        Intent intent = new Intent(this, LibraryActivity.class);
        intent.putExtra(LibraryActivity.ARG_JOKE, joke);
        startActivity(intent);
    }

    /**
     * Interface to handle results
     */
    private EndpointsAsyncTask.IEndPoints mInterface = new EndpointsAsyncTask.IEndPoints() {
        @Override
        public void onPostExecute(String[] results) {
            hideInProgress();

            for (String result : results) {
                showJoke(result);
            }
        }
    };


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        try {
            mFragment = (IMainActivityFragment) fragment;
        } catch (ClassCastException e) {
            Timber.e("Fragment must implement IMainActivityFragment");
        }
    }

    public interface IMainActivityFragment {
        /** Show in progress indicator */
        void showInProgress();
        /** Hide in progress indicator */
        void hideInProgress();
    }

    /** Show in progress indicator */
    void showInProgress() {
        mButton.setEnabled(false);
        if (mFragment != null) {
            mFragment.showInProgress();
        }
    }

    /** Hide in progress indicator */
    void hideInProgress() {
        mButton.setEnabled(true);
        if (mFragment != null) {
            mFragment.hideInProgress();
        }
    }

}
