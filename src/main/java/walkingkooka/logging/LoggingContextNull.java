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
 * A {@link LoggingContext} that logs nothing and ignores all messages.
 */
final class LoggingContextNull implements LoggingContext {

    /**
     * Singleton
     */
    final static LoggingContextNull INSTANCE = new LoggingContextNull();
    
    @Override
    public void debug(final String message) {
        //
    }

    @Override
    public void debug(final String message,
                      final Throwable throwable) {
        //
    }

    @Override
    public void info(final String message) {
        //
    }

    @Override
    public void info(final String message,
                     final Throwable throwable) {
        //
    }

    @Override
    public void warn(final String message) {
        //
    }

    @Override
    public void warn(final String message,
                     final Throwable throwable) {
        //
    }

    @Override
    public void error(final String message) {
        //
    }

    @Override
    public void error(final String message,
                      final Throwable throwable) {
        //
    }

    @Override
    public void log(final LoggingLevel level,
                    final String message) {
        //
    }

    @Override
    public void log(final LoggingLevel level,
                    final String message,
                    final Throwable throwable) {
        //
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public boolean isNoneEnabled() {
        return true;
    }

    @Override
    public boolean isLoggingEnabled(final LoggingLevel level) {
        return LoggingLevel.NONE == level;
    }

    @Override
    public LoggingLevel loggingLevel() {
        return LoggingLevel.NONE;
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.loggingLevel()
            .toString();
    }
}
