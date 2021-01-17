package com.zystems.plantdex;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zystems.plantdex.models.PlantLocation;

import java.util.List;

public class ApplicationUtilities {

    // Main Menu and Intro Page Functionality
    private static boolean closeApp;

    public static boolean isCloseApp() {
        return closeApp;
    }

    public static void setCloseApp(boolean closeApp) {
        ApplicationUtilities.closeApp = closeApp;
    }


    // Contribute Form and Contribute Map Functionality
    private static List<PlantLocation> contributePlantLocations;
    private static boolean hasChanged;

    public static List<PlantLocation> getContributePlantLocations() {
        return contributePlantLocations;
    }

    public static void setContributePlantLocations(List<PlantLocation> contributePlantLocations) {
        ApplicationUtilities.contributePlantLocations = contributePlantLocations;
    }

    public static boolean isHasChanged() {
        return hasChanged;
    }

    public static void setHasChanged(boolean hasChanged) {
        ApplicationUtilities.hasChanged = hasChanged;
    }

    // Android Package Utilities
    public static String getCurrentAppVersionName(Context ctx){
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
