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

public final class LoggingLevelProviderPropertiesTest implements LoggingLevelProviderTesting2<LoggingLevelProviderProperties>,
    HasPropertiesTesting,
    PackagePrivateClassTesting<LoggingLevelProviderProperties>,
    ThrowableTesting,
    ToStringTesting<LoggingLevelProviderProperties> {

    private final static Properties PROPERTIES = Properties.parse("hello.world=DEBUG\nhello=INFO");

    @Test
    public void testWithNullPropertiesFails() {
        assertThrows(
            NullPointerException.class,
            () -> LoggingLevelProviderProperties.with(
                null,
                LoggingLevel.DEBUG
            )
        );
    }

    @Test
    public void testWithNullHasLoggingLevelFails() {
        assertThrows(
            NullPointerException.class,
            () -> LoggingLevelProviderProperties.with(
                Properties.EMPTY,
                null
            )
        );
    }

    @Test
    public void testWithPropertiesHasInvalidLoggingLevelFails() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> LoggingLevelProviderProperties.with(
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

    @Override
    public LoggingLevelProviderProperties createLoggingLevelProvider() {
        return LoggingLevelProviderProperties.with(
            PROPERTIES,
            LoggingLevel.NONE
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createLoggingLevelProvider(),
            "hello=INFO\r\n" +
                "hello.world=DEBUG\r\n" +
                "NONE"
        );
    }

    // HasProperties....................................................................................................

    @Test
    public void testProperties() {
        this.propertiesAndCheck(
            this.createLoggingLevelProvider(),
            PROPERTIES
        );
    }

    // class............................................................................................................

    @Override
    public Class<LoggingLevelProviderProperties> type() {
        return LoggingLevelProviderProperties.class;
    }
}
