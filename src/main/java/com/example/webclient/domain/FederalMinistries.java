package com.example.webclient.domain;

import java.util.List;
import java.util.Objects;

public class FederalMinistries {

    private final List<Department> departmentList;

    private FederalMinistries(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public static FederalMinistries create(List<FederalMinistryStatistic> statistics) {
        List<Department> departmentsWithStatistics = initializeDepartments().stream()
                                                                            .map(department -> department.addIfPartOfDepartment(statistics))
                                                                            .toList();
        return new FederalMinistries(departmentsWithStatistics);
    }

    private static List<Department> initializeDepartments() {
        Department auswaertigesAmt = Department.create("Auswärtiges Amt");
        Department justiz = Department.create("Bundesministerium der Justiz",
                                              List.of("Deutsches Patent- und Markenamt",
                                                      "Bundesamt für Justiz"));
        Department finanzen = Department.create("Bundesministerium der Finanzen",
                                                List.of("Bundeszentralamt für Steuern",
                                                        "Generalzolldirektion",
                                                        "ITZ-Bund"));
        Department verteidigung = Department.create("Bundesministerium der Verteidigung");
        Department innern = Department.create("Bundesministerium des Innern",
                                              List.of("Bundesinstitut für Bau-, Stadt- und Raumforschung (BBSR) im Bundesamt für Bauwesen und Raumordnung (BBR)",
                                                      "Bundesausgleichsamt",
                                                      "Bundesverwaltungsamt",
                                                      "Statistisches Bundesamt"));
        Department arbeitUndSoziales = Department.create("Bundesministerium für Arbeit und Soziales",
                                                         List.of("Bundesanstalt für Arbeitsschutz und Arbeitsmedizin"));
        Department bildungUndForschung = Department.create("Bundesministerium für Bildung und Forschung");

        Department familieSeniorenFrauenUndJugend = Department.create("Bundesministerium für Familie, Senioren, Frauen und Jugend");
        Department wirtschaftlicheZusammenarbeitUndEntwicklung = Department.create("Bundesministerium für wirtschaftliche Zusammenarbeit und Entwicklung");
        Department wirtschaftUndEnergie = Department.create("Bundesministerium für Wirtschaft und Energie",
                                                            List.of("Bundesamt für Wirtschaft und Ausfuhrkontrolle",
                                                                    "Bundesanstalt für Materialforschung und -prüfung"));
        Department ernaehrungUndLandwirtschaft = Department.create("Bundesministerium für Ernährung und Landwirtschaft",
                                                                   List.of("Bundesamt für Verbraucherschutz und Lebensmittelsicherheit",
                                                                           "Bundessortenamt",
                                                                           "Max Rubner-Institut"));
        Department gesundheit = Department.create("Bundesministerium für Gesundheit",
                                                  List.of("Bundesamt für Soziale Sicherung"));
        Department verkehrUndDigitaleInfrastruktur = Department.create("Bundesministerium für Verkehr und digitale Infrastruktur",
                                                                       List.of("mCLOUD"));

        return List.of(auswaertigesAmt,
                       justiz,
                       finanzen,
                       verteidigung,
                       innern,
                       arbeitUndSoziales,
                       bildungUndForschung,
                       familieSeniorenFrauenUndJugend,
                       wirtschaftlicheZusammenarbeitUndEntwicklung,
                       wirtschaftUndEnergie,
                       ernaehrungUndLandwirtschaft,
                       gesundheit,
                       verkehrUndDigitaleInfrastruktur);
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FederalMinistries that = (FederalMinistries) o;
        return departmentList.equals(that.departmentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentList);
    }
}
