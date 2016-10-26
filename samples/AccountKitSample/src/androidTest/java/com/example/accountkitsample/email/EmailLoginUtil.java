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

package com.example.accountkitsample.email;

import android.support.test.espresso.ViewInteraction;

import com.example.accountkitsample.R;
import com.example.accountkitsample.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EmailLoginUtil {

    public static final String EMAIL_ADDRESS = "badEmailAddress@bogus.com";
    public static final String INCOMPLETE_EMAIL_ADDRESS = "incompleteEmailAddress";

    private EmailLoginUtil() { /* no instances */ }

    public static void startEmailLogin() {
        ViewInteraction emailLoginBtn = onView(withText(R.string.log_in_email_button));
        emailLoginBtn.check(matches(isDisplayed()));
        emailLoginBtn.perform(click());
        TestUtils.acceptPermissionsIfNeeded();
        onView(withText("Enter your email address")).check(matches(isDisplayed()));
    }

    public static void submitEmailAddress() {
        final ViewInteraction emailEditTextInteraction = onView(withId(R.id.com_accountkit_email));
        // enter address
        emailEditTextInteraction.perform(typeText(EmailLoginUtil.EMAIL_ADDRESS));
        emailEditTextInteraction.check(matches(withText(EmailLoginUtil.EMAIL_ADDRESS)));
        // submit address
        onView(withId(R.id.com_accountkit_next_button)).perform(click());
    }
}
