package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class SalaryInfo {
    public static String getSalaryInfo(String[] names, String[] data,
                                       String dateFrom, String dateTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate from = LocalDate.parse(dateFrom.trim(), formatter);
        LocalDate to = LocalDate.parse(dateTo.trim(), formatter);

        Map<String, Integer> salaryMap = new HashMap<>();
        for (String n : names) {
            salaryMap.put(n, 0);
        }

        for (String entry : data) {
            try {
                String[] parts = entry.split("\\s+");
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Неверный формат строки: " + entry);
                }

                LocalDate workDate = LocalDate.parse(parts[0], formatter);

                if (workDate.isBefore(from) || workDate.isAfter(to)) {
                    continue;
                }

                String name = parts[1];
                int hours = Integer.parseInt(parts[2]);
                int rate = Integer.parseInt(parts[3]);

                int earned = hours * rate;

                if (salaryMap.containsKey(name)) {
                    salaryMap.put(name, salaryMap.get(name) + earned);
                }

            } catch (DateTimeParseException e) {
                System.out.println("Ошибка формата даты в записи: " + entry);

            } catch (NumberFormatException e) {
                System.out.println("Ошибка преобразования числа в записи: " + entry);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Report for period ").append(dateFrom).append(" - ").append(dateTo).append("\n");

        for (String name : names) {
            sb.append(name).append(" - ").append(salaryMap.get(name)).append("\n");
        }

        return sb.toString().trim();
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


