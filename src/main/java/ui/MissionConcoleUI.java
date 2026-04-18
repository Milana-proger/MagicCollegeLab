package ui;

import analysis.MissionAnalyzer;
import exception.MissionException;
import model.Mission;
import parser.MissionParser;
import parser.MissionParserFactory;
import source.FileMissionDataSourse;
import source.MissionDataSourse;

import java.io.File;
import java.util.Scanner;

public class MissionConcoleUI {
    private MissionParserFactory missionParser;
    private MissionDataSourse missionDataSourse;
    private MissionAnalyzer missionAnalyzer;
    private Scanner scanner;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";

    public MissionConcoleUI(MissionParserFactory missionParserFactory, MissionAnalyzer missionAnalyzer) {
        this.missionParser = missionParserFactory;
        this.scanner = new Scanner(System.in);
        this.missionAnalyzer = missionAnalyzer;
        this.missionDataSourse = new FileMissionDataSourse();
    }

    public MissionConcoleUI(MissionParserFactory missionParser, MissionDataSourse missionDataSourse, MissionAnalyzer missionAnalyzer) {
        this.missionParser = missionParser;
        this.missionDataSourse = missionDataSourse;
        this.missionAnalyzer = missionAnalyzer;
        this.scanner = new Scanner(System.in);
    }

    private void printTitle() {
        System.out.println(BOLD + PURPLE);
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║        ТОКИЙСКИЙ МАГИЧЕСКИЙ КОЛЛЕДЖ                ║");
        System.out.println("║        ЛОКАЛЬНЫЙ АНАЛИЗАТОР МИССИЙ МАГОВ           ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    private void printMenu() {
        System.out.println(BOLD + CYAN);
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.println("│                  ГЛАВНОЕ МЕНЮ                      │");
        System.out.println("├────────────────────────────────────────────────────┤");
        System.out.println("│  " + GREEN + "1" + CYAN + ".  Анализировать файл миссии                     │");
        System.out.println("│  " + GREEN + "2" + CYAN + ".  Анализировать все миссии в папке              │");
        System.out.println("│  " + GREEN + "3" + CYAN + ".  О программе                                   │");
        System.out.println("│  " + RED + "q" + CYAN + ".  Выход                                         │");
        System.out.println("└────────────────────────────────────────────────────┘");
        System.out.print(RESET + BOLD + "\n▶ Выберите действие: " + RESET);
    }

    private void printAbout() {
        System.out.println(BOLD + PURPLE);
        System.out.println("┌────────────────────────────────────────────────────┐");
        System.out.println("│                  О ПРОГРАММЕ                       │");
        System.out.println("├────────────────────────────────────────────────────┤");
        System.out.println("│                                                    │");
        System.out.println("│  " + BOLD + "ЛОКАЛЬНЫЙ АНАЛИЗАТОР МИССИЙ МАГОВ" + RESET + PURPLE + "                 │");
        System.out.println("│  Версия: 1.0.0                                     │");
        System.out.println("│                                                    │");
        System.out.println("│  " + CYAN + "Токийский магический колледж" + RESET + PURPLE + "                      │");
        System.out.println("│  Факультет анализа проклятых данных                │");
        System.out.println("│                                                    │");
        System.out.println("│  " + GREEN + "Разработано для магов-аналитиков" + RESET + PURPLE + "                  │");
        System.out.println("│                                                    │");
        System.out.println("│  " + YELLOW + "Поддерживаемые форматы:" + RESET + PURPLE + "                           │");
        System.out.println("│  • JSON                                            │");
        System.out.println("│  • XML                                             │");
        System.out.println("│  • Text                                            │");
        System.out.println("│                                                    │");
        System.out.println("│  " + WHITE + "© 2026 Токийский магический колледж" + RESET + PURPLE + "               │");
        System.out.println("│  Все права защищены проклятой энергией             │");
        System.out.println("│                                                    │");
        System.out.println("└────────────────────────────────────────────────────┘");
        System.out.println(RESET);
    }

    private void analyzeFile() {
        System.out.print(BLUE + "▶ Введите путь к файлу миссии: " + RESET);
        String filePath = scanner.nextLine().trim();
        try {
            String data = missionDataSourse.load(filePath);
            MissionParser parser = missionParser.getMissionParserFromFileName(filePath);
            if (parser.canBeParsedFromData(data)) {
                Mission mission = parser.parse(data);
                missionAnalyzer.addMissionToAnalyze(mission);
                missionAnalyzer.analyze();
                missionAnalyzer.resetAnalyzer();
            } else {
                parser = missionParser.getMissionParserFromData(data);
                if (parser.canBeParsedFromData(data)) {
                    Mission mission = parser.parse(data);
                    missionAnalyzer.addMissionToAnalyze(mission);
                    missionAnalyzer.analyze();
                    missionAnalyzer.resetAnalyzer();
                } else {
                    System.out.println("Формат данных не поддерживается!");
                }
            }
        } catch (MissionException e) {
            System.out.println("Ошибка обработки файла: " + e.getMessage());
        }
    }

    private void analyzeDirectory() {
        System.out.print(BLUE + "▶ Введите путь к папке с миссиями: " + RESET);
        String dirPath = scanner.nextLine().trim();

        File directory = new File(dirPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println(RED + " Папка не найдена: " + dirPath + RESET);
            return;
        }

        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println(YELLOW + "⚠ В папке не найдено файлов миссий (поддерживаются .json, .xml, .txt)" + RESET);
            return;
        }

        System.out.println(GREEN + "\n Найдено файлов: " + files.length + RESET);
        int succeededFiles = 0;
        int failedFiles = 0;
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            try {
                String data = missionDataSourse.load(file.getAbsolutePath());
                MissionParser parser = missionParser.getMissionParserFromFileName(file.getName());
                if (parser.canBeParsedFromData(data)) {
                    Mission mission = parser.parse(data);
                    missionAnalyzer.addMissionToAnalyze(mission);
                    succeededFiles++;
                } else {
                    parser = missionParser.getMissionParserFromData(data);
                    if (parser.canBeParsedFromData(data)) {
                        Mission mission = parser.parse(data);
                        missionAnalyzer.addMissionToAnalyze(mission);
                        succeededFiles++;
                    } else {
                        failedFiles++;
                    }
                }
            } catch (MissionException e) {
                failedFiles++;
            }
        }
        missionAnalyzer.analyze();
        missionAnalyzer.resetAnalyzer();
    }

    public void start() {
        printTitle();
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    analyzeFile();
                    break;
                case "2":
                    analyzeDirectory();
                    break;
                case "3":
                    printAbout();
                    break;
                case "q":
                case "Q":
                    System.out.println("Миссия окончена");
                    return;
                default:
                    System.out.println("Данные введены неверно");
            }
        }
    }
}