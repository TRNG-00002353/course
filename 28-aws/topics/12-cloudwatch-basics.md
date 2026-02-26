# CloudWatch Basics

## What is CloudWatch?

Amazon CloudWatch is AWS's monitoring and observability service. It collects metrics, logs, and events from your AWS resources.

```
AWS Resources                    CloudWatch
┌─────────────────┐             ┌─────────────────────────┐
│ EC2 Instances   │────────────►│ Metrics (CPU, Memory)   │
│ RDS Databases   │────────────►│ Logs (Application logs) │
│ Lambda Functions│────────────►│ Alarms (Notifications)  │
│ S3 Buckets      │────────────►│ Dashboards (Visuals)    │
└─────────────────┘             └─────────────────────────┘
```

---

## Core CloudWatch Components

| Component | Purpose |
|-----------|---------|
| **Metrics** | Numerical data points (CPU %, request count) |
| **Logs** | Text-based log data |
| **Alarms** | Notifications when metrics cross thresholds |
| **Dashboards** | Visual display of metrics |
| **Events/EventBridge** | React to changes in AWS resources |

---

## CloudWatch Metrics

### What Are Metrics?

Metrics are time-series data points that represent resource behavior.

```
EC2 CPU Utilization Over Time
100%│                    ╭─╮
    │                 ╭──╯ ╰──╮
 75%│              ╭──╯       ╰──╮
    │           ╭──╯             ╰──╮
 50%│        ╭──╯                   ╰──╮
    │     ╭──╯                         ╰──
 25%│  ╭──╯
    │──╯
  0%└────────────────────────────────────────
    8am    10am    12pm    2pm    4pm    6pm
```

### Common EC2 Metrics

| Metric | Description | Unit |
|--------|-------------|------|
| **CPUUtilization** | CPU usage percentage | Percent |
| **NetworkIn** | Bytes received | Bytes |
| **NetworkOut** | Bytes sent | Bytes |
| **DiskReadOps** | Read operations | Count |
| **DiskWriteOps** | Write operations | Count |
| **StatusCheckFailed** | Instance health | Count |

### Common RDS Metrics

| Metric | Description | Unit |
|--------|-------------|------|
| **CPUUtilization** | Database CPU usage | Percent |
| **DatabaseConnections** | Active connections | Count |
| **FreeStorageSpace** | Available storage | Bytes |
| **ReadIOPS** | Read operations/second | Count/Second |
| **WriteIOPS** | Write operations/second | Count/Second |

---

## Viewing Metrics

### Via AWS Console

```
CloudWatch → Metrics → All metrics

1. Select namespace (EC2, RDS, etc.)
2. Choose dimension (Per-Instance Metrics)
3. Select specific metric (CPUUtilization)
4. View graph
```

### Metric Namespaces

```
CloudWatch Metrics Organization
├── AWS/EC2
│   ├── CPUUtilization
│   ├── NetworkIn
│   └── StatusCheckFailed
├── AWS/RDS
│   ├── CPUUtilization
│   ├── DatabaseConnections
│   └── FreeStorageSpace
├── AWS/S3
│   ├── BucketSizeBytes
│   └── NumberOfObjects
└── Custom
    └── Your application metrics
```

---

## CloudWatch Alarms

Alarms notify you when metrics cross defined thresholds.

### Alarm States

```
Alarm States
┌─────────────────────────────────────────────────────┐
│                                                     │
│  OK         │  Metric is within threshold           │
│  ─────────────────────────────────────────────────  │
│  ALARM      │  Metric has crossed threshold         │
│  ─────────────────────────────────────────────────  │
│  INSUFFICIENT_DATA │  Not enough data to evaluate  │
│                                                     │
└─────────────────────────────────────────────────────┘
```

### Creating an Alarm

```
CloudWatch → Alarms → Create alarm

Step 1: Select metric
  - EC2 → Per-Instance Metrics → CPUUtilization

Step 2: Configure conditions
  - Threshold type: Static
  - Whenever CPUUtilization is: Greater than 80
  - For: 2 consecutive periods

Step 3: Configure actions
  - Notification: Create SNS topic
  - Email: your-email@example.com

Step 4: Name and description
  - Name: high-cpu-alarm
  - Description: Alert when CPU > 80%
```

### Example Alarms for Beginners

| Alarm | Metric | Threshold | Action |
|-------|--------|-----------|--------|
| High CPU | CPUUtilization | > 80% | Email alert |
| Low Storage | FreeStorageSpace | < 5 GB | Email alert |
| Billing | EstimatedCharges | > $10 | Email alert |
| Instance Down | StatusCheckFailed | > 0 | Email alert |

---

## CloudWatch Logs

Store, monitor, and analyze log files.

### Log Concepts

```
CloudWatch Logs Structure
┌─────────────────────────────────────────────────────┐
│ Log Group: /aws/ec2/my-app                         │
│ ├── Log Stream: i-1234567890abcdef0                │
│ │   ├── Log Event: 2024-01-15 10:00:00 INFO...    │
│ │   ├── Log Event: 2024-01-15 10:00:01 ERROR...   │
│ │   └── Log Event: 2024-01-15 10:00:02 INFO...    │
│ └── Log Stream: i-0987654321fedcba0                │
│     └── Log Event: ...                             │
└─────────────────────────────────────────────────────┘
```

| Term | Description |
|------|-------------|
| **Log Group** | Collection of log streams (e.g., all logs from an app) |
| **Log Stream** | Sequence of log events from one source |
| **Log Event** | Single log entry with timestamp and message |

