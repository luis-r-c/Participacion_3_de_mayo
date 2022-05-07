<%
    String option = request.getParameter("option");
%>

    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <a href="Logout" class="btn btn-danger">Cerrar sesion</a>
    </div>
<ul class="nav nav-tabs">
    <li class="nav-item">
        <a class="nav-link <%=(option.equals("productos") ? "active" : "")%>" href="ProductoControlador">Productos</a>
    </li>
    <li class="nav-item">
        <a class="nav-link <%=(option.equals("clientes") ? "active" : "")%>" href="ClienteControlador">Clientes</a>
    </li>
    <li class="nav-item">
        <a class="nav-link <%=(option.equals("ventas") ? "active" : "")%>" href="VentaControlador">Ventas</a>
    </li>
</ul>
