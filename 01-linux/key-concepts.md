# Linux Key Concepts for Application Developers

## Overview

This document outlines the essential Linux concepts every application developer must understand. These skills are critical for building, deploying, debugging, and maintaining applications in modern development environments.

---

## 1. File System Navigation & Structure

### Why It Matters
- All source code, configurations, and logs reside in the file system
- Understanding paths is essential for referencing files in code
- Server deployments require knowledge of standard Linux directories

### Key Concepts

| Concept | Description | Developer Use Case |
|---------|-------------|-------------------|
| Absolute Path | Full path from root `/` | Config files, deployment scripts |
| Relative Path | Path from current directory | Project file references |
| Home Directory `~` | User's home folder | Personal configs, SSH keys |
| `/var/log` | System and application logs | Debugging production issues |
| `/etc` | Configuration files | Server configuration |
| `/tmp` | Temporary files | Cache, build artifacts |
| `/opt` | Optional software | Installing custom applications |

### Essential Commands
```bash
pwd                 # Where am I?
ls -la              # What's here? (including hidden files)
cd /path/to/dir     # Navigate to directory
tree -L 2           # Visualize directory structure
```

---

## 2. File Operations

### Why It Matters
- Managing source code files
- Creating configuration files
- Handling build outputs and artifacts

### Key Concepts

| Operation | Command | Example |
|-----------|---------|---------|
| Create file | `touch` | `touch app.js` |
| Create with content | `echo >` | `echo "hello" > file.txt` |
| Copy | `cp` | `cp src/app.js dist/` |
| Move/Rename | `mv` | `mv old.js new.js` |
| Delete | `rm` | `rm -rf node_modules/` |
| Create directory | `mkdir -p` | `mkdir -p src/components` |

### Critical Operations for Developers
```bash
# Copy entire project
cp -r project/ project-backup/

# Move build output
mv dist/* /var/www/html/

# Clean build artifacts
rm -rf build/ dist/ node_modules/

# Create nested directory structure
mkdir -p src/{components,services,utils}
```

---

## 3. File Permissions & Ownership

### Why It Matters
- Security: Protect sensitive files (credentials, keys)
- Execution: Scripts must be executable
- Deployment: Web servers need correct permissions
- SSH: Key files require strict permissions

### Key Concepts

| Permission | Numeric | Meaning |
|------------|---------|---------|
| `rwx------` | 700 | Owner only (private scripts) |
| `rw-------` | 600 | Owner read/write (SSH keys, secrets) |
| `rwxr-xr-x` | 755 | Executable by all (scripts, directories) |
| `rw-r--r--` | 644 | Readable by all (config files) |

### Developer Scenarios
```bash
# Make script executable
chmod +x deploy.sh

# Secure SSH private key (REQUIRED for SSH to work)
chmod 600 ~/.ssh/id_rsa

# Set directory permissions for web server
chmod 755 /var/www/html

# Change ownership for deployment
sudo chown -R www-data:www-data /var/www/app
```

---

## 4. Environment Variables

### Why It Matters
- Application configuration (database URLs, API keys)
- Different values for dev/staging/production
- 12-factor app methodology compliance
- Security: Keep secrets out of code

### Key Concepts

| Variable | Purpose | Example |
|----------|---------|---------|
| `PATH` | Executable locations | Add custom bin directories |
| `HOME` | User home directory | Reference user configs |
| `NODE_ENV` | Node.js environment | `production`, `development` |
| `DATABASE_URL` | Database connection | Connection string |
| `API_KEY` | External service auth | Secret keys |

### Developer Usage
```bash
# View all environment variables
env

# Set for current session
export DATABASE_URL="postgres://localhost:5432/mydb"

# Set for single command
NODE_ENV=production npm start

# Add to shell profile (~/.bashrc or ~/.zshrc)
echo 'export PATH="$HOME/.local/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc

# Using .env files (load with dotenv in app)
cat > .env << EOF
DATABASE_URL=postgres://localhost:5432/mydb
API_KEY=your-secret-key
PORT=3000
EOF
```

