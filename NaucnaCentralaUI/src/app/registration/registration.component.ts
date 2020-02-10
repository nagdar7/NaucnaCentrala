import { Component, OnInit } from "@angular/core";
import { UserService } from "../services/user/user.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-registration",
  templateUrl: "./registration.component.html",
  styleUrls: ["./registration.component.css"]
})
export class RegistrationComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private userService: UserService, private router: Router) {
    let x = userService.startProcess("registration");

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
        alert(err.error.message);
        console.log("Error occured", err);
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
    let x = this.userService.registerUser(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        alert("You registered successfully please check email!");

        this.router.navigate(["/"]);
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  // getTasks(){
  //   let x = this.repositoryService.getTasks(this.processInstance);

  //   x.subscribe(
  //     res => {
  //       console.log(res);
  //       this.tasks = res;
  //     },
  //     err => {
  //       console.log("Error occured");
  //     }
  //   );
  //  }

  //  claim(taskId){
  //   let x = this.repositoryService.claimTask(taskId);

  //   x.subscribe(
  //     res => {
  //       console.log(res);
  //     },
  //     err => {
  //       console.log("Error occured");
  //     }
  //   );
  //  }

  //  complete(taskId){
  //   let x = this.repositoryService.completeTask(taskId);

  //   x.subscribe(
  //     res => {
  //       console.log(res);
  //       this.tasks = res;
  //     },
  //     err => {
  //       console.log("Error occured");
  //     }
  //   );
  //  }
}
