package volunteer.upay.com.upay.util;

import android.content.Context;
import android.support.annotation.NonNull;

import bolts.Task;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.localDb.CenterRepository;

public class CenterUtils {

    public static Task<Centers> getCenterNameFromId(@NonNull Context context, @NonNull String centerId) {
        CenterRepository centerRepository = new CenterRepository(context);
        return centerRepository.getTask(centerId);
    }
}
