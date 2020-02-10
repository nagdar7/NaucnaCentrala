import { Component, OnInit } from "@angular/core";
import { UserService } from "../services/user/user.service";
import { Router } from "@angular/router";

// import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  // private user = {
  //   username: "",
  //   password: ""
  // }

  // private jwtHelper = new JwtHelperService();

  constructor(private userService: UserService, private router: Router) {
    let x = userService.startProcess("login");

    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach(field => {
          if (field.type.name == "enum") {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  ngOnInit() {}

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);
    let x = this.userService.loginUser(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        alert("login successfull!");

        this.router.navigate(["/"]);
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }
}
