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

public final class LoggingContextNullTest implements LoggingContextTesting2<LoggingContextNull> {

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
            false
        );
    }

    @Test
    public void testIsLoggingEnabledWithWarn() {
        this.isLoggingEnabledAndCheck(
            LoggingLevel.WARN,
            false
        );
    }

    @Test
    public void testIsLoggingEnabledWithError() {
        this.isLoggingEnabledAndCheck(
            LoggingLevel.ERROR,
            false
        );
    }

    @Test
    public void testLoggingLevel() {
        this.loggingLevelAndCheck(
            LoggingLevel.NONE
        );
    }

    @Override
    public LoggingContextNull createContext() {
        return LoggingContextNull.INSTANCE;
    }

    @Override
    public Class<LoggingContextNull> type() {
        return LoggingContextNull.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
