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

import java.util.Objects;

/**
 * A {@link CanLog} that logs each and every message to each of the given {@link CanLog}.
 */
final class CanLogTee implements CanLog {

    static CanLogTee with(final CanLog first,
                          final CanLog second) {
        return new CanLogTee(
            Objects.requireNonNull(first, "first"),
            Objects.requireNonNull(second, "second")
        );
    }

    private CanLogTee(final CanLog first,
                      final CanLog second) {
        super();

        this.first = first;
        this.second = second;
    }

    // CanLog...........................................................................................................

    @Override
    public void logEnter(final LoggerPath logger) {
        this.first.logEnter(logger);
        this.second.logEnter(logger);
    }

    @Override
    public void logExit() {
        this.first.logExit();
        this.second.logExit();
    }

    @Override
    public void log(final LoggingLevel level,
                    final String message,
                    final Throwable throwable) {
        this.first.log(level, message, throwable);
        this.second.log(level, message, throwable);
    }

    private CanLog first;

    private CanLog second;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.first + " " + this.second;
    }
}
