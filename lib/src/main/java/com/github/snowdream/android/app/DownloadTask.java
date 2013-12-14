/*
 * Copyright (C) 2013 Snowdream Mobile <yanghui1986527@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.snowdream.android.app;

import android.content.Context;
import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The download task
 *
 * @author snowdream <yanghui1986527@gmail.com>
 * @version v1.0
 * @date Sep 29, 2013
 */
@DatabaseTable(tableName = "downloadtask")
public class DownloadTask extends Object {
    /**
     * id
     */
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id = 0;

    /**
     * url
     */
    @DatabaseField(canBeNull = false)
    private String url = "";

    /**
     * name
     */
    @DatabaseField
    private String name = "";

    /**
     * total size
     */
    @DatabaseField
    private long size = 0;

    /**
     * download status
     */
    @DatabaseField
    private int status = DownloadStatus.STATUS_PENDING;

    /**
     * the time of start downloading
     */
    @DatabaseField
    private long startTime = 0;

    /**
     * the time of finish downloading
     */
    @DatabaseField
    private long finishTime = 0;

    /**
     * type
     */
    @DatabaseField
    private int type = DownloadType.TYPE_UNKNOWN;

    /**
     * mimetype
     */
    @DatabaseField
    private String mimeType = "";

    /**
     * the save path on the sdcard
     */
    @DatabaseField(canBeNull = false)
    private String path = "";

    @DatabaseField(persisted = false)
    private AsycDownloadTask task = null;

    @DatabaseField(persisted = false)
    private Context context = null;

    @DatabaseField(persisted = false)
    private DownloadListener listener = null;

    @SuppressWarnings("unused")
    private DownloadTask() {
    }

    public DownloadTask(Context context) {
        super();
        this.context = context;
    }

    public DownloadTask(DownloadTask task) {
        if (task == null) return;

        this.context = task.context;
        this.startTime = task.startTime;
        this.finishTime = task.finishTime;
        this.id = task.id;
        this.mimeType = task.mimeType;
        this.name = task.name;
        this.path = task.path;
        this.size = task.size;
        this.status = task.status;
        this.type = task.type;
        this.url = task.url;
        this.task = task.task;
        this.listener = task.listener;
    }

    protected void setDownloadTask(DownloadTask task) {
        //this.context = task.context;
        this.startTime = task.startTime;
        //this.finishTime = task.finishTime;
        this.id = task.id;
        if (TextUtils.isEmpty(this.mimeType)) {
            this.mimeType = task.mimeType;
        }
        if (TextUtils.isEmpty(this.name)) {
            this.name = task.name;
        }
        if (TextUtils.isEmpty(this.path)) {
            this.path = task.path;
        }
        this.size = task.size;
        this.status = task.status;
        this.type = task.type;
        this.url = task.url;
        //this.task = task.task;
        //this.listener = task.listener;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DownloadListener getListener() {
        return listener;
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    /**
     * Start the Task
     *
     * @param context  Context
     * @param listener DownloadListener
     */
    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    protected void start(Context context, DownloadListener listener) {
        //Get context,which will be used to communicate with sqlite.
        if (this.context == null && context != null) {
            this.context = context;
        }

        if (task != null) {
            task.cancel(true);
            task = null;
        }

        task = new AsycDownloadTask(listener);
        task.execute(this);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
