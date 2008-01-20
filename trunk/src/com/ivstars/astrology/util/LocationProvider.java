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
package com.ivstars.astrology.util;

import org.apache.poi.hssf.dev.HSSF;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;

/**
 * LocationProvider
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class LocationProvider {
    HSSFWorkbook wb;
    String[]  provinces;
    public LocationProvider(InputStream in) throws IOException {
        POIFSFileSystem fs =
            new POIFSFileSystem(in);
        wb = new HSSFWorkbook(fs);
    }

    public String[] listProvinces(){
        if(provinces == null){
            int count=wb.getNumberOfSheets();
            provinces = new String[count];
            for(int i=0;i<count;i++){
                provinces[i] = wb.getSheetName(i);
            }
        }
        return provinces;
    }

    public Location[] listLocations(String province){
        HSSFSheet sheet = wb.getSheet(province);
        int begin=sheet.getFirstRowNum();
        int end = sheet.getLastRowNum();
        Location[] locs = new Location[end-begin+1];
        for(int i=begin;i<=end;i++){
            locs[i-begin]=createLocation(sheet.getRow(i),province);
        }
        return locs;
    }

    private Location createLocation(HSSFRow row,String province){
        return new Location(row.getCell((short) 5).getNumericCellValue(),
                row.getCell((short) 4).getNumericCellValue(),
                row.getCell((short) 0).getStringCellValue(),province);
    }
}
