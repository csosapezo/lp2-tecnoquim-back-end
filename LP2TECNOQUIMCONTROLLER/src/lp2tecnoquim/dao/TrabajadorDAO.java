/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp2tecnoquim.dao;

import java.util.ArrayList;
import lp2tecnoquim.model.Trabajador;


/**
 *
 * @author alulab14
 */
public interface TrabajadorDAO {
    
    void insertar(Trabajador trabajador);
    void actualizar(Trabajador trabajador);
    void eliminar(int id);
    ArrayList<Trabajador> listar();
    
}
