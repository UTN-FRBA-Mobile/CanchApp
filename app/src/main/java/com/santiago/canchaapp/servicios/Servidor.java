package com.santiago.canchaapp.servicios;

import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.SlotReserva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.santiago.canchaapp.dominio.EstadoReserva.APROBADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;
import static com.santiago.canchaapp.dominio.EstadoReserva.PENDIENTE;
import static com.santiago.canchaapp.dominio.Horario.horaDesde;
import static com.santiago.canchaapp.dominio.TipoCancha.BASQUET;
import static com.santiago.canchaapp.dominio.TipoCancha.FUTBOL5;
import static com.santiago.canchaapp.dominio.TipoCancha.FUTBOL7;
import static com.santiago.canchaapp.dominio.TipoCancha.TENIS;
import static com.santiago.canchaapp.dominio.TipoSuperficie.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class Servidor {

    private static Servidor servidor;

    public static Servidor instancia() {
        if (servidor == null) {
            servidor = new Servidor();
        }
        return servidor;
    }

    private List<Reserva> reservasPendientes = asList(
            new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", horaDesde(21), PENDIENTE),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", horaDesde(17), PENDIENTE),
            new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", horaDesde(18), PENDIENTE)
    );

    private List<Reserva> reservasAprobadas = asList(
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", horaDesde(16), APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", horaDesde(16), APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", horaDesde(19), APROBADA),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", horaDesde(15), APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", horaDesde(21), APROBADA),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", horaDesde(11), APROBADA),
                    new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", horaDesde(16), APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", horaDesde(22), APROBADA),
                    new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", horaDesde(14), APROBADA),
                    new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", horaDesde(22), APROBADA),
                    new Reserva(new Club("Club Atlético Independiente", "Bochini 853"), FUTBOL5, "1/11/17", horaDesde(22), APROBADA)
            );

    private List<Reserva> reservasCanceladas = asList(
            new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", horaDesde(20), CANCELADA, "Va a llover")
    );

    private List<Reserva> alquileresAprobados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 1234"), FUTBOL5, "16/09/17", horaDesde(21), APROBADA),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", horaDesde(17), APROBADA),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "06/10/17", horaDesde(18), APROBADA),
                new Reserva(new Club("Mitre", "Una calle con nombre ultra largo"), TENIS, "06/10/17", horaDesde(19), APROBADA)
        );

    private List<Reserva> alquileresPendientes  = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "27/09/17", horaDesde(16), PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "01/10/17", horaDesde(16), PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "02/10/17", horaDesde(19), PENDIENTE),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "03/10/17", horaDesde(15), PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "03/10/17", horaDesde(21), PENDIENTE),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "04/10/17", horaDesde(11), PENDIENTE),
                new Reserva(new Club("Los Troncos", "Italia 2580"), TENIS, "04/10/17", horaDesde(16), PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "04/10/17", horaDesde(22), PENDIENTE),
                new Reserva(new Club("Club Mitre", "Avellaneda 4433"), FUTBOL7, "30/10/17", horaDesde(14), PENDIENTE),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "1/11/17", horaDesde(22), PENDIENTE),
                new Reserva(new Club("Club Atlético Independiente", "Bochini 853"), FUTBOL5, "1/11/17", horaDesde(22), PENDIENTE),
                new Reserva(new Club("Mitre", "Una calle con nombre ultra largo"), TENIS, "06/10/17", horaDesde(19), PENDIENTE)
        );

    private List<Reserva> alquileresCancelados = asList(
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL5, "29/09/17", horaDesde(20), CANCELADA, "No hay gente"),
                new Reserva(new Club("Da Vinci", "Buenos Aires 2358"), FUTBOL7, "29/09/19", horaDesde(22), CANCELADA, "No pagaron")
        );

    private List<Cancha> canchas = asList(
                new Cancha(UUID.randomUUID(), "Cancha Central", FUTBOL7, PASTO, false, singletonList("http://cancun.gob.mx/obras/files/2013/12/BkZxtXKCUAAAuxe-599x280.jpg"), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha 1", FUTBOL7, PASTO, false, singletonList("http://pastossintetico.com/img/images/cancha-futbol7-pastosintetico-toluca4.jpg"), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha 2", FUTBOL7, PASTO, false, singletonList("https://www.mexicanbusinessweb.mx/wp-content/uploads/2014/09/pastosintetico-lacanchita-futbol7-5.jpg"), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha chica central", FUTBOL5, BALDOSA, true, singletonList("http://www.hoysejuega.com/uploads/Modules/ImagenesComplejos/800_600_captura-de-pantalla-2012-11-29-a-la(s)-15.38.50.png"), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha chica 1", FUTBOL5, BALDOSA, true, singletonList("http://www.platensealoancho.com.ar/web/wp-content/uploads/2013/03/gimnasio-futsal-handball-pintura02.jpg"), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha chica 2", FUTBOL5, BALDOSA, true, singletonList("http://www.pasionfutsal.com.ar/imagenes/noticias/secla.jpg"), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha chica 3", FUTBOL5, BALDOSA, true, new ArrayList<String>(), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha chica 4", FUTBOL5, BALDOSA, true, singletonList("http://www.pasionfutsal.com.ar/imagenes/noticias/almafuerte%20cancha.jpg"), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha tenis 1", TENIS, POLVO_LADRILLO, false, new ArrayList<String>(), new Horario(10, 22)),
                new Cancha(UUID.randomUUID(), "Cancha tenis 2", TENIS, POLVO_LADRILLO, false, asList("https://www.blaugranas.com/media/galeria/25/8/7/8/3/n_f_c_barcelona_camp_nou-2253878.jpg",
                                                                                    "https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Campnou_1.jpg/1125px-Campnou_1.jpg",
                                                                                    "http://www.abc.es/Media/201201/24/estadio-barcelona--644x362.jpg"), new Horario(10, 22))
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

    public Club getClub() {
        return new Club(null, "Barcelona", "Carrer d'Aristides Maillol, 12, 08028 Barcelona, Spain",
                null, "oab@fcbarcelona.cat", "+34 934963600", new Horario(9, 22),
                asList(new Cancha(UUID.randomUUID(), "Cancha Central", FUTBOL7, PASTO, false,
                                singletonList("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg/1200px-2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg"), new Horario(10, 22)),
                        new Cancha(UUID.randomUUID(), "Cancha Inclinada", FUTBOL7, PASTO, false,
                                singletonList("http://oear.cippec.org/wp-content/uploads/2015/02/cancha7.jpg"), new Horario(10, 22))
                ));
    }

    private List<Reserva> getReservasCanchaDia(int dia) {
        Club club = getClub();
        switch (dia) {
            case 0:
                return asList(
                        new Reserva(club, FUTBOL7, "08/10/17", horaDesde(16), PENDIENTE, null, "juancito"),
                        new Reserva(club, FUTBOL7, "08/10/17", horaDesde(17), APROBADA, null, "juancito"),
                        new Reserva(club, FUTBOL7, "08/10/17", horaDesde(20), PENDIENTE, null, "xavi"),
                        new Reserva(club, FUTBOL7, "08/10/17", horaDesde(21), APROBADA, null, "piqué")
                );
            case 1:
                return asList(
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(10), APROBADA, null, "messi"),
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(11), APROBADA, null, "messi"),
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(12), PENDIENTE, null, "iniesta"),
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(13), APROBADA, null, "messi"),
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(14), APROBADA, null, "messi"),
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(17), PENDIENTE, null, "suarez"),
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(20), PENDIENTE, null, "alguien"),
                        new Reserva(club, FUTBOL7, "09/10/17", horaDesde(21), APROBADA, null, "mascherano")
                );
            case 2:
                return asList(
                        new Reserva(club, FUTBOL7, "10/10/17", horaDesde(12), PENDIENTE, null, "messi"),
                        new Reserva(club, FUTBOL7, "10/10/17", horaDesde(14), PENDIENTE, null, "messi"),
                        new Reserva(club, FUTBOL7, "10/10/17", horaDesde(17), PENDIENTE, null, "suarez"),
                        new Reserva(club, FUTBOL7, "10/10/17", horaDesde(20), APROBADA, null, "alguien"),
                        new Reserva(club, FUTBOL7, "10/10/17", horaDesde(21), APROBADA, null, "mascherano")
                );
            case 3:
                return asList(
                        new Reserva(club, FUTBOL7, "11/10/17", horaDesde(19), PENDIENTE, null, "suarez")
                );
        }
        return new ArrayList<>();
    }

    public List<SlotReserva> getHorarios(Horario rangoHorario, int dia) {
        List<Reserva> reservas = getReservasCanchaDia(dia);
        List<SlotReserva> horarios = new ArrayList<>();
        for (int h = rangoHorario.getDesde(); h < rangoHorario.getHasta(); h++) {
            horarios.add(new SlotReserva(horaDesde(h), reservaEnHorario(h, reservas)));
        }
        return horarios;
    }

    private Reserva reservaEnHorario(int hora, List<Reserva> reservas) {
        for (Reserva reserva: reservas) {
            if (reserva.getHorario().getDesde() == hora) {
                return reserva;
            }
        }
        return null;
    }

    public List<Club> getClubes() {
        return asList(
            new Club(null, "Barcelona", "Carrer d'Aristides Maillol, 12, 08028 Barcelona, Spain",
                    null, "oab@fcbarcelona.cat", "+34 934963600", new Horario(9, 22),
                    asList(new Cancha(UUID.randomUUID(), "Cancha Central", FUTBOL7, PASTO, false,
                                    singletonList("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg/1200px-2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg"), new Horario(10, 22)),
                            new Cancha(UUID.randomUUID(), "Cancha Inclinada", FUTBOL7, PASTO, false,
                                    singletonList("http://oear.cippec.org/wp-content/uploads/2015/02/cancha7.jpg"), new Horario(10, 22))
                            )
            ),
            new Club(null, "Real Madrid", "Av. de Concha Espina, 1, 28036 Madrid, Spain",
                    null, "oas@realmadrid.com", "+34 913984300", new Horario(13, 20),
                    asList(new Cancha(UUID.randomUUID(), "Santiago Bernabeu", FUTBOL5, PASTO, false,
                                    singletonList("https://www.esmadrid.com/sites/default/files/styles/content_type_full/public/recursosturisticos/infoturistica/BernabeuEstadio_1412599011.407.jpg?itok=9J7z_5Xl"), new Horario(10, 22)),
                            new Cancha(UUID.randomUUID(), "Estadio suplente", FUTBOL7, PASTO, false,
                                    singletonList("https://norcasiacaldas.files.wordpress.com/2011/06/cancha-alterna-de-barro-2.jpg?w=800"), new Horario(10, 22)),
                            new Cancha(UUID.randomUUID(), "Cancha de basquet flashera", BASQUET, BALDOSA, false,
                                    singletonList("http://www.sopitas.com/wp-content/uploads/2014/08/nikes-house-of-mamba-led-basketball-court-2-e1408304807172.jpg"), new Horario(10, 22)),
                            new Cancha(UUID.randomUUID(), "Mario Tennis 64", TENIS, PASTO, false,
                                    singletonList("http://wiimedia.ign.com/wii/image/article/110/1103094/MarioTennisInline0_1277934772.jpg"), new Horario(10, 22))
                            )
            ),
            new Club(null, "Chelsea", "Stamford Bridge, Fulham Road, London, SW6 1HS",
                    null, "contact@chelsea.com", "00 44 20 7835 6000", new Horario(9, 17),
                    asList(new Cancha(UUID.randomUUID(), "Stamford Bridge", FUTBOL7, PASTO, false,
                                    singletonList("http://www.chelseafc.com/content/cfc/en/homepage/the-club/stadium-tours-and-museum/educational-visits/_jcr_content.autoteaser.jpeg"), new Horario(10, 22)),
                            new Cancha(UUID.randomUUID(), "Cancha Desproporcionada", FUTBOL7, PASTO, false,
                                    singletonList("https://pbs.twimg.com/media/Ct3VBvfXgAAxudR.jpg"), new Horario(10, 22)),
                            new Cancha (UUID.randomUUID(), "Cancha Inundada", FUTBOL5, PASTO, false,
                                    singletonList("http://images.performgroup.com/di/library/Goal_Argentina/dd/4/cancha-lanus-inundada-fortaleza_12dq5dpkb9gds13clt73f8nq9y.jpg?t=1561520289&w=620&h=430"), new Horario(10, 22))
                            )
            )
        );
    }

    public Club miClub() {
        return new Club(null, "Nuestro Club", "Medrano 955, Capital Federal",
                null, "mail@canchapp.com", "03 03 456", new Horario(9, 22), canchas);
    }
}
