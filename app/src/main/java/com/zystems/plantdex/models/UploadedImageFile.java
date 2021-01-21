package com.zystems.plantdex.models;

import android.os.Build;
import android.util.Log;

import com.google.android.gms.common.util.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadedImageFile {

    private String fileName;
    public byte[] fileData;

    public UploadedImageFile() {
    }

    public UploadedImageFile(String fileName) {
        Log.d(UploadedImageFile.class.getSimpleName(), fileName);
        this.fileName = fileName.substring(fileName.lastIndexOf("/")+1);
        Log.d(UploadedImageFile.class.getSimpleName(), this.fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try{
                this.fileData = Files.readAllBytes(Paths.get(fileName));
            }catch (IOException ex){
                Log.d(UploadedImageFile.class.getSimpleName(), "IOException : " + ex.getMessage());
            }
        }else
            Log.d(UploadedImageFile.class.getSimpleName(), "Phone Version Unsupported");
    }

    private String normalize(String input){
        return input.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
