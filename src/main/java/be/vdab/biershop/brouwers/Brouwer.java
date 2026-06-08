package be.vdab.biershop.brouwers;

record Brouwer(
        int id,
        String naam,
        String straat,
        String huisNr,
        Integer postcode,
        String gemeente,
        Integer omzet
) {
}