### Sending Logs to CloudWatch

**From EC2 (using CloudWatch Agent):**

1. Install CloudWatch Agent:
```bash
sudo yum install -y amazon-cloudwatch-agent
```

2. Configure the agent:
```bash
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-config-wizard
```

3. Start the agent:
```bash
sudo systemctl start amazon-cloudwatch-agent
```

### Viewing Logs

```
CloudWatch → Logs → Log groups → Select group → Select stream

Features:
- Filter by time range
- Search within logs
- Download logs
```

---

## CloudWatch Dashboards

Create custom visualizations of your metrics.

### Creating a Dashboard

```
CloudWatch → Dashboards → Create dashboard

1. Name: My-App-Dashboard
2. Add widgets:
   - Line graph: EC2 CPU over time
   - Number: Current connections
   - Alarm status: All alarms
```

### Widget Types

| Widget | Best For |
|--------|----------|
| **Line** | Trends over time |
| **Stacked area** | Comparing multiple metrics |
| **Number** | Current single value |
| **Gauge** | Value against max |
| **Text** | Notes and documentation |
| **Alarm status** | Health overview |

### Example Dashboard Layout

```
┌─────────────────────────────────────────────────────┐
│                  My App Dashboard                    │
├─────────────────────────┬───────────────────────────┤
│   EC2 CPU Utilization   │   RDS Connections         │
│   [Line Graph]          │   [Line Graph]            │
│                         │                           │
├─────────────────────────┼───────────────────────────┤
│   Current Requests      │   Alarm Status            │
│   ┌─────────┐           │   ✓ CPU Alarm: OK         │
│   │  1,234  │           │   ✓ Storage: OK           │
│   └─────────┘           │   ⚠ Memory: ALARM         │
└─────────────────────────┴───────────────────────────┘
```

---

## CloudWatch Agent

The CloudWatch Agent collects additional metrics not available by default.

### Why Use the Agent?

Default EC2 metrics don't include:
- Memory utilization
- Disk space usage
- Custom application metrics

```
Without Agent              With Agent
┌─────────────────┐       ┌─────────────────┐
│ CPU ✓           │       │ CPU ✓           │
│ Network ✓       │       │ Network ✓       │
│ Disk I/O ✓      │       │ Disk I/O ✓      │
│ Memory ✗        │       │ Memory ✓        │
│ Disk Space ✗    │       │ Disk Space ✓    │
└─────────────────┘       └─────────────────┘
```

### Quick Agent Setup

```bash
# Install agent
sudo yum install -y amazon-cloudwatch-agent

# Create basic config
cat << 'EOF' | sudo tee /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json
{
  "metrics": {
    "metrics_collected": {
      "mem": {
        "measurement": ["mem_used_percent"]
      },
      "disk": {
        "measurement": ["disk_used_percent"],
        "resources": ["/"]
      }
    }
  }
}
EOF

# Start agent
sudo amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s \
  -c file:/opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json
```

---

## Billing Alarm Setup

Essential for avoiding surprise charges.

### Step-by-Step

```
1. Go to CloudWatch (must be in us-east-1 region)
2. Alarms → Create alarm
3. Select metric:
   Billing → Total Estimated Charge → USD
4. Set threshold:
   Greater than $5 (or your preferred amount)
5. Create SNS topic and add email
6. Name: billing-alarm
7. Create
```

**Important:** Confirm the SNS email subscription!

---

## Free Tier Limits

```
CloudWatch Free Tier
┌─────────────────────────────────────────────────────┐
│ Metrics:                                            │
│ - 10 custom metrics                                 │
│ - Basic monitoring (5-minute intervals)             │
│                                                     │
│ Alarms:                                             │
│ - 10 alarm metrics                                  │
│                                                     │
│ Logs:                                               │
│ - 5 GB data ingestion                               │
│ - 5 GB data archive                                 │
│                                                     │
│ Dashboards:                                         │
│ - 3 dashboards (up to 50 metrics)                  │
└─────────────────────────────────────────────────────┘
```

---

## Common Monitoring Scenarios

### Scenario 1: EC2 High CPU

```
Problem: Application running slow
Action: Create CPU alarm

Alarm: CPUUtilization > 80% for 5 minutes
Response:
- Scale up instance
- Investigate application
- Add auto-scaling
```

### Scenario 2: RDS Storage Full

```
Problem: Database stops accepting writes
Action: Create storage alarm

Alarm: FreeStorageSpace < 10% of total
Response:
- Increase storage
- Archive old data
- Review queries
```

### Scenario 3: Application Errors

```
Problem: Need to track application errors
Action: Send logs to CloudWatch

Setup:
- Configure CloudWatch Agent
- Create metric filter for "ERROR"
- Create alarm on error count
```

---

## Summary

| Component | Purpose | Free Tier |
|-----------|---------|-----------|
| **Metrics** | Track resource performance | 10 custom metrics |
| **Alarms** | Alert on thresholds | 10 alarms |
| **Logs** | Store and search logs | 5 GB ingestion |
| **Dashboards** | Visualize metrics | 3 dashboards |
| **Agent** | Collect memory/disk metrics | Included |

### Getting Started Checklist

- [ ] Set up billing alarm (most important!)
- [ ] Create CPU utilization alarm for EC2
- [ ] Create storage alarm for RDS
- [ ] Set up basic dashboard
- [ ] (Optional) Install CloudWatch Agent for memory metrics

## Next Topic

Continue to [AWS Trusted Advisor](./13-trusted-advisor.md) to learn about AWS's recommendation service.
