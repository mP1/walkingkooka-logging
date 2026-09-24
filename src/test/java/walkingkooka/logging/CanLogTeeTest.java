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
import walkingkooka.text.HasLineEndingTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CanLogTeeTest implements CanLogTesting2<CanLogTee>,
    HasLoggingLevelTesting,
    HasLineEndingTesting,
    ToStringTesting<CanLogTee> {

    @Test
    public void testWithNullFirstFails() {
        assertThrows(
            NullPointerException.class,
            () -> CanLogTee.with(
                null,
                CanLogs.fake()
            )
        );
    }

    @Test
    public void testWithNullSecondFails() {
        assertThrows(
            NullPointerException.class,
            () -> CanLogTee.with(
                CanLogs.fake(),
                null
            )
        );
    }

    @Test
    public void testLog() {
        final StringBuilder logged = new StringBuilder();

        CanLogTee.with(
            new FakeCanLog() {
                @Override
                public void log(final LoggingLevel loggingLevel,
                                final String message,
                                final Throwable throwable) {
                    logged.append(
                        "" + loggingLevel + ' ' + message.toLowerCase() + ' ' + throwable + LINE_ENDING
                    );
                }
            },
            new FakeCanLog() {
                @Override
                public void log(final LoggingLevel loggingLevel,
                                final String message,
                                final Throwable throwable) {
                    logged.append(
                        "" + loggingLevel + ' ' + message.toUpperCase() + ' ' + throwable + LINE_ENDING
                    );
                }
            }
        ).log(
            LOGGING_LEVEL,
            "Message111",
            new RuntimeException("RuntimeExceptionMessage222")
        );

        this.checkEquals(
            "NONE message111 java.lang.RuntimeException: RuntimeExceptionMessage222" + LINE_ENDING +
                "NONE MESSAGE111 java.lang.RuntimeException: RuntimeExceptionMessage222" + LINE_ENDING,
            logged.toString()
        );
    }

    @Override
    public void testLogExitWithoutLogEnter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CanLogTee createCanLog() {
        return CanLogTee.with(
            CanLogs.nullCanLog(),
            new FakeCanLog() {
                @Override
                public void logEnter(final LoggerPath logger) {
                    // nop
                }

                @Override
                public void logExit() {
                    // nop
                }
            }
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        final CanLog second = CanLogs.fake();

        this.toStringAndCheck(
            CanLogTee.with(
                CanLogs.nullCanLog(),
                second
            ),
            "nul " + second
        );
    }

    // class............................................................................................................

    @Override
    public Class<CanLogTee> type() {
        return CanLogTee.class;
    }
}
