/*
 * Jastrology - Copyright 2006 Fengbo Xie, All Rights Reserved.
 *
 * http://www.ivstars.com/jastrology
 * http://sourceforge.net/projects/jastrology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ivstars.astrology;

import com.ivstars.astrology.util.CommonUtil;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-6-2
 * Time: 0:23:00
 * To change this template use File | Settings | File Templates.
 */
public class VersionInfo {
    private String codename;
    private String version;
    private String release;
    private String build;

    public VersionInfo(String codename, String version, String release, String build) {
        this.codename = codename;
        this.version = version;
        this.release = release;
        this.build =build;
    }

    public String getCodename() {
        return codename;
    }

    public String getVersion() {
        return version;
    }

    public String getRelease() {
        return release;
    }

    public String getBuild() {
        return build;
    }



    public String toString() {
        return version+" "+release+" (Build "+build+")";
    }
}
