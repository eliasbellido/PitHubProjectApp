
package com.beyondthecode.pithubproject.data.datasource.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.beyondthecode.pithubproject.domain.PedidoDetalle;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Computer on 30/01/2018.
 */

public class SqlHelper extends SQLiteOpenHelper {

    private static final String nombreBD = "PitHubBdSQLite";
    private static final String tablaDetalleOrden ="DetalleOrden";
    //atributos de la tabla
    private static final String campoPedidoDetalleId = "pedidodetalleId";
    private static final String campoProductoId = "productId";
    private static final String campoNombre = "productNombre";
    private static final String campoCantidad ="cantidad";
    private static final String campoPrecio = "precio";



    public SqlHelper(Context context) {
        super(context, nombreBD, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreate = "CREATE TABLE "+ tablaDetalleOrden +
                "("+ campoPedidoDetalleId + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                campoProductoId + " TEXT,"+
                campoNombre + " TEXT,"+
                campoCantidad + " TEXT," +
                campoPrecio + " TEXT)";

        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<PedidoDetalle> obtenerOrden() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productId", "productNombre", "cantidad", "precio"};
        String sqlTable = tablaDetalleOrden;


        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        List<PedidoDetalle> listaPedidoDetalle = new ArrayList<>();

        if (cursor.moveToFirst()) {

            do {
                listaPedidoDetalle.add(new PedidoDetalle(
                        cursor.getString(cursor.getColumnIndex("productId")),
                        cursor.getString(cursor.getColumnIndex("productNombre")),
                        cursor.getString(cursor.getColumnIndex("cantidad")),
                        cursor.getString(cursor.getColumnIndex("precio"))

                ));
            } while (cursor.moveToNext());


        }

        db.close();

        return listaPedidoDetalle;

    }

    private PedidoDetalle existeProductoEnLista(String nomproducto) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();



        String[] sqlSelect = {"productId", "productNombre", "cantidad", "precio"};
        String sqlTable = tablaDetalleOrden;

        String selection = "productNombre = ?";
        String selectionArgs[] = {nomproducto};

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, selection, selectionArgs, null, null, null);

        PedidoDetalle listaPedidoDetalle = null;

        if (cursor.moveToFirst()) {

            Log.d("SqlHelper->","Ya existe producto");
            do {
                listaPedidoDetalle = new PedidoDetalle(
                        //cursor.getString(cursor.getColumnIndex("productId")),
                        cursor.getString(cursor.getColumnIndex("productNombre")),
                        cursor.getString(cursor.getColumnIndex("cantidad")),
                        cursor.getString(cursor.getColumnIndex("precio"))

                );
            } while (cursor.moveToNext());


        }

        db.close();

        return listaPedidoDetalle;

    }

    public void agregarAlcarro(PedidoDetalle pedidoDetalle, Context context) {

        PedidoDetalle listaObtenida = existeProductoEnLista(pedidoDetalle.getProductNombre());

        if( listaObtenida != null){

            int cantidadActual = Integer.parseInt(listaObtenida.getCantidad());

            int nuevaCantidad = cantidadActual + Integer.parseInt(pedidoDetalle.getCantidad());
            //Toast.makeText(context, "cantidad actual->"+cantidadActual, Toast.LENGTH_SHORT).show();

            String whereClause = "productNombre = ?";
            String whereArgs[] = {pedidoDetalle.getProductNombre()};


            ContentValues contentValues = new ContentValues();
            contentValues.put(campoCantidad, nuevaCantidad);

            SQLiteDatabase db = getWritableDatabase();
            db.update(tablaDetalleOrden,contentValues,whereClause,whereArgs);

            db.close();

        }else{
            //Toast.makeText(context, "Nuevo producto", Toast.LENGTH_SHORT).show();


            ContentValues contentValues = new ContentValues();
            contentValues.put(campoProductoId, pedidoDetalle.getProductId());
            contentValues.put(campoNombre, pedidoDetalle.getProductNombre());
            contentValues.put(campoCantidad, pedidoDetalle.getCantidad());
            contentValues.put(campoPrecio, pedidoDetalle.getPrecio());


            SQLiteDatabase db = getWritableDatabase();
            db.insert(tablaDetalleOrden,null,contentValues);



            // Toast.makeText(context, "codigo de comida ingresado: "+campoProductoId, Toast.LENGTH_SHORT).show();
            db.close();
        }



    }

    public void eliminarPedidoActual(){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(tablaDetalleOrden,null,null);

    }
}