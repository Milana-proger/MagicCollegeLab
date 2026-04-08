package ui;

import model.Mission;
import model.Sorcerer;
import model.Technique;

public class DetailedMissionReportStrategy implements MissionReportStrategy {

    public String getFormatName() {
        return "Detailed report";
    }

    public String generateMission(Mission mission) {
        StringBuilder sb = new StringBuilder();

        sb.append("╔════════════════════════════════════════════════════════════╗\n");
        sb.append("║              СВИТОК МИССИИ МАГИЧЕСКОГО КОЛЛЕДЖА            ║\n");
        sb.append("╠════════════════════════════════════════════════════════════╣\n");

        sb.append(String.format(" ID миссии: %-41s \n", truncate(mission.getMissionId(), 41)));
        sb.append(String.format(" Дата: %-45s \n", truncate(mission.getDate(), 45)));
        sb.append(String.format(" Локация: %-42s \n", truncate(mission.getLocation(), 42)));

        sb.append(String.format(" Исход: %-44s \n", truncate(mission.getOutcome(), 44)));

        sb.append(String.format(" Ущерб: %,9d\n", mission.getDamageCost()));

        sb.append("╠════════════════════════════════════════════════════════════╣\n");

        if (mission.getCurse() != null) {
            sb.append(" ПРОКЛЯТИЕ:\n");
            sb.append(String.format("   Имя: %-46s \n", truncate(mission.getCurse().getName(), 46)));
            sb.append(String.format("   Уровень угрозы: %-36s \n", truncate(mission.getCurse().getThreatLevel(), 36)));
        } else {
            sb.append(" ПРОКЛЯТИЕ: не указано\n");
        }

        sb.append("╠════════════════════════════════════════════════════════════╣\n");

        sb.append(" МАГИ:\n");
        if (mission.getSorcerers().isEmpty()) {
            sb.append("   Нет участников\n");
        } else {
            for (Sorcerer s : mission.getSorcerers()) {
                sb.append(String.format("   • %s (%s) %-33s \n",
                        truncate(s.getName(), 20),
                        truncate(s.getRank(), 20), ""));
            }
        }

        sb.append("╠════════════════════════════════════════════════════════════╣\n");

        sb.append(" ПРИМЕНЁННЫЕ ТЕХНИКИ:                                      \n");
        if (mission.getTechniques().isEmpty()) {
            sb.append("   Техники не применялись                                 \n");
        } else {
            for (Technique t : mission.getTechniques()) {
                sb.append(String.format("   • %s [%s]%n",
                        truncate(t.getName(), 20),
                        truncate(t.getType(), 15)));
                sb.append(String.format("     Маг: %s | Урон: %,9d%n",
                        truncate(t.getOwner(), 15), t.getDamage()));
            }
        }

        sb.append("╠════════════════════════════════════════════════════════════╣\n");

        sb.append(" ПРИМЕЧАНИЯ:                                               \n");
        if (mission.getNote() == null || mission.getNote().isEmpty()) {
            sb.append("   Нет примечаний                                         \n");
        } else {
            sb.append("   " + mission.getNote() + "\n");
        }

        sb.append("╚════════════════════════════════════════════════════════════╝");

        return sb.toString();
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}