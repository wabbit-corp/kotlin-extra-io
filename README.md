# kotlin-extra-io

A Kotlin library offering enhanced utilities for input and output operations, focusing on file handling and byte order manipulations.

## Overview

The core aim of the kotlin-extra-io project is to provide Kotlin developers with enhanced input/output capabilities, particularly focusing on file handling and byte order manipulations.

## Installation

Add the following dependency to your project:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.wabbit-corp:kotlin-extra-io:1.0.0")
}
```

## Usage

The repository contains examples and implementations of reading data using different byte orders and performing atomic file copy operations. Here is a summary of how the library code can be used:

### 1. EndianInputStream Usage:
`EndianInputStream` allows for reading primitive data types from an `InputStream` with specified byte order (big-endian or little-endian).

```kotlin
val bos = ByteArrayOutputStream()
val os = DataOutputStream(bos)
os.writeShort(0x1234)
os.writeInt(0x12345678)
val bis = ByteArrayInputStream(bos.toByteArray())
val eis = EndianInputStream(bis, ByteOrder.BIG_ENDIAN)
val shortValue = eis.readShort()
val intValue = eis.readInt()
```

### 2. AtomicCopy Functionality:

Use `File.tryAtomicCopy(to: File)` or `Path.tryAtomicCopy(to: Path)` in contexts requiring robust file operations to prevent partial writes.
```kotlin
val sourceFile = File("source.txt")
val destinationFile = File("destination.txt")
sourceFile.tryAtomicCopy(destinationFile)
```

## Licensing

This project is licensed under the GNU Affero General Public License v3.0 (AGPL-3.0) for open source use.

For commercial use, please contact Wabbit Consulting Corporation (at wabbit@wabbit.one) for licensing terms.

## Contributing

Before we can accept your contributions, we kindly ask you to agree to our Contributor License Agreement (CLA).
