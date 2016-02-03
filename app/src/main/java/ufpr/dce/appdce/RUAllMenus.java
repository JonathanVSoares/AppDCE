package ufpr.dce.appdce;

public class RUAllMenus {
    private RUWeekMenu[] menus = new RUWeekMenu[3];

    public RUAllMenus() {
        for (int counter = 0; counter < 3; counter++)
            menus[counter] = new RUWeekMenu();
    }

    public RUWeekMenu getMenu(int weekOfTheYear){
        if (weekOfTheYear < menus.length)
            return menus[weekOfTheYear];
        else
            return menus[0];
    }
}
