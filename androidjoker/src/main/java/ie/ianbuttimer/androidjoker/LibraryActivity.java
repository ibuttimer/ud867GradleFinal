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

package ie.ianbuttimer.androidjoker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;


public class LibraryActivity extends AppCompatActivity {

    /** Argument to add when passing a joke */
    public static final String ARG_JOKE = "joke";

    private TextView mJokeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Bundle bundle = savedInstanceState;
        if (bundle == null) {
            bundle = getIntent().getExtras();
        }
        String joke = null;
        if (bundle != null) {
            if (bundle.containsKey(ARG_JOKE)) {
                joke = bundle.getString(ARG_JOKE);
            }
        }
        if (TextUtils.isEmpty(joke)) {
            joke = getString(R.string.no_joke);
        }

        mJokeTextView = findViewById(R.id.tv_joke_libraryActivity);
        mJokeTextView.setText(joke);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ARG_JOKE, mJokeTextView.getText().toString());
    }
}
