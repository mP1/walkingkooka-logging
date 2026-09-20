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

public final class CanLogPrinterTest implements CanLogTesting2<CanLogPrinter>,
    HashCodeEqualsDefinedTesting2<CanLogPrinter>,
    HasLineEndingTesting,
    ToStringTesting<CanLogPrinter> {

    private final static Printer PRINTER = Printers.fake();

    private final static Throwable THROWABLE = new Throwable("throwable message") {

        @Override
        public void printStackTrace(final PrintWriter printWriter) {
            printWriter.println("StackTrace etc 111");
        }
    };

    @Test
    public void testWithNullPrinterFails() {
        assertThrows(
            NullPointerException.class,
            () -> CanLogPrinter.with(null)
        );
    }

    private final static String MESSAGE = "message 111";
    private final static String MESSAGE2 = "message 222";

    @Test
    public void testLogWithInfo() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.log(
            LoggingLevel.INFO,
            MESSAGE
        );

        this.checkEquals(
            LoggingLevel.INFO + " " + MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public void testInfoThrowable() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.log(
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
    public void testLogWithWarn() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.log(
            LoggingLevel.WARN,
            MESSAGE
        );

        this.checkEquals(
            LoggingLevel.WARN + " " + MESSAGE + LINE_ENDING,
            b.toString()
        );
    }

    @Test
    public void testLogWithWarnThrowable() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.log(
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

    @Test
    public void testLogEnterAndLog() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogPrinter.log(
                LoggingLevel.WARN,
                MESSAGE,
                THROWABLE
            );
        }
        this.checkEquals(
            "logger1 WARN message 111\n" +
                "StackTrace etc 111\n",
            b.toString()
        );
    }

    @Test
    public void testLogEnterAndLog2() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogPrinter.log(
                LoggingLevel.DEBUG,
                MESSAGE,
                null
            );

            {
                canLogPrinter.logEnter(
                    LoggerPath.parse("logger2")
                );
                {
                    canLogPrinter.log(
                        LoggingLevel.INFO,
                        MESSAGE2,
                        null
                    );
                }
                canLogPrinter.logExit();
            }

            canLogPrinter.log(
                LoggingLevel.WARN,
                "message 333",
                null
            );
        }
        canLogPrinter.logExit();

        this.checkEquals(
            "logger1 DEBUG message 111\n" +
                "logger2 INFO message 222\n" +
                "logger1 WARN message 333\n",
            b.toString()
        );
    }

    @Test
    public void testLogEnterAndLogLogExitLog() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogPrinter.log(
                LoggingLevel.WARN,
                MESSAGE,
                THROWABLE
            );
        }
        canLogPrinter.logExit();

        canLogPrinter.log(
            LoggingLevel.DEBUG,
            MESSAGE2
        );

        this.checkEquals(
            "logger1 WARN message 111\n" +
                "StackTrace etc 111\n" +
            "DEBUG message 222\n",
            b.toString()
        );
    }

    @Test
    public void testLogWithWarnThrowable2() {
        final StringBuilder b = new StringBuilder();

        final CanLogPrinter canLogPrinter = this.createCanLog(b);

        canLogPrinter.log(
            LoggingLevel.WARN,
            MESSAGE,
            THROWABLE
        );

        canLogPrinter.log(
            LoggingLevel.ERROR,
            MESSAGE2
        );

        this.checkEquals(
            "WARN message 111\n" +
                "StackTrace etc 111\n" +
                "ERROR message 222\n",
            b.toString()
        );
    }

    @Override
    public CanLogPrinter createCanLog() {
        return this.createCanLog(
            PRINTER
        );
    }

    private CanLogPrinter createCanLog(final StringBuilder b) {
        return this.createCanLog(
            Printers.stringBuilder(
                b,
                LINE_ENDING
            )
        );
    }

    private CanLogPrinter createCanLog(final Printer printer) {
        return CanLogPrinter.with(printer);
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentPrinter() {
        this.checkNotEquals(
            CanLogPrinter.with(
                Printers.fake()
            )
        );
    }

    @Override
    public CanLogPrinter createObject() {
        return this.createCanLog();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createCanLog(),
            PRINTER.toString()
        );
    }

    // class............................................................................................................

    @Override
    public Class<CanLogPrinter> type() {
        return CanLogPrinter.class;
    }
}
