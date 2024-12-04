-- Create the settings table
CREATE TABLE IF NOT EXISTS settings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    settings_key VARCHAR(255) NOT NULL UNIQUE,
    value VARCHAR(1024) NOT NULL
);
