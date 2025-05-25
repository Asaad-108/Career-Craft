package com.example.careercraft.DB;

import com.example.careercraft.Model.Job;

import java.util.ArrayList;
import java.util.List;

public class DummyDatabase {
    public static List<Job> getAllData(){
        List<Job> Jobs=new ArrayList<>();
        for(int i=0;i<10;i++){
            Job temp1=new Job("Backend developer","Lahore","You should have expertises in Java","Devsinc","70000","On-Site");
            Job temp2=new Job("Frontend developer","Lahore","You should have expertises in XML","Devsinc","50000","On-Site");
            Jobs.add(temp1);
            Jobs.add(temp2);
        }
        return Jobs;
    }


}
