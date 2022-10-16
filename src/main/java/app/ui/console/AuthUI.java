package app.ui.console;

import app.controller.AuthController;
import app.domain.model.Company;
import app.domain.shared.Constants;
import app.ui.console.SnsUserUI.AppointmentUI;
import app.ui.console.centercoordinatorUI.CenterCoordinatorUI;
import app.ui.console.receptionistUI.ReceptionistUI;
import app.ui.console.utils.Utils;
import app.ui.console.vaccineUI.VaccineUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

public class AuthUI implements Runnable{
    private AuthController ctrl;
    private Company company;
    public AuthUI(Company company)
    {
        ctrl = new AuthController();
        this.company=company;
    }

    public void run()
    {
        boolean success = doLogin();

        if (success)
        {
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            if ( (roles == null) || (roles.isEmpty()) )
            {
                System.out.println("User has not any role assigned.");
            }
            else
            {
                UserRoleDTO role = selectsRole(roles);
                if (!Objects.isNull(role))
                {
                    List<MenuItem> rolesUI = getMenuItemForRoles();
                    this.redirectToRoleUI(rolesUI,role);
                }
                else
                {
                    System.out.println("User did not select a role.");
                }
            }
        }
        this.logout();
    }

    private List<MenuItem> getMenuItemForRoles()
    {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(Constants.ROLE_ADMIN, new AdminUI(company)));
        rolesUI.add(new MenuItem(Constants.ROLE_ADMIN,new VaccineUI(company)));
        rolesUI.add(new MenuItem(Constants.ROLE_CCOORDINATOR,new CenterCoordinatorUI(company)));
        rolesUI.add(new MenuItem(Constants.ROLE_ADMIN, new AdminUI(company)));
        rolesUI.add(new MenuItem(Constants.ROLE_RECEPTIONIST,new ReceptionistUI(company)));
        rolesUI.add(new MenuItem(Constants.ROLE_NURSE, new NurseUI(company)));
        rolesUI.add(new MenuItem(Constants.USER_SNS, new AppointmentUI(company)));

        //
        return rolesUI;
    }

    private boolean doLogin()
    {
        System.out.println("\nLogin UI:");

        int maxAttempts = 3;
        boolean success = false;
        do
        {
            maxAttempts--;
            String id = Utils.readLineFromConsole("Enter UserId/Email: ");
            String pwd = Utils.readLineFromConsole("Enter Password: ");

            success = ctrl.doLogin(id, pwd);
            if (!success)
            {
                System.out.println("Invalid UserId and/or Password. \n You have  " + maxAttempts + " more attempt(s).");
            }

        } while (!success && maxAttempts > 0);
        return success;
    }

    private void logout()
    {
        ctrl.doLogout();
    }

    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role)
    {
        boolean found = false;
        Iterator<MenuItem> it = rolesUI.iterator();
        while (it.hasNext() && !found)
        {
            MenuItem item = it.next();
            found = item.hasDescription(role.getDescription());
            if (found)
                item.run();
        }
        if (!found)
            System.out.println("There is no UI for users with role '" + role.getDescription() + "'");
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles)
    {
        if (roles.size() == 1)
            return roles.get(0);
        else
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
    }


}
