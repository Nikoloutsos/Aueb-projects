--altering price and adjusted_price columns from varchar to numeric type:
UPDATE Calendar SET price = replace(price, '$' , '');
UPDATE Calendar SET price = replace(price, ',' , '');
UPDATE Calendar SET adjusted_price = replace(adjusted_price, '$' , '');
UPDATE Calendar SET adjusted_price = replace(adjusted_price, ',' , '');

ALTER TABLE Calendar
ALTER COLUMN price TYPE numeric USING price::numeric,
ALTER COLUMN adjusted_price TYPE numeric USING adjusted_price::numeric;

--column 'available' on the Calendar was set to boolean type on Calendar table creation during exercise 2!