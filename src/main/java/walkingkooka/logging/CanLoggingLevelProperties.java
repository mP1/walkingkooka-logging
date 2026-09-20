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

import walkingkooka.collect.map.Maps;
import walkingkooka.props.HasProperties;
import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesPath;
import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.Map;
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

        final Map<PropertiesPath, LoggingLevel> loggerToLoggingLevel = Maps.hash();

        for (Entry<PropertiesPath, String> propertiesPathAndLoggingLevel : properties.entries()) {
            PropertiesPath propertiesPath = propertiesPathAndLoggingLevel.getKey();
            final String loggingLevelName = propertiesPathAndLoggingLevel.getValue();

            final LoggingLevel l;
            try {
                l = LoggingLevel.valueOf(loggingLevelName);
            } catch (final IllegalArgumentException cause) {
                throw new IllegalArgumentException(
                    "Properties entry " + CharSequences.quoteAndEscape(propertiesPath.toString()) + " contains invalid LoggingLevel " + CharSequences.quoteAndEscape(loggingLevelName),
                    cause
                );
            }

            loggerToLoggingLevel.put(
                propertiesPath,
                l
            );

            for (; ; ) {
                propertiesPath = propertiesPath.parent()
                    .orElse(null);
                if (null == propertiesPath) {
                    break;
                }

                if (null != loggerToLoggingLevel.putIfAbsent(
                    propertiesPath,
                    l
                )) {
                    break;
                }
            }
        }
        this.loggerToLoggingLevel = loggerToLoggingLevel;

        this.properties = properties;
        this.loggingLevel = loggingLevel;
    }

    @Override
    public LoggingLevel loggingLevelFor(final LoggerPath path) {
        Objects.requireNonNull(path, "path");

        LoggingLevel loggingLevel;

        PropertiesPath p = path.propertiesPath;
        do {
            loggingLevel = this.loggerToLoggingLevel.get(p);
            if(null != loggingLevel) {
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

    private final Map<PropertiesPath, LoggingLevel> loggerToLoggingLevel;

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
