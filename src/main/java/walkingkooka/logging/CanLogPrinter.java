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
import walkingkooka.text.printer.Printer;

import java.io.PrintWriter;
import java.util.Objects;

/**
 * A {@link CanLog} that does not filter, and prints all messages and dumps the stack trace for any given {@link Throwable}.
 * The {@link LoggingLevel} is ignored and never printed.
 */
final class CanLogPrinter implements CanLog {

    static CanLogPrinter with(final Printer printer) {
        return new CanLogPrinter(
            Objects.requireNonNull(printer, "printer")
        );
    }

    private CanLogPrinter(final Printer printer) {
        super();

        this.printer = printer;
    }

    // CanLog...........................................................................................................

    @Override
    public void log(final LoggingLevel level,
                    final String message,
                    final Throwable throwable) {
        Objects.requireNonNull(level, "level");

        final Printer printer = this.printer;

        printer.println(message);

        if (null != throwable) {
            try (final PrintWriter printWriter = printer.asPrintWriter()) {
                throwable.printStackTrace(printWriter);
                printWriter.flush();
            }
        }
    }

    private final Printer printer;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.printer.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof CanLogPrinter &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final CanLogPrinter other) {
        return this.printer.equals(other.printer);
    }

    @Override
    public String toString() {
        return this.printer.toString();
    }
}
