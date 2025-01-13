package tpvv.dto;

import tpvv.model.Pago;

import java.util.Date;
import java.util.Objects;

public class PagoRecursoData {

    private Long id;
    private String ticketExt;

    private Date fecha;

    private double importe;

    private ComercioData comercioData;

    private EstadoPagoData estadoPagoData;

    private TarjetaPagoData tarjetaPagoData;

    private String shownState;

    private String formatedCardNumber;


    private IncidenciaData incidenciaData;

    // Constructor vacío
    public PagoRecursoData() {}

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketExt() {
        return ticketExt;
    }

    public void setTicketExt(String ticketExt) {
        this.ticketExt = ticketExt;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public ComercioData getComercioData() {
        return comercioData;
    }

    public void setComercioData(ComercioData comercioData) {
        this.comercioData = comercioData;
    }

    public EstadoPagoData getEstadoPagoData() {
        return estadoPagoData;
    }

    public void setEstadoPagoData(EstadoPagoData estadoPagoData) {
        this.estadoPagoData = estadoPagoData;
    }

    public TarjetaPagoData getTarjetaPagoData() {
        return tarjetaPagoData;
    }

    public void setTarjetaPagoData(TarjetaPagoData tarjetaPagoData) {
        this.tarjetaPagoData = tarjetaPagoData;
    }

    public String getShownState() {
        return shownState;
    }

    public void setShownState(String shownState) {
        this.shownState = shownState;
    }

    public String getFormatedCardNumber() {
        return formatedCardNumber;
    }

    public void setFormatedCardNumber(String formatedCardNumber) {
        this.formatedCardNumber = formatedCardNumber;
    }

    public IncidenciaData getIncidenciaData() {return incidenciaData;}

    public void setIncidenciaData(IncidenciaData incidenciaData) {this.incidenciaData = incidenciaData;}

    // Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagoRecursoData that = (PagoRecursoData) o;

        // Si ambos objetos tienen un ID no nulo, comparamos por ID
        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }

        // Si no se pueden comparar por ID, consideramos que son diferentes
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
