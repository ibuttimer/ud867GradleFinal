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

import static com.udacity.gradle.builditbigger.Utils.getManifestMetaDataInteger;

/**
 * Enum representing the implementations steps for the app
 */

public enum ImplementationStep {

    STEP_1, STEP_2, STEP_3;

    /**
     * Get the ImplementationStep represented by the specified value
     * @param value     1-based value
     * @return  ImplementationStep object
     */
    public static ImplementationStep getStep(int value) {
        ImplementationStep[] values = values();
        int length = values.length;
        ImplementationStep step = getDefaultStep();   // default

        if ((value >= 1) || (value <= length)) {
            for (int i = 0; i < length; i++) {
                if ((value - 1) == values[i].ordinal()) {
                    step = values[i];
                    break;
                }
            }
        }
        return step;
    }

    /**
     * Get the default ImplementationStep used by the app
     * @return  ImplementationStep object
     */
    public static ImplementationStep getDefaultStep() {
        ImplementationStep[] values = values();
        int length = values.length;
        return values[length - 1];
    }

    /**
     * Get the ImplementationStep setting for the app
     * @return  ImplementationStep object
     */
    public static ImplementationStep getConfiguredStep(Context context) {
        int value = getManifestMetaDataInteger(context, R.string.implementation_step, -1);
        return ImplementationStep.getStep(value);
    }




}
