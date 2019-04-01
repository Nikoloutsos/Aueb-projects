--w ehad price field as money
ALTER TABLE Calendar
alter column price TYPE varchar;

UPDATE  Calendar
SET 
price = REPLACE(price,'$','');

UPDATE  Calendar
SET 
price = REPLACE(price,',','');

ALTER TABLE Calendar 
alter column price TYPE numeric(10,0) using price::numeric;