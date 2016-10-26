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
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.accountkitsample.AccountKitTest;
import com.example.accountkitsample.MainActivity;
import com.example.accountkitsample.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class EmailTests extends AccountKitTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void startEmailFlow() {
        EmailLoginUtil.startEmailLogin();
    }

    @Test
    public void testInitialState() {
        // email address entry
        final ViewInteraction emailEditTextInteraction = onView(withId(R.id.com_accountkit_email));
        emailEditTextInteraction.check(matches(isDisplayed()));
        emailEditTextInteraction.check(matches(hasFocus()));
        emailEditTextInteraction.check(matches(withText("")));
        // info text
        onView(withId(R.id.com_accountkit_text)).check(matches(isDisplayed()));
        // next btn
        onView(withId(R.id.com_accountkit_next_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void testBadEmailAddressEntry() {
        final ViewInteraction emailEditTextInteraction = onView(withId(R.id.com_accountkit_email));
        // enter address
        emailEditTextInteraction.perform(typeText(EmailLoginUtil.INCOMPLETE_EMAIL_ADDRESS));
        emailEditTextInteraction.check(matches(withText(EmailLoginUtil.INCOMPLETE_EMAIL_ADDRESS)));
        // submit address
        onView(withId(R.id.com_accountkit_next_button)).perform(click());
        // verify state
        emailEditTextInteraction.check(matches(isDisplayed()));
        onView(withText(R.string.com_accountkit_email_invalid)).check(matches(isDisplayed()));
    }


    @Test
    public void testEnterEmailAddress() {
        EmailLoginUtil.submitEmailAddress();
        // possible race condition with sending email.
        onView(withId(R.id.com_accountkit_icon_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testOpenEmail() throws InterruptedException {
        EmailLoginUtil.submitEmailAddress();
//        Looking into Espressos Idling Resource but until then we will just sleep it.
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        onView(withId(R.id.com_accountkit_check_email_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testResentEmail() throws InterruptedException {
        EmailLoginUtil.submitEmailAddress();
//        Looking into Espressos Idling Resource but until then we will just sleep it.
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        onView(withId(R.id.com_accountkit_retry_email_button)).perform(click());
        onView(withText(R.string.com_accountkit_email_login_retry_title))
                .check(matches(isDisplayed()));
    }
}
