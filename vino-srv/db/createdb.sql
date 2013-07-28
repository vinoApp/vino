-- Create vino database
CREATE DATABASE IF NOT EXISTS vino;

-- Create the vino main user
GRANT ALL PRIVILEGES ON vino.* TO 'vino'@'%' IDENTIFIED BY 'vino_pass' WITH GRANT OPTION;

