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

import walkingkooka.Context;

public interface LoggingContext extends Context {

    void debug(final String message);

    void debug(final String message,
               final Throwable throwable);

    void info(final String message);

    void info(final String message,
              final Throwable throwable);

    void warn(final String message);

    void warn(final String message,
              final Throwable throwable);

    void error(final String message);

    void error(final String message,
               final Throwable throwable);

    void log(final LoggingLevel level,
             final String message);

    void log(final LoggingLevel level,
             final String message,
             final Throwable throwable);

    boolean isDebugEnabled();

    boolean isInfoEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();

    boolean isNoneEnabled();

    LoggingLevel loggingLevel();
}
