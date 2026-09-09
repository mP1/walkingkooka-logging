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
import walkingkooka.reflect.PublicClassTesting;

public final class LoggingLevelTest implements PublicClassTesting<LoggingLevel> {

    @Test
    public void testIsEnabledDebugWithDebug() {
        this.isEnabledAndCHeck(
            LoggingLevel.DEBUG
        );
    }

    @Test
    public void testIsEnabledInfoWithDebug() {
        this.isEnabledAndCHeck(
            LoggingLevel.INFO,
            LoggingLevel.DEBUG,
            false
        );
    }

    @Test
    public void testIsEnabledNoneWithDebug() {
        this.isEnabledAndCHeck(
            LoggingLevel.NONE,
            LoggingLevel.DEBUG,
            false
        );
    }

    @Test
    public void testIsEnabledNoneWithNone() {
        this.isEnabledAndCHeck(
            LoggingLevel.NONE
        );
    }

    private void isEnabledAndCHeck(final LoggingLevel level) {
        this.isEnabledAndCHeck(
            level,
            level,
            true
        );
    }

    private void isEnabledAndCHeck(final LoggingLevel level,
                                   final LoggingLevel test,
                                   final boolean expected) {
        this.checkEquals(
            expected,
            level.isEnabled(test),
            () -> level + " isEnabled " + test
        );
    }

    // class............................................................................................................

    @Override
    public Class<LoggingLevel> type() {
        return LoggingLevel.class;
    }
}
