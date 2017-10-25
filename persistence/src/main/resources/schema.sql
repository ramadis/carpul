CREATE TABLE IF NOT EXISTS users (
  id serial PRIMARY KEY,
  created timestamp,
  first_name varchar (100),
  phone_number varchar (100),
  last_name varchar (100),
  username varchar (100),
  password varchar (100))
);
