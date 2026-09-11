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
import walkingkooka.logging.LoggingContextDelegatorTest.TestLoggingContextDelegator;

public final class LoggingContextDelegatorTest implements LoggingContextTesting2<TestLoggingContextDelegator> {

    @Test
    public void testLoggingLevel() {
        this.loggingLevelAndCheck(
            LoggingLevel.NONE
        );
    }

    @Override
    public TestLoggingContextDelegator createContext() {
        return new TestLoggingContextDelegator();
    }

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<TestLoggingContextDelegator> type() {
        return TestLoggingContextDelegator.class;
    }

    final static class TestLoggingContextDelegator implements LoggingContextDelegator {

        @Override
        public LoggingContext loggingContext() {
            return LoggingContexts.canLog(
                () -> LoggingLevel.NONE,
                CanLogs.nullCanLog()
            );
        }

        @Override
        public String toString() {
            return this.getClass()
                .getSimpleName();
        }
    }
}
