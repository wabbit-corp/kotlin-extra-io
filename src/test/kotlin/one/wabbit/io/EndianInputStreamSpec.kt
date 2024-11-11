package std.data

import one.wabbit.io.EndianInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.nio.ByteOrder
import kotlin.test.Test
import kotlin.test.assertEquals

class EndianInputStreamSpec {
    infix fun <A> A.mustBe(that: A) = assertEquals(that, this)

    @Test fun test() {
        val bos = ByteArrayOutputStream()
        val os = DataOutputStream(bos)

        os.writeShort(0x1234)
        os.writeInt(0x12345678)
        os.writeLong(0x123456789abcdef0)
        os.writeLong(0x123456789abcdef0)

        run {
            val bis = bos.toByteArray().inputStream()
            val eis = EndianInputStream(bis, ByteOrder.BIG_ENDIAN)
            eis.readShort() mustBe 0x1234.toShort()
            eis.readInt() mustBe 0x12345678
            eis.readLong() mustBe 0x123456789abcdef0L
        }

        run {
            val bis = bos.toByteArray().inputStream()
            val eis = EndianInputStream(bis, ByteOrder.LITTLE_ENDIAN)
            eis.readShort() mustBe 0x3412.toShort()
            eis.readInt() mustBe 0x78563412
            eis.readLong() mustBe 0xf0debc9a78563412UL.toLong()
        }
    }
}
