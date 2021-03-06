package com.zystems.plantdex;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zystems.plantdex.models.ApplicationUser;
import com.zystems.plantdex.models.Plant;
import com.zystems.plantdex.models.PlantClassificationResult;
import com.zystems.plantdex.models.PlantLocation;

import java.io.File;
import java.util.List;

public class ApplicationUtilities {
    // App's Cross Activity Request Keys
    public static final String SEARCH_SELECTED_PLANT = "plantId";
    private static String loggedUser;


    public static String getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(String loggedUser) {
        ApplicationUtilities.loggedUser = loggedUser;
    }

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
    private static File contributedFile;
    private static boolean hasChanged;

    public static List<PlantLocation> getContributePlantLocations() {
        return contributePlantLocations;
    }
    public static void setContributedPlantImageFile(File file){
        contributedFile = file;
    }

    public static File getContributedPlantImageFile(){
        return contributedFile;
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
    public static List<PlantClassificationResult> classifyPlantsResults;

    public static List<PlantClassificationResult> getClassifyPlantsResults() {
        return classifyPlantsResults;
    }

    public static void setClassifyPlantsResults(List<PlantClassificationResult> classifyPlantsResults) {
        ApplicationUtilities.classifyPlantsResults = classifyPlantsResults;
    }
}
