# Unique IPv4 Address Counter

## Overview
This Java application efficiently counts the number of unique IPv4 addresses in a given file. 
The file should contain one IP address per line. The program leverages memory-efficient data structures to handle large datasets with minimal resource consumption.

## Features
- Supports large datasets with optimized memory usage.
- Implements multiple data structures for storing unique IPs.
- Utilizes Java Streams for efficient file processing.
- Provides detailed logging for execution tracking.

## Requirements
- Java 21
- Maven for building the project
- JUnit 5 for tests

## Installation
Clone the repository and navigate to the project root:
```sh
  git clone https://github.com/Gorgona9/unique-ip-counter.git
  cd unique-ip-counter
```

## Building the Project
Use Maven to compile and package the application:
```sh
  mvn clean package
```
This will generate a JAR file inside the `target/` directory.

## Usage
Run the application from the command line:
```sh
  java -jar target/unique-ip-counter.jar <file-path>
```
Example:
```sh
  java -jar target/unique-ip-counter.jar sample_ips.txt
```
The output will display the number of unique IP addresses 
in the specified file and the time of execution.

## Project Structure
```

unique-ip-counter/
│── src/
│   ├── main/
│   │   └── java/com/ecwid/uniqueipcounter
│   │       ├── Main.java
│   │       └── container
│   │           ├── converter
│   │           │   └── IPv4Converter.java            # Converts IPv4 addresses from string to integer
│   │           └── container
│   │               ├── IntContainer.java             # Interface for IP storage containers
│   │               ├── BitSetContainer.java          # Implementation using dynamic BitSet arrays
│   │               ├── DualBitSetContainer.java      # Optimized implementation using two BitSets
│   │               └── LongArrayContainer.java       # Optimized bit-level storage using a long array
│   └── test/
│       ├── java/com/ecwid/uniqueipcounter
│       │   ├── AcceptanceTest.java                   # Tests for application logic, like argument handling and file reading
│       │   └── container
│       │       ├── converter
│       │       │   ├── IPv4ConverterTest.java        # Tests for IPv4 address conversion
│       │       └── container
│       │           ├── IntContainer.java             # Interface for IP storage containers
│       │           ├── BitSetContainerTest.java      # Tests for BitSet-based container
│       │           ├── DualBitSetContainerTest.java  # Tests for DualBitSet-based container
│       │           └── LongArrayContainerTest.java   # Tests for LongArray-based container
│       └── resources
│           ├── hundred.txt                           # Test file with 100 IP addresses
│           └── ips.txt                               # Test file with various IP addresses
│── pom.xml
└──README.md
```

## Implementation Details
### **IPv4Converter**
Converts an IPv4 address from string format (e.g., `192.168.1.1`) into a 32-bit integer for efficient storage and retrieval.

### **IntContainer Interface**
Defines the core operations for any container storing unique integers:
- `add(int number)`: Adds an integer to the collection.
- `countUnique()`: Returns the number of unique integers stored.
- `addAll(IntContainer other)`: Merges another container (default unsupported).

### **BitSetContainer**
A dynamically allocated array of BitSets to store unique IPs. Uses a bitwise approach to optimize memory usage when IPs are concentrated in specific subnets.

### **DualBitSetContainer**
An optimized version of BitSetContainer that uses two BitSets, separately handling positive and negative numbers to improve performance.

### **LongArrayContainer**
A low-memory alternative that uses a long array as a bitset, efficiently mapping integer representations of IP addresses to bit positions.

## Performance Considerations
- **Memory Usage:** Containers are designed to minimize memory consumption by leveraging bit-level storage.
- **Processing Speed:** The application uses Java Streams for parallelized file reading and processing.
- **Scalability:** Supports large files containing millions of IP addresses.

## Tests
The project includes unit tests, integration tests, and functional tests to validate correctness and performance.
To run tests:
```sh
  mvn test
```

Here’s a more detailed and refined version of your text:

---

## **The Solution**

To efficiently process a text file containing IPv4 addresses, we utilize the `Files.lines()` method, which returns a stream of strings, each representing an IPv4 address in textual format. Since working with string-based representations is inefficient for large-scale processing, each IPv4 address is converted into a 32-bit integer (`int`), occupying exactly 4 bytes.

To efficiently track unique addresses, we use a specialized container where each bit represents the presence of a specific IPv4 address. Given that an IPv4 address is a 32-bit integer, the total number of possible unique addresses is \( 2^{32} \), requiring a bit storage of the same size—equivalent to **512 MB**. This memory footprint remains constant, regardless of the number of actual IPv4 addresses present in the file.

The project implements a single highly optimized converter, along with multiple container implementations tailored for different memory and performance requirements:

- **`LongArrayContainer`**: This implementation preallocates the entire 512 MB memory upfront, making it the most efficient choice for handling very large datasets with widespread IP distributions.
- **`BitSetContainer`**: Unlike `LongArrayContainer`, this container dynamically allocates memory only for active IP address segments. If the dataset contains IPs from limited address ranges, this approach significantly reduces memory consumption.
- **`DualBitSetContainer`**: This is a specialized, slightly optimized variant of `BitSetContainer`, designed to offer better performance under specific conditions.

The following code snippet demonstrates the core logic of our approach:

```java
private static long countUnique(Stream<String> ipAddresses) {
    return ipAddresses
                .mapToInt(new IPv4Converter())  // Convert textual IPs to integers
                .collect(LongArrayContainer::new, IntContainer::add, IntContainer::addAll)
                .countUnique();  // Count unique addresses
}
```  

Here, `IntContainer` is the common interface for all container implementations, and `LongArrayContainer` is one specific implementation.

The `IPv4Converter` class is designed for maximum efficiency and assumes that all input IP addresses are valid. It performs **no validation** and will return an arbitrary integer if given an incorrectly formatted IP address. If input validation is necessary, one can either apply a filtering step to the string stream before conversion or implement a modified version of `IPv4Converter` that includes validation logic.

## License
This project is licensed under the MIT License.

