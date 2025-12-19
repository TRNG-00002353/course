# Assignment 2: Shell Scripting for Automation

## Objective
Apply Linux and shell scripting knowledge to create practical automation scripts.

## Instructions

### Part 1: System Information Script (30 points)

Create a script named `sysinfo.sh` that displays:

1. **System Details**
   - Current date and time
   - Logged-in username and hostname
   - Current working directory
   - Operating system information

2. **Resource Usage**
   - Disk usage summary (using `df -h`)
   - Memory usage (using `free -h`)

3. **Output Format**
   - Add a header banner to the output
   - Format output with clear labels
   - Use variables to store and display information

### Part 2: File Organizer Script (40 points)

Create a script named `organizer.sh` that organizes files by extension:

1. **Script Functionality**
   - Takes a directory path as an argument
   - Creates subdirectories: `images/`, `documents/`, `scripts/`, `others/`
   - Moves files based on their extension:
     - `.jpg`, `.png`, `.gif`, `.svg` → `images/`
     - `.txt`, `.pdf`, `.doc`, `.md` → `documents/`
     - `.sh`, `.py`, `.js` → `scripts/`
     - All other files → `others/`

2. **Error Handling**
   - Check if the directory argument is provided
   - Verify the directory exists before processing
   - Display a summary of files moved to each folder

### Part 3: Backup Script (30 points)

Create a script named `backup.sh` that:

1. **Arguments**
   - Takes source directory as first argument
   - Takes destination directory as second argument

2. **Backup Process**
   - Creates a timestamped backup folder (format: `backup_YYYYMMDD_HHMMSS`)
   - Copies all files from source to the backup folder

3. **Output**
   - Displays backup location
   - Shows number of files copied
   - Reports total size of backup

## Requirements

- All scripts must start with `#!/bin/bash`
- Include comments explaining the code
- Make scripts executable with `chmod +x`
- Test scripts before submission

## Starter Template

```bash
#!/bin/bash

# Script Name: scriptname.sh
# Description: Brief description
# Author: Your Name
# Date: YYYY-MM-DD

# Your code here
```

## Submission Requirements

Submit a folder containing:
- `sysinfo.sh` - System information script
- `organizer.sh` - File organizer script
- `backup.sh` - Backup script
- `README.md` - Instructions on how to run each script

## Grading Criteria

| Criteria | Points |
|----------|--------|
| System Information Script | 30 |
| File Organizer Script | 40 |
| Backup Script | 30 |
| **Total** | **100** |

## Due Date
Submit by end of weekend (Sunday 11:59 PM)