---

## 5. Process Management

### Why It Matters
- Running application servers
- Managing background services
- Debugging hung processes
- Resource monitoring

### Key Concepts

| Command | Purpose | Use Case |
|---------|---------|----------|
| `ps aux` | List processes | Find running apps |
| `top` / `htop` | Real-time monitoring | Performance debugging |
| `kill PID` | Stop process | Stop crashed app |
| `kill -9 PID` | Force kill | Unresponsive process |
| `nohup` | Run after logout | Long-running tasks |
| `&` | Background process | Non-blocking execution |

### Developer Scenarios
```bash
# Find process using port 3000
lsof -i :3000
# or
netstat -tlnp | grep 3000

# Kill process on port 3000
kill $(lsof -t -i:3000)

# Run server in background
nohup node server.js &

# View process resource usage
ps aux | grep node

# Monitor system resources
htop
```

---

## 6. Package Management

### Why It Matters
- Installing development tools
- Setting up server dependencies
- Keeping systems updated and secure

### Key Concepts by Distribution

| Distribution | Package Manager | Commands |
|--------------|-----------------|----------|
| Ubuntu/Debian | apt | `apt install`, `apt update` |
| CentOS/RHEL | yum/dnf | `yum install`, `dnf install` |
| Arch | pacman | `pacman -S` |

### Essential Developer Tools Installation
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install -y git curl wget vim build-essential

# Install Node.js via NodeSource
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs

# Install Docker
sudo apt install -y docker.io docker-compose
sudo usermod -aG docker $USER

# Install Python tools
sudo apt install -y python3 python3-pip python3-venv
```

---

## 7. Text Processing & Search

### Why It Matters
- Searching codebases
- Log analysis and debugging
- Configuration file manipulation
- Data transformation in scripts

### Key Concepts

| Command | Purpose | Example |
|---------|---------|---------|
| `grep` | Search text patterns | Find errors in logs |
| `find` | Search files | Locate config files |
| `sed` | Stream editing | Replace text in files |
| `awk` | Text processing | Extract columns from output |
| `wc` | Count lines/words | Count lines of code |

### Developer Scenarios
```bash
# Find all TODO comments in codebase
grep -rn "TODO" ./src/

# Find all JavaScript files
find . -name "*.js" -not -path "./node_modules/*"

# Count lines of code
find ./src -name "*.js" | xargs wc -l

# Search for errors in logs (last 100 lines)
tail -100 /var/log/app.log | grep -i error

