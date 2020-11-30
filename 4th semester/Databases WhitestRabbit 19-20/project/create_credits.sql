create table Credits(
   full_cast text,
   crew text,
   id int -- in the links table it is called tmbdId
);

delete from Credits where crew in (select crew from (select crew, ROW_NUMBER() over (partition by id order by crew) as row_num from Credits) t where t.row_num > 1);

ALTER TABLE Credits ADD PRIMARY KEY (id);

ALTER TABLE Credits 
ADD CONSTRAINT fkc1 FOREIGN KEY (id) REFERENCES Movies_Metadata (id);