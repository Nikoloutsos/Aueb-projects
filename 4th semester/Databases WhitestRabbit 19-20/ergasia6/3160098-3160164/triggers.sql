/*The following functions and triggers are created as specified by the
project's description for the total listings each host has.
*/

create or replace function decrease_listings()
	returns trigger as
	$$
	begin
		update Host set listings_count = listings_count - 1
		from Listing where Listing.id = Host.listing_id;
		update Host set total_listings_count = total_listings_count - 1
		from Listing where Listing.id = Host.listing_id;
		return null;
	end;
	$$

	language plpgsql;

create trigger list_del
after delete on Listing
for each row
execute procedure decrease_listings();

create or replace function increase_listings()
	returns trigger as
	$$
	begin
		update Host set listings_count = listings_count + 1
		from Listing where Listing.id = Host.listing_id;
		update Host set total_listings_count = total_listings_count + 1
		from Listing where Listing.id = Host.listing_id;
		return null;
	end;
	$$

	language plpgsql;

create trigger list_ins
after insert on Listing
for each row
execute procedure increase_listings();

/*Alter the first or last review date whenever a new entry is added to the Review table if 
the new review date is after the first review date or before the last review date.
*/


create or replace function alter_first_last_rev()
	returns trigger as
	$$
	begin
		update Listing set last_review = greatest(Listing.last_review, to_char(NEW.date, 'YYYY-MM-DD'))
		from Review where Review.listing_id = Listing.id;
		update Listing set first_review = least(Listing.first_review, to_char(NEW.date, 'YYYY-MM-DD'))
		from Review where Review.listing_id = Listing.id;
		return null;
	end;
	$$

	language plpgsql;

create trigger first_last
after insert on Review
for each row
execute procedure alter_first_last_rev();