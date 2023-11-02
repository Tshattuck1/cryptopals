@file:OptIn(ExperimentalEncodingApi::class)


import java.io.File
import kotlin.experimental.xor
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

    @OptIn(ExperimentalEncodingApi::class)
    fun challenge1(input: String): String {
        return Base64.encode(input.fromHex())
    }

    fun challenge2(input: String, cypher: String):String {
        val inputBytes = input.fromHex()
        val cypherBytes = cypher.fromHex()
        val result = inputBytes.zip(cypherBytes).map { (a, b) -> a xor b }.toByteArray()
        return result.joinToString("") { "%02x".format(it) }
    }

    fun challenge3(input: String): List<Pair<String, Double>> {
        val ciphertext = input.fromHex();
        val keys = ('A'..'z').toMutableList()
        keys.addAll(('0'..'9'))
        return keys.map { xor(ciphertext, it) }
            .map { englishScore(it) }
    }

    fun challenge4(path: String): Pair<String, Double> {
        val lines = File(path).readLines().flatMap { challenge3(it) }
        return lines.maxBy { it.second }
    }

    fun englishScore(text: String): Pair<String, Double> {
        val englishFrequencies = mapOf(
            'a' to 8.167, 'b' to 1.492, 'c' to 2.782, 'd' to 4.253, 'e' to 12.702,
            'f' to 2.228, 'g' to 2.015, 'h' to 6.094, 'i' to 6.966, 'j' to 0.153,
            'k' to 0.772, 'l' to 4.025, 'm' to 2.406, 'n' to 6.749, 'o' to 7.507,
            'p' to 1.929, 'q' to 0.095, 'r' to 5.987, 's' to 6.327, 't' to 9.056,
            'u' to 2.758, 'v' to 0.978, 'w' to 2.360, 'x' to 0.150, 'y' to 1.974,
            'z' to 0.074, ' ' to 13.000
        )

        val score = text.filter { it.lowercaseChar() in englishFrequencies.keys }
            .sumOf { englishFrequencies[it.lowercaseChar()] ?: 0.0 }

        return Pair(text, score / text.length)
    }

    fun xor(input: ByteArray, char: Char): String {
        return String(input.map { it.toInt().xor(char.code).toByte() }.toByteArray())
    }

    fun String.fromHex(): ByteArray {
        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }

