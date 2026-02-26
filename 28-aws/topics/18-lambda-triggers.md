# Lambda Triggers and Event Sources

## What Are Triggers?

Triggers are AWS services or events that automatically invoke your Lambda function. Instead of calling Lambda directly, you configure services to trigger it when something happens.

```
                    Triggers
┌────────────────────────────────────────────────────────┐
│                                                        │
│  HTTP Request ──► API Gateway ──┐                     │
│  File Upload ───► S3 ───────────┼──► Lambda Function  │
│  DB Change ─────► DynamoDB ─────┤                     │
│  Schedule ──────► EventBridge ──┤                     │
│  Message ───────► SQS ──────────┘                     │
│                                                        │
└────────────────────────────────────────────────────────┘
```

---

## Types of Invocations

### Synchronous Invocation

Caller waits for response.

```
Client ──request──► Lambda ──response──► Client
                      │
              (waits for completion)

Examples: API Gateway, direct invoke
```

### Asynchronous Invocation

Caller doesn't wait; Lambda handles retries.

```
Client ──request──► Lambda Queue ──► Lambda
         │
    (returns immediately)

Examples: S3, SNS, EventBridge
Retries: 2 automatic retries on failure
```

### Poll-Based Invocation

Lambda polls the source for records.

```
SQS/Kinesis/DynamoDB Streams
         │
    Lambda polls for records
         │
         ▼
      Lambda

Examples: SQS, Kinesis, DynamoDB Streams
```

---

## Common Trigger Types

### 1. API Gateway (HTTP Requests)

Create REST or HTTP APIs that invoke Lambda.

```
Internet ──► API Gateway ──► Lambda
                │
        Routes:
        GET /users
        POST /users
        GET /users/{id}
```

### 2. S3 (File Events)

Trigger on file uploads, deletions, etc.

```
User uploads file ──► S3 Bucket ──► Lambda
                                      │
                          Process file (resize, analyze)
```

### 3. DynamoDB Streams

React to database changes.

```
Application ──► DynamoDB Table
                    │
               Stream enabled
                    │
                    ▼
                 Lambda
                    │
        React to inserts/updates/deletes
```

### 4. EventBridge (CloudWatch Events)

Schedule functions or react to AWS events.

```
Schedule (cron) ──► EventBridge ──► Lambda
AWS Events ────────────┘
Custom Events ─────────┘
```

### 5. SQS (Message Queue)

Process messages from a queue.

```
Producer ──► SQS Queue ──► Lambda Consumer
                │
          Messages processed
          in batches
```

### 6. SNS (Notifications)

Fan-out to multiple functions.

```
                    ┌──► Lambda 1
Publisher ──► SNS ──┼──► Lambda 2
                    └──► Lambda 3
```

---

## Setting Up API Gateway Trigger

### Creating an HTTP API

```
Lambda → Your function → Add trigger

Trigger: API Gateway
API type: HTTP API (simpler, cheaper)
Security: Open (for testing) or IAM
CORS: Enable if frontend calls it
```

### Result

```
Endpoint created:
https://abc123.execute-api.us-east-1.amazonaws.com/my-function

Test:
curl https://abc123.execute-api.us-east-1.amazonaws.com/my-function
```

### Handling API Gateway Events

```python
def lambda_handler(event, context):
    # API Gateway HTTP API event structure
    http_method = event.get('requestContext', {}).get('http', {}).get('method')
    path = event.get('rawPath')
    query_params = event.get('queryStringParameters') or {}
    body = event.get('body')

    # Process request
    if http_method == 'GET':
        return {
            'statusCode': 200,
            'headers': {'Content-Type': 'application/json'},
            'body': json.dumps({'message': 'GET request received'})
        }
    elif http_method == 'POST':
        data = json.loads(body) if body else {}
        return {
            'statusCode': 201,
            'body': json.dumps({'created': data})
        }
```

---

## Setting Up S3 Trigger

### Configuration

```
Lambda → Your function → Add trigger

Trigger: S3
Bucket: my-bucket
Event types:
  - All object create events
  - Or specific: PUT, POST, Copy
Prefix: uploads/  (optional - only trigger for this folder)
Suffix: .jpg      (optional - only trigger for JPG files)
```

