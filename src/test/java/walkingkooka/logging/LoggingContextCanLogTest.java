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
import walkingkooka.text.printer.Printers;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class LoggingContextCanLogTest implements LoggingContextTesting2<LoggingContextCanLog>,
    HasLineEndingTesting,
    HashCodeEqualsDefinedTesting2<LoggingContextCanLog>,
    ToStringTesting<LoggingContextCanLog> {

    private final static HasLoggingLevel HAS_LOGGING_LEVEL = () -> LoggingLevel.INFO;

    private final static CanLog CAN_LOG = CanLogs.fake();

    private final static Throwable THROWABLE = new Throwable("throwable message") {

        @Override
        public void printStackTrace(final PrintWriter printWriter) {
            printWriter.println("StackTrace etc 123");
        }
    };

    @Test
    public void testWithNullHasLoggingLevelFails() {
        assertThrows(
            NullPointerException.class,
            () -> LoggingContextCanLog.with(
                null,
                CAN_LOG
            )
        );
    }

    @Test
    public void testWithNullCanLogFails() {
        assertThrows(
            NullPointerException.class,
            () -> LoggingContextCanLog.with(
                HAS_LOGGING_LEVEL,
                null
            )
        );
    }

    @Test
    public void testIsLoggingEnabledWithDebug() {
        this.isLoggingEnabledAndCheck(
            LoggingLevel.DEBUG,
            false
        );
    }

    @Test
    public void testIsLoggingEnabledWithInfo() {
        this.isLoggingEnabledAndCheck(
            LoggingLevel.INFO,
            true
        );
    }

    @Test
    public void testLoggingLevel() {
        this.loggingLevelAndCheck(
            LoggingLevel.INFO
        );
    }

    @Test
    public void testDebug() {
        this.createContext()
            .debug("message");
    }

    @Test
    public void testDebugAndThrowable() {
        this.createContext()
            .debug(
                "message",
                new Throwable()
            );
    }

    private final static String MESSAGE = "message 123";

    @Test
    public void testInfo() {
        final StringBuilder b = new StringBuilder();

        final LoggingContextCanLog context = this.createContext(b);

        context.info(MESSAGE);

        this.checkEquals(
            MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public void testInfoThrowable() {
        final StringBuilder b = new StringBuilder();

        final LoggingContextCanLog context = this.createContext(b);

        context.info(MESSAGE, THROWABLE);

        this.checkEquals(
            "message 123\n" +
                "StackTrace etc 123\n",
            b.toString()
        );
    }

    @Test
    public void testWarn() {
        final StringBuilder b = new StringBuilder();

        final LoggingContextCanLog context = this.createContext(b);

        context.warn(MESSAGE);

        this.checkEquals(
            MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public void testWarnThrowable() {
        final StringBuilder b = new StringBuilder();

        final LoggingContextCanLog context = this.createContext(b);

        context.warn(MESSAGE, THROWABLE);

        this.checkEquals(
            "message 123\n" +
                "StackTrace etc 123\n",
            b.toString()
        );
    }

    @Test
    public void testErrorWhenNone() {
        final LoggingContextCanLog context = LoggingContextCanLog.with(
            () -> LoggingLevel.NONE,
            CAN_LOG
        );

        context.warn(MESSAGE);
    }

    @Override
    public LoggingContextCanLog createContext() {
        return this.createContext(CAN_LOG);
    }

    private LoggingContextCanLog createContext(final StringBuilder b) {
        return this.createContext(
            CanLogs.printer(
                Printers.stringBuilder(
                    b,
                    LINE_ENDING
                )
            )
        );
    }

    private LoggingContextCanLog createContext(final CanLog canLog) {
        return LoggingContextCanLog.with(
            HAS_LOGGING_LEVEL,
            canLog
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentLoggingLevel() {
        this.checkNotEquals(
            LoggingContextCanLog.with(
                () -> LoggingLevel.NONE,
                CAN_LOG
            )
        );
    }

    @Test
    public void testEqualsDifferentCanLog() {
        this.checkNotEquals(
            LoggingContextCanLog.with(
                HAS_LOGGING_LEVEL,
                CanLogs.fake()
            )
        );
    }

    @Override
    public LoggingContextCanLog createObject() {
        return this.createContext();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            LoggingLevel.INFO + " " + CAN_LOG
        );
    }

    // class............................................................................................................

    @Override
    public Class<LoggingContextCanLog> type() {
        return LoggingContextCanLog.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
