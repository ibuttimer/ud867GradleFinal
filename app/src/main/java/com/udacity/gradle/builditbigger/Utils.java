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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.StringRes;

import timber.log.Timber;


/**
 * This class contains miscellaneous utility functions
 */
@SuppressWarnings("unused")
public class Utils {

    /**
     * Private constructor
     */
    private Utils() {
        // can't instantiate class
    }

    /**
     * Retrieve meta-data bundle from the manifest
     * @param context   Context to use
     * @return  meta-data string
     */
    public static Bundle getManifestMetaDataBundle(Context context) {
        Bundle bundle = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            bundle = ai.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
        }
        return bundle;
    }

    /**
     * Retrieve the value of a meta-data entry from the manifest
     * @param context   Context to use
     * @param key       Meta-data name
     * @return  meta-data string
     */
    public static String getManifestMetaDataString(Context context, String key) {
        String metaData = null;
        Bundle bundle = getManifestMetaDataBundle(context);
        if (bundle != null) {
            metaData = bundle.getString(key);
        }
        return metaData;
    }

    /**
     * Retrieve the value of a meta-data entry from the manifest
     * @param context   Context to use
     * @param key       Meta-data name
     * @return  meta-data string
     */
    public static String getManifestMetaDataString(Context context, @StringRes int key) {
        return getManifestMetaDataString(context, context.getString(key));
    }

    /**
     * Retrieve the value of a meta-data entry from the manifest
     * @param context   Context to use
     * @param key       Meta-data name
     * @param dfltValue Default value
     * @return  meta-data integer
     */
    public static int getManifestMetaDataInteger(Context context, String key, int dfltValue) {
        int metaData = dfltValue;
        Bundle bundle = getManifestMetaDataBundle(context);
        if (bundle != null) {
            metaData = bundle.getInt(key, dfltValue);
        }
        return metaData;
    }

    /**
     * Retrieve the value of a meta-data entry from the manifest
     * @param context   Context to use
     * @param key       Meta-data name
     * @param dfltValue Default value
     * @return  meta-data integer
     */
    public static int getManifestMetaDataInteger(Context context, @StringRes int key, int dfltValue) {
        return getManifestMetaDataInteger(context, context.getString(key), dfltValue);
    }

    /**
     * Retrieve the value of a meta-data entry from the manifest
     * @param context   Context to use
     * @param key       Meta-data name
     * @param dfltValue Default value
     * @return  meta-data string
     */
    public static boolean getManifestMetaDataBoolean(Context context, String key, boolean dfltValue) {
        boolean metaData = dfltValue;
        Bundle bundle = getManifestMetaDataBundle(context);
        if (bundle != null) {
            metaData = bundle.getBoolean(key, dfltValue);
        }
        return metaData;
    }

    /**
     * Retrieve the value of a meta-data entry from the manifest
     * @param context   Context to use
     * @param key       Meta-data name
     * @param dfltValue Default value
     * @return  meta-data string
     */
    public static boolean getManifestMetaDataBoolean(Context context, @StringRes int key, boolean dfltValue) {
        return getManifestMetaDataBoolean(context, context.getString(key), dfltValue);
    }

    /**
     * Start an activity
     * @param context   The current context
     * @param intent    Intent to start activity
     * @return  true if intent was successfully resolved
     */
    public static boolean startActivity(Context context, Intent intent) {
        return startActivity(context, new Intent[] { intent });
    }

    /**
     * Start an activity
     * @param activity      Parent activity
     * @param intent        Intent to start activity
     * @param requestCode   Reply request code
     * @return  true if intent was successfully resolved
     */
    public static boolean startActivityForResult(Activity activity, Intent intent, int requestCode) {
        return startActivityForResult(activity, new Intent[] { intent }, requestCode);
    }

    /**
     * Start an activity with fallback options. All intents will be attempted in ascending order until
     * one is successfully resolved, and that one is used.
     * @param context   The current context
     * @param intents       Intents to start activity
     * @return  true if intent was successfully resolved
     */
    public static boolean startActivity(Context context, Intent[] intents) {
        boolean resolved = false;
        PackageManager manager = context.getPackageManager();
        for (int i = 0, ll = intents.length; (i < ll) && !resolved; i++) {
            resolved = (intents[i].resolveActivity(manager) != null);
            if (resolved) {
                context.startActivity(intents[i]);
            }
        }
        return resolved;
    }

    /**
     * Start an activity with fallback options. All intents will be attempted in ascending order until
     * one is successfully resolved, and that one is used.
     * @param activity      Parent activity
     * @param intents       Intents to start activity
     * @return  true if intent was successfully resolved
     */
    public static boolean startActivityForResult(Activity activity, Intent[] intents, int requestCode) {
        boolean resolved = false;
        PackageManager manager = activity.getPackageManager();
        for (int i = 0, ll = intents.length; (i < ll) && !resolved; i++) {
            resolved = (intents[i].resolveActivity(manager) != null);
            if (resolved) {
                activity.startActivityForResult(intents[i], requestCode);
            }
        }
        return resolved;
    }

}
