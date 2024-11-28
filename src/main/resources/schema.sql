insert into Genre (id, genre_name,created_at,created_by)
values ('1','thriller','2024-11-24','Anonymous user');
insert into Genre (id, genre_name ,created_at,created_by)
values ('2','fiction','2024-11-24','Anonymous user');
insert into Genre (id, genre_name ,created_at,created_by)
values ('3','novel','2024-11-24','Anonymous user');
insert into Genre (id, genre_name ,created_at,created_by)
values ('4','fantasy','2024-11-24','Anonymous user');

insert into book(id, title,author,isbn,language,edition,published_Date,genre_Id,price,quantity,borrowed,available
                ,created_at,created_by)values ('1','Sun Shine', 'Bram E Edward','90-01-20','english','2nd edition',
                                               '2021-01-12','4', 30.91, 100 ,0, 100 ,'2024-11-24','Anonymous user');
insert into book(id, title,author,isbn,language,edition,published_Date,genre_Id,price,quantity,borrowed,available
                ,created_at,created_by) values ('2','Rainbow', 'Bram E Edward','90-01-28','english','22nd edition',
                                                '2021-01-12','2', 30.91, 100 ,0, 100 ,'2024-11-24','Anonymous user');
insert into book(id, title,author,isbn,language,edition,published_Date,genre_Id,price,quantity,borrowed,available ,
                 created_at,created_by) values ('3','In The Eye Of The Storm', 'Shark Man','90-01-10','english',
                                    '2nd edition','2021-01-12','1', 30.91, 100 ,0, 100 ,'2024-11-24','Anonymous user');
insert into book(id, title,author,isbn,language,edition,published_Date,genre_Id,price,quantity,borrowed,available
                ,created_at,created_by)values ('4','Life', 'Adam Williams','90-01-12','english','2nd edition',
                                               '2021-01-12','4', 30.91, 100 ,0, 100 ,'2024-11-24','Anonymous user');

insert into library_user (id, first_Name, last_Name, phone_Number, email, created_at, created_by)
values ('1', 'Uexcel','Tonny', '07032632739', 'uexcel9@gmial.com', '2024-11-24', 'Anonymous user');

insert into library_user (id, first_Name, last_Name, phone_Number, email, created_at, created_by)
values ('2', 'Tobi','Clem', '07032632710', 'tobi_clem@gmial.com', '2024-11-24', 'Anonymous user');

insert into library_user (id, first_Name, last_Name, phone_Number, email, created_at, created_by)
values ('3', 'Maria','Kelvin', '07032632700', 'Kelvin@gmial.com', '2024-11-24', 'Anonymous user');

