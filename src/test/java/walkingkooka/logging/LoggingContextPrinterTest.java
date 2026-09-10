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
import walkingkooka.text.printer.Printers;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class LoggingContextPrinterTest implements LoggingContextTesting2<LoggingContextPrinter>,
    HasLineEndingTesting,
    HashCodeEqualsDefinedTesting2<LoggingContextPrinter>,
    ToStringTesting<LoggingContextPrinter> {

    private final static HasLoggingLevel HAS_LOGGING_LEVEL = () -> LoggingLevel.INFO;

    private final static Printer PRINTER = Printers.fake();

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
            () -> LoggingContextPrinter.with(
                null,
                PRINTER
            )
        );
    }

    @Test
    public void testWithNullPrinterFails() {
        assertThrows(
            NullPointerException.class,
            () -> LoggingContextPrinter.with(
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

        final LoggingContextPrinter context = this.createContext(b);

        context.info(MESSAGE);

        this.checkEquals(
            MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public void testInfoThrowable() {
        final StringBuilder b = new StringBuilder();

        final LoggingContextPrinter context = this.createContext(b);

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

        final LoggingContextPrinter context = this.createContext(b);

        context.warn(MESSAGE);

        this.checkEquals(
            MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public void testWarnThrowable() {
        final StringBuilder b = new StringBuilder();

        final LoggingContextPrinter context = this.createContext(b);

        context.warn(MESSAGE, THROWABLE);

        this.checkEquals(
            "message 123\n" +
                "StackTrace etc 123\n",
            b.toString()
        );
    }

    @Test
    public void testErrorWhenNone() {
        final LoggingContextPrinter context = LoggingContextPrinter.with(
            () -> LoggingLevel.NONE,
            PRINTER
        );

        context.warn(MESSAGE);
    }

    @Override
    public LoggingContextPrinter createContext() {
        return this.createContext(
            PRINTER
        );
    }

    private LoggingContextPrinter createContext(final StringBuilder b) {
        return this.createContext(
            Printers.stringBuilder(
                b,
                LINE_ENDING
            )
        );
    }

    private LoggingContextPrinter createContext(final Printer printer) {
        return LoggingContextPrinter.with(
            HAS_LOGGING_LEVEL,
            printer
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentLoggingLevel() {
        this.checkNotEquals(
            LoggingContextPrinter.with(
                () -> LoggingLevel.NONE,
                PRINTER
            )
        );
    }

    @Test
    public void testEqualsDifferentPrinter() {
        this.checkNotEquals(
            LoggingContextPrinter.with(
                HAS_LOGGING_LEVEL,
                Printers.fake()
            )
        );
    }

    @Override
    public LoggingContextPrinter createObject() {
        return this.createContext();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            LoggingLevel.INFO + " " + PRINTER
        );
    }

    // class............................................................................................................

    @Override
    public Class<LoggingContextPrinter> type() {
        return LoggingContextPrinter.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
