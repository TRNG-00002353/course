# Lambda with S3 and API Gateway

## Building a Practical Serverless Application

This guide walks through building a complete serverless application that combines Lambda, S3, and API Gateway.

---

## Project: Image Upload API

We'll build an API that:
1. Accepts image uploads via API Gateway
2. Stores images in S3
3. Returns the image URL

```
Architecture
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  Client ──POST /upload──► API Gateway ──► Lambda            │
│                                              │              │
│                                              ▼              │
│                                         S3 Bucket           │
│                                         (images)            │
│                                              │              │
│  Client ◄──image URL────── API Gateway ◄────┘              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Step 1: Create S3 Bucket

### Create the Bucket

```
S3 → Create bucket

Bucket name: my-images-bucket-unique-name  (must be globally unique)
Region: us-east-1
Block Public Access: Uncheck "Block all public access" (for public images)
Acknowledge: Check the warning
Create bucket
```

### Configure Bucket Policy (Public Read)

```
S3 → Your bucket → Permissions → Bucket policy

{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicReadGetObject",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::my-images-bucket-unique-name/*"
        }
    ]
}
```

---

## Step 2: Create Lambda Function

### Create Function

```
Lambda → Create function

Name: image-upload-handler
Runtime: Python 3.12
Permissions: Create new role
```

### Add S3 Permissions

```
Lambda → image-upload-handler → Configuration → Permissions
→ Click on the role name → Add permissions → Attach policies

Add: AmazonS3FullAccess (for learning; use specific permissions in production)
```

### Lambda Code

```python
import json
import boto3
import base64
import uuid
from datetime import datetime

s3_client = boto3.client('s3')
BUCKET_NAME = 'my-images-bucket-unique-name'  # Replace with your bucket

