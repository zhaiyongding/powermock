/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package samples.powermockito.junit4.partialmocking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import samples.singleton.StaticExample;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticExample.class)
public class StaticPartialMockingTest {

    @Test
    public void spyingOnStaticMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        assertTrue(Object.class.equals(StaticExample.objectMethod().getClass()));
        when(StaticExample.class, "privateObjectMethod").thenReturn("Hello static");

        assertEquals("Hello static", StaticExample.objectMethod());
        /*
         * privateObjectMethod should be invoked twice, once at "assertTrue" and
         * once above.
         */
        verifyPrivate(StaticExample.class, times(2)).invoke("privateObjectMethod");
    }

    @Test
    public void partialMockingOfStaticMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        assertTrue(Object.class.equals(StaticExample.objectMethod().getClass()));
        doReturn("Hello static").when(StaticExample.class, "privateObjectMethod");

        assertEquals("Hello static", StaticExample.objectMethod());
        /*
         * privateObjectMethod should be invoked twice, once at "assertTrue" and
         * once above.
         */
        verifyPrivate(StaticExample.class, times(2)).invoke("privateObjectMethod");
    }

    @Test
    public void spyingOnStaticFinalMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        assertTrue(Object.class.equals(StaticExample.objectFinalMethod().getClass()));

        when(StaticExample.class, "privateObjectFinalMethod").thenReturn("Hello static");

        assertEquals("Hello static", StaticExample.objectFinalMethod());

        verifyPrivate(StaticExample.class, times(2)).invoke("privateObjectFinalMethod");
    }

    @Test
    public void partialMockingOfStaticFinalMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        assertTrue(Object.class.equals(StaticExample.objectFinalMethod().getClass()));

        doReturn("Hello static").when(StaticExample.class, "privateObjectFinalMethod");

        assertEquals("Hello static", StaticExample.objectFinalMethod());

        verifyPrivate(StaticExample.class, times(2)).invoke("privateObjectFinalMethod");
    }

    @Test(expected = ArrayStoreException.class)
    public void spyingOnStaticVoidMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        StaticExample.voidMethod();

        when(StaticExample.class, "privateVoidMethod").thenThrow(new ArrayStoreException());
        StaticExample.voidMethod();
    }

    @Test(expected = ArrayStoreException.class)
    public void partialMockingOfStaticVoidMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        StaticExample.voidMethod();

        doThrow(new ArrayStoreException()).when(StaticExample.class, "privateVoidMethod");
        StaticExample.voidMethod();
    }

    @Test(expected = ArrayStoreException.class)
    public void spyingOnStaticFinalVoidMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        StaticExample.voidFinalMethod();

        when(StaticExample.class, "privateVoidFinalMethod").thenThrow(new ArrayStoreException());
        StaticExample.voidFinalMethod();
    }

    @Test(expected = ArrayStoreException.class)
    public void partialMockingOfStaticFinalVoidMethodReturningObjectWorks() throws Exception {
        spy(StaticExample.class);

        StaticExample.voidFinalMethod();

        doThrow(new ArrayStoreException()).when(StaticExample.class, "privateVoidFinalMethod");
        StaticExample.voidFinalMethod();
    }

    @Test
    public void partialMockingOfPublicStaticVoidWorks() throws Exception {
        spy(StaticExample.class);

        // Given
        doNothing().when(StaticExample.class);
        StaticExample.staticVoidMethod();

        // When
        StaticExample.staticVoidMethod();

        // Then
        verifyStatic(times(1));
        StaticExample.staticVoidMethod();
    }

    @Test
    public void partialMockingOfPublicStaticFinalVoidWorks() throws Exception {
        spy(StaticExample.class);

        doNothing().when(StaticExample.class);
        StaticExample.staticFinalVoidMethod();

        StaticExample.staticFinalVoidMethod();
    }
}
