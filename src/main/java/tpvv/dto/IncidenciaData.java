package tpvv.dto;

import tpvv.model.Mensaje;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class IncidenciaData {

    private Long id;
    private Date fecha;
    private String titulo;
    private String descripcion;
    private int valoracion;
    private String razon_valoracion;
    private UsuarioData usuarioComercio;
    private UsuarioData usuarioTecnico;
    private Long pago_id;
    private EstadoIncidenciaData estado;
    private Set<MensajeData> mensajes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public String  getFechaFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        return formatter.format(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getRazon_valoracion() {
        return razon_valoracion;
    }

    public void setRazon_valoracion(String razon_valoracion) {
        this.razon_valoracion = razon_valoracion;
    }

    public UsuarioData getUsuarioComercio() {return usuarioComercio;}

    public void setUsuarioComercio(UsuarioData usuarioComercio) {this.usuarioComercio = usuarioComercio;}

    public UsuarioData getUsuarioTecnico() {return usuarioTecnico;}

    public void setUsuarioTecnico(UsuarioData usuarioTecnico) {this.usuarioTecnico = usuarioTecnico;}

    public Long getPago_id() {return pago_id;}

    public void setPago_id(Long pago_id) {this.pago_id = pago_id;}

    public EstadoIncidenciaData getEstado() {return estado;}

    public void setEstado(EstadoIncidenciaData estado_incidencia) {this.estado = estado_incidencia;}
    public Set<MensajeData> getMensajes() {
        return mensajes;
    }

    public void setMensajes(MensajeData mensaje) {
        if (mensajes.contains(mensaje)) return;
        mensajes.add(mensaje);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncidenciaData that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
