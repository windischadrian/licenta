package com.mide.windan.fastjobs.Enums;

import com.mide.windan.fastjobs.Models.JobType;
import com.mide.windan.fastjobs.R;

import java.util.HashMap;

import static com.mide.windan.fastjobs.Models.JobType.*;

public class JobIcon {

    public static final HashMap<JobType, Integer> jobIcons = new HashMap<>();
    static {
        jobIcons.put(ANIMALE, R.drawable.icon_marker_animale);
        jobIcons.put(ELECTRICIAN, R.drawable.icon_marker_electrician);
        jobIcons.put(MASINA, R.drawable.icon_marker_masina);
        jobIcons.put(TEHNIC, R.drawable.icon_marker_tehnic);
    }

    public static Integer getJobIcon(JobType jobType){
        return jobIcons.get(jobType);
    }

}