### Handling S3 Events

```python
import boto3
import urllib.parse

s3_client = boto3.client('s3')

def lambda_handler(event, context):
    # Get bucket and key from event
    for record in event['Records']:
        bucket = record['s3']['bucket']['name']
        key = urllib.parse.unquote_plus(record['s3']['object']['key'])

        print(f"Processing file: s3://{bucket}/{key}")

        # Download file
        response = s3_client.get_object(Bucket=bucket, Key=key)
        content = response['Body'].read()

        # Process file...
        process_file(content)

    return {'statusCode': 200}
```

### S3 Event Structure

```json
{
  "Records": [
    {
      "eventSource": "aws:s3",
      "eventName": "ObjectCreated:Put",
      "s3": {
        "bucket": {
          "name": "my-bucket"
        },
        "object": {
          "key": "uploads/image.jpg",
          "size": 1024
        }
      }
    }
  ]
}
```

---

## Setting Up Scheduled Trigger (EventBridge)

### Configuration

```
Lambda → Your function → Add trigger

Trigger: EventBridge (CloudWatch Events)
Rule: Create a new rule
Rule name: daily-cleanup
Schedule expression: rate(1 day)
  OR
Schedule expression: cron(0 8 * * ? *)  # 8 AM UTC daily
```

### Schedule Expressions

| Expression | Meaning |
|------------|---------|
| `rate(1 minute)` | Every minute |
| `rate(5 minutes)` | Every 5 minutes |
| `rate(1 hour)` | Every hour |
| `rate(1 day)` | Every day |
| `cron(0 12 * * ? *)` | Every day at 12:00 PM UTC |
| `cron(0 8 ? * MON-FRI *)` | Weekdays at 8 AM UTC |

### Cron Format

```
cron(Minutes Hours Day-of-month Month Day-of-week Year)

Examples:
cron(0 10 * * ? *)     # 10:00 AM every day
cron(0 18 ? * MON-FRI *) # 6:00 PM weekdays
cron(0 8 1 * ? *)      # 8:00 AM first day of month
```

### Handling Scheduled Events

```python
def lambda_handler(event, context):
    print("Scheduled task running")

    # Perform cleanup, reports, etc.
    cleanup_old_records()
    send_daily_report()

    return {'statusCode': 200, 'body': 'Task completed'}
```

---

## Setting Up SQS Trigger

### Configuration

```
Lambda → Your function → Add trigger

Trigger: SQS
SQS queue: Select your queue
Batch size: 10 (process up to 10 messages at once)
Batch window: 0 (invoke immediately)
```

### Handling SQS Events

```python
def lambda_handler(event, context):
    for record in event['Records']:
        # Get message body
        body = record['body']
        message = json.loads(body)

        print(f"Processing message: {message}")

        # Process message...
        process_message(message)

    # Messages automatically deleted after successful processing
    return {'statusCode': 200}
```

### SQS Event Structure

```json
{
  "Records": [
    {
      "messageId": "abc123",
      "body": "{\"orderId\": \"12345\"}",
      "attributes": {
        "SentTimestamp": "1234567890"
      }
    }
  ]
}
```

---

## Setting Up DynamoDB Streams Trigger

### Enable Streams on Table

```
DynamoDB → Your table → Exports and streams → DynamoDB stream details

Stream enabled: Yes
View type: New and old images
```

### Add Trigger

```
Lambda → Your function → Add trigger

Trigger: DynamoDB
Table: my-table
Batch size: 100
Starting position: Latest
```

### Handling DynamoDB Events

```python
def lambda_handler(event, context):
    for record in event['Records']:
        event_name = record['eventName']  # INSERT, MODIFY, REMOVE

        if event_name == 'INSERT':
            new_item = record['dynamodb']['NewImage']
            print(f"New item: {new_item}")

        elif event_name == 'MODIFY':
            old_item = record['dynamodb']['OldImage']
            new_item = record['dynamodb']['NewImage']
            print(f"Modified: {old_item} → {new_item}")

        elif event_name == 'REMOVE':
            old_item = record['dynamodb']['OldImage']
            print(f"Deleted: {old_item}")

    return {'statusCode': 200}
```

