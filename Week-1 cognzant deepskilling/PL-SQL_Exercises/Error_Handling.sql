SET SERVEROUTPUT ON;


-- SCENARIO 1: SafeTransferFunds


CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_from_acc IN NUMBER,
    p_to_acc   IN NUMBER,
    p_amount   IN NUMBER
) AS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = p_from_acc;

    IF v_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds.');
    END IF;

    UPDATE Accounts
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_from_acc;

    UPDATE Accounts
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_to_acc;

    DBMS_OUTPUT.PUT_LINE('Transfer Successful');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Invalid account.');
        ROLLBACK;

    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END;
/


-- SCENARIO 2: UpdateSalary


CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_emp_id IN NUMBER,
    p_percent IN NUMBER
) AS
    v_count NUMBER;
BEGIN

    SELECT COUNT(*) INTO v_count
    FROM Employees
    WHERE EmployeeID = p_emp_id;

    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Employee not found.');
    END IF;

    UPDATE Employees
    SET Salary = Salary + (Salary * p_percent / 100)
    WHERE EmployeeID = p_emp_id;

    DBMS_OUTPUT.PUT_LINE('Salary updated');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/


-- SCENARIO 3: AddNewCustomer

CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_id   IN NUMBER,
    p_name IN VARCHAR2,
    p_dob  IN DATE,
    p_bal  IN NUMBER
) AS
BEGIN

    INSERT INTO Customers (
        CustomerID, Name, DOB, Balance, LastModified
    )
    VALUES (
        p_id, p_name, p_dob, p_bal, SYSDATE
    );

    DBMS_OUTPUT.PUT_LINE('Customer inserted successfully');

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Duplicate Customer ID');

    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/


-- TEST EXECUTIONS


-- Safe Transfer
BEGIN
    SafeTransferFunds(1, 2, 1000);
END;
/

-- Salary Update
BEGIN
    UpdateSalary(1, 10);
END;
/

-- Add Customer
BEGIN
    AddNewCustomer(4, 'Test User', SYSDATE, 5000);
END;
/

