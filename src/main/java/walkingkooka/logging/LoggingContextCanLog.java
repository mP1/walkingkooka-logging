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

import walkingkooka.Cast;

import java.util.Objects;

/**
 * A {@link LoggingContext} that delegates all log messsages and {@link Throwable} to the given {@link CanLog}.
 */
final class LoggingContextCanLog implements LoggingContext {

    /**
     * Factory
     */
    static LoggingContextCanLog with(final HasLoggingLevel loggingLevel,
                                     final CanLog canLog) {
        return new LoggingContextCanLog(
            Objects.requireNonNull(loggingLevel, "loggingLevel"),
            Objects.requireNonNull(canLog, "canLog")
        );
    }

    private LoggingContextCanLog(final HasLoggingLevel loggingLevel,
                                 final CanLog canLog) {
        super();

        this.loggingLevel = loggingLevel;
        this.canLog = canLog;
    }

    @Override
    public void debug(final String message) {
        this.debug(
            message,
            null
        );
    }

    @Override
    public void debug(final String message,
                      final Throwable throwable) {
        this.log(
            LoggingLevel.DEBUG,
            message,
            throwable
        );
    }

    @Override
    public void info(final String message) {
        this.info(
            message,
            null
        );
    }

    @Override
    public void info(final String message,
                     final Throwable throwable) {
        this.log(
            LoggingLevel.INFO,
            message,
            throwable
        );
    }

    @Override
    public void warn(final String message) {
        this.warn(
            message,
            null
        );
    }

    @Override
    public void warn(final String message,
                     final Throwable throwable) {
        this.log(
            LoggingLevel.WARN,
            message,
            throwable
        );
    }

    @Override
    public void error(final String message) {
        this.error(
            message,
            null
        );
    }

    @Override
    public void error(final String message,
                      final Throwable throwable) {
        this.log(
            LoggingLevel.ERROR,
            message,
            throwable
        );
    }

    @Override
    public void log(final LoggingLevel level,
                    final String message) {
        this.log(
            level,
            message,
            null
        );
    }

    @Override
    public void log(final LoggingLevel level,
                    final String message,
                    final Throwable throwable) {
        if (this.isLoggingEnabled(level)) {
            this.canLog.log(
                level,
                message,
                throwable
            );
        }
    }

    private final CanLog canLog;

    @Override
    public boolean isDebugEnabled() {
        return this.isLoggingEnabled(LoggingLevel.DEBUG);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.isLoggingEnabled(LoggingLevel.INFO);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.isLoggingEnabled(LoggingLevel.WARN);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.isLoggingEnabled(LoggingLevel.ERROR);
    }

    @Override
    public boolean isNoneEnabled() {
        return this.isLoggingEnabled(LoggingLevel.NONE);
    }

    @Override
    public boolean isLoggingEnabled(final LoggingLevel level) {
        return this.loggingLevel()
            .isEnabled(level);
    }

    @Override
    public LoggingLevel loggingLevel() {
        return this.loggingLevel.loggingLevel();
    }

    private HasLoggingLevel loggingLevel;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.canLog,
            this.loggingLevel
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof LoggingContextCanLog &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final LoggingContextCanLog other) {
        return this.canLog.equals(other.canLog) &&
            this.loggingLevel.equals(other.loggingLevel);
    }

    @Override
    public String toString() {
        return this.loggingLevel() + " " + this.canLog;
    }
}
