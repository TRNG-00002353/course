# Deploying Spring Boot to AWS

## Overview

This topic covers deploying a Spring Boot application to EC2 with RDS as the database.

```
┌─────────────────────────────────────────────────────────┐
│  Deployment Flow                                         │
│                                                          │
│  Local Machine          EC2 Instance         RDS        │
│  ┌───────────┐         ┌───────────┐      ┌─────────┐  │
│  │ Build JAR │ ──SCP──▶│ Run JAR   │─────▶│ MySQL   │  │
│  │           │         │ java -jar │      │         │  │
│  └───────────┘         └───────────┘      └─────────┘  │
│                              ▲                          │
│                              │                          │
│                         Browser/API                     │
└─────────────────────────────────────────────────────────┘
```

---

## Prerequisites

Before deploying:

- [ ] EC2 instance running (Amazon Linux 2023)
- [ ] RDS database created and accessible from EC2
- [ ] Security groups configured (EC2: 22, 8080; RDS: 3306)
- [ ] Spring Boot application ready

---

## Step 1: Prepare Your Application

### Configure Database Connection

**application.properties** (or application-prod.properties):

```properties
# Database
spring.datasource.url=jdbc:mysql://${DB_HOST}:3306/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Server
server.port=8080
```

Using environment variables keeps credentials out of code.

### Build the JAR

```bash
# From project root
./mvnw clean package -DskipTests

# Or with Maven
mvn clean package -DskipTests
```

JAR file created at: `target/your-app-0.0.1-SNAPSHOT.jar`

### Test Locally (Optional)

```bash
java -jar target/your-app-0.0.1-SNAPSHOT.jar
```

---

## Step 2: Prepare EC2 Instance

### Connect to EC2

```bash
ssh -i my-key-pair.pem ec2-user@<EC2-PUBLIC-IP>
```

### Install Java 17

```bash
# Amazon Linux 2023
sudo yum install java-17-amazon-corretto -y

# Verify
java -version
```

### Create Application Directory

```bash
sudo mkdir -p /opt/myapp
sudo chown ec2-user:ec2-user /opt/myapp
```

---

## Step 3: Transfer JAR to EC2

### Using SCP (Secure Copy)

From your local machine:

```bash
scp -i my-key-pair.pem target/your-app-0.0.1-SNAPSHOT.jar \
    ec2-user@<EC2-PUBLIC-IP>:/opt/myapp/app.jar
```

### Using SFTP

```bash
sftp -i my-key-pair.pem ec2-user@<EC2-PUBLIC-IP>
sftp> put target/your-app-0.0.1-SNAPSHOT.jar /opt/myapp/app.jar
sftp> exit
```

---

## Step 4: Configure Environment Variables

### Create Environment File

On EC2, create `/opt/myapp/.env`:

```bash
cat > /opt/myapp/.env << 'EOF'
DB_HOST=my-app-db.abc123xyz.us-east-1.rds.amazonaws.com
DB_NAME=myappdb
DB_USERNAME=admin
DB_PASSWORD=your-secure-password
EOF
```

Set permissions:
```bash
chmod 600 /opt/myapp/.env
```

### Load Environment Variables

```bash
source /opt/myapp/.env
export DB_HOST DB_NAME DB_USERNAME DB_PASSWORD
```

---

## Step 5: Run the Application

### Manual Start (Testing)

```bash
cd /opt/myapp
source .env
export DB_HOST DB_NAME DB_USERNAME DB_PASSWORD

java -jar app.jar
```

Application starts on port 8080.

### Test Access

From browser or curl:
```
http://<EC2-PUBLIC-IP>:8080
http://<EC2-PUBLIC-IP>:8080/api/your-endpoint
```

---

## Step 6: Run as a Service (Production)

Running manually stops when you disconnect. Create a systemd service for automatic startup.

### Create Service File

```bash
sudo nano /etc/systemd/system/myapp.service
```

Content:
```ini
[Unit]
Description=My Spring Boot Application
After=network.target

[Service]
Type=simple
User=ec2-user
WorkingDirectory=/opt/myapp
EnvironmentFile=/opt/myapp/.env
ExecStart=/usr/bin/java -jar /opt/myapp/app.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

### Enable and Start Service

```bash
# Reload systemd
sudo systemctl daemon-reload

