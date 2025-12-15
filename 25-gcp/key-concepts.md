# Google Cloud Platform Key Concepts for Application Developers

## Overview

This document covers essential Google Cloud Platform (GCP) concepts for building, deploying, and managing cloud-native applications. GCP provides a comprehensive suite of cloud services including compute, storage, databases, networking, and managed services.

---

## 1. GCP Core Architecture & Concepts

### Why It Matters
- Understand cloud organization and billing
- Proper resource hierarchy prevents issues
- Security and access control foundation
- Cost management and optimization

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Organization | Top-level entity | Company-wide root |
| Folder | Group projects | Team/dept organization |
| Project | Resource container | Billing and isolation |
| Resource | Services/instances | Actual workloads |
| Region | Geographic location | Data residency, latency |
| Zone | Isolated location | High availability |

### Resource Hierarchy
```
Organization (example.com)
├── Folder (Engineering)
│   ├── Project (app-dev)
│   │   ├── Compute Engine VMs
│   │   ├── Cloud Storage Buckets
│   │   └── Cloud SQL Instances
│   ├── Project (app-staging)
│   └── Project (app-production)
├── Folder (Marketing)
│   └── Project (website)
└── Folder (Shared Services)
    └── Project (monitoring)
```

### Regions and Zones
```
Region: us-central1 (Iowa)
├── Zone: us-central1-a
├── Zone: us-central1-b
├── Zone: us-central1-c
└── Zone: us-central1-f

Region: europe-west1 (Belgium)
├── Zone: europe-west1-b
├── Zone: europe-west1-c
└── Zone: europe-west1-d
```

---

## 2. Compute Services

### Why It Matters
- Run application workloads
- Choose right compute option for use case
- Balance cost, performance, and management
- Scale applications based on demand

### Compute Options

| Service | Type | Use Case |
|---------|------|----------|
| Compute Engine | VMs (IaaS) | Full control, custom configs |
| Google Kubernetes Engine | Containers (CaaS) | Microservices, orchestration |
| Cloud Run | Serverless containers | Stateless HTTP services |
| Cloud Functions | Serverless functions (FaaS) | Event-driven, short tasks |
| App Engine | PaaS | Web apps, auto-scaling |

### Compute Engine (VMs)
```bash
# Create VM instance
gcloud compute instances create my-vm \
  --zone=us-central1-a \
  --machine-type=e2-medium \
  --image-family=ubuntu-2204-lts \
  --image-project=ubuntu-os-cloud \
  --boot-disk-size=20GB \
  --tags=http-server,https-server

# List instances
gcloud compute instances list

# SSH into instance
gcloud compute ssh my-vm --zone=us-central1-a

# Stop instance
gcloud compute instances stop my-vm --zone=us-central1-a

# Start instance
gcloud compute instances start my-vm --zone=us-central1-a

# Delete instance
gcloud compute instances delete my-vm --zone=us-central1-a

# Create instance with startup script
gcloud compute instances create web-server \
  --zone=us-central1-a \
  --machine-type=e2-medium \
  --metadata-from-file startup-script=startup.sh
```

**Startup Script Example:**
```bash
#!/bin/bash
apt-get update
apt-get install -y nginx
systemctl start nginx
systemctl enable nginx
```

### Google Kubernetes Engine (GKE)
```bash
# Create cluster
gcloud container clusters create my-cluster \
  --zone=us-central1-a \
  --num-nodes=3 \
  --machine-type=e2-medium \
  --enable-autoscaling \
  --min-nodes=1 \
  --max-nodes=5

# Get credentials
gcloud container clusters get-credentials my-cluster --zone=us-central1-a

# Now use kubectl
kubectl get nodes
kubectl apply -f deployment.yaml

# Upgrade cluster
gcloud container clusters upgrade my-cluster --zone=us-central1-a

# Resize cluster
gcloud container clusters resize my-cluster --num-nodes=5 --zone=us-central1-a

# Delete cluster
gcloud container clusters delete my-cluster --zone=us-central1-a
```

### Cloud Run (Serverless Containers)
```bash
# Deploy container
gcloud run deploy myapp \
  --image=gcr.io/project-id/myapp:latest \
  --platform=managed \
  --region=us-central1 \
  --allow-unauthenticated \
  --port=8080 \
  --memory=512Mi \
  --cpu=1 \
  --max-instances=10

# Deploy from source
gcloud run deploy myapp \
  --source=. \
  --region=us-central1 \
  --allow-unauthenticated

# List services
gcloud run services list

# Update service
gcloud run services update myapp \
  --region=us-central1 \
  --memory=1Gi

# Delete service
gcloud run services delete myapp --region=us-central1
```

