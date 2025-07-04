SELECT 'CREATE DATABASE template_db' 
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'template_db')\gexec
  
