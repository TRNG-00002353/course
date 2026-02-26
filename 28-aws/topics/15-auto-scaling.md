# Auto Scaling for EC2 Instances

## What is Auto Scaling?

Auto Scaling automatically adjusts the number of EC2 instances based on demand, ensuring you have the right amount of capacity at all times.

```
Low Traffic                      High Traffic
┌─────────────────────┐         ┌─────────────────────┐
│   Auto Scaling      │         │   Auto Scaling      │
│   Group             │         │   Group             │
│                     │         │                     │
│   ┌──┐ ┌──┐        │         │ ┌──┐┌──┐┌──┐┌──┐┌──┐│
│   │EC2│ │EC2│       │ ──────► │ │EC2││EC2││EC2││EC2││EC2││
│   └──┘ └──┘        │  Scale  │ └──┘└──┘└──┘└──┘└──┘│
│                     │   Up    │                     │
│   2 instances       │         │   5 instances       │
└─────────────────────┘         └─────────────────────┘
```

---

## Benefits of Auto Scaling

| Benefit | Description |
|---------|-------------|
| **Cost Optimization** | Pay only for what you need |
| **High Availability** | Maintain minimum healthy instances |
| **Better Performance** | Scale up before users experience slowness |
| **Automated Management** | No manual intervention needed |
| **Fault Tolerance** | Replace unhealthy instances automatically |

---

## Key Concepts

### Auto Scaling Group (ASG)

A collection of EC2 instances managed as a logical unit.

```
Auto Scaling Group Configuration
┌─────────────────────────────────────────────────────┐
│ Minimum: 2    (always have at least 2)             │
│ Desired: 2    (current target)                     │
│ Maximum: 10   (never exceed 10)                    │
│                                                     │
│ Current State:                                      │
│ ┌──┐ ┌──┐                                          │
│ │EC2│ │EC2│    2 running (= desired)               │
│ └──┘ └──┘                                          │
└─────────────────────────────────────────────────────┘
```

### Launch Template

Defines how new instances are created.

```
Launch Template Contents
┌─────────────────────────────────────────────────────┐
│ AMI: ami-12345678 (your application image)         │
│ Instance Type: t3.micro                            │
│ Key Pair: my-key                                   │
│ Security Groups: sg-webapp                         │
│ User Data: (startup script)                        │
│ IAM Role: EC2-App-Role                             │
└─────────────────────────────────────────────────────┘
```

### Scaling Policies

Rules that determine when to scale.

```
Scaling Policy Types
┌─────────────────────────────────────────────────────┐
│                                                     │
│ Target Tracking: "Keep CPU at 50%"                 │
│   - Simplest, most common                          │
│   - AWS handles the math                           │
│                                                     │
│ Step Scaling: "Add 2 if CPU > 80%"                 │
│   - More control                                   │
│   - Multiple steps possible                        │
│                                                     │
│ Scheduled: "Scale to 10 at 9am"                    │
│   - Predictable load patterns                      │
│   - Time-based                                     │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## Architecture with Auto Scaling

```
                         Internet
                             │
                             ▼
                    ┌─────────────────┐
                    │       ALB       │
                    │  (Load Balancer)│
                    └────────┬────────┘
                             │
                             ▼
              ┌──────────────────────────────┐
              │      Auto Scaling Group       │
              │                               │
              │  ┌──────┐ ┌──────┐ ┌──────┐  │
              │  │ EC2  │ │ EC2  │ │ EC2  │  │
              │  │ AZ-a │ │ AZ-b │ │ AZ-c │  │
              │  └──────┘ └──────┘ └──────┘  │
              │                               │
              │  Scales based on:             │
              │  - CPU utilization            │
              │  - Request count              │
              │  - Custom metrics             │
              └──────────────────────────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │      RDS        │
                    └─────────────────┘
```

---

## Creating Auto Scaling Step by Step

### Step 1: Create Launch Template

```
EC2 → Launch Templates → Create launch template

1. Name: my-app-template
2. AMI: Select your application AMI
3. Instance type: t3.micro
4. Key pair: Select your key
5. Security groups: Select app security group
6. Advanced details:
   - IAM instance profile: Your EC2 role
   - User data: (optional startup script)
```

### User Data Script Example

```bash
#!/bin/bash
# Update and install Java
yum update -y
yum install -y java-17-amazon-corretto

