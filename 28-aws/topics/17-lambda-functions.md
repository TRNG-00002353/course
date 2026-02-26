# Creating a Lambda Function

## Your First Lambda Function

Let's create a simple Lambda function step by step using the AWS Console.

---

## Creating a Function via Console

### Step 1: Navigate to Lambda

```
AWS Console → Search "Lambda" → Lambda Dashboard
```

### Step 2: Create Function

```
Lambda → Functions → Create function

1. Choose: Author from scratch
2. Function name: my-first-function
3. Runtime: Python 3.12 (or your preferred language)
4. Architecture: x86_64
5. Permissions: Create a new role with basic Lambda permissions
6. Click "Create function"
```

### Step 3: Write Your Code

The default code editor opens. Replace with:

```python
import json

def lambda_handler(event, context):
    # Log the incoming event
    print("Received event:", json.dumps(event))

    # Get name from event or use default
    name = event.get('name', 'World')

    # Return response
    return {
        'statusCode': 200,
        'body': json.dumps({
            'message': f'Hello, {name}!'
        })
    }
```

### Step 4: Deploy

Click the **Deploy** button to save your changes.

### Step 5: Test Your Function

```
1. Click "Test" button
2. Create new test event:
   - Event name: test-event
   - Event JSON:
     {
       "name": "Student"
     }
3. Click "Test"
```

**Expected output:**
```json
{
  "statusCode": 200,
  "body": "{\"message\": \"Hello, Student!\"}"
}
```

---

## Understanding the Handler

The handler is your function's entry point.

```python
def lambda_handler(event, context):
    #    │              │       │
    #    │              │       └── Runtime information
    #    │              └── Input data (trigger payload)
    #    └── Function name (configurable)
```

### Event Object

Contains the input data from the trigger.

```python
# API Gateway event example
event = {
    "httpMethod": "GET",
    "path": "/users",
    "queryStringParameters": {"id": "123"},
    "body": None
}

# S3 event example
event = {
    "Records": [{
        "s3": {
            "bucket": {"name": "my-bucket"},
            "object": {"key": "uploads/file.txt"}
        }
    }]
}
```

### Context Object

Provides runtime information.

```python
def lambda_handler(event, context):
    # Useful context properties
    print(context.function_name)        # my-first-function
    print(context.memory_limit_in_mb)   # 128
    print(context.get_remaining_time_in_millis())  # Time left
    print(context.aws_request_id)       # Unique request ID
```

---

## Lambda Function in Different Languages

### Node.js

```javascript
// index.mjs (ES module)
export const handler = async (event) => {
    const name = event.name || 'World';

    return {
        statusCode: 200,
        body: JSON.stringify({
            message: `Hello, ${name}!`
        })
    };
};
```

### Java

```java
package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

public class Handler implements RequestHandler<Map<String, String>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(Map<String, String> event, Context context) {
        String name = event.getOrDefault("name", "World");

        return Map.of(
            "statusCode", 200,
            "body", String.format("{\"message\": \"Hello, %s!\"}", name)
        );
    }
}
```

### Python

```python
import json

def lambda_handler(event, context):
    name = event.get('name', 'World')

    return {
        'statusCode': 200,
        'body': json.dumps({'message': f'Hello, {name}!'})
    }
```

---

## Configuring Your Function

### General Configuration

```
Lambda → Your function → Configuration → General configuration

Memory: 128 MB - 10,240 MB (CPU scales with memory)
Timeout: 3 seconds (default) - 15 minutes (max)
```

### Memory and CPU Relationship

```
Memory → CPU Allocation
┌─────────────────────────────────────────────────────┐
│ 128 MB   → Minimal CPU                             │
│ 1,769 MB → 1 vCPU equivalent                       │
│ 3,538 MB → 2 vCPU equivalent                       │
│ 10,240 MB → 6 vCPU equivalent                      │
│                                                     │
│ Tip: More memory = more CPU = faster execution     │
│      Sometimes paying for more memory is cheaper!  │
└─────────────────────────────────────────────────────┘
```

### Environment Variables

Store configuration without hardcoding.

```
Configuration → Environment variables → Edit

Key: DATABASE_URL
Value: jdbc:postgresql://mydb.example.com:5432/mydb

Key: LOG_LEVEL
Value: INFO
```

Access in code:

```python
import os

def lambda_handler(event, context):
    db_url = os.environ.get('DATABASE_URL')
    log_level = os.environ.get('LOG_LEVEL', 'INFO')
```

---

## IAM Roles and Permissions

Lambda needs permissions to access other AWS services.

### Execution Role

```
Lambda Function
      │
      ▼
 IAM Execution Role
      │
      ├── CloudWatch Logs (write logs)
      ├── S3 (if accessing buckets)
      ├── DynamoDB (if accessing tables)
      └── Other services as needed
```

### Basic Execution Role Policy

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "logs:CreateLogGroup",
                "logs:CreateLogStream",
                "logs:PutLogEvents"
            ],
            "Resource": "arn:aws:logs:*:*:*"
        }
    ]
}
```

### Adding Permissions

```
Lambda → Your function → Configuration → Permissions

Option 1: Add managed policy
  - AmazonS3ReadOnlyAccess
  - AmazonDynamoDBReadOnlyAccess

Option 2: Add inline policy
  - Custom permissions for specific resources
