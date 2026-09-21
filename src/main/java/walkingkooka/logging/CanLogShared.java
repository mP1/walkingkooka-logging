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

import javaemul.internal.annotations.GwtIncompatible;
import walkingkooka.Cast;
import walkingkooka.collect.stack.Stack;
import walkingkooka.collect.stack.Stacks;
import walkingkooka.text.printer.Printer;

import java.io.PrintWriter;
import java.util.Objects;

abstract class CanLogShared<P extends Printer> extends CanLogSharedGwt
    implements CanLog {

    CanLogShared(final P printer) {
        super();

        this.printer = printer;
    }

    // CanLog...........................................................................................................

   final void pushLogger(final LoggerPath logger) {
        Objects.requireNonNull(logger, "logger");

        this.loggers.push(logger);
    }

    final void popLogger() {
        // NO LOCK!
        if (this.loggers.isNotEmpty()) {
            this.loggers.pop();
        }
    }

    final Stack<LoggerPath> loggers = Stacks.arrayList();

    @Override
    public final void log(final LoggingLevel loggingLevel,
                          final String message,
                          final Throwable throwable) {
        Objects.requireNonNull(loggingLevel, "level");

        final Printer printer = this.printer;

        // LOGGER1 DEBUG message1
        // DEBUG message 1
        if (this.loggers.isNotEmpty()) {
            printer.print(
                this.loggers.peek().value()
            );
            printer.print(" ");
        }

        printer.print(loggingLevel.name());
        printer.print(" ");
        printer.println(message);

        this.logThrowable(
            throwable,
            printer
        );
    }

    @Override
    @GwtIncompatible
    void logThrowable(final Throwable throwable,
                      final Printer printer) {
        if (null != throwable) {
            final PrintWriter printWriter = printer.asPrintWriter();
            throwable.printStackTrace(printWriter);
            printWriter.flush();
        }
    }

    private final P printer;

    @Override
    public final boolean isLoggingEnabled(final LoggingLevel loggingLevel) {
        Objects.requireNonNull(loggingLevel, "loggingLevel");
        return true;
    }

    // Object...........................................................................................................

    @Override
    public final int hashCode() {
        return this.printer.hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other ||
            null != other && this.getClass() == other.getClass() &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final CanLogShared other) {
        return this.printer.equals(other.printer);
    }

    @Override
    public final String toString() {
        return this.printer.toString();
    }
}
