# Aeropulse

Aeropulse is a full stack application for managing flight notifications, featuring a React front-end and a Spring Boot back-end.

## Prerequisites

- Java 8 or higher
- Maven
- Node.js and npm
- MySQL
- Twilio account (for SMS notifications)
- Email account (for sending emails)

## Backend Setup (Spring Boot)

### Configuration

1. **Clone the repository**:

    ```sh
    git clone https://github.com/yourusername/aeropulse.git
    cd aeropulse
    ```

2. **Create a `.env` file**:

   Copy the `.env.example` file to `.env`:

    ```sh
    cp .env.example .env
    ```

3. **Fill in your configuration values**:

   Open the `.env` file and fill in your database credentials, Twilio credentials, and email configuration:

    ```sh
    DB_USERNAME=your_db_username
    DB_PASSWORD=your_db_password
    TWILIO_ACCOUNT_SID=your_twilio_account_sid
    TWILIO_AUTH_TOKEN=your_twilio_auth_token
    TWILIO_PHONE_NUMBER=your_twilio_phone_number
    MAIL_HOST=smtp.gmail.com
    MAIL_PORT=587
    MAIL_USERNAME=your_mail_username
    MAIL_PASSWORD=your_mail_password
    ```

### Running the Backend

Use the following command to start the Spring Boot application:

```sh
./mvnw spring-boot:run
