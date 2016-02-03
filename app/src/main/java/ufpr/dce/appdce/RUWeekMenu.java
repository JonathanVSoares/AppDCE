package ufpr.dce.appdce;

public class RUWeekMenu {
    private String[][][] menu = new String[7][3][7];


    public RUWeekMenu() {
        menu = RUMenuGenerator.GenerateMenu();
    }

    public String[][][] getMenu() {
        return menu;
    }

}
