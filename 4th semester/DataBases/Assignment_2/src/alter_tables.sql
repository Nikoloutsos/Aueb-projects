BEGIN;
alter table Calendar
add FOREIGN KEY (listing_id) REFERENCES Listings(id);

alter table Listings
add FOREIGN KEY (neighbourhood_cleansed) REFERENCES Neighbourhoods(neighbourhood);

--alter table Neighbourhoods;

alter table Reviews
add FOREIGN KEY (listing_id) REFERENCES Listings(id);

alter table Summary_Listings
add FOREIGN KEY(id) REFERENCES Listings(id);

alter table Summary_Reviews
add FOREIGN KEY (listing_id) REFERENCES Listings(id);
COMMIT;