# Replace string in all files
sed -i 's/oldFunction/newFunction/g' src/*.js

# Extract specific column from output
ps aux | awk '{print $2, $11}' | head -10
```

---

## 8. I/O Redirection & Pipes

### Why It Matters
- Combining commands efficiently
- Saving command output
- Build scripts and automation
- Log file management

### Key Concepts

| Symbol | Meaning | Example |
|--------|---------|---------|
| `>` | Redirect output (overwrite) | `ls > files.txt` |
| `>>` | Redirect output (append) | `echo "log" >> app.log` |
| `<` | Redirect input | `sort < names.txt` |
| `2>` | Redirect errors | `cmd 2> errors.log` |
| `&>` | Redirect all output | `cmd &> all.log` |
| `\|` | Pipe to next command | `cat file \| grep pattern` |

### Developer Scenarios
```bash
# Save test output
npm test > test-results.txt 2>&1

# Append to log file
echo "$(date): Deployment started" >> deploy.log

# Chain commands for analysis
cat access.log | grep "POST" | wc -l

# Run command, ignore output
npm install > /dev/null 2>&1

# Build and log errors only
npm run build 2> build-errors.log
```

---

## 9. Shell Scripting Essentials

### Why It Matters
- Automation of repetitive tasks
- CI/CD pipeline scripts
- Deployment automation
- Development workflow optimization

### Key Concepts

| Concept | Syntax | Use Case |
|---------|--------|----------|
| Shebang | `#!/bin/bash` | Script interpreter |
| Variables | `VAR="value"` | Store values |
| Conditionals | `if [ ]; then fi` | Decision logic |
| Loops | `for i in ...; do done` | Iteration |
| Functions | `name() { }` | Reusable code |
| Exit codes | `exit 0` or `exit 1` | Success/failure |

### Practical Scripts for Developers

**Build & Deploy Script:**
```bash
#!/bin/bash
set -e  # Exit on error

echo "Building application..."
npm run build

echo "Running tests..."
npm test

echo "Deploying to server..."
rsync -avz dist/ user@server:/var/www/app/

echo "Deployment complete!"
```

**Environment Setup Script:**
```bash
#!/bin/bash

# Check for required tools
command -v node >/dev/null 2>&1 || { echo "Node.js required"; exit 1; }
command -v git >/dev/null 2>&1 || { echo "Git required"; exit 1; }

# Install dependencies
npm install

# Setup environment
cp .env.example .env

echo "Development environment ready!"
```

---

## 10. Networking Basics

### Why It Matters
- Running web servers locally
- Debugging connection issues
- Configuring application ports
- Security and firewall rules

### Key Concepts

| Command | Purpose | Example |
|---------|---------|---------|
| `curl` | HTTP requests | Test APIs |
| `wget` | Download files | Fetch resources |
| `netstat` | Network connections | Check open ports |
| `ss` | Socket statistics | Modern netstat |
| `ping` | Test connectivity | Check server reachability |
| `nc` | Network utility | Port testing |

### Developer Scenarios
```bash
# Test API endpoint
curl -X GET http://localhost:3000/api/users
curl -X POST -H "Content-Type: application/json" -d '{"name":"test"}' http://localhost:3000/api/users

# Check what's running on port 3000
ss -tlnp | grep 3000
lsof -i :3000

# Test if port is open
nc -zv localhost 5432

# Download file
wget https://example.com/file.zip

# Check network connectivity
ping -c 4 google.com

# View routing table
ip route
```

---

## 11. SSH & Remote Access

### Why It Matters
- Deploying to remote servers
- Accessing cloud instances
- Secure file transfers
- Git operations with SSH keys

### Key Concepts

| Operation | Command | Use Case |
|-----------|---------|----------|
| Connect | `ssh user@host` | Access remote server |
| Copy files | `scp` | Transfer single files |
| Sync files | `rsync` | Sync directories |
| Key generation | `ssh-keygen` | Create SSH keys |
| Key agent | `ssh-add` | Manage keys |

### Developer Workflows
```bash
# Generate SSH key for GitHub/GitLab
ssh-keygen -t ed25519 -C "your.email@example.com"

# Add key to SSH agent
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519

# Test GitHub connection
ssh -T git@github.com

# Connect to server
ssh -i ~/.ssh/key.pem ubuntu@ec2-instance.amazonaws.com

# Copy file to server
scp ./dist.tar.gz user@server:/home/user/

# Sync project to server
rsync -avz --exclude 'node_modules' ./project/ user@server:/var/www/app/

# SSH tunnel for database access
ssh -L 5432:localhost:5432 user@dbserver
```

---

## 12. Log Management

### Why It Matters
- Debugging production issues
- Monitoring application health
- Security auditing
- Performance analysis

### Key Concepts

| Log Location | Contains |
|--------------|----------|
| `/var/log/syslog` | System messages |
| `/var/log/auth.log` | Authentication events |
| `/var/log/nginx/` | Web server logs |
| `~/.pm2/logs/` | PM2 managed apps |
| Application logs | Custom app output |

### Developer Workflows
```bash
# Follow log file in real-time
tail -f /var/log/app.log

# View last 100 lines
tail -100 /var/log/syslog

# Search logs for errors
grep -i "error" /var/log/app.log | tail -20

# View logs with timestamps
journalctl -u myapp.service --since "1 hour ago"

# Combine multiple logs and search
cat /var/log/app/*.log | grep "Exception" | sort

# Count errors by type
grep -oh "Error: [^:]*" app.log | sort | uniq -c | sort -rn
```

---

## 13. Service Management (systemd)

### Why It Matters
- Running applications as services
- Auto-restart on crash
- Boot-time startup
- Managing dependencies

### Key Concepts

| Command | Purpose |
|---------|---------|
| `systemctl start` | Start service |
| `systemctl stop` | Stop service |
| `systemctl restart` | Restart service |
| `systemctl status` | Check status |
| `systemctl enable` | Enable on boot |
| `journalctl -u` | View service logs |

### Creating Application Service
```bash
# Create service file
sudo nano /etc/systemd/system/myapp.service
```

```ini
[Unit]
Description=My Node.js Application
After=network.target

[Service]
Type=simple
User=www-data
WorkingDirectory=/var/www/myapp
ExecStart=/usr/bin/node /var/www/myapp/server.js
Restart=on-failure
RestartSec=10
Environment=NODE_ENV=production
Environment=PORT=3000

[Install]
WantedBy=multi-user.target
```

```bash
# Enable and start service
sudo systemctl daemon-reload
sudo systemctl enable myapp
sudo systemctl start myapp
sudo systemctl status myapp
```

---

## 14. Disk & Storage Management

### Why It Matters
- Monitoring server space
- Managing log rotation
- Handling upload storage
- Database storage planning

### Key Commands
```bash
# Check disk space
df -h

# Check directory size
du -sh /var/www/

# Find large files
find / -size +100M -type f 2>/dev/null

# List largest directories
du -sh /* 2>/dev/null | sort -hr | head -10

# Check inode usage
df -i
```

---

## 15. Docker Integration

### Why It Matters
- Containers run on Linux kernel
- Development environment parity
- Understanding Docker commands is Linux-based
- Debugging container issues

### Key Concepts
```bash
# Docker uses Linux namespaces and cgroups
# All Docker commands work with Linux concepts

# Enter running container
docker exec -it container_name bash

# View container logs
docker logs -f container_name

# Inspect container
docker inspect container_name

# Check container processes
docker top container_name

# Copy files to/from container
docker cp file.txt container_name:/app/
docker cp container_name:/app/logs ./
```

---

## Quick Reference Card

### Daily Developer Commands
```bash
# Navigation & Files
pwd && ls -la              # Where am I and what's here?
cd ~/projects/myapp        # Go to project
mkdir -p src/{api,models}  # Create structure

# Process & Ports
lsof -i :3000              # What's on port 3000?
kill $(lsof -t -i:3000)    # Kill it

# Logs & Debugging
tail -f app.log            # Watch logs
grep -rn "error" ./src     # Find errors in code

# Permissions
chmod +x script.sh         # Make executable
chmod 600 ~/.ssh/id_rsa    # Secure SSH key

# Environment
export NODE_ENV=production # Set variable
source ~/.bashrc           # Reload config

# Remote Operations
ssh user@server            # Connect
rsync -avz src/ user@server:dest/  # Sync files
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Navigate the Linux file system confidently
- [ ] Manage files and directories using CLI commands
- [ ] Understand and modify file permissions
- [ ] Set and use environment variables
- [ ] Monitor and manage processes
- [ ] Search code and logs using grep and find
- [ ] Write basic shell scripts for automation
- [ ] Use SSH for remote server access
- [ ] Manage services with systemd
- [ ] Debug applications using logs and system tools
- [ ] Understand how Docker utilizes Linux

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 2: Git Version Control](../02-git/) - Essential for code management
- Practice these commands daily in your development workflow
- Set up a Linux VM or use WSL for Windows users
