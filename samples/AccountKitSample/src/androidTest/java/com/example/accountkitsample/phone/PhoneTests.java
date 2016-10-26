/**
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.accountkitsample.phone;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.telephony.TelephonyManager;

import com.example.accountkitsample.AccountKitTest;
import com.example.accountkitsample.MainActivity;
import com.example.accountkitsample.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class PhoneTests extends AccountKitTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void startPhoneLogin() {
        PhoneLoginUtil.startPhoneLogin();
    }

    @Test
    public void testInitialState() {
        TelephonyManager telephonyManager =
                (TelephonyManager) InstrumentationRegistry.getTargetContext()
                        .getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();
        String phoneNumberWithoutCountryCode = phoneNumber.substring(1);


        // phone number entry
        final ViewInteraction phoneNumberEditText =
                onView(withId(R.id.com_accountkit_phone_number));
        phoneNumberEditText.check(matches(isDisplayed()));
        phoneNumberEditText.check(matches(hasFocus()));
        phoneNumberEditText.check(matches(withText(phoneNumberWithoutCountryCode)));
        // info text
        onView(withId(R.id.com_accountkit_text)).check(matches(isDisplayed()));
        // next btn
        onView(withId(R.id.com_accountkit_next_button)).check(matches(isEnabled()));
    }

    @Test
    public void testInvalidPhoneNumber() {
        final ViewInteraction phoneNumberEditText =
                onView(withId(R.id.com_accountkit_phone_number));

        phoneNumberEditText.perform(replaceText(PhoneLoginUtil.BAD_PHONE_NUMBER));
        onView(withId(R.id.com_accountkit_next_button)).perform(click());

        ViewInteraction text = onView(allOf(withId(R.id.com_accountkit_title), isDisplayed()));
        text.check(matches(withText("Please enter a valid phone number.")));
    }

}
