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

import walkingkooka.props.Properties;
import walkingkooka.reflect.PublicStaticHelper;

/**
 * A collection of {@link CanLoggingLevel}.
 */
public final class CanLoggingLevels implements PublicStaticHelper {

    /**
     * {@link FakeCanLoggingLevel}
     */
    public static FakeCanLoggingLevel fake() {
        return new FakeCanLoggingLevel();
    }

    /**
     * {@link CanLoggingLevelProperties}
     */
    public static CanLoggingLevel properties(final Properties properties,
                                             final HasLoggingLevel loggingLevel) {
        return CanLoggingLevelProperties.with(
            properties,
            loggingLevel
        );
    }

    /**
     * Stop creation
     */
    private CanLoggingLevels() {
        throw new UnsupportedOperationException();
    }
}
