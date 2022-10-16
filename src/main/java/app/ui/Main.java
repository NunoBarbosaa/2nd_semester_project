package app.ui;

import app.domain.model.Company;
import app.ui.console.MainMenuUI;
import app.ui.gui.App;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

//Teste
public class Main {

    public static void main(String[] args)
    {
        try
        {
            MainMenuUI menu = new MainMenuUI();

            menu.run();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}