package com.escrow.wazipay.media.dao;

import com.escrow.wazipay.media.entity.Settings;

public interface SettingsDao {
    Settings findBySettingsKey(String settingsKey);
}
