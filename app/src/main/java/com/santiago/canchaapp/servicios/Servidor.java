package com.santiago.canchaapp.servicios;

import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.CanchaHeader;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.EstadoReserva;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.List;

import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;
import static com.santiago.canchaapp.dominio.TipoCancha.FUTBOL5;
import static com.santiago.canchaapp.dominio.TipoCancha.FUTBOL7;
import static com.santiago.canchaapp.dominio.TipoCancha.TENIS;
import static com.santiago.canchaapp.dominio.TipoSuperficie.*;
import static java.util.Arrays.asList;

public class Servidor {

    private static Servidor servidor;

    public static Servidor instancia() {
        if (servidor == null) {
            servidor = new Servidor();
        }
        return servidor;
    }

    private List<Reserva> reservasPendientes = asList(
            new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", 21, PENDIENTE),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 17, PENDIENTE),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 18, PENDIENTE)
    );

    private List<Reserva> reservasAprobadas = asList(
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", 16, APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", 16, APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", 19, APROBADA),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", 15, APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", 21, APROBADA),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", 11, APROBADA),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", 16, APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", 22, APROBADA),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", 14, APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", 22, APROBADA),
                    new Reserva(new Club("Club Atlético Independiente", "Bochini 853"), FUTBOL5, "1/11/17", 22, APROBADA)
            );

    private List<Reserva> reservasCanceladas = asList(
            new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", 20, CANCELADA, "Va a llover")
    );

    private List<Reserva> alquileresAprobados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", 21, APROBADA),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 17, APROBADA),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 18, APROBADA),
                new Reserva(new Club("Mitre", "Una calle con nombre ultra largo"), TENIS, "06/10/17", 19, APROBADA)
        );

    private List<Reserva> alquileresPendientes  = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", 16, PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", 16, PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", 19, PENDIENTE),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", 15, PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", 21, PENDIENTE),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", 11, PENDIENTE),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", 16, PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", 22, PENDIENTE),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", 14, PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", 22, PENDIENTE),
                new Reserva(new Club("Club Atlético Independiente", "Bochini 853"), FUTBOL5, "1/11/17", 22, PENDIENTE),
                new Reserva(new Club("Mitre", "Una calle con nombre ultra largo"), TENIS, "06/10/17", 19, PENDIENTE)
        );

    private List<Reserva> alquileresCancelados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", 20, CANCELADA, "No hay gente"),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL7, "29/09/19", 22, CANCELADA, "No pagaron")
        );

    private List<CanchaHeader> canchas = asList(
                new CanchaHeader("CanchaHeader Central", FUTBOL7, PASTO, false, "http://cancun.gob.mx/obras/files/2013/12/BkZxtXKCUAAAuxe-599x280.jpg"),
                new CanchaHeader("CanchaHeader 1", FUTBOL7, PASTO, false, "http://pastossintetico.com/img/images/cancha-futbol7-pastosintetico-toluca4.jpg"),
                new CanchaHeader("CanchaHeader 2", FUTBOL7, PASTO, false, "https://www.mexicanbusinessweb.mx/wp-content/uploads/2014/09/pastosintetico-lacanchita-futbol7-5.jpg"),
                new CanchaHeader("CanchaHeader chica central", FUTBOL5, BALDOSA, true, "http://www.hoysejuega.com/uploads/Modules/ImagenesComplejos/800_600_captura-de-pantalla-2012-11-29-a-la(s)-15.38.50.png"),
                new CanchaHeader("CanchaHeader chica 1", FUTBOL5, BALDOSA, true, "http://www.platensealoancho.com.ar/web/wp-content/uploads/2013/03/gimnasio-futsal-handball-pintura02.jpg"),
                new CanchaHeader("CanchaHeader chica 2", FUTBOL5, BALDOSA, true, "http://www.pasionfutsal.com.ar/imagenes/noticias/secla.jpg"),
                new CanchaHeader("CanchaHeader chica 3", FUTBOL5, BALDOSA, true, "http://3.bp.blogspot.com/_0SKG4k0u_dI/TTpe4b6DZWI/AAAAAAAABLY/XdF_4pY-l38/s1600/269.JPG"),
                new CanchaHeader("CanchaHeader chica 4", FUTBOL5, BALDOSA, true, "http://www.pasionfutsal.com.ar/imagenes/noticias/almafuerte%20cancha.jpg"),
                new CanchaHeader("CanchaHeader tenis 1", TENIS, POLVO_LADRILLO, false, "http://tenisayh.com.ar/gallery/img_4.jpg"),
                new CanchaHeader("CanchaHeader tenis 2", TENIS, POLVO_LADRILLO, false, "https://ar.all.biz/img/ar/catalog/11918.jpeg")
        );

    public List<Reserva> getReservasPendientes() {
        return reservasPendientes;
    }

    public List<Reserva> getReservasAprobadas() {
        return reservasAprobadas;
    }

    public List<Reserva> getReservasCanceladas() {
        return reservasCanceladas;
    }

    public List<Reserva> getAlquileresAprobados() {
        return alquileresAprobados;
    }

    public List<Reserva> getAlquileresPendientes() {
        return alquileresPendientes;
    }

    public List<Reserva> getAlquileresCancelados() {
        return alquileresCancelados;
    }

    public List<CanchaHeader> getCanchas() {
        return canchas;
    }

    public Cancha getCancha() {
        return new Cancha(new CanchaHeader("Camp Nou", FUTBOL7, PASTO, false, "https://images.clarin.com/2014/01/20/HJH9P2Ii7e_930x525.jpg"),
                asList(
                        "https://www.blaugranas.com/media/galeria/25/8/7/8/3/n_f_c_barcelona_camp_nou-2253878.jpg",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Campnou_1.jpg/1125px-Campnou_1.jpg",
                        "http://www.abc.es/Media/201201/24/estadio-barcelona--644x362.jpg",
                        "http://bolavip.cdnfsn.com/imagenes/670x400/1433363993_barcelona-mes-que-un-club.png",
                        "http://bolavip.cdnfsn.com/imagenes/670x400/1433363993_barcelona-mes-que-un-club.png",
                        "http://www.lapagina.com.sv/userfiles/image/Mosaico.jpg"
                ));
    }

}
