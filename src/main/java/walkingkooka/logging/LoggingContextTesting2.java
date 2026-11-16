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
import walkingkooka.ContextTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface LoggingContextTesting2<C extends LoggingContext> extends ContextTesting<C>,
    CanLogTesting2<C>,
    LoggingContextTesting {

    default void loggingLevelAndCheck(final LoggingLevel expected) {
        final C context = this.createContext();

        this.loggingLevelAndCheck(
            context,
            expected
        );
    }

    @Test
    default void testSetLoggingLevelWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .setLoggingLevel(null)
        );
    }

    // CanLog...........................................................................................................

    @Override
    default C createCanLog() {
        return this.createContext();
    }

    // class............................................................................................................

    @Override
    default String typeNameSuffix() {
        return LoggingContext.class.getSimpleName();
    }
}
