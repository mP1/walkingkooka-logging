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

import walkingkooka.naming.Path;
import walkingkooka.naming.PathSeparator;
import walkingkooka.props.PropertiesPath;

import java.util.Optional;

/**
 * A complete logger path that mimics a {@link PropertiesPath}.
 */
public final class LoggerPath implements Path<LoggerPath, LoggerName>, Comparable<LoggerPath> {

    /**
     * {@link PathSeparator} instance
     */
    public final static PathSeparator SEPARATOR = PropertiesPath.SEPARATOR;

    final static Optional<LoggerPath> NO_PARENT = Optional.empty();

    /**
     * Parses the {@link String} into a {@link LoggerPath}
     */
    public static LoggerPath parse(final String path) {
        return new LoggerPath(
            PropertiesPath.parse(path)
        );
    }

    private LoggerPath(final PropertiesPath propertiesPath) {
        super();

        this.propertiesPath = propertiesPath;
    }
    
    @Override 
    public String value() {
        return this.propertiesPath.value();
    }

    @Override
    public PathSeparator separator() {
        return SEPARATOR;
    }

    @Override
    public LoggerName name() {
        if(null == this.name) {
            this.name = LoggerName.withPropertiesName(
                this.propertiesPath.name()
            );
        }
        return this.name;
    }

    private LoggerName name;
    
    @Override
    public Optional<LoggerPath> parent() {
        if(null == this.parent) {
            this.parent = this.propertiesPath.parent()
                .map(LoggerPath::new);
        }
        return this.parent;
    }

    private Optional<LoggerPath> parent;

    @Override
    public LoggerPath append(final LoggerName loggerName) {
        return new LoggerPath(
            this.propertiesPath.append(
                loggerName.propertiesName
            )
        );
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.propertiesPath.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return (this == other) ||
            ((other instanceof LoggerPath) && this.equals0((LoggerPath) other));
    }

    private boolean equals0(final LoggerPath other) {
        return this.propertiesPath.equals(other.propertiesPath);
    }

    @Override
    public String toString() {
        return this.propertiesPath.toString();
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final LoggerPath other) {
        return this.propertiesPath.compareTo(other.propertiesPath);
    }

    private final PropertiesPath propertiesPath;
}
