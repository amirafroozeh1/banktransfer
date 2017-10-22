
## Bank Transfer Assignment

**Amir Afroozeh** <amir.afroozeh@gmail.com>

This document describes my solution to the assignment, including the design, choice of technology, limitations and possible deployment instruction.


The transfer service is designed as a series of REST APIs, as described below:

```
POST /api/account
 
body:
{
	name: string
	balance: number
}

response:
{
	id: string (UUID)
}
```

Description: creates a new account using the given name and initial balance. Returns the id of the newly created account.

```
GET /api/account/{id}

response:
{
	name: string
	balance: number (BigDecimal)
}
```

Description: returns the account with the given id, or error if the account is not found.

```
GET /api/account/search/{name}

{
	Accounts :  object (Set<Account>)
}
```

Description: returns the set of account with the given name.

```
POST /api/transfer

body:
{
	from: accountId (UUID)
	to: accountId   (UUID) 
	amount: number
	description: string
}

response:
{
	id: string (UUID)
}               
```

Description: executes a bank transfer of a given amount from an account to another, and returns the transfer id.

```
GET /api/transfer/{id}

response: 
{
	from:
	to:
	amount:
	status:
	description:
}
```

Description: returns the information about the bank transfer with the given id. The status can be successful or failed.


### Architecture

In order to solve this task, I chose a simple MVC design pattern (there is no presentation layer). The main controller is responsible to handle REST requests (`GET`, `POST` requests) and calls corresponding services (`AccountService`, `TransferService`). 

- `AccountService` is an interface that provides the main functionality for working with accounts:

```
public interface AccountService {

    Account findById(UUID accountId);

    void addAccount(Account account);

    Set<Account> findByName(String name);

    boolean isValid(AccountJsonData accountJsonData);

    void deleteAll();

    BigDecimal totalBalance();
}
```

Note that I use Java's `BigDecimal` class for double calculations, in order to to not lose precision in withdraw and deposit operations.

- `TransferService` is an interface that provides the main functionality for working with transfers:

```
public interface TransferService {

    Transfer findTransferById(UUID transferId);

    void addTransfer(Transfer transfer);

    List<Transfer> findTransferByAccount(Account from);

    boolean isValid(TransferJsonData transferJsonData);

    Transfer executeTransfer(Account from, Account to, BigDecimal amount, String description);

    void deleteAll();
}

```

At the moment there is just one implementation for each of these interfaces.

For the assignment, I decided to store accounts and transfers in-memory. For accessing the account and transfer objects, I use the `AccountRepository` and `TransferRepository` interfaces, which at the moment have only one implementation, an in-memory map from id (a `UUID` to `Account` and `Transfer`). 


The main functionality of the transfer is implemented in the `executeTransfer` method, which withdraws the money from an account and deposits it into another account. To avoid concurrency problems (race conditions), I synchronized the `withdraw` and `deposit` methods of Accounts.  


### Technology Choices

I chose Spring boot which is a powerful framework to create web service application. For testing, I used JUnit and Mokito.

### Limitations:

Currently, I store the data in memory and use Java `synchronized` for dealing with concurrency issues. There was a possibility to use a relational database such Oracle or Mysql, and implement transfer using database transactions. 

Due to lack of time, I could not use Async and non-blocking approaches and frameworks (reactive programming, future, promise) like Play framework.

Also, given more time, I would have added more unit and integration tests.


### Deployment

The project is build using Maven. To run the tests, you can use `mvn test`. Moreover, there is a main application `AssignmentApplication` that can be used to start the server (at `localhost:8080`) and use postman to test the REST API.


