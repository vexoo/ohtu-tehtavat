package ohtu.intjoukkosovellus;

import java.util.ArrayList;
import java.util.Arrays;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] luvut;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);

    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kummatkin kapasiteetti ja kasvatuskoko täytyy olla positiivisia");
        }
        this.luvut = new int[kapasiteetti];
        this.kasvatuskoko = kasvatuskoko;
        alkioidenLkm = 0;
    }

    public boolean lisaa(int luku) {
        if (kuuluu(luku)) {
            return false;
        }

        if (alkioidenLkm == this.luvut.length) {
            this.luvut = Arrays.copyOf(luvut, luvut.length + kasvatuskoko);
        }
        luvut[alkioidenLkm] = luku;
        alkioidenLkm++;
        return true;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == luvut[i]) {
                return true;
            }
        }

        return false;
    }

    public boolean poista(int luku) {
        ArrayList<Integer> kopio = new ArrayList<>();
        if (kuuluu(luku)) {
            for (Integer index : luvut) {
                if (!index.equals(luku)) {
                    kopio.add(index);
                }
            }
            luvut = kopio.stream().mapToInt(i -> i).toArray();
            this.alkioidenLkm--;
            return true;
        }

        return false;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        StringBuilder merkkijono = new StringBuilder();

        merkkijono.append("{");
        
        if (alkioidenLkm == 0) {
            merkkijono.append("}");
            return merkkijono.toString();
        }
        
        merkkijono.append(luvut[0]);
        
        for (int i = 1; i < alkioidenLkm; i++) {
            merkkijono.append(", " + luvut[i]);
        }
        
        merkkijono.append("}");
        return merkkijono.toString();
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = luvut[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdiste = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            yhdiste.lisaa(a.luvut[i]);
        }
        for (int i = 0; i < b.mahtavuus(); i++) {
            yhdiste.lisaa(b.luvut[i]);
        }
        return yhdiste;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            for (int j = 0; j < b.mahtavuus(); j++) {
                if (a.luvut[i] == b.luvut[j]) {
                    leikkaus.lisaa(b.luvut[j]);
                }
            }
        }
        return leikkaus;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko erotus = new IntJoukko();

        erotus:
        for (int i = 0; i < a.mahtavuus(); i++) {
            for (int j = 0; j < b.mahtavuus(); j++) {
                if (a.luvut[i] == b.luvut[j]) {
                    continue erotus;
                }
            }

            erotus.lisaa(a.luvut[i]);
        }

        return erotus;
    }

}