---

## Multiple Triggers

A single Lambda function can have multiple triggers.

```
                    ┌── API Gateway (HTTP)
                    │
Lambda Function ◄───┼── S3 (File upload)
                    │
                    └── EventBridge (Schedule)
```

### Handling Multiple Event Types

```python
def lambda_handler(event, context):
    # Determine event source
    if 'httpMethod' in event or 'requestContext' in event:
        return handle_api_request(event)

    elif 'Records' in event:
        first_record = event['Records'][0]

        if first_record.get('eventSource') == 'aws:s3':
            return handle_s3_event(event)

        elif first_record.get('eventSource') == 'aws:sqs':
            return handle_sqs_event(event)

        elif first_record.get('eventSource') == 'aws:dynamodb':
            return handle_dynamodb_event(event)

    elif 'source' in event and event['source'] == 'aws.events':
        return handle_scheduled_event(event)

    return {'statusCode': 400, 'body': 'Unknown event type'}
```

---

## Event Source Comparison

| Source | Invocation | Retries | Batch | Use Case |
|--------|------------|---------|-------|----------|
| **API Gateway** | Sync | None | No | REST APIs |
| **S3** | Async | 2 | No | File processing |
| **EventBridge** | Async | 2 | No | Scheduling, events |
| **SQS** | Poll | Configurable | Yes | Message processing |
| **DynamoDB** | Poll | Until success | Yes | Data sync |
| **SNS** | Async | 2 | No | Notifications |

---

## Error Handling by Trigger Type

### Synchronous (API Gateway)

```python
def lambda_handler(event, context):
    try:
        result = process_request(event)
        return {
            'statusCode': 200,
            'body': json.dumps(result)
        }
    except ValueError as e:
        return {
            'statusCode': 400,
            'body': json.dumps({'error': str(e)})
        }
    except Exception as e:
        return {
            'statusCode': 500,
            'body': json.dumps({'error': 'Internal error'})
        }
```

### Asynchronous (S3, SNS)

Failed events go to a Dead Letter Queue (DLQ) after retries.

```
Lambda → Configuration → Asynchronous invocation

Dead-letter queue: Select SQS queue
Maximum retries: 2
Maximum age: 6 hours
```

### Poll-Based (SQS)

Message returns to queue on failure; set up DLQ on the queue.

```
SQS Queue → Dead-letter queue settings

DLQ: my-dlq
Max receives: 3 (message sent to DLQ after 3 failures)
```

---

## Best Practices

### Choose the Right Trigger

```
Need real-time API?     → API Gateway
Processing files?       → S3
Scheduled tasks?        → EventBridge
Decoupled processing?   → SQS
React to data changes?  → DynamoDB Streams
Fan-out notifications?  → SNS
```

### Error Handling

```
✓ Set up Dead Letter Queues
✓ Handle partial batch failures (SQS)
✓ Return proper status codes (API Gateway)
✓ Log errors for debugging
```

### Performance

```
✓ Process batches efficiently
✓ Use appropriate batch sizes
✓ Consider concurrency limits
✓ Warm up for latency-sensitive triggers
```

---

## Summary

| Trigger | Event Type | Best For |
|---------|------------|----------|
| **API Gateway** | Sync | REST APIs, webhooks |
| **S3** | Async | File processing |
| **EventBridge** | Async | Scheduling, AWS events |
| **SQS** | Poll | Message queues |
| **DynamoDB** | Poll | Data replication |
| **SNS** | Async | Fan-out patterns |

### Setup Checklist

- [ ] Choose appropriate trigger type
- [ ] Configure trigger parameters
- [ ] Handle event structure correctly
- [ ] Set up error handling/DLQ
- [ ] Test with realistic events
- [ ] Monitor in CloudWatch

## Next Topic

Continue to [Lambda with S3 and API Gateway](./19-lambda-s3-api-gateway.md) for a practical example combining these services.
