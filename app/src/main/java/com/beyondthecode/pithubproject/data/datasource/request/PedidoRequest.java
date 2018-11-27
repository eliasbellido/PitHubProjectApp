package com.beyondthecode.pithubproject.data.datasource.request;

import com.beyondthecode.pithubproject.domain.PedidoDetalle;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PedidoRequest {

    @SerializedName("idrestaurante")
    int idrest;

    @SerializedName("idusuario")
    int iduser;

    @SerializedName("pedido_total")
    Double pedidoPagoTotal;

    @SerializedName("tipo_pago")
    int tipoPago;

    @SerializedName("direccion_entrega")
    String direccionEntrega;

    @SerializedName("pedido_detalle")
    List<PedidoDetalle> pedidoDetalle;

    public PedidoRequest(int idrest, int iduser, Double pedidoPagoTotal, int tipoPago, String direccionEntrega, List<PedidoDetalle> pedidoDetalle) {
        this.idrest = idrest;
        this.iduser = iduser;
        this.pedidoPagoTotal = pedidoPagoTotal;
        this.tipoPago = tipoPago;
        this.direccionEntrega = direccionEntrega;
        this.pedidoDetalle = pedidoDetalle;
    }

    /*
    * "idusuario": "3",
    "pedido_total": "83.40",
    "tipo_pago": "1",
    "direccion_entrega" :"av. las palmeras 123",
	"pedido_detalle"*/
}
