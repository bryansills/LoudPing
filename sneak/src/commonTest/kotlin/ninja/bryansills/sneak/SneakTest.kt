package ninja.bryansills.sneak

import kotlin.test.Test
import kotlin.test.assertEquals

class SneakTest {
    @Test
    fun `make sure it works`() {
        val salt = "haha butts 1234 this is long enough".toByteArray()
        val sneak = Sneak(salt)

        val input = "this is the text i am trying to obfuscate !@#"

        val obfuscated = sneak.obfuscate(input)

        val output = sneak.deobfuscate(obfuscated)

        assertEquals(input, output)
    }
}
