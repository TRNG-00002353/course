# RDS Setup

## What is RDS?

Amazon Relational Database Service (RDS) is a managed database service. AWS handles backups, patching, and maintenance—you focus on your application.

```
Self-Managed DB (EC2)              Managed DB (RDS)
┌─────────────────────┐      ┌─────────────────────┐
│ Install database    │      │ AWS handles setup   │
│ Configure backups   │  →   │ Automatic backups   │
│ Apply patches       │      │ Automatic patching  │
│ Monitor health      │      │ Built-in monitoring │
└─────────────────────┘      └─────────────────────┘
```

---

## Supported Database Engines

| Engine | Use Case |
|--------|----------|
| **MySQL** | Popular, great for web apps |
| **PostgreSQL** | Advanced features, standards-compliant |
| MariaDB | MySQL-compatible alternative |
| Oracle | Enterprise applications |
| SQL Server | Microsoft ecosystem |
| Aurora | AWS-optimized MySQL/PostgreSQL |

**For this course:** MySQL or PostgreSQL (both have free tier).

---

## Free Tier Limits

| Resource | Free Allowance |
|----------|----------------|
| **Instance** | 750 hours/month of db.t3.micro |
| **Storage** | 20 GB General Purpose SSD |
| **Backups** | 20 GB backup storage |
| **Duration** | 12 months from signup |

**Stay within limits to avoid charges!**

---

## Creating an RDS Instance

### Step 1: Open RDS Console

```
AWS Console → Services → RDS → Create database
```

### Step 2: Choose Creation Method

```
Standard create (full control)
```

### Step 3: Engine Options

```
Engine type: MySQL (or PostgreSQL)
Version: MySQL 8.0.x (latest)
```

### Step 4: Templates

```
✓ Free tier
```

This pre-selects appropriate settings.

### Step 5: Settings

```
DB instance identifier: my-app-db
Master username: admin
Master password: [Choose strong password]
```

**Save these credentials!**

### Step 6: Instance Configuration

```
DB instance class: db.t3.micro (free tier)
```

### Step 7: Storage

```
Storage type: General Purpose SSD (gp2)
Allocated storage: 20 GB
Storage autoscaling: Disable (to stay in free tier)
```

### Step 8: Connectivity

```
VPC: Default VPC
Public access: Yes (for initial setup, secure later)
VPC security group: Create new
  Name: my-db-sg
Availability Zone: No preference
Database port: 3306 (MySQL) or 5432 (PostgreSQL)
```

### Step 9: Authentication

```
Password authentication
```

### Step 10: Additional Configuration

```
Initial database name: myappdb
Backup retention: 7 days
Enable encryption: No (optional for free tier learning)
Enable Enhanced Monitoring: No (to avoid charges)
```

### Step 11: Create Database

Click "Create database" - takes 5-10 minutes.

---

## Getting Connection Details

After creation (status: Available):

```
RDS → Databases → Select database

Endpoint: my-app-db.abc123xyz.us-east-1.rds.amazonaws.com
Port: 3306
Database name: myappdb
Username: admin
```

---

## Configuring Security Group for RDS

By default, RDS blocks all connections. Allow your EC2 instance to connect.

### Option 1: Allow EC2 Security Group

```
RDS → Databases → my-app-db → Security group
  → Edit inbound rules

Add rule:
  Type: MySQL/Aurora (port 3306)
  Source: [EC2 Security Group ID]  (e.g., sg-0abc123)
```

### Option 2: Allow Specific IP (for local testing)

```
Add rule:
  Type: MySQL/Aurora
  Source: My IP
```

**Best Practice:** Use security group reference (Option 1) for EC2-to-RDS communication.

---

## Connecting to RDS

### From EC2 Instance

SSH into your EC2 instance, then:

```bash
# Install MySQL client (Amazon Linux)
sudo yum install mysql -y

# Connect to RDS
mysql -h my-app-db.abc123xyz.us-east-1.rds.amazonaws.com \
      -u admin -p

# Enter password when prompted
```

### From Local Machine (if public access enabled)

```bash
mysql -h my-app-db.abc123xyz.us-east-1.rds.amazonaws.com \
      -u admin -p myappdb
```

### Test Connection

```sql
-- Show databases
SHOW DATABASES;

-- Use your database
USE myappdb;

-- Create a test table
CREATE TABLE test (id INT PRIMARY KEY, name VARCHAR(50));

-- Insert data
INSERT INTO test VALUES (1, 'Hello RDS');

-- Query
SELECT * FROM test;
```

---

## Spring Boot Configuration

Configure your application to connect to RDS.

### application.properties

```properties
# MySQL
spring.datasource.url=jdbc:mysql://my-app-db.abc123xyz.us-east-1.rds.amazonaws.com:3306/myappdb
spring.datasource.username=admin
spring.datasource.password=your-password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### For PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://my-app-db.abc123xyz.us-east-1.rds.amazonaws.com:5432/myappdb
spring.datasource.username=admin
spring.datasource.password=your-password
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Using Environment Variables (Recommended)

Don't hardcode passwords in application.properties:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Set environment variables on EC2:
```bash
export DB_URL=jdbc:mysql://my-app-db.abc123xyz.us-east-1.rds.amazonaws.com:3306/myappdb
export DB_USERNAME=admin
export DB_PASSWORD=your-password
```

---

## RDS Best Practices

### Security

| Practice | Description |
|----------|-------------|
| **No public access** | Disable after initial setup |
| **Security groups** | Only allow EC2 security group |
| **Strong passwords** | Use complex master password |
| **Encryption** | Enable for production data |

### Cost Management

| Practice | Description |
|----------|-------------|
| **Free tier instance** | Use db.t3.micro |
| **Disable autoscaling** | Keep storage at 20 GB |
| **Stop when not in use** | For dev/test environments |
| **Monitor usage** | Check free tier dashboard |

### Stopping RDS (Save Costs)

```
RDS → Databases → Select database → Actions → Stop
```

**Note:** RDS auto-restarts after 7 days. For longer breaks, take a snapshot and delete the instance.

---

## Troubleshooting

### Cannot Connect

1. **Check security group**: Does it allow your IP/EC2 security group?
2. **Check public access**: Is it enabled if connecting from outside VPC?
3. **Check endpoint**: Using correct hostname?
4. **Check credentials**: Correct username/password?

### Connection Timeout

```
# Test connectivity from EC2
nc -zv my-app-db.abc123xyz.us-east-1.rds.amazonaws.com 3306
```

If timeout: Security group is likely blocking.

---

## Summary

| Concept | Description |
|---------|-------------|
| **RDS** | Managed relational database service |
| **db.t3.micro** | Free tier instance type |
| **Endpoint** | Database hostname for connections |
| **Security Group** | Firewall rules for database access |
| **Public Access** | Allow connections from outside VPC |

### Setup Checklist

- [ ] Create RDS instance (db.t3.micro)
- [ ] Note endpoint, port, credentials
- [ ] Configure security group for EC2 access
- [ ] Test connection from EC2
- [ ] Configure Spring Boot application.properties

## Next Topic

Continue to [Networking Essentials](./04-networking-essentials.md) to understand VPC and security group configurations.
