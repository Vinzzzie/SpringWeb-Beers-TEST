package be.vdab.biershop.brouwers;

record BrouwerDto(
        int id,
        String naam,
        String gemeente
) {
    public static BrouwerDto fromBrouwer(Brouwer brouwer) {
        return new BrouwerDto(brouwer.id(), brouwer.naam(), brouwer.gemeente());
    }
}
