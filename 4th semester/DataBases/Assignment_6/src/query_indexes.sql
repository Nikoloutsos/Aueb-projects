--QUERY 1
CREATE INDEX listing_id_index_for_review ON Review(listing_id);
DROP INDEX listing_id_index_for_review;


---------------------------------------------------------------------------------------------------------------------------------------
--QUERY 2
CREATE INDEX guests_included_index_for_listing ON Listing(guests_included, price, review_scores_rating);
DROP INDEX guests_included_index_for_listing;


---------------------------------------------------------------------------------------------------------------------------------------
--QUERY 3
CREATE INDEX lac_index ON listing_amenity_connection(listing_id, amenity_id);
DROP INDEX lac_index;




---------------------------------------------------------------------------------------------------------------------------------------
--QUERY 4
CREATE INDEX amenity_name_index on amenity(amenity_name);
DROP INDEX amenity_name_index;


---------------------------------------------------------------------------------------------------------------------------------------
--QUERY 5
CREATE INDEX index_for_host ON Host(city, state, country);
DROP INDEX index_for_host;

CREATE INDEX index_for_hostid_from_listing on Listing(host_id);
DROP INDEX index_for_hostid_from_listing;



---------------------------------------------------------------------------------------------------------------------------------------
--QUERY 6
CREATE INDEX weekly_price_beds_index ON Listing(weekly_price, beds);
DROP INDEX weekly_price_beds_index;



---------------------------------------------------------------------------------------------------------------------------------------
--QUERY 7
CREATE INDEX listing_score_bed_neighb_propert_index ON Listing(property_type, review_scores_location, bed_type, neighborhood);
DROP INDEX listing_score_bed_neighb_propert_index;




---------------------------------------------------------------------------------------------------------------------------------------
--QUERY 8
CREATE INDEX calendar_price_listingid_index on Calendar(price);
DROP INDEX calendar_price_listingid_index;



