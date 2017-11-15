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

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import timber.log.Timber;

import static com.udacity.gradle.builditbigger.Utils.getManifestMetaDataString;

/**
 * Async task to communicate with GCS backend API
 */

public class EndpointsAsyncTask extends AsyncTask<Bundle, Void, String[]> {

    /** Value of setting in manifest for backend when using emulator to talk to local devappserver */
    public static final String EMULATOR_BACKEND = "emulator";
    /** Value of setting in manifest for backend when using a device to talk to local devappserver */
    public static final String LOCAL_BACKEND = "local";
    /** Value of setting in manifest for backend when using a deployed devappserver */
    public static final String DEPLOYED_BACKEND = "deployed";

    /** Key for method in arguments bundle */
    public static final String ARG_METHOD = "method";
    /** Key for name argument in arguments bundle */
    public static final String ARG_NAME = "name";

    /** Name of sayHi method */
    public static final String SAY_HI_METHOD = "sayHi";
    /** Name of tellAJoke method */
    public static final String TELL_A_JOKE_METHOD = "tellAJoke";

    private static MyApi myApiService = null;
    private Context context;
    private IEndPoints mInterface;
    private ImplementationStep mStep;

    /**
     * Constructor
     * @param context   Context to use
     * @param step      Implementation step to process
     * @param iface     Interface instance to handle results
     */
    public EndpointsAsyncTask(Context context, ImplementationStep step, IEndPoints iface) {
        this.context = context;
        this.mStep = step;
        this.mInterface = iface;
    }

    @Override
    protected String[] doInBackground(Bundle... params) {
        if (params == null) {
            return null; // nothing to do
        }

        if(myApiService == null) {  // Only do this once
            myApiService = buildService();
        }

        int length = params.length;
        String[] results = new String[length];
        for (int i = 0; i < length; i++) {
            Bundle bundle = params[i];
            String method = bundle.getString(ARG_METHOD);
            if (!TextUtils.isEmpty(method)) {
                switch (method) {
                    case SAY_HI_METHOD:
                        try {
                            String name = bundle.getString(ARG_NAME);
                            results[i] = myApiService.sayHi(name).execute().getData();
                        } catch (IOException e) {
                            Timber.e("Backend error: " + method, e);
                            results[i] = e.getMessage();
                        }
                        break;
                    case TELL_A_JOKE_METHOD:
                        try {
                            results[i] = myApiService.tellAJoke().execute().getData();
                        } catch (IOException e) {
                            Timber.e("Backend error: " + method, e);
                            results[i] = e.getMessage();
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        return results;
    }

    /**
     * Buils a service instance
     * @return  new MyApi object
     */
    protected MyApi buildService() {
        String url;
        boolean disableCompressionCfg = true;    // default turn off compression when running against local devappserver
        String backend = getManifestMetaDataString(context, R.string.backend_type);
        if (EMULATOR_BACKEND.equals(backend)) {
            url = getManifestMetaDataString(context, R.string.backend_emulator_url);
        } else if (LOCAL_BACKEND.equals(backend)) {
            url = getManifestMetaDataString(context, R.string.backend_local_url);
        } else if (DEPLOYED_BACKEND.equals(backend)) {
            url = getManifestMetaDataString(context, R.string.backend_deployed_url);
            disableCompressionCfg = true;
        } else {
            throw new IllegalArgumentException("Illegal backend type");
        }
        final boolean disableCompression = disableCompressionCfg;

        MyApi.Builder builder = new MyApi.Builder(new NetHttpTransport(),
        new JacksonFactory(), null)
        .setRootUrl(url)
        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
            @Override
            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                abstractGoogleClientRequest.setDisableGZipContent(disableCompression);
            }
        });

        return builder.build();
    }

    @Override
    protected void onPostExecute(String[] results) {
        for (String result : results) {
            switch (mStep) {
                case STEP_1:
                    // no op
                    break;
                case STEP_2:
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                    break;
                default:
                    mInterface.onPostExecute(results);
                    break;
            }
        }
    }

    /**
     * Create a sayHi argument bundle
     * @param name  Name to sat hi to
     * @return  Argument bundle
     */
    public static Bundle makeSayHiArg(String name) {
        Bundle bundle = newArgBundle(SAY_HI_METHOD);
        bundle.putString(ARG_NAME, name);
        return bundle;
    }

    /**
     * Create a new argument bundle
     * @param method  Method to call
     * @return  Argument bundle
     */
    private static Bundle newArgBundle(String method) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_METHOD, method);
        return bundle;
    }

    /**
     * Create a tellAJoke argument bundle
     * @return  Argument bundle
     */
    public static Bundle makeTellAJokeArg() {
        return newArgBundle(TELL_A_JOKE_METHOD);
    }

    /**
     * Interface to interact with Endpoints AsyncTask
     */
    public interface IEndPoints {
        /**
         * Process the results of an api endpoint call
         * @param results   Results
         */
        void onPostExecute(String[] results);
    }
}
