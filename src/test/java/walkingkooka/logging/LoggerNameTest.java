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

import walkingkooka.naming.NameTesting;
import walkingkooka.text.CaseSensitivity;

public final class LoggerNameTest implements NameTesting<LoggerName, LoggerName> {

    @Override
    public LoggerName createName(final String name) {
        return LoggerName.with(name);
    }

    @Override
    public CaseSensitivity caseSensitivity() {
        return LoggerName.CASE_SENSITIVITY;
    }

    @Override
    public String nameText() {
        return "banana";
    }

    @Override
    public String differentNameText() {
        return "different";
    }

    @Override
    public String nameTextLess() {
        return "apple";
    }

    @Override
    public Class<LoggerName> type() {
        return LoggerName.class;
    }
}
