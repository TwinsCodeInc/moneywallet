/*
 * Copyright (c) 2018.
 *
 * This file is part of MoneyWallet.
 *
 * MoneyWallet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoneyWallet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MoneyWallet.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.oriondev.moneywallet.api;

import android.content.Context;

import com.oriondev.moneywallet.R;
import com.oriondev.moneywallet.api.disk.DiskBackendService;
import com.oriondev.moneywallet.api.disk.DiskBackendServiceAPI;
import com.oriondev.moneywallet.api.dropbox.DropboxBackendService;
import com.oriondev.moneywallet.api.dropbox.DropboxBackendServiceAPI;
import com.oriondev.moneywallet.api.google.GoogleDriveBackendService;
import com.oriondev.moneywallet.api.google.GoogleDriveBackendServiceAPI;
import com.oriondev.moneywallet.model.BackupService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrea on 21/11/18.
 */
public class BackendServiceFactory {

    public static final String SERVICE_ID_DROPBOX = "DropBox";
    public static final String SERVICE_ID_GOOGLE_DRIVE = "GoogleDrive";
    public static final String SERVICE_ID_EXTERNAL_MEMORY = "ExternalMemory";

    public static AbstractBackendServiceDelegate getServiceById(String backendId, AbstractBackendServiceDelegate.BackendServiceStatusListener listener) {
        switch (backendId) {
            case SERVICE_ID_DROPBOX:
                return new DropboxBackendService(listener);
            case SERVICE_ID_GOOGLE_DRIVE:
                return new GoogleDriveBackendService(listener);
            case SERVICE_ID_EXTERNAL_MEMORY:
                return new DiskBackendService(listener);
        }
        return null;
    }

    public static IBackendServiceAPI getServiceAPIById(Context context, String backendId) throws BackendException {
        switch (backendId) {
            case SERVICE_ID_DROPBOX:
                return new DropboxBackendServiceAPI(context);
            case SERVICE_ID_GOOGLE_DRIVE:
                return new GoogleDriveBackendServiceAPI(context);
            case SERVICE_ID_EXTERNAL_MEMORY:
                return new DiskBackendServiceAPI();
            default:
                throw new BackendException("Invalid backend");
        }
    }

    public static List<BackupService> getBackupServices() {
        List<BackupService> services = new ArrayList<>();
        services.add(new BackupService(SERVICE_ID_DROPBOX, R.drawable.ic_dropbox_24dp, R.string.service_backup_drop_box));
        services.add(new BackupService(SERVICE_ID_GOOGLE_DRIVE, R.drawable.ic_google_drive_24dp, R.string.service_backup_google_drive));
        services.add(new BackupService(SERVICE_ID_EXTERNAL_MEMORY, R.drawable.ic_sd_24dp, R.string.service_backup_external_memory));
        return services;
    }
}