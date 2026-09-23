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
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

public final class CanLogSharedPrinterTest extends CanLogSharedTestCase<CanLogSharedPrinter, Printer> {

    private final static Printer PRINTER = Printers.fake();

    @Test
    public void testLogWithNullMessage() {
        this.createCanLog(
            Printers.fake()
        ).log(
            LoggingLevel.DEBUG,
            null
        );
    }

    @Test
    public void testLogWithEmptyMessage() {
        this.createCanLog(
            Printers.fake()
        ).log(
            LoggingLevel.DEBUG,
            null
        );
    }

    @Test
    public void testLogWithNullMessageAndNullThrowable() {
        this.createCanLog(
            Printers.fake()
        ).log(
            LoggingLevel.DEBUG,
            null,
            null
        );
    }

    @Test
    public void testLogWithEmptyMessageAndNullThrowable() {
        this.createCanLog(
            Printers.fake()
        ).log(
            LoggingLevel.DEBUG,
            "",
            null
        );
    }

    @Test
    public void testLogEnterAndLog() {
        final StringBuilder b = new StringBuilder();

        final CanLogSharedPrinter canLogSharedPrinter = this.createCanLog(b);

        canLogSharedPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogSharedPrinter.log(
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

        final CanLogSharedPrinter canLogSharedPrinter = this.createCanLog(b);

        canLogSharedPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogSharedPrinter.log(
                LoggingLevel.DEBUG,
                MESSAGE,
                null
            );

            {
                canLogSharedPrinter.logEnter(
                    LoggerPath.parse("logger2")
                );
                {
                    canLogSharedPrinter.log(
                        LoggingLevel.INFO,
                        MESSAGE2,
                        null
                    );
                }
                canLogSharedPrinter.logExit();
            }

            canLogSharedPrinter.log(
                LoggingLevel.WARN,
                "message 333",
                null
            );
        }
        canLogSharedPrinter.logExit();

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

        final CanLogSharedPrinter canLogSharedPrinter = this.createCanLog(b);

        canLogSharedPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogSharedPrinter.log(
                LoggingLevel.WARN,
                MESSAGE,
                THROWABLE
            );
        }
        canLogSharedPrinter.logExit();

        canLogSharedPrinter.log(
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

        final CanLogSharedPrinter canLogSharedPrinter = this.createCanLog(b);

        canLogSharedPrinter.log(
            LoggingLevel.WARN,
            MESSAGE,
            THROWABLE
        );

        canLogSharedPrinter.log(
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
    public CanLogSharedPrinter createCanLog() {
        return this.createCanLog(
            PRINTER
        );
    }

    @Override
    CanLogSharedPrinter createCanLog(final StringBuilder b) {
        return this.createCanLog(
            Printers.stringBuilder(
                b,
                LINE_ENDING
            )
        );
    }

    @Override
    CanLogSharedPrinter createCanLog(final Printer printer) {
        return CanLogSharedPrinter.with(printer);
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentPrinter() {
        this.checkNotEquals(
            CanLogSharedPrinter.with(
                Printers.fake()
            )
        );
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
    public Class<CanLogSharedPrinter> type() {
        return CanLogSharedPrinter.class;
    }
}
