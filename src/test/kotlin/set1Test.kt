import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Set1Test {

    @Test
    fun challenge1() {
        val converted =
            challenge1("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d")
        assertEquals("SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t", converted)
    }

    @Test
    fun challenge2() {
        val converted = challenge2(
            "1c0111001f010100061a024b53535009181c",
            "686974207468652062756c6c277320657965"
        )
        assertEquals("746865206b696420646f6e277420706c6179", converted)
    }

    @Test
    fun challenge3Test() {
        val ciphertext = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
        val plain = challenge3(ciphertext)
        assertEquals("Cooking MC's like a pound of bacon", plain.maxBy { it.second }.first)
    }

    @Test
    fun challenge4Test() {
        val plaintext = challenge4("src/test/resources/challenge4.txt")
        assertEquals("Now that the party is jumping\n", plaintext.first)
    }
}