# Start your application
cd /app
java -jar myapp.jar
```

### Step 2: Create Auto Scaling Group

```
EC2 → Auto Scaling Groups → Create Auto Scaling group

1. Name: my-app-asg
2. Launch template: my-app-template
3. VPC: Select your VPC
4. Subnets: Select multiple AZs (important!)
5. Load balancing:
   - Attach to existing load balancer
   - Select your target group
6. Health checks:
   - EC2 (default)
   - ELB (recommended when using ALB)
   - Grace period: 300 seconds
```

### Step 3: Configure Group Size

```
Group Size Configuration
┌─────────────────────────────────────────────────────┐
│ Desired capacity: 2                                 │
│ Minimum capacity: 2                                 │
│ Maximum capacity: 6                                 │
│                                                     │
│ Explanation:                                        │
│ - Start with 2 instances                           │
│ - Never go below 2 (high availability)             │
│ - Never exceed 6 (cost control)                    │
└─────────────────────────────────────────────────────┘
```

### Step 4: Configure Scaling Policies

```
Scaling Policies → Target tracking

Policy name: cpu-target-tracking
Metric type: Average CPU utilization
Target value: 50

Additional settings:
- Scale-in cooldown: 300 seconds
- Scale-out cooldown: 60 seconds
```

---

## Scaling Policy Types Explained

### Target Tracking Scaling

The simplest and most recommended approach.

```
Target Tracking Example
┌─────────────────────────────────────────────────────┐
│ Target: Keep CPU at 50%                            │
│                                                     │
│ CPU at 30% → ASG removes instances                 │
│ CPU at 50% → No change                             │
│ CPU at 70% → ASG adds instances                    │
│                                                     │
│ AWS automatically calculates when to scale         │
└─────────────────────────────────────────────────────┘
```

**Available Metrics:**
- Average CPU Utilization
- Average Network In/Out
- ALB Request Count Per Target

### Step Scaling

More control with specific thresholds.

```
Step Scaling Example
┌─────────────────────────────────────────────────────┐
│ Scale Out (Add instances):                         │
│ ├── CPU > 60% for 2 min → Add 1 instance          │
│ ├── CPU > 80% for 2 min → Add 2 instances         │
│ └── CPU > 90% for 2 min → Add 3 instances         │
│                                                     │
│ Scale In (Remove instances):                       │
│ ├── CPU < 40% for 5 min → Remove 1 instance       │
│ └── CPU < 20% for 5 min → Remove 2 instances      │
└─────────────────────────────────────────────────────┘
```

### Scheduled Scaling

For predictable traffic patterns.

```
Scheduled Scaling Example
┌─────────────────────────────────────────────────────┐
│ Weekday Morning Rush:                              │
│   Cron: 0 8 * * 1-5                                │
│   Action: Set desired = 5                          │
│                                                     │
│ Weekday Evening:                                   │
│   Cron: 0 18 * * 1-5                               │
│   Action: Set desired = 2                          │
│                                                     │
│ Weekend:                                           │
│   Cron: 0 0 * * 6-7                                │
│   Action: Set desired = 1                          │
└─────────────────────────────────────────────────────┘
```

---

## Health Checks

### EC2 Health Checks

Default check - instance is running.

### ELB Health Checks

Recommended when using Load Balancer - checks application health.

```
Health Check Configuration
┌─────────────────────────────────────────────────────┐
│ Health check type: ELB                             │
│ Health check grace period: 300 seconds             │
│                                                     │
│ Behavior:                                          │
│ - Wait 300 seconds after launch                    │
│ - If health check fails, mark unhealthy            │
│ - Terminate unhealthy instance                     │
│ - Launch replacement instance                      │
└─────────────────────────────────────────────────────┘
```

---

## Cooldown Periods

Prevent rapid scaling actions.

```
Cooldown Period Example
┌─────────────────────────────────────────────────────┐
│                                                     │
│ 10:00 - CPU spikes to 80%                          │
│ 10:01 - Scale out: Add 2 instances                 │
│ 10:01 - 10:06 - Cooldown period (5 min)            │
│         (No scaling actions during this time)       │
│ 10:06 - Cooldown ends, evaluate metrics again      │
│                                                     │
│ Without cooldown: Could add instances continuously │
└─────────────────────────────────────────────────────┘
```

**Recommended values:**
- Scale-out cooldown: 60-120 seconds (react quickly)
- Scale-in cooldown: 300-600 seconds (be conservative)

---

## Instance Refresh

Update all instances in an ASG with a new launch template.

```
EC2 → Auto Scaling groups → Select group → Instance refresh

