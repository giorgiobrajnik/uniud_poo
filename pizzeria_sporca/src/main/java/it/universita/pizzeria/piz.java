package it.universita.pizzeria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class piz {
    public ArrayList<T> t = new ArrayList<>();
    public ArrayList<C> c = new ArrayList<>();
    public ArrayList<P> m = new ArrayList<>();
    public ArrayList<S> s = new ArrayList<>();
    public ArrayList<O> o = new ArrayList<>();
    public long ns = 1;
    public long no = 1;

    public static class T {
        public int n;
        public int p;
        public boolean i;

        public T(int n, int p, boolean i) {
            this.n = n;
            this.p = p;
            this.i = i;
        }
    }

    public static class C {
        public String c;
        public String n;
        public String x;
        public ArrayList<Integer> z = new ArrayList<>();

        public C(String c, String n, String x) {
            this.c = c;
            this.n = n;
            this.x = x;
        }
    }

    public static class P {
        public String c;
        public String n;
        public String d;
        public double p;
        public String k;
        public String g;

        public P(String c, String n, String d, double p, String k, String g) {
            this.c = c;
            this.n = n;
            this.d = d;
            this.p = p;
            this.k = k;
            this.g = g;
        }

        public boolean ok(LocalDateTime x) {
            return g.contains(x.getDayOfWeek().name());
        }
    }

    public static class S {
        public long n;
        public ArrayList<Integer> t;
        public String c;
        public int p;

        public S(long n, List<Integer> t, String c, int p) {
            this.n = n;
            this.t = new ArrayList<>(t);
            this.c = c;
            this.p = p;
        }
    }

    public static class R {
        public P p;
        public int q;

        public R(P p, int q) {
            this.p = p;
            this.q = q;
        }
    }

    public static class O {
        public long n;
        public long s;
        public LocalDateTime q;
        public ArrayList<Integer> t;
        public String c;
        public int cop;
        public ArrayList<R> r = new ArrayList<>();
        public String stato = "IN_PREPARAZIONE";
        public String pagamento;
        public LocalDateTime pagato;

        public O(long n, long s, LocalDateTime q, List<Integer> t, String c, int cop) {
            this.n = n;
            this.s = s;
            this.q = q;
            this.t = new ArrayList<>(t);
            this.c = c;
            this.cop = cop;
        }

        public double tot() {
            double x = 0;
            for (R a : r) {
                x = x + a.p.p * a.q;
            }
            return x;
        }
    }

    public static class X {
        public String c;
        public String nome;
        public int tavoli;
        public int coperti;
        public double lordo;
        public double pizze;
        public double antipasti;
        public double primi;
        public double carne;
        public double pesce;
        public double contorni;
        public double dolci;
        public double bevande;
        public double contanti;
        public double carta;
        public double buoni;
        public ArrayList<Long> servizi = new ArrayList<>();

        public X(String c, String nome) {
            this.c = c;
            this.nome = nome;
        }
    }

    public piz() {
        caricaTutto();
    }

    public void caricaTutto() {
        int[] a = {2, 4, 4, 6, 8, 2, 4, 6, 8, 4, 2, 6, 8, 4, 6};
        int[] b = {2, 4, 6, 4, 2, 6, 4, 6, 2, 4};
        for (int i = 0; i < 15; i++) {
            t.add(new T(i + 1, a[i], true));
        }
        for (int i = 0; i < 10; i++) {
            t.add(new T(i + 16, b[i], false));
        }

        c.add(new C("C1", "Mario", "Rossi"));
        c.add(new C("C2", "Anna", "Bianchi"));
        c.add(new C("C3", "Luca", "Verdi"));
        c.add(new C("C4", "Sara", "Neri"));
        c.add(new C("C5", "Paolo", "Gallo"));
        c.add(new C("C6", "Elena", "Blu"));
        for (int i = 1; i <= 5; i++) {
            c.get(0).z.add(i);
        }
        for (int i = 6; i <= 10; i++) {
            c.get(1).z.add(i);
        }
        for (int i = 11; i <= 15; i++) {
            c.get(2).z.add(i);
        }
        for (int i = 16; i <= 18; i++) {
            c.get(3).z.add(i);
        }
        for (int i = 19; i <= 21; i++) {
            c.get(4).z.add(i);
        }
        for (int i = 22; i <= 25; i++) {
            c.get(5).z.add(i);
        }

        String tutti = "MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY SATURDAY SUNDAY";
        m.add(new P("P001", "Margherita", "Pomodoro e mozzarella", 8.50, "PIZZE", tutti));
        m.add(new P("P002", "Friulana", "Montasio e frico", 11.00, "PIZZE", "FRIDAY SATURDAY SUNDAY"));
        m.add(new P("A001", "Antipasto della casa", "Salumi e formaggi", 7.00, "ANTIPASTI", tutti));
        m.add(new P("PR01", "Gnocchi", "Gnocchi al pomodoro", 12.00, "PRIMI", tutti));
        m.add(new P("SC01", "Tagliata", "Tagliata di manzo", 15.00, "SECONDI_CARNE", tutti));
        m.add(new P("SP01", "Orata", "Orata al forno", 16.00, "SECONDI_PESCE", tutti));
        m.add(new P("CO01", "Verdure", "Verdure grigliate", 5.00, "CONTORNI", tutti));
        m.add(new P("D001", "Tiramisu", "Dolce della casa", 6.00, "DOLCI", tutti));
        m.add(new P("B001", "Acqua", "Bottiglia da 75 cl", 3.00, "BEVANDE", tutti));
        m.add(new P("B002", "Birra", "Birra artigianale", 4.50, "BEVANDE", tutti));
    }

    public T tav(int n) {
        for (T x : t) {
            if (x.n == n) {
                return x;
            }
        }
        return null;
    }

    public C cam(String x) {
        for (C y : c) {
            if (y.c.equals(x)) {
                return y;
            }
        }
        return null;
    }

    public C camPer(int n) {
        for (C x : c) {
            if (x.z.contains(n)) {
                return x;
            }
        }
        return null;
    }

    public P piatto(String x) {
        for (P y : m) {
            if (y.c.equals(x)) {
                return y;
            }
        }
        return null;
    }

    public S servizio(long x) {
        for (S y : s) {
            if (y.n == x) {
                return y;
            }
        }
        return null;
    }

    public O ordine(long x) {
        for (O y : o) {
            if (y.n == x) {
                return y;
            }
        }
        return null;
    }

    public long apri(List<Integer> tt, int cop) {
        for (Integer x : tt) {
            if (tav(x) == null) {
                throw new IllegalArgumentException("tavolo " + x);
            }
        }
        C cc = camPer(tt.get(0));
        for (Integer x : tt) {
            if (!cc.z.contains(x)) {
                throw new IllegalArgumentException("zone diverse");
            }
        }
        S z = new S(ns, tt, cc.c, cop);
        s.add(z);
        ns++;
        return z.n;
    }

    public O ordina(long id, LocalDateTime quando, String codice, int quantita) {
        S z = servizio(id);
        P p = piatto(codice);
        if (!p.ok(quando)) {
            throw new IllegalStateException(p.n + " non disponibile");
        }
        O x = new O(no, z.n, quando, z.t, z.c, z.p);
        no++;
        x.r.add(new R(p, quantita));
        o.add(x);
        return x;
    }

    public void aggiungi(long id, String codice, int quantita) {
        O x = ordine(id);
        P p = piatto(codice);
        if (!p.ok(x.q)) {
            throw new IllegalStateException(p.n + " non disponibile");
        }
        x.r.add(new R(p, quantita));
    }

    public void stato(long id, String x) {
        ordine(id).stato = x;
    }

    public void paga(long id, String modo, LocalDateTime quando) {
        for (O x : o) {
            if (x.s == id && x.pagato == null) {
                x.stato = "PAGATO";
                x.pagamento = modo;
                x.pagato = quando;
            }
        }
    }

    public ArrayList<O> attivi() {
        ArrayList<O> x = new ArrayList<>();
        for (O y : o) {
            if (!y.stato.equals("PAGATO")) {
                x.add(y);
            }
        }
        return x;
    }

    public double conto(long id, LocalDateTime quando) {
        double x = 0;
        for (O y : o) {
            if (y.s == id && !y.q.isAfter(quando) && (y.pagato == null || y.pagato.isAfter(quando))) {
                x = x + y.tot();
            }
        }
        return x;
    }

    public double conto(int tavolo, LocalDateTime quando) {
        double x = 0;
        for (O y : o) {
            if (y.t.contains(tavolo) && !y.q.isAfter(quando) && (y.pagato == null || y.pagato.isAfter(quando))) {
                x = x + y.tot();
            }
        }
        return x;
    }

    public List<PizzeriaDto.OrdineAttivo> visualizzaOrdiniAttivi() {
        ArrayList<PizzeriaDto.OrdineAttivo> risultato = new ArrayList<>();
        for (O x : o) {
            if (!x.stato.equals("PAGATO")) {
                C cc = cam(x.c);
                ArrayList<PizzeriaDto.Riga> pietanze = new ArrayList<>();
                for (R rr : x.r) {
                    pietanze.add(rigaDto(rr));
                }
                risultato.add(new PizzeriaDto.OrdineAttivo(
                        x.n,
                        x.t,
                        cc.c,
                        cc.n + " " + cc.x,
                        pietanze,
                        x.stato,
                        x.q,
                        x.tot()));
            }
        }
        return List.copyOf(risultato);
    }

    public PizzeriaDto.Conto contoDettagliato(int tavolo, LocalDateTime quando) {
        ArrayList<PizzeriaDto.OrdineConto> ordini = new ArrayList<>();
        double totale = 0;
        for (O x : o) {
            if (x.t.contains(tavolo) && !x.q.isAfter(quando) && (x.pagato == null || x.pagato.isAfter(quando))) {
                ArrayList<PizzeriaDto.Riga> pietanze = new ArrayList<>();
                double totaleOrdine = 0;
                for (R rr : x.r) {
                    double riga = rr.p.p * rr.q;
                    pietanze.add(rigaDto(rr));
                    totaleOrdine = totaleOrdine + riga;
                    totale = totale + riga;
                }
                ordini.add(new PizzeriaDto.OrdineConto(x.n, x.s, pietanze, totaleOrdine));
            }
        }
        return new PizzeriaDto.Conto(tavolo, quando, ordini, totale);
    }

    private PizzeriaDto.Riga rigaDto(R rr) {
        return new PizzeriaDto.Riga(rr.p.c, rr.p.n, rr.q, rr.p.p, rr.q * rr.p.p);
    }

    public ArrayList<X> resoconto(LocalDate giorno) {
        ArrayList<X> risultato = new ArrayList<>();
        for (C cameriere : c) {
            X x = new X(cameriere.c, cameriere.n + " " + cameriere.x);
            risultato.add(x);
        }
        for (O ordine : o) {
            if (ordine.pagato != null && ordine.pagato.toLocalDate().equals(giorno)) {
                int numeroCameriere = 0;
                while (!c.get(numeroCameriere).c.equals(ordine.c)) {
                    numeroCameriere++;
                }
                X x = risultato.get(numeroCameriere);
                double tot = 0;
                for (R riga : ordine.r) {
                    double valore = riga.q * riga.p.p;
                    tot = tot + valore;
                    if (riga.p.k.equals("PIZZE")) {
                        x.pizze = x.pizze + valore;
                    }
                    if (riga.p.k.equals("ANTIPASTI")) {
                        x.antipasti = x.antipasti + valore;
                    }
                    if (riga.p.k.equals("PRIMI")) {
                        x.primi = x.primi + valore;
                    }
                    if (riga.p.k.equals("SECONDI_CARNE")) {
                        x.carne = x.carne + valore;
                    }
                    if (riga.p.k.equals("SECONDI_PESCE")) {
                        x.pesce = x.pesce + valore;
                    }
                    if (riga.p.k.equals("CONTORNI")) {
                        x.contorni = x.contorni + valore;
                    }
                    if (riga.p.k.equals("DOLCI")) {
                        x.dolci = x.dolci + valore;
                    }
                    if (riga.p.k.equals("BEVANDE")) {
                        x.bevande = x.bevande + valore;
                    }
                }
                x.lordo = x.lordo + tot;
                if (ordine.pagamento.equals("CONTANTI")) {
                    x.contanti = x.contanti + tot;
                }
                if (ordine.pagamento.equals("CARTA")) {
                    x.carta = x.carta + tot;
                }
                if (ordine.pagamento.equals("BUONI_PASTO")) {
                    x.buoni = x.buoni + tot;
                }
                if (!x.servizi.contains(ordine.s)) {
                    x.servizi.add(ordine.s);
                    x.tavoli = x.tavoli + ordine.t.size();
                    x.coperti = x.coperti + ordine.cop;
                }
            }
        }
        return risultato;
    }

    public PizzeriaDto.ResocontoSerale stampaResoconto(LocalDate giorno) {
        ArrayList<X> x = resoconto(giorno);
        ArrayList<PizzeriaDto.ResocontoCameriere> camerieri = new ArrayList<>();
        double incassoTotale = 0;
        for (X r : x) {
            Map<String, Double> categorie = new LinkedHashMap<>();
            categorie.put("PIZZE", r.pizze);
            categorie.put("ANTIPASTI", r.antipasti);
            categorie.put("PRIMI", r.primi);
            categorie.put("SECONDI_CARNE", r.carne);
            categorie.put("SECONDI_PESCE", r.pesce);
            categorie.put("CONTORNI", r.contorni);
            categorie.put("DOLCI", r.dolci);
            categorie.put("BEVANDE", r.bevande);

            Map<String, Double> pagamenti = new LinkedHashMap<>();
            pagamenti.put("CONTANTI", r.contanti);
            pagamenti.put("CARTA", r.carta);
            pagamenti.put("BUONI_PASTO", r.buoni);

            camerieri.add(new PizzeriaDto.ResocontoCameriere(
                    r.c,
                    r.nome,
                    r.tavoli,
                    r.coperti,
                    r.lordo,
                    categorie,
                    pagamenti));
            incassoTotale = incassoTotale + r.lordo;
        }
        return new PizzeriaDto.ResocontoSerale(giorno, camerieri, incassoTotale);
    }
}
