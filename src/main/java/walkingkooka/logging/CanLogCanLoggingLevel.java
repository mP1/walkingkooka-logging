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

import walkingkooka.collect.stack.Stack;
import walkingkooka.collect.stack.Stacks;

import java.util.Objects;

/**
 * A {@link CanLog} that logs messages to a given {@link CanLog} adding {@link #isLoggingEnabled(LoggingLevel)} guards
 * using the given {@link CanLoggingLevel}.
 */
final class CanLogCanLoggingLevel implements CanLog {

    static CanLogCanLoggingLevel with(final CanLog canLog,
                                      final CanLoggingLevel canLoggingLevel) {
        return new CanLogCanLoggingLevel(
            Objects.requireNonNull(canLog, "canLog"),
            Objects.requireNonNull(canLoggingLevel, "canLoggingLevel")
        );
    }

    private CanLogCanLoggingLevel(final CanLog canLog,
                                  final CanLoggingLevel canLoggingLevel) {
        super();

        this.canLog = canLog;
        this.canLoggingLevel = canLoggingLevel;
    }

    @Override
    public void logEnter(final LoggerPath logger) {
        Objects.requireNonNull(logger, "logger");

        this.loggers.push(logger);
        this.canLog.logEnter(logger);
    }

    @Override
    public void logExit() {
        if(this.loggers.isNotEmpty()) {
            this.loggers.pop();
        }
        this.canLog.logExit();
    }

    /**
     * Tracks the current {@link LoggerPath}.
     */
    private final Stack<LoggerPath> loggers = Stacks.arrayList();

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

    @Override
    public boolean isLoggingEnabled(final LoggingLevel loggingLevel) {
        Objects.requireNonNull(loggingLevel, "loggingLevel");

        return this.loggers.isEmpty() ?
            true :
            this.canLoggingLevel.loggingLevelFor(
                this.loggers.peek()
            ).isEnabled(loggingLevel);
    }

    private final CanLog canLog;

    private final CanLoggingLevel canLoggingLevel;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "canLog: " + this.canLog + " canLoggingLevel: " + this.canLoggingLevel;
    }
}
