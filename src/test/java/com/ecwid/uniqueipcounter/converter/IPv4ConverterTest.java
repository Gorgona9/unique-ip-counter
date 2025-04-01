package com.ecwid.uniqueipcounter.converter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class IPv4ConverterTest {

    private final IPv4Converter converter = new IPv4Converter();

    @ParameterizedTest
    @CsvSource({
            "0.0.0.8, 8",
            "0.0.0.255, 255",
            "0.0.1.0, 256",
            "0.0.1.44, 300",
            "0.1.0.0, 65536",
            "0.1.1.0, 65792",
            "128.1.1.240, -2147417616",
            "127.1.1.240, 2130772464",
            "0.0.0.1, 1",
            "0.0.0.0, 0",
            "255.255.255.255, -1",
            "127.255.255.255, " + Integer.MAX_VALUE,
            "128.0.0.0, " + Integer.MIN_VALUE
    })
    void shouldConvertStringRepresentationOfIPv4ToInt(String ip, int expected) {
        int actual = converter.applyAsInt(ip);
        assertEquals(expected, actual, "Mismatch in IP conversion");
    }
}
