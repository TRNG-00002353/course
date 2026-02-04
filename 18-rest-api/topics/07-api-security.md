# API Security with Spring Security, JWT, and OAuth2

## Overview

Securing REST APIs is critical for protecting data and ensuring only authorized users can access resources. This topic covers Spring Security fundamentals, JWT (JSON Web Token) authentication, and OAuth2 integration for building secure APIs.

---

## Why API Security Matters

REST APIs expose application data and functionality. Without proper security:

- **Data Breach**: Unauthorized access to sensitive information
- **Identity Theft**: Attackers impersonating legitimate users
- **Data Manipulation**: Unauthorized modifications to data
- **Denial of Service**: Overwhelming the API with requests
- **Compliance Violations**: GDPR, HIPAA, PCI-DSS requirements

### Security Layers

```
┌─────────────────────────────────────────────────────┐
│                    Transport Layer                   │
│                   (HTTPS/TLS)                        │
├─────────────────────────────────────────────────────┤
│                  Authentication                      │
│            (Who are you? - JWT/OAuth2)              │
├─────────────────────────────────────────────────────┤
│                  Authorization                       │
│         (What can you access? - Roles/Permissions)  │
├─────────────────────────────────────────────────────┤
│                   Input Validation                   │
│              (Is the data safe?)                    │
├─────────────────────────────────────────────────────┤
│                    Rate Limiting                     │
│              (Too many requests?)                   │
└─────────────────────────────────────────────────────┘
```

---

## Spring Security Fundamentals

### What is Spring Security?

Spring Security is a powerful framework that provides:

- **Authentication**: Verifying user identity
- **Authorization**: Controlling access to resources
- **Protection**: Against common attacks (CSRF, XSS, etc.)
- **Integration**: With various authentication mechanisms

### Adding Spring Security

**Maven Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**Gradle:**
```groovy
implementation 'org.springframework.boot:spring-boot-starter-security'
```

### Default Behavior

When you add Spring Security without configuration:

1. **All endpoints are secured** - require authentication
2. **Form-based login** is enabled at `/login`
3. **HTTP Basic authentication** is enabled
4. **Default user** created with username "user" and generated password (in console)
5. **CSRF protection** enabled for non-GET requests

### Security Filter Chain

Spring Security uses a filter chain to process requests:

```
HTTP Request
    │
    ▼
┌─────────────────────────────────────────┐
│         Security Filter Chain            │
├─────────────────────────────────────────┤
│  1. CORS Filter                         │
│  2. CSRF Filter                         │
│  3. Authentication Filter               │
│  4. Authorization Filter                │
│  5. Exception Translation Filter        │
└─────────────────────────────────────────┘
    │
    ▼
Controller (if authorized)
```

### Basic Security Configuration

```java
package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable for REST APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
```

---

## Authentication vs Authorization

### Authentication (AuthN)
**"Who are you?"**

Verifying the identity of a user or system.

**Methods:**
- Username/Password
- JWT Tokens
- OAuth2/OpenID Connect
- API Keys
- Certificates (mTLS)

### Authorization (AuthZ)
**"What can you do?"**

Determining what actions an authenticated user can perform.

**Methods:**
- Role-Based Access Control (RBAC)
- Permission-Based Access Control
- Attribute-Based Access Control (ABAC)

```java
// Authentication - verify identity
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
String username = auth.getName(); // Who is this user?

// Authorization - check permissions
boolean isAdmin = auth.getAuthorities().stream()
    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
```

---

## JWT (JSON Web Token) Authentication

### What is JWT?

JWT is a compact, URL-safe token format for securely transmitting information between parties. It's commonly used for stateless authentication in REST APIs.

### JWT Structure

A JWT consists of three parts separated by dots:

```
xxxxx.yyyyy.zzzzz
  │      │      │
  │      │      └── Signature
  │      └── Payload
  └── Header
```

**Example JWT:**
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

### JWT Components

**1. Header:**
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**2. Payload (Claims):**
```json
{
  "sub": "1234567890",      // Subject (user ID)
  "name": "John Doe",        // Custom claim
  "email": "john@example.com",
  "roles": ["USER", "ADMIN"],
  "iat": 1516239022,         // Issued at
  "exp": 1516242622          // Expiration
}
```

**3. Signature:**
```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

### JWT Authentication Flow

```
┌────────┐                              ┌────────┐
│ Client │                              │ Server │
└───┬────┘                              └───┬────┘
    │                                       │
    │  1. POST /api/auth/login              │
    │     {username, password}              │
    │ ─────────────────────────────────────►│
    │                                       │
    │  2. Validate credentials              │
    │     Generate JWT                      │
    │                                       │
    │  3. Return JWT token                  │
    │ ◄─────────────────────────────────────│
    │                                       │
    │  4. GET /api/users                    │
    │     Authorization: Bearer <token>     │
    │ ─────────────────────────────────────►│
    │                                       │
    │  5. Validate JWT                      │
    │     Extract user info                 │
    │     Check authorization               │
    │                                       │
    │  6. Return protected resource         │
    │ ◄─────────────────────────────────────│
    │                                       │
