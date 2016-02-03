package ufpr.dce.appdce;

import java.util.Random;

public class RUMenuGenerator {
    private final static String[] VAR_SALAD = new String[]{"Alface", "Agrião", "Algo que parece Alface mas não é", "Tomate", "Beterraba", "Rabanete", "Pepino"};
    private final static String[] VAR_RICE = new String[]{"Arroz","Arroz Integral"};
    private final static String[] VAR_BEAN = new String[]{"Feijão Branco", "Feijão"};
    private final static String[] VAR_SOUP = new String[]{"de Ervilha", "de Legumes", "de Falta de Criatividade minha para nomes"};
    private final static String[] VAR_MEAT = new String[]{"Carne de Carne", "Carne", "Outra Carne", "Carne", "Carne"};
    private final static String[] VAR_VEGAN_BEG = new String[]{"Carne ", "Torta ", "Lasanha ", "Hamburger "};
    private final static String[] VAR_VEGAN_END = new String[]{"de Berinjela", "de Berinjela", "de Berinjela <3", "de Berinjela s2", "de Batata", "de Feijão Branco"};
    private final static String[] VAR_VEGAN = new String[]{"Berinjela <3", "Berinjela s2", "Torre de Berinjela", "Grão de Bico"};

    public static String[][][] GenerateMenu(){
        String menu[][][] = new String[7][3][7];

        for (String[][] dayMenu:menu)
        {
            // Breakfast
            dayMenu[0][0] = "Café com leite\nPão com margarina";

            Random rand = new Random();

            // Lunch
            int randomNum = rand.nextInt(VAR_SALAD.length + VAR_SALAD.length + 1);
            if (!(randomNum > VAR_SALAD.length * 0.85))
            {
                dayMenu[1][0] = VAR_SALAD[(randomNum % VAR_SALAD.length)];
            }


            randomNum = rand.nextInt(VAR_SALAD.length);
            dayMenu[1][1] = VAR_SALAD[randomNum];


            randomNum = rand.nextInt(VAR_RICE.length);
            dayMenu[1][2] = VAR_RICE[randomNum];


            randomNum = rand.nextInt(VAR_BEAN.length);
            dayMenu[1][3] = VAR_BEAN[randomNum];


            randomNum = rand.nextInt(VAR_SOUP.length + VAR_SOUP.length + 1);
            if (!(randomNum > VAR_SOUP.length * 0.40))
            {
                dayMenu[1][4] = "Sopa " + VAR_SOUP[(randomNum % VAR_SOUP.length)];
            }


            randomNum = rand.nextInt(VAR_MEAT.length);
            dayMenu[1][5] = VAR_MEAT[randomNum];


            randomNum = rand.nextInt(101);
            if (randomNum > 50)
            {
                randomNum = rand.nextInt(VAR_VEGAN_BEG.length);
                dayMenu[1][6] = VAR_VEGAN_BEG[randomNum];

                randomNum = rand.nextInt(VAR_VEGAN_END.length);
                dayMenu[1][6] += VAR_VEGAN_END[randomNum];
            }
            else
            {
                randomNum = rand.nextInt(VAR_VEGAN.length);
                dayMenu[1][6] = VAR_VEGAN[randomNum];
            }



            // Dinner
            randomNum = rand.nextInt(VAR_SALAD.length + VAR_SALAD.length + 1);
            if (!(randomNum > VAR_SALAD.length * 0.85))
            {
                dayMenu[2][0] = VAR_SALAD[(randomNum % VAR_SALAD.length)];
            }


            randomNum = rand.nextInt(VAR_SALAD.length);
            dayMenu[2][1] = VAR_SALAD[randomNum];


            randomNum = rand.nextInt(VAR_RICE.length);
            dayMenu[2][2] = VAR_RICE[randomNum];


            randomNum = rand.nextInt(VAR_BEAN.length);
            dayMenu[2][3] = VAR_BEAN[randomNum];


            randomNum = rand.nextInt(VAR_SOUP.length + VAR_SOUP.length + 1);
            if (!(randomNum > VAR_SOUP.length * 0.40))
            {
                dayMenu[2][4] = "Sopa " + VAR_SOUP[(randomNum % VAR_SOUP.length)];
            }


            randomNum = rand.nextInt(VAR_MEAT.length);
            dayMenu[2][5] = VAR_MEAT[randomNum];

            randomNum = rand.nextInt(101);

            if (randomNum > 50)
            {
                randomNum = rand.nextInt(VAR_VEGAN_BEG.length);
                dayMenu[2][6] = VAR_VEGAN_BEG[randomNum];

                randomNum = rand.nextInt(VAR_VEGAN_END.length);
                dayMenu[2][6] += VAR_VEGAN_END[randomNum];
            }
            else
            {
                randomNum = rand.nextInt(VAR_VEGAN.length);
                dayMenu[2][6] = VAR_VEGAN[randomNum];
            }
        }

        return menu;
    }
}
