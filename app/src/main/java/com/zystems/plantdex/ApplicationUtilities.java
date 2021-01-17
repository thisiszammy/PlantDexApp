package com.zystems.plantdex;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zystems.plantdex.models.Plant;
import com.zystems.plantdex.models.PlantLocation;

import java.util.List;

public class ApplicationUtilities {
    // App's Cross Activity Request Keys
    public static final String SEARCH_SELECTED_PLANT = "plantId";

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

    // Search Results Data
    private static List<Plant> searchPlantsByNameResults;

    public static List<Plant> getSearchPlantsByNameResults() {
        return searchPlantsByNameResults;
    }

    public static void setSearchPlantsByNameResults(List<Plant> searchPlantsByNameResults) {
        ApplicationUtilities.searchPlantsByNameResults = searchPlantsByNameResults;
    }


    // Classify Plant Results
    public static List<Plant> classifyPlantsResults;

    public static List<Plant> getClassifyPlantsResults() {
        return classifyPlantsResults;
    }

    public static void setClassifyPlantsResults(List<Plant> classifyPlantsResults) {
        ApplicationUtilities.classifyPlantsResults = classifyPlantsResults;
    }
}
