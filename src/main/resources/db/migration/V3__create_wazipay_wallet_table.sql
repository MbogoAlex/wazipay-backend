-- V3__populate_wazipay_wallet.sql
CREATE TABLE IF NOT EXISTS wazipay_wallet (
    id INT PRIMARY KEY,
    balance DOUBLE NOT NULL
);
