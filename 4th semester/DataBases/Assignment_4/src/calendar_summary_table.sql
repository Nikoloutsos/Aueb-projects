CREATE TABLE Calendar_Summary 
AS (SELECT id AS listing_id, calendar_updated, calendar_last_scraped AS from_date, 
	 availability_30, availability_60, availability_90
	FROM listings);

ALTER TABLE Calendar_Summary 
ADD FOREIGN KEY (listing_id) REFERENCES Listings(id)
ADD PRIMARY KEY (listing_id, calendar_updated);


ALTER TABLE Listings
DROP COLUMN calendar_updated,
DROP COLUMN calendar_last_scraped,
DROP COLUMN availability_30,
DROP COLUMN availability_60,
DROP COLUMN availability_90;