**Dockerfile for Cloud Run:**
```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install --production
COPY . .
EXPOSE 8080
CMD ["node", "server.js"]
```

### Cloud Functions
```bash
# Deploy function (Node.js)
gcloud functions deploy helloWorld \
  --runtime=nodejs18 \
  --trigger-http \
  --allow-unauthenticated \
  --entry-point=helloWorld \
  --region=us-central1

# Deploy function (Python)
gcloud functions deploy processData \
  --runtime=python311 \
  --trigger-topic=my-topic \
  --entry-point=process_data \
  --region=us-central1

# List functions
gcloud functions list

# View logs
gcloud functions logs read helloWorld

# Delete function
gcloud functions delete helloWorld
```

**Function Example (Node.js):**
```javascript
exports.helloWorld = (req, res) => {
  const name = req.query.name || req.body.name || 'World';
  res.status(200).send(`Hello ${name}!`);
};
```

---

## 3. Storage Services

### Why It Matters
- Store application data, files, and backups
- Different storage types for different needs
- Cost optimization strategies
- Data durability and availability

### Storage Options

| Service | Type | Use Case |
|---------|------|----------|
| Cloud Storage | Object storage | Files, backups, static content |
| Persistent Disk | Block storage | VM storage |
| Filestore | File storage (NFS) | Shared file systems |

### Cloud Storage
```bash
# Create bucket
gsutil mb gs://my-unique-bucket-name

# Create bucket with specific location and storage class
gsutil mb -l us-central1 -c standard gs://my-bucket

# List buckets
gsutil ls

# Upload file
gsutil cp file.txt gs://my-bucket/
gsutil cp -r ./directory gs://my-bucket/

# Download file
gsutil cp gs://my-bucket/file.txt ./

# List bucket contents
gsutil ls gs://my-bucket/
gsutil ls -r gs://my-bucket/**

# Delete file
gsutil rm gs://my-bucket/file.txt

# Delete bucket
gsutil rm -r gs://my-bucket/

# Make file public
gsutil acl ch -u AllUsers:R gs://my-bucket/file.txt

# Sync directory
gsutil rsync -r ./local-dir gs://my-bucket/remote-dir/

# Set lifecycle policy
gsutil lifecycle set lifecycle.json gs://my-bucket/
```

**Storage Classes:**
| Class | Availability | Min Storage | Use Case |
|-------|--------------|-------------|----------|
| Standard | High | None | Frequently accessed |
| Nearline | 99.9% | 30 days | Monthly access |
| Coldline | 99.9% | 90 days | Quarterly access |
| Archive | 99.9% | 365 days | Long-term backup |

**Lifecycle Policy Example:**
```json
{
  "lifecycle": {
    "rule": [
      {
        "action": {"type": "SetStorageClass", "storageClass": "NEARLINE"},
        "condition": {"age": 30}
      },
      {
        "action": {"type": "SetStorageClass", "storageClass": "COLDLINE"},
        "condition": {"age": 90}
      },
      {
        "action": {"type": "Delete"},
        "condition": {"age": 365}
      }
    ]
  }
}
```

### Using Cloud Storage in Applications

**Node.js Example:**
```javascript
const {Storage} = require('@google-cloud/storage');
const storage = new Storage();

// Upload file
async function uploadFile(filename, bucketName) {
  await storage.bucket(bucketName).upload(filename, {
    destination: filename,
    metadata: {
      contentType: 'image/jpeg',
    }
  });
  console.log(`${filename} uploaded to ${bucketName}`);
}

// Download file
async function downloadFile(filename, bucketName) {
  await storage.bucket(bucketName).file(filename).download({
    destination: `/tmp/${filename}`
  });
  console.log(`${filename} downloaded`);
}

// List files
async function listFiles(bucketName) {
  const [files] = await storage.bucket(bucketName).getFiles();
  files.forEach(file => console.log(file.name));
}

// Generate signed URL (temporary access)
async function generateSignedUrl(bucketName, filename) {
  const [url] = await storage
    .bucket(bucketName)
    .file(filename)
    .getSignedUrl({
      version: 'v4',
      action: 'read',
      expires: Date.now() + 15 * 60 * 1000, // 15 minutes
    });
  return url;
}
```

---

## 4. Database Services

### Why It Matters
- Managed databases reduce operational overhead
- Choose right database for data model
- High availability and backup built-in
- Scaling without manual intervention