```

### JWT Dependencies

```xml
<!-- JWT Library -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

### JWT Service Implementation

```java
package com.example.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration; // in milliseconds

    // Generate token for user
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generate token with extra claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Get signing key
    private SecretKey getSigningKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

### JWT Authentication Filter

```java
package com.example.security.filter;

import com.example.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Get Authorization header
        final String authHeader = request.getHeader("Authorization");

        // Check if header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token (remove "Bearer " prefix)
        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        // If username extracted and user not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validate token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Set request details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

### Security Configuration with JWT

```java
package com.example.security.config;

import com.example.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // Enable @PreAuthorize, @Secured, etc.
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                         UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for stateless REST API
            .csrf(csrf -> csrf.disable())

            // Configure endpoint authorization
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Admin only endpoints
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // User endpoints
                .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")

                // All other endpoints require authentication
                .anyRequest().authenticated()
            )

            // Stateless session (no session cookies)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Add authentication provider
            .authenticationProvider(authenticationProvider())

            // Add JWT filter before UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
```

### Authentication Controller

```java
package com.example.security.controller;

import com.example.security.dto.AuthRequest;
import com.example.security.dto.AuthResponse;
import com.example.security.dto.RegisterRequest;
import com.example.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.refreshToken(token));
    }
}
```

### DTOs for Authentication

```java
// AuthRequest.java
package com.example.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    String password
) {}

// RegisterRequest.java
package com.example.security.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "Password must contain uppercase, lowercase, and number"
    )
    String password
) {}

// AuthResponse.java
package com.example.security.dto;

public record AuthResponse(
    String token,
    String type,
    long expiresIn
) {
    public AuthResponse(String token, long expiresIn) {
        this(token, "Bearer", expiresIn);
    }
}
```

### Authentication Service

```java
package com.example.security.service;

import com.example.security.dto.AuthRequest;
import com.example.security.dto.AuthResponse;
import com.example.security.dto.RegisterRequest;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      JwtService jwtService,
                      AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);

        // Generate token
        String token = jwtService.generateToken(user);

        return new AuthResponse(token, jwtService.getExpirationTime());
    }

    public AuthResponse authenticate(AuthRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        // Get user
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate token
        String token = jwtService.generateToken(user);

        return new AuthResponse(token, jwtService.getExpirationTime());
    }

    public AuthResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid token");
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!jwtService.isTokenValid(token, user)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        String newToken = jwtService.generateToken(user);
        return new AuthResponse(newToken, jwtService.getExpirationTime());
    }
}
```

---

## Method-Level Security

### Using Security Annotations

Spring Security provides annotations for method-level security:

```java
package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Only users with ADMIN role can access
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    // Users can only access their own data, or ADMIN can access anyone
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Check permission after method execution
    @PostAuthorize("returnObject.email == authentication.principal.username or hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    // Access current authenticated user
    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername());
    }

    // Multiple roles
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.update(id, request);
    }

    // Custom expression
    @PreAuthorize("@userSecurity.canDelete(#id, authentication)")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
```

### Custom Security Expression

```java
package com.example.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurityService {

    public boolean canDelete(Long userId, Authentication auth) {
        // Admin can delete anyone
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        // Users cannot delete themselves
        User currentUser = (User) auth.getPrincipal();
        return !currentUser.getId().equals(userId);
    }

    public boolean isOwner(Long resourceId, Authentication auth) {
        // Check if current user owns the resource
        User currentUser = (User) auth.getPrincipal();
        // ... check ownership logic
        return true;
    }
}
```

---

## OAuth2 Authentication

### What is OAuth2?

OAuth2 is an authorization framework that enables third-party applications to obtain limited access to a user's resources without exposing credentials.

### OAuth2 Roles

```
┌─────────────────┐      ┌─────────────────┐
│  Resource Owner │      │     Client      │
│     (User)      │      │  (Your App)     │
└────────┬────────┘      └────────┬────────┘
         │                        │
         │  1. Authorization      │
         │     Request            │
         │◄───────────────────────│
         │                        │
         │  2. Grants             │
         │     Permission         │
         │                        │
         ▼                        ▼
┌─────────────────┐      ┌─────────────────┐
│  Authorization  │      │    Resource     │
│     Server      │      │     Server      │
│  (Google, etc.) │      │   (Your API)    │
└─────────────────┘      └─────────────────┘
```

### OAuth2 Grant Types

| Grant Type | Use Case |
|------------|----------|
| **Authorization Code** | Web apps with server-side code |
| **Client Credentials** | Server-to-server communication |
| **Resource Owner Password** | Trusted first-party apps (legacy) |
| **Implicit** | Browser-based apps (deprecated) |
| **PKCE** | Mobile/SPA apps (recommended) |

