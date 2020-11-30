create table Keywords(
   id int,--in the links table it is called tmbdId
   keywords text
);

delete from Keywords where keywords in (select keywords from (select keywords, ROW_NUMBER() over (partition by id order by keywords) as row_num from Keywords) t where t.row_num > 1);

ALTER TABLE Keywords ADD PRIMARY KEY (id);

ALTER TABLE Keywords 
ADD CONSTRAINT fkk1 FOREIGN KEY (id) REFERENCES Movies_Metadata (id);