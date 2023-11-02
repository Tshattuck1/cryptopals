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
        val score = text.filter { it in ('A'..'z') || it == ' ' }
            .groupingBy { it.lowercaseChar() }
            .eachCount().toList()
            .sumOf { it.second }
        return Pair(text, score.toDouble().div(text.length.toDouble()))
    }

    fun xor(input: ByteArray, char: Char): String {
        return String(input.map { it.toInt().xor(char.code).toByte() }.toByteArray())
    }

    fun String.fromHex(): ByteArray {
        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }

