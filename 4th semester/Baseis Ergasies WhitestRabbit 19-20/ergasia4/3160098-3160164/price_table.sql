--creation of table Price with the appropriate columns from Listings:
CREATE TABLE Price
  AS (SELECT DISTINCT id as listing_id, price, weekly_price, monthly_price, security_deposit, cleaning_fee,
	guests_included, extra_people, minimum_nights, maximum_nights, minimum_minimum_nights, maximum_minimum_nights,
	minimum_maximum_nights, maximum_maximum_nights, minimum_nights_avg_ntm, maximum_nights_avg_ntm FROM Listings);



--altering the appropriate varchar columns of table Price, firstly by removing 
--unwanted symbols and secondly by changing their type from varchar to numeric:
UPDATE Price SET price = replace(price, '$' , '');
UPDATE Price SET price = replace(price, ',' , '');
UPDATE Price SET weekly_price = replace(weekly_price, '$' , '');
UPDATE Price SET weekly_price = replace(weekly_price, ',' , '');
UPDATE Price SET monthly_price = replace(monthly_price, '$' , '');
UPDATE Price SET monthly_price = replace(monthly_price, ',' , '');
UPDATE Price SET security_deposit = replace(security_deposit, '$' , '');
UPDATE Price SET security_deposit = replace(security_deposit, ',' , '');
UPDATE Price SET cleaning_fee = replace(cleaning_fee, '$' , '');
UPDATE Price SET cleaning_fee = replace(cleaning_fee, ',' , '');
UPDATE Price SET extra_people = replace(extra_people, '$' , '');
UPDATE Price SET extra_people = replace(extra_people, ',' , '');
UPDATE Price SET minimum_nights_avg_ntm = replace(minimum_nights_avg_ntm, ',' , '');
UPDATE Price SET maximum_nights_avg_ntm = replace(maximum_nights_avg_ntm, ',' , '');

ALTER TABLE Price
ALTER COLUMN price TYPE numeric USING price::numeric,
ALTER COLUMN weekly_price TYPE numeric USING weekly_price::numeric,
ALTER COLUMN monthly_price TYPE numeric USING monthly_price::numeric,
ALTER COLUMN security_deposit TYPE numeric USING security_deposit::numeric,
ALTER COLUMN cleaning_fee TYPE numeric USING cleaning_fee::numeric,
ALTER COLUMN extra_people TYPE numeric USING extra_people::numeric,
ALTER COLUMN minimum_nights_avg_ntm TYPE numeric USING minimum_nights_avg_ntm::numeric,
ALTER COLUMN maximum_nights_avg_ntm TYPE numeric USING maximum_nights_avg_ntm::numeric;


--deleting the price columns from the Listings table:
ALTER TABLE Listings
DROP COLUMN price,
DROP COLUMN weekly_price,
DROP COLUMN monthly_price,
DROP COLUMN security_deposit,
DROP COLUMN cleaning_fee,
DROP COLUMN guests_included,
DROP COLUMN extra_people,
DROP COLUMN minimum_nights,
DROP COLUMN maximum_nights,
DROP COLUMN minimum_minimum_nights,
DROP COLUMN maximum_minimum_nights,
DROP COLUMN minimum_maximum_nights,
DROP COLUMN maximum_maximum_nights,
DROP COLUMN minimum_nights_avg_ntm,
DROP COLUMN maximum_nights_avg_ntm;

--set foreign key on Price by referencing the id column on Listings:
ALTER TABLE Price ADD FOREIGN KEY (listing_id) REFERENCES Listings (id);