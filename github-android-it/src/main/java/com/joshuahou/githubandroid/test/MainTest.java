package com.joshuahou.github-android.test;

import android.test.ActivityInstrumentationTestCase2;
import com.joshuahou.github-android.*;

public class MainTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public MainTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

