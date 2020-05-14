package com.itesm.healthchain.ui.medical_records;

import com.itesm.healthchain.models.Person;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PeopleViewModel extends ViewModel {

    MutableLiveData<ArrayList<Person>> peopleLiveData;
    ArrayList<Person> personArrayList;

    public PeopleViewModel() {
        peopleLiveData = new MutableLiveData<>();

        // call Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Person>> getPeopleMutableLiveData() {
        return peopleLiveData;
    }

    public void init(){
        populateList();
        peopleLiveData.setValue(personArrayList);
    }

    public void populateList(){
        Person person = new Person();
        personArrayList = new ArrayList<>();
        personArrayList.add(person);
        personArrayList.add(person);
        personArrayList.add(person);
    }
}