package com.cc.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;

// By default, the  Android Plug-in for Gradle executes your local unit tests
// against a modified version of the android.jar library, which does not contain
// any actual code. Instead, method calls to Android classes from your unit test
// throw an exception.
//
// You can use a mocking framework to stub out external dependencies in your code,
// to easily test that your component interacts with a dependency in an expected way.
// By substituting Android dependencies with mock objects, you can isolate your unit
// test from the rest of the Android system while verifying that the correct methods
// in those dependencies are called.

@RunWith(MockitoJUnitRunner.class)
public class UnitTestSample {

    private static final String FAKE_STRING = "HELLO WORLD";
    private Sample mSample;

    // create a mock Context object to replace the Context Android dependency
    @Mock
    Context mMockContext;

    @Before
    public void setUp() {

        // stub the behavior of the dependency with when() and thenReturn()
        when(mMockContext.getString(R.string.hello_world)).thenReturn(FAKE_STRING);

        // create object under test with injected mocked dependency
        mSample = new Sample(mMockContext);
    }

    @Test
    public void getHelloWorldString_Sample() {

        // when the string is returned from the object under test...
        String result = mSample.getHelloWorldString();

        // ... the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }

    @After
    public void tearDown() {
        mSample = null;
    }
}
