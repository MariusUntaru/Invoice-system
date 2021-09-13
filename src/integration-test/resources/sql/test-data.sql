INSERT INTO public.vendor (creation_date,first_name,last_name) VALUES
('2020-01-26 10:02:50.000','John','Doe')
,('2020-01-26 10:02:50.000','Jane','Doe')
,('2020-01-26 10:02:50.000','John','Targaryen')
;

INSERT INTO public.invoice (creation_date,description,title,vendor_id) VALUES
('2020-01-26 10:00:30.000','Invoice 1 description','Invoice 1 title',1,1)
,('2020-01-26 10:01:19.000','Invoice 2 description','Invoice 2 title',1,1)
,('2020-01-26 10:01:44.000','Invoice 3 description','Invoice 3 title',1,1)
,('2020-01-26 10:02:15.000','Invoice 4 description','Invoice 4 title',2,1)
;