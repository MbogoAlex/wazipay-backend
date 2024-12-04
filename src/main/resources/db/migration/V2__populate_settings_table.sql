-- Populate the settings table with initial values
INSERT IGNORE INTO settings (id, settings_key, value)
VALUES
    (1, 'imagePath', '/home/mbogo/Desktop/wazipay/backend/images/'),
    (2, 'domain', 'http://192.168.100.5:8000');
