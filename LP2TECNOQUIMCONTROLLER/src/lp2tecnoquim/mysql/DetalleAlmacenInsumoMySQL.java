/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp2tecnoquim.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lp2tecnoquim.config.DBController;
import lp2tecnoquim.config.DBManager;
import lp2tecnoquim.dao.DetalleAlmacenInsumoDAO;
import lp2tecnoquim.model.DetalleAlmacenInsumo;
import lp2tecnoquim.model.EstadoMaterial;
import lp2tecnoquim.model.Insumo;
import lp2tecnoquim.model.LineaInsumo;
import lp2tecnoquim.model.LineaOrden;
import lp2tecnoquim.model.OrdenProduccion;

/**
 *
 * @author Carlos Sosa
 */
public class DetalleAlmacenInsumoMySQL implements DetalleAlmacenInsumoDAO {
    Connection con = null;
    CallableStatement cs;
    int estado;

    @Override
    public void insertar(DetalleAlmacenInsumo detalleAlmacenInsumo,int idInsumo) {
        try{
            con = DriverManager.getConnection(DBManager.url, DBManager.user, DBManager.password);
            cs = con.prepareCall("{call INSERTAR_DETALLE_ALMACEN_INSUMO(?,?,?,?,?,?,?)}");
            cs.setInt("_FK_ID_INSUMO", idInsumo);
            cs.setInt("_FK_ID_ALMACEN", detalleAlmacenInsumo.getAlmacen().getIdAlmacen());
            cs.setInt("_NUM_LOTE", detalleAlmacenInsumo.getnLote());
            cs.setDate("_PERIODO", new java.sql.Date(detalleAlmacenInsumo.getPeriodo().getTime()));
            cs.setInt("_STOCK", detalleAlmacenInsumo.getStock());
            cs.setInt("_CALIDAD", 0);
            
            cs.registerOutParameter("_ID_DET_ALM_INS", java.sql.Types.INTEGER);
            
            cs.executeUpdate();
            
            detalleAlmacenInsumo.setId(cs.getInt("_ID_DET_ALM_INS"));
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            try{con.close();}catch(SQLException ex){System.out.println(ex.getMessage());}
        }
    }

    @Override
    public void actualizar(DetalleAlmacenInsumo detalleAlmacenInsumo) {
        try{
            con = DriverManager.getConnection(DBManager.url, DBManager.user, DBManager.password);
            cs = con.prepareCall("{call ACTUALIZAR_DETALLE_ALMACEN_INSUMO(?,?,?)}");
            cs.setInt("_FK_ID_INSUMO", detalleAlmacenInsumo.getInsumo().getId());
            cs.setInt("_NO_LOTE", detalleAlmacenInsumo.getnLote());
            cs.setInt("_STOCK", detalleAlmacenInsumo.getStock());                    
            cs.executeUpdate();            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            try{con.close();}catch(SQLException ex){System.out.println(ex.getMessage());}
        }
    }

    @Override
    public void eliminar(int id) {
       try{
            con = DriverManager.getConnection(DBManager.url, DBManager.user, DBManager.password);
            cs = con.prepareCall("{call ELIMINAR_DETALLE_ALMACEN_INSUMO(?)}");
            cs.setInt("_ID_DET_ALM_INS", id);
            
           
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            try{con.close();}catch(SQLException ex){System.out.println(ex.getMessage());}
        }
    }

