package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import commons.StaticFunctions;
import models.Company;
import play.data.Form;
import play.libs.Json;
import play.libs.Json.*;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.company.index;
import views.html.company.show;

/**
 * Created by manoj on 2/4/15.
 */


@Api(value = "/company", description = "Operations about company")
public class CompanyController extends Controller {
    static Form<Company> companyForm = Form.form(Company.class);

    public static Result index() {
        return ok(index.render());
    }

    @ApiOperation(value = "get list of companies", notes = "nothing yet", response = ObjectNode.class, httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid request")})
    //@ApiResponse(code = 404, message = "Order not found")})
    public static Result companies() {
        return ok(Company.getPaginatedResults(request().body().asFormUrlEncoded()));
        //return ok(Company.getPaginatedResults(request().queryString()));
    }

    public static Result show(String id) {
        Company c = Company.findCompanyByID(id);
        return ok(show.render(c));
    }

    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result getSimilarCompanies(String id) {
        return ok(Company.getSimilarCompanies("company_id"));
    }
}
