# Amazon EBS (Elastic Block Store)

## What is EBS?

Amazon EBS provides persistent block storage for EC2 instances. Think of it as a virtual hard drive that you can attach to your EC2 instance.

```
EC2 Instance                    EBS Volume
┌─────────────────┐            ┌─────────────────┐
│                 │            │                 │
│   Application   │◄──────────►│   Storage       │
│   Running       │  attached  │   (Persistent)  │
│                 │            │                 │
└─────────────────┘            └─────────────────┘

Key Point: EBS data persists even when EC2 is stopped
```

---

## EBS vs Instance Store

EC2 instances can have two types of storage:

| Feature | EBS | Instance Store |
|---------|-----|----------------|
| **Persistence** | Data survives stop/start | Data lost on stop |
| **Detachable** | Yes, can move between instances | No, fixed to instance |
| **Backup** | Snapshots supported | No built-in backup |
| **Performance** | Configurable | Very fast (local) |
| **Cost** | Per GB/month | Included with instance |

**For beginners:** Always use EBS for important data.

---

## EBS Volume Types

### General Purpose SSD (gp3/gp2)

Best for most workloads.

```
gp3 Characteristics
┌─────────────────────────────────────────────────────┐
│ Base IOPS: 3,000                                    │
│ Base Throughput: 125 MB/s                           │
│ Max IOPS: 16,000                                    │
│ Max Throughput: 1,000 MB/s                          │
│                                                     │
│ Use for: Boot volumes, dev/test, small databases   │
│ Price: ~$0.08/GB/month                              │
└─────────────────────────────────────────────────────┘
```

### Provisioned IOPS SSD (io2/io1)

For high-performance databases.

```
io2 Characteristics
┌─────────────────────────────────────────────────────┐
│ Max IOPS: 64,000                                    │
│ Max Throughput: 1,000 MB/s                          │
│ Durability: 99.999%                                 │
│                                                     │
│ Use for: Large databases, latency-sensitive apps   │
│ Price: ~$0.125/GB/month + IOPS charges             │
└─────────────────────────────────────────────────────┘
```

### Throughput Optimized HDD (st1)

For large, sequential workloads.

```
st1 Characteristics
┌─────────────────────────────────────────────────────┐
│ Max Throughput: 500 MB/s                            │
│ Cannot be boot volume                               │
│                                                     │
│ Use for: Big data, data warehouses, log processing │
│ Price: ~$0.045/GB/month                             │
└─────────────────────────────────────────────────────┘
```

### Cold HDD (sc1)

For infrequently accessed data.

```
sc1 Characteristics
┌─────────────────────────────────────────────────────┐
│ Max Throughput: 250 MB/s                            │
│ Cannot be boot volume                               │
│ Lowest cost                                         │
│                                                     │
│ Use for: Archival, infrequent access               │
│ Price: ~$0.015/GB/month                             │
└─────────────────────────────────────────────────────┘
```

---

## Volume Type Comparison

| Type | Use Case | Max IOPS | Price |
|------|----------|----------|-------|
| **gp3** | General purpose | 16,000 | $0.08/GB |
| **gp2** | General purpose (older) | 16,000 | $0.10/GB |
| **io2** | High performance DB | 64,000 | $0.125/GB |
| **st1** | Big data | 500 | $0.045/GB |
| **sc1** | Cold storage | 250 | $0.015/GB |

**Recommendation:** Use gp3 for most workloads. It's newer and cheaper than gp2.

---

## Creating an EBS Volume

### Via AWS Console

```
EC2 → Volumes → Create Volume

1. Volume Type: gp3
2. Size: 20 GB
3. Availability Zone: Same as your EC2 (important!)
4. Encryption: Enable (recommended)
5. Add tags:
   - Name: my-data-volume
6. Create Volume
```

### Important: Availability Zone

EBS volumes must be in the same AZ as your EC2 instance.

```
✓ EC2 in us-east-1a  ←→  EBS in us-east-1a  (Works)
✗ EC2 in us-east-1a  ←→  EBS in us-east-1b  (Cannot attach)
```

---

## Attaching a Volume to EC2

### Step 1: Attach via Console

```
EC2 → Volumes → Select Volume → Actions → Attach Volume

Instance: Select your EC2 instance
Device: /dev/sdf (or suggested name)
```

### Step 2: Connect to EC2 and Format

```bash
# SSH into your EC2 instance
ssh -i your-key.pem ec2-user@your-ip

# List block devices
lsblk

# Example output:
# NAME    MAJ:MIN RM SIZE RO TYPE MOUNTPOINT
# xvda    202:0    0   8G  0 disk
# └─xvda1 202:1    0   8G  0 part /
# xvdf    202:80   0  20G  0 disk           <- New volume

# Format the new volume (only do this once!)
sudo mkfs -t ext4 /dev/xvdf

# Create mount point
sudo mkdir /data

# Mount the volume
sudo mount /dev/xvdf /data

# Verify
df -h /data
```

### Step 3: Auto-Mount on Reboot

