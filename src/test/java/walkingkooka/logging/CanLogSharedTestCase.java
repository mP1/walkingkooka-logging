/*
 * Copyright 2026 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.logging;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.text.HasLineEndingTesting;
import walkingkooka.text.printer.Printer;

import java.io.PrintWriter;

public abstract class CanLogSharedTestCase<C extends CanLogShared<P>, P extends Printer> implements CanLogTesting2<C>,
    HashCodeEqualsDefinedTesting2<C>,
    HasLineEndingTesting,
    ToStringTesting<C> {

    final static Throwable THROWABLE = new Throwable("throwable message") {

        @Override
        public void printStackTrace(final PrintWriter printWriter) {
            printWriter.println("StackTrace etc 111");
        }
    };

    final static String MESSAGE = "message 111";
    final static String MESSAGE2 = "message 222";

    CanLogSharedTestCase() {
        super();
    }

    @Test
    public final void testLogWithInfo() {
        final StringBuilder b = new StringBuilder();

        final C canLog = this.createCanLog(b);

        canLog.log(
            LoggingLevel.INFO,
            MESSAGE
        );

        this.checkEquals(
            LoggingLevel.INFO + " " + MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public final void testInfoThrowable() {
        final StringBuilder b = new StringBuilder();

        final C canLog = this.createCanLog(b);

        canLog.log(
            LoggingLevel.INFO,
            MESSAGE,
            THROWABLE
        );

        this.checkEquals(
            "INFO message 111\n" +
                "StackTrace etc 111\n",
            b.toString()
        );
    }

    @Test
    public final void testLogWithWarn() {
        final StringBuilder b = new StringBuilder();

        final C canLog = this.createCanLog(b);

        canLog.log(
            LoggingLevel.WARN,
            MESSAGE
        );

        this.checkEquals(
            LoggingLevel.WARN + " " + MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public final void testLogWithWarnThrowable() {
        final StringBuilder b = new StringBuilder();

        final C canLog = this.createCanLog(b);

        canLog.log(
            LoggingLevel.WARN,
            MESSAGE,
            THROWABLE
        );

        this.checkEquals(
            "WARN message 111\n" +
                "StackTrace etc 111\n",
            b.toString()
        );
    }

    abstract C createCanLog(final StringBuilder b);

    abstract C createCanLog(final P printer);

    // hashCode/equals..................................................................................................

    @Override
    public final C createObject() {
        return this.createCanLog();
    }
}
