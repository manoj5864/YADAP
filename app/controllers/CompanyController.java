package controllers;

import models.Company;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.company.*;

/**
 * Created by manoj on 2/4/15.
 */
public class CompanyController extends Controller {
    static Form<Company> companyForm = Form.form(Company.class);

    public static Result index() {
        return ok(index.render());
    }

    public static Result companies() {
        return ok(Company.getPaginatedResults(request().body().asFormUrlEncoded()));
        //return ok(Company.getPaginatedResults(request().queryString()));
    }

    public static Result show(String id) {
        Company c = Company.findCompanyByID(id);
        return ok(show.render(c));
    }
}
