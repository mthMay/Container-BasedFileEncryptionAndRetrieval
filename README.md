# Container-BasedFileEncryptionAndRetrieval
### Description
The Container-Based File Encryption and Retrieval system is designed to securely manage and retrieve files while ensuring robust user management and data integrity. The application leverages Docker containers for distributed processing, allowing for scalable and secure file handling. Users can manage files through a user-friendly interface that emulates a terminal for ease of use.

### Features
1. User Management
2. Database Integration
3. File Management
4. Encryption/Decryptio
5. File Storage
6. Emulation of a Terminal
7. Remote Terminal Emulation
8. Permission Management
9. File Chunking
10. Distributed Docker Containers
11. Audit Trail
12. Access Control
13. Recovery
14. File Metadata
15. Scalability

### How to Run
#### Requirements
• Ensure that Docker and Docker Compose is installed.<br>
• Install Java Development Kit (JDK) version 11 or higher.

#### Instructions
1. Clone the Repository<br>
   ```git clone https://github.com/mthMay/Container-BasedFileEncryptionAndRetrieval.git```
2. Navigate to the project directory<br>
   ```cd Container-BasedFileEncryptionAndRetrieval```
3. Build the Project<br>
   ```mvn clean install```
4. Start the Application<br>
   ```docker-compose up```
5. Access the Application<br>
   Open your web browser and navigate to http://localhost:<port> (replace <port> with the appropriate port defined in the Docker configuration).

### How to Use
1. **User Registration**: Click on the registration button and fill out the necessary information to create an account.
2. **File Upload/Download**: Use the file management interface to upload or download files. View file metadata and manage files as needed.
3. **Terminal Emulation**: Access the terminal interface to execute selected commands.
4. **Acess Controls**: Set permissions for files and folders based on your needs.
5. **Audit Trail**: Review the audit logs to monitor file access and user activities.
6. **Recovery Options**: Utilize the recovery features to restore lost files.
