# First & Second Assignment

So all in all, this series of assignments is a continuity of work done to design, edit and maintain a real database, that holds AirBNB data for hostings, reservations, ratings and more.

We started in the first assignment by creating the PSQL database, using AWS for hosting, installing pgAdmin, connecting to the database and creating some users with different rights.

In the second assignment we had to create different tables from a dataset of CSV files, which was done automatically through a python script that reads a csv file and outputs a SQL CREATE TABLE statement. Then, we had to create the tabes in our database and import the data from the CSV files into the corresponding tables (pgAdmin helped make a lot of these through windows, forms and checkboxes).

My biggest gripe with this was that a few of these CSV files were not small in size (the size of the calendar CSV was 175 MBs). This means that if your upload speed is not great, you will have to wait for a long time for these to be uploaded (and God forbid if there are any errors with the datatypes when you were creating the tables). Fortunately, no mistakes or annoying commas and other symbols were present in the CSV files. 
In the import dialog of pgAdmin, make sure to include UTF-8 encoding, add colon as delimiter and single quotes as quote and escape.

The E-R diagram is very easy to create, but make sure you save it because you will need to update it later on.

First assignment grade: 100/100
Second assignment grade: 85/100