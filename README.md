[![Build Status](https://github.com/mP1/walkingkooka-logging/actions/workflows/build.yaml/badge.svg)](https://github.com/mP1/walkingkooka-logging/actions/workflows/build.yaml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-logging/badge.svg)](https://coveralls.io/github/mP1/walkingkooka-logging)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-logging.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-logging/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-logging.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-logging/alerts/)
![](https://tokei.rs/b1/github/mP1/walkingkooka-logging)
[![J2CL compatible](https://img.shields.io/badge/J2CL-compatible-brightgreen.svg)](https://github.com/mP1/j2cl-central)

# walkingkooka-logging
Defines the core logging abstractions.

- Global and/or cascading [LoggingLevel](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/LoggingLevel.java).
- Support for changing the global [LoggingLevel](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/LoggingLevel.java) via [EnvironmentContext#setLoggingLevel](https://github.com/mP1/walkingkooka-environment/blob/master/src/main/java/walkingkooka/environment/EnvironmentContext.java)
- 5 levels of [LoggingLevel](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/LoggingLevel.java).
- Support for named loggers having individual and different [LoggingLevel](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/LoggingLevel.java)
- Named loggers defined within a [Properties](https://github.com/mP1/walkingkooka-props/blob/master/src/main/java/walkingkooka/props/Properties.java).
- Support for different logging configuration per user/session.
- Support for printing tree (nested) logging messages.
- [TODO] Support for buffering logging messages which are ignored unless an ERROR is logged.

## [CanLog](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/CanLog.java)

- [filter](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/CanLogFilter.java)
- [indentingPrinter](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/CanLogSharedIndentingPrinter.java)
- [null](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/CanLogNull.java)
- [printer](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/CanLogSharedPrinter.java)
- [tee](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/CanLogTee.java)

## [LoggingContext](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/LoggingContext.java)

- [canLog](https://github.com/mP1/walkingkooka-logging/blob/master/src/main/java/walkingkooka/logging/LoggingContextCanLog.java)