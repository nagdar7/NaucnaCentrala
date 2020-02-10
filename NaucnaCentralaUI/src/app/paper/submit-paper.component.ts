import { MagazineService } from "./../services/magazine/magazine.service";
import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";

// import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: "app-submit-paper",
  templateUrl: "./submit-paper.component.html",
  styleUrls: ["./paper.component.css"]
})
export class SubmitPaperComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private createNewMagazineState = true;
  payMembershipProcess = false;
  magazineId;

  // private user = {
  //   username: "",
  //   password: ""
  // }

  // private jwtHelper = new JwtHelperService();

  constructor(
    private magazineService: MagazineService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.magazineId = this.route.snapshot.params["magazineId"];
    let x = this.magazineService.selectMagazineForSubmitPaper(
      this.magazineId,
      this.magazineService.taskId
    );
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
          if (field.id == "uplati_clanarinu") {
            this.payMembershipProcess = true;
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
    console.log(this.payMembershipProcess);
    if (this.payMembershipProcess) {
      this.payMembership(o);
      this.payMembershipProcess = false;
    } else {
      this.submitPaper(o);
    }
  }

  submitPaper(o) {
    let x = this.magazineService.submitPaper(
      this.magazineId,
      o,
      this.formFieldsDto.taskId
    );

    x.subscribe(
      res => {
        console.log(res);

        alert("Submit paper successfull!");

        this.router.navigate(["/"]);
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  payMembership(o) {
    let x = this.magazineService.payMembership(
      this.magazineId,
      this.formFieldsDto.taskId
    );

    x.subscribe(
      res => {
        // console.log(res);

        alert("Membership payed!");

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
