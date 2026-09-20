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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.set.Sets;
import walkingkooka.naming.PathSeparator;
import walkingkooka.naming.PathTesting;
import walkingkooka.props.PropertiesPath;
import walkingkooka.test.ParseStringTesting;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class LoggerPathTest implements PathTesting<LoggerPath, LoggerName>,
    ParseStringTesting<LoggerPath> {

    @Test
    public void testWithNullPropertiesFails() {
        assertThrows(
            NullPointerException.class,
            () -> LoggerPath.with(null)
        );
    }

    @Override
    public void testAppendNameToRoot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public LoggerPath root() {
        throw new UnsupportedOperationException();
    }

    @Override
    public LoggerPath createPath() {
        return LoggerPath.parse("hello");
    }

    @Override
    public LoggerPath parsePath(final String name) {
        return LoggerPath.parse(name);
    }

    @Override
    public LoggerName createName(final int n) {
        return LoggerName.with("hello-" + n);
    }

    @Override
    public PathSeparator separator() {
        return PropertiesPath.SEPARATOR;
    }

    @Override
    public LoggerPath createComparable() {
        return this.createPath();
    }

    // ParseStringTesting ..............................................................................................

    @Test
    public void testParseEmptyComponent() {
        this.parseStringFails(
            "double..dot",
            IllegalArgumentException.class
        );
    }

    @Test
    public void testParseFlat() {
        final String value = "xyz";
        final LoggerPath path = LoggerPath.parse(value);
        this.valueAndCheck(path, value);
        this.rootCheck(path);
        this.nameCheck(
            path,
            LoggerName.with(value)
        );
    }

    @Test
    public void testParseHierarchical() {
        final String value = "ab.cd";
        final LoggerPath path = LoggerPath.parse(value);
        this.valueAndCheck(path, value);
        this.rootNotCheck(path);
        this.nameCheck(
            path,
            LoggerName.with("cd")
        );
        this.parentCheck(
            path,
            "ab"
        );
    }

    @Override
    public LoggerPath parseString(final String text) {
        return LoggerPath.parse(text);
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException expected) {
        return expected;
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> expected) {
        return expected;
    }

    // class............................................................................................................

    @Override
    public Class<LoggerPath> type() {
        return LoggerPath.class;
    }

    @Override
    public Set<LoggerPath> intentionalDuplicateConstants() {
        return Sets.empty();
    }
}
