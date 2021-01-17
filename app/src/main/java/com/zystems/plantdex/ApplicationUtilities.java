package com.zystems.plantdex;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import androidx.annotation.NonNull;

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
    private static boolean dumpData;

    public static List<PlantLocation> getContributePlantLocations() {
        return contributePlantLocations;
    }

    public static void setContributePlantLocations(List<PlantLocation> contributePlantLocations) {
        ApplicationUtilities.contributePlantLocations = contributePlantLocations;
    }

    public static boolean isDumpData() {
        return dumpData;
    }

    public static void setDumpData(boolean dumpData) {
        ApplicationUtilities.dumpData = dumpData;
    }
}
