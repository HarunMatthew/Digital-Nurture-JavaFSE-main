DROP TABLE Loans CASCADE CONSTRAINTS;
DROP TABLE Customers CASCADE CONSTRAINTS;

-- Enable Output
SET SERVEROUTPUT ON;

---

-- TABLE CREATION

CREATE TABLE Customers (
CustomerID NUMBER PRIMARY KEY,
Name VARCHAR2(100),
DOB DATE,
Balance NUMBER,
IsVIP VARCHAR2(5),
LastModified DATE
);

CREATE TABLE Loans (
LoanID NUMBER PRIMARY KEY,
CustomerID NUMBER,
LoanAmount NUMBER,
InterestRate NUMBER,
StartDate DATE,
EndDate DATE,
FOREIGN KEY (CustomerID)
REFERENCES Customers(CustomerID)
);


INSERT INTO Customers
VALUES (1,'John Doe',
TO_DATE('1955-05-15','YYYY-MM-DD'),
12000,'FALSE',SYSDATE);

INSERT INTO Customers
VALUES (2,'Jane Smith',
TO_DATE('1990-07-20','YYYY-MM-DD'),
8000,'FALSE',SYSDATE);

INSERT INTO Customers
VALUES (3,'Robert King',
TO_DATE('1950-03-10','YYYY-MM-DD'),
15000,'FALSE',SYSDATE);

INSERT INTO Loans
VALUES (1,1,50000,8,SYSDATE-100,SYSDATE+20);

INSERT INTO Loans
VALUES (2,2,30000,7,SYSDATE-200,SYSDATE+60);

INSERT INTO Loans
VALUES (3,3,70000,9,SYSDATE-300,SYSDATE+10);

COMMIT;

BEGIN


-- SCENARIO 1
-- Apply 1% discount for customers above 60 years
FOR cust_rec IN
(
    SELECT CustomerID,
           FLOOR(MONTHS_BETWEEN(SYSDATE,DOB)/12) Age
    FROM Customers
)
LOOP
    IF cust_rec.Age > 60 THEN
        UPDATE Loans
        SET InterestRate = InterestRate - 1
        WHERE CustomerID = cust_rec.CustomerID;

        DBMS_OUTPUT.PUT_LINE(
            'Interest discount applied to Customer ID: '
            || cust_rec.CustomerID
        );
    END IF;
END LOOP;

-- SCENARIO 2
-- Mark VIP customers
FOR cust_rec IN

(
    SELECT CustomerID, Balance
    FROM Customers
)
LOOP
    IF cust_rec.Balance > 10000 THEN
        UPDATE Customers
        SET IsVIP = 'TRUE'
        WHERE CustomerID = cust_rec.CustomerID;

        DBMS_OUTPUT.PUT_LINE(
            'Customer ID '
            || cust_rec.CustomerID
            || ' promoted to VIP.'
        );
    ELSE
        UPDATE Customers
        SET IsVIP = 'FALSE'
        WHERE CustomerID = cust_rec.CustomerID;
    END IF;
END LOOP;


FOR loan_rec IN
(
    SELECT l.LoanID,
           c.Name,
           l.EndDate
    FROM Loans l
    JOIN Customers c
    ON l.CustomerID = c.CustomerID
    WHERE l.EndDate BETWEEN SYSDATE
                        AND SYSDATE + 30
)
LOOP
    DBMS_OUTPUT.PUT_LINE(
        'Reminder: Dear '
        || loan_rec.Name
        || ', Loan ID '
        || loan_rec.LoanID
        || ' is due on '
        || TO_CHAR(loan_rec.EndDate,'DD-MON-YYYY')
    ); 
END LOOP;

COMMIT;

END;
/