# Enable on boot
sudo systemctl enable myapp

# Start service
sudo systemctl start myapp

# Check status
sudo systemctl status myapp
```

### Service Commands

```bash
sudo systemctl start myapp    # Start
sudo systemctl stop myapp     # Stop
sudo systemctl restart myapp  # Restart
sudo systemctl status myapp   # Check status
journalctl -u myapp -f        # View logs (follow)
```

---

## Step 7: View Logs

### Application Logs

```bash
# Follow logs in real-time
journalctl -u myapp -f

# Last 100 lines
journalctl -u myapp -n 100

# Logs since today
journalctl -u myapp --since today
```

### Configure Log File (Optional)

Add to application.properties:
```properties
logging.file.name=/opt/myapp/logs/application.log
logging.level.root=INFO
```

Create logs directory:
```bash
mkdir -p /opt/myapp/logs
```

---

## Updating the Application

### Deploy New Version

```bash
# 1. Build new JAR locally
./mvnw clean package -DskipTests

# 2. Transfer to EC2
scp -i my-key-pair.pem target/your-app-0.0.1-SNAPSHOT.jar \
    ec2-user@<EC2-PUBLIC-IP>:/opt/myapp/app.jar

# 3. SSH to EC2 and restart
ssh -i my-key-pair.pem ec2-user@<EC2-PUBLIC-IP>
sudo systemctl restart myapp
```

### Quick Update Script

Create `deploy.sh` locally:
```bash
#!/bin/bash
EC2_IP="<EC2-PUBLIC-IP>"
KEY_PATH="my-key-pair.pem"

echo "Building..."
./mvnw clean package -DskipTests

echo "Uploading..."
scp -i $KEY_PATH target/*.jar ec2-user@$EC2_IP:/opt/myapp/app.jar

echo "Restarting..."
ssh -i $KEY_PATH ec2-user@$EC2_IP "sudo systemctl restart myapp"

echo "Done!"
```

```bash
chmod +x deploy.sh
./deploy.sh
```

---

## Troubleshooting

### Application Won't Start

```bash
# Check logs
journalctl -u myapp -n 50

# Common issues:
# - Wrong Java version
# - Missing environment variables
# - Port already in use
# - Database connection failed
```

### Cannot Connect to Database

```bash
# Test from EC2
mysql -h <RDS-ENDPOINT> -u admin -p

# Check security group allows EC2 → RDS
# Check RDS is in "Available" state
```

### Cannot Access from Browser

```bash
# Check app is running
curl localhost:8080

# Check security group allows port 8080
# Verify using public IP, not private
```

### Port 8080 Already in Use

```bash
# Find process using port
sudo lsof -i :8080

# Kill if needed
sudo kill -9 <PID>
```

---

## Complete Deployment Checklist

### One-Time Setup

- [ ] Launch EC2 instance (t2.micro, Amazon Linux 2023)
- [ ] Create RDS instance (db.t3.micro, MySQL)
- [ ] Configure security groups
- [ ] Install Java 17 on EC2
- [ ] Create /opt/myapp directory
- [ ] Create environment file with DB credentials
- [ ] Create systemd service file

### Each Deployment

- [ ] Build JAR locally
- [ ] Transfer JAR to EC2
- [ ] Restart service
- [ ] Verify application is running
- [ ] Test endpoints

---

## Summary

| Step | Command |
|------|---------|
| Build JAR | `./mvnw clean package` |
| Transfer | `scp -i key.pem app.jar ec2-user@IP:/opt/myapp/` |
| Start | `sudo systemctl start myapp` |
| Stop | `sudo systemctl stop myapp` |
| Logs | `journalctl -u myapp -f` |
| Status | `sudo systemctl status myapp` |

### Architecture Recap

```
User → EC2 (Spring Boot :8080) → RDS (MySQL :3306)
```

## Module Complete

You've learned AWS fundamentals for deploying a monolithic application:
- AWS account setup and IAM
- EC2 instance management
- RDS database setup
- VPC networking and security groups
- Spring Boot deployment

For production, consider adding:
- Load balancer for high availability
- HTTPS with SSL certificate
- CI/CD pipeline for automated deployments
- CloudWatch for monitoring