def lambda_handler(event, context):
    try:
        # Parse request
        if event.get('isBase64Encoded'):
            body = base64.b64decode(event['body'])
        else:
            body = event.get('body', '').encode()

        # Get content type from headers
        headers = event.get('headers', {})
        content_type = headers.get('content-type', 'image/jpeg')

        # Generate unique filename
        extension = get_extension(content_type)
        filename = f"uploads/{datetime.now().strftime('%Y/%m/%d')}/{uuid.uuid4()}{extension}"

        # Upload to S3
        s3_client.put_object(
            Bucket=BUCKET_NAME,
            Key=filename,
            Body=body,
            ContentType=content_type
        )

        # Generate URL
        image_url = f"https://{BUCKET_NAME}.s3.amazonaws.com/{filename}"

        return {
            'statusCode': 200,
            'headers': {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            'body': json.dumps({
                'message': 'Image uploaded successfully',
                'url': image_url,
                'key': filename
            })
        }

    except Exception as e:
        print(f"Error: {str(e)}")
        return {
            'statusCode': 500,
            'headers': {'Content-Type': 'application/json'},
            'body': json.dumps({'error': str(e)})
        }

def get_extension(content_type):
    extensions = {
        'image/jpeg': '.jpg',
        'image/png': '.png',
        'image/gif': '.gif',
        'image/webp': '.webp'
    }
    return extensions.get(content_type, '.bin')
```

---

## Step 3: Create API Gateway

### Create HTTP API

```
API Gateway → Create API → HTTP API → Build

API name: image-upload-api
```

### Add Route

```
Routes → Create

Method: POST
Path: /upload
Integration: Lambda
Lambda function: image-upload-handler
```

### Configure for Binary Data

```
API Gateway → Your API → API settings

Binary media types: Add
  - image/jpeg
  - image/png
  - image/gif
  - image/*
```

### Deploy API

```
Deploy → Stage: $default

Endpoint: https://abc123.execute-api.us-east-1.amazonaws.com
```

---

## Step 4: Test the API

### Using cURL

```bash
# Upload an image
curl -X POST \
  https://abc123.execute-api.us-east-1.amazonaws.com/upload \
  -H "Content-Type: image/jpeg" \
  --data-binary @photo.jpg

# Response:
{
  "message": "Image uploaded successfully",
  "url": "https://my-images-bucket.s3.amazonaws.com/uploads/2024/01/15/abc123.jpg",
  "key": "uploads/2024/01/15/abc123.jpg"
}
```

### Using JavaScript (Frontend)

```javascript
async function uploadImage(file) {
  const response = await fetch('https://abc123.execute-api.us-east-1.amazonaws.com/upload', {
    method: 'POST',
    headers: {
      'Content-Type': file.type
    },
    body: file
  });

  const data = await response.json();
  console.log('Uploaded:', data.url);
  return data;
}

// Usage with file input
document.getElementById('fileInput').addEventListener('change', async (e) => {
  const file = e.target.files[0];
  const result = await uploadImage(file);
  document.getElementById('preview').src = result.url;
});
```

---

## Adding S3 Trigger for Processing

Now let's add automatic image processing when files are uploaded.

### Create Processing Lambda

```python
import boto3
import urllib.parse

s3_client = boto3.client('s3')

def lambda_handler(event, context):
    for record in event['Records']:
        bucket = record['s3']['bucket']['name']
        key = urllib.parse.unquote_plus(record['s3']['object']['key'])

        print(f"Processing: s3://{bucket}/{key}")

        # Example: Log file metadata
        response = s3_client.head_object(Bucket=bucket, Key=key)
        size = response['ContentLength']
        content_type = response['ContentType']

        print(f"Size: {size} bytes, Type: {content_type}")

        # Add your processing logic here:
        # - Generate thumbnails
        # - Extract metadata
        # - Update database
        # - Send notifications

    return {'statusCode': 200}
```

### Add S3 Trigger

```
Lambda → image-processor → Add trigger

Source: S3
Bucket: my-images-bucket-unique-name
Event type: All object create events
Prefix: uploads/  (only trigger for uploads folder)
```

---

## Complete Architecture

```
                                    ┌──────────────┐
                                    │ Processing   │
                                    │ Lambda       │
                                    └──────┬───────┘
                                           │ S3 Trigger
                                           │
Client ──► API Gateway ──► Upload Lambda ──► S3 Bucket
  │                                            │
  │                                            │
  └──────────── Image URL ◄────────────────────┘
```

---

## Alternative: Presigned URLs

For large files, use presigned URLs to upload directly to S3.

### Generate Presigned URL Lambda

```python
import boto3
import json
import uuid

s3_client = boto3.client('s3')
BUCKET_NAME = 'my-images-bucket'

def lambda_handler(event, context):
    # Generate unique key
    file_key = f"uploads/{uuid.uuid4()}"

    # Get content type from query params
    query_params = event.get('queryStringParameters') or {}
    content_type = query_params.get('contentType', 'image/jpeg')

    # Generate presigned URL
    presigned_url = s3_client.generate_presigned_url(
        'put_object',
        Params={
            'Bucket': BUCKET_NAME,
            'Key': file_key,
            'ContentType': content_type
        },
        ExpiresIn=300  # 5 minutes
    )

    return {
        'statusCode': 200,
        'headers': {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        'body': json.dumps({
            'uploadUrl': presigned_url,
            'key': file_key,
            'expiresIn': 300
        })
    }
```

### Client-Side Upload with Presigned URL

```javascript
async function uploadWithPresignedUrl(file) {
  // Step 1: Get presigned URL
  const response = await fetch(
    `https://your-api.execute-api.us-east-1.amazonaws.com/get-upload-url?contentType=${file.type}`
  );
  const { uploadUrl, key } = await response.json();

  // Step 2: Upload directly to S3
  await fetch(uploadUrl, {
    method: 'PUT',
    headers: {
      'Content-Type': file.type
    },
    body: file
  });

  // Step 3: Return the public URL
  return `https://my-images-bucket.s3.amazonaws.com/${key}`;
}
```

### Presigned URL Flow

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  1. Client ──GET /get-upload-url──► API Gateway → Lambda    │
│                                                             │
│  2. Lambda generates presigned URL                          │
│                                                             │
│  3. Client ◄── presigned URL ────────────────────           │
│                                                             │
│  4. Client ──PUT (file)──► S3 (direct upload)              │
│                                                             │
│  Benefits:                                                  │
│  - No Lambda payload limit                                  │
│  - Faster uploads                                           │
│  - Less Lambda cost                                         │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## CORS Configuration

### API Gateway CORS

```
API Gateway → Your API → Routes → OPTIONS /upload

Or enable CORS when creating:
API Gateway → CORS → Configure
  - Allow Origin: *
  - Allow Methods: POST, OPTIONS
  - Allow Headers: Content-Type
```

### S3 CORS (for presigned URLs)

```
S3 → Your bucket → Permissions → CORS

[
    {
        "AllowedHeaders": ["*"],
        "AllowedMethods": ["PUT", "GET"],
        "AllowedOrigins": ["*"],
        "ExposeHeaders": []
    }
]
```

---

## Best Practices

### Security

```
✓ Validate file types in Lambda
✓ Limit file sizes
✓ Use IAM roles, not access keys
✓ Enable S3 bucket versioning for recovery
✓ Use presigned URLs for large files
```

### File Validation

```python
ALLOWED_TYPES = ['image/jpeg', 'image/png', 'image/gif']
MAX_SIZE = 5 * 1024 * 1024  # 5 MB

def validate_upload(content_type, size):
    if content_type not in ALLOWED_TYPES:
        raise ValueError(f"Invalid file type: {content_type}")

    if size > MAX_SIZE:
        raise ValueError(f"File too large: {size} bytes")
```

### Cost Optimization

```
✓ Use S3 lifecycle rules to delete old files
✓ Use S3 Intelligent-Tiering for varying access patterns
✓ Compress images before storing
✓ Use CloudFront for frequently accessed images
```

---

## Troubleshooting

| Issue | Cause | Solution |
|-------|-------|----------|
| 403 Forbidden | S3 bucket policy | Check bucket permissions |
| CORS error | Missing CORS config | Configure API Gateway and S3 CORS |
| Binary garbled | Missing binary config | Add binary media types in API Gateway |
| Large file fails | Lambda payload limit | Use presigned URLs |
| Slow uploads | Going through Lambda | Use presigned URLs for direct S3 upload |

---

## Summary

| Component | Role |
|-----------|------|
| **API Gateway** | HTTP endpoint for clients |
| **Lambda (Upload)** | Process upload requests |
| **Lambda (Process)** | React to S3 events |
| **S3** | Store uploaded files |

### Checklist

- [ ] Create S3 bucket
- [ ] Configure bucket policy for public read (if needed)
- [ ] Create Lambda function
- [ ] Add S3 permissions to Lambda role
- [ ] Create API Gateway endpoint
- [ ] Configure binary media types
- [ ] Set up CORS
- [ ] (Optional) Add S3 trigger for processing
- [ ] Test end-to-end

## Next Topic

Continue to [Monitoring Lambda](./20-lambda-monitoring.md) to learn how to monitor and debug your serverless applications.
