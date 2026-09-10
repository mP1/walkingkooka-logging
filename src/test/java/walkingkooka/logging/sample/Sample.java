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

package walkingkooka.logging.sample;

import org.junit.jupiter.api.Test;
import walkingkooka.logging.CanLogs;
import walkingkooka.logging.LoggingContexts;
import walkingkooka.logging.LoggingLevel;

public final class Sample {

    public static void main(final String[] args) {
        final Sample sample = new Sample();
        sample.testNullLoggingContext();
    }

    @Test
    public void testNullLoggingContext() {
        LoggingContexts.canLog(
                () -> LoggingLevel.NONE,
                CanLogs.nullCanLog()
            ).error("Hello World 123");
    }
}
