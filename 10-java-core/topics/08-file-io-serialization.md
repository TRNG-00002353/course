# File I/O and Serialization

## Table of Contents
- [Introduction](#introduction)
- [File Class](#file-class)
- [Byte Streams](#byte-streams)
- [Character Streams](#character-streams)
- [java.nio Package (NIO)](#javanio-package-nio)
- [Serialization](#serialization)
- [transient Modifier](#transient-modifier)
- [Externalizable Interface](#externalizable-interface)
- [Best Practices](#best-practices)
- [Summary](#summary)

---

## Introduction

Java provides comprehensive APIs for file operations and object serialization through the `java.io` and `java.nio` packages.

---

## File Class

The `File` class represents file and directory pathnames.

```java
import java.io.File;

public class FileBasics {
    public static void main(String[] args) {
        File file = new File("data.txt");
        File dir = new File("myFolder");

        // File information
        System.out.println("Exists: " + file.exists());
        System.out.println("Is File: " + file.isFile());
        System.out.println("Is Directory: " + file.isDirectory());
        System.out.println("Name: " + file.getName());
        System.out.println("Path: " + file.getPath());
        System.out.println("Absolute Path: " + file.getAbsolutePath());
        System.out.println("Size: " + file.length() + " bytes");
        System.out.println("Readable: " + file.canRead());
        System.out.println("Writable: " + file.canWrite());
    }
}
```

### File Operations

```java
import java.io.File;
import java.io.IOException;

public class FileOperations {
    public static void main(String[] args) throws IOException {
        // Create a new file
        File newFile = new File("newFile.txt");
        boolean created = newFile.createNewFile();
        System.out.println("File created: " + created);

        // Create directories
        File dir = new File("parent/child");
        dir.mkdirs();  // Creates parent directories too

        // List files in directory
        File folder = new File(".");
        String[] files = folder.list();
        for (String name : files) {
            System.out.println(name);
        }

        // List with filter
        File[] txtFiles = folder.listFiles((d, name) -> name.endsWith(".txt"));

        // Rename file
        File oldFile = new File("old.txt");
        File renamedFile = new File("new.txt");
        oldFile.renameTo(renamedFile);

        // Delete file
        newFile.delete();

        // Delete on exit (cleanup)
        File tempFile = new File("temp.txt");
        tempFile.deleteOnExit();
    }
}
```

---

## Byte Streams

Byte streams handle raw binary data (8-bit bytes). Use for images, audio, video, and binary files.

### FileInputStream and FileOutputStream

```java
import java.io.*;

public class ByteStreamExample {
    public static void main(String[] args) {
        // Writing bytes
        try (FileOutputStream fos = new FileOutputStream("output.bin")) {
            byte[] data = {65, 66, 67, 68, 69};  // A, B, C, D, E
            fos.write(data);
            fos.write(70);  // Write single byte
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading bytes
        try (FileInputStream fis = new FileInputStream("output.bin")) {
            int byteData;
            while ((byteData = fis.read()) != -1) {
                System.out.print((char) byteData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Copying Files with Byte Streams

```java
import java.io.*;

public class FileCopy {
    public static void copyFile(String source, String dest) {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("File copied successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### BufferedInputStream and BufferedOutputStream

Buffered streams improve performance by reducing disk I/O operations.

```java
import java.io.*;

public class BufferedByteStream {
    public static void copyWithBuffer(String source, String dest) {
        try (BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(source));
             BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(dest))) {

            byte[] buffer = new byte[8192];  // 8KB buffer
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Character Streams

Character streams handle text data (16-bit Unicode characters). Use for text files.

### FileReader and FileWriter

```java
import java.io.*;

public class CharacterStreamExample {
    public static void main(String[] args) {
        // Writing characters
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write("Hello, World!\n");
            writer.write("Java File I/O");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading characters
        try (FileReader reader = new FileReader("output.txt")) {
            int charData;
            while ((charData = reader.read()) != -1) {
                System.out.print((char) charData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### BufferedReader and BufferedWriter

Buffered character streams for efficient text processing.

```java
import java.io.*;

public class BufferedCharacterStream {
    public static void main(String[] args) {
        // Writing with BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("output.txt"))) {
            writer.write("Line 1");
            writer.newLine();  // Platform-independent line separator
            writer.write("Line 2");
            writer.newLine();
            writer.write("Line 3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading with BufferedReader
        try (BufferedReader reader = new BufferedReader(
                new FileReader("output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### PrintWriter

Convenient text output with formatting support.

```java
import java.io.*;

public class PrintWriterExample {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            writer.println("Hello, World!");
            writer.printf("Name: %s, Age: %d%n", "John", 25);
            writer.print("No newline");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Stream Hierarchy

```
                    InputStream                          OutputStream
                         │                                    │
         ┌───────────────┼───────────────┐      ┌─────────────┼─────────────┐
         │               │               │      │             │             │
  FileInputStream  ByteArray     Filter    FileOutput   ByteArray    Filter
                   InputStream   InputStream  Stream    OutputStream  OutputStream
                                     │                                    │
                              ┌──────┴──────┐                      ┌──────┴──────┐
                              │             │                      │             │
                         Buffered       Data                  Buffered       Data
                         InputStream   InputStream            OutputStream  OutputStream


                       Reader                                Writer
                         │                                      │
         ┌───────────────┼───────────────┐      ┌───────────────┼───────────────┐
         │               │               │      │               │               │
   FileReader     Buffered         InputStream  FileWriter  Buffered       OutputStreamWriter
                  Reader           Reader                   Writer
                                                               │
                                                          PrintWriter
```

---

## Byte vs Character Streams

| Aspect | Byte Streams | Character Streams |
|--------|--------------|-------------------|
| Unit | 8-bit bytes | 16-bit Unicode characters |
| Base Classes | InputStream, OutputStream | Reader, Writer |
| Use Case | Binary files (images, audio) | Text files |
| Encoding | None (raw bytes) | Handles character encoding |
| Examples | FileInputStream, FileOutputStream | FileReader, FileWriter |

---

## java.nio Package (NIO)

New I/O (NIO) provides improved file operations with better performance.

### Files and Paths

```java
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class NIOExample {
    public static void main(String[] args) throws IOException {
        // Creating Path
        Path path = Paths.get("data.txt");
        Path absolutePath = Paths.get("/home/user/data.txt");

        // Path information
        System.out.println("File Name: " + path.getFileName());
        System.out.println("Parent: " + path.getParent());
        System.out.println("Root: " + path.getRoot());
        System.out.println("Absolute: " + path.toAbsolutePath());

        // Check file attributes
        System.out.println("Exists: " + Files.exists(path));
        System.out.println("Is Regular File: " + Files.isRegularFile(path));
        System.out.println("Is Directory: " + Files.isDirectory(path));
        System.out.println("Is Readable: " + Files.isReadable(path));
        System.out.println("Is Writable: " + Files.isWritable(path));
        System.out.println("Size: " + Files.size(path));
    }
}
```

### Reading and Writing with Files Class

```java
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class NIOReadWrite {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("data.txt");

        // Write string to file
        Files.writeString(path, "Hello, NIO!");

        // Write lines to file
        List<String> lines = List.of("Line 1", "Line 2", "Line 3");
        Files.write(path, lines);

        // Append to file
        Files.writeString(path, "\nAppended text", StandardOpenOption.APPEND);

        // Read entire file as string
        String content = Files.readString(path);
        System.out.println(content);

        // Read all lines
        List<String> allLines = Files.readAllLines(path);
        allLines.forEach(System.out::println);

        // Read all bytes
        byte[] bytes = Files.readAllBytes(path);
    }
}
```

### File Operations with NIO

```java
import java.nio.file.*;
import java.io.IOException;

public class NIOFileOperations {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("source.txt");
        Path target = Paths.get("target.txt");
        Path dir = Paths.get("newDir");

        // Create file
        Files.createFile(source);

        // Create directory
        Files.createDirectory(dir);

        // Create directories (including parents)
        Files.createDirectories(Paths.get("parent/child/grandchild"));

        // Copy file
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        // Move file
        Files.move(source, Paths.get("moved.txt"), StandardCopyOption.REPLACE_EXISTING);

        // Delete file
        Files.delete(target);

        // Delete if exists (no exception if missing)
        Files.deleteIfExists(target);
    }
}
```

### Walking Directory Tree

```java
import java.nio.file.*;
import java.io.IOException;

public class DirectoryWalk {
    public static void main(String[] args) throws IOException {
        Path start = Paths.get(".");

        // Walk directory tree
        Files.walk(start)
            .filter(Files::isRegularFile)
            .forEach(System.out::println);

        // Find files matching pattern
        Files.walk(start)
            .filter(p -> p.toString().endsWith(".java"))
            .forEach(System.out::println);

        // Walk with depth limit
        Files.walk(start, 2)  // Max depth of 2
            .forEach(System.out::println);

        // List directory contents (non-recursive)
        Files.list(start)
            .forEach(System.out::println);
    }
}
```

---

## Serialization

Serialization converts objects to byte streams for storage or transmission. Deserialization reconstructs objects from byte streams.

### Serializable Interface

```java
import java.io.*;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}
```

### Serializing Objects

```java
import java.io.*;

public class SerializationExample {
    public static void main(String[] args) {
        Person person = new Person("John", 30, "john@example.com");

        // Serialize (write object to file)
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("person.ser"))) {
            oos.writeObject(person);
            System.out.println("Object serialized successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize (read object from file)
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("person.ser"))) {
            Person loadedPerson = (Person) ois.readObject();
            System.out.println("Deserialized: " + loadedPerson);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

### serialVersionUID

The `serialVersionUID` ensures version compatibility during deserialization.

```java
public class User implements Serializable {
    // Explicitly declare for version control
    private static final long serialVersionUID = 1L;

    private String username;
    private String email;

    // If serialVersionUID doesn't match during deserialization,
    // InvalidClassException is thrown
}
```

**Why use serialVersionUID:**
- JVM generates one if not declared (based on class structure)
- Class changes can cause generated UID to change
- Explicit UID allows controlled versioning
- Prevents `InvalidClassException` on minor changes

---

## transient Modifier

The `transient` keyword excludes fields from serialization.

```java
import java.io.*;

public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private transient String password;      // Not serialized - sensitive
    private transient Connection dbConn;    // Not serialized - non-serializable
    private transient int loginCount;       // Not serialized - computed value

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.loginCount = 0;
    }

    // After deserialization, transient fields have default values:
    // - null for objects
    // - 0 for numbers
    // - false for booleans
}
```

### Reinitializing transient Fields

```java
import java.io.*;

public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private transient String sessionId;

    // Called during deserialization
    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();  // Deserialize non-transient fields
        this.sessionId = generateNewSessionId();  // Reinitialize transient
    }

    private String generateNewSessionId() {
        return "SESSION-" + System.currentTimeMillis();
    }
}
```

**Use transient for:**
- Sensitive data (passwords, security keys)
- Non-serializable fields (streams, connections, threads)
- Cached or computed values
- Large data that can be reconstructed

---

## Serializing Collections

```java
import java.io.*;
import java.util.*;

public class CollectionSerialization {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 25, "alice@example.com"));
        people.add(new Person("Bob", 30, "bob@example.com"));

        // Serialize list
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("people.ser"))) {
            oos.writeObject(people);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize list
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("people.ser"))) {
            @SuppressWarnings("unchecked")
            List<Person> loadedPeople = (List<Person>) ois.readObject();
            loadedPeople.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Externalizable Interface

`Externalizable` provides full control over serialization process.

```java
import java.io.*;

public class CustomPerson implements Externalizable {
    private String name;
    private int age;
    private String ssn;  // Sensitive - custom handling

    // Required: public no-arg constructor
    public CustomPerson() {}

    public CustomPerson(String name, int age, String ssn) {
        this.name = name;
        this.age = age;
        this.ssn = ssn;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        // Encrypt SSN before writing
        out.writeUTF(encrypt(ssn));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        name = in.readUTF();
        age = in.readInt();
        // Decrypt SSN after reading
        ssn = decrypt(in.readUTF());
    }

    private String encrypt(String data) {
        // Simple example - use proper encryption in production
        return new StringBuilder(data).reverse().toString();
    }

    private String decrypt(String data) {
        return new StringBuilder(data).reverse().toString();
    }
}
```

### Serializable vs Externalizable

| Aspect | Serializable | Externalizable |
|--------|--------------|----------------|
| Control | Automatic (JVM handles) | Full control (developer implements) |
| Performance | Slower (uses reflection) | Faster (direct read/write) |
| Default Constructor | Not required | Required (public no-arg) |
| transient | Supported | Not needed (you control what's written) |
| Ease of Use | Simple | Complex |
| Use Case | Most scenarios | Performance-critical or custom format |

---

## Best Practices

### File I/O Best Practices

1. **Always use try-with-resources** for automatic resource cleanup
2. **Use buffered streams** for better performance
3. **Handle exceptions properly** - don't swallow IOException
4. **Use NIO for modern applications** - better performance and features
5. **Specify character encoding** when reading/writing text

```java
// Specify encoding explicitly
try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
            new FileInputStream("file.txt"),
            StandardCharsets.UTF_8))) {
    // Read file
}

// Or with NIO
Files.readString(path, StandardCharsets.UTF_8);
```

### Serialization Best Practices

1. **Always declare serialVersionUID** explicitly
2. **Use transient for sensitive data** (passwords, keys)
3. **Validate deserialized data** for security
4. **Consider alternatives** (JSON, XML) for cross-platform compatibility
5. **Implement readObject validation** for security

```java
private void readObject(ObjectInputStream ois)
        throws IOException, ClassNotFoundException {
    ois.defaultReadObject();

    // Validate deserialized state
    if (age < 0 || age > 150) {
        throw new InvalidObjectException("Invalid age: " + age);
    }
}
```

---

## Common Exceptions

| Exception | Cause |
|-----------|-------|
| `FileNotFoundException` | File doesn't exist or can't be opened |
| `IOException` | General I/O error |
| `EOFException` | Unexpected end of file |
| `NotSerializableException` | Object doesn't implement Serializable |
| `InvalidClassException` | serialVersionUID mismatch |
| `StreamCorruptedException` | Corrupted serialization stream |
| `ClassNotFoundException` | Class not found during deserialization |

---

## Quick Reference

| Operation | Traditional I/O | NIO |
|-----------|----------------|-----|
| Read text file | BufferedReader + FileReader | Files.readString() |
| Write text file | BufferedWriter + FileWriter | Files.writeString() |
| Read binary file | FileInputStream | Files.readAllBytes() |
| Copy file | Stream loop | Files.copy() |
| List directory | File.listFiles() | Files.list() |
| Create directory | File.mkdir() | Files.createDirectory() |
| Delete file | File.delete() | Files.delete() |
| Check exists | File.exists() | Files.exists() |

---

## Summary

| Topic | Key Points |
|-------|------------|
| File Class | Represents file/directory paths, metadata operations |
| Byte Streams | Binary data, FileInputStream/FileOutputStream |
| Character Streams | Text data, FileReader/FileWriter, BufferedReader/BufferedWriter |
| NIO (java.nio) | Modern API, Files/Paths classes, better performance |
| Serialization | Object to bytes, implements Serializable, serialVersionUID |
| transient | Excludes fields from serialization |
| Externalizable | Full control over serialization process |

## Next Topic

Continue to [Multithreading](./09-multithreading.md) to learn about concurrent programming in Java.
