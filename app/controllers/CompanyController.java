package controllers;

import models.Company;
import org.bson.types.ObjectId;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.company.index;

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

    public static Result newCompany() {
        Form<Company> filledForm = companyForm.bindFromRequest();
//        if(filledForm.hasErrors()) {
//            return badRequest(companies.render(Company.getPaginatedResults(request())));
//        } else {
            Company.create(filledForm.get());
            return redirect(routes.CompanyController.companies());
//        }
    }

    public static Result deleteCompany(String id) {
        ObjectId oid =  new org.bson.types.ObjectId(id);
        Company.delete(oid);
        return redirect(routes.CompanyController.companies());
    }
}
