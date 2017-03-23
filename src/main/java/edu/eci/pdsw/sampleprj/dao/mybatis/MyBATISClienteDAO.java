/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.sampleprj.dao.ClienteDAO;
import edu.eci.pdsw.sampleprj.dao.PersistenceException;
import edu.eci.pdsw.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.pdsw.samples.entities.Cliente;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Esteban
 */
public class MyBATISClienteDAO implements ClienteDAO{

    @Inject
    private ClienteMapper clienteMapper;
    
    @Override
    public void save(Cliente c) throws PersistenceException {
       clienteMapper.agregarCliente(c);
    }

    @Override
    public Cliente load(int id) throws PersistenceException {
        try{
            return clienteMapper.consultarCliente(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar el cliente con Documento "+id,e);
        }
    }

    @Override
    public List<Cliente> loadClientes() throws PersistenceException {
        return clienteMapper.consultarClientes();
    }

    @Override
    public void registrarItemRentado(int idcl, int idit, Date fi, Date ff) throws PersistenceException {
        clienteMapper.agregarItemRentadoACliente(idit, idit, fi, ff);
    }
    
    
    
}
