package com.ecwid.uniqueipcounter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AcceptanceTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream stubOut = new ByteArrayOutputStream();

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(stubOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void shouldCheckNumberOfArguments() {
        // No arguments case
        Main.main(new String[]{});
        assertTrue(stubOut.toString().contains("specify one argument"),
                "Expected error message for missing arguments");

        stubOut.reset();

        // More than one argument case
        Main.main(new String[]{"src/test/resources/one.txt", "src/test/resources/ips.txt"});
        assertTrue(stubOut.toString().contains("specify one argument"),
                "Expected error message for too many arguments");
    }

    @ParameterizedTest
    @MethodSource("provideIpTestCases")
    void shouldCountUniqueIpv4Addresses(String content, int expected) throws IOException {
        Path sourcePath = tempDir.resolve("ips.txt");
        Files.writeString(sourcePath, content);

        Main.main(new String[]{sourcePath.toString()});

        int actual = Integer.parseInt(stubOut.toString().trim());
        assertEquals(expected, actual, "Mismatch in unique IP count");

        Files.deleteIfExists(sourcePath);
    }

    private static List<Arguments> provideIpTestCases() {
        return List.of(
                Arguments.of("0.0.0.0", 1),
                Arguments.of("1.2.3.4\n4.5.6.7\n9.9.8.8", 3),
                Arguments.of("""
                         127.7.28.11
                         1.2.3.4
                         43.234.255.245
                         43.234.255.245
                         1.2.3.4
                         0.0.0.0
                         255.255.255.255
                         127.0.0.1
                         """, 6),
                Arguments.of("", 0)
        );
    }
}