```bash
# Get the UUID
sudo blkid /dev/xvdf
# Output: /dev/xvdf: UUID="abc123..." TYPE="ext4"

# Edit fstab
sudo nano /etc/fstab

# Add this line (use your UUID):
UUID=abc123...  /data  ext4  defaults,nofail  0  2

# Test the fstab entry
sudo mount -a
```

---

## EBS Snapshots

Snapshots are point-in-time backups of EBS volumes stored in S3.

### Creating a Snapshot

```
EC2 → Volumes → Select Volume → Actions → Create Snapshot

Description: "Daily backup 2024-01-15"
Add tags: Name = my-volume-backup
```

### Snapshot Features

```
EBS Volume                         S3 (Snapshot Storage)
┌─────────────────┐               ┌─────────────────┐
│   20 GB Data    │ ─────────────►│  Snapshot       │
│                 │   Create      │  (Incremental)  │
└─────────────────┘               └─────────────────┘
                                          │
                                          │ Create Volume
                                          ▼
                                  ┌─────────────────┐
                                  │  New EBS Volume │
                                  │  (Any AZ/Region)│
                                  └─────────────────┘
```

### Incremental Snapshots

Only changed data is stored after the first snapshot:

```
Day 1: Full snapshot (20 GB stored)
Day 2: Changed 2 GB (only 2 GB stored)
Day 3: Changed 1 GB (only 1 GB stored)

Total storage used: 23 GB (not 60 GB)
```

---

## Restoring from Snapshot

### Create Volume from Snapshot

```
EC2 → Snapshots → Select Snapshot → Actions → Create Volume

Volume Type: gp3
Size: 20 GB (or larger)
Availability Zone: Choose based on target EC2
Encryption: Enable
```

### Copy Snapshot to Another Region

```
EC2 → Snapshots → Select Snapshot → Actions → Copy

Destination Region: us-west-2
Encryption: Enable
```

---

## EBS Encryption

### Benefits

- Data encrypted at rest
- Data encrypted in transit (EC2 to EBS)
- Snapshots automatically encrypted
- Minimal performance impact

### Enabling Encryption

**For new volumes:**
```
When creating volume → Encryption: Enable
Select KMS key (default or custom)
```

**For existing volumes:**
1. Create snapshot of unencrypted volume
2. Copy snapshot with encryption enabled
3. Create new volume from encrypted snapshot
4. Replace old volume with new encrypted one

---

## Managing EBS Volumes

### Resize a Volume

```
EC2 → Volumes → Select Volume → Actions → Modify Volume

New Size: 50 GB (can only increase, not decrease)
```

After modifying, extend the filesystem:

```bash
# Check current size
lsblk

# Extend the partition (if needed)
sudo growpart /dev/xvdf 1

# Extend the filesystem
sudo resize2fs /dev/xvdf
# or for xfs:
sudo xfs_growfs /data

# Verify
df -h /data
```

### Delete a Volume

```
EC2 → Volumes → Select Volume → Actions → Delete Volume

Note: Volume must be detached first
      Data is permanently lost!
```

---

## Free Tier Limits

```
EBS Free Tier (12 months)
┌─────────────────────────────────────────────────────┐
│ Storage: 30 GB total (any combination of gp2/gp3)  │
│ Snapshots: 1 GB                                     │
│                                                     │
│ Warning: Going over 30 GB incurs charges           │
└─────────────────────────────────────────────────────┘
```

---

## Best Practices

### Do's

```
✓ Enable encryption for sensitive data
✓ Take regular snapshots
✓ Use gp3 instead of gp2 (newer, cheaper)
✓ Delete unused volumes and old snapshots
✓ Tag volumes for cost tracking
```

### Don'ts

```
✗ Store important data on instance store
✗ Forget to delete volumes when terminating EC2
✗ Keep unnecessary snapshots (costs money)
✗ Format a volume that already has data
```

---

## Common Commands Reference

```bash
# List block devices
lsblk

# Check disk usage
df -h

# Format new volume
sudo mkfs -t ext4 /dev/xvdf

# Mount volume
sudo mount /dev/xvdf /data

# Unmount volume
sudo umount /data

# Check filesystem
sudo file -s /dev/xvdf

# Extend filesystem after resize
sudo resize2fs /dev/xvdf
```

---

## Summary

| Concept | Description |
|---------|-------------|
| **EBS** | Persistent block storage for EC2 |
| **gp3** | Best general-purpose volume type |
| **Snapshot** | Backup stored in S3 |
| **Encryption** | Protects data at rest and in transit |
| **Availability Zone** | Volume must match EC2's AZ |

### EBS Checklist

- [ ] Choose appropriate volume type (gp3 for most cases)
- [ ] Place volume in same AZ as EC2
- [ ] Enable encryption
- [ ] Set up regular snapshots
- [ ] Configure auto-mount in fstab
- [ ] Monitor and delete unused volumes

## Next Topic

Continue to [CloudWatch Basics](./12-cloudwatch-basics.md) to learn how to monitor your AWS resources.
