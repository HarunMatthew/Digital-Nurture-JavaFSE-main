
SET SERVEROUTPUT ON;

CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary NUMBER,
    Department VARCHAR2(50),
    HireDate DATE
); 

INSERT INTO Employees VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15','YYYY-MM-DD'));
INSERT INTO Employees VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20','YYYY-MM-DD'));
INSERT INTO Employees VALUES (3, 'Charlie Lee', 'Analyst', 50000, 'Finance', TO_DATE('2018-09-10','YYYY-MM-DD'));

INSERT INTO Accounts VALUES (1, 1, 'Savings', 12000, SYSDATE);
INSERT INTO Accounts VALUES (2, 2, 'Checking', 8000, SYSDATE);
INSERT INTO Accounts VALUES (3, 3, 'Savings', 15000, SYSDATE);

BEGIN

--------------------------------------------------
-- SCENARIO 1: Monthly Interest (1% for Savings)
--------------------------------------------------
FOR acc_rec IN (
    SELECT AccountID, Balance, AccountType
    FROM Accounts
)
LOOP
    IF acc_rec.AccountType = 'Savings' THEN
        UPDATE Accounts
        SET Balance = Balance + (Balance * 0.01),
            LastModified = SYSDATE
        WHERE AccountID = acc_rec.AccountID;

        DBMS_OUTPUT.PUT_LINE(
            'Interest added for Account ID: ' || acc_rec.AccountID
        );
    END IF;
END LOOP;

--------------------------------------------------
-- SCENARIO 2: Employee Bonus by Department
--------------------------------------------------
-- (Using 10% bonus as example; you can change value if needed)

FOR emp_rec IN (
    SELECT EmployeeID, Department, Salary
    FROM Employees
)
LOOP
    IF emp_rec.Department = 'IT' THEN
        UPDATE Employees
        SET Salary = Salary + (Salary * 0.10)
        WHERE EmployeeID = emp_rec.EmployeeID;

        DBMS_OUTPUT.PUT_LINE(
            'Bonus added for Employee ID: ' || emp_rec.EmployeeID
        );
    END IF;
END LOOP;

--------------------------------------------------
-- SCENARIO 3: Transfer Funds Between Accounts
--------------------------------------------------
DECLARE
    v_balance NUMBER;
BEGIN
    FOR t IN (
        SELECT 1 AS from_acc, 2 AS to_acc, 1000 AS amt FROM dual
    )
    LOOP
        SELECT Balance INTO v_balance
        FROM Accounts
        WHERE AccountID = t.from_acc;

        IF v_balance >= t.amt THEN

            UPDATE Accounts
            SET Balance = Balance - t.amt,
                LastModified = SYSDATE
            WHERE AccountID = t.from_acc;

            UPDATE Accounts
            SET Balance = Balance + t.amt,
                LastModified = SYSDATE
            WHERE AccountID = t.to_acc;

            DBMS_OUTPUT.PUT_LINE('Transfer Successful');

        ELSE
            DBMS_OUTPUT.PUT_LINE('Insufficient Balance');
        END IF;
    END LOOP;
END;

--------------------------------------------------

COMMIT;

END;
/