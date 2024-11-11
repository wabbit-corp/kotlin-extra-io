package one.wabbit.io

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

fun File.tryAtomicCopy(to: File) {
    val from = this
    from.toPath().tryAtomicCopy(to.toPath())
}

fun Path.tryAtomicCopy(to: Path) {
    val from = this
    try {
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
    } catch (e: java.lang.UnsupportedOperationException) {
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING)
    }
}
