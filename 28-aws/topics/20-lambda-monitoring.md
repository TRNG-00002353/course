# Monitoring Lambda with CloudWatch

## Why Monitor Lambda?

Monitoring helps you understand function performance, detect issues, and optimize costs.

```
Without Monitoring              With Monitoring
┌─────────────────────┐        ┌─────────────────────┐
│ "Is my function     │        │ Dashboard shows:    │
│  working?"          │        │ - Invocations: 1.2K │
│                     │   →    │ - Errors: 2 (0.1%)  │
│ "Why is it slow?"   │        │ - Duration: 150ms   │
│                     │        │ - Cost: $0.05/day   │
│ "How much does      │        │                     │
│  it cost?"          │        │ Alert: Error spike! │
└─────────────────────┘        └─────────────────────┘
```

---

## CloudWatch Metrics for Lambda

Lambda automatically sends metrics to CloudWatch.

### Key Metrics

| Metric | Description | What to Watch |
|--------|-------------|---------------|
| **Invocations** | Number of function calls | Traffic patterns |
| **Duration** | Execution time (ms) | Performance |
| **Errors** | Failed invocations | Application health |
| **Throttles** | Requests rejected (concurrency limit) | Scaling issues |
| **ConcurrentExecutions** | Simultaneous executions | Capacity |
| **IteratorAge** | Lag for stream-based triggers | Processing delay |

### Viewing Metrics

```
Lambda → Your function → Monitor tab

Or:

CloudWatch → Metrics → Lambda → By Function Name
```

### Metrics Dashboard

```
Lambda Metrics Overview
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  Invocations (last hour)        Duration (avg)              │
│  ┌────────────────────┐         ┌────────────────────┐      │
│  │    ╭──╮            │         │ 145ms              │      │
│  │   ╭╯  ╰╮   ╭╮     │         │                    │      │
│  │ ╭─╯    ╰───╯╰──   │         │ p95: 320ms         │      │
│  └────────────────────┘         └────────────────────┘      │
│                                                             │
│  Errors                         Throttles                   │
│  ┌────────────────────┐         ┌────────────────────┐      │
│  │ 2                  │         │ 0                  │      │
│  └────────────────────┘         └────────────────────┘      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## CloudWatch Logs

Every Lambda execution writes logs to CloudWatch.

### Log Structure

```
CloudWatch Log Groups
└── /aws/lambda/my-function
    └── Log Stream: 2024/01/15/[$LATEST]abc123
        ├── START RequestId: xxx
        ├── [Your print statements]
        ├── END RequestId: xxx
        └── REPORT RequestId: xxx Duration: 145ms ...
```

### Viewing Logs

```
Lambda → Your function → Monitor → View CloudWatch logs

Or:

CloudWatch → Logs → Log groups → /aws/lambda/your-function
```

### Log Entry Example

```
START RequestId: d4c9c8f4-1234-5678-abcd-123456789abc Version: $LATEST
INFO: Processing request for user 12345
DEBUG: Database query took 45ms
INFO: Request completed successfully
END RequestId: d4c9c8f4-1234-5678-abcd-123456789abc
REPORT RequestId: d4c9c8f4-1234-5678-abcd-123456789abc
    Duration: 145.67 ms
    Billed Duration: 146 ms
    Memory Size: 128 MB
    Max Memory Used: 67 MB
    Init Duration: 234.56 ms  (only on cold start)
```

### REPORT Line Explained

```
REPORT Fields
┌─────────────────────────────────────────────────────────────┐
│ Duration: 145.67 ms        Actual execution time           │
│ Billed Duration: 146 ms    Rounded up (what you pay for)   │
│ Memory Size: 128 MB        Configured memory               │
│ Max Memory Used: 67 MB     Peak memory during execution    │
│ Init Duration: 234.56 ms   Cold start initialization       │
└─────────────────────────────────────────────────────────────┘
```

---

## Logging Best Practices

### Structured Logging

```python
import json
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def lambda_handler(event, context):
    # Structured log entry
    logger.info(json.dumps({
        'event': 'request_received',
        'request_id': context.aws_request_id,
        'user_id': event.get('userId'),
        'action': event.get('action')
    }))

    try:
        result = process_request(event)

        logger.info(json.dumps({
            'event': 'request_completed',
            'request_id': context.aws_request_id,
            'status': 'success'
        }))

        return result

    except Exception as e:
        logger.error(json.dumps({
            'event': 'request_failed',
            'request_id': context.aws_request_id,
            'error': str(e),
            'error_type': type(e).__name__
        }))
        raise
