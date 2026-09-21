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

/**
 * Minimalist interface that supports logging messages if the accompanying {@link LoggingLevel} is enabled.
 */
public interface CanLog {

    /**
     * Starts a new logging scope set to the given {@link LoggerPath}. This will be used to determine the current
     * {@link LoggingLevel}.
     */
    void logEnter(final LoggerPath logger);

    /**
     * Closes a previous entered {@link #logEnter(LoggerPath)} scope.
     */
    void logExit();

    default void log(final LoggingLevel loggingLevel,
                     final String message) {
        this.log(
            loggingLevel,
            message,
            null
        );
    }

    void log(final LoggingLevel loggingLevel,
             final String message,
             final Throwable throwable);

    /**
     * Tests if the given {@link LoggingLevel} is enabled.
     */
    boolean isLoggingEnabled(final LoggingLevel loggingLevel);
}