### OAuth2 Resource Server (JWT)

Configure your API to accept OAuth2/JWT tokens from an external provider:

**Dependencies:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

**Configuration:**
```yaml
# application.yml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://accounts.google.com
          # Or specify jwk-set-uri directly
          # jwk-set-uri: https://www.googleapis.com/oauth2/v3/certs
```

**Security Config:**
```java
@Configuration
@EnableWebSecurity
public class OAuth2ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    // Convert JWT claims to Spring Security authorities
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter =
            new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
            grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
```

### OAuth2 Client (Login with Google/GitHub)

**Dependencies:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```

**Configuration:**
```yaml
# application.yml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            scope: openid, profile, email
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}
            scope: user:email, read:user
```

**Security Config:**
```java
@Configuration
@EnableWebSecurity
public class OAuth2ClientConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService())
                )
            );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }
}
```

### Custom OAuth2 User Service

```java
package com.example.security.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Find or create user in your database
        User user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setProvider(provider);
                newUser.setRole(Role.USER);
                return userRepository.save(newUser);
            });

        return new CustomOAuth2User(oauth2User, user);
    }
}
```

---

## Password Security

### Password Encoding

**Never store plain text passwords!** Always use a secure hashing algorithm.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    // BCrypt is recommended - includes salt and is intentionally slow
    return new BCryptPasswordEncoder();
}

// Usage
String encodedPassword = passwordEncoder.encode("plainPassword");
boolean matches = passwordEncoder.matches("plainPassword", encodedPassword);
```

### Password Strength Validation

```java
public record RegisterRequest(
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be 8-100 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "Password must contain: uppercase, lowercase, number, and special character"
    )
    String password
) {}
```

---

## Security Best Practices

### 1. HTTPS Only

Always use HTTPS in production:

```yaml
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: ${SSL_PASSWORD}
    key-store-type: PKCS12
```

### 2. Secure Headers

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .headers(headers -> headers
            .contentSecurityPolicy(csp -> csp
                .policyDirectives("default-src 'self'"))
            .frameOptions(frame -> frame.deny())
            .xssProtection(xss -> xss.disable()) // Modern browsers handle this
            .contentTypeOptions(content -> {}) // nosniff by default
        );

    return http.build();
}
```

### 3. Rate Limiting

```java
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS = 100; // per minute

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        int count = requestCounts.getOrDefault(clientIp, 0);

        if (count >= MAX_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded");
            return;
        }

        requestCounts.put(clientIp, count + 1);
        filterChain.doFilter(request, response);
    }
}
```

### 4. Input Validation

Always validate input to prevent injection attacks:

```java
@PostMapping
public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request) {
    // @Valid ensures validation annotations are checked
    return ResponseEntity.ok(userService.create(request));
}
```

### 5. Audit Logging

Log security events for monitoring:

```java
@Component
public class SecurityAuditLogger {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAuditLogger.class);

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        logger.info("LOGIN_SUCCESS: user={}", event.getAuthentication().getName());
    }

    @EventListener
    public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        logger.warn("LOGIN_FAILURE: user={}, reason={}",
            event.getAuthentication().getName(),
            event.getException().getMessage());
    }
}
```

---

## Common Security Vulnerabilities

### 1. SQL Injection
**Prevention:** Use parameterized queries (JPA/Hibernate does this automatically)

```java
// BAD - vulnerable
String query = "SELECT * FROM users WHERE email = '" + email + "'";

// GOOD - parameterized
@Query("SELECT u FROM User u WHERE u.email = :email")
User findByEmail(@Param("email") String email);
```

### 2. Cross-Site Scripting (XSS)
**Prevention:** Encode output, use Content-Security-Policy

### 3. Cross-Site Request Forgery (CSRF)
**Prevention:** CSRF tokens (for session-based), disable for stateless JWT APIs

### 4. Broken Authentication
**Prevention:** Strong passwords, account lockout, MFA

### 5. Sensitive Data Exposure
**Prevention:** Use HTTPS, encrypt sensitive data, don't log secrets

---

## Summary

| Concept | Description |
|---------|-------------|
| **Spring Security** | Framework for authentication and authorization |
| **JWT** | Stateless token-based authentication |
| **OAuth2** | Authorization framework for third-party access |
| **BCrypt** | Password hashing algorithm |
| **@PreAuthorize** | Method-level security annotation |
| **SecurityFilterChain** | Configures request security |
| **UserDetailsService** | Loads user for authentication |

### When to Use What

| Scenario | Solution |
|----------|----------|
| REST API with mobile/SPA clients | JWT Authentication |
| Login with Google/GitHub | OAuth2 Client |
| Microservice receiving tokens | OAuth2 Resource Server |
| Internal API | API Key or mTLS |
| Server-to-server | Client Credentials Grant |

---

## Next Steps

- Review the security demo project for complete implementation
- Practice with the exercises
- Continue to the Microservices module for distributed security patterns
