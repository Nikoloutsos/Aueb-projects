--creation of table Location with the appropriate columns from Listings:
CREATE TABLE Location
  AS (SELECT DISTINCT id as listing_id, street, neighbourhood, neighbourhood_cleansed, city, state,
	zipcode, market, smart_location, country_code, country, latitude, longitude, is_location_exact from Listings);

--deleting the location columns from the Listings table:
ALTER TABLE Listings
DROP COLUMN street,
DROP COLUMN neighbourhood,
DROP COLUMN neighbourhood_cleansed,
DROP COLUMN city,
DROP COLUMN state,
DROP COLUMN zipcode,
DROP COLUMN market,
DROP COLUMN smart_location,
DROP COLUMN country_code,
DROP COLUMN country,
DROP COLUMN latitude,
DROP COLUMN longitude,
DROP COLUMN is_location_exact;

--set foreign key on Location by referencing the id column on Listings:
ALTER TABLE Location ADD FOREIGN KEY (listing_id) REFERENCES Listings (id);

--remove the correlation between Listings and Neighbourhoods on the Listings table:
ALTER TABLE Listings DROP CONSTRAINT FK_Neighbour;

--create correlation between Location and Neighbourhoods
ALTER TABLE Location ADD FOREIGN KEY (neighbourhood_cleansed) REFERENCES Neighbourhoods (neighbourhood);