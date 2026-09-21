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
 * A collection of {@link CanLog}.
 */
public final class CanLogs implements PublicStaticHelper {

    /**
     * {@link FakeCanLog}
     */
    public static FakeCanLog fake() {
        return new FakeCanLog();
    }

    /**
     * {@link CanLogFilter}
     */
    public static CanLog filter(final CanLog canLog,
                                final CanLoggingLevel canLoggingLevel) {
        return CanLogFilter.with(
            canLog,
            canLoggingLevel
        );
    }

    /**
     * {@link CanLogNull}
     */
    public static CanLog nullCanLog() {
        return CanLogNull.INSTANCE;
    }

    /**
     * {@link CanLogPrinter}
     */
    public static CanLog printer(final Printer printer) {
        return CanLogPrinter.with(printer);
    }

    /**
     * {@link CanLogTee}
     */
    public static CanLog tee(final CanLog first,
                             final CanLog second) {
        return CanLogTee.with(
            first,
            second
        );
    }

    /**
     * Stop creation
     */
    private CanLogs() {
        throw new UnsupportedOperationException();
    }
}