    @Override
    public ArrayList<DetalleAlmacenInsumo> listar(String dato) {
        ArrayList<DetalleAlmacenInsumo> detalleAlmacenInsumo = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBManager.url, DBManager.user, DBManager.password);
            cs = con.prepareCall("{call LISTAR_DETALLE_INSUMO(?)}");
            cs.setString("_NOMBRE", dato);
            ResultSet rs = cs.executeQuery();
            while(rs.next()){
                DetalleAlmacenInsumo  d = new DetalleAlmacenInsumo();
                d.setId(rs.getInt("ID_DET_ALM_INS"));
                Insumo i=new Insumo();                
                i.setId(rs.getInt("ID_INSUMO"));
                i.setNombre(rs.getString("NOMBRE"));
                i.setColor(rs.getString("COLOR"));
                i.setGranularidad(rs.getFloat("GRANULARIDAD"));
                i.setCantidad(rs.getFloat("CANTIDAD"));
                i.setUnidad(rs.getString("UNIDAD"));
                i.setRestriccion(rs.getBoolean("RESTRICCION"));
                d.setInsumo(i);
                /* Guardar insumo */
//                d.getInsumo().setId(rs.getInt("ID_INSUMO"));
//                d.getInsumo().setNombre(rs.getString("NOMBRE"));
//                d.getInsumo().setGranularidad(rs.getFloat("GRANULARIDAD"));
//                d.getInsumo().setColor(rs.getString("COLOR"));
//                d.getInsumo().setCantidad(rs.getFloat("CANTIDAD"));
//                d.getInsumo().setUnidad(rs.getString("UNIDAD"));
                /* Fin guardar insumo */
                d.setnLote(rs.getInt("NUM_LOTE"));
                d.setPeriodo(new java.util.Date(rs.getDate("PERIODO").getTime()));
                d.setStock(rs.getInt("STOCK"));
                /* Guardar Almacen */
//                d.getAlmacen().setIdAlmacen(rs.getInt("ID_ALMACEN"));
//                d.getAlmacen().setTipo(rs.getString("TIPO"));
//                d.getAlmacen().setDireccion(rs.getString("DIRECCION"));
                /* --- Guardar Trabajador */
//                d.getAlmacen().getTrabajador().setId(rs.getInt("ID_TRABAJADOR"));
//                d.getAlmacen().getTrabajador().setNombres(rs.getString("NOMBRES"));
//                d.getAlmacen().getTrabajador().setApellidos(rs.getString("APELLIDOS"));
//                d.getAlmacen().getTrabajador().setDni(rs.getString("DNI"));
//                d.getAlmacen().getTrabajador().setCorreo(rs.getString("CORREO"));
                /* ........ Guardar Rol */
//                d.getAlmacen().getTrabajador().getRol().setIdRol(rs.getInt("ID_ROL"));
//                d.getAlmacen().getTrabajador().getRol().setDescripcion(rs.getString("DESCRIPCION"));
                /* ........ Fin Guardar Rol */
                /* --- Fin Guardar Trabajador */
                /* Fin Guardar Almacen */
                estado = rs.getInt("CALIDAD");
                
                switch (estado) {
                    case 0:
                        d.setEstado(EstadoMaterial.Bueno);
                        break;
                    case 1:
                        d.setEstado(EstadoMaterial.Corregido);
                        break;
                    default:
                        d.setEstado(EstadoMaterial.Pendiente);
                        break;
                }
                
                detalleAlmacenInsumo.add(d);
            }
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            try{con.close();}catch(SQLException ex){System.out.println(ex.getMessage());}
        }
        return detalleAlmacenInsumo;
    }
    
    @Override
    public void actualizarPorOrden(OrdenProduccion orden){
        for (LineaOrden linea: orden.getLineasOrden()){
            ArrayList<LineaInsumo> insumosRequeridos = DBController.listarLineaInsumo(linea.getProducto().getInstructivo().getId());
            for (LineaInsumo lineaInsumo : insumosRequeridos){
                ArrayList<DetalleAlmacenInsumo> almacen = listar(lineaInsumo.getInsumo().getNombre());
                int cantidadRequerida = linea.getCantProducto() * lineaInsumo.getCantInsumo();
                for (DetalleAlmacenInsumo insumoAlmacen : almacen){
                    if (insumoAlmacen.getStock() > cantidadRequerida){
                        insumoAlmacen.setStock(insumoAlmacen.getStock() - cantidadRequerida);
                        actualizar(insumoAlmacen);
                        break;
                    } else if (insumoAlmacen.getStock() == cantidadRequerida) {
                        eliminar(insumoAlmacen.getId());
                        break;
                    } else {
                        eliminar(insumoAlmacen.getId());
                        cantidadRequerida -= insumoAlmacen.getStock();
                    }
                }
        }
        }
    }
}
