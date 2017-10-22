package com.santiago.canchaapp.servicios;

import com.google.android.gms.maps.model.LatLng;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;

import java.util.ArrayList;
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
import static com.santiago.canchaapp.dominio.TipoSuperficie.BALDOSA;
import static com.santiago.canchaapp.dominio.TipoSuperficie.PASTO;
import static com.santiago.canchaapp.dominio.TipoSuperficie.POLVO_LADRILLO;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class Servidor {

    private static Servidor servidor;

    public static Servidor instancia() {
        if (servidor == null) {
            servidor = new Servidor();
        }
        return servidor;
    }

    private List<Reserva> reservasPendientes = asList(
            new Reserva(getClub(), FUTBOL5, "16/09/17", horaDesde(21), PENDIENTE),
            new Reserva(getClub(), TENIS, "06/10/17", horaDesde(17), PENDIENTE),
            new Reserva(getClub(), TENIS, "06/10/17", horaDesde(18), PENDIENTE)
    );

    private List<Reserva> reservasAprobadas = asList(
                    new Reserva(getClub(), FUTBOL5, "27/09/17", horaDesde(16), APROBADA),
                    new Reserva(getClub(), FUTBOL5, "01/10/17", horaDesde(16), APROBADA),
                    new Reserva(getClub(), FUTBOL5, "02/10/17", horaDesde(19), APROBADA),
                    new Reserva(getClub(), TENIS, "03/10/17", horaDesde(15), APROBADA),
                    new Reserva(getClub(), FUTBOL5, "03/10/17", horaDesde(21), APROBADA),
                    new Reserva(getClub(), FUTBOL7, "04/10/17", horaDesde(11), APROBADA),
                    new Reserva(getClub(), TENIS, "04/10/17", horaDesde(16), APROBADA),
                    new Reserva(getClub(), FUTBOL5, "04/10/17", horaDesde(22), APROBADA),
                    new Reserva(getClub(), FUTBOL7, "30/10/17", horaDesde(14), APROBADA),
                    new Reserva(getClub(), FUTBOL5, "1/11/17", horaDesde(22), APROBADA),
                    new Reserva(getClub(), FUTBOL5, "1/11/17", horaDesde(22), APROBADA)
            );

    private List<Reserva> reservasCanceladas = asList(
            new Reserva(getClub(), FUTBOL5, "29/09/17", horaDesde(20), CANCELADA, "Va a llover")
    );

    private List<Reserva> alquileresAprobados = asList(
                new Reserva(getClub(), FUTBOL5, "16/09/17", horaDesde(21), APROBADA),
                new Reserva(getClub(), TENIS, "06/10/17", horaDesde(17), APROBADA),
                new Reserva(getClub(), TENIS, "06/10/17", horaDesde(18), APROBADA),
                new Reserva(getClub(), TENIS, "06/10/17", horaDesde(19), APROBADA)
        );

    private List<Reserva> alquileresPendientes  = asList(
                new Reserva(getClub(), FUTBOL5, "27/09/17", horaDesde(16), PENDIENTE),
                new Reserva(getClub(), FUTBOL5, "01/10/17", horaDesde(16), PENDIENTE),
                new Reserva(getClub(), FUTBOL5, "02/10/17", horaDesde(19), PENDIENTE),
                new Reserva(getClub(), TENIS, "03/10/17", horaDesde(15), PENDIENTE),
                new Reserva(getClub(), FUTBOL5, "03/10/17", horaDesde(21), PENDIENTE),
                new Reserva(getClub(), FUTBOL7, "04/10/17", horaDesde(11), PENDIENTE),
                new Reserva(getClub(), TENIS, "04/10/17", horaDesde(16), PENDIENTE),
                new Reserva(getClub(), FUTBOL5, "04/10/17", horaDesde(22), PENDIENTE),
                new Reserva(getClub(), FUTBOL7, "30/10/17", horaDesde(14), PENDIENTE),
                new Reserva(getClub(), FUTBOL5, "1/11/17", horaDesde(22), PENDIENTE),
                new Reserva(getClub(), FUTBOL5, "1/11/17", horaDesde(22), PENDIENTE),
                new Reserva(getClub(), TENIS, "06/10/17", horaDesde(19), PENDIENTE)
        );

    private List<Reserva> alquileresCancelados = asList(
                new Reserva(getClub(), FUTBOL5, "29/09/17", horaDesde(20), CANCELADA, "No hay gente"),
                new Reserva(getClub(), FUTBOL7, "29/09/19", horaDesde(22), CANCELADA, "No pagaron")
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
        UUID idClub = fromString("77b86fc9-555e-4cc1-9e5e-277e8e92d990");
        return asList(
                new Cancha(fromString("43c5e100-b593-4dfc-834e-cda34cf8d6ee"), "Cancha Central", FUTBOL7, PASTO, false, singletonList("http://cancun.gob.mx/obras/files/2013/12/BkZxtXKCUAAAuxe-599x280.jpg"), idClub, new Horario(10, 22)),
                new Cancha(fromString("d4789664-89a8-4574-a4ab-bdc49dc49ba7"), "Cancha 1", FUTBOL7, PASTO, false, singletonList("http://pastossintetico.com/img/images/cancha-futbol7-pastosintetico-toluca4.jpg"), idClub, new Horario(10, 22)),
                new Cancha(fromString("bc06a2d5-b86e-4eeb-94f7-edefc63398a3"), "Cancha 2", FUTBOL7, PASTO, false, singletonList("https://www.mexicanbusinessweb.mx/wp-content/uploads/2014/09/pastosintetico-lacanchita-futbol7-5.jpg"), idClub, new Horario(10, 22)),
                new Cancha(fromString("8bed2b62-179b-4459-aaea-bb3cc397af81"), "Cancha chica central", FUTBOL5, BALDOSA, true, singletonList("http://www.hoysejuega.com/uploads/Modules/ImagenesComplejos/800_600_captura-de-pantalla-2012-11-29-a-la(s)-15.38.50.png"), idClub, new Horario(10, 22)),
                new Cancha(fromString("f02f7b53-c410-492f-8d5d-45230ec6747a"), "Cancha chica 1", FUTBOL5, BALDOSA, true, singletonList("http://www.platensealoancho.com.ar/web/wp-content/uploads/2013/03/gimnasio-futsal-handball-pintura02.jpg"), idClub, new Horario(10, 22)),
                new Cancha(fromString("fbb8e08e-4bbe-4d38-90bf-b76efdb2a442"), "Cancha chica 2", FUTBOL5, BALDOSA, true, singletonList("http://www.pasionfutsal.com.ar/imagenes/noticias/secla.jpg"), idClub, new Horario(10, 22)),
                new Cancha(fromString("3cb1e133-4f27-4a5f-b199-7ce90bd2c223"), "Cancha chica 3", FUTBOL5, BALDOSA, true, new ArrayList<String>(), idClub, new Horario(10, 22)),
                new Cancha(fromString("c5f42db9-1dae-4fdf-97a8-e643d954d821"), "Cancha chica 4", FUTBOL5, BALDOSA, true, singletonList("http://www.pasionfutsal.com.ar/imagenes/noticias/almafuerte%20cancha.jpg"), idClub, new Horario(10, 22)),
                new Cancha(fromString("3a224458-96f6-4c28-b410-bb32f09f469b"), "Cancha tenis 1", TENIS, POLVO_LADRILLO, false, new ArrayList<String>(), idClub, new Horario(10, 22)),
                new Cancha(fromString("ae83d269-1ed7-455c-b108-116240f1ba38"), "Cancha tenis 2", TENIS, POLVO_LADRILLO, false, asList("https://www.blaugranas.com/media/galeria/25/8/7/8/3/n_f_c_barcelona_camp_nou-2253878.jpg",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Campnou_1.jpg/1125px-Campnou_1.jpg",
                        "http://www.abc.es/Media/201201/24/estadio-barcelona--644x362.jpg"), idClub, new Horario(10, 22))
        );
    }

    public Club getClub() {
        UUID idClubBarcelona = fromString("ac452950-3f5b-419a-a36d-73057a76f81b");
        return new Club(idClubBarcelona, "Barcelona", "Carrer d'Aristides Maillol, 12, 08028 Barcelona, Spain",
                new LatLng(-34.603371, -58.451497), "oab@fcbarcelona.cat", "+34 934963600", new Horario(9, 22),
                asList(new Cancha(fromString("05da8cde-ce21-4407-b40d-edb1b803839a"), "Cancha Central", FUTBOL7, PASTO, false,
                                singletonList("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg/1200px-2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg"), idClubBarcelona, new Horario(10, 22)),
                        new Cancha(fromString("b36b5704-3abe-425d-9c3c-e1a965935657"), "Cancha Inclinada", FUTBOL7, PASTO, false,
                                singletonList("http://oear.cippec.org/wp-content/uploads/2015/02/cancha7.jpg"), idClubBarcelona, new Horario(10, 22))
                ));
    }

    public List<Club> getClubes() {
        UUID idClubBarcelona = fromString("ac452950-3f5b-419a-a36d-73057a76f81b");
        UUID idClubReal = fromString("3b8d3160-6927-48d2-98d7-ac61864cbabb");
        UUID idClubChelsea = fromString("a048abd3-10bd-4f49-8dd4-fd15cee8512b");
        return asList(
            new Club(idClubBarcelona, "Barcelona", "Carrer d'Aristides Maillol, 12, 08028 Barcelona, Spain",
                    new LatLng(-34.603371, -58.451497), "oab@fcbarcelona.cat", "+34 934963600", new Horario(9, 22),
                    asList(new Cancha(fromString("a991a22f-bafb-450f-80cc-a3d7bd12ca4c"), "Cancha Central", FUTBOL7, PASTO, false,
                                    singletonList("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg/1200px-2014._Camp_Nou._M%C3%A9s_que_un_club._Barcelona_B40.jpg"), idClubBarcelona, new Horario(9, 22)),
                            new Cancha(fromString("fe62bc0b-aa10-4611-abc4-198451d05012"), "Cancha Inclinada", FUTBOL7, PASTO, false,
                                    singletonList("http://oear.cippec.org/wp-content/uploads/2015/02/cancha7.jpg"), idClubBarcelona, new Horario(9, 22))
                            )
            ),
            new Club(idClubReal, "Real Madrid", "Av. de Concha Espina, 1, 28036 Madrid, Spain",
                    new LatLng(-34.603371, -58.451497), "oas@realmadrid.com", "+34 913984300", new Horario(13, 20),
                    asList(new Cancha(fromString("392b2443-c28f-4554-94d5-9e5e9b30fcaf"), "Santiago Bernabeu", FUTBOL5, PASTO, false,
                                    singletonList("https://www.esmadrid.com/sites/default/files/styles/content_type_full/public/recursosturisticos/infoturistica/BernabeuEstadio_1412599011.407.jpg?itok=9J7z_5Xl"), idClubReal, new Horario(13, 20)),
                            new Cancha(fromString("4c3d1d86-f6fc-4860-a40c-e9b792597083"), "Estadio suplente", FUTBOL7, PASTO, false,
                                    singletonList("https://norcasiacaldas.files.wordpress.com/2011/06/cancha-alterna-de-barro-2.jpg?w=800"), idClubReal, new Horario(13, 20)),
                            new Cancha(fromString("584c2c20-6afd-4da7-b7d0-abbbdc41186c"), "Cancha de basquet flashera", BASQUET, BALDOSA, false,
                                    singletonList("http://www.sopitas.com/wp-content/uploads/2014/08/nikes-house-of-mamba-led-basketball-court-2-e1408304807172.jpg"), idClubReal, new Horario(13, 20)),
                            new Cancha(fromString("9e64f101-cf82-4be3-aa59-b33903566846"), "Mario Tennis 64", TENIS, PASTO, false,
                                    singletonList("http://wiimedia.ign.com/wii/image/article/110/1103094/MarioTennisInline0_1277934772.jpg"), idClubReal, new Horario(13, 20))
                            )
            ),
            new Club(idClubChelsea, "Chelsea", "Stamford Bridge, Fulham Road, London, SW6 1HS",
                    new LatLng(-34.603371, -58.451497), "contact@chelsea.com", "00 44 20 7835 6000", new Horario(9, 17),
                    asList(new Cancha(fromString("0ffeb129-b54a-4955-8e4a-61ce03c1de6b"), "Stamford Bridge", FUTBOL7, PASTO, false,
                                    singletonList("http://www.chelseafc.com/content/cfc/en/homepage/the-club/stadium-tours-and-museum/educational-visits/_jcr_content.autoteaser.jpeg"), idClubChelsea, new Horario(9, 17)),
                            new Cancha(fromString("1e06f441-8c51-4aa1-9ee7-d7fa4d4664ff"), "Cancha Desproporcionada", FUTBOL7, PASTO, false,
                                    singletonList("https://pbs.twimg.com/media/Ct3VBvfXgAAxudR.jpg"), idClubChelsea, new Horario(9, 17)),
                            new Cancha (fromString("43196d14-cde8-4492-9340-76d369b33d2e"), "Cancha Inundada", FUTBOL5, PASTO, false,
                                    singletonList("http://images.performgroup.com/di/library/Goal_Argentina/dd/4/cancha-lanus-inundada-fortaleza_12dq5dpkb9gds13clt73f8nq9y.jpg?t=1561520289&w=620&h=430"), idClubChelsea, new Horario(9, 17))
                            )
            ),
            miClub()
        );
    }

    public Club miClub() {
        return new Club(fromString("6d5aa173-2227-42f2-aab3-d4542605fef1"), "Nuestro Club", "Medrano 955, Capital Federal",
                new LatLng(-34.603371, -58.451497), "mail@canchapp.com", "03 03 456", new Horario(9, 22), getCanchas());
    }
}
