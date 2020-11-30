--creation of table Room with the appropriate columns from Listings:
CREATE TABLE Room
  AS (SELECT DISTINCT id as listing_id, accommodates, bathrooms, bedrooms, beds, bed_type,
  	amenities, square_feet, price, weekly_price, monthly_price, security_deposit FROM Listings);

--deleting the room columns from the Listings table:
ALTER TABLE Listings
DROP COLUMN accommodates,
DROP COLUMN bathrooms,
DROP COLUMN bedrooms,
DROP COLUMN beds,
DROP COLUMN bed_type,
DROP COLUMN amenities,
DROP COLUMN square_feet,
DROP COLUMN price,
DROP COLUMN weekly_price,
DROP COLUMN monthly_price,
DROP COLUMN security_deposit;

--set foreign key on Room by referencing the id column on Listings:
ALTER TABLE Room ADD FOREIGN KEY (listing_id) REFERENCES Listings (id);