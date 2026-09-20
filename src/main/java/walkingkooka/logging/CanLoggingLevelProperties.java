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

import walkingkooka.props.HasProperties;
import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesPath;
import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.Map.Entry;
import java.util.Objects;

/**
 * A {@link CanLoggingLevel} that sources {@link LoggingLevel} from a {@link Properties}.
 */
final class CanLoggingLevelProperties implements CanLoggingLevel,
    HasProperties,
    TreePrintable {

    static CanLoggingLevelProperties with(final Properties properties,
                                          final HasLoggingLevel loggingLevel) {
        return new CanLoggingLevelProperties(
            Objects.requireNonNull(properties, "properties"),
            Objects.requireNonNull(loggingLevel, "loggingLevel")
        );
    }

    private CanLoggingLevelProperties(final Properties properties,
                                      final HasLoggingLevel loggingLevel) {
        super();

        for(Entry<PropertiesPath, String> propertiesPathAndLoggingLevel : properties.entries()) {
            final PropertiesPath propertiesPath = propertiesPathAndLoggingLevel.getKey();
            final String loggingLevelName = propertiesPathAndLoggingLevel.getValue();

            try {
                LoggingLevel.valueOf(loggingLevelName);
            } catch (final IllegalArgumentException cause) {
                throw new IllegalArgumentException(
                    "Properties entry " + CharSequences.quoteAndEscape(propertiesPath.toString()) + " contains invalid LoggingLevel " + CharSequences.quoteAndEscape(loggingLevelName),
                    cause
                );
            }
        }

        this.properties = properties;
        this.loggingLevel = loggingLevel;
    }

    @Override
    public LoggingLevel loggingLevelFor(final LoggerPath path) {
        Objects.requireNonNull(path, "path");

        LoggingLevel loggingLevel = null;

        LoggerPath p = path;
        do {
            final String loggingLevelString = this.properties.get(p.propertiesPath)
                .orElse(null);
            if(null != loggingLevelString) {
                loggingLevel = LoggingLevel.valueOf(loggingLevelString);
                break;
            }

            // try again with parent.
            p = p.parent()
                .orElse(null);
        } while(null != p);

        return null != loggingLevel ?
            loggingLevel :
            this.loggingLevel.loggingLevel();
    }

    // HasLoggingLevel..................................................................................................

    @Override
    public LoggingLevel loggingLevel() {
        return this.loggingLevel.loggingLevel();
    }

    /**
     * Provides the default if a {@link LoggingLevel} cannot be determined from the properties.
     */
    private final HasLoggingLevel loggingLevel;

    // HasProperties....................................................................................................

    @Override
    public Properties properties() {
        return this.properties;
    }

    private final Properties properties;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.properties.toString() + this.loggingLevel;
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.print(this.getClass().getSimpleName());
        printer.indent();
        {
            printer.println("loggingLevel");
            printer.indent();
            {
                TreePrintable.printTreeOrToString(
                    this.loggingLevel,
                    printer
                );
            }
            printer.outdent();

            printer.println("properties");
            printer.indent();
            {
                this.properties.printTree(printer);
            }
            printer.outdent();
        }
        printer.outdent();
    }
}
