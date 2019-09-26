/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp2tecnoquim.dao;

import java.util.ArrayList;
import lp2tecnoquim.model.PlanMaestroProduccion;


/**
 *
 * @author alulab14
 */
public interface PlanMaestroProduccionDAO {
    
    void insertar(PlanMaestroProduccion plan);
    void actualizar(PlanMaestroProduccion plan);
    void eliminar(int id);
    ArrayList<PlanMaestroProduccion> listar();
    
}
