package ninja.bryansills.sneak

import kotlin.experimental.xor

class Sneak(private val salt: ByteArray) {
  fun obfuscate(rawText: String): ByteArray {
    val inputBytes = rawText.toByteArray(charset = Charsets.UTF_8)
    return bitwiseFlip(inputBytes)
  }

  fun deobfuscate(bytes: ByteArray): String {
    val deciphered = bitwiseFlip(bytes)
    return deciphered.decodeToString()
  }

  private fun bitwiseFlip(bytes: ByteArray): ByteArray {
    return bytes.mapIndexed { index, byte -> byte.xor(salt[index.mod(salt.size)]) }.toByteArray()
  }
}
