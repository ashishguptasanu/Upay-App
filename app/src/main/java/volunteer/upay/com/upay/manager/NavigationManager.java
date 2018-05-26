package volunteer.upay.com.upay.manager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import volunteer.upay.com.upay.Activities.CenterActivity;
import volunteer.upay.com.upay.Activities.StudentActivity;
import volunteer.upay.com.upay.Activities.VolunteerActivity;
import volunteer.upay.com.upay.Activities.WebviewActivity;
import volunteer.upay.com.upay.Models.CategoryModel;
import volunteer.upay.com.upay.util.AppConstants;

/**
 * Created by amanbansal on 12/05/18.
 */

public class NavigationManager {

    public static void openCategory(Context context, CategoryModel categoryModel) {
        if (categoryModel == null || TextUtils.isEmpty(categoryModel.getCategory())) {
            return;
        }
        switch (categoryModel.getCategory().toLowerCase()) {
            case "events":
                WebviewActivity.open(context, AppConstants.EVENTS_URL, "Events");
                break;
            case "volunteers":
                Intent intentVolunteer = new Intent(context, VolunteerActivity.class);
                intentVolunteer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentVolunteer);
                break;

            case "students":
                Intent intentStudents = new Intent(context, StudentActivity.class);
                intentStudents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentStudents);
                break;
            case "centers":
                Intent intentCenter = new Intent(context, CenterActivity.class);
                intentCenter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentCenter);
                break;
            case "contacts":
                WebviewActivity.open(context, AppConstants.CONTACT_US_URL, "Contact Us");
                break;
        }
    }




}
