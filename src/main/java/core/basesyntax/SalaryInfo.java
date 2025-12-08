package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int EXPECTED_PARTS = 4;
    private static final int DATE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int HOURS_INDEX = 2;
    private static final int RATE_INDEX = 3;

    public static String getSalaryInfo(String[] names, String[] data,
                                       String dateFrom, String dateTo) {

        LocalDate from = LocalDate.parse(dateFrom.trim(), FORMATTER);
        LocalDate to = LocalDate.parse(dateTo.trim(), FORMATTER);

        int [] salaries = new int[names.length];

        for (String entry : data) {

            String[] parts = entry.split("\\s+");
            if (parts.length != EXPECTED_PARTS) {
                continue;
            }
            LocalDate workDate;
            try {
                workDate = LocalDate.parse(parts[DATE_INDEX], FORMATTER);
            } catch (Exception e) {
                continue;
            }
            if (workDate.isBefore(from) || workDate.isAfter(to)) {
                continue;
            }
            String name = parts[NAME_INDEX];
            int index = findIndex(names, name);
            if (index == -1) {
                continue;
            }
            int hours;
            int rate;
            try {
                hours = Integer.parseInt(parts[HOURS_INDEX]);
                rate = Integer.parseInt(parts[RATE_INDEX]);
            } catch (Exception e) {
                continue;
            }
            salaries[index] += hours * rate;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Report for period ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo)
                .append(System.lineSeparator());

        for (int i = 0; i < names.length; i++) {
            sb.append(names[i])
                    .append(" - ")
                    .append(salaries[i])
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    private static int findIndex(String[] names, String target) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String [] args) {
        String[] names = {"John", "Andrew", "Kate"};
        String[] data = { "26.04.2019 John 4 50",
                "05.04.2019 Andrew 3 200",
                "10.04.2019 John 7 100",
                "22.04.2019 Kate 9 100",
                "25.06.2019 John 11 50",
                "26.04.2019 Andrew 3 150",
                "13.02.2019 John 7 100",
                "26.04.2019 Kate 9 100" };
        String dateFrom = "01.04.2019";
        String dateTo = "30.04.2019";
        String report = getSalaryInfo(names, data, dateFrom, dateTo);
        System.out.println(report);
    }
}


