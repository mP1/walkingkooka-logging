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
import walkingkooka.text.HasIndentationTesting;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.IndentingPrinters;
import walkingkooka.text.printer.Printers;

public final class CanLogSharedIndentingPrinterTest extends CanLogSharedTestCase<CanLogSharedIndentingPrinter, IndentingPrinter>
    implements HasIndentationTesting {

    private final static IndentingPrinter PRINTER = IndentingPrinters.fake();

    @Test
    public void testLogEnterAndLog() {
        final StringBuilder b = new StringBuilder();

        final CanLogSharedIndentingPrinter canLogSharedIndentingPrinter = this.createCanLog(b);

        canLogSharedIndentingPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogSharedIndentingPrinter.log(
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

        final CanLogSharedIndentingPrinter canLogSharedIndentingPrinter = this.createCanLog(b);

        canLogSharedIndentingPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogSharedIndentingPrinter.log(
                LoggingLevel.DEBUG,
                MESSAGE,
                null
            );

            {
                canLogSharedIndentingPrinter.logEnter(
                    LoggerPath.parse("logger2")
                );
                {
                    canLogSharedIndentingPrinter.log(
                        LoggingLevel.INFO,
                        MESSAGE2,
                        null
                    );
                }
                canLogSharedIndentingPrinter.logExit();
            }

            canLogSharedIndentingPrinter.log(
                LoggingLevel.WARN,
                "message 333",
                null
            );
        }
        canLogSharedIndentingPrinter.logExit();

        this.checkEquals(
            "logger1 DEBUG message 111\n" +
                "  logger2 INFO message 222\n" +
                "logger1 WARN message 333\n",
            b.toString()
        );
    }

    @Test
    public void testLogEnterAndLogLogExitLog() {
        final StringBuilder b = new StringBuilder();

        final CanLogSharedIndentingPrinter canLogSharedIndentingPrinter = this.createCanLog(b);

        canLogSharedIndentingPrinter.logEnter(
            LoggerPath.parse("logger1")
        );
        {
            canLogSharedIndentingPrinter.log(
                LoggingLevel.WARN,
                MESSAGE,
                THROWABLE
            );
        }
        canLogSharedIndentingPrinter.logExit();

        canLogSharedIndentingPrinter.log(
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

        final CanLogSharedIndentingPrinter canLogSharedIndentingPrinter = this.createCanLog(b);

        canLogSharedIndentingPrinter.log(
            LoggingLevel.WARN,
            MESSAGE,
            THROWABLE
        );

        canLogSharedIndentingPrinter.log(
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
    public CanLogSharedIndentingPrinter createCanLog() {
        return this.createCanLog(
            PRINTER
        );
    }

    @Override
    CanLogSharedIndentingPrinter createCanLog(final StringBuilder b) {
        return this.createCanLog(
            Printers.stringBuilder(
                b,
                LINE_ENDING
            ).indenting(INDENTATION)
        );
    }

    @Override
    CanLogSharedIndentingPrinter createCanLog(final IndentingPrinter printer) {
        return CanLogSharedIndentingPrinter.with(printer);
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentIndentingPrinter() {
        this.checkNotEquals(
            CanLogSharedIndentingPrinter.with(
                IndentingPrinters.fake()
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
    public Class<CanLogSharedIndentingPrinter> type() {
        return CanLogSharedIndentingPrinter.class;
    }
}
