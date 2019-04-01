UPDATE Listings
SET street = SPLIT_PART(street, ',', 1);

----------------------------------------------------------------------------------------------------------------------

-- for price fields we had datatype money, so we now changed it to varchar
alter table Listings
alter column price TYPE varchar,
alter column weekly_price TYPE varchar,
alter column monthly_price TYPE varchar,
alter column security_deposit TYPE varchar,
alter column cleaning_fee TYPE varchar;

UPDATE  Listings
SET 
price = REPLACE(price,'$',''),
weekly_price = REPLACE(weekly_price,'$',''),
monthly_price = REPLACE(monthly_price,'$',''),
security_deposit = REPLACE(security_deposit,'$',''),
cleaning_fee = REPLACE(cleaning_fee,'$','');

UPDATE  Listings
SET 
price = REPLACE(price,',',''),
weekly_price = REPLACE(weekly_price,',',''),
monthly_price = REPLACE(monthly_price,',',''),
security_deposit = REPLACE(security_deposit,',',''),
cleaning_fee = REPLACE(cleaning_fee,',','');

ALTER TABLE Listings 
alter column price TYPE numeric(10,0) using price::numeric,
alter column weekly_price TYPE numeric(10,0) using weekly_price::numeric,
alter column monthly_price TYPE numeric(10,0) using monthly_price::numeric,
alter column security_deposit TYPE numeric(10,0) using security_deposit::numeric,
alter column cleaning_fee TYPE numeric(10,0) using cleaning_fee::numeric;