CREATE TABLE Neighborhood
AS (SELECT DISTINCT neighbourhood as neighborhood_name, 
    neighbourhood_cleansed as zip_code FROM Listings);


DELETE FROM Neighborhood
WHERE  neighborhood_name is null OR zip_code is null;
--------------------------------------------------------------------------------------------------------------------------------------

ALTER TABLE Neighborhood
ADD PRIMARY KEY (neighborhood_name, zip_code);


ALTER TABLE Listings
ADD FOREIGN KEY (neighborhood, neighbourhood_cleansed ) REFERENCES Neighborhood(neighborhood_name, zip_code);



ALTER TABLE Listings
DROP COLUMN neighbourhood_group_cleansed;


ALTER TABLE Listings 
RENAME neighbourhood TO neighborhood;


ALTER TABLE Listings
DROP CONSTRAINT   listings_neighbourhood_cleansed_fkey;

--------------------------------------------------------------------------------------------------------------------------------------
DROP TABLE Neighbourhoods;

--------------------------------------------------------------------------------------------------------------------------------------
ALTER TABLE Summary_Listings 
RENAME neighbourhood TO zip_code;

ALTER TABLE Summary_Listings
DROP COLUMN neighbourhood_group;

