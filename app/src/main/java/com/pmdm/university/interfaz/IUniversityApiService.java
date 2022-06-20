package com.pmdm.university.interfaz;

import com.google.gson.annotations.SerializedName;
import com.pmdm.university.entidad.University;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IUniversityApiService {

    @SerializedName("web_pages")
    @GET("search?country=Spain")
    Call<List<University>> getAll();
}
