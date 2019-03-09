/*Full
 line comment*/
 
--Inner Join
SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
FROM Orders, Customers
WHERE Orders.customerID = Customers.customerID;

--Limit in number of rows.
SELECT *
FROM Orders
LIMIT 2;

--Alias
select o.price
FROM Orders AS o
WHERE o.price > 10;


--Operators
SELECT COUNT(*)
FROM Customers;

SELECT SUM(Orders.OrderID)
FROM Orders;

SELECT MIN(Orders.OrderID)
FROM Orders;

SELECT MAX(Orders.OrderID)
FROM Orders;

SELECT AVG(Orders.OrderID)
FROM Orders;

--Alter
--add column
Alter table Orders
add orderPrice money;

--drop column
Alter table Orders
drop column orderDate;

--add foreign key
Alter table Orders
add FOREIGN KEY(customerId) ReferencesCustomers(customerId);






  