### Database Options

| Service | Type | Use Case |
|---------|------|----------|
| Cloud SQL | Relational (MySQL, PostgreSQL, SQL Server) | Traditional apps |
| Cloud Spanner | Distributed SQL | Global scale, strong consistency |
| Firestore | NoSQL document | Mobile/web apps, real-time |
| Bigtable | NoSQL wide-column | Analytics, time-series |
| Memorystore | In-memory (Redis, Memcached) | Caching |

### Cloud SQL (PostgreSQL)
```bash
# Create instance
gcloud sql instances create my-postgres \
  --database-version=POSTGRES_15 \
  --tier=db-f1-micro \
  --region=us-central1 \
  --root-password=secure-password \
  --backup-start-time=03:00

# Create database
gcloud sql databases create myapp --instance=my-postgres

# Create user
gcloud sql users create myuser \
  --instance=my-postgres \
  --password=user-password

# Connect to instance
gcloud sql connect my-postgres --user=postgres

# List instances
gcloud sql instances list

# Backup instance
gcloud sql backups create --instance=my-postgres

# List backups
gcloud sql backups list --instance=my-postgres

# Restore from backup
gcloud sql backups restore BACKUP_ID --instance=my-postgres
```

**Connecting from Application:**
```javascript
// Node.js with Cloud SQL Proxy
const { Pool } = require('pg');

const pool = new Pool({
  host: '/cloudsql/project:region:instance',
  user: 'myuser',
  password: 'user-password',
  database: 'myapp',
  max: 5,
});

async function query(text, params) {
  const result = await pool.query(text, params);
  return result.rows;
}
```

### Firestore (NoSQL)
```javascript
const admin = require('firebase-admin');
admin.initializeApp();
const db = admin.firestore();

// Create document
async function createUser(userId, data) {
  await db.collection('users').doc(userId).set({
    name: data.name,
    email: data.email,
    createdAt: admin.firestore.FieldValue.serverTimestamp()
  });
}

// Read document
async function getUser(userId) {
  const doc = await db.collection('users').doc(userId).get();
  if (!doc.exists) {
    return null;
  }
  return doc.data();
}

// Query collection
async function getActiveUsers() {
  const snapshot = await db.collection('users')
    .where('active', '==', true)
    .orderBy('createdAt', 'desc')
    .limit(10)
    .get();

  return snapshot.docs.map(doc => ({
    id: doc.id,
    ...doc.data()
  }));
}

// Update document
async function updateUser(userId, updates) {
  await db.collection('users').doc(userId).update(updates);
}

// Delete document
async function deleteUser(userId) {
  await db.collection('users').doc(userId).delete();
}

// Real-time listener
function listenToUser(userId, callback) {
  return db.collection('users').doc(userId).onSnapshot(doc => {
    callback(doc.data());
  });
}
```

---

## 5. Networking

### Why It Matters
- Control traffic flow and security
- Connect resources securely
- Expose applications to internet
- Implement hybrid cloud connectivity

### Key Concepts

| Service | Purpose | Use Case |
|---------|---------|----------|
| VPC | Virtual network | Resource isolation |
| Subnet | Network segment | Regional resources |
| Firewall Rules | Traffic control | Security |
| Load Balancing | Distribute traffic | High availability |
| Cloud NAT | Outbound internet | Private instances |
| VPN/Interconnect | Hybrid connectivity | Connect to on-prem |

### VPC and Subnets
```bash
# Create VPC network
gcloud compute networks create my-vpc \
  --subnet-mode=custom \
  --bgp-routing-mode=regional

# Create subnet
gcloud compute networks subnets create my-subnet \
  --network=my-vpc \
  --region=us-central1 \
  --range=10.0.1.0/24

# List networks
gcloud compute networks list

# List subnets
gcloud compute networks subnets list
```

### Firewall Rules
```bash
# Allow HTTP traffic
gcloud compute firewall-rules create allow-http \
  --network=my-vpc \
  --allow=tcp:80 \
  --source-ranges=0.0.0.0/0 \
  --target-tags=http-server

# Allow HTTPS traffic
gcloud compute firewall-rules create allow-https \
  --network=my-vpc \
  --allow=tcp:443 \
  --source-ranges=0.0.0.0/0 \
  --target-tags=https-server

# Allow SSH from specific IP
gcloud compute firewall-rules create allow-ssh \
  --network=my-vpc \
  --allow=tcp:22 \
  --source-ranges=203.0.113.0/24

# Allow internal traffic
gcloud compute firewall-rules create allow-internal \
  --network=my-vpc \
  --allow=tcp:0-65535,udp:0-65535,icmp \
  --source-ranges=10.0.0.0/8

# List firewall rules
gcloud compute firewall-rules list

# Delete firewall rule
gcloud compute firewall-rules delete allow-http
```

