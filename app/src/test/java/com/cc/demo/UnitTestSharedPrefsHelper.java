package com.cc.demo;

import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestSharedPrefsHelper {
    private static final String TEST_NAME = "John Doe";

    private SharedPrefsHelper mMockSharedPrefsHelper;

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Before
    public void setUp() {
        when(mMockSharedPreferences.getString(eq(SharedPrefsHelper.KEY_NAME), anyString()))
                .thenReturn(TEST_NAME);

        // make sure putString does not change the editor (keeps the mock one)
        when(mMockEditor.putString(anyString(), anyString())).thenReturn(mMockEditor);

        // mock a successful save
        when(mMockEditor.commit()).thenReturn(true);

        // return mocked editor
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);

        mMockSharedPrefsHelper = new SharedPrefsHelper(mMockSharedPreferences);
    }

    @Test
    public void saveName_SharedPrefsHelper() {
        boolean success = mMockSharedPrefsHelper.saveName(TEST_NAME);
        assertThat("checking saveName returns true", success, is(true));
    }

    @Test
    public void getName_SharedPrefsHelper() {
        String name = mMockSharedPrefsHelper.getName();
        assertThat("checking getName returns saved name", name, is(TEST_NAME));
    }

    @After
    public void tearDown() {
    }
}
