CROP TYPES:CROP
------------------
MILLETS: Jowar,Bazra,Raagi,Maize,Korra,Pulses,Rajma,Redgram,Blackgram,Greengram,Bengalgram
OIL SEEDS: Sunflower,Groundnut
COMMERCIAL CROPS: Tobacco,Chillies,Cotton,Sugarcane
VEGETABLES: Tomato,Brinjal,Cabbage
FRUITS: Pomegranate,Banana,Guava,Watermelon,Mango,Coconut,


INT CATEGORY - CAT1


insert into kls_schema.district values (9999,'B01','Dummy');
 
INSERT INTO kls_schema.bank_parameter
(id, bank_code, bank_name, process_status, loan_limit_per_farmer,district_id, priority_order, max_share_amount, business_date,max_days_for_inconsistency) 
VALUES (9999,'TB001','Kissan Bank',1,10000,9999,'ASC',5000,sysdate,9);