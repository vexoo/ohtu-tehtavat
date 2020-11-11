package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Pankki pankki;
    Varasto varasto;
    Viitegeneraattori viiteg;
    Kauppa kauppa;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        varasto = mock(Varasto.class);
        viiteg = mock(Viitegeneraattori.class);
        kauppa = new Kauppa(varasto, pankki, viiteg);

    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        when(viiteg.uusi()).thenReturn(35);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        kauppa.tilimaksu("pekka", "35");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("35"), anyString(), eq(5));
    }

    @Test
    public void kahdenEriTuotteenOstos() {
        when(viiteg.uusi()).thenReturn(36);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 6));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("pekka", "36");

        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("36"), anyString(), eq(11));
    }

    @Test
    public void kahdenSamanTuotteenOstos() {
        when(viiteg.uusi()).thenReturn(37);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "37");

        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("37"), anyString(), eq(10));
    }

    @Test
    public void kahdenEriTuotteenOstosJoistaYksiLoppu() {
        when(viiteg.uusi()).thenReturn(38);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 6));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("pekka", "38");

        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("38"), anyString(), eq(5));
    }

    @Test
    public void koristaPoisto() {
        when(viiteg.uusi()).thenReturn(39);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 6));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.poistaKorista(1);
        kauppa.tilimaksu("pekka", "39");

        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("39"), anyString(), eq(6));
    }

}
