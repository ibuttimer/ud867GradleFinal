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
import android.support.test.InstrumentationRegistry;
import android.text.TextUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Test of EndpointsAsyncTask
 */
public class EndpointsAsyncTaskTest {

    private CountDownLatch mLatch;
    private EndpointsAsyncTask mAsyncTask;
    private String[] mResults;

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void setUp() {
        Context mContext = InstrumentationRegistry.getTargetContext();

        mLatch = new CountDownLatch(1);

        mAsyncTask = new EndpointsAsyncTask(mContext, ImplementationStep.STEP_3, mInterface);
    }

    @Test
    public void asyncTaskTest() {

        mAsyncTask.execute(EndpointsAsyncTask.makeTellAJokeArg());
        try {
            mLatch.await();

            assertNotNull("No results", mResults);

            for (String result : mResults) {
                assertFalse("Empty string", TextUtils.isEmpty(result));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        assertEquals("Latch count not zero", mLatch.getCount(), 0);
    }

    /**
     * Interface to handle results
     */
    private EndpointsAsyncTask.IEndPoints mInterface = new EndpointsAsyncTask.IEndPoints() {
        @Override
        public void onPostExecute(String[] results) {
            mResults = results;
            mLatch.countDown();
        }
    };

}