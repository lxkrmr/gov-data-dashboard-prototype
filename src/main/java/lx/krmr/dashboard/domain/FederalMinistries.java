package lx.krmr.dashboard.domain;

import lx.krmr.dashboard.domain.model.types.Department;
import lx.krmr.dashboard.domain.model.types.FederalMinistryStatistic;

import java.util.List;

public record FederalMinistries(List<Department> departments) {
    public static FederalMinistries create(List<FederalMinistryStatistic> statistics) {
        List<Department> departmentsWithStatistics = initializeDepartments().stream()
                                                                            .map(department -> department.addIfPartOfDepartment(statistics))
                                                                            .toList();
        return new FederalMinistries(departmentsWithStatistics);
    }

    private static List<Department> initializeDepartments() {
        Department auswaertigesAmt = Department.create("auswaertiges-amt");

        Department justiz = Department.create("bmj",
                                              List.of("dpma",
                                                      "bundesamt-fur-justiz"));

        Department finanzen = Department.create("bmf",
                                                List.of("bzst",
                                                        "zoll",
                                                        "itzbund"));

        Department verteidigung = Department.create("bmvg");

        Department innern = Department.create("bmi",
                                              List.of("bbsr",
                                                      "badv",
                                                      "bva",
                                                      "statistisches-bundesamt"));

        Department arbeitUndSoziales = Department.create("bmas",
                                                         List.of("baua"));

        Department bildungUndForschung = Department.create("bundesministerium-fur-bildung-und-forschung");

        Department familieSeniorenFrauenUndJugend = Department.create("bmfsfj");

        Department wirtschaftlicheZusammenarbeitUndEntwicklung = Department.create("bmz");

        Department wirtschaftUndEnergie = Department.create("bmwi",
                                                            List.of("bafa",
                                                                    "bam"));

        Department ernaehrungUndLandwirtschaft = Department.create("bmel",
                                                                   List.of("bvl",
                                                                           "bsa",
                                                                           "mri"));

        Department gesundheit = Department.create("bmg",
                                                  List.of("bas"));

        Department verkehrUndDigitaleInfrastruktur = Department.create("bmdv",
                                                                       List.of("mcloud"));

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
}