### Load Balancing
```bash
# Create HTTP load balancer

# 1. Create instance group
gcloud compute instance-groups managed create web-servers \
  --base-instance-name=web \
  --size=2 \
  --template=web-server-template \
  --zone=us-central1-a

# 2. Create health check
gcloud compute health-checks create http http-health-check \
  --port=80 \
  --request-path=/health

# 3. Create backend service
gcloud compute backend-services create web-backend \
  --protocol=HTTP \
  --health-checks=http-health-check \
  --global

# 4. Add instance group to backend
gcloud compute backend-services add-backend web-backend \
  --instance-group=web-servers \
  --instance-group-zone=us-central1-a \
  --global

# 5. Create URL map
gcloud compute url-maps create web-map \
  --default-service=web-backend

# 6. Create HTTP proxy
gcloud compute target-http-proxies create http-proxy \
  --url-map=web-map

# 7. Create forwarding rule
gcloud compute forwarding-rules create http-forwarding-rule \
  --global \
  --target-http-proxy=http-proxy \
  --ports=80
```

---

## 6. Identity and Access Management (IAM)

### Why It Matters
- Control who can access what
- Principle of least privilege
- Secure service-to-service communication
- Audit access and changes

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Principal | Who | user@example.com, service account |
| Role | What permissions | Editor, Viewer, Custom |
| Resource | Where | Project, bucket, VM |
| Policy | Binding of above | User X has role Y on resource Z |

### Common Roles

| Role | Permissions | Use Case |
|------|-------------|----------|
| Owner | Full control | Project owner |
| Editor | Modify resources | Developers |
| Viewer | Read-only | Auditors |
| Service Account User | Use service accounts | Application access |
| Storage Object Admin | Manage storage | File uploads |
| Cloud SQL Client | Connect to databases | Application DB access |

### IAM Commands
```bash
# Grant role to user
gcloud projects add-iam-policy-binding my-project \
  --member=user:alice@example.com \
  --role=roles/editor

# Grant role to service account
gcloud projects add-iam-policy-binding my-project \
  --member=serviceAccount:my-sa@my-project.iam.gserviceaccount.com \
  --role=roles/storage.objectAdmin

# List IAM policy
gcloud projects get-iam-policy my-project

# Remove role
gcloud projects remove-iam-policy-binding my-project \
  --member=user:alice@example.com \
  --role=roles/editor

# Grant role on specific bucket
gsutil iam ch user:alice@example.com:objectViewer gs://my-bucket
```

### Service Accounts
```bash
# Create service account
gcloud iam service-accounts create my-service-account \
  --display-name="My Service Account"

# List service accounts
gcloud iam service-accounts list

# Create key
gcloud iam service-accounts keys create key.json \
  --iam-account=my-sa@my-project.iam.gserviceaccount.com

# Grant role to service account
gcloud projects add-iam-policy-binding my-project \
  --member=serviceAccount:my-sa@my-project.iam.gserviceaccount.com \
  --role=roles/storage.objectAdmin

# Delete service account
gcloud iam service-accounts delete my-sa@my-project.iam.gserviceaccount.com
```

**Using Service Account in Application:**
```javascript
// Set environment variable
// export GOOGLE_APPLICATION_CREDENTIALS="/path/to/key.json"

const {Storage} = require('@google-cloud/storage');
const storage = new Storage(); // Automatically uses service account

// Or explicitly
const storage = new Storage({
  projectId: 'my-project',
  keyFilename: '/path/to/key.json'
});
```

---

## 7. gcloud CLI - Command Line Tool

### Why It Matters
- Primary tool for GCP management
- Automate deployments and operations
- Script infrastructure management
- Essential for DevOps workflows

### Installation
```bash
# Install gcloud CLI
# Visit: https://cloud.google.com/sdk/docs/install

# Initialize
gcloud init

# Authenticate
gcloud auth login

# Set default project
gcloud config set project my-project-id

# Set default region/zone
gcloud config set compute/region us-central1
gcloud config set compute/zone us-central1-a
```

### Configuration Management
```bash
# List configurations
gcloud config configurations list

# Create new configuration
gcloud config configurations create dev
gcloud config configurations create prod

# Activate configuration
gcloud config configurations activate dev

# Set properties
gcloud config set project dev-project
gcloud config set compute/region us-west1

# View all properties
gcloud config list

# Unset property
gcloud config unset compute/zone
```