```

### Log Levels

```python
import logging
logger = logging.getLogger()
logger.setLevel(logging.INFO)

# Use appropriate levels
logger.debug("Detailed debugging info")    # Development
logger.info("General information")          # Normal operations
logger.warning("Something unexpected")      # Potential issues
logger.error("Error occurred")              # Errors
logger.critical("Critical failure")         # System down
```

---

## Creating CloudWatch Alarms

Set up alerts for important metrics.

### Error Rate Alarm

```
CloudWatch → Alarms → Create alarm

1. Select metric:
   Lambda → By Function Name → Errors → your-function

2. Conditions:
   Threshold: Greater than 5
   Period: 5 minutes

3. Actions:
   Send notification to SNS topic
   Email: your-email@example.com

4. Name: lambda-error-alarm
```

### Duration Alarm

```
CloudWatch → Alarms → Create alarm

Metric: Duration (Average)
Threshold: Greater than 5000 ms (5 seconds)
Period: 5 minutes
Action: SNS notification
```

### Recommended Alarms

| Alarm | Metric | Threshold | Why |
|-------|--------|-----------|-----|
| High Errors | Errors | > 5 in 5 min | Detect failures |
| Slow Execution | Duration (p95) | > 5000 ms | Performance issues |
| Throttling | Throttles | > 0 | Capacity issues |
| High Concurrent | ConcurrentExecutions | > 900 | Near limit |

---

## CloudWatch Dashboards

Create a visual overview of your functions.

### Creating a Dashboard

```
CloudWatch → Dashboards → Create dashboard

Name: Lambda-Dashboard
```

### Add Widgets

```
Widget Types:
- Line: Invocations over time
- Number: Current error count
- Stacked area: Duration percentiles
- Alarm status: All Lambda alarms
```

### Example Dashboard Layout

```
┌─────────────────────────────────────────────────────────────┐
│                   Lambda Dashboard                           │
├───────────────────────────┬─────────────────────────────────┤
│   Invocations (24h)       │   Errors (24h)                  │
│   [Line Graph]            │   [Line Graph]                  │
│                           │                                  │
├───────────────────────────┼─────────────────────────────────┤
│   Duration Percentiles    │   Concurrent Executions         │
│   [Stacked Area]          │   [Line Graph]                  │
│   p50 / p90 / p99         │                                  │
├───────────────────────────┴─────────────────────────────────┤
│   Alarm Status                                               │
│   ✓ Error Alarm: OK    ✓ Duration Alarm: OK                 │
│   ✓ Throttle Alarm: OK ⚠ Memory Alarm: ALARM                │
└─────────────────────────────────────────────────────────────┘
```

---

## CloudWatch Logs Insights

Query and analyze logs.

### Sample Queries

**Find errors:**
```sql
fields @timestamp, @message
| filter @message like /ERROR/
| sort @timestamp desc
| limit 50
```

**Analyze cold starts:**
```sql
filter @type = "REPORT"
| stats count(*) as invocations,
        avg(@initDuration) as avgColdStart,
        max(@initDuration) as maxColdStart
  by bin(1h)
```

**Duration statistics:**
```sql
filter @type = "REPORT"
| stats avg(@duration) as avgDuration,
        max(@duration) as maxDuration,
        percentile(@duration, 95) as p95
  by bin(1h)
```

**Memory usage:**
```sql
filter @type = "REPORT"
| stats avg(@maxMemoryUsed) as avgMemory,
        max(@maxMemoryUsed) as maxMemory,
        avg(@memorySize) as configuredMemory
```

### Running Queries

```
CloudWatch → Logs → Logs Insights

1. Select log group: /aws/lambda/your-function
2. Enter query
3. Set time range
4. Run query
```

---

## AWS X-Ray for Tracing

Trace requests through your serverless application.

### Enable X-Ray

```
Lambda → Your function → Configuration → Monitoring and operations tools

