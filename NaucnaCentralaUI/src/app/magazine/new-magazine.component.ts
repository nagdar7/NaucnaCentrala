import { MagazineService } from "./../services/magazine/magazine.service";
import { Component, OnInit } from "@angular/core";
import { UserService } from "../services/user/user.service";
import { Router } from "@angular/router";

// import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: "app-new-magazine",
  templateUrl: "./new-magazine.component.html",
  styleUrls: ["./magazine.component.css"]
})
export class NewMagazineComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private createNewMagazineState = true;

  // private user = {
  //   username: "",
  //   password: ""
  // }

  // private jwtHelper = new JwtHelperService();

  constructor(
    private magazineService: MagazineService,
    private router: Router
  ) {
    let x = this.magazineService.startProcess();
    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach(field => {
          if (field.type.name == "enum") {
            this.enumValues[field.id] = Object.keys(field.type.values);
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
      var vvv = value[property];
      if (!!value[property].join) {
        vvv = value[property].join(",");
      }
      o.push({ fieldId: property, fieldValue: vvv });
    }

    console.log(o);
    if (this.createNewMagazineState) {
      this.createNewMagazine(o);
      this.createNewMagazineState = false;
    } else {
      this.asignRolesToMagazine(o);
    }
  }

  asignRolesToMagazine(o) {
    let x = this.magazineService.asignRolesToMagazine(
      o,
      this.formFieldsDto.taskId
    );

    x.subscribe(
      res => {
        console.log(res);

        alert("asign roles to magazine successfull!");

        this.router.navigate(["/"]);
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  createNewMagazine(o) {
    let x = this.magazineService.createNewMagazine(
      o,
      this.formFieldsDto.taskId
    );

    x.subscribe(
      res => {
        // console.log(res);

        alert("Magazine created!");

        // this.router.navigate(["/"]);
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach(field => {
          if (field.type.name == "enum") {
            this.enumValues[field.id] = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }
}
