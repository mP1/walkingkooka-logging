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

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.text.printer.Printer;

/**
 * A collection of {@link LoggingContext}.
 */
public final class LoggingContexts implements PublicStaticHelper {

    /**
     * {@link FakeLoggingContext}
     */
    public static FakeLoggingContext fake() {
        return new FakeLoggingContext();
    }

    /**
     * {@link LoggingContextNull}
     */
    public static LoggingContext nullLoggingContext() {
        return LoggingContextNull.INSTANCE;
    }

    /**
     * {@link LoggingContextPrinter}
     */
    public static LoggingContext printer(final HasLoggingLevel loggingLevel,
                                         final Printer printer) {
        return LoggingContextPrinter.with(
            loggingLevel,
            printer
        );
    }

    /**
     * Stop creation
     */
    private LoggingContexts() {
        throw new UnsupportedOperationException();
    }
}
