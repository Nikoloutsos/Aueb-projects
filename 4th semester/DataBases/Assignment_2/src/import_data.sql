BEGIN;
set client_encoding to 'utf8';

\copy Listings FROM 'listings.csv' DELIMITER ',' CSV HEADER;

\copy Neighbourhoods FROM 'neighbourhoods.csv' DELIMITER ',' CSV HEADER;

\copy Calendar FROM 'calendar.csv' DELIMITER ',' CSV HEADER;

\copy Reviews FROM 'reviews.csv' DELIMITER ',' CSV HEADER;

\copy Summary_Listings FROM 'summary_listings.csv' DELIMITER ',' CSV HEADER;

\copy Summary_Reviews FROM 'summary_reviews.csv' DELIMITER ',' CSV HEADER;
COMMIT;