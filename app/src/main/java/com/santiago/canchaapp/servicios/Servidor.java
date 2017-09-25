package com.santiago.canchaapp.servicios;

import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.TipoCancha;
import com.santiago.canchaapp.dominio.TipoSuperficie;

import java.util.List;

import static com.santiago.canchaapp.dominio.TipoCancha.*;
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
            new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", 21),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 17),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 18)
    );

    private List<Reserva> reservasAprobadas = asList(
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", 16),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", 16),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", 19),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", 15),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", 21),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", 11),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", 16),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", 22),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", 14),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", 22),
                    new Reserva(new Club("Club Atlético Independiente", "Bochini 853"), FUTBOL5, "1/11/17", 22)
            );

    private List<Reserva> reservasCanceladas = asList(
            new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", 20)
    );

    private List<Reserva> alquileresAprobados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", 21),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 17),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", 18),
                new Reserva(new Club("Mitre", "Una calle con nombre ultra largo"), TENIS, "06/10/17", 19)
        );

    private List<Reserva> alquileresPendientes  = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", 16),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", 16),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", 19),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", 15),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", 21),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", 11),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", 16),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", 22),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", 14),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", 22),
                new Reserva(new Club("Club Atlético Independiente", "Bochini 853"), FUTBOL5, "1/11/17", 22),
                new Reserva(new Club("Mitre", "Una calle con nombre ultra largo"), TENIS, "06/10/17", 19)
        );

    private List<Reserva> alquileresCancelados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", 20),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL7, "29/09/19", 22)
        );

    private List<Cancha> canchas = asList(
                new Cancha("Cancha Central", FUTBOL7, PASTO, false, "http://cancun.gob.mx/obras/files/2013/12/BkZxtXKCUAAAuxe-599x280.jpg"),
                new Cancha("Cancha 1", FUTBOL7, PASTO, false, "http://pastossintetico.com/img/images/cancha-futbol7-pastosintetico-toluca4.jpg"),
                new Cancha("Cancha 2", FUTBOL7, PASTO, false, "https://www.mexicanbusinessweb.mx/wp-content/uploads/2014/09/pastosintetico-lacanchita-futbol7-5.jpg"),
                new Cancha("Cancha chica central", FUTBOL5, BALDOSA, true, "http://www.hoysejuega.com/uploads/Modules/ImagenesComplejos/800_600_captura-de-pantalla-2012-11-29-a-la(s)-15.38.50.png"),
                new Cancha("Cancha chica 1", FUTBOL5, BALDOSA, true, "http://www.platensealoancho.com.ar/web/wp-content/uploads/2013/03/gimnasio-futsal-handball-pintura02.jpg"),
                new Cancha("Cancha chica 2", FUTBOL5, BALDOSA, true, "http://www.pasionfutsal.com.ar/imagenes/noticias/secla.jpg"),
                new Cancha("Cancha chica 3", FUTBOL5, BALDOSA, true, "http://3.bp.blogspot.com/_0SKG4k0u_dI/TTpe4b6DZWI/AAAAAAAABLY/XdF_4pY-l38/s1600/269.JPG"),
                new Cancha("Cancha chica 4", FUTBOL5, BALDOSA, true, "http://www.pasionfutsal.com.ar/imagenes/noticias/almafuerte%20cancha.jpg"),
                new Cancha("Cancha tenis 1", TENIS, POLVO_LADRILLO, false, "http://tenisayh.com.ar/gallery/img_4.jpg"),
                new Cancha("Cancha tenis 2", TENIS, POLVO_LADRILLO, false, "https://ar.all.biz/img/ar/catalog/11918.jpeg")
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

    public List<Cancha> getCanchas() {
        return canchas;
    }

}
