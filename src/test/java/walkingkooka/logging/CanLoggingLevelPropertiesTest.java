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
import walkingkooka.ToStringTesting;
import walkingkooka.props.HasPropertiesTesting;
import walkingkooka.props.Properties;
import walkingkooka.reflect.PackagePrivateClassTesting;
import walkingkooka.reflect.ThrowableTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CanLoggingLevelPropertiesTest implements CanLoggingLevelTesting2<CanLoggingLevelProperties>,
    HasPropertiesTesting,
    PackagePrivateClassTesting<CanLoggingLevelProperties>,
    ThrowableTesting,
    ToStringTesting<CanLoggingLevelProperties> {

    private final static Properties PROPERTIES = Properties.parse("hello.world=DEBUG\nhello=INFO");

    @Test
    public void testWithNullPropertiesFails() {
        assertThrows(
            NullPointerException.class,
            () -> CanLoggingLevelProperties.with(
                null,
                LoggingLevel.DEBUG
            )
        );
    }

    @Test
    public void testWithNullHasLoggingLevelFails() {
        assertThrows(
            NullPointerException.class,
            () -> CanLoggingLevelProperties.with(
                Properties.EMPTY,
                null
            )
        );
    }

    @Test
    public void testWithPropertiesHasInvalidLoggingLevelFails() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> CanLoggingLevelProperties.with(
                Properties.parse("hello.world=DEBUG\nhello.invalid=BAD-LOGGER_LEVEL"),
                LoggingLevel.DEBUG
            )
        );

        this.getMessageAndCheck(
            thrown,
            "Properties entry \"hello.invalid\" contains invalid LoggingLevel \"BAD-LOGGER_LEVEL\""
        );
    }

    @Test
    public void testLoggingLevelForPropertiesEntry() {
        this.loggingLevelForAndCheck(
            LoggerPath.parse("hello.world"),
            LoggingLevel.DEBUG
        );
    }

    @Test
    public void testLoggingLevelForPropertiesEntryParent() {
        this.loggingLevelForAndCheck(
            LoggerPath.parse("hello.222"),
            LoggingLevel.INFO
        );
    }

    @Test
    public void testLoggingLevelForPropertiesPathMissing() {
        this.loggingLevelForAndCheck(
            LoggerPath.parse("missing.123"),
            LoggingLevel.NONE
        );
    }

    @Test
    public void testLoggingLevelMany() {
        final CanLoggingLevelProperties canLoggingLevelProperties = CanLoggingLevelProperties.with(
            Properties.parse(
                "hello.world.111=DEBUG\n" +
                    "hello.222=INFO\n" +
                    "hello=WARN\n"
            ),
            LoggingLevel.ERROR
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("hello.world.111"),
            LoggingLevel.DEBUG
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("hello.world"),
            LoggingLevel.DEBUG
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("hello.222"),
            LoggingLevel.INFO
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("hello"),
            LoggingLevel.WARN
        );
    }

    @Test
    public void testLoggingLevelMany2() {
        final CanLoggingLevelProperties canLoggingLevelProperties = CanLoggingLevelProperties.with(
            Properties.parse(
                "aaa.222=INFO\n" +
                    "bbb=WARN\n" +
                "hello.world.111=DEBUG\n"
            ),
            LoggingLevel.ERROR
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("hello.world.111"),
            LoggingLevel.DEBUG
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("hello.world"),
            LoggingLevel.DEBUG
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("aaa.222"),
            LoggingLevel.INFO
        );

        this.loggingLevelForAndCheck(
            canLoggingLevelProperties,
            LoggerPath.parse("bbb"),
            LoggingLevel.WARN
        );
    }

    @Override
    public CanLoggingLevelProperties createCanLoggingLevel() {
        return CanLoggingLevelProperties.with(
            PROPERTIES,
            LoggingLevel.NONE
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createCanLoggingLevel(),
            "hello=INFO\r\n" +
                "hello.world=DEBUG\r\n" +
                "NONE"
        );
    }

    // HasProperties....................................................................................................

    @Test
    public void testProperties() {
        this.propertiesAndCheck(
            this.createCanLoggingLevel(),
            PROPERTIES
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testPrintTree() {
        this.treePrintAndCheck(
            this.createCanLoggingLevel(),
            "CanLoggingLevelPropertiesloggingLevel\n" +
                "    NONE\n" +
                "  properties\n" +
                "    hello=INFO\n" +
                "    hello.world=DEBUG\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CanLoggingLevelProperties> type() {
        return CanLoggingLevelProperties.class;
    }
}
