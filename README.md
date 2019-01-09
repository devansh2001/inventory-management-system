# inventory-management-system
This is a JavaFX-based Inventory Management System. Users can add, delete, and modify data.
Searching and sorting on different parameters is also possible. 
Java is used for the operations and GUI, while mySQL used for the backend.

------------------------------------------------------------------------------------------------------------------------------------

For this application, the username and password for mySQL is set as 'root' and 'rootroot' respectively.
This can be changed easily.

------------------------------------------------------------------------------------------------------------------------------------

To get started, create a database in mySQL with the name 'inventory' by running the following command:
  CREATE DATABASE inventory;

Further, to enter this database, run:
  USE inventory;

Next, create a table titled 'items' with several columns by running:
  CREATE TABLE items (
	  uniqueID int,
	  type varchar(30),
	  manufacturer varchar(30),
	  note varchar(100),
	  price decimal(5,2),
	  quantity int,
	  PRIMARY KEY(uniqueID)
);

------------------------------------------------------------------------------------------------------------------------------------

With this, you are ready to get started!
