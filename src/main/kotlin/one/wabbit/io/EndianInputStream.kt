package one.wabbit.io

import java.io.DataInput
import java.io.EOFException
import java.io.InputStream
import java.nio.ByteOrder

class EndianInputStream(val base: InputStream, val defaultByteOrder: ByteOrder) : InputStream(), DataInput {
    override fun read(): Int {
        return base.read()
    }

    override fun available(): Int = base.available()

    fun readShort(byteOrder: ByteOrder): Short {
        val b1 = base.read()
        if (b1 < 0) throw EOFException()
        val b2 = base.read()
        if (b2 < 0) throw EOFException()

        return if (byteOrder == ByteOrder.BIG_ENDIAN) {
            (b1 shl 8 or b2).toShort()
        } else {
            (b2 shl 8 or b1).toShort()
        }
    }

    fun readUShort(byteOrder: ByteOrder): UShort {
        val b1 = base.read()
        if (b1 < 0) throw EOFException()
        val b2 = base.read()
        if (b2 < 0) throw EOFException()

        return if (byteOrder == ByteOrder.BIG_ENDIAN) {
            (b1 shl 8 or b2).toUShort()
        } else {
            (b2 shl 8 or b1).toUShort()
        }
    }

    fun readInt(byteOrder: ByteOrder): Int {
        val b1 = base.read()
        if (b1 < 0) throw EOFException()
        val b2 = base.read()
        if (b2 < 0) throw EOFException()
        val b3 = base.read()
        if (b3 < 0) throw EOFException()
        val b4 = base.read()
        if (b4 < 0) throw EOFException()

        return if (byteOrder == ByteOrder.BIG_ENDIAN) {
            (b1 shl 24) or (b2 shl 16) or (b3 shl 8) or b4
        } else {
            (b4 shl 24) or (b3 shl 16) or (b2 shl 8) or b1
        }
    }

    fun readUInt(byteOrder: ByteOrder): UInt {
        val b1 = base.read()
        if (b1 < 0) throw EOFException()
        val b2 = base.read()
        if (b2 < 0) throw EOFException()
        val b3 = base.read()
        if (b3 < 0) throw EOFException()
        val b4 = base.read()
        if (b4 < 0) throw EOFException()

        return if (byteOrder == ByteOrder.BIG_ENDIAN) {
            (b1.toUInt() shl 24) or (b2.toUInt() shl 16) or (b3.toUInt() shl 8) or b4.toUInt()
        } else {
            (b4.toUInt() shl 24) or (b3.toUInt() shl 16) or (b2.toUInt() shl 8) or b1.toUInt()
        }
    }

    fun readLong(byteOrder: ByteOrder): Long {
        val b1 = base.read().toLong()
        if (b1 < 0) throw EOFException()
        val b2 = base.read().toLong()
        if (b2 < 0) throw EOFException()
        val b3 = base.read().toLong()
        if (b3 < 0) throw EOFException()
        val b4 = base.read().toLong()
        if (b4 < 0) throw EOFException()
        val b5 = base.read().toLong()
        if (b5 < 0) throw EOFException()
        val b6 = base.read().toLong()
        if (b6 < 0) throw EOFException()
        val b7 = base.read().toLong()
        if (b7 < 0) throw EOFException()
        val b8 = base.read().toLong()
        if (b8 < 0) throw EOFException()

        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return ((b1 shl 56) or
                    ((b2 and 0xff) shl 48) or
                    ((b3 and 0xff) shl 40) or
                    ((b4 and 0xff) shl 32) or
                    ((b5 and 0xff) shl 24) or
                    ((b6 and 0xff) shl 16) or
                    ((b7 and 0xff) shl 8) or
                    ((b8 and 0xff) shl 0))
        } else {
            return ((b8 shl 56) or
                    ((b7 and 0xff) shl 48) or
                    ((b6 and 0xff) shl 40) or
                    ((b5 and 0xff) shl 32) or
                    ((b4 and 0xff) shl 24) or
                    ((b3 and 0xff) shl 16) or
                    ((b2 and 0xff) shl 8) or
                    ((b1 and 0xff) shl 0))
        }
    }

    fun readULong(byteOrder: ByteOrder): ULong = readLong(byteOrder).toULong()

    override fun readUTF(): String {
        val length = readShort(ByteOrder.BIG_ENDIAN).toInt()
        val bytes = ByteArray(length)
        readFully(bytes)
        return String(bytes)
    }

    override fun readFully(b: ByteArray) {
        var off = 0
        var len = b.size
        while (len > 0) {
            val n = base.read(b, off, len)
            if (n < 0) {
                throw java.io.EOFException()
            }
            off += n
            len -= n
        }
    }

    override fun readFully(b: ByteArray, off: Int, len: Int) {
        if (len < 0) {
            throw IndexOutOfBoundsException()
        }
        var n = 0
        while (n < len) {
            val count = base.read(b, off + n, len - n)
            if (count < 0) {
                throw java.io.EOFException()
            }
            n += count
        }
    }


    override fun readBoolean(): Boolean {
        val b1 = base.read()
        if (b1 < 0) throw EOFException()
        return b1 != 0
    }
    override fun readByte(): Byte {
        val b1 = base.read()
        if (b1 < 0) throw EOFException()
        return b1.toByte()
    }
    override fun readUnsignedByte(): Int {
        val b1 = base.read()
        if (b1 < 0) throw EOFException()
        return b1 and 0xFF
    }
    override fun readShort(): Short = readShort(defaultByteOrder)
    override fun readUnsignedShort(): Int = readUShort(defaultByteOrder).toInt()
    override fun readChar(): Char = readShort(defaultByteOrder).toChar()
    override fun readInt(): Int = readInt(defaultByteOrder)
    override fun readLong(): Long = readLong(defaultByteOrder)
    override fun readFloat(): Float = java.lang.Float.intBitsToFloat(readInt())
    override fun readDouble(): Double = java.lang.Double.longBitsToDouble(readLong())

    override fun skipBytes(n: Int): Int {
        return base.skip(n.toLong()).toInt()
    }
    override fun readLine(): String {
        throw UnsupportedOperationException("readLine")
    }
}
