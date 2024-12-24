package com.escrow.wazipay.dao.settings;

import com.escrow.wazipay.model.settings.Settings;

public interface SettingsDao {
    Settings findBySettingsKey(String settingsKey);
}
