package com.emergentes.controlador;

import com.emergentes.dao.ClienteDAO;
import com.emergentes.dao.ClienteDAOimpl;
import com.emergentes.dao.ProductoDAO;
import com.emergentes.dao.ProductoDAOimpl;
import com.emergentes.dao.VentaDAO;
import com.emergentes.dao.VentaDAOimpl;
import com.emergentes.modelo.Cliente;
import com.emergentes.modelo.Producto;
import com.emergentes.modelo.Venta;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VentaControlador", urlPatterns = {"/VentaControlador"})
public class VentaControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VentaDAO dao = new VentaDAOimpl();
        ProductoDAO daoProducto = new ProductoDAOimpl();
        ClienteDAO daoCliente = new ClienteDAOimpl();
        int id;
        List<Producto> lista_productos = null;
        List<Cliente> lista_clientes = null;
        Venta venta = new Venta();
        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";
        switch (action) {
            case "add":
                try {
                    lista_productos = daoProducto.getAll();
                    lista_clientes = daoCliente.getAll();
                } catch (Exception ex) {
                    Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("lista_productos", lista_productos);
                request.setAttribute("lista_clientes", lista_clientes);
                request.setAttribute("venta", venta);
                request.getRequestDispatcher("frmventa.jsp").forward(request, response);
                break;

            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                 {
                    try {
                        venta = dao.getById(id);
                    } catch (Exception ex) {
                        Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                try {
                    lista_productos = daoProducto.getAll();
                    lista_clientes = daoCliente.getAll();
                } catch (Exception ex) {
                    Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.setAttribute("lista_productos", lista_productos);
                request.setAttribute("lista_clientes", lista_clientes);
                request.setAttribute("venta", venta);
                request.getRequestDispatcher("frmventa.jsp").forward(request, response);
                break;

            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                try {
                    dao.delete(id);
                } catch (Exception ex) {
                    System.out.println("Erro al eliminar: " + ex.getMessage());
                }
                response.sendRedirect("VentaControlador");
                break;
            case "view":
                List<Venta> lista = new ArrayList<Venta>();
                try {
                    lista = dao.getAll();
                } catch (Exception ex) {
                    System.out.println("Error al listar " + ex.getMessage());
                }
                request.setAttribute("ventas", lista);
                request.getRequestDispatcher("ventas.jsp").forward(request, response);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int cliente_id = Integer.parseInt(request.getParameter("cliente_id"));
        int producto_id = Integer.parseInt(request.getParameter("producto_id"));
        String fecha = request.getParameter("fecha");

        Venta avi = new Venta();

        avi.setId(id);
        avi.setCliente_id(cliente_id);
        avi.setProducto_id(producto_id);
        avi.setFecha(convierteFecha(fecha));

        VentaDAO dao = new VentaDAOimpl();

        if (id == 0) {
            try {
                // Nuevo
                dao.insert(avi);
                response.sendRedirect("VentaControlador");
            } catch (Exception ex) {
                Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                // Edición
                dao.update(avi);
                response.sendRedirect("VentaControlador");
            } catch (Exception ex) {
                Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Date convierteFecha(String fecha) {
        Date fechaBD = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fechaTMP;
        try {
            fechaTMP = formato.parse(fecha);
            fechaBD = new Date(fechaTMP.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(VentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaBD;
    }
}
