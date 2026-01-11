# Java Networking

## Table of Contents

### Required Topics
- [Introduction](#introduction)
- [Networking Fundamentals](#networking-fundamentals)
- [Socket Programming](#socket-programming)
- [java.net.Socket (Client Socket)](#javanet-socket-client-socket)
- [java.net.ServerSocket](#javanetserversocket)
- [Implementing Client-Server Communication](#implementing-client-server-communication)
- [Summary](#summary)

### Optional / Additional Reference
- [Multi-Client Server](#multi-client-server-optional)
- [Real-World Examples](#real-world-examples-optional)
- [UDP Communication](#udp-communication-optional)
- [Best Practices](#best-practices-optional)

---

## Introduction

Java Networking enables communication between programs running on different machines connected via a network. The `java.net` package provides classes for implementing network applications using TCP (connection-oriented) and UDP (connectionless) protocols.

**Key Concepts:**
- **Client**: A program that initiates communication
- **Server**: A program that waits for and responds to client requests
- **Socket**: An endpoint for communication between two machines
- **Port**: A numbered gateway for network communication (0-65535)
- **Protocol**: Rules governing data transmission (TCP, UDP, HTTP)

---

## Networking Fundamentals

### OSI Model Layers Relevant to Java

```
┌─────────────────────────────────────────┐
│  Application Layer (HTTP, FTP, SMTP)   │  ← Java Application
├─────────────────────────────────────────┤
│  Transport Layer (TCP, UDP)            │  ← Socket, ServerSocket, DatagramSocket
├─────────────────────────────────────────┤
│  Network Layer (IP)                    │  ← InetAddress
├─────────────────────────────────────────┤
│  Data Link & Physical Layers           │  ← Hardware (NIC, cables)
└─────────────────────────────────────────┘
```

### TCP vs UDP

| Feature | TCP | UDP |
|---------|-----|-----|
| Connection | Connection-oriented | Connectionless |
| Reliability | Guaranteed delivery | No guarantee |
| Order | Maintains order | No order guarantee |
| Speed | Slower (overhead) | Faster |
| Use Case | File transfer, web browsing | Streaming, gaming |
| Java Classes | Socket, ServerSocket | DatagramSocket, DatagramPacket |

### Port Numbers

```
┌──────────────────────────────────────────────────┐
│  Port Range        │  Description               │
├──────────────────────────────────────────────────┤
│  0 - 1023          │  Well-known (HTTP:80, FTP:21) │
│  1024 - 49151      │  Registered ports          │
│  49152 - 65535     │  Dynamic/private ports     │
└──────────────────────────────────────────────────┘
```

---

## Socket Programming

Socket programming enables bidirectional communication between client and server applications.

### How Socket Communication Works

```
┌────────────────────┐              ┌────────────────────┐
│      CLIENT        │              │       SERVER       │
├────────────────────┤              ├────────────────────┤
│                    │              │                    │
│  1. Create Socket  │              │  1. Create         │
│     with server    │              │     ServerSocket   │
│     IP and port    │              │     on a port      │
│                    │              │                    │
│  2. Connect to     │ ──────────► │  2. accept()       │
│     server         │   Request    │     waits for      │
│                    │              │     connection     │
│                    │              │                    │
│  3. Get I/O        │ ◄──────────►│  3. Get I/O        │
│     streams        │   Data       │     streams        │
│                    │              │                    │
│  4. Read/Write     │              │  4. Read/Write     │
│     data           │              │     data           │
│                    │              │                    │
│  5. Close socket   │              │  5. Close socket   │
└────────────────────┘              └────────────────────┘
```

---

## java.net.Socket (Client Socket)

The `Socket` class represents a client-side connection to a server.

### Basic Client Example

```java
import java.net.Socket;
import java.io.*;

public class SimpleClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port)) {

            System.out.println("Connected to server: " + socket.getInetAddress());
            System.out.println("Local port: " + socket.getLocalPort());
            System.out.println("Remote port: " + socket.getPort());

            // Get output stream to send data to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Get input stream to receive data from server
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            // Send message to server
            out.println("Hello, Server!");

            // Receive response from server
            String response = in.readLine();
            System.out.println("Server says: " + response);

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
```

### Socket Methods

```java
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.IOException;

public class SocketMethodsDemo {
    public static void main(String[] args) {
        try {
            // Create unconnected socket
            Socket socket = new Socket();

            // Set socket options before connecting
            socket.setSoTimeout(10000);           // Read timeout: 10 seconds
            socket.setKeepAlive(true);            // Enable keep-alive
            socket.setTcpNoDelay(true);           // Disable Nagle's algorithm
            socket.setReceiveBufferSize(8192);    // Receive buffer size
            socket.setSendBufferSize(8192);       // Send buffer size

            // Connect with timeout
            socket.connect(new InetSocketAddress("localhost", 5000), 5000);

            // Socket information
            System.out.println("Connected: " + socket.isConnected());
            System.out.println("Bound: " + socket.isBound());
            System.out.println("Closed: " + socket.isClosed());
            System.out.println("Input Shutdown: " + socket.isInputShutdown());
            System.out.println("Output Shutdown: " + socket.isOutputShutdown());

            // Get streams
            var inputStream = socket.getInputStream();
            var outputStream = socket.getOutputStream();

            // Shutdown one direction (half-close)
            socket.shutdownOutput();  // No more sending

            // Close socket
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## java.net.ServerSocket

The `ServerSocket` class listens for incoming client connections on a specified port.

### Basic Server Example

```java
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class SimpleServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server started on port " + port);
            System.out.println("Waiting for client connection...");

            // accept() blocks until a client connects
            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Get input stream to receive data from client
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

            // Get output stream to send data to client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read message from client
            String message = in.readLine();
            System.out.println("Client says: " + message);

            // Send response to client
            out.println("Hello, Client! Message received.");

            // Close client socket
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
```

### ServerSocket with Continuous Connection Handling

```java
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Echo Server started on port " + port);

            while (true) {
                System.out.println("\nWaiting for client...");

                try (Socket clientSocket = serverSocket.accept()) {

                    System.out.println("Client connected: " +
                        clientSocket.getInetAddress().getHostAddress());

                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                    String inputLine;

                    // Echo back everything the client sends
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Received: " + inputLine);

                        if (inputLine.equalsIgnoreCase("bye")) {
                            out.println("Goodbye!");
                            break;
                        }

                        out.println("ECHO: " + inputLine);
                    }

                    System.out.println("Client disconnected");

                } catch (IOException e) {
                    System.out.println("Client handling error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
```

### Interactive Echo Client

```java
import java.net.Socket;
import java.io.*;

public class EchoClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(
                 new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(
                 new InputStreamReader(System.in))) {

            System.out.println("Connected to Echo Server. Type messages (type 'bye' to exit):");

            String userMessage;

            while ((userMessage = userInput.readLine()) != null) {
                // Send to server
                out.println(userMessage);

                // Receive response
                String response = in.readLine();
                System.out.println("Server: " + response);

                if (userMessage.equalsIgnoreCase("bye")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
```

---

## Implementing Client-Server Communication

This section demonstrates a complete working example of socket programming with both client and server.

### Complete Example: Calculator Service

**CalculatorServer.java** - A server that performs arithmetic operations

```java
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class CalculatorServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Calculator Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                System.out.println("\nWaiting for client connection...");

                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " +
                    clientSocket.getInetAddress().getHostAddress());

                // Handle client request
                handleClient(clientSocket);
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true)
        ) {
            out.println("Welcome to Calculator Server!");
            out.println("Format: number1 operator number2 (e.g., 5 + 3)");
            out.println("Type 'quit' to disconnect");

            String input;
            while ((input = in.readLine()) != null) {
                System.out.println("Received: " + input);

                if (input.equalsIgnoreCase("quit")) {
                    out.println("Goodbye!");
                    break;
                }

                String result = calculate(input);
                out.println("Result: " + result);
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String calculate(String expression) {
        try {
            String[] parts = expression.trim().split("\\s+");
            if (parts.length != 3) {
                return "Invalid format. Use: number1 operator number2";
            }

            double num1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double num2 = Double.parseDouble(parts[2]);
            double result;

            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/":
                    if (num2 == 0) return "Error: Division by zero";
                    result = num1 / num2;
                    break;
                default: return "Unknown operator: " + operator;
            }

            return String.valueOf(result);

        } catch (NumberFormatException e) {
            return "Error: Invalid number format";
        }
    }
}
```

**CalculatorClient.java** - A client that sends calculation requests

```java
import java.net.Socket;
import java.io.*;

public class CalculatorClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Connecting to Calculator Server...");

        try (
            Socket socket = new Socket(SERVER, PORT);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(
                new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server!\n");

            // Read welcome messages from server
            String serverMessage;
            for (int i = 0; i < 3; i++) {
                serverMessage = in.readLine();
                System.out.println("Server: " + serverMessage);
            }

            System.out.println("\nEnter calculations:");

            // Read user input and send to server
            String userLine;
            while ((userLine = userInput.readLine()) != null) {
                out.println(userLine);

                String response = in.readLine();
                System.out.println("Server: " + response);

                if (userLine.equalsIgnoreCase("quit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }

        System.out.println("Disconnected from server.");
    }
}
```

### Running the Example

**Step 1: Compile both files**
```bash
javac CalculatorServer.java CalculatorClient.java
```

**Step 2: Start the server (in one terminal)**
```bash
java CalculatorServer
```

**Step 3: Start the client (in another terminal)**
```bash
java CalculatorClient
```

**Sample Output:**

Server Terminal:
```
Calculator Server starting...
Server listening on port 5000

Waiting for client connection...
Client connected: 127.0.0.1
Received: 10 + 5
Received: 20 * 3
Received: quit
Client disconnected
```

Client Terminal:
```
Connecting to Calculator Server...
Connected to server!

Server: Welcome to Calculator Server!
Server: Format: number1 operator number2 (e.g., 5 + 3)
Server: Type 'quit' to disconnect

Enter calculations:
10 + 5
Server: Result: 15.0
20 * 3
Server: Result: 60.0
quit
Server: Goodbye!
Disconnected from server.
```

### Key Takeaways

1. **ServerSocket** listens on a port and waits for connections using `accept()`
2. **Socket** represents the connection - both client and server get one
3. **I/O Streams** are used to send and receive data through the socket
4. **Always close resources** using try-with-resources
5. **Server loop** - A real server keeps accepting new clients after one disconnects

---

# Additional Reference (Optional)

> The following sections are optional and provided for additional learning. Focus on the required topics above first.

---

## Multi-Client Server (Optional)

A production server must handle multiple clients simultaneously using threads.

### Multi-Threaded Server

```java
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class MultiClientServer {
    private static int clientCounter = 0;

    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Multi-Client Server started on port " + port);

            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                clientCounter++;

                System.out.println("Client #" + clientCounter + " connected: " +
                    clientSocket.getInetAddress().getHostAddress());

                // Handle client in separate thread
                ClientHandler handler = new ClientHandler(clientSocket, clientCounter);
                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private int clientId;

    public ClientHandler(Socket socket, int id) {
        this.clientSocket = socket;
        this.clientId = id;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true)) {

            out.println("Welcome! You are Client #" + clientId);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client #" + clientId + ": " + inputLine);

                if (inputLine.equalsIgnoreCase("bye")) {
                    out.println("Goodbye, Client #" + clientId + "!");
                    break;
                }

                out.println("ECHO: " + inputLine);
            }

        } catch (IOException e) {
            System.out.println("Error handling client #" + clientId + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client #" + clientId + " disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### Thread Pool Server (Production-Ready)

```java
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {
    private static final int PORT = 5000;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Thread Pool Server started on port " + PORT);
            System.out.println("Max concurrent clients: " + THREAD_POOL_SIZE);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client: " + clientSocket.getInetAddress());

                // Submit client handling task to thread pool
                threadPool.submit(new ClientTask(clientSocket));
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }
}

class ClientTask implements Runnable {
    private Socket socket;

    public ClientTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String clientAddress = socket.getInetAddress().getHostAddress();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Connected to Thread Pool Server");

            String line;
            while ((line = in.readLine()) != null) {
                if ("quit".equalsIgnoreCase(line)) {
                    out.println("Goodbye!");
                    break;
                }
                out.println("Processed: " + line.toUpperCase());
            }

        } catch (IOException e) {
            System.out.println("Error with client " + clientAddress + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client " + clientAddress + " disconnected");
        }
    }
}
```

---

## Real-World Examples (Optional)

### Example 1: Simple Chat Application

**ChatServer.java**
```java
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 5000;
    private static Set<PrintWriter> clientWriters = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Chat Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new Thread(new ChatClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast message to all connected clients
    public static void broadcast(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    public static void addClient(PrintWriter writer) {
        clientWriters.add(writer);
    }

    public static void removeClient(PrintWriter writer) {
        clientWriters.remove(writer);
    }
}

class ChatClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ChatClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // First message is the username
            out.println("Enter your username:");
            username = in.readLine();

            ChatServer.addClient(out);
            ChatServer.broadcast("*** " + username + " has joined the chat ***");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }
                ChatServer.broadcast(username + ": " + message);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (username != null) {
                ChatServer.broadcast("*** " + username + " has left the chat ***");
            }
            ChatServer.removeClient(out);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

**ChatClient.java**
```java
import java.net.*;
import java.io.*;

public class ChatClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT);
             BufferedReader in = new BufferedReader(
                 new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(
                 new InputStreamReader(System.in))) {

            // Thread to receive messages from server
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server");
                }
            });
            receiveThread.start();

            // Main thread sends messages
            String userMessage;
            while ((userMessage = userInput.readLine()) != null) {
                out.println(userMessage);
                if (userMessage.equalsIgnoreCase("/quit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
```

### Example 2: File Transfer Application

**FileServer.java**
```java
import java.net.*;
import java.io.*;

public class FileServer {
    private static final int PORT = 6000;
    private static final String FILE_DIRECTORY = "./server_files/";

    public static void main(String[] args) {
        // Create directory if not exists
        new File(FILE_DIRECTORY).mkdirs();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("File Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            // Read command: UPLOAD or DOWNLOAD
            String command = in.readUTF();
            String fileName = in.readUTF();

            if ("UPLOAD".equals(command)) {
                receiveFile(in, out, fileName);
            } else if ("DOWNLOAD".equals(command)) {
                sendFile(in, out, fileName);
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void receiveFile(DataInputStream in, DataOutputStream out,
                                    String fileName) throws IOException {
        long fileSize = in.readLong();
        File file = new File(FILE_DIRECTORY + fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            long remaining = fileSize;

            while (remaining > 0) {
                int bytesToRead = (int) Math.min(buffer.length, remaining);
                int bytesRead = in.read(buffer, 0, bytesToRead);
                fos.write(buffer, 0, bytesRead);
                remaining -= bytesRead;
            }
        }

        out.writeUTF("SUCCESS");
        System.out.println("Received file: " + fileName + " (" + fileSize + " bytes)");
    }

    private static void sendFile(DataInputStream in, DataOutputStream out,
                                 String fileName) throws IOException {
        File file = new File(FILE_DIRECTORY + fileName);

        if (!file.exists()) {
            out.writeUTF("ERROR: File not found");
            return;
        }

        out.writeUTF("SUCCESS");
        out.writeLong(file.length());

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        System.out.println("Sent file: " + fileName);
    }
}
```

**FileClient.java**
```java
import java.net.*;
import java.io.*;

public class FileClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 6000;

    public static void uploadFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (Socket socket = new Socket(SERVER, PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream());
             FileInputStream fis = new FileInputStream(file)) {

            // Send command and file info
            out.writeUTF("UPLOAD");
            out.writeUTF(file.getName());
            out.writeLong(file.length());

            // Send file content
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            // Wait for confirmation
            String response = in.readUTF();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            System.out.println("Upload error: " + e.getMessage());
        }
    }

    public static void downloadFile(String fileName, String savePath) {
        try (Socket socket = new Socket(SERVER, PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            // Send command
            out.writeUTF("DOWNLOAD");
            out.writeUTF(fileName);

            // Check response
            String response = in.readUTF();
            if (response.startsWith("ERROR")) {
                System.out.println(response);
                return;
            }

            // Receive file
            long fileSize = in.readLong();

            try (FileOutputStream fos = new FileOutputStream(savePath)) {
                byte[] buffer = new byte[4096];
                long remaining = fileSize;

                while (remaining > 0) {
                    int bytesToRead = (int) Math.min(buffer.length, remaining);
                    int bytesRead = in.read(buffer, 0, bytesToRead);
                    fos.write(buffer, 0, bytesRead);
                    remaining -= bytesRead;
                }
            }

            System.out.println("Downloaded: " + fileName + " (" + fileSize + " bytes)");

        } catch (IOException e) {
            System.out.println("Download error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Example usage
        uploadFile("./test.txt");
        downloadFile("test.txt", "./downloaded_test.txt");
    }
}
```

### Example 3: Simple HTTP Server

```java
import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleHttpServer {
    private static final int PORT = 8080;
    private static final String WEB_ROOT = "./www";

    public static void main(String[] args) {
        new File(WEB_ROOT).mkdirs();

        // Create sample index.html
        try {
            Files.writeString(Path.of(WEB_ROOT + "/index.html"),
                "<html><body><h1>Welcome to Java HTTP Server</h1></body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("HTTP Server started at http://localhost:" + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleRequest(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {

            // Read HTTP request
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }

            System.out.println("[" + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] " + requestLine);

            // Parse request
            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String path = parts[1];

            // Read headers (consume them)
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                // Headers can be processed here if needed
            }

            if ("GET".equals(method)) {
                serveFile(out, path);
            } else {
                sendResponse(out, 405, "Method Not Allowed", "Method not supported");
            }

        } catch (IOException e) {
            System.out.println("Error handling request: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void serveFile(OutputStream out, String path) throws IOException {
        if ("/".equals(path)) {
            path = "/index.html";
        }

        Path filePath = Path.of(WEB_ROOT + path);

        if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
            byte[] content = Files.readAllBytes(filePath);
            String contentType = getContentType(path);

            String header = "HTTP/1.1 200 OK\r\n" +
                           "Content-Type: " + contentType + "\r\n" +
                           "Content-Length: " + content.length + "\r\n" +
                           "Connection: close\r\n" +
                           "\r\n";

            out.write(header.getBytes());
            out.write(content);
        } else {
            sendResponse(out, 404, "Not Found", "File not found: " + path);
        }
    }

    private static void sendResponse(OutputStream out, int statusCode,
                                     String statusText, String body) throws IOException {
        String response = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                         "Content-Type: text/html\r\n" +
                         "Content-Length: " + body.length() + "\r\n" +
                         "Connection: close\r\n" +
                         "\r\n" +
                         body;

        out.write(response.getBytes());
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";
        return "text/plain";
    }
}
```

---

## UDP Communication (Optional)

UDP (User Datagram Protocol) is connectionless and provides faster but unreliable communication.

### DatagramSocket and DatagramPacket

**UDPServer.java**
```java
import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        int port = 9000;

        try (DatagramSocket socket = new DatagramSocket(port)) {

            System.out.println("UDP Server started on port " + port);

            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // Receive packet
                DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // Extract data
                String message = new String(receivePacket.getData(),
                    0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("Received from " + clientAddress + ":" +
                    clientPort + " - " + message);

                // Send response
                String response = "ECHO: " + message;
                byte[] sendBuffer = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, sendBuffer.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**UDPClient.java**
```java
import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 9000;

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader userInput = new BufferedReader(
                 new InputStreamReader(System.in))) {

            InetAddress serverAddr = InetAddress.getByName(serverAddress);
            socket.setSoTimeout(5000);  // 5 second timeout

            System.out.println("UDP Client started. Type messages:");

            String line;
            while ((line = userInput.readLine()) != null) {
                if ("quit".equalsIgnoreCase(line)) break;

                // Send packet
                byte[] sendBuffer = line.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, sendBuffer.length, serverAddr, serverPort);
                socket.send(sendPacket);

                // Receive response
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer, receiveBuffer.length);

                try {
                    socket.receive(receivePacket);
                    String response = new String(receivePacket.getData(),
                        0, receivePacket.getLength());
                    System.out.println("Server: " + response);
                } catch (SocketTimeoutException e) {
                    System.out.println("No response from server (timeout)");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### Multicast Communication

```java
import java.net.*;

public class MulticastReceiver {
    public static void main(String[] args) {
        String multicastGroup = "230.0.0.1";
        int port = 4446;

        try (MulticastSocket socket = new MulticastSocket(port)) {

            InetAddress group = InetAddress.getByName(multicastGroup);
            socket.joinGroup(group);

            System.out.println("Joined multicast group: " + multicastGroup);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MulticastSender {
    public static void main(String[] args) {
        String multicastGroup = "230.0.0.1";
        int port = 4446;

        try (DatagramSocket socket = new DatagramSocket()) {

            InetAddress group = InetAddress.getByName(multicastGroup);

            for (int i = 1; i <= 5; i++) {
                String message = "Multicast message #" + i;
                byte[] buffer = message.getBytes();

                DatagramPacket packet = new DatagramPacket(
                    buffer, buffer.length, group, port);
                socket.send(packet);

                System.out.println("Sent: " + message);
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## Best Practices (Optional)

### 1. Resource Management

```java
// ALWAYS use try-with-resources
try (ServerSocket server = new ServerSocket(port);
     Socket client = server.accept();
     BufferedReader in = new BufferedReader(
         new InputStreamReader(client.getInputStream()));
     PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

    // Handle communication
    // Resources automatically closed

} catch (IOException e) {
    // Handle exceptions
}
```

### 2. Set Timeouts

```java
// Prevent indefinite blocking
socket.setSoTimeout(30000);        // 30 second read timeout
socket.connect(address, 10000);    // 10 second connection timeout

serverSocket.setSoTimeout(60000);  // Accept timeout
```

### 3. Handle Exceptions Properly

```java
try {
    // Network operations
} catch (UnknownHostException e) {
    System.out.println("Invalid host: " + e.getMessage());
} catch (ConnectException e) {
    System.out.println("Connection refused: " + e.getMessage());
} catch (SocketTimeoutException e) {
    System.out.println("Connection timed out");
} catch (SocketException e) {
    System.out.println("Socket error: " + e.getMessage());
} catch (IOException e) {
    System.out.println("I/O error: " + e.getMessage());
}
```

### 4. Use Thread Pools for Servers

```java
// Use thread pool instead of creating threads per client
ExecutorService executor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors() * 2);

// Or for more control
ExecutorService executor = new ThreadPoolExecutor(
    10,                      // Core pool size
    100,                     // Maximum pool size
    60, TimeUnit.SECONDS,    // Keep alive time
    new LinkedBlockingQueue<>(1000)  // Work queue
);
```

### 5. Buffer Sizes

```java
// Appropriate buffer sizes
socket.setReceiveBufferSize(65536);  // 64KB
socket.setSendBufferSize(65536);

// For high-latency networks
socket.setTcpNoDelay(true);  // Disable Nagle's algorithm
```

### 6. Graceful Shutdown

```java
public class GracefulServer {
    private volatile boolean running = true;
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        // Shutdown hook for cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        while (running) {
            try {
                Socket client = serverSocket.accept();
                // Handle client
            } catch (SocketException e) {
                if (!running) break;  // Expected during shutdown
                throw e;
            }
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Common Exceptions (Optional)

| Exception | Cause |
|-----------|-------|
| `UnknownHostException` | DNS lookup failed, invalid hostname |
| `ConnectException` | Connection refused, server not running |
| `SocketTimeoutException` | Connection or read timeout exceeded |
| `SocketException` | Socket error (closed, reset, etc.) |
| `BindException` | Port already in use |
| `NoRouteToHostException` | Network unreachable |
| `PortUnreachableException` | UDP port not listening |

---

## Summary

### Required Concepts

| Topic | Key Points |
|-------|------------|
| Networking Fundamentals | Client-server model, TCP vs UDP, ports, protocols |
| Socket Programming | Bidirectional communication between client and server |
| java.net.Socket | Client-side TCP socket for connecting to servers |
| java.net.ServerSocket | Server-side socket that listens for connections using `accept()` |
| I/O Streams | `BufferedReader`/`PrintWriter` for communication through sockets |
| Resource Management | Always use try-with-resources for socket cleanup |

### Key Code Pattern

```java
// Server
try (ServerSocket server = new ServerSocket(PORT)) {
    Socket client = server.accept();  // Wait for connection
    // Use I/O streams to communicate
}

// Client
try (Socket socket = new Socket(SERVER, PORT)) {
    // Use I/O streams to communicate
}
```

## Next Topic

Continue to [JDBC](../../11-jdbc/README.md) to learn about database connectivity in Java.
