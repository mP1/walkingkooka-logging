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
import walkingkooka.ToStringTesting;
import walkingkooka.props.Properties;
import walkingkooka.text.HasLineEndingTesting;
import walkingkooka.text.printer.Printer;
import walkingkooka.text.printer.Printers;

public final class CanLogFilterTest implements CanLogTesting2<CanLogFilter>,
    HasLineEndingTesting,
    ToStringTesting<CanLogFilter> {

    private final static LoggerPath LOGGER1 = LoggerPath.parse("logger1");
    private final static LoggerPath LOGGER2 = LoggerPath.parse("logger2");
    private final static LoggerPath LOGGER3 = LoggerPath.parse("logger3");

    private final static String MESSAGE1 = "message1";
    private final static String MESSAGE2 = "message22";
    private final static String MESSAGE3 = "message333";

    @Test
    public void testLogEnterAndLogLogExit() {
        final CanLogFilter canLogFilter = this.createCanLog(
            LoggingLevel.DEBUG,
            Printers.fake(),
            properties(
                LoggingLevel.WARN,
                LoggingLevel.NONE,
                LoggingLevel.NONE
            )
        );

        canLogFilter.logEnter(LOGGER1);
        {
            canLogFilter.log(
                LoggingLevel.DEBUG,
                MESSAGE1
            );
        }
        canLogFilter.logExit();

        canLogFilter.logEnter(LOGGER2);
        {
            canLogFilter.log(
                LoggingLevel.DEBUG,
                MESSAGE2
            );
        }
        canLogFilter.logExit();

        canLogFilter.logEnter(LOGGER3);
        {
            canLogFilter.log(
                LoggingLevel.DEBUG,
                MESSAGE3
            );
        }
        canLogFilter.logExit();
    }

    @Test
    public void testLogEnterAndLogLogExit2() {
        final StringBuilder b = new StringBuilder();

        final CanLogFilter canLogFilter = this.createCanLog(
            LoggingLevel.DEBUG,
            b,
            properties(
                LoggingLevel.DEBUG,
                LoggingLevel.INFO,
                LoggingLevel.WARN
            )
        );

        canLogFilter.logEnter(LOGGER1);
        {
            canLogFilter.log(
                LoggingLevel.DEBUG,
                MESSAGE1
            );

            canLogFilter.log(
                LoggingLevel.INFO,
                MESSAGE1
            );
        }
        canLogFilter.logExit();

        canLogFilter.logEnter(LOGGER2);
        {
            canLogFilter.log(
                LoggingLevel.DEBUG,
                MESSAGE2
            );
        }
        canLogFilter.logExit();

        canLogFilter.logEnter(LOGGER3);
        {
            canLogFilter.log(
                LoggingLevel.DEBUG,
                MESSAGE3
            );
        }
        canLogFilter.logExit();

        this.checkEquals(
            "logger1 DEBUG message1\n" +
                "logger1 INFO message1\n",
            b.toString()
        );
    }

    @Test
    public void testLogEnterAndLogLogExit3() {
        final StringBuilder b = new StringBuilder();

        final CanLogFilter canLogFilter = this.createCanLog(
            LoggingLevel.DEBUG,
            b,
            properties(
                LoggingLevel.DEBUG,
                LoggingLevel.INFO,
                LoggingLevel.WARN
            )
        );

        canLogFilter.logEnter(LOGGER1);
        {
            canLogFilter.log(
                LoggingLevel.INFO,
                MESSAGE1
            );
        }
        canLogFilter.logExit();

        canLogFilter.logEnter(LOGGER2);
        {
            canLogFilter.log(
                LoggingLevel.INFO,
                MESSAGE2
            );
        }
        canLogFilter.logExit();

        canLogFilter.logEnter(LOGGER3);
        {
            canLogFilter.log(
                LoggingLevel.INFO,
                MESSAGE3
            );
        }
        canLogFilter.logExit();

        this.checkEquals(
            "logger1 INFO message1\n" +
                "logger2 INFO message22\n",
            b.toString()
        );
    }

    @Test
    public void testNestedLogEnterLogExit() {
        final StringBuilder b = new StringBuilder();

        final CanLogFilter canLogFilter = this.createCanLog(
            LoggingLevel.DEBUG,
            b,
            properties(
                LoggingLevel.DEBUG,
                LoggingLevel.INFO,
                LoggingLevel.WARN
            )
        );

        canLogFilter.logEnter(LOGGER1);
        {
            canLogFilter.log(
                LoggingLevel.INFO,
                MESSAGE1
            );

            canLogFilter.logEnter(LOGGER2);
            {
                canLogFilter.log(
                    LoggingLevel.INFO,
                    MESSAGE2
                );
            }
            canLogFilter.logExit();
        }
        canLogFilter.logExit();

        canLogFilter.logEnter(LOGGER3);
        {
            canLogFilter.log(
                LoggingLevel.INFO,
                MESSAGE3
            );
        }
        canLogFilter.logExit();

        this.checkEquals(
            "logger1 INFO message1\n" +
                "logger2 INFO message22\n",
            b.toString()
        );
    }

    private static Properties properties(final LoggingLevel logger1,
                                         final LoggingLevel logger2,
                                         final LoggingLevel logger3) {
        return Properties.parse(
            LOGGER1 + "=" + logger1 + LINE_ENDING +
            LOGGER2 + "=" + logger2 + LINE_ENDING +
            LOGGER3 + "=" + logger3 + LINE_ENDING
        );
    }

    @Override
    public CanLogFilter createCanLog() {
        return this.createCanLog(
            LoggingLevel.DEBUG,
            Printers.sink(LINE_ENDING),
            Properties.EMPTY
        );
    }

    private CanLogFilter createCanLog(final HasLoggingLevel loggingLevel,
                                      final StringBuilder b,
                                      final String properties) {
        return this.createCanLog(
            loggingLevel,
            b,
            Properties.parse(properties)
        );
    }

    private CanLogFilter createCanLog(final HasLoggingLevel loggingLevel,
                                      final StringBuilder b,
                                      final Properties properties) {
        return this.createCanLog(
            loggingLevel,
            Printers.stringBuilder(
                b,
                LINE_ENDING
            ),
            properties
        );
    }

    private CanLogFilter createCanLog(final HasLoggingLevel loggingLevel,
                                      final Printer printer,
                                      final Properties properties) {
        return CanLogFilter.with(
            CanLogs.printer(
                printer
            ),
            CanLoggingLevels.properties(
                properties,
                loggingLevel
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final CanLoggingLevel canLoggingLevel = CanLoggingLevels.fake();

        this.toStringAndCheck(
            CanLogFilter.with(
                CAN_LOG,
                canLoggingLevel
            ),
            "canLog: " + CAN_LOG + " canLoggingLevel: " + canLoggingLevel
        );
    }

    // class............................................................................................................

    @Override
    public Class<CanLogFilter> type() {
        return CanLogFilter.class;
    }
}
