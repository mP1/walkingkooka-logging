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
import walkingkooka.naming.PathName;
import walkingkooka.props.PropertiesName;
import walkingkooka.text.CaseSensitivity;

public final class LoggerName implements PathName,
    Comparable<LoggerName> {

    public static LoggerName with(final String name) {
        return withPropertiesName(
            PropertiesName.with(name)
        );
    }

    static LoggerName withPropertiesName(final PropertiesName propertiesName) {
        return new LoggerName(propertiesName);
    }

    private LoggerName(final PropertiesName propertiesName) {
        super();

        this.propertiesName = propertiesName;
    }

    @Override
    public String value() {
        return this.propertiesName.value();
    }

    final PropertiesName propertiesName;

    // HasCaseSensitivity...............................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }

    public final static CaseSensitivity CASE_SENSITIVITY = PropertiesName.CASE_SENSITIVITY;

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final LoggerName other) {
        return this.propertiesName.compareTo(other.propertiesName);
    }

    // Object...........................................................................................................

    public int hashCode() {
        return this.propertiesName.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof LoggerName &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final LoggerName other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return this.propertiesName.toString();
    }
}