```

---

## Deploying Code

### Method 1: Console Editor

Best for small functions and quick testing.

```
Lambda → Your function → Code → Edit inline
```

### Method 2: Upload ZIP File

For larger functions or dependencies.

```bash
# Create deployment package
zip -r function.zip .

# Upload via console:
Lambda → Your function → Upload from → .zip file
```

### Method 3: AWS CLI

```bash
# Deploy code
aws lambda update-function-code \
    --function-name my-first-function \
    --zip-file fileb://function.zip
```

### Method 4: Container Image

For large dependencies or custom runtimes.

```dockerfile
FROM public.ecr.aws/lambda/python:3.12

COPY app.py ${LAMBDA_TASK_ROOT}
COPY requirements.txt .
RUN pip install -r requirements.txt

CMD ["app.lambda_handler"]
```

---

## Adding Dependencies

### Python

```bash
# Install to local directory
pip install requests -t ./package

# Create zip with dependencies
cd package
zip -r ../deployment.zip .
cd ..
zip deployment.zip lambda_function.py
```

### Node.js

```bash
# Initialize and install
npm init -y
npm install axios

# Zip entire directory
zip -r function.zip .
```

### Using Lambda Layers

Layers let you share code across functions.

```
Lambda → Layers → Create layer

1. Name: my-python-dependencies
2. Upload zip with dependencies
3. Compatible runtimes: Python 3.12

Attach to function:
Lambda → Your function → Layers → Add a layer
```

---

## Logging and Debugging

### CloudWatch Logs

All `print()` statements and errors go to CloudWatch.

```python
def lambda_handler(event, context):
    print("INFO: Processing request")  # Goes to CloudWatch
    print(f"Event: {event}")

    try:
        result = process_data(event)
        print(f"SUCCESS: {result}")
        return result
    except Exception as e:
        print(f"ERROR: {str(e)}")
        raise
```

### Viewing Logs

```
Lambda → Your function → Monitor → View CloudWatch logs

Or:
CloudWatch → Log groups → /aws/lambda/my-first-function
```

### Log Format

```
START RequestId: abc-123 Version: $LATEST
INFO: Processing request
Event: {"name": "Student"}
SUCCESS: {"statusCode": 200, ...}
END RequestId: abc-123
REPORT RequestId: abc-123
    Duration: 15.00 ms
    Billed Duration: 16 ms
    Memory Size: 128 MB
    Max Memory Used: 50 MB
```

---

## Testing Strategies

### Console Testing

```
Lambda → Your function → Test

Create test events for different scenarios:
- Happy path
- Missing parameters
- Error cases
```

### Local Testing with SAM CLI

```bash
# Install SAM CLI
pip install aws-sam-cli

# Create test event
sam local generate-event apigateway aws-proxy > event.json

# Invoke locally
sam local invoke MyFunction -e event.json
```

### Unit Testing (Python)

```python
# test_lambda.py
import unittest
from lambda_function import lambda_handler

class TestLambda(unittest.TestCase):
    def test_with_name(self):
        event = {'name': 'Test'}
        result = lambda_handler(event, None)
        self.assertEqual(result['statusCode'], 200)
        self.assertIn('Test', result['body'])

    def test_without_name(self):
        event = {}
        result = lambda_handler(event, None)
        self.assertIn('World', result['body'])

if __name__ == '__main__':
    unittest.main()
```

---

## Function Versions and Aliases

### Versions

Immutable snapshots of your function.

```
Lambda → Your function → Versions → Publish new version

Version 1 ← Immutable snapshot
Version 2 ← Another snapshot
$LATEST   ← Current working version
```

### Aliases

Named pointers to versions.

```
Aliases
┌─────────────────────────────────────────────────────┐
│ prod  ──────► Version 5                            │
│ stage ──────► Version 6                            │
│ dev   ──────► $LATEST                              │
└─────────────────────────────────────────────────────┘

Usage: arn:aws:lambda:region:account:function:my-func:prod
```

---

## Best Practices

### Code Best Practices

```python
# ✓ Initialize outside handler (reused across invocations)
import boto3
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('my-table')

def lambda_handler(event, context):
    # ✓ Handler only contains request-specific logic
    return table.get_item(Key={'id': event['id']})

# ✗ Don't initialize inside handler
def bad_handler(event, context):
    dynamodb = boto3.resource('dynamodb')  # Created every time!
    table = dynamodb.Table('my-table')
    return table.get_item(Key={'id': event['id']})
```

### Configuration Best Practices

```
✓ Use environment variables for configuration
✓ Set appropriate timeout (not too long, not too short)
✓ Right-size memory (test to find optimal)
✓ Enable X-Ray for tracing (debugging)
✓ Use Layers for shared dependencies
```

---

## Summary

| Concept | Description |
|---------|-------------|
| **Handler** | Entry point function for your code |
| **Event** | Input data from trigger |
| **Context** | Runtime information |
| **Execution Role** | IAM permissions for the function |
| **Environment Variables** | Configuration storage |
| **Layers** | Shared dependencies |

### Quick Start Checklist

- [ ] Create function in console
- [ ] Write handler code
- [ ] Configure memory and timeout
- [ ] Set up environment variables
- [ ] Add necessary IAM permissions
- [ ] Test with sample events
- [ ] Check CloudWatch logs

## Next Topic

Continue to [Lambda Triggers and Event Sources](./18-lambda-triggers.md) to learn how to invoke your functions automatically.
