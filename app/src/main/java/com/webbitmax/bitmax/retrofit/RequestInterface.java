package com.webbitmax.bitmax.retrofit;

import com.webbitmax.bitmax.model.Chamados;
import com.webbitmax.bitmax.model.ServerRequest;
import com.webbitmax.bitmax.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by leonardo on 21/08/17.
 */

public interface RequestInterface {

    @FormUrlEncoded
    @POST("login")
    Call<ServerRequest> login(@Field("login") String login, @Field("senha") String senha);


    @GET("chamados/{tecnico}")
    Call<ServerRequest> chamados(@Path("tecnico") String tecnico);

    @FormUrlEncoded
    @POST("iniciar")
    Call<ServerRequest> iniciar(@Field("id") int id, @Field("login") String login);

    @FormUrlEncoded
    @POST("fechar")
    Call<ServerRequest> fechar(
            @Field("id") int id,
            @Field("cliente") int cliente,
            @Field("resolvido") String resolvido,
            @Field("relatorio") String relatorio,
            @Field("mcabo") String mcabo,
            @Field("qconector") String qconector,
            @Field("login") String login
    );

    @FormUrlEncoded
    @POST("pendencia")
    Call<ServerRequest> pendencia(
            @Field("id") int id,
            @Field("notapendencia") String notapendencia,
            @Field("datapendencia") String datapendencia,
            @Field("login") String login
    );

    @FormUrlEncoded
    @POST("resetarmac")
    Call<ServerRequest> resetarmac(
            @Field("clienteLogin") String clienteLogin,
            @Field("login") String login
    );

    @FormUrlEncoded
    @POST("marcarponto")
    Call<ServerRequest> marcarPonto(
            @Field("codigo") String codigo,
            @Field("lati") double lati,
            @Field("longi") double longi,
            @Field("login") String login
    );

    @FormUrlEncoded
    @POST("updatePendencia")
    Call<ServerRequest> updatePendencia(
            @Field("id") int id,
            @Field("notapendencia") String notapendencia,
            @Field("datapendencia") String datapendencia,
            @Field("login") String login
    );

    @FormUrlEncoded
    @POST("updateFechar")
    Call<ServerRequest> updateFechar(
            @Field("id") int id,
            @Field("cliente") int cliente,
            @Field("resolvido") String resolvido,
            @Field("relatorio") String relatorio,
            @Field("iniciado") String iniciado,
            @Field("datafechamento") String datafechamento,
            @Field("mcabo") String mcabo,
            @Field("qconector") String qconector
    );

}
