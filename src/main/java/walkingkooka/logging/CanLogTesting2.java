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
import walkingkooka.reflect.PackagePrivateClassTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface CanLogTesting2<C extends CanLog> extends CanLogTesting,
    PackagePrivateClassTesting<C> {

    @Test
    default void testLogWithNullLoggingLevelFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCanLog()
                .log(
                    null, // LoggingLevel
                    "message123",
                    new RuntimeException()
                )
        );
    }

    C createCanLog();
}
