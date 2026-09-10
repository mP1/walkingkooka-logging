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

public interface LoggingContextDelegator extends LoggingContext {

    @Override
    default void debug(final String message) {
        this.loggingContext()
            .debug(message);
    }

    @Override
    default void debug(final String message,
                       final Throwable throwable) {
        this.loggingContext()
            .debug(
                message,
                throwable
            );
    }

    @Override
    default void info(final String message) {
        this.loggingContext()
            .info(message);
    }

    @Override
    default void info(final String message,
                      final Throwable throwable) {
        this.loggingContext()
            .info(
                message,
                throwable
            );
    }

    @Override
    default void warn(final String message) {
        this.loggingContext()
            .warn(message);
    }

    @Override
    default void warn(final String message,
                      final Throwable throwable) {
        this.loggingContext()
            .warn(
                message,
                throwable
            );
    }

    @Override
    default void error(final String message) {
        this.loggingContext()
            .error(message);
    }

    @Override
    default void error(final String message,
                       final Throwable throwable) {
        this.loggingContext()
            .error(
                message,
                throwable
            );
    }

    @Override
    default void log(final LoggingLevel level,
                     final String message) {
        this.loggingContext()
            .log(
                level,
                message
            );
    }

    @Override
    default void log(final LoggingLevel level,
                     final String message,
                     final Throwable throwable) {
        this.loggingContext()
            .log(
                level,
                message,
                throwable
            );
    }

    @Override
    default boolean isDebugEnabled() {
        return this.loggingContext()
            .isDebugEnabled();
    }

    @Override
    default boolean isInfoEnabled() {
        return this.loggingContext()
            .isInfoEnabled();
    }

    @Override
    default boolean isWarnEnabled() {
        return this.loggingContext()
            .isWarnEnabled();
    }

    @Override
    default boolean isErrorEnabled() {
        return this.loggingContext()
            .isErrorEnabled();
    }

    @Override
    default boolean isNoneEnabled() {
        return this.loggingContext()
            .isNoneEnabled();
    }

    @Override
    default boolean isLoggingEnabled(final LoggingLevel level) {
        return this.loggingContext()
            .isLoggingEnabled(level);
    }

    @Override
    default LoggingLevel loggingLevel() {
        return this.loggingContext()
            .loggingLevel();
    }

    LoggingContext loggingContext();
}