### Common Commands by Service

**Compute Engine:**
```bash
# VMs
gcloud compute instances list
gcloud compute instances create vm-name
gcloud compute instances stop vm-name
gcloud compute instances delete vm-name

# Disks
gcloud compute disks list
gcloud compute disks create disk-name --size=100GB

# Images
gcloud compute images list
gcloud compute images create my-image --source-disk=my-disk
```

**Kubernetes Engine:**
```bash
# Clusters
gcloud container clusters list
gcloud container clusters create cluster-name
gcloud container clusters get-credentials cluster-name
gcloud container clusters delete cluster-name

# Node pools
gcloud container node-pools list --cluster=cluster-name
gcloud container node-pools create pool-name --cluster=cluster-name
```

**Cloud Run:**
```bash
# Services
gcloud run services list
gcloud run deploy service-name --image=image-url
gcloud run services delete service-name

# Revisions
gcloud run revisions list
```

**Storage:**
```bash
# Using gsutil
gsutil ls
gsutil mb gs://bucket-name
gsutil cp file.txt gs://bucket-name/
gsutil rm gs://bucket-name/file.txt
```

**Cloud SQL:**
```bash
# Instances
gcloud sql instances list
gcloud sql instances create instance-name
gcloud sql instances delete instance-name

# Databases
gcloud sql databases list --instance=instance-name
gcloud sql databases create db-name --instance=instance-name

# Users
gcloud sql users list --instance=instance-name
gcloud sql users create user-name --instance=instance-name --password=pass
```

### Output Formats
```bash
# Default table format
gcloud compute instances list

# JSON output
gcloud compute instances list --format=json

# YAML output
gcloud compute instances list --format=yaml

# CSV output
gcloud compute instances list --format="csv(name,zone,machineType)"

# Custom format
gcloud compute instances list \
  --format="table(name,zone,status,networkInterfaces[0].networkIP)"

# Filter results
gcloud compute instances list --filter="zone:us-central1-a"
gcloud compute instances list --filter="status=RUNNING"
```

---

## Quick Reference Card

### Essential Commands
```bash
# Setup
gcloud init
gcloud auth login
gcloud config set project PROJECT_ID

# Compute Engine
gcloud compute instances create VM_NAME --zone=ZONE
gcloud compute instances list
gcloud compute ssh VM_NAME

# GKE
gcloud container clusters create CLUSTER_NAME
gcloud container clusters get-credentials CLUSTER_NAME
kubectl get nodes

# Cloud Run
gcloud run deploy SERVICE --image=IMAGE --region=REGION
gcloud run services list

# Storage
gsutil mb gs://BUCKET_NAME
gsutil cp FILE gs://BUCKET_NAME/
gsutil ls gs://BUCKET_NAME/

# Cloud SQL
gcloud sql instances create INSTANCE_NAME --database-version=POSTGRES_15
gcloud sql databases create DB_NAME --instance=INSTANCE_NAME

# IAM
gcloud projects add-iam-policy-binding PROJECT_ID \
  --member=user:USER_EMAIL \
  --role=ROLE

# Info
gcloud compute regions list
gcloud compute zones list
gcloud compute machine-types list
```

### Project Structure Example
```
my-app/
├── app/
│   ├── Dockerfile
│   ├── package.json
│   └── src/
├── terraform/
│   ├── main.tf
│   ├── variables.tf
│   └── outputs.tf
├── kubernetes/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ingress.yaml
└── cloudbuild.yaml
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand GCP resource hierarchy
- [ ] Choose appropriate compute service for application
- [ ] Deploy applications to Compute Engine, GKE, and Cloud Run
- [ ] Store and retrieve files from Cloud Storage
- [ ] Set up and connect to Cloud SQL databases
- [ ] Use Firestore for NoSQL data
- [ ] Configure VPC networks and firewall rules
- [ ] Implement IAM policies and service accounts
- [ ] Use gcloud CLI effectively
- [ ] Deploy containerized applications
- [ ] Set up load balancers
- [ ] Manage costs and billing
- [ ] Implement security best practices
- [ ] Monitor and log applications

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 26: DevOps](../26-devops/) - Automate deployments with CI/CD
- Practice deploying full-stack applications
- Learn Terraform for infrastructure as code
- Explore Cloud Monitoring and Logging
- Study cost optimization strategies
- Implement multi-region deployments for high availability
