# Banking Management System 🏦

Banking Management System is a Java-based console application that demonstrates customer and account management, deposits, withdrawals, and transaction records using **Java, JPA, Hibernate, Maven, and MySQL**.

## Overview

The project models customers, bank accounts, and account transactions. Jakarta Persistence (JPA) defines the entity relationships, Hibernate provides the ORM implementation, and MySQL stores the application data. DAO classes separate persistence operations from the entity model.

The current entry point creates a sample customer and savings account, saves them, then attempts a deposit and withdrawal. This is a learning project and is not production-ready banking software.

## Features

### Customers and accounts
- Persist customer details including name, email, and phone number.
- Associate a customer with multiple accounts.
- Store account numbers, account types, and balances.
- Find a customer by ID.
- Use JPA relationships to link accounts to their owning customer.

### Deposits and withdrawals
- Deposit an amount into an account and update its balance.
- Check that an account has sufficient funds before a withdrawal.
- Record successful deposit and withdrawal operations as transaction entities.
- Associate transaction records with the relevant account.

### Persistence and request handling
- Use DAO classes to keep database operations separate from entity classes.
- Use JPA `EntityManager` and `EntityManagerFactory` to interact with the database.
- Configure persistence through `META-INF/persistence.xml`.
- Read database credentials from environment variables rather than hard-coding the password.
- Use Maven to manage dependencies and build the project.

## Technology Stack

| Technology | Role in the project |
|---|---|
| Java 25 | Application language (as configured in Maven) |
| Maven | Build and dependency management |
| Jakarta Persistence (JPA) | Persistence API and entity mapping |
| Hibernate ORM 7.4.5.Final | JPA implementation and ORM |
| MySQL | Relational database |
| MySQL Connector/J 9.7.0 | JDBC driver for MySQL |
| IntelliJ IDEA | Recommended IDE |

Dependencies and compiler configuration are declared in `pom.xml`.

## Application Architecture

The application follows a simple entity–DAO–persistence organization:

1. **Entity classes** represent customers, accounts, and transactions.
2. **DAO classes** perform database operations for customers, accounts, and transactions.
3. **JPA/Hibernate** maps Java entities to relational database tables.
4. **`persistence.xml`** defines the persistence unit and database connection.
5. **MySQL** stores the persistent customer, account, and transaction records.
6. **The main class** demonstrates creating sample data and invoking account operations.

### Application flow

For the sample execution:

1. The application creates an `EntityManager` from the `bank` persistence unit.
2. A customer and savings account are created and linked.
3. `CustomerDao` persists the customer and its associated account.
4. `AccountDao` attempts a deposit of 5,000.
5. `AccountDao` attempts a withdrawal of 2,000.
6. The entity manager and entity manager factory are closed.

The sample currently uses account ID `1` for the deposit and withdrawal calls, so it assumes the generated account ID is `1`.

## Project Structure

```text
Banking_Management_System/
├── pom.xml                           # Maven project configuration and dependencies
└── src/main/
    ├── java/
    │   ├── dao/
    │   │   ├── AccountDao.java       # Deposit and withdrawal operations
    │   │   ├── CustomerDao.java      # Save and find customers
    │   │   └── TransactionDao.java   # Query transactions by account
    │   ├── entity/
    │   │   ├── Account.java          # Account details and relationships
    │   │   ├── Customer.java         # Customer details and account collection
    │   │   └── Transaction.java      # Transaction details and account reference
    │   ├── org/
    │   │   └── main.java             # Sample application entry point
    │   └── util/
    │       └── JPAUtill.java         # EntityManagerFactory and EntityManager setup
    └── resources/
        └── META-INF/
            └── persistence.xml       # JPA persistence-unit configuration
```

## Operations and Application Flow

The main operations currently exposed by the DAO classes are:

| Class | Operation | Purpose |
|---|---|---|
| `CustomerDao` | `save(...)` | Persists a customer and commits the transaction |
| `CustomerDao` | `find(...)` | Finds a customer by ID |
| `AccountDao` | `deposit(...)` | Updates the account balance and stores a deposit record |
| `AccountDao` | `withdraw(...)` | Checks available balance, updates the balance when sufficient, and stores a withdrawal record |
| `TransactionDao` | `getTransactions(...)` | Retrieves transaction records for an account |
| `JPAUtill` | `getEntityManager()` | Creates an entity manager from the configured persistence unit |
| `JPAUtill` | `close()` | Closes the entity manager factory |

The exact method signatures are defined in the Java source files.

## Data Models

### Customer

Defined in `entity/Customer.java`.

| Field | Purpose |
|---|---|
| `customerId` | Generated customer identifier |
| `name` | Customer name |
| `email` | Customer email address |
| `phone` | Customer phone number |
| `accounts` | One-to-many relationship to the customer's accounts |

The customer-to-account relationship uses `@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)`.

### Account