Configuration:
- Minimum healthy percentage: 90%
- Instance warmup: 300 seconds
- Skip matching: Yes (skip already updated instances)
```

---

## Monitoring Auto Scaling

### CloudWatch Metrics

| Metric | Description |
|--------|-------------|
| **GroupMinSize** | Minimum group size |
| **GroupMaxSize** | Maximum group size |
| **GroupDesiredCapacity** | Current target |
| **GroupInServiceInstances** | Healthy running instances |
| **GroupPendingInstances** | Instances starting up |
| **GroupTerminatingInstances** | Instances shutting down |

### Useful Alarms

```
Recommended Alarms
┌─────────────────────────────────────────────────────┐
│ 1. GroupInServiceInstances < Minimum               │
│    Alert: ASG couldn't maintain minimum capacity   │
│                                                     │
│ 2. GroupDesiredCapacity = Maximum                  │
│    Alert: ASG maxed out, may need higher limit     │
│                                                     │
│ 3. Failed launch activities                        │
│    Alert: New instances failing to start           │
└─────────────────────────────────────────────────────┘
```

---

## Lifecycle Hooks

Perform actions during instance launch or termination.

```
Lifecycle Hook Example
┌─────────────────────────────────────────────────────┐
│                                                     │
│ Instance Launch:                                    │
│ ┌──────┐      ┌──────────┐      ┌──────────┐      │
│ │Pending│ ──► │Pending:  │ ──► │InService │       │
│ │       │     │Wait      │     │          │       │
│ └──────┘      └──────────┘      └──────────┘      │
│                    │                               │
│               Run setup script                     │
│               Register with monitoring             │
│               Warm up cache                        │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## Pricing

```
Auto Scaling Pricing
┌─────────────────────────────────────────────────────┐
│ Auto Scaling service: FREE                         │
│                                                     │
│ You pay for:                                        │
│ - EC2 instances launched                           │
│ - CloudWatch metrics (if using detailed)           │
│ - Data transfer                                    │
│                                                     │
│ Cost optimization:                                  │
│ - Only runs instances when needed                  │
│ - Terminates unused capacity automatically         │
└─────────────────────────────────────────────────────┘
```

---

## Best Practices

### Do's

```
✓ Use multiple Availability Zones
✓ Set appropriate min/max values
✓ Use ELB health checks with ALB
✓ Set proper cooldown periods
✓ Use target tracking for simplicity
✓ Test scaling with load testing
✓ Set up CloudWatch alarms
```

### Don'ts

```
✗ Set minimum to 0 for production (no availability)
✗ Set cooldown too short (causes thrashing)
✗ Forget the health check grace period
✗ Use overly aggressive scaling thresholds
✗ Ignore scaling activity history
```

---

## Troubleshooting

| Issue | Possible Cause | Solution |
|-------|---------------|----------|
| Instances not launching | Launch template issue | Check template config, AMI |
| Scaling too slowly | Cooldown too long | Reduce cooldown period |
| Thrashing (rapid scale in/out) | Cooldown too short | Increase cooldown |
| Unhealthy instances | App not starting | Check user data script |
| Stuck in Pending:Wait | Lifecycle hook timeout | Complete or abandon hook |

### Viewing Scaling Activity

```
EC2 → Auto Scaling groups → Select group → Activity

Shows:
- Scaling actions taken
- Success/failure status
- Timestamps
- Reasons for actions
```

---

## Summary

| Concept | Description |
|---------|-------------|
| **ASG** | Manages a group of EC2 instances |
| **Launch Template** | Defines instance configuration |
| **Target Tracking** | Maintain a target metric value |
| **Step Scaling** | Scale based on threshold steps |
| **Scheduled Scaling** | Scale at specific times |
| **Cooldown** | Pause between scaling actions |

### Setup Checklist

- [ ] Create Launch Template with AMI
- [ ] Create Auto Scaling Group
- [ ] Configure minimum, desired, maximum
- [ ] Attach to Load Balancer target group
- [ ] Enable ELB health checks
- [ ] Create scaling policy (target tracking recommended)
- [ ] Set appropriate cooldown periods
- [ ] Set up CloudWatch alarms

## Next Topic

Continue to [Introduction to Serverless](./16-serverless-intro.md) to learn about running code without managing servers.
