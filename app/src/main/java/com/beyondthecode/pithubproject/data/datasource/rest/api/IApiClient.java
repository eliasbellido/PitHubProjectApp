package com.beyondthecode.pithubproject.data.datasource.rest.api;

import com.beyondthecode.pithubproject.data.datasource.request.LoginRequest;
import com.beyondthecode.pithubproject.data.datasource.request.PedidoRequest;
import com.beyondthecode.pithubproject.data.datasource.response.LoginResponse;
import com.beyondthecode.pithubproject.data.datasource.response.PedidoResponse;
import com.beyondthecode.pithubproject.data.datasource.response.ProductosResponse;
import com.beyondthecode.pithubproject.data.datasource.response.RestaurantesResponse;
import com.beyondthecode.pithubproject.data.datasource.response.TipoRestaurantesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiClient {


    //login service
    @POST("usuario/validarUsuario")
    Call<LoginResponse> autenticar(@Body LoginRequest credential);

    //listar tipo restaurantes service
    @GET("restaurantes/categorias")
    Call<TipoRestaurantesResponse> obtenerCategoriasRestaurantes(@Header("x-access-token") String token);

    @GET("restaurantes/categoria/{id}")
    Call<RestaurantesResponse> obtenerRestaurantesxCategoria(@Header("x-access-token") String token, @Path("id") int idcategoriarest);

    //obtener productos segun restaurante
    @GET("productos/{id}")
    Call<ProductosResponse> obtenerProductosxRestaurante(@Header("x-access-token") String token, @Path("id") int idrest);

    @POST("pedido/generarPedido")
    Call<PedidoResponse> generarPedido(@Header("x-access-token") String token, @Body PedidoRequest pedido);

    @POST("usuario/registrarUsuarioFinal")
    Call<LoginResponse> registrarusuario(@Body LoginRequest usuario);

}
