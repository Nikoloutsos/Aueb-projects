-----------------------------------------------------------------------------
CREATE FUNCTION decrease_total_reviews()
	RETURNS trigger AS
'BEGIN
UPDATE listing 
SET number_of_reviews = number_of_reviews - 1
WHERE id = OLD.listing_id;
RETURN OLD;
END;
'
LANGUAGE plpgsql;

CREATE FUNCTION increase_total_reviews()
	RETURNS trigger AS
'BEGIN
UPDATE listing 
SET number_of_reviews = number_of_reviews + 1
WHERE id = NEW.listing_id;
RETURN NEW;
END;
'
LANGUAGE plpgsql;


CREATE TRIGGER deleteReviewTrigger AFTER DELETE ON review
FOR EACH ROW
EXECUTE PROCEDURE decrease_total_reviews();


CREATE TRIGGER insertReviewTrigger AFTER INSERT ON review
FOR EACH ROW
EXECUTE PROCEDURE increase_total_reviews();


----------------------------------------------------------------------------------------------------------------------------------------------
/*our custom trigger
 When a new listing is inserted/deleted into listing
 table then we automaticly increment/decrement
 the total number of listings owned by the host!
*/
CREATE FUNCTION update_host_total_listings()
  RETURNS trigger AS
'BEGIN
IF TG_OP = ''DELETE'' THEN
 UPDATE Host SET total_listing_count = total_listing_count - 1
 WHERE id = OLD.host_id;
 RETURN OLD;
ELSIF TG_OP = ''INSERT'' THEN
 UPDATE Host SET total_listing_count = total_listing_count + 1
 WHERE id = OLD.host_id;
 RETURN NEW;
END IF;
END;
'LANGUAGE plpgsql;

CREATE TRIGGER numbReviews AFTER DELETE OR INSERT ON listing
FOR EACH ROW
EXECUTE PROCEDURE update_host_total_listings();