Defined in `entity/Account.java`.

| Field | Purpose |
|---|---|
| `accountId` | Generated account identifier |
| `accountNumber` | Unique account number |
| `accountType` | Account type, such as Savings |
| `balance` | Current account balance |
| `customer` | Many-to-one relationship to the owning customer |
| `transactions` | One-to-many relationship to account transactions |

The account references its customer through a `@ManyToOne` mapping and `customer_id` join column. The account-to-transaction relationship is configured with cascade operations.

### Transaction

Defined in `entity/Transaction.java`.

| Field | Purpose |
|---|---|
| `transactionId` | Generated transaction identifier |
| `type` | Transaction type, such as Deposit or Withdraw |
| `amount` | Transaction amount |
| `transactionDate` | Date and time initialized when the transaction is created |
| `account` | Many-to-one relationship to the associated account |

## Validation and Transaction Handling

- **Customer persistence:** `CustomerDao` persists a customer inside a JPA transaction.
- **Deposit:** `AccountDao` adds the amount to the account balance and persists a deposit transaction.
- **Withdrawal:** `AccountDao` checks whether the balance is sufficient before subtracting the amount and persisting a withdrawal transaction.
- **Transaction history:** `TransactionDao` uses a JPQL query to retrieve transactions by account ID.
- **Credential configuration:** `JPAUtill` reads `BANK_DB_PASSWORD` and reads `BANK_DB_USERNAME`, which defaults to `root`.

The current code should be strengthened before it is used beyond a learning example. In particular, validate that amounts are positive, handle missing accounts, roll back transactions on exceptions, and use `BigDecimal` instead of `double` for monetary values.

## Configuration

The persistence unit is named `bank` in `src/main/resources/META-INF/persistence.xml`. It connects to:

```text
jdbc:mysql://localhost:3306/bankdb
```

Hibernate schema auto-update and SQL logging are enabled in the current development configuration.

### Environment variables

Set the database credentials in the environment used to launch the application.

**Windows PowerShell**
```powershell
$env:BANK_DB_USERNAME = "root"
$env:BANK_DB_PASSWORD = "your_mysql_password"
```

**macOS/Linux**
```bash
export BANK_DB_USERNAME=root
export BANK_DB_PASSWORD='your_mysql_password'
```

Replace the example password with your local MySQL password. Do not commit real credentials to the repository.

## Setup and Running

### Prerequisites
- JDK 25, matching the Maven compiler configuration.
- Maven.
- A running MySQL server.
- An IDE such as IntelliJ IDEA, or a terminal for building the project.

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/gayatri-tonde/Banking_Management_System.git
   cd Banking_Management_System
   ```

2. Create the database in MySQL:

   ```sql
   CREATE DATABASE bankdb;
   ```

3. Set `BANK_DB_USERNAME` and `BANK_DB_PASSWORD` as described in the Configuration section.

4. Compile the project from its root directory:

   ```bash
   mvn clean compile
   ```

5. Open the project in IntelliJ IDEA, import it as a Maven project, select JDK 25, and configure the environment variables in the run configuration.

6. Run the main method in `src/main/java/org/main.java`.

The sample main class creates a customer named John and a savings account with an initial balance of 10,000, then attempts a deposit of 5,000 and a withdrawal of 2,000.

## Important Limitations and Improvements

- The project is a demonstration and does not implement production banking security or authorization.
- The main method assumes the account ID is `1`; generated IDs should be retrieved and used instead of hard-coded.
- Amounts are not comprehensively validated for zero or negative values.
- Missing-account handling and rollback behavior should be improved.
- `double` can introduce rounding errors for money; use `BigDecimal`.
- Concurrent account updates need suitable locking or optimistic versioning.
- Add automated tests for customer persistence, deposits, withdrawals, insufficient funds, and transaction history.
- Consider adding a service layer, input validation, structured logging, and database migrations.
- Review cascade behavior carefully to avoid unintended deletion of financial history.

## Troubleshooting

- **Password environment variable error:** Set `BANK_DB_PASSWORD` in the terminal or IDE run configuration.
- **Cannot connect to MySQL:** Check that MySQL is running and listening on port `3306`.
- **Unknown database `bankdb`:** Create the database before launching the application.
- **Access denied:** Verify the MySQL username, password, and permissions.
- **Maven or compiler errors:** Confirm that JDK 25 is selected and Maven is using that JDK.
- **Account not found during the sample operations:** Do not assume the generated account ID is `1`; retrieve the actual ID after persistence.

## Author

**Gayatri Tonde**

- GitHub: [@gayatri-tonde](https://github.com/gayatri-tonde)
- Repository: [gayatri-tonde/Banking_Management_System](https://github.com/gayatri-tonde/Banking_Management_System)

---

Built as a Java persistence project to practice JPA entity relationships, Hibernate ORM, DAO design, and relational database operations.