Active tracing: Enable
```

### Add X-Ray SDK (Python)

```python
from aws_xray_sdk.core import xray_recorder
from aws_xray_sdk.core import patch_all

# Patch AWS SDK calls
patch_all()

def lambda_handler(event, context):
    # Create custom subsegment
    with xray_recorder.in_subsegment('process-data'):
        result = process_data(event)

    return result
```

### View Traces

```
X-Ray → Service map

Shows:
- Request flow through services
- Latency at each step
- Error rates
```

### X-Ray Service Map

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   API Gateway ──► Lambda ──► DynamoDB                       │
│      25ms          150ms       45ms                         │
│                                                             │
│   Total: 220ms                                              │
│   Errors: 0.5%                                              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Cost Monitoring

Track Lambda costs.

### Cost Metrics

```
CloudWatch → Metrics → Lambda → Across All Functions

Useful metrics:
- Total invocations (for cost calculation)
- Total duration (GB-seconds)
```

### Cost Calculation

```
Lambda Cost Formula
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│ Request Cost = Invocations × $0.0000002                     │
│                                                             │
│ Duration Cost = (Memory GB) × (Duration seconds) × $0.0000166667 │
│                                                             │
│ Example (128 MB, 200ms, 1M invocations):                   │
│   Requests: 1,000,000 × $0.0000002 = $0.20                 │
│   Duration: 0.128 × 0.2 × 1,000,000 × $0.0000166667 = $0.43│
│   Total: $0.63 (free tier covers 400K GB-seconds)          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Cost Optimization Tips

```
1. Right-size memory
   - Test with different memory settings
   - Sometimes more memory = faster = cheaper

2. Reduce cold starts
   - Provisioned concurrency (costs money)
   - Keep functions warm (scheduled ping)

3. Optimize code
   - Reduce dependencies
   - Use connection pooling
   - Cache when possible
```

---

## Setting Up Alerts

### Complete Alert Setup

```bash
# Create SNS topic for alerts
aws sns create-topic --name lambda-alerts

# Subscribe email
aws sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:123456789:lambda-alerts \
    --protocol email \
    --notification-endpoint your-email@example.com

# Create CloudWatch alarm
aws cloudwatch put-metric-alarm \
    --alarm-name lambda-high-errors \
    --alarm-description "Lambda function error rate high" \
    --metric-name Errors \
    --namespace AWS/Lambda \
    --dimensions Name=FunctionName,Value=my-function \
    --statistic Sum \
    --period 300 \
    --evaluation-periods 1 \
    --threshold 5 \
    --comparison-operator GreaterThanThreshold \
    --alarm-actions arn:aws:sns:us-east-1:123456789:lambda-alerts
```

---

## Troubleshooting with Logs

### Common Issues

**Function timeout:**
```
Look for: Task timed out after X seconds
Solution: Increase timeout or optimize code
```

**Out of memory:**
```
Look for: Runtime exited with error: signal: killed
Solution: Increase memory allocation
```

**Permission denied:**
```
Look for: AccessDeniedException
Solution: Update IAM role permissions
```

**Cold start issues:**
```
Look for: High Init Duration values
Solution: Reduce package size, use provisioned concurrency
```

---

## Summary

| Tool | Purpose | When to Use |
|------|---------|-------------|
| **CloudWatch Metrics** | Performance data | Always (automatic) |
| **CloudWatch Logs** | Debug and audit | Always (automatic) |
| **CloudWatch Alarms** | Alerting | Set up for production |
| **Logs Insights** | Log analysis | Troubleshooting |
| **X-Ray** | Request tracing | Complex architectures |

### Monitoring Checklist

- [ ] Review Lambda metrics dashboard
- [ ] Set up error rate alarm
- [ ] Set up duration alarm
- [ ] Configure log retention (to manage costs)
- [ ] Create custom dashboard
- [ ] (Optional) Enable X-Ray tracing

### Key Metrics to Watch

1. **Error rate** - Should be near 0%
2. **Duration** - Should be consistent
3. **Throttles** - Should be 0
4. **Concurrent executions** - Monitor against limits
5. **Iterator age** - For stream processing

## Next Topic

Continue to [Well-Architected Framework](./21-well-architected-framework.md) to learn AWS best practices for building reliable systems.
