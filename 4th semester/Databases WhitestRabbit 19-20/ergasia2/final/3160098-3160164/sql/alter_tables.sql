alter table calendar add constraint FK_ID foreign key (listing_id) references listings (id);
alter table reviews add constraint FK_ID foreign key (listing_id) references listings (id);
alter table listings add constraint FK_Neighbour foreign key (neighbourhood_cleansed) references neighbourhoods (neighbourhood);
alter table summary_reviews add constraint FK_ID foreign key (listing_id) references listings (id);
alter table summary_listings add constraint FK_ID foreign key (id) references listings (id);
alter table summary_listings add constraint FK_Neighbour foreign key (neighbourhood) references neighbourhoods (neighbourhood);
alter table geolocation add constraint FK_Neighbour foreign key (properties_neighbourhood) references neighbourhoods (neighbourhood);
alter table geolocation add constraint FK_NeighbourGroup foreign key (properties_neighbourhood_group) references neighbourhoods (neighbourhood_group);
